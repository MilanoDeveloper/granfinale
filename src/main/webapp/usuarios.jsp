<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.fiap.granfinale.model.User" %>
<%@ page import="java.util.List" %>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <title>Usuários</title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-body-secondary px-0">
<div class="d-flex justify-content-between align-items-center mb-4">
    <h3>Usuários</h3>
    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#novoUsuarioModal">+ Novo Usuário</button>
</div>
<div class="row">
    <%
        List<User> usuarios = (List<User>) request.getAttribute("usuarios");
        if (usuarios != null && !usuarios.isEmpty()) {
            for (User user : usuarios) {
                String[] nomeDividido = user.getNome().split(" ");
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
                <h5><%= user.getNome() %>
                </h5>
                <p class="text-muted"><%= user.getEmail() %>
                </p>
                <a href="#" class="text-primary me-2"><i class="bi bi-pencil"></i></a>
                <a href="#" class="text-danger"><i class="bi bi-trash"></i></a>
            </div>
        </div>
    </div>
    <%
        }
    } else {
    %>
    <p class="text-muted">Nenhum usuário encontrado.</p>
    <%
        }
    %>
</div>

<!-- Modal Novo Usuário -->
<div class="modal fade" id="novoUsuarioModal" tabindex="-1" aria-labelledby="novoUsuarioModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content" action="cadastrar-usuario" method="post">
            <div class="modal-header">
                <h5 class="modal-title" id="novoUsuarioModalLabel">Novo Usuário</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="nome" id="nome" class="form-label">Nome</label>
                    <input type="text" class="form-control" name="nome" required>
                </div>
                <div class="mb-3">
                    <label for="email" id="email" class="form-label">Email</label>
                    <input type="email" class="form-control" name="email" required>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Salvar</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
