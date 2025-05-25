
package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.DespesaDAO;
import br.com.fiap.granfinale.model.Despesa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/despesas")
public class DespesaServlet extends HttpServlet {

    private DespesaDAO dao = new DespesaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Despesa> despesas = dao.listar();
        req.setAttribute("despesas", despesas);
        req.getRequestDispatcher("despesas.jsp").forward(req, resp);
    }
}
