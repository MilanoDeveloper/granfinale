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
import java.sql.SQLException;

@WebServlet("/cadastrar")
public class CadastroServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try (Connection conn = ConnectionManager.getConnection()) {
            UserDAO dao = new UserDAO(conn);
            User novoUsuario = new User();
            novoUsuario.setNome(nome);
            novoUsuario.setEmail(email);
            novoUsuario.setSenha(senha);
            boolean sucesso = dao.cadastrar(novoUsuario);

            if (sucesso) {
                response.sendRedirect("login.jsp");
            } else {
                request.setAttribute("erro", "Esse e-mail já foi cadastrado.");
                request.getRequestDispatcher("cadastrar.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
