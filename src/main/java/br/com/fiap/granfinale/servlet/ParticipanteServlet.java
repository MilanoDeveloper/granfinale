package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.ParticipanteDAO;
import br.com.fiap.granfinale.factory.DAOFactory;
import br.com.fiap.granfinale.model.Participante;
import br.com.fiap.granfinale.util.ConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/participantes")
public class ParticipanteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ConnectionManager.getConnection()) {
            DAOFactory factory = new DAOFactory(conn);
            ParticipanteDAO dao = factory.getParticipanteDAO();

            List<Participante> lista = dao.listarTodos();
            req.setAttribute("participantes", lista);
            req.getRequestDispatcher("participantes.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Erro ao carregar participantes", e);
        }
    }
}
