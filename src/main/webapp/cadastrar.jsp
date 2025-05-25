<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cadastro</title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="d-flex justify-content-center align-items-center vh-100 bg-light">

<div class="card shadow-sm p-4" style="width: 100%; max-width: 400px;">
    <div class="text-center mb-4">
        <img src="resources/img/logo.png" alt="SplitWise" style="max-height: 50px;">
        <h3 class="mt-2 text-primary">Criar Conta</h3>
    </div>

    <% if (request.getAttribute("erro") != null) { %>
    <div class="alert alert-danger text-center">
        <%= request.getAttribute("erro") %>
    </div>
    <% } %>

    <form action="cadastrar" method="post" class="needs-validation" novalidate>
        <div class="mb-3">
            <label class="form-label">Nome:</label>
            <input type="text" class="form-control" name="nome" required minlength="3">
            <div class="invalid-feedback">Informe um nome com ao menos 3 letras.</div>
        </div>
        <div class="mb-3">
            <label class="form-label">E-mail:</label>
            <input type="email" class="form-control" name="email" required>
            <div class="invalid-feedback">Informe um e-mail válido.</div>
        </div>
        <div class="mb-3">
            <label class="form-label">Senha:</label>
            <input type="password" class="form-control" name="senha" required minlength="6">
            <div class="invalid-feedback">A senha deve ter ao menos 6 caracteres.</div>
        </div>
        <div class="d-grid">
            <button type="submit" class="btn btn-success">Cadastrar</button>
        </div>
    </form>

    <div class="text-center mt-3">
        <small>Já possui conta? <a href="login.jsp">Fazer login</a></small>
    </div>
</div>

<script>
    (() => {
        'use strict';
        const forms = document.querySelectorAll('.needs-validation');
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    })();
</script>

</body>
</html>
