package model;


public class Medico {
    
    private String crm;
    private String nome;
    private String especialidade;
    private String telefone;
    private String email;

    
    public Medico(String crm, String nome, String especialidade, String telefone, String email) {
        this.crm = crm;
        this.nome = nome;
        this.especialidade = especialidade;
        this.telefone = telefone;
        this.email = email;
    }

   
    public Medico() {
    }

    
    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

}
