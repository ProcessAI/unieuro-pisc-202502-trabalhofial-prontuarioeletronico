package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConvenioDAO {

    public boolean cadastrar(Convenio convenio) {
        String sql = "INSERT INTO convenio (convenio_nome, convenio_tipo, convenio_area, convenio_coparticipacao) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, convenio.getConvenioNome());
            stmt.setString(2, convenio.getConvenioTipo());
            stmt.setString(3, convenio.getConvenioArea());
            stmt.setString(4, convenio.getCoparticipacao());

            stmt.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    public List<Convenio> listar() {
        String sql = "SELECT convenio_nome, convenio_tipo, convenio_area, convenio_coparticipacao FROM convenio";
        List<Convenio> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Convenio c = new Convenio(); // usa o construtor vazio
                c.setConvenioNome(rs.getString("convenio_nome"));
                c.setConvenioTipo(rs.getString("convenio_tipo"));
                c.setConvenioArea(rs.getString("convenio_area"));
                c.setCoparticipacao(rs.getString("convenio_coparticipacao"));

                lista.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar: " + e.getMessage());
        }

        return lista;
    }
}
