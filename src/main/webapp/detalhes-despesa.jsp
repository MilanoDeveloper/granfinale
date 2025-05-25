<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.fiap.granfinale.model.*" %>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <title>Detalhes da Despesa</title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-body-secondary px-0">
<div class="container py-5">

    <%
        Despesa d = (Despesa) request.getAttribute("despesa");
    %>

    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
            <h5>Detalhes da Despesa</h5>
        </div>
        <div class="card-body">
            <p><strong>Descrição:</strong> <%= d.getDescricao() %>
            </p>
            <p><strong>Valor:</strong> R$ <%= String.format("%.2f", d.getValor()) %>
            </p>
            <p><strong>Data:</strong> <%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(d.getData()) %>
            </p>
            <p><strong>Grupo:</strong> <%= d.getGrupo().getNome() %>
            </p>
            <p><strong>Pago por:</strong> <%= d.getPagador().getNome() %>
            </p>
            <p><strong>Divisão:</strong></p>
            <ul>
                <%
                    for (DespesaDivisao div : d.getDivisoes()) {
                %>
                <li><%= div.getParticipante().getNome() %> - R$ <%= String.format("%.2f", div.getValor()) %>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>

    <div class="mt-3">
        <a href="despesas" class="btn btn-secondary">Voltar</a>
    </div>

</div>
</body>
</html>
