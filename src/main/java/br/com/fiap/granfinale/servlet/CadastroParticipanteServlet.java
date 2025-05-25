
package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.ParticipanteDAO;
import br.com.fiap.granfinale.model.Participante;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/cadastrar-participante")
public class CadastroParticipanteServlet extends HttpServlet {
    private ParticipanteDAO dao = new ParticipanteDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");

        Participante p = new Participante(nome, email);
        dao.cadastrar(p);

        resp.sendRedirect("participantes");
    }
}
