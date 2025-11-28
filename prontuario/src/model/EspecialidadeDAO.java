package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadeDAO {

    public boolean insert(Especialidade e) {
        String sql = "INSERT INTO especialidade (" +
                "especialidade_nome, especialidade_status, especialidade_cbo, " +
                "especialidade_escala, especialidade_descricao" +
                ") VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, e.getEspecialidadeNome());
            stmt.setString(2, e.getEspecialidadeStatus());
            stmt.setString(3, e.getEspecialidadeCbo());
            stmt.setString(4, e.getEspecialidadeEscala());
            stmt.setString(5, e.getEspecialidadeDescricao());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        e.setEspecialidadeId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir especialidade", ex);
        }

        return false;
    }

    public List<Especialidade> findAll() {
        List<Especialidade> lista = new ArrayList<>();

        String sql = "SELECT especialidade_id, especialidade_nome, especialidade_status, " +
                "especialidade_cbo, especialidade_escala, especialidade_descricao " +
                "FROM especialidade ORDER BY especialidade_nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Especialidade e = new Especialidade(
                        rs.getInt("especialidade_id"),
                        rs.getString("especialidade_nome"),
                        rs.getString("especialidade_status"),
                        rs.getString("especialidade_cbo"),
                        rs.getString("especialidade_escala"),
                        rs.getString("especialidade_descricao")
                );
                lista.add(e);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar especialidades", ex);
        }

        return lista;
    }

    public Especialidade findById(int id) {
        String sql = "SELECT especialidade_id, especialidade_nome, especialidade_status, " +
                "especialidade_cbo, especialidade_escala, especialidade_descricao " +
                "FROM especialidade WHERE especialidade_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Especialidade(
                            rs.getInt("especialidade_id"),
                            rs.getString("especialidade_nome"),
                            rs.getString("especialidade_status"),
                            rs.getString("especialidade_cbo"),
                            rs.getString("especialidade_escala"),
                            rs.getString("especialidade_descricao")
                    );
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar especialidade por ID", ex);
        }

        return null;
    }

    public boolean update(Especialidade e) {
        String sql = "UPDATE especialidade SET " +
                "especialidade_nome = ?, especialidade_status = ?, especialidade_cbo = ?, " +
                "especialidade_escala = ?, especialidade_descricao = ? " +
                "WHERE especialidade_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getEspecialidadeNome());
            stmt.setString(2, e.getEspecialidadeStatus());
            stmt.setString(3, e.getEspecialidadeCbo());
            stmt.setString(4, e.getEspecialidadeEscala());
            stmt.setString(5, e.getEspecialidadeDescricao());
            stmt.setInt(6, e.getEspecialidadeId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar especialidade", ex);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM especialidade WHERE especialidade_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar especialidade", ex);
        }
    }
}
