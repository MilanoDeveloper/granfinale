
package br.com.fiap.granfinale.dao;

import br.com.fiap.granfinale.model.Participante;
import br.com.fiap.granfinale.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDAO {


    public boolean atualizar(Participante participante) {
        String sql = "UPDATE participantes SET nome = ?, email = ? WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getEmail());
            stmt.setInt(3, participante.getId());
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM participantes WHERE id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cadastrar(Participante participante) {
        String sql = "INSERT INTO participantes (nome, email) VALUES (?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getEmail());
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Participante> listarTodos() {
        List<Participante> lista = new ArrayList<>();
        String sql = "SELECT * FROM participantes";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Participante p = new Participante();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEmail(rs.getString("email"));
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
