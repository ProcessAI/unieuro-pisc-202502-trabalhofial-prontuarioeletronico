package model;

public class Medicamento {

    private int medicamentoId;
    private String medicamentoNome;
    private String bula;
    private String tipo;          // 1 char
    private String tarja;         // 1 char
    private String principioAtivo;
    private String observacao;

    public Medicamento() {}

    public Medicamento(int medicamentoId, String medicamentoNome, String bula, String tipo, String tarja, String principioAtivo, String observacao) {
        this.medicamentoId = medicamentoId;
        this.medicamentoNome = medicamentoNome;
        this.bula = bula;
        this.tipo = tipo;
        this.tarja = tarja;
        this.principioAtivo = principioAtivo;
        this.observacao = observacao;
    }

    public Medicamento(String medicamentoNome, String bula, String tipo, String tarja, String principioAtivo, String observacao) {
        this.medicamentoNome = medicamentoNome;
        this.bula = bula;
        this.tipo = tipo;
        this.tarja = tarja;
        this.principioAtivo = principioAtivo;
        this.observacao = observacao;
    }

    public int getMedicamentoId() { return medicamentoId; }
    public void setMedicamentoId(int medicamentoId) { this.medicamentoId = medicamentoId; }

    public String getMedicamentoNome() { return medicamentoNome; }
    public void setMedicamentoNome(String medicamentoNome) { this.medicamentoNome = medicamentoNome; }

    public String getBula() { return bula; }
    public void setBula(String bula) { this.bula = bula; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTarja() { return tarja; }
    public void setTarja(String tarja) { this.tarja = tarja; }

    public String getPrincipioAtivo() { return principioAtivo; }
    public void setPrincipioAtivo(String principioAtivo) { this.principioAtivo = principioAtivo; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    @Override
    public String toString() {
        return medicamentoNome;
    }
}
