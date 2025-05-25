<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="d-flex justify-content-center align-items-center vh-100 bg-body-secondary">

<div class="card shadow-sm p-4" style="width: 100%; max-width: 400px;">
    <div class="text-center mb-4">
        <img src="resources/img/logo.png" alt="SplitWise" style="max-height: 250px;">
    </div>

    <% if (request.getAttribute("erro") != null) { %>
    <div class="alert alert-danger text-center" role="alert">
        <%= request.getAttribute("erro") %>
    </div>
    <% } %>
    <form action="login" method="post">
        <div class="mb-3">
            <label class="form-label">E-mail:</label>
            <input type="email" class="form-control" name="email" required/>
        </div>
        <div class="mb-3">
            <label class="form-label">Senha:</label>
            <input type="password" class="form-control" name="senha" required/>
        </div>
        <div class="d-grid">
            <button type="submit" class="btn btn-primary">Entrar</button>
        </div>
    </form>

    <div class="text-center mt-3">
        <small>Não possui conta? <a href="cadastrar.jsp">Cadastre-se</a></small>
    </div>
</div>

</body>
</html>
