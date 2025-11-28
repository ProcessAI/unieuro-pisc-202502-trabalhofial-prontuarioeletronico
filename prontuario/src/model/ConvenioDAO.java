package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConvenioDAO {

    public boolean insert(Convenio convenio) {
        String sql = "INSERT INTO convenio (" +
                "convenio_nome, convenio_tipo, convenio_area, convenio_coparticipacao, convenio_status" +
                ") VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, convenio.getConvenioNome());
            stmt.setString(2, convenio.getConvenioTipo());
            stmt.setString(3, convenio.getConvenioArea());
            stmt.setString(4, convenio.getCoparticipacao());
            stmt.setString(5, convenio.getConvenioStatus());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        convenio.setIdconvenio(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir convênio", e);
        }
        return false;
    }

    public List<Convenio> findAll() {
        List<Convenio> lista = new ArrayList<>();

        String sql = "SELECT convenio_id, convenio_nome, convenio_tipo, convenio_area, " +
                "convenio_coparticipacao, convenio_status " +
                "FROM convenio ORDER BY convenio_nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Convenio c = new Convenio(
                        rs.getInt("convenio_id"),
                        rs.getString("convenio_nome"),
                        rs.getString("convenio_tipo"),
                        rs.getString("convenio_area"),
                        rs.getString("convenio_coparticipacao"),
                        rs.getString("convenio_status")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar convênios", e);
        }

        return lista;
    }

    // 🔹 ESTE é o método que a TelaPaciente usa:
    public List<Convenio> listarTodos() {
        return findAll();
    }

    public Convenio findById(int id) {
        String sql = "SELECT convenio_id, convenio_nome, convenio_tipo, convenio_area, " +
                "convenio_coparticipacao, convenio_status " +
                "FROM convenio WHERE convenio_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Convenio(
                            rs.getInt("convenio_id"),
                            rs.getString("convenio_nome"),
                            rs.getString("convenio_tipo"),
                            rs.getString("convenio_area"),
                            rs.getString("convenio_coparticipacao"),
                            rs.getString("convenio_status")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar convênio por ID", e);
        }

        return null;
    }

    public boolean update(Convenio convenio) {
        String sql = "UPDATE convenio SET " +
                "convenio_nome = ?, convenio_tipo = ?, convenio_area = ?, " +
                "convenio_coparticipacao = ?, convenio_status = ? " +
                "WHERE convenio_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, convenio.getConvenioNome());
            stmt.setString(2, convenio.getConvenioTipo());
            stmt.setString(3, convenio.getConvenioArea());
            stmt.setString(4, convenio.getCoparticipacao());
            stmt.setString(5, convenio.getConvenioStatus());
            stmt.setInt(6, convenio.getIdconvenio());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar convênio", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM convenio WHERE convenio_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar convênio", e);
        }
    }
}
