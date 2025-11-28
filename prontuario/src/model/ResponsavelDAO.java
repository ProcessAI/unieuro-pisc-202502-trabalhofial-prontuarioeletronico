package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResponsavelDAO {

    /**
     * Insere um novo responsável.
     * @param responsavel
     * @return 
     */
    public boolean insert(Responsavel responsavel) {
        String sql = "INSERT INTO responsavel (" +
                "responsavel_nome, responsavel_cpf, responsavel_dtnascimento, " +
                "responsavel_telefone, responsavel_email, responsavel_endereco, " +
                "responsavel_parentesco, responsavel_observacoes" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, responsavel.getNome());
            stmt.setString(2, responsavel.getCpf());
            stmt.setDate(3, responsavel.getDataNascimento());
            stmt.setString(4, responsavel.getTelefone());
            stmt.setString(5, responsavel.getEmail());
            stmt.setString(6, responsavel.getEndereco());
            stmt.setString(7, responsavel.getParentesco());
            stmt.setString(8, responsavel.getObservacoes());

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        responsavel.setId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir responsável", e);
        }

        return false;
    }

    /**
     * Lista todos os responsáveis.
     * @return 
     */
    public List<Responsavel> findAll() {
        List<Responsavel> lista = new ArrayList<>();

        String sql = "SELECT responsavel_id, responsavel_nome, responsavel_cpf, responsavel_dtnascimento, " +
                "responsavel_telefone, responsavel_email, responsavel_endereco, " +
                "responsavel_parentesco, responsavel_observacoes " +
                "FROM responsavel ORDER BY responsavel_nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Responsavel r = new Responsavel();
                r.setId(rs.getInt("responsavel_id"));
                r.setNome(rs.getString("responsavel_nome"));
                r.setCpf(rs.getString("responsavel_cpf"));
                r.setDataNascimento(rs.getDate("responsavel_dtnascimento"));
                r.setTelefone(rs.getString("responsavel_telefone"));
                r.setEmail(rs.getString("responsavel_email"));
                r.setEndereco(rs.getString("responsavel_endereco"));
                r.setParentesco(rs.getString("responsavel_parentesco"));
                r.setObservacoes(rs.getString("responsavel_observacoes"));

                lista.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar responsáveis", e);
        }

        return lista;
    }

    /**
     * Busca por ID.
     * @param id
     * @return 
     */
    public Responsavel findById(int id) {
        String sql = "SELECT responsavel_id, responsavel_nome, responsavel_cpf, responsavel_dtnascimento, " +
                "responsavel_telefone, responsavel_email, responsavel_endereco, " +
                "responsavel_parentesco, responsavel_observacoes " +
                "FROM responsavel WHERE responsavel_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Responsavel r = new Responsavel();
                    r.setId(rs.getInt("responsavel_id"));
                    r.setNome(rs.getString("responsavel_nome"));
                    r.setCpf(rs.getString("responsavel_cpf"));
                    r.setDataNascimento(rs.getDate("responsavel_dtnascimento"));
                    r.setTelefone(rs.getString("responsavel_telefone"));
                    r.setEmail(rs.getString("responsavel_email"));
                    r.setEndereco(rs.getString("responsavel_endereco"));
                    r.setParentesco(rs.getString("responsavel_parentesco"));
                    r.setObservacoes(rs.getString("responsavel_observacoes"));
                    return r;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar responsável por ID", e);
        }

        return null;
    }

    /**
     * Atualiza um responsável.
     * @param responsavel
     * @return 
     */
    public boolean update(Responsavel responsavel) {
        String sql = "UPDATE responsavel SET " +
                "responsavel_nome = ?, responsavel_cpf = ?, responsavel_dtnascimento = ?, " +
                "responsavel_telefone = ?, responsavel_email = ?, responsavel_endereco = ?, " +
                "responsavel_parentesco = ?, responsavel_observacoes = ? " +
                "WHERE responsavel_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, responsavel.getNome());
            stmt.setString(2, responsavel.getCpf());
            stmt.setDate(3, responsavel.getDataNascimento());
            stmt.setString(4, responsavel.getTelefone());
            stmt.setString(5, responsavel.getEmail());
            stmt.setString(6, responsavel.getEndereco());
            stmt.setString(7, responsavel.getParentesco());
            stmt.setString(8, responsavel.getObservacoes());
            stmt.setInt(9, responsavel.getId());

            int linhas = stmt.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar responsável", e);
        }
    }

    /**
     * Exclui por ID.
     * @param id
     * @return 
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM responsavel WHERE responsavel_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar responsável", e);
        }
    }
}
