package br.com.sgi.action;

import br.com.sgi.controller.BusinesLogicAbstract;
import br.com.sgi.controller.BusinessLogic;
import br.com.sgi.model.Categoria;
import br.com.sgi.model.Conta;
import br.com.sgi.model.Lancamento;
import br.com.sgi.model.Membro;
import br.com.sgi.model.Mensagem;
import br.com.sgi.persistence.CategoriaDAO;
import br.com.sgi.persistence.ContaDAO;
import br.com.sgi.persistence.LancamentoDAO;
import br.com.sgi.persistence.MembroDAO;
import br.com.sgi.util.RegraNegocioException;
import br.com.sgi.util.SGIUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DashboardAction extends BusinesLogicAbstract implements BusinessLogic{

    private List<Mensagem> mensagens;
    private Lancamento lancamento;
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        String acao = request.getParameter("action");
        mensagens = new ArrayList<>();
        
        switch(acao.toLowerCase()){
            case "inserir":
                formulario(request, response, new Lancamento(), null);
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
        request.setAttribute("nomePagina", "lancamento");
    }

    @Override
    public void listar(HttpServletRequest request, HttpServletResponse response, List<Mensagem> mensagens) throws ServletException, IOException {
        getNomePagina(request);
        request.setAttribute("mensagens", mensagens);
        
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        Date primeiroDia = aCalendar.getTime();
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date ultimoDia = aCalendar.getTime();
        
        request.setAttribute("primeiroDia", primeiroDia);
        request.setAttribute("ultimoDia", ultimoDia);
        request.setAttribute("listaLancamentos", new LancamentoDAO().find(primeiroDia, ultimoDia, null, null));
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    @Override
    public void pesquisar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            getNomePagina(request);

            Date primeiroDia = null, ultimoDia = null;
            String descricao = request.getParameter("descricao");
            String pagoRecebidoString = request.getParameter("pagoRecebido");
            Boolean pagoRecebido = null;
            
            if(request.getParameter("primeiroDia") != null && !request.getParameter("primeiroDia").trim().equals("")){
                primeiroDia = SGIUtil.getPrimeiraHoraDia(SGIUtil.formataData(request.getParameter("primeiroDia")));
            }
            
            if(request.getParameter("ultimoDia") != null && !request.getParameter("ultimoDia").trim().equals("")){
                ultimoDia = SGIUtil.getUltimaHoraDia(SGIUtil.formataData(request.getParameter("ultimoDia")));
            }
            
            if(pagoRecebidoString != null && !pagoRecebidoString.equals("")){
                pagoRecebido = Boolean.parseBoolean(pagoRecebidoString);
            }

            request.setAttribute("primeiroDia", primeiroDia);
            request.setAttribute("ultimoDia", ultimoDia);
            request.setAttribute("listaLancamentos", new LancamentoDAO().find(primeiroDia, ultimoDia, descricao, pagoRecebido));
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void formulario(HttpServletRequest request, HttpServletResponse response, Object object, List<Mensagem> mensagens) throws ServletException, IOException {
        getNomePagina(request);
        request.setAttribute("objLancamento", ((Lancamento)object));
        request.setAttribute("mensagens", mensagens);
        request.setAttribute("contas", new ContaDAO().findAll("Conta", "nome"));
        request.setAttribute("categorias", new CategoriaDAO().findAll("Categoria", "descricao"));
        request.setAttribute("membros", new MembroDAO().findAll("Membro", "nome"));
        request.getRequestDispatcher("lancamentoForm.jsp").forward(request, response);
    }
    
    @Override
    public void alterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Lancamento lancamentoParam = new LancamentoDAO().findById(Lancamento.class, Integer.parseInt(request.getParameter("id")));
        formulario(request, response, lancamentoParam, null);
    }

    @Override
    public void salvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            lancamento = populaDadosLancamento(request);
            
            if(lancamento.getId() == 0){
                if(new LancamentoDAO().inserir(lancamento)){
                    mensagens.add(new Mensagem("Sucesso ao salvar lancamento", Mensagem.TIPO_SUCESSO));
                    listar(request, response, mensagens);
                }else{
                    mensagens.add(new Mensagem("Ops! Erro ao tentar salvar lancamento", Mensagem.TIPO_ERRO));
                    formulario(request, response, lancamento, mensagens);
                }
            }else{
                if(new LancamentoDAO().alterar(lancamento)){
                    mensagens.add(new Mensagem("Sucesso ao alterar lancamento", Mensagem.TIPO_SUCESSO));
                    listar(request, response, mensagens);
                }else{
                    mensagens.add(new Mensagem("Ops! Erro ao tentar alterar lancamento", Mensagem.TIPO_ERRO));
                    formulario(request, response, lancamento, mensagens);
                }
            }
        }catch(RegraNegocioException e){
            formulario(request, response, lancamento, e.getMensagens());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private Lancamento populaDadosLancamento(HttpServletRequest request){
        try{
            Lancamento lancamentoAux = new Lancamento();
            lancamentoAux.setId(Integer.parseInt(request.getParameter("id")));
            lancamentoAux.setDescricao(request.getParameter("descricao"));
            lancamentoAux.setDataLancada(SGIUtil.formataData(request.getParameter("dataLancada")));

            if(request.getParameter("valor") != null && !request.getParameter("valor").equals("")){
                String valor = request.getParameter("valor");
                valor = valor.replace(".", "");
                valor = valor.replace(",", ".");
                lancamentoAux.setValor(Double.parseDouble(valor));
            }else{
                lancamentoAux.setValor(0.0);
            }

            if(request.getParameter("tipo") != null && request.getParameter("tipo").equals("1")){
                lancamentoAux.setTipo(Lancamento.TIPO_RECEITA);
            }else{
                lancamentoAux.setTipo(Lancamento.TIPO_DESPESA);
            }

            if(request.getParameter("pagoRecebido") != null && request.getParameter("pagoRecebido").equals("true")){
                lancamentoAux.setPagoRecebido(true);
            }else{
                lancamentoAux.setPagoRecebido(false);
            }

            if(request.getParameter("id_conta") != null && !request.getParameter("id_conta").equals(""))
                lancamentoAux.setConta(new ContaDAO().findById(Conta.class, Integer.parseInt(request.getParameter("id_conta"))));

            if(request.getParameter("id_categoria") != null && !request.getParameter("id_categoria").equals(""))
                lancamentoAux.setCategoria(new CategoriaDAO().findById(Categoria.class, Integer.parseInt(request.getParameter("id_categoria"))));

            if(request.getParameter("id_membro") != null && !request.getParameter("id_membro").equals(""))
                lancamentoAux.setMembro(new MembroDAO().findById(Membro.class, Integer.parseInt(request.getParameter("id_membro"))));
            
            return lancamentoAux;
        }catch(Exception e){
        }
        
        return null;
    }
    
    @Override
    public void remover(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isOk = new LancamentoDAO().remove(Lancamento.class, id);

        if(isOk){
            mensagens.add(new Mensagem("Sucess ao tentar excluir a lancamento", Mensagem.TIPO_SUCESSO));
        }else{
            mensagens.add(new Mensagem("Ops! Erro ao tentar excluir lancamento, "
                    + "verifique se esta lancamento está sendo usada por um lançamento", Mensagem.TIPO_SUCESSO));            
        }
        
        listar(request, response, mensagens);
    }
}
