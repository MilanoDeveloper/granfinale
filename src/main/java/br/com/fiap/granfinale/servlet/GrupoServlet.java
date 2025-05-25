package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.GrupoDAO;
import br.com.fiap.granfinale.model.Grupo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/grupos")
public class GrupoServlet extends HttpServlet {
    private GrupoDAO dao = new GrupoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Grupo> grupos = dao.listarTodos();
        req.setAttribute("grupos", grupos);
        req.getRequestDispatcher("grupos.jsp").forward(req, resp);
    }
}
