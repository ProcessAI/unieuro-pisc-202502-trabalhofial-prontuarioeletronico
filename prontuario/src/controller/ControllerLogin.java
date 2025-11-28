package controller;

import model.FuncionarioDAO;

public class ControllerLogin {

    private final FuncionarioDAO funcionarioDAO;

    public ControllerLogin() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    public boolean autenticar(String usuario, String senha) {
        // 1. Lógica do Admin ("da mesma forma que antes")
        // Permite acesso se o usuário for "admin" e a senha "123"
        if (usuario.equalsIgnoreCase("admin") && senha.equals("123")) {
            return true;
        }

        // 2. Lógica do Funcionário (Novo método)
        // Verifica no banco de dados: Usuario = Email, Senha = CPF
        return funcionarioDAO.autenticar(usuario, senha);
    }
}