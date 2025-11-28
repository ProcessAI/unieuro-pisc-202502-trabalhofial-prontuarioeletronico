package controller;

import model.Medico;
import model.MedicoDAO;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MedicoController {

    private final MedicoDAO medicoDAO;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public MedicoController() {
        this.medicoDAO = new MedicoDAO();
    }

    public boolean cadastrarMedico(String nome, String crm, String cpf,
                                   String telefone, String email, String dataNascStr,
                                   String status, String sexo, String endereco,
                                   Integer especialidadeId) {

        if (nome == null || nome.trim().isEmpty()) return false;
        if (crm == null || crm.trim().isEmpty()) return false;

        try {
            LocalDate ld = LocalDate.parse(dataNascStr, FORMATTER);
            Date sqlDate = Date.valueOf(ld);

            Medico medico = new Medico(
                    nome,
                    crm,
                    cpf,
                    telefone,
                    email,
                    sqlDate,
                    status,
                    sexo,
                    endereco,
                    especialidadeId
            );

            return medicoDAO.insert(medico);

        } catch (Exception e) {
            System.err.println("Erro ao cadastrar médico: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarMedico(int id, String nome, String crm, String cpf,
                                   String telefone, String email, String dataNascStr,
                                   String status, String sexo, String endereco,
                                   Integer especialidadeId) {

        try {
            LocalDate ld = LocalDate.parse(dataNascStr, FORMATTER);
            Date sqlDate = Date.valueOf(ld);

            Medico medico = new Medico(
                    id,
                    nome,
                    crm,
                    cpf,
                    telefone,
                    email,
                    sqlDate,
                    status,
                    sexo,
                    endereco,
                    especialidadeId
            );

            return medicoDAO.update(medico);

        } catch (Exception e) {
            System.err.println("Erro ao atualizar médico: " + e.getMessage());
            return false;
        }
    }

    public List<Medico> listarTodos() {
        return medicoDAO.findAll();
    }

    public Medico buscarPorId(int id) {
        return medicoDAO.findById(id);
    }

    public boolean excluirMedico(int id) {
        return medicoDAO.delete(id);
    }
}
