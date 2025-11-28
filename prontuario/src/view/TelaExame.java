package view;

import controller.ControllerExame;
import model.Exame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaExame extends JFrame {

    private final ControllerExame controller;

    private JTextField campoId;
    private JTextField campoNome;
    private JTextField campoTipo;
    private JTextField campoStatus;
    private JTextField campoDuracao;   // minutos
    private JTextArea areaOrientacao;

    private JTextArea areaLista;

    private JButton botaoSalvar;
    private JButton botaoListar;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private JButton botaoLimpar;

    public TelaExame() {
        controller = new ControllerExame();
        setTitle("CRUD de Exames");
        setSize(800, 650);
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
        campoTipo = adicionarCampo(painelCampos, "Tipo (1 char):");
        campoStatus = adicionarCampo(painelCampos, "Status (A/I):");
        campoDuracao = adicionarCampo(painelCampos, "Duração (minutos):");

        add(painelCampos, BorderLayout.NORTH);

        JPanel painelCentro = new JPanel(new BorderLayout(5, 5));

        JPanel painelOrientacao = new JPanel(new BorderLayout(5, 5));
        painelOrientacao.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
        painelOrientacao.add(new JLabel("Orientação:"), BorderLayout.NORTH);

        areaOrientacao = new JTextArea(4, 20);
        areaOrientacao.setLineWrap(true);
        areaOrientacao.setWrapStyleWord(true);
        JScrollPane scrollOrient = new JScrollPane(areaOrientacao);
        painelOrientacao.add(scrollOrient, BorderLayout.CENTER);

        painelCentro.add(painelOrientacao, BorderLayout.NORTH);

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

        areaLista = new JTextArea(10, 60);
        areaLista.setEditable(false);
        JScrollPane scrollLista = new JScrollPane(areaLista);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Lista de Exames"));

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
        campoStatus.setText("");
        campoDuracao.setText("");
        areaOrientacao.setText("");
        botaoSalvar.setText("Salvar Novo");
    }

    private Integer parseDuracao(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        return Integer.valueOf(texto.trim());
    }

    private void salvarOuAtualizar() {
        try {
            String nome = campoNome.getText().trim();
            String tipo = campoTipo.getText().trim();
            String status = campoStatus.getText().trim();
            String orientacao = areaOrientacao.getText().trim();
            String duracaoStr = campoDuracao.getText().trim();

            if (nome.isEmpty()) {
                throw new IllegalArgumentException("Nome é obrigatório.");
            }

            Integer duracao = null;
            if (!duracaoStr.isEmpty()) {
                duracao = parseDuracao(duracaoStr);
                if (duracao <= 0) {
                    throw new IllegalArgumentException("Duração deve ser um número positivo (em minutos).");
                }
            }

            String idText = campoId.getText().trim();
            boolean sucesso;

            if (!idText.isEmpty()) {
                int id = Integer.parseInt(idText);
                sucesso = controller.atualizarExame(id, nome, tipo, status, orientacao, duracao);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Exame ID " + id + " atualizado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao atualizar exame.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                sucesso = controller.cadastrarExame(nome, tipo, status, orientacao, duracao);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Exame cadastrado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao cadastrar exame.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (sucesso) {
                limparCampos();
                listar();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Duração inválida. Digite apenas números (minutos).",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar/atualizar exame: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listar() {
        List<Exame> exames = controller.listarTodos();
        StringBuilder sb = new StringBuilder();
        sb.append("--- Lista de Exames ---\n");

        if (exames.isEmpty()) {
            sb.append("Nenhum exame cadastrado.");
        } else {
            for (Exame e : exames) {
                sb.append("ID: ").append(e.getExameId())
                        .append(" | Nome: ").append(e.getExameNome())
                        .append(" | Tipo: ").append(e.getExameTipo())
                        .append(" | Status: ").append(e.getExameStatus())
                        .append(" | Duração: ");
                if (e.getDuracaoMinutos() != null) {
                    sb.append(e.getDuracaoMinutos()).append(" min");
                } else {
                    sb.append("N/D");
                }
                sb.append(" | Orientação: ").append(e.getExameOrientacao())
                        .append("\n");
            }
        }

        areaLista.setText(sb.toString());
    }

    private void carregarParaEdicao() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do exame para CARREGAR:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            Exame e = controller.buscarPorId(id);

            if (e != null) {
                campoId.setText(String.valueOf(e.getExameId()));
                campoNome.setText(e.getExameNome());
                campoTipo.setText(e.getExameTipo());
                campoStatus.setText(e.getExameStatus());
                areaOrientacao.setText(e.getExameOrientacao());

                if (e.getDuracaoMinutos() != null) {
                    campoDuracao.setText(String.valueOf(e.getDuracaoMinutos()));
                } else {
                    campoDuracao.setText("");
                }

                botaoSalvar.setText("Atualizar Exame");

                JOptionPane.showMessageDialog(this,
                        "Dados carregados para edição.",
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Exame com ID " + id + " não encontrado.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido. Digite apenas números.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do exame para EXCLUIR:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            int op = JOptionPane.showConfirmDialog(this,
                    "Confirma excluir o exame ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                boolean sucesso = controller.excluirExame(id);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Exame excluído com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listar();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao excluir exame.",
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
        SwingUtilities.invokeLater(() -> new TelaExame().setVisible(true));
    }
}
