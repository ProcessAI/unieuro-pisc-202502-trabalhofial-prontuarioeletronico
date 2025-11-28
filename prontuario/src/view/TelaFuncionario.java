package view;

import controller.ControllerFuncionario;
import model.Funcionario;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaFuncionario extends JFrame {

    private final ControllerFuncionario controller;

    private JTextField campoId;
    private JTextField campoNome;
    private JTextField campoFuncao;
    private JTextField campoCpf;
    private JTextField campoStatus;
    private JTextField campoEmail;
    private JTextField campoTelefone;
    private JTextField campoSexo;
    private JTextField campoDataNasc;
    private JTextField campoEndereco;

    private JTextArea areaLista;

    private JButton botaoSalvar;
    private JButton botaoListar;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private JButton botaoLimpar;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public TelaFuncionario() {
        controller = new ControllerFuncionario();
        setTitle("CRUD de Funcionários");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(5, 4, 5, 5));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        campoId = adicionarCampo(painelCampos, "ID (Edição):");
        campoId.setEditable(false);

        campoNome = adicionarCampo(painelCampos, "Nome:");
        campoFuncao = adicionarCampo(painelCampos, "Função (1 char):");
        campoCpf = adicionarCampo(painelCampos, "CPF (11 dígitos):");
        campoStatus = adicionarCampo(painelCampos, "Status (A/I):");
        campoEmail = adicionarCampo(painelCampos, "Email:");
        campoTelefone = adicionarCampo(painelCampos, "Telefone:");
        campoSexo = adicionarCampo(painelCampos, "Sexo (M/F):");
        campoDataNasc = adicionarCampo(painelCampos, "Data Nasc. (DD/MM/AAAA):");
        campoEndereco = adicionarCampo(painelCampos, "Endereço:");

        add(painelCampos, BorderLayout.NORTH);

        JPanel painelCentro = new JPanel(new BorderLayout(5, 5));

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

        areaLista = new JTextArea(10, 70);
        areaLista.setEditable(false);
        JScrollPane scrollLista = new JScrollPane(areaLista);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Lista de Funcionários"));
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
        campoFuncao.setText("");
        campoCpf.setText("");
        campoStatus.setText("");
        campoEmail.setText("");
        campoTelefone.setText("");
        campoSexo.setText("");
        campoDataNasc.setText("");
        campoEndereco.setText("");
        botaoSalvar.setText("Salvar Novo");
    }

    private void salvarOuAtualizar() {
        try {
            String nome = campoNome.getText().trim();
            String funcao = campoFuncao.getText().trim();
            String cpf = campoCpf.getText().trim();
            String status = campoStatus.getText().trim();
            String email = campoEmail.getText().trim();
            String telefone = campoTelefone.getText().trim();
            String sexo = campoSexo.getText().trim();
            String dataNascStr = campoDataNasc.getText().trim();
            String endereco = campoEndereco.getText().trim();

            if (nome.isEmpty() || dataNascStr.isEmpty()) {
                throw new IllegalArgumentException("Nome e Data de Nascimento são obrigatórios.");
            }

            java.util.Date parsed = formatter.parse(dataNascStr);
            Date dtnasc = new Date(parsed.getTime());

            String idText = campoId.getText().trim();
            boolean sucesso;

            if (!idText.isEmpty()) {
                int id = Integer.parseInt(idText);
                sucesso = controller.atualizarFuncionario(id, nome, funcao, cpf, status,
                        email, telefone, sexo, dtnasc, endereco);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Funcionário ID " + id + " atualizado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao atualizar funcionário.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                sucesso = controller.cadastrarFuncionario(nome, funcao, cpf, status,
                        email, telefone, sexo, dtnasc, endereco);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Funcionário cadastrado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao cadastrar funcionário.",
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
                    "Erro ao salvar/atualizar funcionário: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listar() {
        List<Funcionario> funcionarios = controller.listarTodos();
        StringBuilder sb = new StringBuilder();
        sb.append("--- Lista de Funcionários ---\n");

        if (funcionarios.isEmpty()) {
            sb.append("Nenhum funcionário cadastrado.");
        } else {
            for (Funcionario f : funcionarios) {
                String dataStr = "";
                if (f.getFuncionarioDtnascimento() != null) {
                    dataStr = formatter.format(f.getFuncionarioDtnascimento());
                }

                sb.append("ID: ").append(f.getFuncionarioId())
                        .append(" | Nome: ").append(f.getFuncionarioNome())
                        .append(" | Função: ").append(f.getFuncionarioFuncao())
                        .append(" | CPF: ").append(f.getFuncionarioCpf())
                        .append(" | Status: ").append(f.getFuncionarioStatus())
                        .append(" | Email: ").append(f.getFuncionarioEmail())
                        .append(" | Tel: ").append(f.getFuncionarioTelefone())
                        .append(" | Sexo: ").append(f.getFuncionarioSexo())
                        .append(" | Nasc: ").append(dataStr)
                        .append(" | Endereço: ").append(f.getFuncionarioEndereco())
                        .append("\n");
            }
        }

        areaLista.setText(sb.toString());
    }

    private void carregarParaEdicao() {
        String idStr = JOptionPane.showInputDialog(this,
                "Digite o ID do funcionário para CARREGAR:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            Funcionario f = controller.buscarPorId(id);

            if (f != null) {
                campoId.setText(String.valueOf(f.getFuncionarioId()));
                campoNome.setText(f.getFuncionarioNome());
                campoFuncao.setText(f.getFuncionarioFuncao());
                campoCpf.setText(f.getFuncionarioCpf());
                campoStatus.setText(f.getFuncionarioStatus());
                campoEmail.setText(f.getFuncionarioEmail());
                campoTelefone.setText(f.getFuncionarioTelefone());
                campoSexo.setText(f.getFuncionarioSexo());
                if (f.getFuncionarioDtnascimento() != null) {
                    campoDataNasc.setText(formatter.format(f.getFuncionarioDtnascimento()));
                } else {
                    campoDataNasc.setText("");
                }
                campoEndereco.setText(f.getFuncionarioEndereco());

                botaoSalvar.setText("Atualizar Funcionário");

                JOptionPane.showMessageDialog(this,
                        "Dados carregados para edição.",
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Funcionário com ID " + id + " não encontrado.",
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
                "Digite o ID do funcionário para EXCLUIR:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            int op = JOptionPane.showConfirmDialog(this,
                    "Confirma excluir o funcionário ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                boolean sucesso = controller.excluirFuncionario(id);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Funcionário excluído com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listar();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao excluir funcionário.",
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
        SwingUtilities.invokeLater(() -> new TelaFuncionario().setVisible(true));
    }
}
