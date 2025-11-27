// Pasta: model
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EspecialidadeDAO {

    // CREATE
    public void salvar(Especialidade especialidade) throws SQLException {
        String sql = "INSERT INTO especialidade (especialidade_nome, especialidade_status, especialidade_cbo, especialidade_escala, especialidade_descricao) VALUES (?, ?, ?, ?, ?)";
        
        // Uso do try-with-resources garante que a conexão e o PreparedStatement serão fechados
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, especialidade.getNome());
            stmt.setString(2, especialidade.getStatus());
            stmt.setString(3, especialidade.getCbo());
            stmt.setString(4, especialidade.getEscala());
            stmt.setString(5, especialidade.getDescricao());

            stmt.executeUpdate();
        }
    }

    // READ (Busca por ID)
    public Especialidade buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM especialidade WHERE especialidade_id = ?";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEspecialidade(rs);
                }
                return null; // Não encontrou
            }
        }
    }
    
    // UPDATE
    public void atualizar(Especialidade especialidade) throws SQLException {
        String sql = "UPDATE especialidade SET especialidade_nome = ?, especialidade_status = ?, especialidade_cbo = ?, especialidade_escala = ?, especialidade_descricao = ? WHERE especialidade_id = ?";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, especialidade.getNome());
            stmt.setString(2, especialidade.getStatus());
            stmt.setString(3, especialidade.getCbo());
            stmt.setString(4, especialidade.getEscala());
            stmt.setString(5, especialidade.getDescricao());
            stmt.setInt(6, especialidade.getId()); 

            stmt.executeUpdate();
        }
    }

    // DELETE
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM especialidade WHERE especialidade_id = ?";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    // Método auxiliar para mapear o ResultSet para o objeto Especialidade
    private Especialidade mapearEspecialidade(ResultSet rs) throws SQLException {
        int id = rs.getInt("especialidade_id");
        String nome = rs.getString("especialidade_nome");
        String status = rs.getString("especialidade_status");
        String cbo = rs.getString("especialidade_cbo");
        String escala = rs.getString("especialidade_escala");
        String descricao = rs.getString("especialidade_descricao");
        
        return new Especialidade(id, nome, status, cbo, escala, descricao);
    }
}