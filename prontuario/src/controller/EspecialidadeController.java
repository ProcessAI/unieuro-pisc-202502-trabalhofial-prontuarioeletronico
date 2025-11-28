package controller;

import model.Especialidade;
import model.EspecialidadeDAO;

import java.util.List;

public class EspecialidadeController {

    private final EspecialidadeDAO especialidadeDAO;

    public EspecialidadeController() {
        this.especialidadeDAO = new EspecialidadeDAO();
    }

    public boolean cadastrarEspecialidade(String nome, String status,
                                          String cbo, String escala, String descricao) {

        Especialidade e = new Especialidade(nome, status, cbo, escala, descricao);
        return especialidadeDAO.insert(e);
    }

    public List<Especialidade> listarTodas() {
        return especialidadeDAO.findAll();
    }

    public Especialidade buscarPorId(int id) {
        return especialidadeDAO.findById(id);
    }

    public boolean atualizarEspecialidade(int id, String nome, String status,
                                          String cbo, String escala, String descricao) {

        Especialidade e = new Especialidade(nome, status, cbo, escala, descricao);
        e.setEspecialidadeId(id);
        return especialidadeDAO.update(e);
    }

    public boolean excluirEspecialidade(int id) {
        return especialidadeDAO.delete(id);
    }
}
