<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        <c:if test="${objConta.id != 0}">
                            <h4 class="title">Alteração de Conta</h4>
                        </c:if>
                        <c:if test="${objConta.id == 0}">
                            <h4 class="title">Inclusão de Conta</h4>
                        </c:if>
                        
                        <ol class="breadcrumb">
                            <li><a href="servletmain?business=ContaAction&action=listar">Contas</a></li>
                            <li class="active">Inclusão de Conta</li>
                        </ol>
                        
                        <form class="form-horizontal" action="servletmain" method="post">
                            <input type="hidden" name="business" value="ContaAction"/>
                            <input type="hidden" name="action" value="salvar"/>
                            <input type="hidden" name="id" value="${objConta.id}"/>

                            <div class="form-group">
                                <label class="control-label col-sm-2">Descrição:</label>
                                <div class="col-sm-10"> 
                                    <input type="text" name="nome" class="form-control" value="${objConta.nome}" placeholder="Ex: Conta Bancária, Em caixa, Conta Corrente">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2">Valor inicial de saldo:</label>
                                <div class="col-sm-10"> 
                                    <input type="text" name="saldo" class="form-control dinheiro" value="<fmt:formatNumber value="${objConta.saldo}" type="currency"/>" placeholder="Ex: 5.000,00">
                                </div>
                            </div>

                            <div class="form-group"> 
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-success">Salvar</button>
                                    <a href="servletmain?business=ContaAction&action=listar" class="btn btn-default">Voltar</a>
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
    <script>
        $('.dinheiro').mask('000.000.000.000.000,00', {reverse: true});
    </script>
</body>
</html>