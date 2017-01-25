<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="comum/css.jsp" %>
    <link href="lib/css/select2.min.css" rel="stylesheet">
</head>
<body>
    
    <%@include file="comum/menu.jsp" %>
    
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-12">
                        <h2>Relatórios</h2>
                        
                        <ol class="breadcrumb">
                            <li><a href="servletmain?business=RelatorioAction&action=relatorios">Relatórios</a></li>
                        </ol>
                        
                        <div>
                            <div class="well">
                                <label>Relatório de dízimos por membro</label>
                                <div class="row">
                                    <form action="servletmain" method="post">
                                        <input type="hidden" name="business" value="RelatorioAction"/>
                                        <input type="hidden" name="action" value="membros"/>

                                        <div class="col-md-9">
                                            <label>
                                                Membro
                                            </label>
                                            <select name="id_membro" class="form-control basic-single-membro" required="required">
                                                <option></option>
                                                <c:forEach items="${membros}" var="item">
                                                    <option value="${item.id}">
                                                        ${item.nome}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="col-md-3">
                                            <div style="margin-top: 24px">
                                                <input type="submit" class="btn btn-default" value="Filtrar">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            
                            <c:if test="${relatorioMembros != null}">
                                <table class="table table-hover table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th><a>Nome</a></th>
                                            <th><a>Data</a></th>
                                            <th><a>Valor</a></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${relatorioMembros}" var="item">
                                            <tr class="dizimos">
                                                <td>${item.nome}</td>
                                                <td><span class="data">${item.data}</span></td>
                                                <td style="text-align: right;"><fmt:formatNumber value="${item.valor}" type="currency"/></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                        </div>
                        
                        <hr>
                        
                        <div>
                            <div class="well">
                                <label>Relatório destinado aos gastos totais com cada categoria</label>
                                <div class="row">
                                    <form action="servletmain" method="post">
                                        <input type="hidden" name="business" value="RelatorioAction"/>
                                        <input type="hidden" name="action" value="categorias"/>

                                        <div class="col-md-4">
                                            <label>
                                                Data Inicio
                                            </label>
                                            <input type="text" name="primeiroDia" class="form-control dataFormat" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${primeiroDia}"/>" required="required">
                                        </div>

                                        <div class="col-md-4">
                                            <label>
                                                Data Final
                                            </label>
                                            <input type="text" name="ultimoDia" class="form-control dataFormat" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${ultimoDia}"/>" required="required">
                                        </div>

                                        <div class="col-md-3">
                                            <div style="margin-top: 24px">
                                                <input type="submit" class="btn btn-default" value="Filtrar">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                                        
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
                        
                        <hr>
                        
                        <div>
                            <div class="well">
                                <label>Gráfico Despesa x Receita</label>
                                <div class="row">
                                    <form action="servletmain" method="post">
                                        <input type="hidden" name="business" value="RelatorioAction"/>
                                        <input type="hidden" name="action" value="lancamentos"/>

                                        <div class="col-md-4">
                                            <label>
                                                Data Inicio
                                            </label>
                                            <input type="text" name="primeiroDia" class="form-control dataFormat" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${primeiroDia}"/>">
                                        </div>

                                        <div class="col-md-4">
                                            <label>
                                                Data Final
                                            </label>
                                            <input type="text" name="ultimoDia" class="form-control dataFormat" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${ultimoDia}"/>">
                                        </div>

                                        <div class="col-md-4">
                                            <div style="margin-top: 24px">
                                                <input type="submit" class="btn btn-default" value="Filtrar">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <c:if test="${relatioLancamento != null}">
                                <canvas id="myChart" width="400" height="400"></canvas>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <%@include file="comum/footer.jsp" %>
    </div>
    
    <%@include file="comum/js.jsp" %>
    <script src="lib/js/select2.min.js"></script>
    <script src="lib/js/Chart.min.js"></script>
    <script>
        $(document).ready(function(){
            $('.dataFormat').mask('00/00/0000');
            $(".basic-single-categoria").select2();
            $(".basic-single-membro").select2();
            
            $('.dizimos').each(function(){
               var data = $(this).find('.data').text();
               var mes = data.slice(3, 8);
               var dia = data.slice(7, 10);
               var ano = data.slice(24, data.length);
               
               $(this).find('.data').text(dia + ' /' + mes + '/' + ano);
            });
            
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
        });
    </script>
</body>
</html>