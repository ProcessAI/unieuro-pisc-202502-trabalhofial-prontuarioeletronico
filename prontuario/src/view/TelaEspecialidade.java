package view;

import controller.EspecialidadeController;
import model.Especialidade;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaEspecialidade extends JFrame {

    private final EspecialidadeController controller;

    private JTextField campoId;
    private JTextField campoNome;
    private JTextField campoStatus;
    private JTextField campoCbo;
    private JTextArea  areaEscala;
    private JTextArea  areaDescricao;

    private JTextArea areaLista;

    private JButton botaoSalvar;
    private JButton botaoListar;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private JButton botaoLimpar;

    public TelaEspecialidade() {
        controller = new EspecialidadeController();
        setTitle("Cadastro de Especialidades");
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

        campoNome = adicionarCampo(painelCampos, "Nome da Especialidade:");
        campoStatus = adicionarCampo(painelCampos, "Status (A/I):");
        campoCbo = adicionarCampo(painelCampos, "CBO:");
        // completa células do grid
        painelCampos.add(new JLabel());
        painelCampos.add(new JLabel());

        add(painelCampos, BorderLayout.NORTH);

        // ===========================
        //  CENTRO: Escala + Descrição + Botões
        // ===========================
        JPanel painelCentro = new JPanel(new BorderLayout(5, 5));

        JPanel painelTextos = new JPanel(new GridLayout(1, 2, 5, 5));
        painelTextos.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));

        // ESCALA
        JPanel painelEscala = new JPanel(new BorderLayout(5, 5));
        painelEscala.add(new JLabel("Escala:"), BorderLayout.NORTH);
        areaEscala = new JTextArea(6, 30);
        areaEscala.setLineWrap(true);
        areaEscala.setWrapStyleWord(true);
        JScrollPane scrollEscala = new JScrollPane(areaEscala);
        painelEscala.add(scrollEscala, BorderLayout.CENTER);

        // DESCRIÇÃO
        JPanel painelDescricao = new JPanel(new BorderLayout(5, 5));
        painelDescricao.add(new JLabel("Descrição:"), BorderLayout.NORTH);
        areaDescricao = new JTextArea(6, 30);
        areaDescricao.setLineWrap(true);
        areaDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(areaDescricao);
        painelDescricao.add(scrollDescricao, BorderLayout.CENTER);

        painelTextos.add(painelEscala);
        painelTextos.add(painelDescricao);

        painelCentro.add(painelTextos, BorderLayout.NORTH);

        // BOTÕES
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botaoSalvar = new JButton("Salvar Novo");
        botaoListar = new JButton("Listar Todas");
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
        scrollLista.setBorder(BorderFactory.createTitledBorder("Lista de Especialidades"));
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
        campoStatus.setText("");
        campoCbo.setText("");
        areaEscala.setText("");
        areaDescricao.setText("");
        botaoSalvar.setText("Salvar Novo");
    }

    private void salvarOuAtualizar() {
        try {
            String nome = campoNome.getText().trim();
            String status = campoStatus.getText().trim();
            String cbo = campoCbo.getText().trim();
            String escala = areaEscala.getText().trim();
            String descricao = areaDescricao.getText().trim();

            if (nome.isEmpty()) {
                throw new IllegalArgumentException("Nome da especialidade é obrigatório.");
            }

            String idText = campoId.getText().trim();
            boolean sucesso;

            if (!idText.isEmpty()) {
                int id = Integer.parseInt(idText);
                sucesso = controller.atualizarEspecialidade(id, nome, status, cbo, escala, descricao);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Especialidade ID " + id + " atualizada com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao atualizar especialidade.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                sucesso = controller.cadastrarEspecialidade(nome, status, cbo, escala, descricao);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Especialidade cadastrada com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao cadastrar especialidade.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (sucesso) {
                limparCampos();
                listar();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido (erro ao converter).",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar/atualizar especialidade: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listar() {
        List<Especialidade> especialidades = controller.listarTodas();
        StringBuilder sb = new StringBuilder();
        sb.append("--- Lista de Especialidades ---\n");

        if (especialidades.isEmpty()) {
            sb.append("Nenhuma especialidade cadastrada.");
        } else {
            for (Especialidade e : especialidades) {
                sb.append("ID: ").append(e.getEspecialidadeId())
                        .append(" | Nome: ").append(e.getEspecialidadeNome())
                        .append(" | Status: ").append(e.getEspecialidadeStatus())
                        .append(" | CBO: ").append(e.getEspecialidadeCbo())
                        .append("\n  Escala: ").append(e.getEspecialidadeEscala() == null ? "" : e.getEspecialidadeEscala())
                        .append("\n  Descrição: ").append(e.getEspecialidadeDescricao() == null ? "" : e.getEspecialidadeDescricao())
                        .append("\n----------------------------------------\n");
            }
        }

        areaLista.setText(sb.toString());
    }

    private void carregarParaEdicao() {
        String idStr = JOptionPane.showInputDialog(this,
                "Digite o ID da especialidade para CARREGAR:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            Especialidade e = controller.buscarPorId(id);

            if (e != null) {
                campoId.setText(String.valueOf(e.getEspecialidadeId()));
                campoNome.setText(e.getEspecialidadeNome());
                campoStatus.setText(e.getEspecialidadeStatus());
                campoCbo.setText(e.getEspecialidadeCbo());
                areaEscala.setText(e.getEspecialidadeEscala());
                areaDescricao.setText(e.getEspecialidadeDescricao());

                botaoSalvar.setText("Atualizar Especialidade");

                JOptionPane.showMessageDialog(this,
                        "Dados carregados para edição.",
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Especialidade com ID " + id + " não encontrada.",
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
                "Digite o ID da especialidade para EXCLUIR:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            int op = JOptionPane.showConfirmDialog(this,
                    "Confirma excluir a especialidade ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                boolean sucesso = controller.excluirEspecialidade(id);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Especialidade excluída com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listar();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao excluir especialidade.",
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
        SwingUtilities.invokeLater(() -> new TelaEspecialidade().setVisible(true));
    }
}
