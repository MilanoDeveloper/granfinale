package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.DespesaDAO;
import br.com.fiap.granfinale.factory.DAOFactory;
import br.com.fiap.granfinale.util.ConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/editar-divisao")
public class EditarDivisaoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int despesaId = Integer.parseInt(req.getParameter("despesaId"));
        int participanteId = Integer.parseInt(req.getParameter("participanteId"));
        double novoValor = Double.parseDouble(req.getParameter("novoValor"));

        try (Connection conn = ConnectionManager.getConnection()) {
            DAOFactory factory = new DAOFactory(conn);
            DespesaDAO dao = factory.getDespesaDAO();

            dao.atualizarValor(despesaId, participanteId, novoValor);
            resp.sendRedirect("saldos");

        } catch (Exception e) {
            throw new ServletException("Erro ao atualizar valor da divisão", e);
        }
    }
}
