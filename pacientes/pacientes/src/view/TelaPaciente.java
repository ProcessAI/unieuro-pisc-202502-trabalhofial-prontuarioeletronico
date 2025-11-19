package view;

import controller.ControllerPaciente;
import model.Paciente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaPaciente extends JFrame {

    private ControllerPaciente controller;

    // NOVO CAMPO: Para armazenar o ID do paciente em edição (escondido ou somente leitura)
    private JTextField campoId;

    private JTextField campoNome, campoTelefone, campoCpf, campoEmail, campoEndereco, campoAlergia, campoNacionalidade;
    private JTextField campoStatus, campoSexo, campoEstadocivil, campoDnas; // Exemplo de data

    private JTextArea areaListaPacientes;
    private JButton botaoSalvar, botaoListar, botaoExcluir, botaoEditar, botaoLimpar; // Adicionado botão Limpar

    public TelaPaciente() {
        controller = new ControllerPaciente();
        setTitle("CRUD de Pacientes");
        setSize(800, 650); // Aumentado o tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // 1. Painel de Cadastro (Top/North)
        // Aumentado para 7 linhas para acomodar o campo ID e um Label de Limpar/Aviso
        JPanel painelCadastro = new JPanel(new GridLayout(7, 4, 5, 5));

        // ----------------- NOVO CAMPO ID -----------------
        campoId = adicionarCampo(painelCadastro, "ID (Edição):");
        campoId.setEditable(false); // ID não deve ser alterado manualmente
        // -------------------------------------------------

        campoNome = adicionarCampo(painelCadastro, "Nome:");
        campoTelefone = adicionarCampo(painelCadastro, "Telefone:");
        campoCpf = adicionarCampo(painelCadastro, "CPF:");
        campoDnas = adicionarCampo(painelCadastro, "Nascimento (AAAA-MM-DD):");
        campoEmail = adicionarCampo(painelCadastro, "Email:");
        campoEndereco = adicionarCampo(painelCadastro, "Endereço:");
        campoStatus = adicionarCampo(painelCadastro, "Status (A/I):");
        campoAlergia = adicionarCampo(painelCadastro, "Alergia:");
        campoSexo = adicionarCampo(painelCadastro, "Sexo (M/F):");
        campoNacionalidade = adicionarCampo(painelCadastro, "Nacionalidade:");
        campoEstadocivil = adicionarCampo(painelCadastro, "Est. Civil (S/C/D/V):");

        // Espaço vazio para completar a grade
        painelCadastro.add(new JLabel(""));
        painelCadastro.add(new JLabel("Preencha todos os campos."));

        add(painelCadastro, BorderLayout.NORTH);

        // 2. Painel de Botões (Center/North)
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        botaoSalvar = new JButton("Salvar Novo"); // Este texto mudará para 'Atualizar Paciente' na edição
        botaoListar = new JButton("Listar Todos");
        botaoExcluir = new JButton("Excluir (por ID)");
        botaoEditar = new JButton("Carregar p/ Edição"); // Nome mais claro
        botaoLimpar = new JButton("Limpar Campos");

        // Adiciona Ações aos Botões
        botaoSalvar.addActionListener(e -> salvarOuAtualizar()); // MUDANÇA: chama o novo método
        botaoListar.addActionListener(e -> listar());
        botaoExcluir.addActionListener(e -> excluir());
        botaoEditar.addActionListener(e -> editar());
        botaoLimpar.addActionListener(e -> limparCampos());

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoListar);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoLimpar);

        add(painelBotoes, BorderLayout.CENTER);


        // 3. Área de Resultado (South)
        areaListaPacientes = new JTextArea("Lista de Pacientes aparecerá aqui...", 10, 60);
        areaListaPacientes.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaListaPacientes);

        add(scroll, BorderLayout.SOUTH);

        // Lista automaticamente ao abrir
        listar();
    }

    // Método auxiliar para simplificar a criação de campos
    private JTextField adicionarCampo(JPanel painel, String label) {
        painel.add(new JLabel(label));
        JTextField campo = new JTextField(15);
        painel.add(campo);
        return campo;
    }

    // Novo método para limpar os campos da interface
    private void limparCampos() {
        campoId.setText("");
        campoNome.setText("");
        campoTelefone.setText("");
        campoCpf.setText("");
        campoDnas.setText("");
        campoEmail.setText("");
        campoEndereco.setText("");
        campoStatus.setText("");
        campoAlergia.setText("");
        campoSexo.setText("");
        campoNacionalidade.setText("");
        campoEstadocivil.setText("");
        botaoSalvar.setText("Salvar Novo"); // Reseta o texto do botão
    }


    /**
     * Lógica unificada para Salvar (Novo) ou Atualizar (Existente) paciente.
     */
    private void salvarOuAtualizar() {
        try {
            // 1. Validação e conversão de Data
            if (campoNome.getText().isEmpty() || campoCpf.getText().isEmpty() || campoDnas.getText().isEmpty()) {
                throw new IllegalArgumentException("Nome, CPF e Data de Nascimento são obrigatórios.");
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = formatter.parse(campoDnas.getText());
            Date dnas = new Date(parsedDate.getTime());

            String idText = campoId.getText();
            boolean sucesso = false;

            // 2. Cria o objeto Paciente com os dados da tela
            String nome = campoNome.getText();
            String telefone = campoTelefone.getText();
            String cpf = campoCpf.getText();
            String email = campoEmail.getText();
            String endereco = campoEndereco.getText();
            String status = campoStatus.getText();
            String alergia = campoAlergia.getText();
            String sexo = campoSexo.getText();
            String nacionalidade = campoNacionalidade.getText();
            String estadocivil = campoEstadocivil.getText();


            // 3. LÓGICA DE DECISÃO: UPDATE ou INSERT
            if (idText != null && !idText.trim().isEmpty()) {
                // É uma ATUALIZAÇÃO
                int id = Integer.parseInt(idText);
                sucesso = controller.editarPaciente(
                        id, nome, telefone, cpf, dnas, email, endereco,
                        status, alergia, sexo, nacionalidade, estadocivil
                );

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Paciente ID " + id + " atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao atualizar paciente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                // É uma INSERÇÃO (NOVO REGISTRO)
                sucesso = controller.cadastrarPaciente(
                        nome, telefone, cpf, dnas, email, endereco,
                        status, alergia, sexo, nacionalidade, estadocivil
                );

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao cadastrar paciente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            // 4. Se a operação foi um sucesso (inserir ou atualizar)
            if(sucesso) {
                limparCampos();
                listar();
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Erro de validação: " + ex.getMessage(), "Erro de Dados", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException | ParseException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage() + "\nVerifique o formato da data (AAAA-MM-DD) e se todos os campos estão preenchidos corretamente.", "Erro de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Lógica para listar todos os pacientes
    private void listar() {
        List<Paciente> pacientes = controller.listarTodos();
        StringBuilder sb = new StringBuilder();
        sb.append("--- Lista de Pacientes ---\n");

        if (pacientes.isEmpty()) {
            sb.append("Nenhum paciente encontrado.");
        } else {
            for (Paciente p : pacientes) {
                // .trim() é usado para caracteres char(1) do banco
                sb.append("ID: ").append(p.getPacienteId())
                        .append(" | Nome: ").append(p.getPacienteNome())
                        .append(" | CPF: ").append(p.getPacienteCpf())
                        .append(" | D.Nasc: ").append(p.getPacienteDnas())
                        .append(" | Status: ").append(p.getPacienteStatus() != null ? p.getPacienteStatus().trim() : "")
                        .append(" | Sexo: ").append(p.getPacienteSexo() != null ? p.getPacienteSexo().trim() : "")
                        .append("\n");
            }
        }
        areaListaPacientes.setText(sb.toString());
    }

    // Lógica para excluir
    private void excluir() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do paciente para EXCLUIR:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                boolean sucesso = controller.excluirPaciente(id);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Paciente ID " + id + " excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                    listar();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao excluir paciente. ID não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Digite apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Lógica para carregar os dados de um paciente nos campos da tela para edição.
     * Requer que PacienteDAO.findById(id) esteja implementado.
     */
    private void editar() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do paciente para CARREGAR os dados:");

        if (idStr == null || idStr.trim().isEmpty()) {
            return; // Usuário cancelou
        }

        try {
            int id = Integer.parseInt(idStr.trim());

            // 1. Busca o paciente no banco
            Paciente paciente = controller.buscarPorId(id);

            if (paciente != null) {
                // 2. Preenche todos os campos da tela
                campoId.setText(String.valueOf(paciente.getPacienteId()));
                campoNome.setText(paciente.getPacienteNome());
                campoTelefone.setText(paciente.getPacienteTelefone());
                campoCpf.setText(paciente.getPacienteCpf());

                // Conversão de java.sql.Date para String (AAAA-MM-DD)
                campoDnas.setText(paciente.getPacienteDnas().toString());

                campoEmail.setText(paciente.getPacienteEmail());
                campoEndereco.setText(paciente.getPacienteEndereco());
                // .trim() para remover espaços extras de char(1)
                campoStatus.setText(paciente.getPacienteStatus().trim());
                campoAlergia.setText(paciente.getPacienteAlergia());
                campoSexo.setText(paciente.getPacienteSexo().trim());
                campoNacionalidade.setText(paciente.getPacienteNacionalidade());
                campoEstadocivil.setText(paciente.getPacienteEstadocivil().trim());

                // 3. Altera o texto do botão para indicar que a próxima ação é uma atualização
                botaoSalvar.setText("Atualizar Paciente");

                JOptionPane.showMessageDialog(this, "Dados do paciente ID " + id + " carregados para edição.", "Carregamento Completo", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(this, "Paciente com ID " + id + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido. Digite apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPaciente().setVisible(true));
    }
}