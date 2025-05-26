
package br.com.fiap.granfinale.dao;

import br.com.fiap.granfinale.model.*;
import br.com.fiap.granfinale.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DespesaDAO {

    public DespesaDAO(Connection connection) {
    }

    public boolean cadastrar(Despesa despesa) {
        String sqlDespesa = "INSERT INTO despesas (descricao, valor, data, grupo_id, pagador_id) VALUES (?, ?, ?, ?, ?)";
        String sqlDivisao = "INSERT INTO despesa_participante (despesa_id, participante_id, valor) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtDespesa = conn.prepareStatement(sqlDespesa, new String[]{"id"})) {
                stmtDespesa.setString(1, despesa.getDescricao());
                stmtDespesa.setDouble(2, despesa.getValor());
                stmtDespesa.setDate(3, new java.sql.Date(despesa.getData().getTime()));
                stmtDespesa.setInt(4, despesa.getGrupo().getId());
                stmtDespesa.setInt(5, despesa.getPagador().getId());
                stmtDespesa.executeUpdate();

                ResultSet rs = stmtDespesa.getGeneratedKeys();
                if (rs.next()) {
                    int despesaId = rs.getInt(1);

                    try (PreparedStatement stmtDiv = conn.prepareStatement(sqlDivisao)) {
                        for (DespesaDivisao div : despesa.getDivisoes()) {
                            stmtDiv.setInt(1, despesaId);
                            stmtDiv.setInt(2, div.getParticipante().getId());
                            stmtDiv.setDouble(3, div.getValor());
                            stmtDiv.addBatch();
                        }
                        stmtDiv.executeBatch();
                    }
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Despesa> listar() {
        List<Despesa> despesas = new ArrayList<>();
        String sql = "SELECT d.*, g.nome as grupo_nome, p.nome as pagador_nome, p.email as pagador_email " +
                "FROM despesas d " +
                "JOIN grupos g ON d.grupo_id = g.id " +
                "JOIN participantes p ON d.pagador_id = p.id";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Despesa d = new Despesa();
                d.setId(rs.getInt("id"));
                d.setDescricao(rs.getString("descricao"));
                d.setValor(rs.getDouble("valor"));
                d.setData(rs.getDate("data"));

                Grupo g = new Grupo();
                g.setId(rs.getInt("grupo_id"));
                g.setNome(rs.getString("grupo_nome"));
                d.setGrupo(g);

                Participante pagador = new Participante();
                pagador.setId(rs.getInt("pagador_id"));
                pagador.setNome(rs.getString("pagador_nome"));
                pagador.setEmail(rs.getString("pagador_email"));
                d.setPagador(pagador);

                d.setDivisoes(buscarDivisoes(d.getId(), conn));
                despesas.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return despesas;
    }

    public boolean excluir(int id) {
        String sqlDivisoes = "DELETE FROM despesa_participante WHERE despesa_id = ?";
        String sqlDespesa = "DELETE FROM despesas WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(sqlDivisoes)) {
                stmt1.setInt(1, id);
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(sqlDespesa)) {
                stmt2.setInt(1, id);
                stmt2.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void atualizarStatus(int despesaId, int participanteId, String status) {
        String sql = "UPDATE despesa_participante SET status = ? WHERE despesa_id = ? AND participante_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, despesaId);
            stmt.setInt(3, participanteId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<DespesaDivisao> buscarDivisoes(int despesaId, Connection conn) throws SQLException {
        List<DespesaDivisao> divs = new ArrayList<>();
        String sql = "SELECT dp.valor, dp.status, p.id, p.nome, p.email " +
                "FROM despesa_participante dp " +
                "JOIN participantes p ON dp.participante_id = p.id WHERE dp.despesa_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, despesaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Participante p = new Participante();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEmail(rs.getString("email"));

                DespesaDivisao div = new DespesaDivisao(p, rs.getDouble("valor"));
                div.setStatus(rs.getString("status"));
                divs.add(div);
            }
        }

        return divs;
    }

    public void atualizarValor(int despesaId, int participanteId, double valor) {
        String sql = "UPDATE despesa_participante SET valor = ? WHERE despesa_id = ? AND participante_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, valor);
            stmt.setInt(2, despesaId);
            stmt.setInt(3, participanteId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
