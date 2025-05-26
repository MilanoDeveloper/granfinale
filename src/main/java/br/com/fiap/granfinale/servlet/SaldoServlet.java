package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.DespesaDAO;
import br.com.fiap.granfinale.factory.DAOFactory;
import br.com.fiap.granfinale.model.*;

import br.com.fiap.granfinale.util.ConnectionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;

@WebServlet("/saldos")
public class SaldoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ConnectionManager.getConnection()) {
            DAOFactory factory = new DAOFactory(conn);
            DespesaDAO despesaDAO = factory.getDespesaDAO();

            List<Despesa> despesas = despesaDAO.listar();

            int pendentes = 0, pagos = 0, cancelados = 0;
            Map<Participante, List<DespesaDivisao>> resumos = new HashMap<>();

            for (Despesa d : despesas) {
                for (DespesaDivisao div : d.getDivisoes()) {
                    switch (div.getStatus()) {
                        case "PAGO" -> pagos++;
                        case "CANCELADO" -> cancelados++;
                        default -> pendentes++;
                    }

                    Participante pagador = d.getPagador();
                    resumos.computeIfAbsent(pagador, k -> new ArrayList<>()).add(div);
                }
            }

            req.setAttribute("resumos", resumos);
            req.setAttribute("despesas", despesas);
            req.setAttribute("pendentes", pendentes);
            req.setAttribute("pagos", pagos);
            req.setAttribute("cancelados", cancelados);

            req.getRequestDispatcher("saldos.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Erro ao carregar saldos", e);
        }
    }
}
