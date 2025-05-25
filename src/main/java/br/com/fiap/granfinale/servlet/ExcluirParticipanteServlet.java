
package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.ParticipanteDAO;
import br.com.fiap.granfinale.model.Participante;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/excluir-participante")
public class ExcluirParticipanteServlet extends HttpServlet {

    private ParticipanteDAO dao = new ParticipanteDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            dao.excluir(id);
            resp.sendRedirect("participantes");
        } catch (Exception e) {
                req.setAttribute("erroExclusao", "Não é possível excluir este participante. Ele está associado a uma ou mais despesas ou grupos.");
                req.setAttribute("participantes", dao.listarTodos());
                req.getRequestDispatcher("participantes.jsp").forward(req, resp);
        }
    }
}