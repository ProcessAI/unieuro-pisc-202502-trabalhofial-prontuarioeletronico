// Pasta: model
package model;

public class Especialidade {

    private int id; // especialidade_id
    private String nome; // especialidade_nome
    private String status; // especialidade_status (char[1])
    private String cbo; // especialidade_cbo
    private String escala; // especialidade_escala
    private String descricao; // especialidade_descricao

    // Construtor completo (sem ID, que é SERIAL)
    public Especialidade(String nome, String status, String cbo, String escala, String descricao) {
        this.nome = nome;
        this.status = status;
        this.cbo = cbo;
        this.escala = escala;
        this.descricao = descricao;
    }

    // Construtor para busca (com ID)
    public Especialidade(int id, String nome, String status, String cbo, String escala, String descricao) {
        this(nome, status, cbo, escala, descricao);
        this.id = id;
    }
    
    // Construtor vazio
    public Especialidade() {
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCbo() { return cbo; }
    public void setCbo(String cbo) { this.cbo = cbo; }

    public String getEscala() { return escala; }
    public void setEscala(String escala) { this.escala = escala; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}