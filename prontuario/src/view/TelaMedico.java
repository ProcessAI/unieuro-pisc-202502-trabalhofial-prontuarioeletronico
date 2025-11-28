package view;

import controller.MedicoController;
import model.Especialidade;
import model.EspecialidadeDAO;
import model.Medico;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaMedico extends JFrame {

    private final MedicoController controller;

    private JTextField campoId;
    private JTextField campoNome;
    private JTextField campoCrm;
    private JTextField campoCpf;
    private JTextField campoTelefone;
    private JTextField campoEmail;
    private JTextField campoDataNasc; // dd/MM/yyyy
    private JTextField campoStatus;   // A/I
    private JTextField campoSexo;     // M/F
    private JTextField campoEndereco;

    private JComboBox<Especialidade> comboEspecialidade;

    private JTextArea areaLista;

    private JButton botaoSalvar;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private JButton botaoListar;
    private JButton botaoLimpar;

    public TelaMedico() {
        controller = new MedicoController();
        setTitle("Cadastro de Médico");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        iniciarComponentes();
        carregarEspecialidades();
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // ===========================
        //  CAMPOS SUPERIORES (6 linhas x 4 colunas)
        // ===========================
        JPanel painelCampos = new JPanel(new GridLayout(6, 4, 5, 5));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // Linha 1
        campoId = adicionarCampo(painelCampos, "ID (Edição):");
        campoId.setEditable(false);
        campoNome = adicionarCampo(painelCampos, "Nome:");

        // Linha 2
        campoCrm = adicionarCampo(painelCampos, "CRM:");
        campoCpf = adicionarCampo(painelCampos, "CPF:");

        // Linha 3
        campoTelefone = adicionarCampo(painelCampos, "Telefone:");
        campoEmail = adicionarCampo(painelCampos, "Email:");

        // Linha 4
        campoDataNasc = adicionarCampo(painelCampos, "Data Nasc. (dd/MM/yyyy):");
        campoStatus = adicionarCampo(painelCampos, "Status (A/I):");

        // Linha 5
        campoSexo = adicionarCampo(painelCampos, "Sexo (M/F):");
        campoEndereco = adicionarCampo(painelCampos, "Endereço:");

        // Linha 6 (Especialidade + Espaço vazio para completar o grid)
        painelCampos.add(new JLabel("Especialidade:"));
        comboEspecialidade = new JComboBox<>();
        painelCampos.add(comboEspecialidade);

        // Preenche os 2 últimos slots da grade para ficar alinhado
        painelCampos.add(new JLabel());
        painelCampos.add(new JLabel());

        add(painelCampos, BorderLayout.NORTH);

        // ===========================
        //  BOTÕES
        // ===========================
        JPanel painelCentro = new JPanel(new BorderLayout(5, 5));
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        botaoSalvar = new JButton("Salvar / Atualizar");
        botaoEditar = new JButton("Carregar p/ Edição");
        botaoExcluir = new JButton("Excluir (por ID)");
        botaoListar = new JButton("Listar Todos");
        botaoLimpar = new JButton("Limpar Campos");

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoListar);
        painelBotoes.add(botaoLimpar);

        painelCentro.add(painelBotoes, BorderLayout.NORTH);
        add(painelCentro, BorderLayout.CENTER);

        // ===========================
        //  LISTA
        // ===========================
        areaLista = new JTextArea(10, 70);
        areaLista.setEditable(false);
        JScrollPane scrollLista = new JScrollPane(areaLista);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Lista de Médicos"));
        add(scrollLista, BorderLayout.SOUTH);

        // LISTENERS
        botaoSalvar.addActionListener(e -> salvarOuAtualizar());
        botaoEditar.addActionListener(e -> carregarParaEdicao());
        botaoExcluir.addActionListener(e -> excluir());
        botaoListar.addActionListener(e -> listar());
        botaoLimpar.addActionListener(e -> limparCampos());
    }

    private JTextField adicionarCampo(JPanel painel, String rotulo) {
        painel.add(new JLabel(rotulo));
        JTextField campo = new JTextField(15);
        painel.add(campo);
        return campo;
    }

    private void carregarEspecialidades() {
        try {
            EspecialidadeDAO dao = new EspecialidadeDAO();
            List<Especialidade> lista = dao.findAll();
            comboEspecialidade.removeAllItems();

            // Adiciona opção "Nenhum"
            Especialidade eNone = new Especialidade();
            eNone.setEspecialidadeId(0);
            comboEspecialidade.addItem(eNone);

            for (Especialidade e : lista) {
                comboEspecialidade.addItem(e);
            }
            comboEspecialidade.setSelectedIndex(0); // Seleciona "Nenhum"
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar especialidades: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoId.setText("");
        campoNome.setText("");
        campoCrm.setText("");
        campoCpf.setText("");
        campoTelefone.setText("");
        campoEmail.setText("");
        campoDataNasc.setText("");
        campoStatus.setText("");
        campoSexo.setText("");
        campoEndereco.setText("");
        comboEspecialidade.setSelectedIndex(0); // Seleciona "Nenhum"
    }

    private void salvarOuAtualizar() {
        try {
            String nome = campoNome.getText().trim();
            String crm = campoCrm.getText().trim();
            String cpf = campoCpf.getText().trim();
            String tel = campoTelefone.getText().trim();
            String email = campoEmail.getText().trim();
            String data = campoDataNasc.getText().trim();
            String status = campoStatus.getText().trim();
            String sexo = campoSexo.getText().trim();
            String end = campoEndereco.getText().trim();

            Especialidade esp = (Especialidade) comboEspecialidade.getSelectedItem();
            // Lógica para Especialidade: Se ID for 0 (Nenhum), passa null
            Integer espId = (esp != null && esp.getEspecialidadeId() != 0) ? esp.getEspecialidadeId() : null;

            if (nome.isEmpty() || crm.isEmpty() || data.isEmpty()) {
                throw new IllegalArgumentException("Nome, CRM e Data de Nascimento são obrigatórios.");
            }

            String idStr = campoId.getText().trim();
            boolean ok;

            if (!idStr.isEmpty()) {
                int id = Integer.parseInt(idStr);
                ok = controller.atualizarMedico(id, nome, crm, cpf, tel, email, data, status, sexo, end, espId);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Médico atualizado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao atualizar médico.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                ok = controller.cadastrarMedico(nome, crm, cpf, tel, email, data, status, sexo, end, espId);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Médico cadastrado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao cadastrar médico.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (ok) {
                limparCampos();
                listar();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido ao atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar/atualizar médico: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listar() {
        try {
            java.util.List<Medico> medicos = controller.listarTodos();
            StringBuilder sb = new StringBuilder();
            sb.append("--- Lista de Médicos ---\n");

            // Instancia o DAO de Especialidade para buscar o nome
            EspecialidadeDAO espDAO = new EspecialidadeDAO();

            if (medicos.isEmpty()) {
                sb.append("Nenhum médico cadastrado.");
            } else {
                for (Medico m : medicos) {
                    // Busca o nome da especialidade se o ID não for nulo/zero
                    String nomeEspecialidade = "Nenhuma";
                    if (m.getEspecialidadeId() != null && m.getEspecialidadeId() != 0) {
                        Especialidade esp = espDAO.findById(m.getEspecialidadeId());
                        if (esp != null) {
                            nomeEspecialidade = esp.getEspecialidadeNome();
                        }
                    }

                    sb.append("ID: ").append(m.getMedicoId())
                            .append(" | Nome: ").append(m.getMedicoNome())
                            .append(" | CRM: ").append(m.getMedicoCrm())
                            // CORREÇÃO: Mostra o Nome da Especialidade ao invés do ID
                            .append(" | Especialidade: ").append(nomeEspecialidade)
                            .append("\n");
                }
            }

            areaLista.setText(sb.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao listar médicos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarParaEdicao() {
        String idStr = JOptionPane.showInputDialog(this, "ID do médico para editar:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            Medico m = controller.buscarPorId(id);

            if (m == null) {
                JOptionPane.showMessageDialog(this,
                        "Médico não encontrado.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            campoId.setText(String.valueOf(m.getMedicoId()));
            campoNome.setText(m.getMedicoNome());
            campoCrm.setText(m.getMedicoCrm());
            campoCpf.setText(m.getMedicoCpf());
            campoTelefone.setText(m.getMedicoTelefone());
            campoEmail.setText(m.getMedicoEmail());
            campoDataNasc.setText(m.getMedicoDtnascimento() != null ?
                    new java.text.SimpleDateFormat("dd/MM/yyyy").format(m.getMedicoDtnascimento()) : "");
            campoStatus.setText(m.getMedicoStatus());
            campoSexo.setText(m.getMedicoSexo());
            campoEndereco.setText(m.getMedicoEndereco());

            // Seleciona a especialidade no combo
            if (m.getEspecialidadeId() != null) {
                for (int i = 0; i < comboEspecialidade.getItemCount(); i++) {
                    Especialidade esp = comboEspecialidade.getItemAt(i);
                    if (esp.getEspecialidadeId() == m.getEspecialidadeId()) {
                        comboEspecialidade.setSelectedIndex(i);
                        break;
                    }
                }
            } else {
                comboEspecialidade.setSelectedIndex(0); // Seleciona "Nenhum"
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        String idStr = JOptionPane.showInputDialog(this, "ID do médico para excluir:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            int op = JOptionPane.showConfirmDialog(this,
                    "Confirma excluir médico ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                boolean ok = controller.excluirMedico(id);
                if (ok) {
                    JOptionPane.showMessageDialog(this,
                            "Médico excluído com sucesso.",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listar();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao excluir médico.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaMedico().setVisible(true));
    }
}