package br.com.sgi.action;

import br.com.sgi.controller.BusinesLogicAbstract;
import br.com.sgi.controller.BusinessLogic;
import br.com.sgi.model.Conta;
import br.com.sgi.model.Mensagem;
import br.com.sgi.persistence.ContaDAO;
import br.com.sgi.util.PopulateObject;
import br.com.sgi.util.RegraNegocioException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContaAction extends BusinesLogicAbstract implements BusinessLogic{

    private List<Mensagem> mensagens;
    private Conta conta;
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        String acao = request.getParameter("action");
        mensagens = new ArrayList<>();
        
        switch(acao.toLowerCase()){
            case "inserir":
                formulario(request, response, new Conta(), null);
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
        request.setAttribute("nomePagina", "conta");
    }

    @Override
    public void listar(HttpServletRequest request, HttpServletResponse response, List<Mensagem> mensagens) throws ServletException, IOException {
        getNomePagina(request);
        request.setAttribute("mensagens", mensagens);
        request.setAttribute("listaContas", new ContaDAO().findAll("Conta", "nome"));
        request.getRequestDispatcher("contas.jsp").forward(request, response);
    }

    @Override
    public void pesquisar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getNomePagina(request);
        
        String nome = request.getParameter("nome");
        
        request.setAttribute("listaContas", new ContaDAO().findByName(nome));
        request.getRequestDispatcher("contas.jsp").forward(request, response);
    }

    @Override
    public void formulario(HttpServletRequest request, HttpServletResponse response, Object object, List<Mensagem> mensagens) throws ServletException, IOException {
        getNomePagina(request);
        request.setAttribute("objConta", ((Conta)object));
        request.setAttribute("mensagens", mensagens);
        request.getRequestDispatcher("contaForm.jsp").forward(request, response);
    }
    
    @Override
    public void alterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Conta contaParam = new ContaDAO().findById(Conta.class, Integer.parseInt(request.getParameter("id")));
        formulario(request, response, contaParam, null);
    }

    @Override
    public void salvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            conta = (Conta) PopulateObject.createObjectBusiness(new Conta(), request);
            conta.setId(Integer.parseInt(request.getParameter("id")));

            if(request.getParameter("saldo") != null && !request.getParameter("saldo").equals("")){
                String valor = request.getParameter("saldo");
                valor = valor.replace(".", "");
                valor = valor.replace(",", ".");
                conta.setSaldo(Double.parseDouble(valor));
            }else{
                conta.setSaldo(0.0);
            }
            
            if(conta.getId() == 0){
                if(new ContaDAO().inserir(conta)){
                    mensagens.add(new Mensagem("Sucesso ao salvar conta", Mensagem.TIPO_SUCESSO));
                    listar(request, response, mensagens);
                }else{
                    mensagens.add(new Mensagem("Ops! Erro ao tentar salvar conta", Mensagem.TIPO_ERRO));
                    formulario(request, response, conta, mensagens);
                }
            }else{
                if(new ContaDAO().alterar(conta)){
                    mensagens.add(new Mensagem("Sucesso ao alterar conta", Mensagem.TIPO_SUCESSO));
                    listar(request, response, mensagens);
                }else{
                    mensagens.add(new Mensagem("Ops! Erro ao tentar alterar conta", Mensagem.TIPO_ERRO));
                    formulario(request, response, conta, mensagens);
                }
            }
        }catch(RegraNegocioException e){
            formulario(request, response, conta, e.getMensagens());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void remover(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isOk = new ContaDAO().remove(Conta.class, id);

        if(isOk){
            mensagens.add(new Mensagem("Sucess ao tentar excluir a conta", Mensagem.TIPO_SUCESSO));
        }else{
            mensagens.add(new Mensagem("Ops! Erro ao tentar excluir conta, "
                    + "verifique se esta conta está sendo usada por um lançamento", Mensagem.TIPO_SUCESSO));            
        }
        
        listar(request, response, mensagens);
    }
}
