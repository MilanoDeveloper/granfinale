
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.fiap.granfinale.model.Participante" %>
<%@ page import="java.util.List" %>
<jsp:include page="header.jsp" />
<!DOCTYPE html>
<html>
<head>
  <title>Participantes</title>
  <link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-body-secondary px-0">
<div class="container py-5">

  <div class="d-flex justify-content-between align-items-center mb-4">
    <h3 class="text-primary">Participantes</h3>
    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#novoParticipanteModal">+ Novo Participante</button>
  </div>

  <div class="row">
    <%
      List<Participante> participantes = (List<Participante>) request.getAttribute("participantes");
      if (participantes != null && !participantes.isEmpty()) {
        for (Participante p : participantes) {
          String[] nomeDividido = p.getNome().split(" ");
          String iniciais = nomeDividido.length > 1 ?
                  (nomeDividido[0].charAt(0) + "" + nomeDividido[1].charAt(0)) :
                  nomeDividido[0].substring(0, 2);
    %>
    <div class="col-md-4 mb-4">
      <div class="card shadow-sm">
        <div class="card-body text-center">
          <div class="rounded-circle bg-primary text-white d-inline-block mb-3"
               style="width:60px; height:60px; line-height:60px; font-size:20px;">
            <%= iniciais.toUpperCase() %>
          </div>
          <h5><%= p.getNome() %></h5>
          <p class="text-muted"><%= p.getEmail() %></p>
          <div class="d-flex justify-content-center">
            <a href="#" class="text-primary me-3" data-bs-toggle="modal" data-bs-target="#editarModal<%= p.getId() %>">✏️</a>
            <a href="#" class="text-danger" data-bs-toggle="modal" data-bs-target="#excluirModal<%= p.getId() %>">🗑️</a>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal Editar -->
    <div class="modal fade" id="editarModal<%= p.getId() %>" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog">
        <form class="modal-content" action="editar-participante" method="post">
          <input type="hidden" name="id" value="<%= p.getId() %>">
          <div class="modal-header">
            <h5 class="modal-title">Editar Participante</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label class="form-label">Nome</label>
              <input type="text" class="form-control" name="nome" value="<%= p.getNome() %>" required>
            </div>
            <div class="mb-3">
              <label class="form-label">Email</label>
              <input type="email" class="form-control" name="email" value="<%= p.getEmail() %>" required>
            </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-success">Salvar</button>
          </div>
        </form>
      </div>
    </div>

    <!-- Modal Excluir -->
    <div class="modal fade" id="excluirModal<%= p.getId() %>" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog">
        <form class="modal-content" action="excluir-participante" method="post">
          <input type="hidden" name="id" value="<%= p.getId() %>">
          <div class="modal-header">
            <h5 class="modal-title">Excluir Participante</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
            <p>Tem certeza que deseja excluir <strong><%= p.getNome() %></strong>?</p>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-danger">Excluir</button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
          </div>
        </form>
      </div>
    </div>
    <%
      }
    } else {
    %>
    <p class="text-muted">Nenhum participante encontrado.</p>
    <%
      }
    %>
  </div>

  <!-- Modal Novo Participante -->
  <div class="modal fade" id="novoParticipanteModal" tabindex="-1" aria-labelledby="novoParticipanteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <form class="modal-content" action="cadastrar-participante" method="post">
        <div class="modal-header">
          <h5 class="modal-title" id="novoParticipanteModalLabel">Novo Participante</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label class="form-label">Nome</label>
            <input type="text" class="form-control" name="nome" required>
          </div>
          <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" class="form-control" name="email" required>
          </div>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-success">Salvar</button>
        </div>
      </form>
    </div>
  </div>

  <% if (request.getAttribute("erroExclusao") != null) { %>
  <script>
    document.addEventListener("DOMContentLoaded", function () {
      var erroModal = new bootstrap.Modal(document.getElementById('erroExclusaoModal'));
      erroModal.show();
    });
  </script>

  <div class="modal fade" id="erroExclusaoModal" tabindex="-1" aria-labelledby="erroModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content border-danger">
        <div class="modal-header bg-danger text-white">
          <h5 class="modal-title" id="erroModalLabel">Erro ao excluir participante</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body text-danger">
          <%= request.getAttribute("erroExclusao") %>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
        </div>
      </div>
    </div>
  </div>
  <% } %>
</div>
</body>
</html>
