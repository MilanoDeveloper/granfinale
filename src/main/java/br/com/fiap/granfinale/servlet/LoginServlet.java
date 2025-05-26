package br.com.fiap.granfinale.servlet;


import br.com.fiap.granfinale.dao.UserDAO;
import br.com.fiap.granfinale.exception.UsuarioNaoEncontradoException;
import br.com.fiap.granfinale.model.User;
import br.com.fiap.granfinale.util.ConnectionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try (Connection conn = ConnectionManager.getConnection()) {
            UserDAO dao = new UserDAO(conn);
            User usuario = dao.autenticar(email, senha);

            if (usuario != null) {
                request.getSession().setAttribute("usuario", usuario);
                response.sendRedirect("dashboard.jsp");
            } else {
                request.setAttribute("erro", "Usuário ou senha inválidos");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (UsuarioNaoEncontradoException e) {
            throw new ServletException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

