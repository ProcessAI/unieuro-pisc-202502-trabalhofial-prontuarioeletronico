// Pasta: controller
package controller;

import model.Especialidade;
import model.EspecialidadeDAO;
import java.sql.SQLException;

public class EspecialidadeController {

    private EspecialidadeDAO especialidadeDAO;

    public EspecialidadeController() {
        this.especialidadeDAO = new EspecialidadeDAO();
    }

    // CREATE
    public String salvarEspecialidade(String nome, String status, String cbo, String escala, String descricao) {
        if (nome == null || nome.trim().isEmpty() || status == null || status.trim().isEmpty()) {
            return "Erro: Nome e Status são obrigatórios.";
        }
        
        Especialidade novaEspecialidade = new Especialidade(nome, status, cbo, escala, descricao);

        try {
            especialidadeDAO.salvar(novaEspecialidade);
            return "Especialidade cadastrada com sucesso!";
        } catch (SQLException e) {
            System.err.println("Erro SQL ao salvar: " + e.getMessage());
            return "Erro no banco de dados ao salvar. Verifique a conexão.";
        }
    }

    // READ
    public Especialidade buscarEspecialidade(String idStr) {
        if (idStr == null || idStr.trim().isEmpty()) {
             System.err.println("Erro: O ID de busca não pode ser vazio.");
             return null;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            return especialidadeDAO.buscarPorId(id);
        } catch (NumberFormatException e) {
            System.err.println("Erro: O ID deve ser um número inteiro.");
            return null;
        } catch (SQLException e) {
            System.err.println("Erro SQL na busca: " + e.getMessage());
            return null;
        }
    }
    
    // UPDATE
    public String atualizarEspecialidade(String idStr, String nome, String status, String cbo, String escala, String descricao) {
        if (idStr == null || idStr.trim().isEmpty() || nome == null || nome.trim().isEmpty()) {
            return "Erro: ID e Nome são obrigatórios para atualização.";
        }
        
        try {
            int id = Integer.parseInt(idStr);
            Especialidade especialidadeAtualizada = new Especialidade(id, nome, status, cbo, escala, descricao);
            
            especialidadeDAO.atualizar(especialidadeAtualizada);
            return "Dados da Especialidade atualizados com sucesso!";
        } catch (NumberFormatException e) {
            return "Erro: O ID deve ser um número inteiro válido.";
        } catch (SQLException e) {
            System.err.println("Erro SQL na atualização: " + e.getMessage());
            return "Erro no banco de dados ao atualizar.";
        }
    }

    // DELETE
    public String excluirEspecialidade(String idStr) {
        if (idStr == null || idStr.trim().isEmpty()) {
            return "Erro: O ID da Especialidade é obrigatório para exclusão.";
        }
        
        try {
            int id = Integer.parseInt(idStr);
            especialidadeDAO.excluir(id);
            return "Especialidade excluída com sucesso!";
        } catch (NumberFormatException e) {
            return "Erro: O ID deve ser um número inteiro válido.";
        } catch (SQLException e) {
            System.err.println("Erro SQL na exclusão: " + e.getMessage());
            return "Erro no banco de dados ao excluir. (Pode haver registros vinculados)";
        }
    }
}