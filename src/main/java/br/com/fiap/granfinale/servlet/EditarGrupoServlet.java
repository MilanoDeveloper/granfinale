
package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.GrupoDAO;
import br.com.fiap.granfinale.model.Grupo;
import br.com.fiap.granfinale.model.Participante;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/editar-grupo")
public class EditarGrupoServlet extends HttpServlet {
    private GrupoDAO grupoDAO = new GrupoDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
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
        grupo.setId(id);
        grupo.setNome(nome);
        grupo.setMembros(membros);

        grupoDAO.atualizar(grupo);
        resp.sendRedirect("grupos");
    }
}
