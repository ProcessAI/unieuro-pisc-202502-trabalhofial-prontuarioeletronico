package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {

    public boolean insert(Medicamento m) {
        String sql = "INSERT INTO medicamento (medicamento_nome, medicamento_bula, medicamento_tipo, medicamento_tarja, medicamento_principioativo, medicamento_observacao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, m.getMedicamentoNome());
            stmt.setString(2, m.getBula());
            stmt.setString(3, m.getTipo());
            stmt.setString(4, m.getTarja());
            stmt.setString(5, m.getPrincipioAtivo());
            stmt.setString(6, m.getObservacao());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        m.setMedicamentoId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir medicamento", e);
        }

        return false;
    }

    public List<Medicamento> findAll() {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM medicamento ORDER BY medicamento_nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medicamento m = new Medicamento(
                        rs.getInt("medicamento_id"),
                        rs.getString("medicamento_nome"),
                        rs.getString("medicamento_bula"),
                        rs.getString("medicamento_tipo"),
                        rs.getString("medicamento_tarja"),
                        rs.getString("medicamento_principioativo"),
                        rs.getString("medicamento_observacao")
                );
                lista.add(m);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar medicamentos", e);
        }

        return lista;
    }

    public Medicamento findById(int id) {
        String sql = "SELECT * FROM medicamento WHERE medicamento_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Medicamento(
                            rs.getInt("medicamento_id"),
                            rs.getString("medicamento_nome"),
                            rs.getString("medicamento_bula"),
                            rs.getString("medicamento_tipo"),
                            rs.getString("medicamento_tarja"),
                            rs.getString("medicamento_principioativo"),
                            rs.getString("medicamento_observacao")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar medicamento por ID", e);
        }

        return null;
    }

    public boolean update(Medicamento m) {
        String sql = "UPDATE medicamento SET medicamento_nome=?, medicamento_bula=?, medicamento_tipo=?, medicamento_tarja=?, medicamento_principioativo=?, medicamento_observacao=? WHERE medicamento_id=?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getMedicamentoNome());
            stmt.setString(2, m.getBula());
            stmt.setString(3, m.getTipo());
            stmt.setString(4, m.getTarja());
            stmt.setString(5, m.getPrincipioAtivo());
            stmt.setString(6, m.getObservacao());
            stmt.setInt(7, m.getMedicamentoId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar medicamento", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM medicamento WHERE medicamento_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir medicamento", e);
        }
    }
}
