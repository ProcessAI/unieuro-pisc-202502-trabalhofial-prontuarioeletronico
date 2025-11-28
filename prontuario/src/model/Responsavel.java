package model;

import java.sql.Date;

public class Responsavel {

    private int id;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String telefone;
    private String email;
    private String endereco;
    private String parentesco;
    private String observacoes;

    public Responsavel() {
    }

    public Responsavel(String nome, String cpf, Date dataNascimento, String telefone, String email, String endereco, String parentesco, String observacoes) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.parentesco = parentesco;
        this.observacoes = observacoes;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public Date getDataNascimento() { return dataNascimento; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public String getEndereco() { return endereco; }
    public String getParentesco() { return parentesco; }
    public String getObservacoes() { return observacoes; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEmail(String email) { this.email = email; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setParentesco(String parentesco) { this.parentesco = parentesco; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    // NOVO: Sobrescreve toString para exibir o nome na ComboBox
    @Override
    public String toString() {
        if (id == 0) return "Nenhum";
        if (nome != null) {
            return nome;
        }
        return "Responsável #" + id;
    }
}