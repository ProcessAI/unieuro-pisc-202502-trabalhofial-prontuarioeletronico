package model;

import java.sql.*;
import java.util.ArrayList;

public class FuncionarioDAO {

    public boolean inserir(Funcionario f) {
        String sql = "INSERT INTO funcionario (funcionario_nome, funcionario_funcao, funcionario_cpf, funcionario_status, funcionario_email, funcionario_telefone, funcionario_sexo, funcionario_dtnascimento, funcionario_endereco) VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, f.getNome());
            ps.setString(2, f.getFuncao());
            ps.setString(3, f.getCpf());
            ps.setString(4, String.valueOf(f.getStatus()));
            ps.setString(5, f.getEmail());
            ps.setString(6, f.getTelefone());
            ps.setString(7, String.valueOf(f.getSexo()));
            ps.setDate(8, f.getDtnascimento());
            ps.setString(9, f.getEndereco());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Erro inserir: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca um funcionário pelo seu ID.
     */
    public Funcionario buscarPorId(int id) {
        String sql = "SELECT * FROM funcionario WHERE funcionario_id = ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }

        } catch (Exception e) {
            System.out.println("Erro buscar por ID: " + e.getMessage());
        }

        return null;
    }

    /**
     * Atualiza os dados de um funcionário existente.
     */
    public boolean editar(Funcionario f) {
        String sql = "UPDATE funcionario SET funcionario_nome = ?, funcionario_funcao = ?, funcionario_cpf = ?, funcionario_status = ?, funcionario_email = ?, funcionario_telefone = ?, funcionario_sexo = ?, funcionario_dtnascimento = ?, funcionario_endereco = ? WHERE funcionario_id = ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, f.getNome());
            ps.setString(2, f.getFuncao());
            ps.setString(3, f.getCpf());
            ps.setString(4, String.valueOf(f.getStatus()));
            ps.setString(5, f.getEmail());
            ps.setString(6, f.getTelefone());
            ps.setString(7, String.valueOf(f.getSexo()));
            ps.setDate(8, f.getDtnascimento());
            ps.setString(9, f.getEndereco());
            ps.setInt(10, f.getId()); // Onde o ID é usado para o WHERE

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Erro editar: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Exclui um funcionário pelo seu ID.
     */
    public boolean excluir(int id) {
        String sql = "DELETE FROM funcionario WHERE funcionario_id = ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Erro excluir: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Funcionario> listarAtivos() {
        ArrayList<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario WHERE funcionario_status = 'a' ORDER BY funcionario_id";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(map(rs));

        } catch (Exception e) {
            System.out.println("Erro listar ativos: " + e.getMessage());
        }

        return lista;
    }

    public ArrayList<Funcionario> listarTodos() {
        ArrayList<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario ORDER BY funcionario_id";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(map(rs));

        } catch (Exception e) {
            System.out.println("Erro listar todos: " + e.getMessage());
        }

        return lista;
    }

    private Funcionario map(ResultSet rs) throws Exception {
        return new Funcionario(
                rs.getInt("funcionario_id"),
                rs.getString("funcionario_nome"),
                rs.getString("funcionario_funcao"),
                rs.getString("funcionario_cpf"),
                rs.getString("funcionario_status").charAt(0),
                rs.getString("funcionario_email"),
                rs.getString("funcionario_telefone"),
                rs.getString("funcionario_sexo").charAt(0),
                rs.getDate("funcionario_dtnascimento"),
                rs.getString("funcionario_endereco")
        );
    }
}