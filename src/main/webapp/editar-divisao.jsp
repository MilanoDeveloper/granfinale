<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.fiap.granfinale.dao.*, br.com.fiap.granfinale.model.*" %>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Valor da Divisão</title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-body-secondary px-0">

<%
    int despesaId = Integer.parseInt(request.getParameter("despesaId"));
    int participanteId = Integer.parseInt(request.getParameter("participanteId"));

    DespesaDAO dao = new DespesaDAO();
    Despesa despesa = dao.listar().stream()
            .filter(d -> d.getId() == despesaId)
            .findFirst().orElse(null);

    DespesaDivisao divisao = despesa.getDivisoes().stream()
            .filter(dv -> dv.getParticipante().getId() == participanteId)
            .findFirst().orElse(null);
%>

<div class="card">
    <div class="card-header bg-secondary text-white">
        <h5>Editar Valor de <%= divisao.getParticipante().getNome() %> na despesa "<%= despesa.getDescricao() %>"</h5>
    </div>
    <div class="card-body">
        <form action="editar-divisao" method="post">
            <input type="hidden" name="despesaId" value="<%= despesaId %>">
            <input type="hidden" name="participanteId" value="<%= participanteId %>">
            <div class="mb-3">
                <label class="form-label">Novo valor</label>
                <input type="number" name="novoValor" class="form-control" step="0.01" value="<%= divisao.getValor() %>"
                       required>
            </div>
            <button type="submit" class="btn btn-success">Salvar</button>
            <a href="saldos" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
</div>

</body>
</html>
