package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ConsultaDAO {

    // CORREÇÃO DEFINITIVA BASEADA NO DUMP DO BANCO DE DADOS: O nome real da tabela é 'agenda_consulta'.
    private static final String NOME_TABELA_CONSULTA = "agenda_consulta"; 

    /**
     * Insere uma nova consulta no banco de dados.
     * Colunas ajustadas: 'consulta_data' -> 'data', 'consulta_hora' -> 'hora', 'observacoes' -> 'descricao'.
     * @param c
     * @return 
     */
    public boolean insert(Consulta c) {
        String sql = "INSERT INTO " + NOME_TABELA_CONSULTA + " (data, hora, paciente_id, medico_id, descricao) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, c.getData());
            stmt.setTime(2, c.getHora());
            stmt.setInt(3, c.getPacienteId());
            stmt.setInt(4, c.getMedicoId());
            stmt.setString(5, c.getObservacoes()); // getObservacoes() é mapeado para a coluna 'descricao'

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir consulta", e);
        }
    }

    /**
     * Checa sobreposição de horários (overlap) para Consultas.
     * Colunas ajustadas: 'consulta_data' -> 'data', 'consulta_hora' -> 'hora'.
     * @param medicoId
     * @param data
     * @param hora
     * @param duracaoMinutos - Duração do NOVO agendamento (Exame ou Consulta) em minutos.
     * @return 
     */
    public boolean existeConflitoHorario(int medicoId, Date data, Time hora, int duracaoMinutos) {
        // Checa se o novo agendamento (com sua duração) se sobrepõe a uma consulta existente (duração fixa de 30 minutos).
        String sqlConsulta =
                "SELECT COUNT(*) AS total " +
                        "FROM " + NOME_TABELA_CONSULTA + " " +
                        "WHERE medico_id = ? AND data = ? AND " + // Nomes de coluna corrigidos aqui
                        "    (hora, INTERVAL '1 minute' * 30) OVERLAPS (CAST(? AS time), INTERVAL '1 minute' * ?)"; // Nomes de coluna corrigidos aqui

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmtConsulta = conn.prepareStatement(sqlConsulta)) {

            stmtConsulta.setInt(1, medicoId);
            stmtConsulta.setDate(2, data);
            stmtConsulta.setTime(3, hora);
            stmtConsulta.setInt(4, duracaoMinutos); 

            try (ResultSet rs = stmtConsulta.executeQuery()) {
                if (rs.next() && rs.getInt("total") > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar conflito de horário (consulta)", e);
        }
        
        return false;
    }


    /**
     * Busca uma consulta pelo ID.
     * Colunas ajustadas: 'consulta_data' -> 'data', 'consulta_hora' -> 'hora', 'observacoes' -> 'descricao'.
     * @param id
     * @return 
     */
    public Consulta findById(int id) {
        String sql = "SELECT * FROM " + NOME_TABELA_CONSULTA + " WHERE consulta_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Consulta(
                            rs.getInt("consulta_id"),
                            rs.getDate("data"), // Coluna corrigida
                            rs.getTime("hora"), // Coluna corrigida
                            rs.getInt("paciente_id"),
                            rs.getInt("medico_id"),
                            rs.getString("descricao") // Coluna corrigida
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consulta por ID", e);
        }
        return null;
    }

    /**
     * Lista todas as consultas futuras para um determinado médico.
     * Colunas ajustadas: 'consulta_data' -> 'data', 'consulta_hora' -> 'hora', 'observacoes' -> 'descricao'.
     * @param medicoId
     * @return 
     */
    public List<Consulta> listByMedico(int medicoId) {
        List<Consulta> lista = new ArrayList<>();
        // Colunas ajustadas na query: 'consulta_data' -> 'data', 'consulta_hora' -> 'hora'
        String sql = "SELECT * FROM " + NOME_TABELA_CONSULTA + " WHERE medico_id = ? AND data >= CURRENT_DATE ORDER BY data, hora";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, medicoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Consulta c = new Consulta(
                            rs.getInt("consulta_id"),
                            rs.getDate("data"), // Coluna corrigida
                            rs.getTime("hora"), // Coluna corrigida
                            rs.getInt("paciente_id"),
                            rs.getInt("medico_id"),
                            rs.getString("descricao") // Coluna corrigida
                    );
                    lista.add(c);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas por médico", e);
        }
        return lista;
    }

    /**
     * Remove uma consulta agendada pelo ID.
     * @param id
     * @return 
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM " + NOME_TABELA_CONSULTA + " WHERE consulta_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar consulta", e);
        }
    }

    /**
     * Atualiza uma consulta agendada.
     * Colunas ajustadas: 'consulta_data' -> 'data', 'consulta_hora' -> 'hora', 'observacoes' -> 'descricao'.
     * @param c
     * @return 
     */
    public boolean update(Consulta c) {
        String sql = "UPDATE " + NOME_TABELA_CONSULTA + " SET data = ?, hora = ?, paciente_id = ?, medico_id = ?, descricao = ? WHERE consulta_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, c.getData());
            stmt.setTime(2, c.getHora());
            stmt.setInt(3, c.getPacienteId());
            stmt.setInt(4, c.getMedicoId());
            stmt.setString(5, c.getObservacoes()); // getObservacoes() é mapeado para a coluna 'descricao'
            stmt.setInt(6, c.getConsultaId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar consulta", e);
        }
    }
}