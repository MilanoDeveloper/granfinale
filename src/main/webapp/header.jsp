<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.fiap.granfinale.model.User" %>

<%
    User userLogado = (User) session.getAttribute("usuario");
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary px-0">
    <div class="container-fluid px-3">
        <a class="navbar-brand" href="dashboard.jsp">SplitWise</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" href="participantes">Usuários</a></li>
                <li class="nav-item"><a class="nav-link" href="grupos">Grupos</a></li>
                <li class="nav-item"><a class="nav-link" href="despesas">Despesas</a></li>
                <li class="nav-item"><a class="nav-link" href="saldos">Saldos</a></li>
            </ul>
            <div class="dropdown">
                <button class="btn btn-danger dropdown-toggle rounded-circle text-white fw-bold"
                        type="button" data-bs-toggle="dropdown"
                        style="width: 40px; height: 40px;">
                    <%= userLogado.getNome().substring(0, 1).toUpperCase() %>
                </button>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li><span class="dropdown-item-text fw-bold"><%= userLogado.getNome() %></span></li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li><a class="dropdown-item text-danger" href="logout">Sair</a></li>
                </ul>
            </div>
        </div>
    </div>
</nav>
