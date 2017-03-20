<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="comum/css.jsp" %>
</head>
<body>
    
    <%@include file="comum/menu.jsp" %>
    
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-12">
                        <h2>Dashboard</h2>
                        
                        <ol class="breadcrumb">
                            <li><a href="servletmain?business=DashboardAction&action=listar">Dashboard</a></li>
                        </ol>
                        
                        <div class="well">
                            <label>Filtros</label>
                            <div class="row">
                                <form action="servletmain" method="post">
                                    <input type="hidden" name="business" value="DashboardAction"/>
                                    <input type="hidden" name="action" value="pesquisar"/>
                                    
                                    <div class="col-md-2">
                                        <label>
                                            Data Inicio
                                        </label>
                                        <input type="text" name="primeiroDia" class="form-control dataFormat" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${primeiroDia}"/>">
                                    </div>

                                    <div class="col-md-2">
                                        <label>
                                            Data Final
                                        </label>
                                        <input type="text" name="ultimoDia" class="form-control dataFormat" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${ultimoDia}"/>">
                                    </div>

                                    <div class="col-md-3">
                                        <label>
                                            Descrição
                                        </label>
                                        <input type="text" name="descricao" class="form-control">
                                    </div>
                                    
                                    <div class="col-md-2">
                                        <label>
                                            Pago / Recebido
                                        </label>
                                        <select class="form-control" name="pagoRecebido">
                                            <option></option>
                                            <option value="true">Sim</option>
                                            <option value="false">Não</option>
                                        </select>
                                    </div>

                                    <div class="col-md-3">
                                        <div style="margin-top: 24px">
                                            <input type="submit" class="btn btn-default" value="Filtrar">
                                            <a href="servletmain?business=DashboardAction&action=inserir" class="btn btn-info">Adicionar Lançamento</a>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="table-responsive">
                            <table class="table table-hover table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th><a>Número</a></th>
                                        <th><a>Descrição</a></th>
                                        <th><a>Tipo</a></th>
                                        <th><a>Data de Lançamento</a></th>
                                        <th><a>Pago / Recebido</a></th>
                                        <th><a>Valor</a></th>
                                        <th class="acoes"><a>Ações</a></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaLancamentos}" var="item">
                                        <tr class="lancamentos">
                                            <td>${item.id}</td>
                                            <td>${item.descricao}</td>
                                            <td>
                                                <c:if test="${item.tipo == 0}">
                                                    Despesa
                                                </c:if>
                                                <c:if test="${item.tipo == 1}">
                                                    Receita
                                                </c:if>
                                            </td>
                                            <td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.dataLancada}"/></td>
                                            <td>
                                                <c:if test="${item.pagoRecebido == true}">
                                                    Sim
                                                </c:if>
                                                <c:if test="${item.pagoRecebido == false}">
                                                    Não
                                                </c:if>
                                            </td>
                                            <td class="tipo${item.tipo}">
                                                <b><label class="valor"><fmt:formatNumber value="${item.valor}" type="currency"/></label></b>
                                            </td>
                                            <td class="center">
                                                <a href="servletmain?business=DashboardAction&action=alterar&id=${item.id}" class="btn btn-info"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                                                <a data-toggle="modal" data-target="#myModal${item.id}" class="btn btn-danger"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                                            </td>
                                            
                                            <div id="myModal${item.id}" class="modal fade" role="dialog">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                            <h4 class="modal-title">Remover</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            Esta operação não pode ser desfeita, deseja mesmo remover o lançamento <b> ${item.descricao} </b>?
                                                        </div>
                                                        <div class="modal-footer">
                                                            <a href="servletmain?business=DashboardAction&action=remover&id=${item.id}" class="btn btn-success">Remover</a>
                                                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </tr>
                                    </c:forEach> 
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td style="text-align: right" colspan="5">Total Receita </td>
                                        <td class="tipo1" style="text-align: right"colspan="2"><label id="totalReceitas"/></td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: right" colspan="5">Total Despesas </td>
                                        <td class="tipo0" style="text-align: right" colspan="2"><label id="totalDespesas"/></td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: right" colspan="5">Total </td>
                                        <td style="text-align: right" colspan="2"><label id="valorTotal"/></td>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <%@include file="comum/footer.jsp" %>
    </div>
    
    <%@include file="comum/js.jsp" %>
    
    <script>
        $('.dataFormat').mask('00/00/0000');
        
        $(document).ready(function(){
           calculaValorDespesas();
           calculaValorReceitas();
           calculaValorTotal();
        });
        
        function calculaValorDespesas(){
            var valor = 0.0;
            
            $('.table .lancamentos .tipo0').each(function(){
               var aux = $(this).find('.valor').text();
               valor += replaceValor(aux);
            });
            
            $('#totalDespesas').text(colocaZeros(valor));
            $('#totalDespesas').mask('000.000.000.000.000,00', {reverse: true});
        }
        
        function calculaValorReceitas(){
            var valor = 0.0;
            
            $('.table .lancamentos .tipo1').each(function(){
               var aux = $(this).find('.valor').text();
               valor += replaceValor(aux);
            });
            
            $('#totalReceitas').text(colocaZeros(valor));
            $('#totalReceitas').mask('000.000.000.000.000,00', {reverse: true});
        }
        
        function calculaValorTotal(){
            var valorTotal = 0.0;
            var receitas = replaceValor($('#totalReceitas').text());
            var despesas = replaceValor($('#totalDespesas').text());
            
            valorTotal = receitas - despesas;
            valorTotal = colocaZeros(valorTotal);
            
            $('#valorTotal').text(valorTotal);
            $('#valorTotal').mask('000.000.000.000.000,00', {reverse: true});
            
            var ponto = $('#valorTotal').text().slice(0, 1);
            if(ponto === '.'){
                var valorSemPonto = $('#valorTotal').text();
                valorSemPonto = valorSemPonto.slice(1, valorSemPonto.length);
                $('#valorTotal').text(valorSemPonto);
            }
            
            if(despesas > receitas){
                var valorNegativo = $('#valorTotal').text();
                $('#valorTotal').text("- " + valorNegativo);
            }
        }
        
        function replaceValor(valor){
            valor = valor.replace("R$ ","");
            valor = valor.replace(".","");
            valor = valor.replace(",",".");
            
            valor = parseFloat(valor);
            
            return valor;
        }
        
        function colocaZeros(valor){
            valor = valor+"";
            if(valor.indexOf(".")<0){
                valor = valor + ".00";
            }else if(valor.substring(valor.indexOf(".")).length==2){
                valor = valor + "0";
            }else if(valor.substring(valor.indexOf(".")).length>3){
                valor = valor.substring(0, valor.indexOf(".")+3);
            }

            return valor;
        }
    </script>
</body>
</html>
