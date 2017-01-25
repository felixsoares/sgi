<script src="lib/js/jquery.js"></script>
<script src="lib/js/bootstrap.min.js"></script>
<script src="lib/js/notify.js"></script>
<script src="lib/js/mask.plugin.js"></script>

<script>
    <c:if test="${mensagens != null}">
        <c:forEach items="${mensagens}" var="item">
            $.notify("${item.mensagem}", "${item.tipo}");
        </c:forEach>
    </c:if>
</script>