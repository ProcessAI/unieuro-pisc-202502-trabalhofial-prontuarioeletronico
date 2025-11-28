package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public boolean insert(Paciente paciente) {
        String sql = "INSERT INTO paciente (" +
                "paciente_nome, paciente_telefone, paciente_cpf, paciente_dnas, " +
                "paciente_email, paciente_endereco, paciente_status, paciente_alergia, " +
                "paciente_sexo, paciente_nacionalidade, paciente_estadocivil, " +
                "responsavel_id, convenio_id" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

            // responsavel_id
            if (paciente.getResponsavelId() != null) {
                stmt.setInt(12, paciente.getResponsavelId());
            } else {
                stmt.setNull(12, Types.INTEGER);
            }

            // convenio_id
            if (paciente.getConvenioId() != null) {
                stmt.setInt(13, paciente.getConvenioId());
            } else {
                stmt.setNull(13, Types.INTEGER);
            }

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir paciente", e);
        }
    }

    public List<Paciente> findAll() {
        List<Paciente> lista = new ArrayList<>();

        String sql = "SELECT " +
                "p.paciente_id, p.paciente_nome, p.paciente_telefone, p.paciente_cpf, p.paciente_dnas, " +
                "p.paciente_email, p.paciente_endereco, p.paciente_status, p.paciente_alergia, " +
                "p.paciente_sexo, p.paciente_nacionalidade, p.paciente_estadocivil, " +
                "p.responsavel_id, r.responsavel_nome, " +
                "p.convenio_id,   c.convenio_nome " +
                "FROM paciente p " +
                "LEFT JOIN responsavel r ON r.responsavel_id = p.responsavel_id " +
                "LEFT JOIN convenio   c ON c.convenio_id   = p.convenio_id " +
                "ORDER BY p.paciente_nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente(
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
                        rs.getString("paciente_estadocivil"),
                        (Integer) rs.getObject("convenio_id")
                );

                // extra: responsável e convênio (nomes + IDs)
                p.setResponsavelId((Integer) rs.getObject("responsavel_id"));
                p.setResponsavelNome(rs.getString("responsavel_nome"));
                p.setConvenioNome(rs.getString("convenio_nome"));

                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pacientes", e);
        }

        return lista;
    }

    public Paciente findById(int pacienteId) {
        String sql = "SELECT " +
                "p.paciente_id, p.paciente_nome, p.paciente_telefone, p.paciente_cpf, p.paciente_dnas, " +
                "p.paciente_email, p.paciente_endereco, p.paciente_status, p.paciente_alergia, " +
                "p.paciente_sexo, p.paciente_nacionalidade, p.paciente_estadocivil, " +
                "p.responsavel_id, r.responsavel_nome, " +
                "p.convenio_id,   c.convenio_nome " +
                "FROM paciente p " +
                "LEFT JOIN responsavel r ON r.responsavel_id = p.responsavel_id " +
                "LEFT JOIN convenio   c ON c.convenio_id   = p.convenio_id " +
                "WHERE p.paciente_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pacienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Paciente p = new Paciente(
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
                            rs.getString("paciente_estadocivil"),
                            (Integer) rs.getObject("convenio_id")
                    );

                    p.setResponsavelId((Integer) rs.getObject("responsavel_id"));
                    p.setResponsavelNome(rs.getString("responsavel_nome"));
                    p.setConvenioNome(rs.getString("convenio_nome"));
                    return p;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente por ID", e);
        }
        return null;
    }

    public boolean update(Paciente paciente) {
        String sql = "UPDATE paciente SET " +
                "paciente_nome = ?, paciente_telefone = ?, paciente_cpf = ?, paciente_dnas = ?, " +
                "paciente_email = ?, paciente_endereco = ?, paciente_status = ?, paciente_alergia = ?, " +
                "paciente_sexo = ?, paciente_nacionalidade = ?, paciente_estadocivil = ?, " +
                "responsavel_id = ?, convenio_id = ? " +
                "WHERE paciente_id = ?";

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

            if (paciente.getResponsavelId() != null) {
                stmt.setInt(12, paciente.getResponsavelId());
            } else {
                stmt.setNull(12, Types.INTEGER);
            }

            if (paciente.getConvenioId() != null) {
                stmt.setInt(13, paciente.getConvenioId());
            } else {
                stmt.setNull(13, Types.INTEGER);
            }

            stmt.setInt(14, paciente.getPacienteId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar paciente", e);
        }
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
