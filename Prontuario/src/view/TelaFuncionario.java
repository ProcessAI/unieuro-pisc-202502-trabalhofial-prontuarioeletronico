package view;

import controller.ControllerFuncionario;
import model.Funcionario;
import javax.swing.JOptionPane;

public class TelaFuncionario extends javax.swing.JFrame {

    ControllerFuncionario controller = new ControllerFuncionario();

    public TelaFuncionario() {
        initComponents();
        setLocationRelativeTo(null);

        txtLista.setText(controller.listarAtivos());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabelNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabelFuncao = new javax.swing.JLabel();
        txtFuncao = new javax.swing.JTextField();
        jLabelCpf = new javax.swing.JLabel();
        txtCpf = new javax.swing.JTextField();
        jLabelStatus = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        jLabelEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabelTelefone = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JTextField();
        jLabelSexo = new javax.swing.JLabel();
        txtSexo = new javax.swing.JTextField();
        jLabelNascimento = new javax.swing.JLabel();
        txtNascimento = new javax.swing.JTextField();
        jLabelEndereco = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        btnListarTodos = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLista = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CRUD de Funcionários");

        jPanel1.setLayout(null);

        jLabelId.setText("ID Carregado:"); 
        jPanel1.add(jLabelId);
        jLabelId.setBounds(20, 10, 150, 20);

        txtId.setEditable(false); 
        txtId.setEnabled(true);
        jPanel1.add(txtId);
        txtId.setBounds(20, 30, 120, 25);

        jLabelNome.setText("Nome:");
        jPanel1.add(jLabelNome);
        jLabelNome.setBounds(170, 10, 150, 20);

        jPanel1.add(txtNome);
        txtNome.setBounds(170, 30, 150, 25);

        jLabelFuncao.setText("Função:");
        jPanel1.add(jLabelFuncao);
        jLabelFuncao.setBounds(20, 70, 180, 20);

        jPanel1.add(txtFuncao);
        txtFuncao.setBounds(20, 90, 150, 25);

        jLabelCpf.setText("CPF:");
        jPanel1.add(jLabelCpf);
        jLabelCpf.setBounds(200, 70, 80, 20);

        jPanel1.add(txtCpf);
        txtCpf.setBounds(200, 90, 150, 25);

        jLabelStatus.setText("Status (A/I):");
        jPanel1.add(jLabelStatus);
        jLabelStatus.setBounds(380, 70, 100, 20);

        jPanel1.add(txtStatus);
        txtStatus.setBounds(380, 90, 80, 25);

        jLabelEmail.setText("Email:");
        jPanel1.add(jLabelEmail);
        jLabelEmail.setBounds(380, 10, 200, 20);

        jPanel1.add(txtEmail);
        txtEmail.setBounds(380, 30, 200, 25);

        jLabelTelefone.setText("Telefone:");
        jPanel1.add(jLabelTelefone);
        jLabelTelefone.setBounds(20, 130, 100, 20);

        jPanel1.add(txtTelefone);
        txtTelefone.setBounds(20, 150, 150, 25);

        jLabelSexo.setText("Sexo (M/F):");
        jPanel1.add(jLabelSexo);
        jLabelSexo.setBounds(200, 130, 150, 20);

        jPanel1.add(txtSexo);
        txtSexo.setBounds(200, 150, 80, 25);

        jLabelNascimento.setText("Nascimento (AAAA-MM-DD):");
        jPanel1.add(jLabelNascimento);
        jLabelNascimento.setBounds(310, 130, 200, 20);

        jPanel1.add(txtNascimento);
        txtNascimento.setBounds(310, 150, 150, 25);

        jLabelEndereco.setText("Endereço:");
        jPanel1.add(jLabelEndereco);
        jLabelEndereco.setBounds(20, 190, 100, 20);

        jPanel1.add(txtEndereco);
        txtEndereco.setBounds(20, 210, 280, 25);

        btnSalvar.setText("Salvar/Atualizar"); 
        btnSalvar.addActionListener(evt -> salvar());
        jPanel1.add(btnSalvar);
        btnSalvar.setBounds(20, 260, 120, 30);

        btnListarTodos.setText("Listar Todos");
        btnListarTodos.addActionListener(evt -> listarTodos());
        jPanel1.add(btnListarTodos);
        btnListarTodos.setBounds(160, 260, 120, 30);

        btnEditar.setText("Carregar p/ Edição"); 
        btnEditar.addActionListener(evt -> carregarEdicao());
        jPanel1.add(btnEditar);
        btnEditar.setBounds(300, 260, 150, 30);

        btnExcluir.setText("Excluir (Pergunta ID)"); 
        btnExcluir.addActionListener(evt -> excluir());
        jPanel1.add(btnExcluir);
        btnExcluir.setBounds(470, 260, 150, 30);

        btnLimpar.setText("Limpar Campos");
        btnLimpar.addActionListener(evt -> limparCampos());
        jPanel1.add(btnLimpar);
        btnLimpar.setBounds(640, 260, 120, 30);

        txtLista.setEditable(false);
        txtLista.setColumns(20);
        txtLista.setRows(5);
        jScrollPane1.setViewportView(txtLista);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 310, 760, 200);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 800, 530);

        setSize(800, 550);
    }

    // -----------------------------
    // MÉTODOS DOS BOTÕES
    // -----------------------------

    private void salvar() {
        String idText = txtId.getText().trim();
        boolean ok;

        if (idText.isEmpty()) {
            ok = controller.salvar(
                    txtNome.getText(),
                    txtFuncao.getText(),
                    txtCpf.getText(),
                    txtStatus.getText(),
                    txtEmail.getText(),
                    txtTelefone.getText(),
                    txtSexo.getText(),
                    txtNascimento.getText(),
                    txtEndereco.getText()
            );
        } else {
            ok = controller.editar(
                    idText, 
                    txtNome.getText(),
                    txtFuncao.getText(),
                    txtCpf.getText(),
                    txtStatus.getText(),
                    txtEmail.getText(),
                    txtTelefone.getText(),
                    txtSexo.getText(),
                    txtNascimento.getText(),
                    txtEndereco.getText()
            );
        }

        if (ok) {
            JOptionPane.showMessageDialog(this, "Operação realizada com sucesso!");
            txtLista.setText(controller.listarAtivos());
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao realizar a operação! Verifique os dados (ex: formato da data).");
        }
    }

    private void listarTodos() {
        txtLista.setText(controller.listarTodos());
    }

    private void carregarEdicao() {
        String idText = JOptionPane.showInputDialog(this, 
                "Digite o ID do funcionário para carregar para edição:", 
                "Carregar para Edição", 
                JOptionPane.QUESTION_MESSAGE);

        if (idText == null || idText.trim().isEmpty()) {
            limparCampos();
            return;
        }

        txtId.setText(idText.trim()); 

        Funcionario f = controller.carregarEdicao(idText.trim());

        if (f != null) {
            txtNome.setText(f.getNome());
            txtFuncao.setText(f.getFuncao());
            txtCpf.setText(f.getCpf());
            txtStatus.setText(String.valueOf(f.getStatus()));
            txtEmail.setText(f.getEmail());
            txtTelefone.setText(f.getTelefone());
            txtSexo.setText(String.valueOf(f.getSexo()));
            txtNascimento.setText(f.getDtnascimento().toString()); // Formato AAAA-MM-DD
            txtEndereco.setText(f.getEndereco());

            JOptionPane.showMessageDialog(this, "Dados do funcionário ID " + f.getId() + " carregados para edição.");
        } else {
            JOptionPane.showMessageDialog(this, "Funcionário não encontrado ou ID inválido.");
            limparCampos();
        }
    }

    private void excluir() {
        String idText = JOptionPane.showInputDialog(this, 
                "Digite o ID do funcionário para exclusão:", 
                "Excluir Funcionário", 
                JOptionPane.QUESTION_MESSAGE);

        // 2. Verifica se o usuário cancelou ou não digitou nada
        if (idText == null || idText.trim().isEmpty()) {
            limparCampos();
            return;
        }
        
        txtId.setText(idText.trim()); 

        int confirmacao = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir o funcionário de ID: " + idText.trim() + "?", 
                "Confirmação de Exclusão", 
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            boolean ok = controller.excluir(idText.trim());

            if (ok) {
                JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
                txtLista.setText(controller.listarAtivos());
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir funcionário. Verifique se o ID está correto ou se há dependências.");
                limparCampos();
            }
        } else {
            limparCampos(); 
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtFuncao.setText("");
        txtCpf.setText("");
        txtStatus.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtSexo.setText("");
        txtNascimento.setText("");
        txtEndereco.setText("");
    }

    // -----------------------------
    // DECLARAÇÕES DOS COMPONENTES
    // -----------------------------
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnListarTodos;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelFuncao;
    private javax.swing.JLabel jLabelCpf;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelTelefone;
    private javax.swing.JLabel jLabelSexo;
    private javax.swing.JLabel jLabelNascimento;
    private javax.swing.JLabel jLabelEndereco;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtFuncao;
    private javax.swing.JTextField txtCpf;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtTelefone;
    private javax.swing.JTextField txtSexo;
    private javax.swing.JTextField txtNascimento;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextArea txtLista;
}