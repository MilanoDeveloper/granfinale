<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - PayShare</title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-body-secondary px-0">

<div class="container py-5 text-center">
    <h1 class="text-primary fw-bold">Bem-vindo ao PayShare</h1>
    <p class="text-muted">A maneira mais fácil de dividir despesas com amigos e família</p>

    <div class="row justify-content-center mt-4">

        <div class="col-sm-6 col-md-4 col-lg-3 mb-4">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <div class="mb-3 fs-1 text-primary">👥</div>
                    <h5 class="card-title">Usuários</h5>
                    <p class="card-text">Gerencie os usuários do sistema, adicione novos membros e mantenha suas
                        informações atualizadas.</p>
                    <a href="participantes" class="btn btn-primary">Acessar</a>
                </div>
            </div>
        </div>

        <div class="col-sm-6 col-md-4 col-lg-3 mb-4">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <div class="mb-3 fs-1 text-primary">👤👤</div>
                    <h5 class="card-title">Grupos</h5>
                    <p class="card-text">Crie grupos para organizar suas despesas compartilhadas com amigos, família ou
                        colegas.</p>
                    <a href="grupos" class="btn btn-primary">Acessar</a>
                </div>
            </div>
        </div>

        <div class="col-sm-6 col-md-4 col-lg-3 mb-4">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <div class="mb-3 fs-1 text-primary">🧾</div>
                    <h5 class="card-title">Despesas</h5>
                    <p class="card-text">Registre todas as suas despesas compartilhadas e mantenha um controle detalhado
                        dos gastos.</p>
                    <a href="despesas" class="btn btn-primary">Acessar</a>
                </div>
            </div>
        </div>

        <div class="col-sm-6 col-md-4 col-lg-3 mb-4">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <div class="mb-3 fs-1 text-primary">🏦</div>
                    <h5 class="card-title">Saldos</h5>
                    <p class="card-text">Acompanhe quem deve para quem e mantenha suas contas em dia com facilidade.</p>
                    <a href="saldos" class="btn btn-primary">Acessar</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
