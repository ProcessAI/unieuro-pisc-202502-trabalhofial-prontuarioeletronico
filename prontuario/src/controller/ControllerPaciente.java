package controller;

import model.Paciente;
import model.PacienteDAO;

import java.sql.Date;
import java.util.List;

public class ControllerPaciente {

    private final PacienteDAO pacienteDAO;

    public ControllerPaciente() {
        this.pacienteDAO = new PacienteDAO();
    }

    /**
     * Cadastro de novo paciente.
     * @param nome
     * @param telefone
     * @param estadocivil
     * @param dnas
     * @param cpf
     * @param convenioId
     * @param endereco
     * @param alergia
     * @param email
     * @param status
     * @param sexo
     * @param nacionalidade
     * @return 
     */
    public boolean cadastrarPaciente(String nome, String telefone, String cpf, Date dnas,
                                     String email, String endereco, String status,
                                     String alergia, String sexo, String nacionalidade,
                                     String estadocivil, Integer convenioId) {

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
        novoPaciente.setConvenioId(convenioId);

        return pacienteDAO.insert(novoPaciente);
    }

    /**
     * Lista todos os pacientes.
     * @return 
     */
    public List<Paciente> listarTodos() {
        return pacienteDAO.findAll();
    }

    /**
     * Edição de paciente.
     * @param id
     * @param nome
     * @param telefone
     * @param cpf
     * @param dnas
     * @param email
     * @param endereco
     * @param status
     * @param alergia
     * @param sexo
     * @param nacionalidade
     * @param estadocivil
     * @param convenioId
     * @return 
     */
    public boolean editarPaciente(int id, String nome, String telefone, String cpf, Date dnas,
                                  String email, String endereco, String status,
                                  String alergia, String sexo, String nacionalidade,
                                  String estadocivil, Integer convenioId) {

        Paciente pacienteAtualizado = new Paciente();
        pacienteAtualizado.setPacienteId(id);
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
        pacienteAtualizado.setConvenioId(convenioId);

        return pacienteDAO.update(pacienteAtualizado);
    }

    /**
     * Busca por ID.
     * @param id
     * @return 
     */
    public Paciente buscarPorId(int id) {
        return pacienteDAO.findById(id);
    }

    /**
     * Exclusão por ID.
     * @param id
     * @return 
     */
    public boolean excluirPaciente(int id) {
        return pacienteDAO.delete(id);
    }
}
