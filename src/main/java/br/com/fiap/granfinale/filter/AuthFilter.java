package br.com.fiap.granfinale.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        boolean logado = (session != null && session.getAttribute("usuario") != null);
        boolean ehRecursoPublico = uri.contains("login") || uri.contains("cadastrar")
                || uri.endsWith(".css") || uri.endsWith(".js")
                || uri.endsWith(".png") || uri.endsWith(".jpg")
                || uri.endsWith(".woff") || uri.endsWith(".ttf") || uri.contains("resources");

        if (logado || ehRecursoPublico) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect("login.jsp");
        }
    }
}
