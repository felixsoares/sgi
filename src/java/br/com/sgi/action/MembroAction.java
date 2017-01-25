package br.com.sgi.action;

import br.com.sgi.controller.BusinesLogicAbstract;
import br.com.sgi.controller.BusinessLogic;
import br.com.sgi.model.Membro;
import br.com.sgi.model.Mensagem;
import br.com.sgi.persistence.MembroDAO;
import br.com.sgi.util.PopulateObject;
import br.com.sgi.util.RegraNegocioException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MembroAction extends BusinesLogicAbstract implements BusinessLogic{

    private List<Mensagem> mensagens;
    private Membro membro;
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        String acao = request.getParameter("action");
        mensagens = new ArrayList<>();
        
        switch(acao.toLowerCase()){
            case "inserir":
                formulario(request, response, new Membro(), null);
                break;
            case "alterar":
                alterar(request, response);
                break;
            case "listar":
                listar(request, response, null);
                break;
            case "salvar":
                salvar(request,response);
                break;
            case "pesquisar":
                pesquisar(request, response);
                break;
            case "remover":
                remover(request, response);
                break;
        }
    }

    @Override
    public void getNomePagina(HttpServletRequest request) {
        request.setAttribute("nomePagina", "membro");       
    }

    @Override
    public void listar(HttpServletRequest request, HttpServletResponse response, List<Mensagem> mensagens) throws ServletException, IOException {
        getNomePagina(request);
        request.setAttribute("mensagens", mensagens);
        request.setAttribute("listaMembros", new MembroDAO().findAll("Membro", "nome"));
        request.getRequestDispatcher("membros.jsp").forward(request, response);
    }

    @Override
    public void pesquisar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getNomePagina(request);
        
        String nome = request.getParameter("nome");
        
        request.setAttribute("listaMembros", new MembroDAO().findByNome(nome));
        request.getRequestDispatcher("membros.jsp").forward(request, response);
    }

    @Override
    public void formulario(HttpServletRequest request, HttpServletResponse response, Object object, List<Mensagem> mensagens) throws ServletException, IOException {
        getNomePagina(request);
        request.setAttribute("objMembro", ((Membro)object));
        request.setAttribute("mensagens", mensagens);
        request.getRequestDispatcher("membroForm.jsp").forward(request, response);
    }
    
    @Override
    public void alterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Membro membroParam = new MembroDAO().findById(Membro.class, Integer.parseInt(request.getParameter("id")));
        formulario(request, response, membroParam, null);
    }

    @Override
    public void salvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            membro = (Membro) PopulateObject.createObjectBusiness(new Membro(), request);
            membro.setId(Integer.parseInt(request.getParameter("id")));

            if(membro.getId() == 0){
                if(new MembroDAO().inserir(membro)){
                    mensagens.add(new Mensagem("Sucesso ao salvar membro", Mensagem.TIPO_SUCESSO));
                    listar(request, response, mensagens);
                }else{
                    mensagens.add(new Mensagem("Ops! Erro ao tentar salvar membro", Mensagem.TIPO_ERRO));
                    formulario(request, response, membro, mensagens);
                }
            }else{
                if(new MembroDAO().alterar(membro)){
                    mensagens.add(new Mensagem("Sucesso ao alterar membro", Mensagem.TIPO_SUCESSO));
                    listar(request, response, mensagens);
                }else{
                    mensagens.add(new Mensagem("Ops! Erro ao tentar alterar membro", Mensagem.TIPO_ERRO));
                    formulario(request, response, membro, mensagens);
                }
            }
        }catch(RegraNegocioException e){
            formulario(request, response, membro, e.getMensagens());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void remover(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isOk = new MembroDAO().remove(Membro.class, id);

        if(isOk){
            mensagens.add(new Mensagem("Sucess ao tentar excluir a membro", Mensagem.TIPO_SUCESSO));
        }else{
            mensagens.add(new Mensagem("Ops! Erro ao tentar excluir membro, "
                    + "verifique se esta membro está sendo usada por um lançamento", Mensagem.TIPO_SUCESSO));            
        }
        
        listar(request, response, mensagens);
    }
}
