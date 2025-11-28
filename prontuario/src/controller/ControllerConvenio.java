package controller;

import model.Convenio;
import model.ConvenioDAO;

import java.util.List;

public class ControllerConvenio {

    private final ConvenioDAO convenioDAO;

    public ControllerConvenio() {
        this.convenioDAO = new ConvenioDAO();
    }

    public boolean cadastrarConvenio(String nome, String tipo, String area,
                                     String coparticipacao, String status) {

        Convenio convenio = new Convenio(nome, tipo, area, coparticipacao, status);
        return convenioDAO.insert(convenio);
    }

    public List<Convenio> listarTodos() {
        return convenioDAO.findAll();
    }

    public Convenio buscarPorId(int id) {
        return convenioDAO.findById(id);
    }

    public boolean atualizarConvenio(int id, String nome, String tipo, String area,
                                     String coparticipacao, String status) {

        Convenio convenio = new Convenio(nome, tipo, area, coparticipacao, status);
        convenio.setIdconvenio(id);
        return convenioDAO.update(convenio);
    }

    public boolean excluirConvenio(int id) {
        return convenioDAO.delete(id);
    }
}
