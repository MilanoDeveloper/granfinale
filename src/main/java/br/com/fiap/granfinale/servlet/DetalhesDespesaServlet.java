package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.DespesaDAO;
import br.com.fiap.granfinale.model.Despesa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/detalhes-despesa")
public class DetalhesDespesaServlet extends HttpServlet {

    private DespesaDAO dao = new DespesaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Despesa despesa = dao.listar().stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);

        req.setAttribute("despesa", despesa);
        req.getRequestDispatcher("detalhes-despesa.jsp").forward(req, resp);
    }
}
