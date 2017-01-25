package br.com.sgi.persistence;

import br.com.sgi.model.Categoria;
import br.com.sgi.model.Mensagem;
import br.com.sgi.model.dto.RelatorioCategoriaDTO;
import br.com.sgi.model.dto.RelatorioMembroDTO;
import br.com.sgi.util.RegraNegocioException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CategoriaDAO extends GenericDAO<Categoria> implements IGenericDAO{
    
    @Override
    public Boolean inserir(Object objeto) throws RegraNegocioException{
        validaRegras(((Categoria)objeto));
        return persist((Categoria)objeto);
    }
    
    @Override
    public Boolean alterar(Object objeto) throws RegraNegocioException{
        validaRegras(((Categoria)objeto));
        return save((Categoria)objeto);
    }

    @Override
    public Boolean deletar(int id) throws RegraNegocioException{
        return remove(Categoria.class, id);
    }

    @Override
    public void validaRegras(Object objeto) throws RegraNegocioException{
        List<Mensagem> mensagens = new ArrayList<>();
        
        Categoria categoria = ((Categoria)objeto);
        
        if(categoria.getDescricao() == null || categoria.getDescricao().trim().equals("")){
            mensagens.add(new Mensagem("Campo 'Descrição' é obrigatório", Mensagem.TIPO_ERRO));
        }
        
        if(mensagens.size() > 0){
            throw new RegraNegocioException(mensagens);
        }
    }
    
    public List<Categoria> findByDescricao(String descricao){
        List<Categoria> lista;
        
        EntityManager entityManager = null;
        
        try {
            entityManager = getEMF().createEntityManager();
            
            String queryString = "SELECT c FROM Categoria c";
            
            if(descricao != null && !descricao.equals("")){
                queryString += " WHERE c.descricao LIKE :descricao";
            }
            
            Query query = entityManager.createQuery(queryString);
            
            if(descricao != null && !descricao.equals("")){
                query.setParameter("descricao", "%"+descricao+"%");
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
    
    public List<RelatorioCategoriaDTO> getRelatorioCategoria(Date primeiroDia, Date ultimoDia){
        
        List<RelatorioCategoriaDTO> lista = new ArrayList<>();
        EntityManager entityManager = null;
        
        try {
            entityManager = getEMF().createEntityManager();
            
            String queryString = "SELECT DISTINCT c.id, c.descricao, SUM(l.valor) " +
                                 "FROM Lancamento l " +
                                 "JOIN l.categoria c " +
                                 "WHERE l.dataLancada >= :primeiroDia AND l.dataLancada <= :ultimoDia "+
                                 "GROUP BY c.id";
            
            Query query = entityManager.createQuery(queryString);
            query.setParameter("primeiroDia", primeiroDia);
            query.setParameter("ultimoDia", ultimoDia);
            
            List<Object[]> resultado = query.getResultList();
            
            if(resultado != null && resultado.size() > 0){
                for(Object[] valores : resultado){
                    RelatorioCategoriaDTO categoriaDTO = new RelatorioCategoriaDTO();
                    categoriaDTO.setNome(valores[1].toString());
                    categoriaDTO.setValor(Double.parseDouble(valores[2].toString()));
                    lista.add(categoriaDTO);
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
}
