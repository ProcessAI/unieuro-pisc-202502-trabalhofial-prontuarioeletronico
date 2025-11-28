package model;

public class Exame {

    // Campos principais
    private int exameId;
    private String exameNome;
    private String exameTipo;
    private String exameStatus;
    private String exameOrientacao;

    // ⏱ Duração em minutos (coluna exame_duracao_minutos)
    private Integer duracaoMinutos;

    // =========================
    // Construtores
    // =========================

    public Exame() {
    }

    // Construtor usado no SELECT do ExameDataAcessObject
    public Exame(int exameId,
                 String exameNome,
                 String exameTipo,
                 String exameStatus,
                 String exameOrientacao,
                 Integer duracaoMinutos) {
        this.exameId = exameId;
        this.exameNome = exameNome;
        this.exameTipo = exameTipo;
        this.exameStatus = exameStatus;
        this.exameOrientacao = exameOrientacao;
        this.duracaoMinutos = duracaoMinutos;
    }

    // Construtor sem ID (para INSERT)
    public Exame(String exameNome,
                 String exameTipo,
                 String exameStatus,
                 String exameOrientacao,
                 Integer duracaoMinutos) {
        this.exameNome = exameNome;
        this.exameTipo = exameTipo;
        this.exameStatus = exameStatus;
        this.exameOrientacao = exameOrientacao;
        this.duracaoMinutos = duracaoMinutos;
    }

    // =========================
    // Getters/Setters NOVOS (camelCase)
    // =========================

    public int getExameId() {
        return exameId;
    }

    public void setExameId(int exameId) {
        this.exameId = exameId;
    }

    public String getExameNome() {
        return exameNome;
    }

    public void setExameNome(String exameNome) {
        this.exameNome = exameNome;
    }

    public String getExameTipo() {
        return exameTipo;
    }

    public void setExameTipo(String exameTipo) {
        this.exameTipo = exameTipo;
    }

    public String getExameStatus() {
        return exameStatus;
    }

    public void setExameStatus(String exameStatus) {
        this.exameStatus = exameStatus;
    }

    public String getExameOrientacao() {
        return exameOrientacao;
    }

    public void setExameOrientacao(String exameOrientacao) {
        this.exameOrientacao = exameOrientacao;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    // =========================
    // Getters/Setters ANTIGOS (com underline) – compatibilidade
    // =========================

    public int getExame_id() {
        return getExameId();
    }

    public void setExame_id(int exame_id) {
        setExameId(exame_id);
    }

    public String getExame_nome() {
        return getExameNome();
    }

    public void setExame_nome(String exame_nome) {
        setExameNome(exame_nome);
    }

    public String getExame_tipo() {
        return getExameTipo();
    }

    public void setExame_tipo(String exame_tipo) {
        setExameTipo(exame_tipo);
    }

    public String getExame_status() {
        return getExameStatus();
    }

    public void setExame_status(String exame_status) {
        setExameStatus(exame_status);
    }

    public String getExame_orientacao() {
        return getExameOrientacao();
    }

    public void setExame_orientacao(String exame_orientacao) {
        setExameOrientacao(exame_orientacao);
    }

    // =========================
    // toString (usado em JComboBox)
    // =========================

    @Override
    public String toString() {
        if (exameId == 0) return "Nenhum";
        String base = (exameNome != null ? exameNome : ("Exame #" + exameId));
        if (duracaoMinutos != null) {
            base += " - " + duracaoMinutos + " min";
        }
        return base;
    }
}