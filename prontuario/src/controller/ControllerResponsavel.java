package controller;

import model.Responsavel;
import model.ResponsavelDAO;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ControllerResponsavel {

    private final ResponsavelDAO responsavelDAO;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ControllerResponsavel() {
        this.responsavelDAO = new ResponsavelDAO();
    }

    // ---------- Versão usada pela nova tela (data já em java.sql.Date) ----------
    public boolean cadastrarResponsavel(String nome, String cpf, Date dnas,
                                        String telefone, String email, String endereco,
                                        String parentesco, String observacoes) {

        Responsavel r = new Responsavel();
        r.setNome(nome);
        r.setCpf(cpf);
        r.setDataNascimento(dnas);
        r.setTelefone(telefone);
        r.setEmail(email);
        r.setEndereco(endereco);
        r.setParentesco(parentesco);
        r.setObservacoes(observacoes);

        return responsavelDAO.insert(r);
    }

    // ---------- Versão antiga (data como String) - mantém compatibilidade ----------
    public boolean cadastrarResponsavel(String nome, String cpf, String dnas,
                                        String telefone, String email, String endereco,
                                        String parentesco, String observacoes, boolean dummy) {
        try {
            LocalDate localDate = LocalDate.parse(dnas, DATE_FORMATTER);
            Date sqlDate = Date.valueOf(localDate);
            return cadastrarResponsavel(nome, cpf, sqlDate, telefone, email, endereco, parentesco, observacoes);
        } catch (Exception e) {
            System.err.println("Erro de formato de data: " + e.getMessage());
            return false;
        }
    }

    // ---------- Leitura / lista ----------
    public List<Responsavel> listarTodos() {
        return responsavelDAO.findAll();
    }

    public Responsavel buscarPorId(int id) {
        return responsavelDAO.findById(id);
    }

    // ---------- Atualização ----------
    public boolean atualizarResponsavel(int id, String nome, String cpf, Date dnas,
                                        String telefone, String email, String endereco,
                                        String parentesco, String observacoes) {

        Responsavel r = new Responsavel();
        r.setId(id);
        r.setNome(nome);
        r.setCpf(cpf);
        r.setDataNascimento(dnas);
        r.setTelefone(telefone);
        r.setEmail(email);
        r.setEndereco(endereco);
        r.setParentesco(parentesco);
        r.setObservacoes(observacoes);

        return responsavelDAO.update(r);
    }

    // ---------- Exclusão ----------
    public boolean excluirResponsavel(int id) {
        return responsavelDAO.delete(id);
    }
}
