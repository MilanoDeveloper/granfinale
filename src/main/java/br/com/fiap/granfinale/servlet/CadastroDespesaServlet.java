package br.com.fiap.granfinale.servlet;

import br.com.fiap.granfinale.dao.DespesaDAO;
import br.com.fiap.granfinale.factory.DAOFactory;
import br.com.fiap.granfinale.model.*;

import br.com.fiap.granfinale.util.ConnectionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/cadastrar-despesa")
public class CadastroDespesaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = ConnectionManager.getConnection()) {
            DAOFactory factory = new DAOFactory(conn);
            DespesaDAO dao = factory.getDespesaDAO();

            String descricao = req.getParameter("descricao");
            double valor = Double.parseDouble(req.getParameter("valor"));
            Date data = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("data"));

            int grupoId = Integer.parseInt(req.getParameter("grupo"));
            int pagadorId = Integer.parseInt(req.getParameter("pagador"));

            String[] participanteIds = req.getParameterValues("participanteId");
            String[] valoresPorParticipante = req.getParameterValues("valorPorParticipante");

            Despesa despesa = new Despesa();
            despesa.setDescricao(descricao);
            despesa.setValor(valor);
            despesa.setData(data);

            Grupo grupo = new Grupo();
            grupo.setId(grupoId);
            despesa.setGrupo(grupo);

            Participante pagador = new Participante();
            pagador.setId(pagadorId);
            despesa.setPagador(pagador);

            List<DespesaDivisao> divs = new ArrayList<>();
            if (participanteIds != null && valoresPorParticipante != null) {
                for (int i = 0; i < participanteIds.length; i++) {
                    Participante p = new Participante();
                    p.setId(Integer.parseInt(participanteIds[i]));
                    double val = Double.parseDouble(valoresPorParticipante[i]);

                    divs.add(new DespesaDivisao(p, val));
                }
            }

            despesa.setDivisoes(divs);
            dao.cadastrar(despesa);
            resp.sendRedirect("despesas");

        } catch (Exception e) {
            throw new ServletException("Erro ao cadastrar despesa", e);
        }
    }
}
