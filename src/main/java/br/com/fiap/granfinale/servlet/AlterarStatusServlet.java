package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.DespesaDAO;
import br.com.fiap.granfinale.factory.DAOFactory;
import br.com.fiap.granfinale.util.ConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/alterar-status")
public class AlterarStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int despesaId = Integer.parseInt(req.getParameter("despesaId"));
        int participanteId = Integer.parseInt(req.getParameter("participanteId"));
        String status = req.getParameter("status");

        try (Connection conn = ConnectionManager.getConnection()) {
            DAOFactory factory = new DAOFactory(conn);
            DespesaDAO dao = factory.getDespesaDAO();

            dao.atualizarStatus(despesaId, participanteId, status);
            resp.sendRedirect("saldos");

        } catch (Exception e) {
            throw new ServletException("Erro ao alterar status da despesa", e);
        }
    }
}
