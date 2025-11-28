package view;

import controller.MedicamentoController;
import model.Medicamento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaMedicamento extends JFrame {

    private final MedicamentoController controller;

    private JTextField campoId;
    private JTextField campoNome;
    private JTextField campoTipo;
    private JTextField campoTarja;
    private JTextField campoPrincipioAtivo;

    private JTextArea areaBula;
    private JTextArea areaObservacao;

    private JTextArea areaLista;

    private JButton botaoSalvar;
    private JButton botaoListar;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private JButton botaoLimpar;

    public TelaMedicamento() {
        controller = new MedicamentoController();
        setTitle("Cadastro de Medicamentos");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // ===========================
        //  CAMPOS SUPERIORES
        // ===========================
        JPanel painelCampos = new JPanel(new GridLayout(3, 4, 5, 5));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        campoId = adicionarCampo(painelCampos, "ID (Edição):");
        campoId.setEditable(false);

        campoNome = adicionarCampo(painelCampos, "Nome do Medicamento:");
        campoTipo = adicionarCampo(painelCampos, "Tipo (1 char):");
        campoTarja = adicionarCampo(painelCampos, "Tarja (1 char):");
        campoPrincipioAtivo = adicionarCampo(painelCampos, "Princípio Ativo:");

        // deixa dois “buracos” pra completar o grid 3x4
        painelCampos.add(new JLabel());
        painelCampos.add(new JLabel());

        add(painelCampos, BorderLayout.NORTH);

        // ===========================
        //  CENTRO: Bula + Observação + Botões
        // ===========================
        JPanel painelCentro = new JPanel(new BorderLayout(5, 5));

        // Bula e Observação lado a lado
        JPanel painelTextos = new JPanel(new GridLayout(1, 2, 5, 5));
        painelTextos.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));

        // BULA
        JPanel painelBula = new JPanel(new BorderLayout(5, 5));
        painelBula.add(new JLabel("Bula:"), BorderLayout.NORTH);
        areaBula = new JTextArea(6, 30);
        areaBula.setLineWrap(true);
        areaBula.setWrapStyleWord(true);
        JScrollPane scrollBula = new JScrollPane(areaBula);
        painelBula.add(scrollBula, BorderLayout.CENTER);

        // OBSERVAÇÃO
        JPanel painelObs = new JPanel(new BorderLayout(5, 5));
        painelObs.add(new JLabel("Observações:"), BorderLayout.NORTH);
        areaObservacao = new JTextArea(6, 30);
        areaObservacao.setLineWrap(true);
        areaObservacao.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(areaObservacao);
        painelObs.add(scrollObs, BorderLayout.CENTER);

        painelTextos.add(painelBula);
        painelTextos.add(painelObs);

        painelCentro.add(painelTextos, BorderLayout.NORTH);

        // BOTÕES
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botaoSalvar = new JButton("Salvar Novo");
        botaoListar = new JButton("Listar Todos");
        botaoEditar = new JButton("Carregar p/ Edição");
        botaoExcluir = new JButton("Excluir (por ID)");
        botaoLimpar = new JButton("Limpar Campos");

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoListar);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoLimpar);

        painelCentro.add(painelBotoes, BorderLayout.CENTER);

        add(painelCentro, BorderLayout.CENTER);

        // ===========================
        //  LISTA INFERIOR
        // ===========================
        areaLista = new JTextArea(10, 70);
        areaLista.setEditable(false);
        JScrollPane scrollLista = new JScrollPane(areaLista);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Lista de Medicamentos"));
        add(scrollLista, BorderLayout.SOUTH);

        // ===========================
        //  LISTENERS
        // ===========================
        botaoSalvar.addActionListener(e -> salvarOuAtualizar());
        botaoListar.addActionListener(e -> listar());
        botaoEditar.addActionListener(e -> carregarParaEdicao());
        botaoExcluir.addActionListener(e -> excluir());
        botaoLimpar.addActionListener(e -> limparCampos());
    }

    private JTextField adicionarCampo(JPanel painel, String rotulo) {
        painel.add(new JLabel(rotulo));
        JTextField campo = new JTextField(15);
        painel.add(campo);
        return campo;
    }

    private void limparCampos() {
        campoId.setText("");
        campoNome.setText("");
        campoTipo.setText("");
        campoTarja.setText("");
        campoPrincipioAtivo.setText("");
        areaBula.setText("");
        areaObservacao.setText("");
        botaoSalvar.setText("Salvar Novo");
    }

    private void salvarOuAtualizar() {
        try {
            String nome = campoNome.getText().trim();
            String tipo = campoTipo.getText().trim();
            String tarja = campoTarja.getText().trim();
            String principio = campoPrincipioAtivo.getText().trim();
            String bula = areaBula.getText().trim();
            String obs = areaObservacao.getText().trim();

            if (nome.isEmpty()) {
                throw new IllegalArgumentException("Nome do medicamento é obrigatório.");
            }

            String idText = campoId.getText().trim();
            boolean sucesso;

            if (!idText.isEmpty()) {
                int id = Integer.parseInt(idText);
                sucesso = controller.atualizar(id, nome, bula, tipo, tarja, principio, obs);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Medicamento ID " + id + " atualizado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao atualizar medicamento.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                sucesso = controller.cadastrar(nome, bula, tipo, tarja, principio, obs);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Medicamento cadastrado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao cadastrar medicamento.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (sucesso) {
                limparCampos();
                listar();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido (erro interno ao converter).",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar/atualizar medicamento: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listar() {
        List<Medicamento> meds = controller.listarTodos();
        StringBuilder sb = new StringBuilder();
        sb.append("--- Lista de Medicamentos ---\n");

        if (meds.isEmpty()) {
            sb.append("Nenhum medicamento cadastrado.");
        } else {
            for (Medicamento m : meds) {
                sb.append("ID: ").append(m.getMedicamentoId())
                        .append(" | Nome: ").append(m.getMedicamentoNome())
                        .append(" | Tipo: ").append(m.getTipo())
                        .append(" | Tarja: ").append(m.getTarja())
                        .append(" | Princípio Ativo: ").append(m.getPrincipioAtivo())
                        .append("\n  Bula: ").append(m.getBula() == null ? "" : m.getBula())
                        .append("\n  Observação: ").append(m.getObservacao() == null ? "" : m.getObservacao())
                        .append("\n----------------------------------------\n");
            }
        }

        areaLista.setText(sb.toString());
    }

    private void carregarParaEdicao() {
        String idStr = JOptionPane.showInputDialog(this,
                "Digite o ID do medicamento para CARREGAR:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            Medicamento m = controller.buscarPorId(id);

            if (m != null) {
                campoId.setText(String.valueOf(m.getMedicamentoId()));
                campoNome.setText(m.getMedicamentoNome());
                campoTipo.setText(m.getTipo());
                campoTarja.setText(m.getTarja());
                campoPrincipioAtivo.setText(m.getPrincipioAtivo());
                areaBula.setText(m.getBula());
                areaObservacao.setText(m.getObservacao());

                botaoSalvar.setText("Atualizar Medicamento");

                JOptionPane.showMessageDialog(this,
                        "Dados carregados para edição.",
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Medicamento com ID " + id + " não encontrado.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido. Digite apenas números.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        String idStr = JOptionPane.showInputDialog(this,
                "Digite o ID do medicamento para EXCLUIR:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            int op = JOptionPane.showConfirmDialog(this,
                    "Confirma excluir o medicamento ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                boolean sucesso = controller.excluir(id);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Medicamento excluído com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listar();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao excluir medicamento.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido. Digite apenas números.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaMedicamento().setVisible(true));
    }
}
