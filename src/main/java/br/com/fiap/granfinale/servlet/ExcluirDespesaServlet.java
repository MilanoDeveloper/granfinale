
package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.DespesaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/excluir-despesa")
public class ExcluirDespesaServlet extends HttpServlet {

    private DespesaDAO dao = new DespesaDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        dao.excluir(id);
        resp.sendRedirect("despesas");
    }
}
