/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Usuario;
import model.UsuarioDAO;

/**
 *
 * @author aluno
 */
public class ControllerLogin {
    
    public boolean autenticar(String login, String senha)
    {
        //if(login.compareTo("rafael") == 0 && senha.compareTo("123") == 0) return true;
        UsuarioDAO udao = new UsuarioDAO();
        Usuario usuario = udao.getUsuarioByLogin(login);
        if(usuario == null) return false;
        if(usuario.getSenha().compareTo(senha) == 0) return true;
        return false;
    }
}
