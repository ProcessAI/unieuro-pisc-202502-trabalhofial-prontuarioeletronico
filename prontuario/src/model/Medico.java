package model;

import java.sql.Date;

public class Medico {

    private int medicoId;
    private String medicoNome;
    private String medicoCrm;
    private String medicoCpf;
    private String medicoTelefone;
    private String medicoEmail;
    private Date   medicoDtnascimento;
    private String medicoStatus;   // A/I
    private String medicoSexo;     // M/F
    private String medicoEndereco;
    private Integer especialidadeId; // FK para especialidade

    public Medico() {
    }

    // 🔹 Construtor COMPLETO (com ID)
    public Medico(int medicoId, String medicoNome, String medicoCrm, String medicoCpf,
                  String medicoTelefone, String medicoEmail, Date medicoDtnascimento,
                  String medicoStatus, String medicoSexo, String medicoEndereco,
                  Integer especialidadeId) {
        this.medicoId = medicoId;
        this.medicoNome = medicoNome;
        this.medicoCrm = medicoCrm;
        this.medicoCpf = medicoCpf;
        this.medicoTelefone = medicoTelefone;
        this.medicoEmail = medicoEmail;
        this.medicoDtnascimento = medicoDtnascimento;
        this.medicoStatus = medicoStatus;
        this.medicoSexo = medicoSexo;
        this.medicoEndereco = medicoEndereco;
        this.especialidadeId = especialidadeId;
    }

    // 🔹 Construtor SEM ID (com especialidade)
    public Medico(String medicoNome, String medicoCrm, String medicoCpf,
                  String medicoTelefone, String medicoEmail, Date medicoDtnascimento,
                  String medicoStatus, String medicoSexo, String medicoEndereco,
                  Integer especialidadeId) {
        this.medicoNome = medicoNome;
        this.medicoCrm = medicoCrm;
        this.medicoCpf = medicoCpf;
        this.medicoTelefone = medicoTelefone;
        this.medicoEmail = medicoEmail;
        this.medicoDtnascimento = medicoDtnascimento;
        this.medicoStatus = medicoStatus;
        this.medicoSexo = medicoSexo;
        this.medicoEndereco = medicoEndereco;
        this.especialidadeId = especialidadeId;
    }

    // 🔹 CONSTRUTOR ANTIGO (SEM especialidadeId) – usado pelo MedicoController/TelaMedico
    //    Ele apenas chama o construtor novo passando null como especialidade
    public Medico(String medicoNome, String medicoCrm, String medicoCpf,
                  String medicoTelefone, String medicoEmail, Date medicoDtnascimento,
                  String medicoStatus, String medicoSexo, String medicoEndereco) {
        this(medicoNome, medicoCrm, medicoCpf,
                medicoTelefone, medicoEmail, medicoDtnascimento,
                medicoStatus, medicoSexo, medicoEndereco,
                null); // especialidadeId ainda não usado
    }

    public int getMedicoId() { return medicoId; }
    public void setMedicoId(int medicoId) { this.medicoId = medicoId; }

    public String getMedicoNome() { return medicoNome; }
    public void setMedicoNome(String medicoNome) { this.medicoNome = medicoNome; }

    public String getMedicoCrm() { return medicoCrm; }
    public void setMedicoCrm(String medicoCrm) { this.medicoCrm = medicoCrm; }

    public String getMedicoCpf() { return medicoCpf; }
    public void setMedicoCpf(String medicoCpf) { this.medicoCpf = medicoCpf; }

    public String getMedicoTelefone() { return medicoTelefone; }
    public void setMedicoTelefone(String medicoTelefone) { this.medicoTelefone = medicoTelefone; }

    public String getMedicoEmail() { return medicoEmail; }
    public void setMedicoEmail(String medicoEmail) { this.medicoEmail = medicoEmail; }

    public Date getMedicoDtnascimento() { return medicoDtnascimento; }
    public void setMedicoDtnascimento(Date medicoDtnascimento) { this.medicoDtnascimento = medicoDtnascimento; }

    public String getMedicoStatus() { return medicoStatus; }
    public void setMedicoStatus(String medicoStatus) { this.medicoStatus = medicoStatus; }

    public String getMedicoSexo() { return medicoSexo; }
    public void setMedicoSexo(String medicoSexo) { this.medicoSexo = medicoSexo; }

    public String getMedicoEndereco() { return medicoEndereco; }
    public void setMedicoEndereco(String medicoEndereco) { this.medicoEndereco = medicoEndereco; }

    public Integer getEspecialidadeId() { return especialidadeId; }
    public void setEspecialidadeId(Integer especialidadeId) { this.especialidadeId = especialidadeId; }

    // NOVO: Sobrescreve toString para exibir o nome e CRM na ComboBox
    @Override
    public String toString() {
        if (medicoId == 0) return "Nenhum";
        if (medicoNome != null && medicoCrm != null) {
            return medicoNome + " (CRM: " + medicoCrm + ")";
        }
        return super.toString();
    }
}