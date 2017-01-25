<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <c:if test="${objMembro.id != 0}">
                            <h4 class="title">Alteração de Membro</h4>
                        </c:if>
                        <c:if test="${objMembro.id == 0}">
                            <h4 class="title">Inclusão de Membro</h4>
                        </c:if>
                        
                        <ol class="breadcrumb">
                            <li><a href="servletmain?business=MembroAction&action=listar">Membros</a></li>
                            <li class="active">Inclusão de Membro</li>
                        </ol>
                        
                        <form class="form-horizontal" action="servletmain" method="post">
                            <input type="hidden" name="business" value="MembroAction"/>
                            <input type="hidden" name="action" value="salvar"/>
                            <input type="hidden" name="id" value="${objMembro.id}"/>
                            
                            <div class="form-group">
                                <label class="control-label col-sm-2">Nome:</label>
                                <div class="col-sm-10"> 
                                    <input type="text" name="nome" class="form-control" value="${objMembro.nome}" placeholder="Ex: Rosana, Amado(Mara), Irmão Antonio">
                                </div>
                            </div>

                            <div class="form-group"> 
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-success">Salvar</button>
                                    <a href="servletmain?business=MembroAction&action=listar" class="btn btn-default">Cancelar</a>
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
</body>
</html>