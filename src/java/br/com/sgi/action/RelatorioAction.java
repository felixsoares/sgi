package br.com.sgi.action;

import br.com.sgi.controller.BusinessLogic;
import br.com.sgi.model.Mensagem;
import br.com.sgi.persistence.CategoriaDAO;
import br.com.sgi.persistence.ContaDAO;
import br.com.sgi.persistence.LancamentoDAO;
import br.com.sgi.persistence.MembroDAO;
import br.com.sgi.util.SGIUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RelatorioAction implements BusinessLogic{

    private List<Mensagem> mensagens;
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        String acao = request.getParameter("action");
        mensagens = new ArrayList<>();
        
        switch(acao.toLowerCase()){
            case "relatorios":
                relatorio(request, response);
                break;
            case "membros":
                relatorioMembros(request, response);
                break;
            case "dizimistas":
                relatorioLancamentoMensal(request, response);
                break;
            case "categorias":
                relatorioCategorias(request, response);
                break;
            case "lancamentos":
                relatorioLancamentos(request, response);
                break;    
            case "saldo":
                relatorioSaldos(request, response);
                break;             
        }
    }
    
    private void relatorioLancamentoMensal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            Date primeiroDia = null, ultimoDia = null;

            if(request.getParameter("primeiroDia") != null && !request.getParameter("primeiroDia").trim().equals("")){
                primeiroDia = SGIUtil.formataData(request.getParameter("primeiroDia"));
            }

            if(request.getParameter("ultimoDia") != null && !request.getParameter("ultimoDia").trim().equals("")){
                ultimoDia = SGIUtil.formataData(request.getParameter("ultimoDia"));
            }
            request.setAttribute("primeiroDia", primeiroDia);
            request.setAttribute("ultimoDia", ultimoDia);
            
            String nomeMes1 = SGIUtil.getNomeMes(primeiroDia);
            String nomeMes2 = SGIUtil.getNomeMes(ultimoDia);
            
            String mes = "";
            if(nomeMes1.equals(nomeMes2)){
                mes = " - " + nomeMes1;
            }else{
                mes = " - ("+nomeMes1+" a " +nomeMes2+")";
            }
        
            request.setAttribute("nomeRelatorio", "Relatório de dízimistas por mês" + mes);
            request.setAttribute("relatorioMembros", new MembroDAO().getRelatorioLancamentoMensal(SGIUtil.getPrimeiraHoraDia(primeiroDia), SGIUtil.getUltimaHoraDia(ultimoDia)));
            request.getRequestDispatcher("relatoriosTabela.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    
    private void relatorioLancamentos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            Date primeiroDia = null, ultimoDia = null;

            if(request.getParameter("primeiroDia") != null && !request.getParameter("primeiroDia").trim().equals("")){
                primeiroDia = SGIUtil.formataData(request.getParameter("primeiroDia"));
            }

            if(request.getParameter("ultimoDia") != null && !request.getParameter("ultimoDia").trim().equals("")){
                ultimoDia = SGIUtil.formataData(request.getParameter("ultimoDia"));
            }
            request.setAttribute("primeiroDia", primeiroDia);
            request.setAttribute("ultimoDia", ultimoDia);
        
            request.setAttribute("nomeRelatorio", "Relatório Despesa x Receita");
            request.setAttribute("relatioLancamento", new LancamentoDAO().getRelatorioLancamento(SGIUtil.getPrimeiraHoraDia(primeiroDia), SGIUtil.getUltimaHoraDia(ultimoDia)));
            request.getRequestDispatcher("relatoriosTabela.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void relatorioMembros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("id_membro"));
        request.setAttribute("relatorioMembros", new MembroDAO().getRelatorioMembro(id));
        request.setAttribute("nomeRelatorio", "Relatório de dízimos");
        request.getRequestDispatcher("relatoriosTabela.jsp").forward(request, response);
    }
    
    private void relatorioCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            
            Date primeiroDia = null, ultimoDia = null;

            if(request.getParameter("primeiroDia") != null && !request.getParameter("primeiroDia").trim().equals("")){
                primeiroDia = SGIUtil.formataData(request.getParameter("primeiroDia"));
            }

            if(request.getParameter("ultimoDia") != null && !request.getParameter("ultimoDia").trim().equals("")){
                ultimoDia = SGIUtil.formataData(request.getParameter("ultimoDia"));
            }
        
            request.setAttribute("nomeRelatorio", "Relatório dos gastos totais com cada categoria");
            request.setAttribute("relatorioCategorias", new CategoriaDAO().getRelatorioCategoria(SGIUtil.getPrimeiraHoraDia(primeiroDia), SGIUtil.getUltimaHoraDia(ultimoDia)));
            request.getRequestDispatcher("relatoriosTabela.jsp").forward(request, response);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void relatorio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        getNomePagina(request);
        
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        Date primeiroDia = aCalendar.getTime();
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date ultimoDia = aCalendar.getTime();
        
        request.setAttribute("primeiroDia", primeiroDia);
        request.setAttribute("ultimoDia", ultimoDia);
        
        request.setAttribute("membros", new MembroDAO().findAll("Membro", "nome"));
        request.getRequestDispatcher("relatorios.jsp").forward(request, response);
    }

    private void relatorioSaldos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            request.setAttribute("nomeRelatorio", "Relatório de saldo");
            request.setAttribute("relatioSaldo", new ContaDAO().getRelatorioSaldo());
            request.getRequestDispatcher("relatoriosTabela.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void getNomePagina(HttpServletRequest request) {
        request.setAttribute("nomePagina", "relatorio");       
    }
}
