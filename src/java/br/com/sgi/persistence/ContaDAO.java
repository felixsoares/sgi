package br.com.sgi.persistence;

import br.com.sgi.model.Conta;
import br.com.sgi.model.Mensagem;
import br.com.sgi.model.dto.RelatorioMembroDTO;
import br.com.sgi.model.dto.RelatorioSaldoDTO;
import br.com.sgi.util.RegraNegocioException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ContaDAO extends GenericDAO<Conta> implements IGenericDAO{

    @Override
    public Boolean inserir(Object objeto) throws RegraNegocioException{
        validaRegras(((Conta)objeto));
        return persist((Conta)objeto);
    }
    
    @Override
    public Boolean alterar(Object objeto) throws RegraNegocioException{
        validaRegras(((Conta)objeto));
        return save((Conta)objeto);
    }

    @Override
    public Boolean deletar(int id) throws RegraNegocioException{
        return remove(Conta.class, id);
    }

    @Override
    public void validaRegras(Object objeto) throws RegraNegocioException{
        List<Mensagem> mensagens = new ArrayList<>();
        
        Conta conta = ((Conta)objeto);
        
        if(conta.getNome() == null || conta.getNome().equals("")){
            mensagens.add(new Mensagem("Campo 'Descrição' é obrigatório", Mensagem.TIPO_ERRO));
        }
        
        if(conta.getSaldo() == null || conta.getSaldo().equals(0.0)){
            mensagens.add(new Mensagem("Campo 'Saldo' é obrigatório", Mensagem.TIPO_ERRO));
        }
        
        if(mensagens.size() > 0){
            throw new RegraNegocioException(mensagens);
        }
    }
    
    public List<Conta> findByName(String nome){
        List<Conta> lista;
        
        EntityManager entityManager = null;
        
        try {
            entityManager = getEMF().createEntityManager();
            
            String queryString = "SELECT conta FROM Conta conta";
            
            if(nome != null && !nome.equals("")){
                queryString += " WHERE conta.nome LIKE :nome";
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
    
    public List<RelatorioSaldoDTO> getRelatorioSaldo(){
        
        List<RelatorioSaldoDTO> lista = new ArrayList<>();
        EntityManager entityManager = null;
        
        try {
            entityManager = getEMF().createEntityManager();
            
            String queryString = "SELECT c.id, c.nome, c.saldo " +
                                 "FROM Conta c " +
                                 "ORDER BY c.nome";
            
            Query query = entityManager.createQuery(queryString);
            
            List<Object[]> resultado = query.getResultList();
            
            if(resultado != null && resultado.size() > 0){
                for(Object[] valores : resultado){
                    
                    int idConta = Integer.parseInt(valores[0].toString());
                    
                    Conta conta = new Conta();
                    conta.setId(idConta);
                    conta.setNome(valores[1].toString());
                    conta.setSaldo(Double.parseDouble(valores[2].toString()));
                    
                    RelatorioSaldoDTO saldoDTO = new RelatorioSaldoDTO();
                    saldoDTO.setConta(conta);
                    saldoDTO.setLancamentoDTO(new LancamentoDAO().getLancamentoByConta(idConta));
                    lista.add(saldoDTO);
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
