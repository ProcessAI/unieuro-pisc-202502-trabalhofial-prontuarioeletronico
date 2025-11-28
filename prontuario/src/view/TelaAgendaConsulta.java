package view;

import controller.ConsultaController;
import model.Consulta;
import model.ConsultaDAO;
import model.Medico;
import model.MedicoDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.text.BadLocationException;
import model.Paciente;
import model.PacienteDAO;

public class TelaAgendaConsulta extends JFrame {

    private final ConsultaController controller;

    private JTextField campoPacienteId;
    private JComboBox<Medico> comboMedico;
    private JTextField campoData;
    private JComboBox<String> comboHora;
    private JTextArea  areaObs;

    private JTextArea areaLista;

    private JButton botaoAgendar;
    private JButton botaoListar;
    private JButton botaoCancelar;
    private JButton botaoLimpar;
    private JButton botaoVerDisponibilidade;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final int DURACAO_CONSULTA = 60;


    public TelaAgendaConsulta() {
        controller = new ConsultaController();
        setTitle("Agenda de Consultas");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        iniciarComponentes();
        carregarMedicos();
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // ============ CAMPOS ============
        JPanel painelCampos = new JPanel(new GridLayout(3, 4, 5, 5));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        campoPacienteId = adicionarCampo(painelCampos, "ID do Paciente:");

        painelCampos.add(new JLabel("Médico:"));
        comboMedico = new JComboBox<>();
        painelCampos.add(comboMedico);

        campoData       = adicionarCampo(painelCampos, "Data (DD/MM/AAAA):");

        // Substitui campoHora por comboHora
        painelCampos.add(new JLabel("Hora (HH:MM):"));
        comboHora = new JComboBox<>();
        popularComboHora(DURACAO_CONSULTA);
        comboHora.setEditable(false);
        comboHora.setEnabled(false); // <--- NOVO: DESATIVA SELEÇÃO MANUAL
        painelCampos.add(comboHora);

        // completa grid 3x4
        painelCampos.add(new JLabel());
        painelCampos.add(new JLabel());
        painelCampos.add(new JLabel());
        painelCampos.add(new JLabel());

        add(painelCampos, BorderLayout.NORTH);

        // ============ CENTRO: Observações + Botões ============
        JPanel painelCentro = new JPanel(new BorderLayout(5, 5));

        JPanel painelObs = new JPanel(new BorderLayout(5, 5));
        painelObs.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
        painelObs.add(new JLabel("Observações da consulta:"), BorderLayout.NORTH);
        areaObs = new JTextArea(4, 30);
        areaObs.setLineWrap(true);
        areaObs.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(areaObs);
        painelObs.add(scrollObs, BorderLayout.CENTER);

        painelCentro.add(painelObs, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botaoAgendar = new JButton("Agendar Consulta");
        botaoListar  = new JButton("Listar Próximas por Médico");
        botaoCancelar = new JButton("Cancelar Consulta (por ID)");
        botaoVerDisponibilidade = new JButton("Ver Horários Livres");
        botaoLimpar  = new JButton("Limpar Campos");

        painelBotoes.add(botaoAgendar);
        painelBotoes.add(botaoListar);
        painelBotoes.add(botaoCancelar);
        painelBotoes.add(botaoVerDisponibilidade);
        painelBotoes.add(botaoLimpar);

        painelCentro.add(painelBotoes, BorderLayout.CENTER);

        add(painelCentro, BorderLayout.CENTER);

        // ============ LISTA ============
        areaLista = new JTextArea(10, 70);
        areaLista.setEditable(false);
        JScrollPane scrollLista = new JScrollPane(areaLista);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Próximas Consultas do Médico"));
        add(scrollLista, BorderLayout.SOUTH);

        // Listener para "Click and Select"
        areaLista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarHorarioPorClique(e);
            }
        });

        // ============ LISTENERS ============
        botaoAgendar.addActionListener(e -> agendar());
        botaoListar.addActionListener(e -> listar());
        botaoLimpar.addActionListener(e -> limparCampos());
        botaoCancelar.addActionListener(e -> cancelarConsulta());
        botaoVerDisponibilidade.addActionListener(e -> listarHorariosDisponiveis());
    }

    private void popularComboHora(int duracaoSlot) {
        comboHora.removeAllItems();
        LocalTime hora = LocalTime.of(8, 0);
        LocalTime fecha = LocalTime.of(17, 0);

        while (hora.isBefore(fecha) || hora.equals(fecha)) {
            comboHora.addItem(hora.toString());
            hora = hora.plusMinutes(duracaoSlot);
        }
        comboHora.setSelectedIndex(-1);
    }

    private void selecionarHorarioPorClique(MouseEvent e) {
        try {
            int offset = areaLista.viewToModel(e.getPoint());
            int linha = areaLista.getLineOfOffset(offset);
            String textoLinha = areaLista.getText().substring(areaLista.getLineStartOffset(linha), areaLista.getLineEndOffset(linha));

            if (textoLinha.contains("(LIVRE)")) {
                // Tenta extrair a hora (HH:MM)
                int start = textoLinha.indexOf("- ") + 2;
                int end = textoLinha.indexOf(" (LIVRE)");
                String horaSelecionada = textoLinha.substring(start, end).trim();

                // Tenta encontrar a data no cabeçalho da lista
                String listaTexto = areaLista.getText();
                String dataStr = null;

                // Padrão de busca da data no cabeçalho: "em DD/MM/AAAA"
                int dataIndex = listaTexto.indexOf("em ") + 3;
                if (dataIndex > 3) {
                    dataStr = listaTexto.substring(dataIndex, dataIndex + 10);
                }

                if (dataStr != null && !dataStr.isEmpty()) {
                    campoData.setText(dataStr);
                    comboHora.setSelectedItem(horaSelecionada);

                    JOptionPane.showMessageDialog(this,
                            "Horário (" + horaSelecionada + ") e Data (" + dataStr + ") selecionados.",
                            "Seleção", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (HeadlessException | BadLocationException ex) {
            // Ignora cliques em áreas vazias ou títulos
        }
    }


    private void carregarMedicos() {
        try {
            MedicoDAO dao = new MedicoDAO();
            List<Medico> medicos = dao.findAllActive();
            comboMedico.removeAllItems();

            for (Medico m : medicos) {
                comboMedico.addItem(m);
            }

            if (comboMedico.getItemCount() > 0) {
                comboMedico.setSelectedIndex(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar médicos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTextField adicionarCampo(JPanel painel, String rotulo) {
        painel.add(new JLabel(rotulo));
        JTextField campo = new JTextField(15);
        painel.add(campo);
        return campo;
    }

    private void limparCampos() {
        campoPacienteId.setText("");
        campoData.setText("");
        areaObs.setText("");
        areaLista.setText("");
        comboMedico.setSelectedIndex(0);
        comboHora.setSelectedIndex(-1);
    }

    private void agendar() {
        try {
            String pacienteStr = campoPacienteId.getText().trim();
            Medico medico = (Medico) comboMedico.getSelectedItem();
            String dataStr     = campoData.getText().trim();
            String horaStr     = (String) comboHora.getSelectedItem();
            String obs         = areaObs.getText().trim();

            if (pacienteStr.isEmpty() || medico == null || dataStr.isEmpty() || horaStr == null) {
                throw new IllegalArgumentException("Paciente, Médico, Data e Hora são obrigatórios.");
            }

            int medicoId = medico.getMedicoId();
            int pacienteId = Integer.parseInt(pacienteStr);

            boolean ok = controller.agendarConsulta(dataStr, horaStr, pacienteId, medicoId, obs);

            if (ok) {
                JOptionPane.showMessageDialog(this,
                        "Consulta agendada com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                listar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Falha ao agendar consulta.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID do Paciente deve ser numérico.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao agendar consulta: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listar() {
        // NOTA: Certifique-se de que a variável 'formatter' (ex: new SimpleDateFormat("dd/MM/yyyy")) 
        // foi declarada e inicializada como um campo de classe (atributo) da TelaAgendaConsulta.
        
        // NOTA: Certifique-se de que 'comboMedico' e 'areaLista' foram inicializados no construtor.

        try {
            // 1. Obter o médico selecionado
            Medico medico = (Medico) comboMedico.getSelectedItem();

            if (medico == null) {
                // Presumo que você tenha um JOptionPane.showMessageDialog em TelaAgendaConsulta
                throw new IllegalArgumentException("Selecione um Médico para listar as próximas consultas.");
            }

            int medicoId = medico.getMedicoId();

            // 2. Acesso aos DAOs (ConsultaDAO e PacienteDAO)
            // Presumo que você já tem o ConsultaDAO configurado na sua TelaAgendaConsulta
            ConsultaDAO consultaDAO = new ConsultaDAO();
            PacienteDAO pacienteDAO = new PacienteDAO();
            
            // 3. Listar consultas agendadas pelo médico
            List<Consulta> consultas = consultaDAO.listByMedico(medicoId); 

            // 4. Preparar a saída
            StringBuilder sb = new StringBuilder();
            sb.append("Próximas consultas do médico ").append(medico.getMedicoNome())
                    .append(" (ID: ").append(medicoId).append(") (a partir de hoje):\n\n");

            if (consultas.isEmpty()) {
                sb.append("Nenhuma consulta futura agendada.");
            } else {
                // Presumo que 'formatter' é um SimpleDateFormat ou similar para Date (ex: "dd/MM/yyyy")
                // e que 'Consulta' tem os getters necessários (getData(), getHora(), getPacienteId(), getObservacoes())
                for (Consulta c : consultas) {
                    Paciente p = pacienteDAO.findById(c.getPacienteId());
                    
                    String nomePaciente = (p != null ? p.getPacienteNome() : "ID " + c.getPacienteId());

                    // Novo formato de exibição: inclui a data
                    sb.append("ID Agenda: ").append(c.getConsultaId())
                            .append(" | Data: ").append(formatter.format(c.getData())) 
                            .append(" | Hora: ").append(c.getHora())
                            .append(" | Paciente: ").append(nomePaciente)
                            .append(" | Obs: ").append(c.getObservacoes() == null ? "" : c.getObservacoes())
                            .append("\n");
                }
            }

            // 5. Exibir no JTextArea
            // Presumo que 'areaLista' é o JTextArea onde você exibe a lista.
            areaLista.setText(sb.toString());

        } catch (IllegalArgumentException ex) {
            // Presumo que 'this' e 'JOptionPane' estão disponíveis (é um JFrame)
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            // ADICIONADO: Imprimir o rastreamento de pilha para identificar o erro real
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erro ao listar consultas: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void cancelarConsulta() {
        String idStr = JOptionPane.showInputDialog(this,
                "Digite o ID da Consulta a ser CANCELADA:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            int op = JOptionPane.showConfirmDialog(this,
                    "Confirma cancelar a Consulta ID " + id + "?",
                    "Confirmação de Cancelamento", JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                ConsultaDAO dao = new ConsultaDAO();
                boolean sucesso = dao.delete(id);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Consulta ID " + id + " cancelada com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                    listar();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao cancelar consulta. ID não encontrado.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido. Digite apenas números.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cancelar consulta: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarHorariosDisponiveis() {
        try {
            Medico medico = (Medico) comboMedico.getSelectedItem();
            String dataStr = campoData.getText().trim();

            if (medico == null || dataStr.isEmpty()) {
                throw new IllegalArgumentException("Selecione um Médico e uma Data para verificar a disponibilidade.");
            }

            int medicoId = medico.getMedicoId();

            // Valida e converte a data
            LocalDate ld = LocalDate.parse(dataStr, DATE_FORMATTER);
            Date dataSql = Date.valueOf(ld);

            // Parâmetros de tempo e duração
            LocalTime abre = LocalTime.of(8, 0);
            LocalTime fecha = LocalTime.of(17, 0);
            final int DURACAO_SLOT_MIN = 60;
            final int TEMPO_CONSULTA = 60;

            ConsultaDAO dao = new ConsultaDAO();
            StringBuilder sb = new StringBuilder();

            sb.append("Horários Livres para ").append(medico.getMedicoNome())
                    .append(" em ").append(dataStr).append(":\n");

            LocalTime slot = abre;
            int slotsDisponiveis = 0;

            // O slot precisa terminar antes ou exatamente na hora de fechar (fecha = 17:00)
            while (slot.plusMinutes(TEMPO_CONSULTA).isBefore(fecha) || slot.plusMinutes(TEMPO_CONSULTA).equals(fecha)) {

                // Verifica se a consulta (60 min) começando neste slot conflita com algum agendamento.
                Time horaInicioSql = Time.valueOf(slot);

                if (!dao.existeConflitoHorario(medicoId, dataSql, horaInicioSql, TEMPO_CONSULTA)) {
                    sb.append("  - ").append(slot.toString()).append(" (LIVRE)\n");
                    slotsDisponiveis++;
                }

                slot = slot.plusMinutes(DURACAO_SLOT_MIN); // Avança 60 minutos
            }

            if (slotsDisponiveis == 0) {
                sb.append("\nNenhum horário disponível para uma consulta de ").append(TEMPO_CONSULTA).append(" minutos.");
            }

            areaLista.setText(sb.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID do paciente deve ser numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar disponibilidade: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaAgendaConsulta().setVisible(true));
    }
}