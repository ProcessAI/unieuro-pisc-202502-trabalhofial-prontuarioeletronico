package controller;

import model.Funcionario;
import model.FuncionarioDAO;

import java.sql.Date;
import java.util.List;

public class ControllerFuncionario {

    private final FuncionarioDAO funcionarioDAO;

    public ControllerFuncionario() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    public boolean cadastrarFuncionario(String nome, String funcao, String cpf,
                                        String status, String email, String telefone,
                                        String sexo, Date dtnasc, String endereco) {

        Funcionario f = new Funcionario(
                nome, funcao, cpf, status, email, telefone, sexo, dtnasc, endereco
        );
        return funcionarioDAO.insert(f);
    }

    public List<Funcionario> listarTodos() {
        return funcionarioDAO.findAll();
    }

    public Funcionario buscarPorId(int id) {
        return funcionarioDAO.findById(id);
    }

    public boolean atualizarFuncionario(int id, String nome, String funcao, String cpf,
                                        String status, String email, String telefone,
                                        String sexo, Date dtnasc, String endereco) {

        Funcionario f = new Funcionario(
                nome, funcao, cpf, status, email, telefone, sexo, dtnasc, endereco
        );
        f.setFuncionarioId(id);
        return funcionarioDAO.update(f);
    }

    public boolean excluirFuncionario(int id) {
        return funcionarioDAO.delete(id);
    }
}
