package br.com.sgi.action;

import br.com.sgi.controller.BusinesLogicAbstract;
import br.com.sgi.controller.BusinessLogic;
import br.com.sgi.model.Categoria;
import br.com.sgi.model.Mensagem;
import br.com.sgi.persistence.CategoriaDAO;
import br.com.sgi.util.PopulateObject;
import br.com.sgi.util.RegraNegocioException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CategoriaAction extends BusinesLogicAbstract implements BusinessLogic{

    private List<Mensagem> mensagens;
    private Categoria categoria;
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        String acao = request.getParameter("action");
        mensagens = new ArrayList<>();
        
        switch(acao.toLowerCase()){
            case "inserir":
                formulario(request, response, new Categoria(), null);
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
        request.setAttribute("nomePagina", "categoria");
    }

    @Override
    public void listar(HttpServletRequest request, HttpServletResponse response, List<Mensagem> mensagens) throws ServletException, IOException {
        getNomePagina(request);
        request.setAttribute("mensagens", mensagens);
        request.setAttribute("listaCategorias", new CategoriaDAO().findAll("Categoria", "descricao"));
        request.getRequestDispatcher("categorias.jsp").forward(request, response);
    }

    @Override
    public void pesquisar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getNomePagina(request);
        
        String descricao = request.getParameter("descricao");
        
        request.setAttribute("listaCategorias", new CategoriaDAO().findByDescricao(descricao));
        request.getRequestDispatcher("categorias.jsp").forward(request, response);
    }

    @Override
    public void formulario(HttpServletRequest request, HttpServletResponse response, Object object, List<Mensagem> mensagens) throws ServletException, IOException {
        getNomePagina(request);
        request.setAttribute("objCategoria", ((Categoria)object));
        request.setAttribute("mensagens", mensagens);
        request.getRequestDispatcher("categoriaForm.jsp").forward(request, response);
    }
    
    @Override
    public void alterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Categoria categoriaParam = new CategoriaDAO().findById(Categoria.class, Integer.parseInt(request.getParameter("id")));
        formulario(request, response, categoriaParam, null);
    }

    @Override
    public void salvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            categoria = (Categoria) PopulateObject.createObjectBusiness(new Categoria(), request);
            categoria.setId(Integer.parseInt(request.getParameter("id")));

            if(categoria.getId() == 0){
                if(new CategoriaDAO().inserir(categoria)){
                    mensagens.add(new Mensagem("Sucesso ao salvar categoria", Mensagem.TIPO_SUCESSO));
                    listar(request, response, mensagens);
                }else{
                    mensagens.add(new Mensagem("Ops! Erro ao tentar salvar categoria", Mensagem.TIPO_ERRO));
                    formulario(request, response, categoria, mensagens);
                }
            }else{
                if(new CategoriaDAO().alterar(categoria)){
                    mensagens.add(new Mensagem("Sucesso ao alterar categoria", Mensagem.TIPO_SUCESSO));
                    listar(request, response, mensagens);
                }else{
                    mensagens.add(new Mensagem("Ops! Erro ao tentar alterar categoria", Mensagem.TIPO_ERRO));
                    formulario(request, response, categoria, mensagens);
                }
            }
        }catch(RegraNegocioException e){
            formulario(request, response, categoria, e.getMensagens());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void remover(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isOk = new CategoriaDAO().remove(Categoria.class, id);

        if(isOk){
            mensagens.add(new Mensagem("Sucess ao tentar excluir a categoria", Mensagem.TIPO_SUCESSO));
        }else{
            mensagens.add(new Mensagem("Ops! Erro ao tentar excluir categoria, "
                    + "verifique se esta categoria está sendo usada por um lançamento", Mensagem.TIPO_SUCESSO));            
        }
        
        listar(request, response, mensagens);
    }
}
