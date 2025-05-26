
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

@WebServlet("/excluir-participante")
public class ExcluirParticipanteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        try (Connection conn = ConnectionManager.getConnection()) {
            DAOFactory factory = new DAOFactory(conn);
            ParticipanteDAO dao = factory.getParticipanteDAO();

            dao.excluir(id);
            resp.sendRedirect("participantes");

        } catch (Exception e) {
            try (Connection conn = ConnectionManager.getConnection()) {
                DAOFactory factory = new DAOFactory(conn);
                ParticipanteDAO dao = factory.getParticipanteDAO();

                req.setAttribute("erroExclusao", "Não é possível excluir este participante. Ele está associado a uma ou mais despesas ou grupos.");
                req.setAttribute("participantes", dao.listarTodos());
                req.getRequestDispatcher("participantes.jsp").forward(req, resp);
            } catch (Exception ex) {
                throw new ServletException("Erro ao carregar participantes após falha de exclusão", ex);
            }
        }
    }
}
