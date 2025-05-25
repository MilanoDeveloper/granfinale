
package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.UserDAO;
import br.com.fiap.granfinale.exception.EmailJaCadastradoException;
import br.com.fiap.granfinale.model.User;
import br.com.fiap.granfinale.util.ConnectionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/cadastrar-usuario")
public class CadastroUsuarioServlet extends HttpServlet {

    Connection conn = ConnectionManager.getConnection();
    private UserDAO userDAO = new UserDAO(conn);

    public CadastroUsuarioServlet() throws Exception {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");

        User user = new User();
        user.setNome(nome);
        user.setEmail(email);

        try {
            userDAO.cadastrar(user);
        } catch (EmailJaCadastradoException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("usuarios");
    }
}
