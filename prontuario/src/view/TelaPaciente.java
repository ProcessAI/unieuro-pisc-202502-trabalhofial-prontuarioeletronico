package view;

import model.Paciente;
import model.PacienteDAO;
import model.Convenio;
import model.ConvenioDAO;
import model.Responsavel;
import model.ResponsavelDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaPaciente extends JFrame {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Campos de texto
    private JTextField campoId;
    private JTextField campoTelefone;
    private JTextField campoNascimento;
    private JTextField campoEndereco;
    private JTextField campoAlergia;
    private JTextField campoNacionalidade;

    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoEmail;
    private JTextField campoStatus;
    private JTextField campoSexo;
    private JTextField campoEstadoCivil;

    // Combos
    private JComboBox<Convenio> comboConvenio;
    private JComboBox<Responsavel> comboResponsavel;

    // Botões
    private JButton botaoSalvar;
    private JButton botaoListar;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private JButton botaoLimpar;

    // Área de lista
    private JTextArea areaLista;

    // DAO
    private final PacienteDAO pacienteDAO;

    public TelaPaciente() {
        pacienteDAO = new PacienteDAO();

        setTitle("CRUD de Pacientes");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
        carregarConvenios();
        carregarResponsaveis();
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout(10, 10));
        JPanel painelCampos = new JPanel(new GridLayout(7, 4, 5, 5));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // Coluna esquerda
        painelCampos.add(new JLabel("ID (Edição):"));
        campoId = new JTextField();
        campoId.setEditable(false);
        painelCampos.add(campoId);

        painelCampos.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painelCampos.add(campoNome);

        painelCampos.add(new JLabel("Telefone:"));
        campoTelefone = new JTextField();
        painelCampos.add(campoTelefone);

        painelCampos.add(new JLabel("CPF:"));
        campoCpf = new JTextField();
        painelCampos.add(campoCpf);

        painelCampos.add(new JLabel("Nascimento (DD/MM/AAAA):"));
        campoNascimento = new JTextField();
        painelCampos.add(campoNascimento);

        painelCampos.add(new JLabel("Email:"));
        campoEmail = new JTextField();
        painelCampos.add(campoEmail);

        painelCampos.add(new JLabel("Endereço:"));
        campoEndereco = new JTextField();
        painelCampos.add(campoEndereco);

        painelCampos.add(new JLabel("Status (A/I):"));
        campoStatus = new JTextField();
        painelCampos.add(campoStatus);

        painelCampos.add(new JLabel("Alergia:"));
        campoAlergia = new JTextField();
        painelCampos.add(campoAlergia);

        painelCampos.add(new JLabel("Sexo (M/F):"));
        campoSexo = new JTextField();
        painelCampos.add(campoSexo);

        painelCampos.add(new JLabel("Nacionalidade:"));
        campoNacionalidade = new JTextField();
        painelCampos.add(campoNacionalidade);

        painelCampos.add(new JLabel("Est. Civil (S/C/D/V):"));
        campoEstadoCivil = new JTextField();
        painelCampos.add(campoEstadoCivil);

        // Responsável
        painelCampos.add(new JLabel("Responsável:"));
        comboResponsavel = new JComboBox<>();
        painelCampos.add(comboResponsavel);

        // Convênio
        painelCampos.add(new JLabel("Convênio:"));
        comboConvenio = new JComboBox<>();
        painelCampos.add(comboConvenio);

        add(painelCampos, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botaoSalvar = new JButton("Salvar Novo / Atualizar");
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

        // Área de lista
        areaLista = new JTextArea(8, 70);
        areaLista.setEditable(false);
        JScrollPane scrollLista = new JScrollPane(areaLista);
        scrollLista.setBorder(BorderFactory.createTitledBorder("--- Lista de Pacientes ---"));
        add(scrollLista, BorderLayout.SOUTH);

        // Listeners
        botaoSalvar.addActionListener(e -> salvarOuAtualizar());
        botaoListar.addActionListener(e -> listarTodos());
        botaoEditar.addActionListener(e -> carregarParaEdicao());
        botaoExcluir.addActionListener(e -> excluirPorId());
        botaoLimpar.addActionListener(e -> limparCampos());
    }

    private void carregarConvenios() {
        try {
            ConvenioDAO dao = new ConvenioDAO();
            List<Convenio> convenios = dao.listarTodos();
            comboConvenio.removeAllItems();

            // Adiciona opção "Nenhum"
            Convenio cNone = new Convenio();
            cNone.setIdconvenio(0); // ID 0 é interpretado como "Nenhum" pelo toString()
            comboConvenio.addItem(cNone);

            for (Convenio c : convenios) {
                comboConvenio.addItem(c);
            }
            comboConvenio.setSelectedIndex(0); // Seleciona "Nenhum"
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar convênios: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarResponsaveis() {
        try {
            ResponsavelDAO dao = new ResponsavelDAO();
            List<Responsavel> responsaveis = dao.findAll();
            comboResponsavel.removeAllItems();

            // Adiciona opção "Nenhum"
            Responsavel rNone = new Responsavel();
            rNone.setId(0); // ID 0 é interpretado como "Nenhum" pelo toString()
            comboResponsavel.addItem(rNone);

            for (Responsavel r : responsaveis) {
                comboResponsavel.addItem(r);
            }
            comboResponsavel.setSelectedIndex(0); // Seleciona "Nenhum"
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar responsáveis: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoId.setText("");
        campoTelefone.setText("");
        campoNascimento.setText("");
        campoEndereco.setText("");
        campoAlergia.setText("");
        campoNacionalidade.setText("");
        campoNome.setText("");
        campoCpf.setText("");
        campoEmail.setText("");
        campoStatus.setText("");
        campoSexo.setText("");
        campoEstadoCivil.setText("");
        comboConvenio.setSelectedIndex(0); // Seleciona "Nenhum"
        comboResponsavel.setSelectedIndex(0); // Seleciona "Nenhum"
    }

    private void salvarOuAtualizar() {
        try {
            String nome = campoNome.getText().trim();
            String telefone = campoTelefone.getText().trim();
            String cpf = campoCpf.getText().trim();
            String dataStr = campoNascimento.getText().trim();
            String email = campoEmail.getText().trim();
            String endereco = campoEndereco.getText().trim();
            String status = campoStatus.getText().trim();
            String alergia = campoAlergia.getText().trim();
            String sexo = campoSexo.getText().trim();
            String nacionalidade = campoNacionalidade.getText().trim();
            String estadoCivil = campoEstadoCivil.getText().trim();

            if (nome.isEmpty() || dataStr.isEmpty()) {
                throw new IllegalArgumentException("Nome e Data de Nascimento são obrigatórios.");
            }

            LocalDate ld = LocalDate.parse(dataStr, FORMATTER);
            Date dataSql = Date.valueOf(ld);

            // Lógica para Convênio: Se ID for 0 (Nenhum), passa null
            Convenio conv = (Convenio) comboConvenio.getSelectedItem();
            Integer convenioId = (conv != null && conv.getIdconvenio() != 0 ? conv.getIdconvenio() : null);

            // Lógica para Responsável: Se ID for 0 (Nenhum), passa null
            Responsavel resp = (Responsavel) comboResponsavel.getSelectedItem();
            Integer responsavelId = (resp != null && resp.getId() != 0 ? resp.getId() : null);

            String idStr = campoId.getText().trim();
            boolean ok;

            if (!idStr.isEmpty()) {
                // Atualizar
                int id = Integer.parseInt(idStr);

                Paciente p = new Paciente(
                        id,
                        nome,
                        telefone,
                        cpf,
                        dataSql,
                        email,
                        endereco,
                        status,
                        alergia,
                        sexo,
                        nacionalidade,
                        estadoCivil,
                        convenioId
                );
                p.setResponsavelId(responsavelId);

                ok = pacienteDAO.update(p);
                if (ok) {
                    JOptionPane.showMessageDialog(this,
                            "Paciente atualizado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erro ao atualizar paciente.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                // Inserir novo
                Paciente p = new Paciente(
                        nome,
                        telefone,
                        cpf,
                        dataSql,
                        email,
                        endereco,
                        status,
                        alergia,
                        sexo,
                        nacionalidade,
                        estadoCivil,
                        convenioId
                );
                p.setResponsavelId(responsavelId);

                ok = pacienteDAO.insert(p);
                if (ok) {
                    JOptionPane.showMessageDialog(this,
                            "Paciente cadastrado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erro ao inserir paciente.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (ok) {
                limparCampos();
                listarTodos();
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar/atualizar paciente: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarTodos() {
        try {
            List<Paciente> pacientes = pacienteDAO.findAll();
            StringBuilder sb = new StringBuilder();
            sb.append("--- Lista de Pacientes ---\n");

            if (pacientes.isEmpty()) {
                sb.append("Nenhum paciente cadastrado.");
            } else {
                for (Paciente p : pacientes) {
                    sb.append("ID: ").append(p.getPacienteId())
                            .append(" | Nome: ").append(p.getPacienteNome())
                            .append(" | CPF: ").append(p.getPacienteCpf())
                            .append(" | D.Nasc: ").append(p.getPacienteDnas())
                            .append(" | Status: ").append(p.getPacienteStatus())
                            .append(" | Sexo: ").append(p.getPacienteSexo())
                            .append(" | Responsável: ")
                            .append(p.getResponsavelNome() != null ? p.getResponsavelNome() : "Nenhum")
                            .append(" | Convênio: ")
                            .append(p.getConvenioNome() != null ? p.getConvenioNome() : "Nenhum")
                            .append("\n");
                }
            }

            areaLista.setText(sb.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao listar pacientes: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarParaEdicao() {
        String idStr = JOptionPane.showInputDialog(this, "Informe o ID do paciente para edição:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            Paciente p = pacienteDAO.findById(id);

            if (p == null) {
                JOptionPane.showMessageDialog(this,
                        "Paciente não encontrado.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            campoId.setText(String.valueOf(p.getPacienteId()));
            campoNome.setText(p.getPacienteNome());
            campoTelefone.setText(p.getPacienteTelefone());
            campoCpf.setText(p.getPacienteCpf());
            campoNascimento.setText(
                    p.getPacienteDnas() != null
                            ? p.getPacienteDnas().toLocalDate().format(FORMATTER)
                            : ""
            );
            campoEmail.setText(p.getPacienteEmail());
            campoEndereco.setText(p.getPacienteEndereco());
            campoStatus.setText(p.getPacienteStatus());
            campoAlergia.setText(p.getPacienteAlergia());
            campoSexo.setText(p.getPacienteSexo());
            campoNacionalidade.setText(p.getPacienteNacionalidade());
            campoEstadoCivil.setText(p.getPacienteEstadocivil());

            // Seleciona convênio
            if (p.getConvenioId() != null) {
                for (int i = 0; i < comboConvenio.getItemCount(); i++) {
                    Convenio c = comboConvenio.getItemAt(i);
                    if (c.getIdconvenio() == p.getConvenioId()) {
                        comboConvenio.setSelectedIndex(i);
                        break;
                    }
                }
            } else {
                comboConvenio.setSelectedIndex(0); // Seleciona "Nenhum"
            }

            // Seleciona responsável
            if (p.getResponsavelId() != null) {
                for (int i = 0; i < comboResponsavel.getItemCount(); i++) {
                    Responsavel r = comboResponsavel.getItemAt(i);
                    if (r.getId() == p.getResponsavelId()) {
                        comboResponsavel.setSelectedIndex(i);
                        break;
                    }
                }
            } else {
                comboResponsavel.setSelectedIndex(0); // Seleciona "Nenhum"
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar paciente para edição: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirPorId() {
        String idStr = JOptionPane.showInputDialog(this, "Informe o ID do paciente para excluir:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            int op = JOptionPane.showConfirmDialog(this,
                    "Confirma exclusão do paciente ID " + id + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                boolean ok = pacienteDAO.delete(id);
                if (ok) {
                    JOptionPane.showMessageDialog(this,
                            "Paciente excluído com sucesso.",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    listarTodos();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erro ao excluir paciente.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao excluir paciente: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPaciente().setVisible(true));
    }
}