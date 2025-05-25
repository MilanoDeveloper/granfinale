package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.UserDAO;
import br.com.fiap.granfinale.model.User;
import br.com.fiap.granfinale.util.ConnectionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {
    Connection conn = ConnectionManager.getConnection();
    private UserDAO userDAO = new UserDAO(conn);

    public UsuarioServlet() throws Exception {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> usuarios = userDAO.listarTodos();
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("usuarios.jsp").forward(req, resp);
    }
}
