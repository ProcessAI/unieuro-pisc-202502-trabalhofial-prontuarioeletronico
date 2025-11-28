package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExameDataAcessObject {

    public boolean insert(Exame exame) {
        String sql = "INSERT INTO exame (" +
                "exame_nome, exame_tipo, exame_status, exame_orientacao, exame_duracao_minutos" +
                ") VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, exame.getExame_nome());
            stmt.setString(2, exame.getExame_tipo());
            stmt.setString(3, exame.getExame_status());
            stmt.setString(4, exame.getExame_orientacao());

            if (exame.getDuracaoMinutos() != null) {
                stmt.setInt(5, exame.getDuracaoMinutos());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        exame.setExame_id(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir exame", e);
        }

        return false;
    }

    public List<Exame> findAll() {
        List<Exame> lista = new ArrayList<>();

        String sql = "SELECT exame_id, exame_nome, exame_tipo, exame_status, " +
                "exame_orientacao, exame_duracao_minutos " +
                "FROM exame ORDER BY exame_nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Exame e = new Exame(
                        rs.getInt("exame_id"),
                        rs.getString("exame_nome"),
                        rs.getString("exame_tipo"),
                        rs.getString("exame_status"),
                        rs.getString("exame_orientacao"),
                        (Integer) rs.getObject("exame_duracao_minutos")
                );
                lista.add(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar exames", e);
        }

        return lista;
    }

    public Exame findById(int id) {
        String sql = "SELECT exame_id, exame_nome, exame_tipo, exame_status, " +
                "exame_orientacao, exame_duracao_minutos " +
                "FROM exame WHERE exame_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Exame(
                            rs.getInt("exame_id"),
                            rs.getString("exame_nome"),
                            rs.getString("exame_tipo"),
                            rs.getString("exame_status"),
                            rs.getString("exame_orientacao"),
                            (Integer) rs.getObject("exame_duracao_minutos")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar exame por ID", e);
        }

        return null;
    }

    public boolean update(Exame exame) {
        String sql = "UPDATE exame SET " +
                "exame_nome = ?, exame_tipo = ?, exame_status = ?, " +
                "exame_orientacao = ?, exame_duracao_minutos = ? " +
                "WHERE exame_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, exame.getExameNome());
            stmt.setString(2, exame.getExameTipo());
            stmt.setString(3, exame.getExameStatus());
            stmt.setString(4, exame.getExameOrientacao());

            if (exame.getDuracaoMinutos() != null) {
                stmt.setInt(5, exame.getDuracaoMinutos());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setInt(6, exame.getExameId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar exame", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM exame WHERE exame_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar exame", e);
        }
    }
}
