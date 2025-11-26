package model;

import java.sql.Date;

public class Funcionario {

    private int id;
    private String nome;
    private String funcao;
    private String cpf;
    private char status;
    private String email;
    private String telefone;
    private char sexo;
    private Date dtnascimento;
    private String endereco;

    public Funcionario() {}

    public Funcionario(int id, String nome, String funcao, String cpf, char status, String email,
                       String telefone, char sexo, Date dtnascimento, String endereco) {
        this.id = id;
        this.nome = nome;
        this.funcao = funcao;
        this.cpf = cpf;
        this.status = status;
        this.email = email;
        this.telefone = telefone;
        this.sexo = sexo;
        this.dtnascimento = dtnascimento;
        this.endereco = endereco;
    }

    // Getters e Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public char getStatus() { return status; }
    public void setStatus(char status) { this.status = status; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public char getSexo() { return sexo; }
    public void setSexo(char sexo) { this.sexo = sexo; }

    public Date getDtnascimento() { return dtnascimento; }
    public void setDtnascimento(Date dtnascimento) { this.dtnascimento = dtnascimento; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}
