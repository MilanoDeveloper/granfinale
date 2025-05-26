package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.GrupoDAO;
import br.com.fiap.granfinale.factory.DAOFactory;
import br.com.fiap.granfinale.util.ConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/excluir-grupo")
public class ExcluirGrupoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int grupoId = Integer.parseInt(req.getParameter("id"));

        try (Connection conn = ConnectionManager.getConnection()) {
            DAOFactory factory = new DAOFactory(conn);
            GrupoDAO grupoDAO = factory.getGrupoDAO();

            grupoDAO.excluir(grupoId);
            resp.sendRedirect("grupos");

        } catch (Exception e) {
            throw new ServletException("Erro ao excluir grupo", e);
        }
    }
}
