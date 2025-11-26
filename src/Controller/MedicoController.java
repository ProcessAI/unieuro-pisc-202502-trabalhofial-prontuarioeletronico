package controller;

import model.Medico;
import model.MedicoDAO;
import java.sql.SQLException;

public class MedicoController {
    
    private MedicoDAO medicoDAO;

    public MedicoController() {
        this.medicoDAO = new MedicoDAO();
    }

  
    public String salvarMedico(String crm, String nome, String especialidade, String telefone, String email) {
        if (crm == null || crm.trim().isEmpty() || nome == null || nome.trim().isEmpty()) {
            return "Erro: CRM e Nome são obrigatórios.";
        }
        
        Medico novoMedico = new Medico(crm, nome, especialidade, telefone, email);
        
        try {
          
            if (medicoDAO.buscarPorCrm(crm) != null) {
                return "Erro: Já existe um médico cadastrado com este CRM.";
            }
            medicoDAO.salvar(novoMedico);
            return "Médico cadastrado com sucesso!";
        } catch (SQLException e) {
            System.err.println("Erro SQL: " + e.getMessage());
            return "Erro no banco de dados. Tente novamente.";
        }
    }

  
    public Medico buscarMedico(String crm) {
        try {
            return medicoDAO.buscarPorCrm(crm);
        } catch (SQLException e) {
            System.err.println("Erro SQL na busca: " + e.getMessage());
            return null; 
        }
    }
    
   
    public String atualizarMedico(String crm, String nome, String especialidade, String telefone, String email) {
        if (crm == null || crm.trim().isEmpty()) {
            return "Erro: CRM é obrigatório para atualização.";
        }
        
        Medico medicoAtualizado = new Medico(crm, nome, especialidade, telefone, email);
        
        try {
            medicoDAO.atualizar(medicoAtualizado);
            return "Dados do médico atualizados com sucesso!";
        } catch (SQLException e) {
            System.err.println("Erro SQL na atualização: " + e.getMessage());
            return "Erro no banco de dados ao atualizar.";
        }
    }
    
    
    public String excluirMedico(String crm) {
        if (crm == null || crm.trim().isEmpty()) {
            return "Erro: CRM é obrigatório para exclusão.";
        }
        
        try {
            medicoDAO.excluir(crm);
            return "Médico excluído com sucesso!";
        } catch (SQLException e) {
            System.err.println("Erro SQL na exclusão: " + e.getMessage());
            return "Erro no banco de dados ao excluir.";
        }
    }

}
