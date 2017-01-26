package br.com.sgi.persistence;

import br.com.sgi.model.Lancamento;
import br.com.sgi.model.Mensagem;
import br.com.sgi.model.dto.RelatorioLancamentoDTO;
import br.com.sgi.model.dto.RelatorioMembroDTO;
import br.com.sgi.util.RegraNegocioException;
import br.com.sgi.util.SGIUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class LancamentoDAO extends  GenericDAO<Lancamento> implements IGenericDAO{
    
    @Override
    public Boolean inserir(Object objeto) throws RegraNegocioException{
        validaRegras(((Lancamento)objeto));
        
        if(((Lancamento)objeto).getDataLancada() == null)
            ((Lancamento)objeto).setDataLancada(new Date());
        
        return persist((Lancamento)objeto);
    }
    
    @Override
    public Boolean alterar(Object objeto) throws RegraNegocioException{
        validaRegras(((Lancamento)objeto));
        return save((Lancamento)objeto);
    }

    @Override
    public Boolean deletar(int id) throws RegraNegocioException{
        return remove(Lancamento.class, id);
    }

    @Override
    public void validaRegras(Object objeto) throws RegraNegocioException{
        List<Mensagem> mensagens = new ArrayList<>();
        
        Lancamento lancamento = ((Lancamento)objeto);
        
        if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")){
            mensagens.add(new Mensagem("Campo 'Descrição' é obrigatório", Mensagem.TIPO_ERRO));
        }
        
        if(lancamento.getValor() == null || lancamento.getValor().equals(0.0)){
            mensagens.add(new Mensagem("Campo 'Valor' é obrigatório e maior que 0,00", Mensagem.TIPO_ERRO));            
        }
        
        if(lancamento.getTipo() == null){
            mensagens.add(new Mensagem("Campo 'Tipo' é obrigatório", Mensagem.TIPO_ERRO));
        }
        
        if(lancamento.getPagoRecebido() == null){
            mensagens.add(new Mensagem("Campo 'Pago / Recebido' é obrigatório", Mensagem.TIPO_ERRO));            
        }
        
        if(lancamento.getConta() == null){
            mensagens.add(new Mensagem("Campo 'Conta' é obrigatório", Mensagem.TIPO_ERRO));            
        }
        
        if(mensagens.size() > 0){
            throw new RegraNegocioException(mensagens);
        }
    }
    
    public List<Lancamento> find(Date dataInicio, Date dataFinal, String descricao, Boolean pagoRecebido){
        List<Lancamento> lista;
        
        EntityManager entityManager = null;
        
        try {
            entityManager = getEMF().createEntityManager();
            
            String queryString = "SELECT lc FROM Lancamento lc WHERE 1 = 1";
            
            if(descricao != null && !descricao.equals("")){
                queryString += " AND lc.descricao LIKE :descricao";
            }
            
            if(dataInicio != null){
                queryString += " AND lc.dataLancada >= :dataInicio";
            }
            
            if(dataFinal != null){
                queryString += " AND lc.dataLancada <= :dataFinal";
            }
            
            if(pagoRecebido != null){
                queryString += " AND lc.pagoRecebido = :pagoRecebido";
            }
            
            Query query = entityManager.createQuery(queryString);
            
            if(descricao != null && !descricao.equals("")){
                query.setParameter("descricao", "%"+descricao+"%");
            }
            
            if(dataInicio != null){
                query.setParameter("dataInicio", dataInicio);
            }
            
            if(dataFinal != null){
                query.setParameter("dataFinal", dataFinal);
            }
            
            if(pagoRecebido != null){
                query.setParameter("pagoRecebido", pagoRecebido);
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
    
    public RelatorioLancamentoDTO getRelatorioLancamento(Date primeiroDia, Date ultimoDia){
        RelatorioLancamentoDTO lancamentoDTO = new RelatorioLancamentoDTO();
        
        EntityManager entityManager = null;
        
        try {
            entityManager = getEMF().createEntityManager();
            
            String queryString = "SELECT DISTINCT SUM(l.valor) " +
                                 "FROM Lancamento l " +
                                 "WHERE l.dataLancada >= :primeiroDia AND l.dataLancada <= :ultimoDia AND l.tipo = 0";
            
            Query query = entityManager.createQuery(queryString);
            query.setParameter("primeiroDia", primeiroDia);
            query.setParameter("ultimoDia", ultimoDia);
            
            Double resultado = (Double)query.getSingleResult();
            
            if(resultado != null){
                lancamentoDTO.setValorDespesa(SGIUtil.getPrecisao(resultado));
            }
            
            queryString = "SELECT DISTINCT SUM(l.valor) " +
                          "FROM Lancamento l " +
                          "WHERE l.dataLancada >= :primeiroDia AND l.dataLancada <= :ultimoDia AND l.tipo = 1";
            
            query = entityManager.createQuery(queryString);
            query.setParameter("primeiroDia", primeiroDia);
            query.setParameter("ultimoDia", ultimoDia);
            
            resultado = (Double)query.getSingleResult();
            
            if(resultado != null){
                lancamentoDTO.setValorReceita(SGIUtil.getPrecisao(resultado));
            }
            
        } catch (Exception e) {
            lancamentoDTO = null;
            System.err.println("Erro: " + e.getMessage());
        } finally {
            entityManager.close();
            getEMF().close();
        } 
        
        return lancamentoDTO;
    }
    
    public RelatorioLancamentoDTO getLancamentoByConta(int idConta){
        RelatorioLancamentoDTO lancamentoDTO = new RelatorioLancamentoDTO();
        
        EntityManager entityManager = null;
        
        try {
            entityManager = getEMF().createEntityManager();
            
            String queryString = "SELECT DISTINCT SUM(l.valor) " +
                                 "FROM Lancamento l " +
                                 "WHERE l.conta.id = :idConta AND l.tipo = 0";
            
            Query query = entityManager.createQuery(queryString);
            query.setParameter("idConta", idConta);
            
            Double resultado = (Double)query.getSingleResult();
            
            if(resultado != null){
                lancamentoDTO.setValorDespesa(SGIUtil.getPrecisao(resultado));
            }
            
            queryString = "SELECT DISTINCT SUM(l.valor) " +
                          "FROM Lancamento l " +
                          "WHERE l.conta.id = :idConta AND l.tipo = 1";
            
            query = entityManager.createQuery(queryString);
            query.setParameter("idConta", idConta);
            
            resultado = (Double)query.getSingleResult();
            
            if(resultado != null){
                lancamentoDTO.setValorReceita(SGIUtil.getPrecisao(resultado));
            }
            
        } catch (Exception e) {
            lancamentoDTO = null;
            System.err.println("Erro: " + e.getMessage());
        } finally {
            entityManager.close();
            getEMF().close();
        } 
        
        return lancamentoDTO;
    }
}
