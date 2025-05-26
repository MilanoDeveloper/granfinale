package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.DespesaDAO;
import br.com.fiap.granfinale.factory.DAOFactory;
import br.com.fiap.granfinale.model.Despesa;
import br.com.fiap.granfinale.util.ConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/detalhes-despesa")
public class DetalhesDespesaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        try (Connection conn = ConnectionManager.getConnection()) {
            DAOFactory factory = new DAOFactory(conn);
            DespesaDAO dao = factory.getDespesaDAO();

            List<Despesa> despesas = dao.listar();
            Despesa despesa = despesas.stream()
                    .filter(d -> d.getId() == id)
                    .findFirst()
                    .orElse(null);

            req.setAttribute("despesa", despesa);
            req.getRequestDispatcher("detalhes-despesa.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Erro ao carregar detalhes da despesa", e);
        }
    }
}
