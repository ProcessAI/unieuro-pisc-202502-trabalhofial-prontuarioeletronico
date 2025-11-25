package controller;

import java.util.List;
import model.Convenio;
import model.ConvenioDAO;

public class ControllerConvenio {
    
    public boolean salvar(String nome, String tipo, String area, String copart) {
        
        Convenio convenio = new Convenio(nome, tipo, area, copart); // usa o construtor corrigido
        
        ConvenioDAO dao = new ConvenioDAO();
        return dao.cadastrar(convenio);
    }
    
    public List<Convenio> listarConvenios() {
        ConvenioDAO dao = new ConvenioDAO();
        return dao.listar();
    }
}
