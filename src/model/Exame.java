package model;

public class Exame {
    private int exame_id;
    private String exame_nome;
    private String exame_tipo;
    private String exame_status;
    private String exame_orientacao;
    private String exame_horai;
    private String exame_horaf;

    public int getExame_id() { return exame_id; }
    public void setExame_id(int exame_id) { this.exame_id = exame_id; }

    public String getExame_nome() { return exame_nome; }
    public void setExame_nome(String exame_nome) { this.exame_nome = exame_nome; }

    public String getExame_tipo() { return exame_tipo; }
    public void setExame_tipo(String exame_tipo) { this.exame_tipo = exame_tipo; }

    public String getExame_status() { return exame_status; }
    public void setExame_status(String exame_status) { this.exame_status = exame_status; }

    public String getExame_orientacao() { return exame_orientacao; }
    public void setExame_orientacao(String exame_orientacao) { this.exame_orientacao = exame_orientacao; }

    public String getExame_horai() { return exame_horai; }
    public void setExame_horai(String exame_horai) { this.exame_horai = exame_horai; }

    public String getExame_horaf() { return exame_horaf; }
    public void setExame_horaf(String exame_horaf) { this.exame_horaf = exame_horaf; }
}