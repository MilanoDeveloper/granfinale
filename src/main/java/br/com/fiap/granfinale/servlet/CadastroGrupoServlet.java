package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.GrupoDAO;
import br.com.fiap.granfinale.dao.ParticipanteDAO;
import br.com.fiap.granfinale.factory.DAOFactory;
import br.com.fiap.granfinale.model.Grupo;
import br.com.fiap.granfinale.model.Participante;
import br.com.fiap.granfinale.util.ConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cadastrar-grupo")
public class CadastroGrupoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String[] participantesIds = req.getParameterValues("participantes");

        List<Participante> membros = new ArrayList<>();
        if (participantesIds != null) {
            for (String idStr : participantesIds) {
                Participante p = new Participante();
                p.setId(Integer.parseInt(idStr));
                membros.add(p);
            }
        }

        Grupo grupo = new Grupo();
        grupo.setNome(nome);
        grupo.setMembros(membros);

        try (Connection conn = ConnectionManager.getConnection()) {
            DAOFactory factory = new DAOFactory(conn);
            GrupoDAO grupoDAO = factory.getGrupoDAO();

            grupoDAO.cadastrar(grupo);
            resp.sendRedirect("grupos");

        } catch (Exception e) {
            throw new ServletException("Erro ao cadastrar grupo", e);
        }
    }
}
