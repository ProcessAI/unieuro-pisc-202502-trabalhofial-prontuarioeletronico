package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
   
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"; 
    
    private static final String USUARIO = "postgres";
    private static final String SENHA = "123456"; 

    public static Connection getConnection() { 
        try {
         
            Class.forName("org.postgresql.Driver"); 
         
            return DriverManager.getConnection(URL, USUARIO, SENHA);
            
        } catch (SQLException e) {
           
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        } catch (ClassNotFoundException e) {
       
            System.err.println("Driver do PostgreSQL não encontrado.");
            throw new RuntimeException("Driver do PostgreSQL não encontrado.", e);
        }
    }
}