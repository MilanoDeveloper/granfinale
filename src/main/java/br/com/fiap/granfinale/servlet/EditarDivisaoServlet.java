package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.DespesaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/editar-divisao")
public class EditarDivisaoServlet extends HttpServlet {

    private DespesaDAO dao = new DespesaDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int despesaId = Integer.parseInt(req.getParameter("despesaId"));
        int participanteId = Integer.parseInt(req.getParameter("participanteId"));
        double novoValor = Double.parseDouble(req.getParameter("novoValor"));

        dao.atualizarValor(despesaId, participanteId, novoValor);
        resp.sendRedirect("saldos");
    }
}
