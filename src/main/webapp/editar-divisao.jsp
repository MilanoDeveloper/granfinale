
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.fiap.granfinale.model.*" %>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Valor da Divisão</title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-body-secondary px-0">

<%
    Despesa despesa = (Despesa) request.getAttribute("despesa");
    DespesaDivisao divisao = (DespesaDivisao) request.getAttribute("divisao");
    int despesaId = request.getAttribute("despesaId") != null
            ? (Integer) request.getAttribute("despesaId")
            : Integer.parseInt(request.getParameter("despesaId"));
    int participanteId = request.getAttribute("participanteId") != null
            ? (Integer) request.getAttribute("participanteId")
            : Integer.parseInt(request.getParameter("participanteId"));
%>

<% if (divisao != null && despesa != null) { %>
<div class="card m-4">
    <div class="card-header bg-secondary text-white">
        <h5>Editar Valor de <%= divisao.getParticipante().getNome() %> na despesa "<%= despesa.getDescricao() %>"</h5>
    </div>
    <div class="card-body">
        <form action="editar-divisao" method="post">
            <input type="hidden" name="despesaId" value="<%= despesaId %>">
            <input type="hidden" name="participanteId" value="<%= participanteId %>">
            <div class="mb-3">
                <label class="form-label">Novo valor</label>
                <input type="number" name="novoValor" class="form-control" step="0.01" value="<%= divisao.getValor() %>" required>
            </div>
            <button type="submit" class="btn btn-success">Salvar</button>
            <a href="saldos" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
</div>
<% } else { %>
<div class="alert alert-danger m-4">
    <strong>Erro:</strong> Não foi possível carregar os dados da divisão. Verifique se os parâmetros são válidos ou tente novamente.
</div>
<% } %>

</body>
</html>
