
package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.ParticipanteDAO;
import br.com.fiap.granfinale.model.Participante;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/editar-participante")
public class EditarParticipanteServlet extends HttpServlet {
    private ParticipanteDAO dao = new ParticipanteDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");

        Participante p = new Participante();
        p.setId(id);
        p.setNome(nome);
        p.setEmail(email);

        dao.atualizar(p);
        resp.sendRedirect("participantes");
    }
}
