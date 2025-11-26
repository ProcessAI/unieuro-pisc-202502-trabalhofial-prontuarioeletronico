package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicoDAO {

    // Método para SALVAR um novo médico no PostgreSQL
    public void salvar(Medico medico) throws SQLException {
        String sql = "INSERT INTO medicos (crm, nome, especialidade, telefone, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, medico.getCrm());
            stmt.setString(2, medico.getNome());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setString(4, medico.getTelefone());
            stmt.setString(5, medico.getEmail());
            
            stmt.executeUpdate();
        }
    }

    // Método para BUSCAR um médico pelo CRM
    public Medico buscarPorCrm(String crm) throws SQLException {
        String sql = "SELECT nome, especialidade, telefone, email FROM medicos WHERE crm = ?";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, crm);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Medico medico = new Medico();
                    medico.setCrm(crm); 
                    medico.setNome(rs.getString("nome"));
                    medico.setEspecialidade(rs.getString("especialidade"));
                    medico.setTelefone(rs.getString("telefone"));
                    medico.setEmail(rs.getString("email"));
                    return medico;
                }
                return null;
            }
        }
    }

    // Método para ATUALIZAR os dados de um médico
    public void atualizar(Medico medico) throws SQLException {
        String sql = "UPDATE medicos SET nome = ?, especialidade = ?, telefone = ?, email = ? WHERE crm = ?";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getEspecialidade());
            stmt.setString(3, medico.getTelefone());
            stmt.setString(4, medico.getEmail());
            stmt.setString(5, medico.getCrm()); // Onde o CRM é a chave
            
            stmt.executeUpdate();
        }
    }

    // Método para EXCLUIR um médico pelo CRM
    public void excluir(String crm) throws SQLException {
        String sql = "DELETE FROM medicos WHERE crm = ?";
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setString(1, crm);
            stmt.executeUpdate();
        }
    }
}