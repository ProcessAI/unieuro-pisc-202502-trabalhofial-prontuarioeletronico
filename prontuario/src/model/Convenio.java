package model;

public class Convenio {

    private int idconvenio;
    private String convenioNome;
    private String convenioTipo;      // ex: P (privado), C (convênio), etc
    private String convenioArea;      // ex: H (hospitalar), C (clínico), etc
    private String coparticipacao;    // S/N
    private String convenioStatus;    // A/I

    public Convenio() {
    }

    // Para carregar do banco (com ID)
    public Convenio(int idconvenio, String convenioNome, String convenioTipo,
                    String convenioArea, String coparticipacao, String convenioStatus) {
        this.idconvenio = idconvenio;
        this.convenioNome = convenioNome;
        this.convenioTipo = convenioTipo;
        this.convenioArea = convenioArea;
        this.coparticipacao = coparticipacao;
        this.convenioStatus = convenioStatus;
    }

    // Para cadastrar (sem ID)
    public Convenio(String convenioNome, String convenioTipo,
                    String convenioArea, String coparticipacao, String convenioStatus) {
        this.convenioNome = convenioNome;
        this.convenioTipo = convenioTipo;
        this.convenioArea = convenioArea;
        this.coparticipacao = coparticipacao;
        this.convenioStatus = convenioStatus;
    }

    public int getIdconvenio() {
        return idconvenio;
    }

    public void setIdconvenio(int idconvenio) {
        this.idconvenio = idconvenio;
    }

    public String getConvenioNome() {
        return convenioNome;
    }

    public void setConvenioNome(String convenioNome) {
        this.convenioNome = convenioNome;
    }

    public String getConvenioTipo() {
        return convenioTipo;
    }

    public void setConvenioTipo(String convenioTipo) {
        this.convenioTipo = convenioTipo;
    }

    public String getConvenioArea() {
        return convenioArea;
    }

    public void setConvenioArea(String convenioArea) {
        this.convenioArea = convenioArea;
    }

    public String getCoparticipacao() {
        return coparticipacao;
    }

    public void setCoparticipacao(String coparticipacao) {
        this.coparticipacao = coparticipacao;
    }

    public String getConvenioStatus() {
        return convenioStatus;
    }

    public void setConvenioStatus(String convenioStatus) {
        this.convenioStatus = convenioStatus;
    }

    @Override
    public String toString() {
        if (idconvenio == 0) return "Nenhum";
        return convenioNome;
    }
}