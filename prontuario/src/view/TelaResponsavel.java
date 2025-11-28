package view;

import controller.ControllerResponsavel;
import model.Responsavel;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaResponsavel extends JFrame {

    private final ControllerResponsavel controller;

    // Campos
    private JTextField campoId;
    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoDataNasc;
    private JTextField campoTelefone;
    private JTextField campoEmail;
    private JTextField campoEndereco;
    private JTextField campoParentesco;
    private JTextArea areaObservacoes;

    private JTextArea areaLista;
    private JButton botaoSalvar;
    private JButton botaoListar;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private JButton botaoLimpar;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public TelaResponsavel() {
        controller = new ControllerResponsavel();
        setTitle("CRUD de Responsáveis");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // ---------- Painel de campos (topo) ----------
        JPanel painelCampos = new JPanel(new GridLayout(5, 4, 5, 5));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        campoId = adicionarCampo(painelCampos, "ID (Edição):");
        campoId.setEditable(false);

        campoNome = adicionarCampo(painelCampos, "Nome:");
        campoCpf = adicionarCampo(painelCampos, "CPF (11 dígitos):");
        campoDataNasc = adicionarCampo(painelCampos, "Data Nasc. (DD/MM/AAAA):");
        campoTelefone = adicionarCampo(painelCampos, "Telefone:");
        campoEmail = adicionarCampo(painelCampos, "Email:");
        campoEndereco = adicionarCampo(painelCampos, "Endereço:");
        campoParentesco = adicionarCampo(painelCampos, "Parentesco:");

        add(painelCampos, BorderLayout.NORTH);

        // ---------- Painel de observações (meio) ----------
        JPanel painelCentro = new JPanel(new BorderLayout(5, 5));

        JPanel painelObs = new JPanel(new BorderLayout(5, 5));
        painelObs.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
        painelObs.add(new JLabel("Observações:"), BorderLayout.NORTH);

        areaObservacoes = new JTextArea(4, 20);
        areaObservacoes.setLineWrap(true);
        areaObservacoes.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(areaObservacoes);
        painelObs.add(scrollObs, BorderLayout.CENTER);

        painelCentro.add(painelObs, BorderLayout.NORTH);

        // ---------- Painel de botões ----------
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

        // ---------- Lista de responsáveis (baixo) ----------
        areaLista = new JTextArea(10, 60);
        areaLista.setEditable(false);
        JScrollPane scrollLista = new JScrollPane(areaLista);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Lista de Responsáveis"));
        add(scrollLista, BorderLayout.SOUTH);

        // ---------- Ações ----------
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
        campoCpf.setText("");
        campoDataNasc.setText("");
        campoTelefone.setText("");
        campoEmail.setText("");
        campoEndereco.setText("");
        campoParentesco.setText("");
        areaObservacoes.setText("");
        botaoSalvar.setText("Salvar Novo");
    }

    private void salvarOuAtualizar() {
        try {
            if (campoNome.getText().trim().isEmpty() ||
                    campoCpf.getText().trim().isEmpty() ||
                    campoDataNasc.getText().trim().isEmpty()) {

                throw new IllegalArgumentException("Nome, CPF e Data de Nascimento são obrigatórios.");
            }

            // Data dd/MM/yyyy -> java.sql.Date
            java.util.Date parsed = formatter.parse(campoDataNasc.getText().trim());
            Date dnas = new Date(parsed.getTime());

            String nome = campoNome.getText().trim();
            String cpf = campoCpf.getText().trim();
            String telefone = campoTelefone.getText().trim();
            String email = campoEmail.getText().trim();
            String endereco = campoEndereco.getText().trim();
            String parentesco = campoParentesco.getText().trim();
            String observacoes = areaObservacoes.getText().trim();

            String idText = campoId.getText().trim();
            boolean sucesso;

            if (!idText.isEmpty()) {
                // Atualização
                int id = Integer.parseInt(idText);
                sucesso = controller.atualizarResponsavel(
                        id, nome, cpf, dnas, telefone, email, endereco, parentesco, observacoes
                );
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Responsável ID " + id + " atualizado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao atualizar responsável.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Inserção
                sucesso = controller.cadastrarResponsavel(
                        nome, cpf, dnas, telefone, email, endereco, parentesco, observacoes
                );
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Responsável cadastrado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao cadastrar responsável.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (sucesso) {
                limparCampos();
                listar();
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Data inválida. Use o formato DD/MM/AAAA.",
                    "Erro de Data", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar/atualizar responsável: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listar() {
        List<Responsavel> lista = controller.listarTodos();
        StringBuilder sb = new StringBuilder();
        sb.append("--- Lista de Responsáveis ---\n");

        if (lista.isEmpty()) {
            sb.append("Nenhum responsável cadastrado.");
        } else {
            for (Responsavel r : lista) {
                String dataStr = "";
                if (r.getDataNascimento() != null) {
                    dataStr = formatter.format(r.getDataNascimento());
                }
                sb.append("ID: ").append(r.getId())
                        .append(" | Nome: ").append(r.getNome())
                        .append(" | CPF: ").append(r.getCpf())
                        .append(" | D.Nasc: ").append(dataStr)
                        .append(" | Tel: ").append(r.getTelefone())
                        .append(" | Email: ").append(r.getEmail())
                        .append(" | Parentesco: ").append(r.getParentesco())
                        .append(" | Obs: ").append(r.getObservacoes() != null ? r.getObservacoes() : "")
                        .append("\n");
            }
        }

        areaLista.setText(sb.toString());
    }

    private void carregarParaEdicao() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do responsável para CARREGAR:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            Responsavel r = controller.buscarPorId(id);

            if (r != null) {
                campoId.setText(String.valueOf(r.getId()));
                campoNome.setText(r.getNome());
                campoCpf.setText(r.getCpf());
                if (r.getDataNascimento() != null) {
                    campoDataNasc.setText(formatter.format(r.getDataNascimento()));
                } else {
                    campoDataNasc.setText("");
                }
                campoTelefone.setText(r.getTelefone());
                campoEmail.setText(r.getEmail());
                campoEndereco.setText(r.getEndereco());
                campoParentesco.setText(r.getParentesco());
                areaObservacoes.setText(r.getObservacoes());

                botaoSalvar.setText("Atualizar Responsável");

                JOptionPane.showMessageDialog(this,
                        "Dados carregados para edição.",
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Responsável com ID " + id + " não encontrado.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido. Digite apenas números.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do responsável para EXCLUIR:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            int opc = JOptionPane.showConfirmDialog(this,
                    "Confirma excluir o responsável ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (opc == JOptionPane.YES_OPTION) {
                boolean sucesso = controller.excluirResponsavel(id);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Responsável excluído com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listar();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao excluir responsável.",
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
        SwingUtilities.invokeLater(() -> new TelaResponsavel().setVisible(true));
    }
}
