
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.fiap.granfinale.model.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="header.jsp" />
<!DOCTYPE html>
<html>
<head>
  <title>Despesas</title>
  <link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-body-secondary px-0">

<div class="container py-5">
  <%
    List<Despesa> despesas = (List<Despesa>) request.getAttribute("despesas");
  %>

  <div class="d-flex justify-content-between align-items-center mb-4">
    <h3 class="text-primary">Despesas</h3>
    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#novaDespesaModal">Adicionar Despesa</button>
  </div>

  <div class="row">
    <%
      if (despesas != null && !despesas.isEmpty()) {
        for (Despesa d : despesas) {
    %>
    <div class="col-md-12 mb-3">
      <div class="card shadow-sm">
        <div class="card-body d-flex justify-content-between align-items-center">
          <div>
            <h5><%= d.getDescricao() %></h5>
            <p class="mb-1">R$ <%= String.format("%.2f", d.getValor()) %> - <%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(d.getData()) %></p>
            <p class="mb-0">Grupo: <%= d.getGrupo().getNome() %> | Pago por: <%= d.getPagador().getNome() %></p>
          </div>
          <div class="text-end">
            <a href="detalhes-despesa?id=<%= d.getId() %>" class="btn btn-link">Ver Detalhes</a>
            <form action="excluir-despesa" method="post" class="d-inline">
              <input type="hidden" name="id" value="<%= d.getId() %>">
              <button type="submit" class="btn btn-link text-danger">&#128465;</button>
            </form>
          </div>
        </div>
      </div>
    </div>
    <%
      }
    } else {
    %>
    <p class="text-muted">Nenhuma despesa cadastrada.</p>
    <%
      }
    %>
  </div>

  <!-- Modal Nova Despesa -->
  <%
    br.com.fiap.granfinale.dao.GrupoDAO grupoDAO = new br.com.fiap.granfinale.dao.GrupoDAO();
    List<Grupo> grupos = grupoDAO.listarTodos();

    br.com.fiap.granfinale.dao.ParticipanteDAO participanteDAO = new br.com.fiap.granfinale.dao.ParticipanteDAO();
    List<Participante> participantes = participanteDAO.listarTodos();
  %>
  <div class="modal fade" id="novaDespesaModal" tabindex="-1" aria-labelledby="novaDespesaModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <form class="modal-content" action="cadastrar-despesa" method="post">
        <div class="modal-header">
          <h5 class="modal-title">Nova Despesa</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label class="form-label">Descrição</label>
            <input type="text" class="form-control" name="descricao" required>
          </div>
          <div class="mb-3">
            <label class="form-label">Valor Total</label>
            <input type="number" step="0.01" class="form-control" name="valor" required>
          </div>
          <div class="mb-3">
            <label class="form-label">Data</label>
            <input type="date" class="form-control" name="data" required>
          </div>
          <div class="mb-3">
            <label class="form-label">Grupo</label>
            <select class="form-select" name="grupo" required>
              <%
                for (Grupo g : grupos) {
              %>
              <option value="<%= g.getId() %>"><%= g.getNome() %></option>
              <%
                }
              %>
            </select>
          </div>
          <div class="mb-3">
            <label class="form-label">Pago por</label>
            <select class="form-select" name="pagador" required>
              <%
                for (Participante p : participantes) {
              %>
              <option value="<%= p.getId() %>"><%= p.getNome() %> (<%= p.getEmail() %>)</option>
              <%
                }
              %>
            </select>
          </div>
          <div class="mb-3">
            <label class="form-label">Divisão do valor</label>
            <div class="row">
              <%
                for (Participante p : participantes) {
              %>
              <div class="col-md-6 mb-2">
                <div class="input-group">
                  <span class="input-group-text"><%= p.getNome() %></span>
                  <input type="hidden" name="participanteId" value="<%= p.getId() %>">
                  <input type="number" step="0.01" class="form-control" name="valorPorParticipante" placeholder="Valor">
                </div>
              </div>
              <%
                }
              %>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-success">Salvar</button>
        </div>
      </form>
    </div>
  </div>

</div>
</body>
</html>
