package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public boolean insert(Funcionario f) {
        String sql = "INSERT INTO funcionario (" +
                "funcionario_nome, funcionario_funcao, funcionario_cpf, funcionario_status, " +
                "funcionario_email, funcionario_telefone, funcionario_sexo, " +
                "funcionario_dtnascimento, funcionario_endereco" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, f.getFuncionarioNome());
            stmt.setString(2, f.getFuncionarioFuncao());
            stmt.setString(3, f.getFuncionarioCpf());
            stmt.setString(4, f.getFuncionarioStatus());
            stmt.setString(5, f.getFuncionarioEmail());
            stmt.setString(6, f.getFuncionarioTelefone());
            stmt.setString(7, f.getFuncionarioSexo());
            stmt.setDate(8, f.getFuncionarioDtnascimento());
            stmt.setString(9, f.getFuncionarioEndereco());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        f.setFuncionarioId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir funcionário", e);
        }

        return false;
    }

    public List<Funcionario> findAll() {
        List<Funcionario> lista = new ArrayList<>();

        String sql = "SELECT funcionario_id, funcionario_nome, funcionario_funcao, funcionario_cpf, " +
                "funcionario_status, funcionario_email, funcionario_telefone, funcionario_sexo, " +
                "funcionario_dtnascimento, funcionario_endereco " +
                "FROM funcionario ORDER BY funcionario_nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario f = new Funcionario(
                        rs.getInt("funcionario_id"),
                        rs.getString("funcionario_nome"),
                        rs.getString("funcionario_funcao"),
                        rs.getString("funcionario_cpf"),
                        rs.getString("funcionario_status"),
                        rs.getString("funcionario_email"),
                        rs.getString("funcionario_telefone"),
                        rs.getString("funcionario_sexo"),
                        rs.getDate("funcionario_dtnascimento"),
                        rs.getString("funcionario_endereco")
                );
                lista.add(f);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários", e);
        }

        return lista;
    }

    public Funcionario findById(int id) {
        String sql = "SELECT funcionario_id, funcionario_nome, funcionario_funcao, funcionario_cpf, " +
                "funcionario_status, funcionario_email, funcionario_telefone, funcionario_sexo, " +
                "funcionario_dtnascimento, funcionario_endereco " +
                "FROM funcionario WHERE funcionario_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                            rs.getInt("funcionario_id"),
                            rs.getString("funcionario_nome"),
                            rs.getString("funcionario_funcao"),
                            rs.getString("funcionario_cpf"),
                            rs.getString("funcionario_status"),
                            rs.getString("funcionario_email"),
                            rs.getString("funcionario_telefone"),
                            rs.getString("funcionario_sexo"),
                            rs.getDate("funcionario_dtnascimento"),
                            rs.getString("funcionario_endereco")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário por ID", e);
        }

        return null;
    }

    public boolean update(Funcionario f) {
        String sql = "UPDATE funcionario SET " +
                "funcionario_nome = ?, funcionario_funcao = ?, funcionario_cpf = ?, " +
                "funcionario_status = ?, funcionario_email = ?, funcionario_telefone = ?, " +
                "funcionario_sexo = ?, funcionario_dtnascimento = ?, funcionario_endereco = ? " +
                "WHERE funcionario_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getFuncionarioNome());
            stmt.setString(2, f.getFuncionarioFuncao());
            stmt.setString(3, f.getFuncionarioCpf());
            stmt.setString(4, f.getFuncionarioStatus());
            stmt.setString(5, f.getFuncionarioEmail());
            stmt.setString(6, f.getFuncionarioTelefone());
            stmt.setString(7, f.getFuncionarioSexo());
            stmt.setDate(8, f.getFuncionarioDtnascimento());
            stmt.setString(9, f.getFuncionarioEndereco());
            stmt.setInt(10, f.getFuncionarioId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM funcionario WHERE funcionario_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar funcionário", e);
        }
    }

    /**
     * Autentica o funcionário verificando se o Email e CPF correspondem a um registro ativo.
     * @param email O email do funcionário (Login)
     * @param cpf O CPF do funcionário (Senha)
     * @return true se as credenciais forem válidas
     */
    public boolean autenticar(String email, String cpf) {
        // Verifica email e senha (cpf), e também se o status é 'A' (Ativo)
        String sql = "SELECT funcionario_id FROM funcionario WHERE funcionario_email = ? AND funcionario_cpf = ? AND funcionario_status = 'A'";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                // Se retornar alguma linha, o login é válido
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar funcionário", e);
        }
    }
}