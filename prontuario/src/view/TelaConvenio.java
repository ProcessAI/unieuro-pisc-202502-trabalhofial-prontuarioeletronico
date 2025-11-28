package view;

import controller.ControllerConvenio;
import model.Convenio;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaConvenio extends JFrame {

    private final ControllerConvenio controller;

    private JTextField campoId;
    private JTextField campoNome;
    private JTextField campoTipo;
    private JTextField campoArea;
    private JTextField campoCoparticipacao;
    private JTextField campoStatus;

    private JTextArea areaLista;

    private JButton botaoSalvar;
    private JButton botaoListar;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private JButton botaoLimpar;

    public TelaConvenio() {
        controller = new ControllerConvenio();
        setTitle("CRUD de Convênios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(3, 4, 5, 5));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        campoId = adicionarCampo(painelCampos, "ID (Edição):");
        campoId.setEditable(false);

        campoNome = adicionarCampo(painelCampos, "Nome:");
        campoTipo = adicionarCampo(painelCampos, "Tipo:");
        campoArea = adicionarCampo(painelCampos, "Área:");
        campoCoparticipacao = adicionarCampo(painelCampos, "Coparticipação (S/N):");
        campoStatus = adicionarCampo(painelCampos, "Status (A/I):");

        add(painelCampos, BorderLayout.NORTH);

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

        add(painelBotoes, BorderLayout.CENTER);

        areaLista = new JTextArea(10, 60);
        areaLista.setEditable(false);
        JScrollPane scrollLista = new JScrollPane(areaLista);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Lista de Convênios"));

        add(scrollLista, BorderLayout.SOUTH);

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
        campoArea.setText("");
        campoCoparticipacao.setText("");
        campoStatus.setText("");
        botaoSalvar.setText("Salvar Novo");
    }

    private void salvarOuAtualizar() {
        try {
            String nome = campoNome.getText().trim();
            String tipo = campoTipo.getText().trim();
            String area = campoArea.getText().trim();
            String cop = campoCoparticipacao.getText().trim();
            String status = campoStatus.getText().trim();

            if (nome.isEmpty()) {
                throw new IllegalArgumentException("Nome é obrigatório.");
            }

            String idText = campoId.getText().trim();
            boolean sucesso;

            if (!idText.isEmpty()) {
                int id = Integer.parseInt(idText);
                sucesso = controller.atualizarConvenio(id, nome, tipo, area, cop, status);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Convênio ID " + id + " atualizado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao atualizar convênio.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                sucesso = controller.cadastrarConvenio(nome, tipo, area, cop, status);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Convênio cadastrado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao cadastrar convênio.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (sucesso) {
                limparCampos();
                listar();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar/atualizar convênio: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listar() {
        List<Convenio> lista = controller.listarTodos();
        StringBuilder sb = new StringBuilder();
        sb.append("--- Lista de Convênios ---\n");

        if (lista.isEmpty()) {
            sb.append("Nenhum convênio cadastrado.");
        } else {
            for (Convenio c : lista) {
                sb.append("ID: ").append(c.getIdconvenio())
                        .append(" | Nome: ").append(c.getConvenioNome())
                        .append(" | Tipo: ").append(c.getConvenioTipo())
                        .append(" | Área: ").append(c.getConvenioArea())
                        .append(" | Copart.: ").append(c.getCoparticipacao())
                        .append(" | Status: ").append(c.getConvenioStatus())
                        .append("\n");
            }
        }

        areaLista.setText(sb.toString());
    }

    private void carregarParaEdicao() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do convênio para CARREGAR:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            Convenio c = controller.buscarPorId(id);
            if (c != null) {
                campoId.setText(String.valueOf(c.getIdconvenio()));
                campoNome.setText(c.getConvenioNome());
                campoTipo.setText(c.getConvenioTipo());
                campoArea.setText(c.getConvenioArea());
                campoCoparticipacao.setText(c.getCoparticipacao());
                campoStatus.setText(c.getConvenioStatus());
                botaoSalvar.setText("Atualizar Convênio");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Convênio com ID " + id + " não encontrado.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido. Digite apenas números.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do convênio para EXCLUIR:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            int op = JOptionPane.showConfirmDialog(this,
                    "Confirma excluir o convênio ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                boolean sucesso = controller.excluirConvenio(id);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Convênio excluído com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listar();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao excluir convênio.",
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
        SwingUtilities.invokeLater(() -> new TelaConvenio().setVisible(true));
    }
}
