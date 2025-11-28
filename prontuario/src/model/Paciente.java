package model;

import java.sql.Date;

public class Paciente {

    // Campos da tabela public.paciente
    private int pacienteId;
    private String pacienteNome;
    private String pacienteTelefone;
    private String pacienteCpf;
    private Date pacienteDnas; // java.sql.Date
    private String pacienteEmail;
    private String pacienteEndereco;
    private String pacienteStatus;      // char(1)
    private String pacienteAlergia;
    private String pacienteSexo;        // char(1)
    private String pacienteNacionalidade;
    private String pacienteEstadocivil;

    // FKs
    private Integer responsavelId;      // FK para responsavel
    private Integer convenioId;         // FK para convenio

    // Campos auxiliares para exibição
    private String responsavelNome;     // JOIN com responsavel
    private String convenioNome;        // JOIN com convenio

    // ===============================
    // Construtores
    // ===============================

    public Paciente() {
    }

    // Construtor usado pelo PacienteDAO.findAll / findById (mesma assinatura que você já usava)
    public Paciente(int pacienteId,
                    String pacienteNome,
                    String pacienteTelefone,
                    String pacienteCpf,
                    Date pacienteDnas,
                    String pacienteEmail,
                    String pacienteEndereco,
                    String pacienteStatus,
                    String pacienteAlergia,
                    String pacienteSexo,
                    String pacienteNacionalidade,
                    String pacienteEstadocivil,
                    Integer convenioId) {

        this.pacienteId = pacienteId;
        this.pacienteNome = pacienteNome;
        this.pacienteTelefone = pacienteTelefone;
        this.pacienteCpf = pacienteCpf;
        this.pacienteDnas = pacienteDnas;
        this.pacienteEmail = pacienteEmail;
        this.pacienteEndereco = pacienteEndereco;
        this.pacienteStatus = pacienteStatus;
        this.pacienteAlergia = pacienteAlergia;
        this.pacienteSexo = pacienteSexo;
        this.pacienteNacionalidade = pacienteNacionalidade;
        this.pacienteEstadocivil = pacienteEstadocivil;
        this.convenioId = convenioId;
    }

    // Construtor sem ID (para INSERT)
    public Paciente(String pacienteNome,
                    String pacienteTelefone,
                    String pacienteCpf,
                    Date pacienteDnas,
                    String pacienteEmail,
                    String pacienteEndereco,
                    String pacienteStatus,
                    String pacienteAlergia,
                    String pacienteSexo,
                    String pacienteNacionalidade,
                    String pacienteEstadocivil,
                    Integer convenioId) {

        this.pacienteNome = pacienteNome;
        this.pacienteTelefone = pacienteTelefone;
        this.pacienteCpf = pacienteCpf;
        this.pacienteDnas = pacienteDnas;
        this.pacienteEmail = pacienteEmail;
        this.pacienteEndereco = pacienteEndereco;
        this.pacienteStatus = pacienteStatus;
        this.pacienteAlergia = pacienteAlergia;
        this.pacienteSexo = pacienteSexo;
        this.pacienteNacionalidade = pacienteNacionalidade;
        this.pacienteEstadocivil = pacienteEstadocivil;
        this.convenioId = convenioId;
    }

    // ===============================
    // Getters e Setters
    // ===============================

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getPacienteNome() {
        return pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }

    public String getPacienteTelefone() {
        return pacienteTelefone;
    }

    public void setPacienteTelefone(String pacienteTelefone) {
        this.pacienteTelefone = pacienteTelefone;
    }

    public String getPacienteCpf() {
        return pacienteCpf;
    }

    public void setPacienteCpf(String pacienteCpf) {
        this.pacienteCpf = pacienteCpf;
    }

    public Date getPacienteDnas() {
        return pacienteDnas;
    }

    public void setPacienteDnas(Date pacienteDnas) {
        this.pacienteDnas = pacienteDnas;
    }

    public String getPacienteEmail() {
        return pacienteEmail;
    }

    public void setPacienteEmail(String pacienteEmail) {
        this.pacienteEmail = pacienteEmail;
    }

    public String getPacienteEndereco() {
        return pacienteEndereco;
    }

    public void setPacienteEndereco(String pacienteEndereco) {
        this.pacienteEndereco = pacienteEndereco;
    }

    public String getPacienteStatus() {
        return pacienteStatus;
    }

    public void setPacienteStatus(String pacienteStatus) {
        this.pacienteStatus = pacienteStatus;
    }

    public String getPacienteAlergia() {
        return pacienteAlergia;
    }

    public void setPacienteAlergia(String pacienteAlergia) {
        this.pacienteAlergia = pacienteAlergia;
    }

    public String getPacienteSexo() {
        return pacienteSexo;
    }

    public void setPacienteSexo(String pacienteSexo) {
        this.pacienteSexo = pacienteSexo;
    }

    public String getPacienteNacionalidade() {
        return pacienteNacionalidade;
    }

    public void setPacienteNacionalidade(String pacienteNacionalidade) {
        this.pacienteNacionalidade = pacienteNacionalidade;
    }

    public String getPacienteEstadocivil() {
        return pacienteEstadocivil;
    }

    public void setPacienteEstadocivil(String pacienteEstadocivil) {
        this.pacienteEstadocivil = pacienteEstadocivil;
    }

    public Integer getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(Integer responsavelId) {
        this.responsavelId = responsavelId;
    }

    public Integer getConvenioId() {
        return convenioId;
    }

    public void setConvenioId(Integer convenioId) {
        this.convenioId = convenioId;
    }

    public String getResponsavelNome() {
        return responsavelNome;
    }

    public void setResponsavelNome(String responsavelNome) {
        this.responsavelNome = responsavelNome;
    }

    public String getConvenioNome() {
        return convenioNome;
    }

    public void setConvenioNome(String convenioNome) {
        this.convenioNome = convenioNome;
    }

    @Override
    public String toString() {
        return pacienteNome != null ? pacienteNome : ("Paciente #" + pacienteId);
    }
}
