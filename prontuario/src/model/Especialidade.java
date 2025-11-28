package model;

public class Especialidade {

    private int especialidadeId;
    private String especialidadeNome;
    private String especialidadeStatus;   // A/I
    private String especialidadeCbo;
    private String especialidadeEscala;
    private String especialidadeDescricao;

    public Especialidade() {
    }

    // Construtor com ID (carregar do banco)
    public Especialidade(int especialidadeId, String especialidadeNome,
                         String especialidadeStatus, String especialidadeCbo,
                         String especialidadeEscala, String especialidadeDescricao) {
        this.especialidadeId = especialidadeId;
        this.especialidadeNome = especialidadeNome;
        this.especialidadeStatus = especialidadeStatus;
        this.especialidadeCbo = especialidadeCbo;
        this.especialidadeEscala = especialidadeEscala;
        this.especialidadeDescricao = especialidadeDescricao;
    }

    // Construtor sem ID (inserção)
    public Especialidade(String especialidadeNome, String especialidadeStatus,
                         String especialidadeCbo, String especialidadeEscala,
                         String especialidadeDescricao) {
        this.especialidadeNome = especialidadeNome;
        this.especialidadeStatus = especialidadeStatus;
        this.especialidadeCbo = especialidadeCbo;
        this.especialidadeEscala = especialidadeEscala;
        this.especialidadeDescricao = especialidadeDescricao;
    }

    public int getEspecialidadeId() {
        return especialidadeId;
    }

    public void setEspecialidadeId(int especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

    public String getEspecialidadeNome() {
        return especialidadeNome;
    }

    public void setEspecialidadeNome(String especialidadeNome) {
        this.especialidadeNome = especialidadeNome;
    }

    public String getEspecialidadeStatus() {
        return especialidadeStatus;
    }

    public void setEspecialidadeStatus(String especialidadeStatus) {
        this.especialidadeStatus = especialidadeStatus;
    }

    public String getEspecialidadeCbo() {
        return especialidadeCbo;
    }

    public void setEspecialidadeCbo(String especialidadeCbo) {
        this.especialidadeCbo = especialidadeCbo;
    }

    public String getEspecialidadeEscala() {
        return especialidadeEscala;
    }

    public void setEspecialidadeEscala(String especialidadeEscala) {
        this.especialidadeEscala = especialidadeEscala;
    }

    public String getEspecialidadeDescricao() {
        return especialidadeDescricao;
    }

    public void setEspecialidadeDescricao(String especialidadeDescricao) {
        this.especialidadeDescricao = especialidadeDescricao;
    }

    @Override
    public String toString() {
        if (especialidadeId == 0) return "Nenhum";
        return especialidadeNome;
    }
}