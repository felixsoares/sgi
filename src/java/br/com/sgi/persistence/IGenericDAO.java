package br.com.sgi.persistence;

import br.com.sgi.util.RegraNegocioException;

public interface IGenericDAO {
    
    public Boolean inserir(Object objeto) throws RegraNegocioException;
    
    public Boolean alterar(Object objeto) throws RegraNegocioException;
    
    public Boolean deletar(int id) throws RegraNegocioException;
    
    public void validaRegras(Object objeto) throws RegraNegocioException;    
}
