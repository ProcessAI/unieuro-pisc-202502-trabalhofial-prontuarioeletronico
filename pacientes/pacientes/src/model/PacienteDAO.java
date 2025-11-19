package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    /**
     * C - Create: Insere um novo paciente no banco de dados.
     * @param paciente O objeto Paciente a ser inserido.
     * @return true se a inserção for bem-sucedida, false caso contrário.
     */
    public boolean insert(Paciente paciente) {
        String sql = "INSERT INTO paciente (paciente_nome, paciente_telefone, paciente_cpf, paciente_dnas, paciente_email, paciente_endereco, paciente_status, paciente_alergia, paciente_sexo, paciente_nacionalidade, paciente_estadocivil) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getPacienteNome());
            stmt.setString(2, paciente.getPacienteTelefone());
            stmt.setString(3, paciente.getPacienteCpf());
            stmt.setDate(4, paciente.getPacienteDnas()); // java.sql.Date
            stmt.setString(5, paciente.getPacienteEmail());
            stmt.setString(6, paciente.getPacienteEndereco());
            stmt.setString(7, paciente.getPacienteStatus());
            stmt.setString(8, paciente.getPacienteAlergia());
            stmt.setString(9, paciente.getPacienteSexo());
            stmt.setString(10, paciente.getPacienteNacionalidade());
            stmt.setString(11, paciente.getPacienteEstadocivil());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir paciente", e);
        }
    }

    /**
     * R - Read: Lista todos os pacientes cadastrados.
     * @return Uma lista de objetos Paciente.
     */
    public List<Paciente> findAll() {
        String sql = "SELECT * FROM paciente ORDER BY paciente_nome";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("paciente_id"),
                        rs.getString("paciente_nome"),
                        rs.getString("paciente_telefone"),
                        rs.getString("paciente_cpf"),
                        rs.getDate("paciente_dnas"), // java.sql.Date
                        rs.getString("paciente_email"),
                        rs.getString("paciente_endereco"),
                        rs.getString("paciente_status"),
                        rs.getString("paciente_alergia"),
                        rs.getString("paciente_sexo"),
                        rs.getString("paciente_nacionalidade"),
                        rs.getString("paciente_estadocivil")
                );
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pacientes", e);
        }
        return pacientes;
    }

    /**
     * U - Update: Atualiza um paciente existente no banco de dados.
     * @param paciente O objeto Paciente com os dados atualizados.
     * @return true se a atualização for bem-sucedida, false caso contrário.
     */
    public boolean update(Paciente paciente) {
        String sql = "UPDATE paciente SET paciente_nome = ?, paciente_telefone = ?, paciente_cpf = ?, paciente_dnas = ?, paciente_email = ?, paciente_endereco = ?, paciente_status = ?, paciente_alergia = ?, paciente_sexo = ?, paciente_nacionalidade = ?, paciente_estadocivil = ? WHERE paciente_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getPacienteNome());
            stmt.setString(2, paciente.getPacienteTelefone());
            stmt.setString(3, paciente.getPacienteCpf());
            stmt.setDate(4, paciente.getPacienteDnas());
            stmt.setString(5, paciente.getPacienteEmail());
            stmt.setString(6, paciente.getPacienteEndereco());
            stmt.setString(7, paciente.getPacienteStatus());
            stmt.setString(8, paciente.getPacienteAlergia());
            stmt.setString(9, paciente.getPacienteSexo());
            stmt.setString(10, paciente.getPacienteNacionalidade());
            stmt.setString(11, paciente.getPacienteEstadocivil());
            stmt.setInt(12, paciente.getPacienteId()); // A chave para o WHERE

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar paciente", e);
        }
    }

    /**
     * D - Delete: Remove um paciente do banco de dados pelo ID.
     * @param pacienteId O ID do paciente a ser removido.
     * @return true se a remoção for bem-sucedida, false caso contrário.
     */

    public Paciente findById(int id) {
        String sql = "SELECT * FROM paciente WHERE paciente_id = ?";
        Paciente paciente = null;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Se encontrar, constrói e retorna o objeto Paciente
                    paciente = new Paciente(
                            rs.getInt("paciente_id"),
                            rs.getString("paciente_nome"),
                            rs.getString("paciente_telefone"),
                            rs.getString("paciente_cpf"),
                            rs.getDate("paciente_dnas"),
                            rs.getString("paciente_email"),
                            rs.getString("paciente_endereco"),
                            rs.getString("paciente_status"),
                            rs.getString("paciente_alergia"),
                            rs.getString("paciente_sexo"),
                            rs.getString("paciente_nacionalidade"),
                            rs.getString("paciente_estadocivil")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente por ID", e);
        }
        return paciente;
    }

    public boolean delete(int pacienteId) {
        String sql = "DELETE FROM paciente WHERE paciente_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pacienteId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar paciente", e);
        }
    }
}