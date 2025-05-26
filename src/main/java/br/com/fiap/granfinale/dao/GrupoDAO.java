
package br.com.fiap.granfinale.dao;

import br.com.fiap.granfinale.model.Grupo;
import br.com.fiap.granfinale.model.Participante;
import br.com.fiap.granfinale.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAO {

    public GrupoDAO(Connection connection) {
    }

    public boolean cadastrar(Grupo grupo) {
        String sqlGrupo = "INSERT INTO grupos (nome) VALUES (?)";
        String sqlAssoc = "INSERT INTO grupo_participante (grupo_id, participante_id) VALUES (?, ?)";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtGrupo = conn.prepareStatement(sqlGrupo, new String[]{"id"})) {
                stmtGrupo.setString(1, grupo.getNome());
                stmtGrupo.executeUpdate();

                ResultSet rs = stmtGrupo.getGeneratedKeys();
                if (rs.next()) {
                    int grupoId = rs.getInt(1);
                    grupo.setId(grupoId);

                    try (PreparedStatement stmtAssoc = conn.prepareStatement(sqlAssoc)) {
                        for (Participante p : grupo.getMembros()) {
                            stmtAssoc.setInt(1, grupoId);
                            stmtAssoc.setInt(2, p.getId());
                            stmtAssoc.addBatch();
                        }
                        stmtAssoc.executeBatch();
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

    public List<Grupo> listarTodos() {
        List<Grupo> grupos = new ArrayList<>();
        String sql = "SELECT g.id, g.nome FROM grupos g";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Grupo grupo = new Grupo();
                grupo.setId(rs.getInt("id"));
                grupo.setNome(rs.getString("nome"));
                grupo.setMembros(listarMembros(grupo.getId()));
                grupos.add(grupo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return grupos;
    }

    private List<Participante> listarMembros(int grupoId) {
        List<Participante> membros = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.email FROM participantes p " +
                "JOIN grupo_participante gp ON p.id = gp.participante_id WHERE gp.grupo_id = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, grupoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Participante p = new Participante();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEmail(rs.getString("email"));
                membros.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return membros;
    }

    public boolean atualizar(Grupo grupo) {
        String updateGrupo = "UPDATE grupos SET nome = ? WHERE id = ?";
        String deleteAssociacoes = "DELETE FROM grupo_participante WHERE grupo_id = ?";
        String insertAssociacoes = "INSERT INTO grupo_participante (grupo_id, participante_id) VALUES (?, ?)";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtGrupo = conn.prepareStatement(updateGrupo)) {
                stmtGrupo.setString(1, grupo.getNome());
                stmtGrupo.setInt(2, grupo.getId());
                stmtGrupo.executeUpdate();
            }

            try (PreparedStatement stmtDelete = conn.prepareStatement(deleteAssociacoes)) {
                stmtDelete.setInt(1, grupo.getId());
                stmtDelete.executeUpdate();
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(insertAssociacoes)) {
                for (Participante p : grupo.getMembros()) {
                    stmtInsert.setInt(1, grupo.getId());
                    stmtInsert.setInt(2, p.getId());
                    stmtInsert.addBatch();
                }
                stmtInsert.executeBatch();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int grupoId) {
        String deleteAssociacoes = "DELETE FROM grupo_participante WHERE grupo_id = ?";
        String deleteGrupo = "DELETE FROM grupos WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(deleteAssociacoes)) {
                stmt1.setInt(1, grupoId);
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(deleteGrupo)) {
                stmt2.setInt(1, grupoId);
                stmt2.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
