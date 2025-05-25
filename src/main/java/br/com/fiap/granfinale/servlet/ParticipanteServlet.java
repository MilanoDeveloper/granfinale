package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.ParticipanteDAO;
import br.com.fiap.granfinale.model.Participante;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/participantes")
public class ParticipanteServlet extends HttpServlet {
    private ParticipanteDAO dao = new ParticipanteDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Participante> lista = dao.listarTodos();
        req.setAttribute("participantes", lista);
        req.getRequestDispatcher("participantes.jsp").forward(req, resp);
    }
}
