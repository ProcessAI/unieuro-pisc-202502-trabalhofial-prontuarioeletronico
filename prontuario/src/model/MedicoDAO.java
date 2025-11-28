package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    public boolean insert(Medico medico) {
        String sql = "INSERT INTO medico (" +
                "medico_nome, medico_crm, medico_cpf, medico_telefone, medico_email, " +
                "medico_dtnascimento, medico_status, medico_sexo, medico_endereco, especialidade_id" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, medico.getMedicoNome());
            stmt.setString(2, medico.getMedicoCrm());
            stmt.setString(3, medico.getMedicoCpf());
            stmt.setString(4, medico.getMedicoTelefone());
            stmt.setString(5, medico.getMedicoEmail());
            stmt.setDate(6, medico.getMedicoDtnascimento());
            stmt.setString(7, medico.getMedicoStatus());
            stmt.setString(8, medico.getMedicoSexo());
            stmt.setString(9, medico.getMedicoEndereco());
            if (medico.getEspecialidadeId() != null) {
                stmt.setInt(10, medico.getEspecialidadeId());
            } else {
                stmt.setNull(10, Types.INTEGER);
            }

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        medico.setMedicoId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir médico", e);
        }

        return false;
    }

    public List<Medico> findAll() {
        List<Medico> lista = new ArrayList<>();

        String sql = "SELECT medico_id, medico_nome, medico_crm, medico_cpf, " +
                "medico_telefone, medico_email, medico_dtnascimento, " +
                "medico_status, medico_sexo, medico_endereco, especialidade_id " +
                "FROM medico ORDER BY medico_nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medico m = new Medico(
                        rs.getInt("medico_id"),
                        rs.getString("medico_nome"),
                        rs.getString("medico_crm"),
                        rs.getString("medico_cpf"),
                        rs.getString("medico_telefone"),
                        rs.getString("medico_email"),
                        rs.getDate("medico_dtnascimento"),
                        rs.getString("medico_status"),
                        rs.getString("medico_sexo"),
                        rs.getString("medico_endereco"),
                        (Integer) rs.getObject("especialidade_id")
                );
                lista.add(m);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar médicos", e);
        }

        return lista;
    }

    /**
     * Lista todos os médicos com status 'A' (Ativo) para a combo de agendamento.
     * @return 
     */
    public List<Medico> findAllActive() {
        List<Medico> lista = new ArrayList<>();

        String sql = "SELECT medico_id, medico_nome, medico_crm, medico_cpf, " +
                "medico_telefone, medico_email, medico_dtnascimento, " +
                "medico_status, medico_sexo, medico_endereco, especialidade_id " +
                "FROM medico WHERE medico_status = 'A' ORDER BY medico_nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medico m = new Medico(
                        rs.getInt("medico_id"),
                        rs.getString("medico_nome"),
                        rs.getString("medico_crm"),
                        rs.getString("medico_cpf"),
                        rs.getString("medico_telefone"),
                        rs.getString("medico_email"),
                        rs.getDate("medico_dtnascimento"),
                        rs.getString("medico_status"),
                        rs.getString("medico_sexo"),
                        rs.getString("medico_endereco"),
                        (Integer) rs.getObject("especialidade_id")
                );
                lista.add(m);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar médicos ativos", e);
        }

        return lista;
    }


    public Medico findById(int id) {
        String sql = "SELECT medico_id, medico_nome, medico_crm, medico_cpf, " +
                "medico_telefone, medico_email, medico_dtnascimento, " +
                "medico_status, medico_sexo, medico_endereco, especialidade_id " +
                "FROM medico WHERE medico_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Medico(
                            rs.getInt("medico_id"),
                            rs.getString("medico_nome"),
                            rs.getString("medico_crm"),
                            rs.getString("medico_cpf"),
                            rs.getString("medico_telefone"),
                            rs.getString("medico_email"),
                            rs.getDate("medico_dtnascimento"),
                            rs.getString("medico_status"),
                            rs.getString("medico_sexo"),
                            rs.getString("medico_endereco"),
                            (Integer) rs.getObject("especialidade_id")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar médico por ID", e);
        }

        return null;
    }

    public boolean update(Medico medico) {
        String sql = "UPDATE medico SET " +
                "medico_nome = ?, medico_crm = ?, medico_cpf = ?, " +
                "medico_telefone = ?, medico_email = ?, medico_dtnascimento = ?, " +
                "medico_status = ?, medico_sexo = ?, medico_endereco = ?, especialidade_id = ? " +
                "WHERE medico_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medico.getMedicoNome());
            stmt.setString(2, medico.getMedicoCrm());
            stmt.setString(3, medico.getMedicoCpf());
            stmt.setString(4, medico.getMedicoTelefone());
            stmt.setString(5, medico.getMedicoEmail());
            stmt.setDate(6, medico.getMedicoDtnascimento());
            stmt.setString(7, medico.getMedicoStatus());
            stmt.setString(8, medico.getMedicoSexo());
            stmt.setString(9, medico.getMedicoEndereco());
            if (medico.getEspecialidadeId() != null) {
                stmt.setInt(10, medico.getEspecialidadeId());
            } else {
                stmt.setNull(10, Types.INTEGER);
            }
            stmt.setInt(11, medico.getMedicoId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar médico", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM medico WHERE medico_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar médico", e);
        }
    }
}