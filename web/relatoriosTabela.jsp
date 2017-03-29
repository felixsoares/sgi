<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="comum/css.jsp" %>
    <link href="lib/css/print.css" rel="stylesheet">
</head>
<body>
    
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-12">
                        <h1>${nomeRelatorio}</h1>
                        
                        <div style="margin-bottom: 15px;" class="no-print">
                            <a class="btn btn-default" href="servletmain?business=RelatorioAction&action=relatorios">Voltar</a>
                        </div>
                        
                        <div>
                            <c:if test="${relatorioMembros != null}">
                                <table class="table table-hover table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th width="10%"><a>Numeração</th>
                                            <th><a>Nome</a></th>
                                            <th style="text-align: right"><a>Valor</a></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="numero" value="${0}"/>
                                        <c:set var="valorTotal" value="${0.0}"/>
                                        <c:forEach items="${relatorioMembros}" var="item">
                                            <c:set var="numero" value="${numero = numero + 1}"/>
                                            <tr class="dizimos">
                                                <td>${numero}</td>
                                                <td>${item.nome}</td>
                                                <c:set var="valorTotal" value="${valortotal = valortotal + item.valor}"/>
                                                <td style="text-align: right;"><span class="itemValor"><fmt:formatNumber value="${item.valor}" type="currency"/></span></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <td style="text-align: right; font-weight: bold;" colspan=2">Total</td>
                                            <td style="text-align: right; font-weight: bold;"><span class="itemValorTotal"><fmt:formatNumber value="${valorTotal}" type="currency"/></span></td>
                                        </tr>
                                        <c:if test="${tirarValor != null}">
                                            <tr>
                                                <td style="text-align: right; font-weight: bold;" colspan=2">Dizimo para convenção 10%</td>
                                                <td style="text-align: right; font-weight: bold;"><span class="itemValorDesconto"></span></td>
                                            </tr>
                                        </c:if>
                                    </tfoot>
                                </table>
                            </c:if>
                        </div>
                        
                        <div>
                            <c:if test="${relatorioCategorias != null}">
                                <table class="table table-hover table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th><a>Categoria</a></th>
                                            <th><a>Valor</a></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${relatorioCategorias}" var="item">
                                            <tr>
                                                <td>${item.nome}</td>
                                                <td style="text-align: right;"><fmt:formatNumber value="${item.valor}" type="currency"/></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                        </div>
                        
                        <div>
                            <c:if test="${relatioLancamento != null}">
                                <canvas id="myChart" width="400" height="400"></canvas>
                            </c:if>
                        </div>
                                        
                        <div>
                            <c:if test="${relatioSaldo != null}">
                                <table class="table table-hover table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th><a>Conta</a></th>
                                            <th><a>Saldo</a></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="totalGeral" value="${0.0}"/>
                                        <c:forEach items="${relatioSaldo}" var="item">
                                            <tr>
                                                <td>${item.conta.nome}</td>
                                                <td style="text-align: right;"><fmt:formatNumber value="${item.conta.saldo}" type="currency"/></td>
                                            </tr>
                                            <tr>
                                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Receita</td>
                                                <td style="text-align: right;"><fmt:formatNumber value="${item.lancamentoDTO.valorReceita}" type="currency"/></td>
                                            </tr>
                                            <tr>
                                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Despesa</td>
                                                <td style="text-align: right;"><fmt:formatNumber value="${item.lancamentoDTO.valorDespesa}" type="currency"/></td>
                                            </tr>
                                            <tr>
                                                <td><b>TOTAL</b></td>
                                                <td style="text-align: right;"><b><fmt:formatNumber value="${(item.conta.saldo + item.lancamentoDTO.valorReceita) - item.lancamentoDTO.valorDespesa}" type="currency"/></b></td>
                                            </tr>
                                            
                                            <c:set var="totalGeral" value="${totalGeral + ((item.conta.saldo + item.lancamentoDTO.valorReceita) - item.lancamentoDTO.valorDespesa)}" />
                                            
                                            <tr class="divisor">
                                                <td colspan="2"><hr></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <td><b>TOTAL GERAL</b></td>
                                            <td style="text-align: right;"><b><fmt:formatNumber value="${totalGeral}" type="currency"/></b></td>
                                        </tr>
                                    </tfoot>
                                </table>
                            </c:if>
                        </div>
                                        
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <%@include file="comum/js.jsp" %>
    <script src="lib/js/Chart.min.js"></script>
    <script>
        <c:if test="${relatorioMembros != null}">
            $('.dizimos').each(function(){
                var valor = $(this).find('.itemValor').text();
                valor = valor.replace("R$ ", "");
                $(this).find('.itemValor').text(valor);
            });
            
            var texto = $('.itemValorTotal').text();
            texto = texto.replace("R$ ", "");
            $('.itemValorTotal').text(texto);
        </c:if>
         <c:if test="${relatioLancamento != null}">
            var ctx = document.getElementById("myChart");
            var data = {
                labels: [
                    "Despesas",
                    "Receitas"
                ],
                datasets:
                [{
                    data: [${relatioLancamento.valorDespesa}, ${relatioLancamento.valorReceita}],
                    backgroundColor:
                    [
                        "#FF6384",
                        "#18cb57"
                    ],
                    hoverBackgroundColor:
                    [
                        "#e55976",
                        "#15b64e"
                    ]
                }]
            };
            var myPieChart = new Chart(ctx,{
                type: 'pie',
                data: data,
                options: {
                    responsive: false
                }
            });
         </c:if>
    </script>
</body>
</html>
