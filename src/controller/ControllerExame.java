package controller;

import model.Exame;
import model.ExameDataAcessObject;

import java.util.List;

public class ControllerExame {

    ExameDataAcessObject dao = new ExameDataAcessObject();

    public void cadastrar(Exame e) {
        dao.inserir(e);
    }

    public List<Exame> listar() {
        return dao.listar();
    }

    public void atualizar(Exame e) {
        dao.atualizar(e);
    }

    public void deletar(int id) {
        dao.deletar(id);
    }
}