package model;

public class Convenio {

    private String convenioNome;
    private String convenioTipo;
    private String convenioArea;
    private String coparticipacao;

    public Convenio() {}

   
    public Convenio(String nome, String tipo, String area, String coparticipacao) {
        this.convenioNome = nome;
        this.convenioTipo = tipo;
        this.convenioArea = area;
        this.coparticipacao = coparticipacao;
    }

    
    public String getConvenioNome() {
        return convenioNome;
    }

    public String getConvenioTipo() {
        return convenioTipo;
    }

    public String getConvenioArea() {
        return convenioArea;
    }

    public String getCoparticipacao() {
        return coparticipacao;
    }


    public void setConvenioNome(String convenioNome) {
        this.convenioNome = convenioNome;
    }

    public void setConvenioTipo(String convenioTipo) {
        this.convenioTipo = convenioTipo;
    }

    public void setConvenioArea(String convenioArea) {
        this.convenioArea = convenioArea;
    }

    public void setCoparticipacao(String coparticipacao) {
        this.coparticipacao = coparticipacao;
    }
}
