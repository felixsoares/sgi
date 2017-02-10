package br.com.sgi.persistence;

import br.com.sgi.model.Membro;
import br.com.sgi.model.Mensagem;
import br.com.sgi.model.dto.RelatorioMembroDTO;
import br.com.sgi.util.RegraNegocioException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class MembroDAO extends GenericDAO<Membro> implements IGenericDAO{
    
    @Override
    public Boolean inserir(Object objeto) throws RegraNegocioException{
        validaRegras(((Membro)objeto));
        return persist((Membro)objeto);
    }
    
    @Override
    public Boolean alterar(Object objeto) throws RegraNegocioException{
        validaRegras(((Membro)objeto));
        return save((Membro)objeto);
    }

    @Override
    public Boolean deletar(int id) throws RegraNegocioException{
        return remove(Membro.class, id);
    }

    @Override
    public void validaRegras(Object objeto) throws RegraNegocioException{
        List<Mensagem> mensagens = new ArrayList<>();
        
        Membro membro = ((Membro)objeto);
        
        if(membro.getNome() == null || membro.getNome().trim().equals("")){
            mensagens.add(new Mensagem("Campo 'Nome' é obrigatório", Mensagem.TIPO_ERRO));
        }
        
        if(mensagens.size() > 0){
            throw new RegraNegocioException(mensagens);
        }
    }
    
    public List<Membro> findByNome(String nome){
        List<Membro> lista;
        
        EntityManager entityManager = null;
        
        try {
            entityManager = getEMF().createEntityManager();
            
            String queryString = "SELECT m FROM Membro m";
            
            if(nome != null && !nome.equals("")){
                queryString += " WHERE m.nome LIKE :nome";
            }
            
            Query query = entityManager.createQuery(queryString);
            
            if(nome != null && !nome.equals("")){
                query.setParameter("nome", "%"+nome+"%");
            }
            
            lista = query.getResultList();
            
        } catch (Exception e) {
            lista = null;
            System.err.println("Erro: " + e.getMessage());
        } finally {
            entityManager.close();
            getEMF().close();
        }
        
        return lista;
    }
    
    public List<RelatorioMembroDTO> getRelatorioMembro(int id){
        
        List<RelatorioMembroDTO> lista = new ArrayList<>();
        EntityManager entityManager = null;
        
        try {
            entityManager = getEMF().createEntityManager();
            
            String queryString = "SELECT m.nome, l.dataLancada, l.valor " +
                                 "FROM Lancamento l " +
                                 "JOIN l.membro m " +
                                 "WHERE m.id = :id "+
                                 "ORDER BY l.dataLancada ASC";
            
            Query query = entityManager.createQuery(queryString);
            query.setParameter("id", id);
            
            List<Object[]> resultado = query.getResultList();
            
            if(resultado != null && resultado.size() > 0){
                for(Object[] valores : resultado){
                    RelatorioMembroDTO membroDTO = new RelatorioMembroDTO();
                    membroDTO.setNome(valores[0].toString());
                    membroDTO.setData(valores[1].toString());
                    membroDTO.setValor(Double.parseDouble(valores[2].toString()));
                    lista.add(membroDTO);
                }
            }
            
        } catch (Exception e) {
            lista = null;
            System.err.println("Erro: " + e.getMessage());
        } finally {
            entityManager.close();
            getEMF().close();
        }        
        
        return lista;
    }
    
    public List<RelatorioMembroDTO> getRelatorioLancamentoMensal(Date primeiroDia, Date ultimoDia){
        
        EntityManager entityManager = null;
        List<RelatorioMembroDTO> listaRelatorio = new ArrayList<>();
        
        try{
            
            entityManager = getEMF().createEntityManager();
            
            String queryString = "SELECT m.nome, l.dataLancada, l.valor " +
                                 "FROM Lancamento l " +
                                 "JOIN l.membro m " +
                                 "WHERE l.dataLancada >= :primeiroDia AND l.dataLancada <= :ultimoDia "+
                                 "ORDER BY m.nome ASC";
            
            Query query = entityManager.createQuery(queryString);
            query.setParameter("primeiroDia", primeiroDia);
            query.setParameter("ultimoDia", ultimoDia);
            
            List<Object[]> resultado = query.getResultList();
            
            if(resultado != null && resultado.size() > 0){
                for(Object[] valores : resultado){
                    RelatorioMembroDTO membroDTO = new RelatorioMembroDTO();
                    membroDTO.setNome(valores[0].toString());
                    membroDTO.setData(valores[1].toString());
                    membroDTO.setValor(Double.parseDouble(valores[2].toString()));
                    listaRelatorio.add(membroDTO);
                }
            }
            
        }catch(Exception e){
            listaRelatorio = null;
            System.err.println("Erro: " + e.getMessage());
        }finally{
            entityManager.close();
            getEMF().close();
        }
        
        return listaRelatorio;
    }
}
