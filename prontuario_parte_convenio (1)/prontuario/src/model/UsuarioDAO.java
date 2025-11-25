/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author aluno
 */
public class UsuarioDAO {
    
    
    public Usuario getUsuarioByLoginMock(String login)
    {
        if(login.compareTo("rafael") ==0)
        {
            return new Usuario("rafael", "123");
        }
        return null;
    }
    
    public Usuario getUsuarioByLogin(String login) {
    String sql = "SELECT idusuario, login, senha FROM usuario WHERE login = ?";
    
    try (Connection conn = Conexao.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, login);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Usuario usuario = new Usuario(rs.getInt("idusuario"), rs.getString("login"), rs.getString("senha"));
                return usuario;
            }
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar usuário por login", e);
    }

        return null; // caso não encontre
    }
}
