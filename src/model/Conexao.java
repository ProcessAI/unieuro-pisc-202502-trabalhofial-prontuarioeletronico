// Pasta: model
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    // Configurações para PostgreSQL (pgAdmin)
    private static final String URL = "jdbc:postgresql://localhost:5432/prontuario_db"; 
    private static final String USER = "postgres";
    private static final String PASSWORD = "sua_senha_aqui"; // <-- **ATUALIZE A SENHA**
    private static final String DRIVER = "org.postgresql.Driver";

    public static Connection getConnection() throws SQLException {
        try {
            // Carrega o driver
            Class.forName(DRIVER); 
            // Estabelece e retorna a conexão
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC do PostgreSQL não encontrado. Verifique se o JAR está no projeto.");
            throw new SQLException("Problema no Driver: " + e.getMessage());
        }
    }
}