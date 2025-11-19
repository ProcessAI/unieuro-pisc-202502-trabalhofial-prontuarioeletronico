package controller;

import model.Paciente;
import model.PacienteDAO;
import java.util.List;
import java.sql.Date; // Necessário para Paciente.java

public class ControllerPaciente {

    private PacienteDAO pacienteDAO;

    public ControllerPaciente() {
        this.pacienteDAO = new PacienteDAO();
    }

    /**
     * Gerencia a operação de cadastro de um novo paciente.
     */
    public boolean cadastrarPaciente(String nome, String telefone, String cpf, Date dnas, String email, String endereco, String status, String alergia, String sexo, String nacionalidade, String estadocivil) {

        // 1. Cria o objeto Paciente com os dados da View
        Paciente novoPaciente = new Paciente();
        novoPaciente.setPacienteNome(nome);
        novoPaciente.setPacienteTelefone(telefone);
        novoPaciente.setPacienteCpf(cpf);
        novoPaciente.setPacienteDnas(dnas);
        novoPaciente.setPacienteEmail(email);
        novoPaciente.setPacienteEndereco(endereco);
        novoPaciente.setPacienteStatus(status);
        novoPaciente.setPacienteAlergia(alergia);
        novoPaciente.setPacienteSexo(sexo);
        novoPaciente.setPacienteNacionalidade(nacionalidade);
        novoPaciente.setPacienteEstadocivil(estadocivil);

        // 2. Chama a camada DAO para persistir o objeto
        return pacienteDAO.insert(novoPaciente);
    }

    /**
     * Gerencia a listagem de todos os pacientes.
     */
    public List<Paciente> listarTodos() {
        return pacienteDAO.findAll();
    }

    /**
     * Gerencia a operação de edição de um paciente.
     */
    public boolean editarPaciente(int id, String nome, String telefone, String cpf, Date dnas, String email, String endereco, String status, String alergia, String sexo, String nacionalidade, String estadocivil) {

        // 1. Cria o objeto Paciente para a atualização
        Paciente pacienteAtualizado = new Paciente();
        pacienteAtualizado.setPacienteId(id); // ID é obrigatório para o UPDATE
        pacienteAtualizado.setPacienteNome(nome);
        pacienteAtualizado.setPacienteTelefone(telefone);
        pacienteAtualizado.setPacienteCpf(cpf);
        pacienteAtualizado.setPacienteDnas(dnas);
        pacienteAtualizado.setPacienteEmail(email);
        pacienteAtualizado.setPacienteEndereco(endereco);
        pacienteAtualizado.setPacienteStatus(status);
        pacienteAtualizado.setPacienteAlergia(alergia);
        pacienteAtualizado.setPacienteSexo(sexo);
        pacienteAtualizado.setPacienteNacionalidade(nacionalidade);
        pacienteAtualizado.setPacienteEstadocivil(estadocivil);

        // 2. Chama a camada DAO para atualizar o objeto
        return pacienteDAO.update(pacienteAtualizado);
    }

    /**
     * Gerencia a busca de um paciente pelo ID.
     */
    public Paciente buscarPorId(int id) {
        return pacienteDAO.findById(id);
    }

    /**
     * Gerencia a operação de exclusão de um paciente.
     */
    public boolean excluirPaciente(int id) {
        return pacienteDAO.delete(id);
    }

    // Você pode adicionar outras regras de negócio aqui (ex: validar CPF, formatar dados)
}