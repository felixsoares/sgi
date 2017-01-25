package br.com.sgi.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerServlet extends HttpServlet {
   
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        
        //Crio uma variavel que será lida pela requisição.
        String businessLogicClassName = request.getParameter("business");
        //Crio um objeto do tipo Class.
        Class businessLogicClass = null;

        try {
            // Verifico se a classe requisitada existe no projeto
            businessLogicClass = Class.forName("br.com.sgi.action." + businessLogicClassName);
        } catch (ClassNotFoundException e) {
            throw new ServletException("Não encontro a classe " + businessLogicClassName);
        }
        
        //Verifico se a classe requisitada esta implementando a Interface BusinessLogic
        if (!BusinessLogic.class.isAssignableFrom(businessLogicClass)) {
            throw new ServletException("Classe não implementa a interface: " + businessLogicClassName);
        }
        
        // Crio um objeto do tipo BusinessLogic.
        BusinessLogic businessLogicObject = null;
        try {
            // Crio uma instancia da classe requisitada.
            businessLogicObject = (BusinessLogic) businessLogicClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ServletException(e);
        }
        try {
            //Invoco o método execute da classe.
            businessLogicObject.execute(request, response);
        } catch (ServletException | IOException e) {
            throw new ServletException("A lógica de negócios causou uma exceção.", e);
        }

    }

}
