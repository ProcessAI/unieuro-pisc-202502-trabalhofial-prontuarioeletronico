package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.sql.Time;

public class ExameDataAcessObject {

    public void inserir(Exame e) {
        String sql = "INSERT INTO exame (exame_id, exame_nome, exame_tipo, exame_status, exame_orientacao, exame_horai, exame_horaf) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, e.getExame_id());
            stmt.setString(2, e.getExame_nome());
            stmt.setString(3, e.getExame_tipo());
            stmt.setString(4, e.getExame_status());
            stmt.setString(5, e.getExame_orientacao());

            LocalTime localTimeI = LocalTime.parse(e.getExame_horai());
            Time sqlTimeI = Time.valueOf(localTimeI);
            stmt.setTime(6, sqlTimeI);

            LocalTime localTimeF = LocalTime.parse(e.getExame_horaf());
            Time sqlTimeF = Time.valueOf(localTimeF);
            stmt.setTime(7, sqlTimeF);

            stmt.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Erro ao inserir exame: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<Exame> listar() {
        List<Exame> lista = new ArrayList<>();
        String sql = "SELECT * FROM exame";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Exame e = new Exame();
                e.setExame_id(rs.getInt("exame_id"));
                e.setExame_nome(rs.getString("exame_nome"));
                e.setExame_tipo(rs.getString("exame_tipo"));
                e.setExame_status(rs.getString("exame_status"));
                e.setExame_orientacao(rs.getString("exame_orientacao"));

                // CORREÇÃO: Conversão de java.sql.Time para String
                Time sqlTimeI = rs.getTime("exame_horai");
                e.setExame_horai(sqlTimeI.toLocalTime().toString());

                Time sqlTimeF = rs.getTime("exame_horaf");
                e.setExame_horaf(sqlTimeF.toLocalTime().toString());

                lista.add(e);
            }

        } catch (Exception ex) {
            System.err.println("Erro ao listar exames: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public void atualizar(Exame e) {
        String sql = "UPDATE exame SET exame_nome=?, exame_tipo=?, exame_status=?, exame_orientacao=?, exame_horai=?, exame_horaf=? "
                + "WHERE exame_id=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getExame_nome());
            stmt.setString(2, e.getExame_tipo());
            stmt.setString(3, e.getExame_status());
            stmt.setString(4, e.getExame_orientacao());

            LocalTime localTimeI = LocalTime.parse(e.getExame_horai());
            Time sqlTimeI = Time.valueOf(localTimeI);
            stmt.setTime(5, sqlTimeI);

            LocalTime localTimeF = LocalTime.parse(e.getExame_horaf());
            Time sqlTimeF = Time.valueOf(localTimeF);
            stmt.setTime(6, sqlTimeF);

            stmt.setInt(7, e.getExame_id());

            stmt.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Erro ao atualizar exame: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM exame WHERE exame_id=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Erro ao deletar exame: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}