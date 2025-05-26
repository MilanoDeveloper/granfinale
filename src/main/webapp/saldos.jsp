<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="br.com.fiap.granfinale.model.*" %>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <title>Saldos e Pagamentos</title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-body-secondary px-0">
<div class="container py-5">

    <%
        Map<Participante, List<DespesaDivisao>> resumos = (Map<Participante, List<DespesaDivisao>>) request.getAttribute("resumos");
        List<Despesa> despesas = (List<Despesa>) request.getAttribute("despesas");
        int pendentes = (int) request.getAttribute("pendentes");
        int pagos = (int) request.getAttribute("pagos");
        int cancelados = (int) request.getAttribute("cancelados");
    %>

    <h3 class="text-primary mb-4">Saldos e Pagamentos</h3>

    <div class="row mb-4">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">Ressonhos por Pagador</div>
                <div class="card-body">
                    <%
                        for (Map.Entry<Participante, List<DespesaDivisao>> entry : resumos.entrySet()) {
                            Participante pagador = entry.getKey();
                            List<DespesaDivisao> divs = entry.getValue();
                            double total = 0.0;
                    %>
                    <h6 class="text-primary"><%= pagador.getNome() %>
                    </h6>
                    <ul class="list-unstyled">
                        <%
                            for (Despesa d : despesas) {
                                if (d.getPagador().getId() == pagador.getId()) {
                                    for (DespesaDivisao div : d.getDivisoes()) {
                                        if (div.getParticipante().getId() != pagador.getId()) {
                                            total += div.getValor();
                        %>
                        <li>
                            <%= div.getParticipante().getNome() %> deve pagar<br>
                            <small>Despesa: <%= d.getDescricao() %> |
                                Data: <%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(d.getData()) %>
                            </small>
                            <span class="text-success float-end">R$ <%= String.format("%.2f", div.getValor()) %></span>
                        </li>
                        <%
                                        }
                                    }
                                }
                            }
                        %>
                    </ul>
                    <div class="text-end fw-bold text-primary">Total: R$ <%= String.format("%.2f", total) %>
                    </div>
                    <hr/>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">Resumo de Pagamentos</div>
                <div class="card-body">
                    <p>Pendentes: <span class="text-warning fw-bold"><%= pendentes %></span></p>
                    <p>Pagos: <span class="text-success fw-bold"><%= pagos %></span></p>
                    <p>Cancelados: <span class="text-danger fw-bold"><%= cancelados %></span></p>
                </div>
            </div>
        </div>
    </div>

    <div class="table-responsive">
        <table class="table table-bordered table-hover">
            <thead class="table-light">
            <tr>
                <th>Despesa</th>
                <th>Grupo</th>
                <th>Valor</th>
                <th>Pago por</th>
                <th>Data</th>
                <th>Status</th>
                <th>Ações</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Despesa d : despesas) {
                    for (DespesaDivisao div : d.getDivisoes()) {
            %>
            <tr>
                <td><%= d.getDescricao() %>
                </td>
                <td><%= d.getGrupo().getNome() %>
                </td>
                <td>R$ <%= String.format("%.2f", div.getValor()) %>
                </td>
                <td><%= div.getParticipante().getNome() %>
                </td>
                <td><%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(d.getData()) %>
                </td>
                <td>
              <span class="badge
                <%= "PAGO".equals(div.getStatus()) ? "bg-success" :
                     "CANCELADO".equals(div.getStatus()) ? "bg-danger" : "bg-warning text-dark" %>">
                <%= div.getStatus() %>
              </span>
                </td>
                <td>
                    <form action="alterar-status" method="post" class="d-inline">
                        <input type="hidden" name="despesaId" value="<%= d.getId() %>">
                        <input type="hidden" name="participanteId" value="<%= div.getParticipante().getId() %>">
                        <input type="hidden" name="status" value="PAGO">
                        <button class="btn btn-success btn-sm">&#10003;</button>
                    </form>
                    <form action="alterar-status" method="post" class="d-inline">
                        <input type="hidden" name="despesaId" value="<%= d.getId() %>">
                        <input type="hidden" name="participanteId" value="<%= div.getParticipante().getId() %>">
                        <input type="hidden" name="status" value="CANCELADO">
                        <button class="btn btn-danger btn-sm">&#10060;</button>
                    </form>
                    <a href="editar-divisao-form?despesaId=<%= d.getId() %>&participanteId=<%= div.getParticipante().getId() %>" class="btn btn-secondary btn-sm">&#9998;</a>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
