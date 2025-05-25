
package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.GrupoDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/excluir-grupo")
public class ExcluirGrupoServlet extends HttpServlet {
    private GrupoDAO grupoDAO = new GrupoDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int grupoId = Integer.parseInt(req.getParameter("id"));
        grupoDAO.excluir(grupoId);
        resp.sendRedirect("grupos");
    }
}
