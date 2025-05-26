package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.DespesaDAO;
import br.com.fiap.granfinale.dao.GrupoDAO;
import br.com.fiap.granfinale.dao.ParticipanteDAO;
import br.com.fiap.granfinale.factory.DAOFactory;
import br.com.fiap.granfinale.model.Despesa;
import br.com.fiap.granfinale.model.Grupo;
import br.com.fiap.granfinale.model.Participante;
import br.com.fiap.granfinale.util.ConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/despesas")
public class DespesaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ConnectionManager.getConnection()) {
            DAOFactory factory = new DAOFactory(conn);

            DespesaDAO despesaDAO = factory.getDespesaDAO();
            GrupoDAO grupoDAO = factory.getGrupoDAO();
            ParticipanteDAO participanteDAO = factory.getParticipanteDAO();

            List<Despesa> despesas = despesaDAO.listar();
            List<Grupo> grupos = grupoDAO.listarTodos();
            List<Participante> participantes = participanteDAO.listarTodos();

            req.setAttribute("despesas", despesas);
            req.setAttribute("grupos", grupos);
            req.setAttribute("participantes", participantes);

            req.getRequestDispatcher("despesas.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Erro ao carregar despesas", e);
        }
    }
}
