package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/prontuario";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456789";

    static {
        try {
            // Força o carregamento do driver (ajuda em alguns ambientes)
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL não encontrado no classpath!");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
