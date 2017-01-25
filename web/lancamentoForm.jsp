<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="comum/css.jsp" %>
    <link href="lib/css/bootstrap-toggle.min.css" rel="stylesheet">
    <link href="lib/css/select2.min.css" rel="stylesheet">
</head>
<body>
    
    <%@include file="comum/menu.jsp" %>
    
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-12">
                        <c:if test="${objLancamento.id != 0}">
                            <h4 class="title">Alteração de Lançamento</h4>
                        </c:if>
                        <c:if test="${objLancamento.id == 0}">
                            <h4 class="title">Inclusão de Lançamento</h4>
                        </c:if>
                        
                        <ol class="breadcrumb">
                            <li><a href="servletmain?business=DashboardAction&action=listar">Dashboard</a></li>
                            <li class="active">Inclusão de Lançamento</li>
                        </ol>
                        
                        <form class="form-horizontal" action="servletmain" method="post">
                            <input type="hidden" name="business" value="DashboardAction"/>
                            <input type="hidden" name="action" value="salvar"/>
                            <input type="hidden" name="id" value="${objLancamento.id}"/>
                            
                            <div class="form-group">
                                <label class="control-label col-sm-2">Tipo:</label>
                                <div class="col-sm-2"> 
                                    <input id="toggle-one" name="tipo" type="checkbox" value="1" <c:if test="${objLancamento.tipo == 1}"> checked="checked"</c:if>>
                                </div>
                            
                                <label class="control-label col-sm-2">Pago ou Recebido:</label>
                                <div class="col-sm-2"> 
                                    <input id="toggle-two" name="pagoRecebido" type="checkbox" value="true" <c:if test="${objLancamento.pagoRecebido == true}"> checked="checked" </c:if>>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-sm-2">Descrição:</label>
                                <div class="col-sm-10"> 
                                    <input type="text" name="descricao" class="form-control" value="${objLancamento.descricao}" placeholder="Ex: Despesa com Limpeza, Conta de Água, Salário etc.">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-sm-2">Valor:</label>
                                <div class="col-sm-10"> 
                                    <input type="text" name="valor" class="form-control dinheiro" value="<fmt:formatNumber value="${objLancamento.valor}" type="currency"/>" placeholder="Ex: 5.000,00">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-sm-2">Conta: </label>
                                <div class="col-sm-10">
                                    <select name="id_conta" class="form-control basic-single-categoria">
                                        <c:forEach items="${contas}" var="item">
                                            <option value="${item.id}" <c:if test="${item.id == objLancamento.conta.id}"> selected="selected" </c:if> >
                                                ${item.nome}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-sm-2">Data de Lançamento:</label>
                                <div class="col-sm-10"> 
                                    <input type="text" name="dataLancada" class="form-control dataLancamento" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${objLancamento.dataLancada}"/>" placeholder="Ex: 15/02/2017">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-sm-2">Categoria:</label>
                                <div class="col-sm-10"> 
                                    <select name="id_categoria" class="form-control basic-single-categoria">
                                        <option></option>
                                        <c:forEach items="${categorias}" var="item">
                                            <option value="${item.id}" <c:if test="${objLancamento.categoria != null && item.id == objLancamento.categoria.id}"> selected="selected" </c:if> >
                                                ${item.descricao}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-sm-2">Membro:</label>
                                <div class="col-sm-10"> 
                                    <select name="id_membro" class="form-control basic-single-membro">
                                        <option></option>
                                        <c:forEach items="${membros}" var="item">
                                            <option value="${item.id}" <c:if test="${objLancamento.membro != null && item.id == objLancamento.membro.id}"> selected="selected" </c:if> >
                                                ${item.nome}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="form-group"> 
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-success">Salvar</button>
                                    <a href="servletmain?business=DashboardAction&action=listar" class="btn btn-default">Voltar</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
        <%@include file="comum/footer.jsp" %>
    </div>
    
    <%@include file="comum/js.jsp" %>
    <script src="lib/js/bootstrap-toggle.min.js"></script>
    <script src="lib/js/select2.min.js"></script>
    <script>
        $(document).ready(function(){
            $('.dataLancamento').mask('00/00/0000');
            
            $('.dinheiro').mask('000.000.000.000.000,00', {reverse: true});
            
            $('#toggle-one').bootstrapToggle({
                on: 'Receita',
                off: 'Despesa',
                onstyle: 'success',
                offstyle: 'danger'
            });
            
            $('#toggle-two').bootstrapToggle({
                on: 'Sim',
                off: 'Não',
                onstyle: 'success',
                offstyle: 'danger'
            });
            
             $(".basic-single-categoria").select2();
             $(".basic-single-membro").select2();
        });
    </script>
</body>
</html>