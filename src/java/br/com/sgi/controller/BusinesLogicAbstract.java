package br.com.sgi.controller;

import br.com.sgi.model.Mensagem;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BusinesLogicAbstract {
    
    public abstract void listar(HttpServletRequest request, HttpServletResponse response, List<Mensagem> mensagens) throws ServletException, IOException;

    public abstract void pesquisar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    public abstract void formulario(HttpServletRequest request, HttpServletResponse response, Object object, List<Mensagem> mensagens) throws ServletException, IOException;

    public abstract void alterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    
    public abstract void salvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    
    public abstract void remover(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
