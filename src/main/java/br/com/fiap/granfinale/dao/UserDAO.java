package br.com.fiap.granfinale.dao;

import br.com.fiap.granfinale.exception.EmailJaCadastradoException;
import br.com.fiap.granfinale.model.User;
import br.com.fiap.granfinale.util.ConnectionManager;
import br.com.fiap.granfinale.util.CriptografiaUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    private boolean emailJaExiste(String email) {
        String sql = "SELECT 1 FROM usuarios WHERE email = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar email", e);
        }
    }

    public boolean cadastrar(User user) throws EmailJaCadastradoException {
        if (emailJaExiste(user.getEmail())) {
            throw new EmailJaCadastradoException(user.getEmail());
        }

        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            String senhaCriptografada = CriptografiaUtils.criptografar(user.getSenha());
            stmt.setString(3, senhaCriptografada);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User autenticar(String email, String senha) {
        User user = null;
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, CriptografiaUtils.criptografar(senha));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setNome(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setSenha(rs.getString("senha"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User buscarPorEmailESenha(String email, String senha) {
        User user = null;
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setNome(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setSenha(rs.getString("senha"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> listarTodos() {
        List<User> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNome(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setSenha(rs.getString("senha")); // evite exibir isso na view
                usuarios.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
