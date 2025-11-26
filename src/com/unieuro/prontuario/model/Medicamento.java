package com.unieuro.prontuario.model;

public class Medicamento {

    private Long id;
    private String nome;
    private String bula;
    private String tipo;
    private String tarja;
    private String principioAtivo;
    private String observacao;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBula() {
        return bula;
    }

    public void setBula(String bula) {
        this.bula = bula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTarja() {
        return tarja;
    }

    public void setTarja(String tarja) {
        this.tarja = tarja;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}