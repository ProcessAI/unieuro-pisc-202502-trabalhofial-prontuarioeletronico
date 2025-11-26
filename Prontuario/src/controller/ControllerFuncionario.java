package controller;

import model.Funcionario;
import model.FuncionarioDAO;

import java.sql.Date;

public class ControllerFuncionario {

    private final FuncionarioDAO dao = new FuncionarioDAO();

    public boolean salvar(String nome, String funcao, String cpf, String status, String email,
                          String telefone, String sexo, String dtnasc, String endereco) {

        Funcionario f = new Funcionario();

        try {
            f.setNome(nome);
            f.setFuncao(funcao);
            f.setCpf(cpf);
            f.setStatus(status.charAt(0));
            f.setEmail(email);
            f.setTelefone(telefone);
            f.setSexo(sexo.charAt(0));
            f.setDtnascimento(Date.valueOf(dtnasc));
            f.setEndereco(endereco);

            return dao.inserir(f);

        } catch (Exception e) {
            System.out.println("Erro salvar: " + e.getMessage());
            return false;
        }
    }

    public Funcionario carregarEdicao(String id) {
        try {
            int idInt = Integer.parseInt(id);
            return dao.buscarPorId(idInt);
        } catch (NumberFormatException e) {
            System.out.println("Erro de formato no ID: " + e.getMessage());
            return null;
        }
    }

    public boolean editar(String id, String nome, String funcao, String cpf, String status, String email,
                          String telefone, String sexo, String dtnasc, String endereco) {
        Funcionario f = new Funcionario();

        try {
            f.setId(Integer.parseInt(id)); 
            f.setNome(nome);
            f.setFuncao(funcao);
            f.setCpf(cpf);
            f.setStatus(status.charAt(0));
            f.setEmail(email);
            f.setTelefone(telefone);
            f.setSexo(sexo.charAt(0));
            f.setDtnascimento(Date.valueOf(dtnasc));
            f.setEndereco(endereco);

            return dao.editar(f);

        } catch (Exception e) {
            System.out.println("Erro editar: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(String id) {
        try {
            int idInt = Integer.parseInt(id);
            return dao.excluir(idInt);
        } catch (NumberFormatException e) {
            System.out.println("Erro de formato no ID para exclusão: " + e.getMessage());
            return false;
        }
    }

    public String listarAtivos() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Funcionários Ativos ---\n");
        dao.listarAtivos().forEach(f -> sb.append(format(f)));
        return sb.toString();
    }

    public String listarTodos() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Todos Funcionários ---\n");
        dao.listarTodos().forEach(f -> sb.append(format(f)));
        return sb.toString();
    }

    private String format(Funcionario f) {
        return "ID: " + f.getId()
                + " | Nome: " + f.getNome()
                + " | Função: " + f.getFuncao()
                + " | CPF: " + f.getCpf()
                + " | Status: " + f.getStatus()
                + " | Sexo: " + f.getSexo()
                + " | Nasc: " + f.getDtnascimento()
                + "\n";
    }
}