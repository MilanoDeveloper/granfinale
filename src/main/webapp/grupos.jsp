<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="br.com.fiap.granfinale.model.Grupo" %>
<%@ page import="br.com.fiap.granfinale.model.Participante" %>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <title>Grupos</title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-body-secondary px-0">

<div class="container py-5">

    <%
        br.com.fiap.granfinale.dao.ParticipanteDAO participanteDAO = new br.com.fiap.granfinale.dao.ParticipanteDAO();
        List<br.com.fiap.granfinale.model.Participante> participantes = participanteDAO.listarTodos();
        List<Grupo> grupos = (List<Grupo>) request.getAttribute("grupos");
    %>

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="text-primary">Grupos</h3>
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#novoGrupoModal">+ Novo Grupo</button>
    </div>

    <div class="row">
        <%
            if (grupos != null && !grupos.isEmpty()) {
                for (Grupo grupo : grupos) {
        %>
        <div class="col-md-4 mb-4">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h5><%= grupo.getNome() %>
                    </h5>
                    <p class="fw-bold">Membros:</p>
                    <div class="mb-2 d-flex flex-wrap">
                        <%
                            List<Participante> membros = grupo.getMembros();
                            for (int i = 0; i < membros.size(); i++) {
                                Participante p = membros.get(i);
                                String[] partes = p.getNome().split(" ");
                                String iniciais = partes.length > 1 ?
                                        ("" + partes[0].charAt(0) + partes[1].charAt(0)) :
                                        partes[0].substring(0, Math.min(2, partes[0].length()));
                        %>
                        <div class="rounded-circle bg-primary text-white text-center me-1 mb-1"
                             style="width:35px; height:35px; line-height:35px; font-size:14px;">
                            <%= iniciais.toUpperCase() %>
                        </div>
                        <%
                            if (i == 2 && membros.size() > 3) {
                        %>
                        <div class="rounded-circle bg-secondary text-white text-center me-1 mb-1"
                             style="width:35px; height:35px; line-height:35px; font-size:14px;">
                            +<%= membros.size() - 2 %>
                        </div>
                        <%
                                    break;
                                }
                            }
                        %>
                    </div>
                    <div class="d-flex flex-wrap gap-1">
                        <%
                            for (Participante p : membros) {
                        %>
                        <span class="badge bg-light text-dark border"><%= p.getNome() %></span>
                        <%
                            }
                        %>
                    </div>
                    <div class="mt-3 d-flex justify-content-end gap-3">
                        <a href="#" class="text-primary" data-bs-toggle="modal"
                           data-bs-target="#editarGrupoModal<%= grupo.getId() %>">&#9998;</a>
                        <a href="#" class="text-danger" data-bs-toggle="modal"
                           data-bs-target="#excluirGrupoModal<%= grupo.getId() %>">&#128465;</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="editarGrupoModal<%= grupo.getId() %>" tabindex="-1">
            <div class="modal-dialog">
                <form class="modal-content" action="editar-grupo" method="post">
                    <input type="hidden" name="id" value="<%= grupo.getId() %>">
                    <div class="modal-header">
                        <h5 class="modal-title">Editar Grupo</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Nome do Grupo</label>
                            <input type="text" class="form-control" name="nome" value="<%= grupo.getNome() %>" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Participantes</label>
                            <select class="form-select" name="participantes" multiple required>
                                <%
                                    for (Participante p : participantes) {
                                        boolean selecionado = false;
                                        for (Participante m : grupo.getMembros()) {
                                            if (m.getId() == p.getId()) {
                                                selecionado = true;
                                                break;
                                            }
                                        }
                                %>
                                <option value="<%= p.getId() %>" <%= selecionado ? "selected" : "" %>>
                                    <%= p.getNome() %> (<%= p.getEmail() %>)
                                </option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Salvar Alterações</button>
                    </div>
                </form>
            </div>
        </div>

        <div class="modal fade" id="excluirGrupoModal<%= grupo.getId() %>" tabindex="-1">
            <div class="modal-dialog">
                <form class="modal-content" action="excluir-grupo" method="post">
                    <input type="hidden" name="id" value="<%= grupo.getId() %>">
                    <div class="modal-header">
                        <h5 class="modal-title">Excluir Grupo</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <p>Tem certeza que deseja excluir o grupo <strong><%= grupo.getNome() %>
                        </strong>?</p>
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
        <p class="text-muted">Nenhum grupo cadastrado.</p>
        <%
            }
        %>
    </div>

    <div class="modal fade" id="novoGrupoModal" tabindex="-1">
        <div class="modal-dialog">
            <form class="modal-content" action="cadastrar-grupo" method="post">
                <div class="modal-header">
                    <h5 class="modal-title">Novo Grupo</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Nome do Grupo</label>
                        <input type="text" class="form-control" name="nome" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Participantes</label>
                        <select class="form-select" name="participantes" multiple required>
                            <%
                                for (Participante p : participantes) {
                            %>
                            <option value="<%= p.getId() %>"><%= p.getNome() %> (<%= p.getEmail() %>)</option>
                            <%
                                }
                            %>
                        </select>
                        <small class="form-text text-muted">Segure Ctrl (ou Cmd) para selecionar múltiplos</small>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">Criar Grupo</button>
                </div>
            </form>
        </div>
    </div>

</div>
</body>
</html>
