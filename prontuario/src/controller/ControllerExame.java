package controller;

import model.Exame;
import model.ExameDataAcessObject;

import java.util.List;

public class ControllerExame {

    private final ExameDataAcessObject exameDAO;

    public ControllerExame() {
        this.exameDAO = new ExameDataAcessObject();
    }

    public boolean cadastrarExame(String nome, String tipo, String status,
                                  String orientacao, Integer duracaoMinutos) {

        Exame e = new Exame(nome, tipo, status, orientacao, duracaoMinutos);
        return exameDAO.insert(e);
    }

    public List<Exame> listarTodos() {
        return exameDAO.findAll();
    }

    public Exame buscarPorId(int id) {
        return exameDAO.findById(id);
    }

    public boolean atualizarExame(int id, String nome, String tipo, String status,
                                  String orientacao, Integer duracaoMinutos) {

        Exame e = new Exame(nome, tipo, status, orientacao, duracaoMinutos);
        e.setExameId(id);
        return exameDAO.update(e);
    }

    public boolean excluirExame(int id) {
        return exameDAO.delete(id);
    }
}
