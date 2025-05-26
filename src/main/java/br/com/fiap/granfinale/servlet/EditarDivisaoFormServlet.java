
package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.DespesaDAO;
import br.com.fiap.granfinale.factory.DAOFactory;
import br.com.fiap.granfinale.model.Despesa;
import br.com.fiap.granfinale.model.DespesaDivisao;
import br.com.fiap.granfinale.util.ConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/editar-divisao-form")
public class EditarDivisaoFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int despesaId = Integer.parseInt(req.getParameter("despesaId"));
        int participanteId = Integer.parseInt(req.getParameter("participanteId"));

        try (Connection conn = ConnectionManager.getConnection()) {
            DAOFactory factory = new DAOFactory(conn);
            DespesaDAO dao = factory.getDespesaDAO();

            Despesa despesa = dao.listar().stream()
                    .filter(d -> d.getId() == despesaId)
                    .findFirst().orElse(null);

            DespesaDivisao divisao = despesa != null
                    ? despesa.getDivisoes().stream()
                    .filter(dv -> dv.getParticipante().getId() == participanteId)
                    .findFirst().orElse(null)
                    : null;

            req.setAttribute("despesa", despesa);
            req.setAttribute("divisao", divisao);
            req.setAttribute("despesaId", despesaId);
            req.setAttribute("participanteId", participanteId);

            req.getRequestDispatcher("editar-divisao.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Erro ao preparar a edição da divisão", e);
        }
    }
}

