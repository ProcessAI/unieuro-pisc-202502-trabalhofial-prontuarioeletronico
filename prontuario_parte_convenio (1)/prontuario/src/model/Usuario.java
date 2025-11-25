/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author aluno
 */
public class Usuario {
    
    private int idusuario;
    private String login;
    private String senha;
    
    public String getLogin()
    {
        return login;
    }
    public String getSenha()
    {
        return senha;
    }
    
    public void setLogin(String login)
    {
        this.login = login;
    }
    
    public void setSenha(String senha)
    {
        this.senha = senha;
    }
    
    public Usuario(String login, String senha)
    {
        this.login = login;
        this.senha = senha;
    }
    public Usuario(int idusuario, String login, String senha)
    {
        this.login = login;
        this.senha = senha;
        this.idusuario = idusuario;
    }      
    
    public int getIdusuario(){
        return idusuario;
    
    }
    
    public void setIdusuario(int idusuario)
    {
        this.idusuario = idusuario;
    }
    
}
