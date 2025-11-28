package controller;

import model.Medicamento;
import model.MedicamentoDAO;
import java.util.List;

public class MedicamentoController {

    private final MedicamentoDAO repo;

    public MedicamentoController() {
        this.repo = new MedicamentoDAO();
    }

    public boolean cadastrar(String nome, String bula, String tipo, String tarja, String principio, String obs) {
        Medicamento m = new Medicamento(nome, bula, tipo, tarja, principio, obs);
        return repo.insert(m);
    }

    public boolean atualizar(int id, String nome, String bula, String tipo, String tarja, String principio, String obs) {
        Medicamento m = new Medicamento(nome, bula, tipo, tarja, principio, obs);
        m.setMedicamentoId(id);
        return repo.update(m);
    }

    public boolean excluir(int id) {
        return repo.delete(id);
    }

    public Medicamento buscarPorId(int id) {
        return repo.findById(id);
    }

    public List<Medicamento> listarTodos() {
        return repo.findAll();
    }
}
