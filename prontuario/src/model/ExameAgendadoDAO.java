package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExameAgendadoDAO {

    // Reutiliza a mesma checagem de conflito da ConsultaDAO,
    // mas aqui fazemos só p/ exames (já juntamos lá consulta+exame)
    public boolean existeConflitoHorario(int medicoId, Date data, Time hora) {
        // CORREÇÃO: Usando 'data' e 'hora' em vez de 'exame_data' e 'exame_hora'
        String sql =
                "SELECT COUNT(*) AS total " +
                        "FROM exame_agendado " +
                        "WHERE medico_id = ? AND data = ? AND hora = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, medicoId);
            stmt.setDate(2, data);
            stmt.setTime(3, hora);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar conflito de horário (exame)", e);
        }
        return false;
    }

    public boolean insert(ExameAgendado ex) {
        // CORREÇÃO: Usando 'data' e 'hora' em vez de 'exame_data' e 'exame_hora'
        String sql = "INSERT INTO exame_agendado " +
                "(data, hora, paciente_id, medico_id, exame_id, observacoes) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, ex.getData());
            stmt.setTime(2, ex.getHora());
            stmt.setInt(3, ex.getPacienteId());
            stmt.setInt(4, ex.getMedicoId());
            stmt.setInt(5, ex.getExameId());
            stmt.setString(6, ex.getObservacoes());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            // Este é o método que está falhando atualmente. Mantenha a exceção clara.
            throw new RuntimeException("Erro ao inserir exame agendado", e);
        }
    }

    public List<ExameAgendado> listAll() {
        List<ExameAgendado> lista = new ArrayList<>();
        // CORREÇÃO: Usando 'data' e 'hora' em vez de 'exame_data' e 'exame_hora'
        String sql = "SELECT * FROM exame_agendado ORDER BY data, hora";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ExameAgendado ex = new ExameAgendado(
                        rs.getInt("exame_agendado_id"),
                        // CORREÇÃO: Nomes de coluna
                        rs.getDate("data"),
                        rs.getTime("hora"),
                        rs.getInt("paciente_id"),
                        rs.getInt("medico_id"),
                        rs.getInt("exame_id"),
                        rs.getString("observacoes")
                );
                lista.add(ex);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os exames agendados", e);
        }
        return lista;
    }

    public List<ExameAgendado> listByMedico(int medicoId) {
        List<ExameAgendado> lista = new ArrayList<>();
        // CORREÇÃO: Usando 'data' e 'hora' em vez de 'exame_data' e 'exame_hora'
        String sql = "SELECT * FROM exame_agendado WHERE medico_id = ? ORDER BY data, hora";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, medicoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ExameAgendado ex = new ExameAgendado(
                            rs.getInt("exame_agendado_id"),
                            // CORREÇÃO: Nomes de coluna
                            rs.getDate("data"),
                            rs.getTime("hora"),
                            rs.getInt("paciente_id"),
                            rs.getInt("medico_id"),
                            rs.getInt("exame_id"),
                            rs.getString("observacoes")
                    );
                    lista.add(ex);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar exames agendados por médico", e);
        }
        return lista;
    }

    /**
     * Remove um exame agendado pelo ID. (NOVO)
     * @param id
     * @return 
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM exame_agendado WHERE exame_agendado_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar exame agendado", e);
        }
    }
}