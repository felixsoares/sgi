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
                        <h2>Categorias</h2>
                        
                        <ol class="breadcrumb">
                            <li><a href="servletmain?business=CategoriaAction&action=listar">Categorias</a></li>
                        </ol>
                        
                        <div class="well">
                            <label>Filtros</label>
                            <div class="row">
                                <form action="servletmain" method="post">
                                    <input type="hidden" name="business" value="CategoriaAction"/>
                                    <input type="hidden" name="action" value="pesquisar"/>

                                    <div class="col-md-9">
                                        <label>
                                            Descrição
                                        </label>
                                        <input type="text" name="descricao" class="form-control" placeholder="Pesquise por descrição">
                                    </div>

                                    <div class="col-md-3">
                                        <div style="margin-top: 24px">
                                            <input type="submit" class="btn btn-default" value="Filtrar">
                                            <a href="servletmain?business=CategoriaAction&action=inserir" class="btn btn-info">Adicionar Categoria</a>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="table-responsive">
                            <table class="table table-hover table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th><a>Descrição</a></th>
                                        <th class="acoes"><a>Ações</a></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaCategorias}" var="item">
                                        <tr>
                                            <td>${item.descricao}</td>
                                            <td class="center">
                                                <a href="servletmain?business=CategoriaAction&action=alterar&id=${item.id}" class="btn btn-info"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
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
                                                            Esta operação não pode ser desfeita, deseja mesmo remover a categoria <b> ${item.descricao} </b>?
                                                        </div>
                                                        <div class="modal-footer">
                                                            <a href="servletmain?business=CategoriaAction&action=remover&id=${item.id}" class="btn btn-success">Remover</a>
                                                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </tr>
                                    </c:forEach>                            
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <%@include file="comum/footer.jsp" %>
    </div>
    
    <%@include file="comum/js.jsp" %>
</body>
</html>