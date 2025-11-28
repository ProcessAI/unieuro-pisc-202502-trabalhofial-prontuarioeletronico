package model;

import java.sql.Date;

public class Funcionario {

    private int funcionarioId;
    private String funcionarioNome;
    private String funcionarioFuncao;      // 1 char no banco, mas String aqui
    private String funcionarioCpf;
    private String funcionarioStatus;      // A/I
    private String funcionarioEmail;
    private String funcionarioTelefone;
    private String funcionarioSexo;        // M/F
    private Date   funcionarioDtnascimento;
    private String funcionarioEndereco;

    public Funcionario() {
    }

    // Construtor com ID (carregar do banco)
    public Funcionario(int funcionarioId, String funcionarioNome, String funcionarioFuncao,
                       String funcionarioCpf, String funcionarioStatus, String funcionarioEmail,
                       String funcionarioTelefone, String funcionarioSexo,
                       Date funcionarioDtnascimento, String funcionarioEndereco) {
        this.funcionarioId = funcionarioId;
        this.funcionarioNome = funcionarioNome;
        this.funcionarioFuncao = funcionarioFuncao;
        this.funcionarioCpf = funcionarioCpf;
        this.funcionarioStatus = funcionarioStatus;
        this.funcionarioEmail = funcionarioEmail;
        this.funcionarioTelefone = funcionarioTelefone;
        this.funcionarioSexo = funcionarioSexo;
        this.funcionarioDtnascimento = funcionarioDtnascimento;
        this.funcionarioEndereco = funcionarioEndereco;
    }

    // Construtor sem ID (inserção)
    public Funcionario(String funcionarioNome, String funcionarioFuncao,
                       String funcionarioCpf, String funcionarioStatus, String funcionarioEmail,
                       String funcionarioTelefone, String funcionarioSexo,
                       Date funcionarioDtnascimento, String funcionarioEndereco) {
        this.funcionarioNome = funcionarioNome;
        this.funcionarioFuncao = funcionarioFuncao;
        this.funcionarioCpf = funcionarioCpf;
        this.funcionarioStatus = funcionarioStatus;
        this.funcionarioEmail = funcionarioEmail;
        this.funcionarioTelefone = funcionarioTelefone;
        this.funcionarioSexo = funcionarioSexo;
        this.funcionarioDtnascimento = funcionarioDtnascimento;
        this.funcionarioEndereco = funcionarioEndereco;
    }

    public int getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(int funcionarioId) { this.funcionarioId = funcionarioId; }

    public String getFuncionarioNome() { return funcionarioNome; }
    public void setFuncionarioNome(String funcionarioNome) { this.funcionarioNome = funcionarioNome; }

    public String getFuncionarioFuncao() { return funcionarioFuncao; }
    public void setFuncionarioFuncao(String funcionarioFuncao) { this.funcionarioFuncao = funcionarioFuncao; }

    public String getFuncionarioCpf() { return funcionarioCpf; }
    public void setFuncionarioCpf(String funcionarioCpf) { this.funcionarioCpf = funcionarioCpf; }

    public String getFuncionarioStatus() { return funcionarioStatus; }
    public void setFuncionarioStatus(String funcionarioStatus) { this.funcionarioStatus = funcionarioStatus; }

    public String getFuncionarioEmail() { return funcionarioEmail; }
    public void setFuncionarioEmail(String funcionarioEmail) { this.funcionarioEmail = funcionarioEmail; }

    public String getFuncionarioTelefone() { return funcionarioTelefone; }
    public void setFuncionarioTelefone(String funcionarioTelefone) { this.funcionarioTelefone = funcionarioTelefone; }

    public String getFuncionarioSexo() { return funcionarioSexo; }
    public void setFuncionarioSexo(String funcionarioSexo) { this.funcionarioSexo = funcionarioSexo; }

    public Date getFuncionarioDtnascimento() { return funcionarioDtnascimento; }
    public void setFuncionarioDtnascimento(Date funcionarioDtnascimento) { this.funcionarioDtnascimento = funcionarioDtnascimento; }

    public String getFuncionarioEndereco() { return funcionarioEndereco; }
    public void setFuncionarioEndereco(String funcionarioEndereco) { this.funcionarioEndereco = funcionarioEndereco; }
}
