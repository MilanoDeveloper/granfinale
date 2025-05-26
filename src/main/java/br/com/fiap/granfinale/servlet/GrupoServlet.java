
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
import java.util.List;

@WebServlet("/grupos")
public class GrupoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ConnectionManager.getConnection()) {
            DAOFactory factory = new DAOFactory(conn);
            GrupoDAO grupoDAO = factory.getGrupoDAO();
            ParticipanteDAO participanteDAO = factory.getParticipanteDAO();

            List<Grupo> grupos = grupoDAO.listarTodos();
            List<Participante> participantes = participanteDAO.listarTodos();

            req.setAttribute("grupos", grupos);
            req.setAttribute("participantes", participantes);
            req.getRequestDispatcher("grupos.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Erro ao carregar grupos", e);
        }
    }
}
