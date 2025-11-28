package view;

import controller.ExameAgendadoController;
import model.Paciente;
import model.PacienteDAO;
import model.Exame;
import model.ExameDataAcessObject;
import model.ExameAgendado;
import model.ExameAgendadoDAO;
import model.Medico;
import model.MedicoDAO;
import model.ConsultaDAO;

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

public class TelaAgendaExame extends JFrame {

    private final ExameAgendadoController controller;

    private JTextField campoPacienteId;
    private JComboBox<Medico> comboMedico;
    private JTextField campoData;
    private JComboBox<String> comboHora;
    private JTextArea  areaObs;

    private JComboBox<Exame> comboExame;

    private JTextArea areaLista;

    private JButton botaoAgendar;
    private JButton botaoListar;
    private JButton botaoCancelar;
    private JButton botaoLimpar;
    private JButton botaoVerDisponibilidade;

    // Adicionar formatador de data
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaAgendaExame() {
        controller = new ExameAgendadoController();
        setTitle("Agenda de Exames");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        iniciarComponentes();
        carregarExames();
        carregarMedicos();
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(3, 4, 5, 5));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        campoPacienteId = adicionarCampo(painelCampos, "ID do Paciente:");

        // Adiciona ComboBox para Médicos
        painelCampos.add(new JLabel("Médico:"));
        comboMedico = new JComboBox<>();
        painelCampos.add(comboMedico);

        // Combo de exame
        painelCampos.add(new JLabel("Exame:"));
        comboExame = new JComboBox<>();
        painelCampos.add(comboExame);

        campoData = adicionarCampo(painelCampos, "Data (DD/MM/AAAA):");

        // Substitui campoHora por comboHora
        painelCampos.add(new JLabel("Hora (HH:MM):"));
        comboHora = new JComboBox<>();
        popularComboHora(15);
        comboHora.setEditable(false);
        comboHora.setEnabled(false); // <--- NOVO: DESATIVA SELEÇÃO MANUAL
        painelCampos.add(comboHora);

        // completa grid
        painelCampos.add(new JLabel());

        add(painelCampos, BorderLayout.NORTH);

        // Listener para mudar a lista de horas quando o exame muda
        comboExame.addActionListener(e -> popularComboHoraPorExame());


        JPanel painelCentro = new JPanel(new BorderLayout(5, 5));

        JPanel painelObs = new JPanel(new BorderLayout(5, 5));
        painelObs.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
        painelObs.add(new JLabel("Observações do exame:"), BorderLayout.NORTH);
        areaObs = new JTextArea(4, 30);
        areaObs.setLineWrap(true);
        areaObs.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(areaObs);
        painelObs.add(scrollObs, BorderLayout.CENTER);

        painelCentro.add(painelObs, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botaoAgendar = new JButton("Agendar Exame");
        botaoListar  = new JButton("Listar Próximos por Médico");
        botaoCancelar = new JButton("Cancelar Exame (por ID)");
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
        scrollLista.setBorder(BorderFactory.createTitledBorder("Próximos Exames do Médico"));
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
        botaoCancelar.addActionListener(e -> cancelarExame());
        botaoVerDisponibilidade.addActionListener(e -> listarHorariosDisponiveis());
    }

    private void popularComboHora(int duracaoSlot) {
        comboHora.removeAllItems();
        LocalTime hora = LocalTime.of(8, 0);
        LocalTime fecha = LocalTime.of(17, 0);

        comboHora.addItem("Nenhum");

        while (hora.isBefore(fecha) || hora.equals(fecha)) {
            comboHora.addItem(hora.toString());
            hora = hora.plusMinutes(duracaoSlot);
        }
        comboHora.setSelectedIndex(0);
    }

    private void popularComboHoraPorExame() {
        Exame exame = (Exame) comboExame.getSelectedItem();
        if (exame != null) {
            int duracaoMin = (exame.getDuracaoMinutos() != null) ? exame.getDuracaoMinutos() : 15;

            // Define o intervalo do slot com base na duração do exame
            int slotIntervalo = 15;
            if (duracaoMin >= 30 && duracaoMin < 60) {
                slotIntervalo = 30;
            } else if (duracaoMin >= 60) {
                slotIntervalo = 60;
            }
            popularComboHora(slotIntervalo);
        }
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

    private void carregarExames() {
        try {
            ExameDataAcessObject dao = new ExameDataAcessObject();
            List<Exame> exames = dao.findAll();
            comboExame.removeAllItems();

            for (Exame e : exames) {
                comboExame.addItem(e);
            }

            if (comboExame.getItemCount() > 0) {
                comboExame.setSelectedIndex(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar exames: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoPacienteId.setText("");
        campoData.setText("");
        areaObs.setText("");
        comboMedico.setSelectedIndex(0);
        comboExame.setSelectedIndex(0);
        comboHora.setSelectedIndex(0);
    }

    private void agendar() {
        try {
            String pacienteStr = campoPacienteId.getText().trim();
            Medico medico = (Medico) comboMedico.getSelectedItem();
            String dataStr     = campoData.getText().trim();
            String horaStr     = (String) comboHora.getSelectedItem();
            String obs         = areaObs.getText().trim();
            Exame exame    = (Exame) comboExame.getSelectedItem();

            if (pacienteStr.isEmpty() || medico == null || dataStr.isEmpty() || horaStr == null || "Nenhum".equals(horaStr) || exame == null) {
                throw new IllegalArgumentException("Paciente, Médico, Exame, Data e Hora são obrigatórios.");
            }

            int pacienteId = Integer.parseInt(pacienteStr);
            int medicoId   = medico.getMedicoId();
            int exameId    = exame.getExameId();

            boolean ok = controller.agendarExame(dataStr, horaStr, pacienteId, medicoId, exameId, obs);

            if (ok) {
                JOptionPane.showMessageDialog(this,
                        "Exame agendado com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                listar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Falha ao agendar exame.",
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
                    "Erro ao agendar exame: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listar() {
        try {
            Medico medico = (Medico) comboMedico.getSelectedItem();

            if (medico == null) {
                throw new IllegalArgumentException("Selecione um Médico para listar os próximos exames.");
            }

            int medicoId = medico.getMedicoId();

            ExameAgendadoDAO dao = new ExameAgendadoDAO();
            List<ExameAgendado> exames = dao.listByMedico(medicoId);

            // DAOs extras pra buscar nomes
            PacienteDAO pacienteDAO = new PacienteDAO();
            ExameDataAcessObject exameDAO = new ExameDataAcessObject();

            StringBuilder sb = new StringBuilder();
            sb.append("Próximos exames do médico ").append(medico.getMedicoNome())
                    .append(" (ID: ").append(medicoId).append(") (a partir de hoje):\n\n");


            if (exames.isEmpty()) {
                sb.append("Nenhum exame futuro agendado.");
            } else {
                for (ExameAgendado ex : exames) {
                    Paciente p = pacienteDAO.findById(ex.getPacienteId());
                    Exame exame = exameDAO.findById(ex.getExameId());

                    String nomePaciente = (p != null ? p.getPacienteNome() : "ID " + ex.getPacienteId());
                    String nomeExame    = (exame != null ? exame.getExameNome() : "ID " + ex.getExameId());

                    // Novo formato de exibição: inclui a data
                    sb.append("ID Agenda: ").append(ex.getExameAgendaId())
                            .append(" | Data: ").append(formatter.format(ex.getData())) // Formata e mostra a data
                            .append(" | Hora: ").append(ex.getHora())
                            .append(" | Paciente: ").append(nomePaciente)
                            .append(" | Exame: ").append(nomeExame)
                            .append(" | Obs: ").append(ex.getObservacoes() == null ? "" : ex.getObservacoes())
                            .append("\n");
                }
            }

            areaLista.setText(sb.toString());

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao listar exames: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarExame() {
        String idStr = JOptionPane.showInputDialog(this,
                "Digite o ID da Agenda do Exame a ser CANCELADA:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            int op = JOptionPane.showConfirmDialog(this,
                    "Confirma cancelar o Exame Agendado ID " + id + "?",
                    "Confirmação de Cancelamento", JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                ExameAgendadoDAO dao = new ExameAgendadoDAO();
                boolean sucesso = dao.delete(id);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Exame Agendado ID " + id + " cancelado com sucesso!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                    listar();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Falha ao cancelar exame agendado. ID não encontrado.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido. Digite apenas números.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cancelar exame agendado: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarHorariosDisponiveis() {
        try {
            Medico medico = (Medico) comboMedico.getSelectedItem();
            String dataStr = campoData.getText().trim();
            Exame exameSelecionado = (Exame) comboExame.getSelectedItem();

            if (medico == null || dataStr.isEmpty() || exameSelecionado == null) {
                throw new IllegalArgumentException("Selecione um Médico, um Exame e uma Data para verificar a disponibilidade.");
            }

            int medicoId = medico.getMedicoId();

            // 1. Obter a duração do exame selecionado
            int duracaoExameMin = (exameSelecionado.getDuracaoMinutos() != null) ? exameSelecionado.getDuracaoMinutos() : 30;

            // Valida e converte a data
            LocalDate ld = LocalDate.parse(dataStr, DATE_FORMATTER);
            Date dataSql = Date.valueOf(ld);

            // Parâmetros de tempo e duração
            LocalTime abre = LocalTime.of(8, 0);
            LocalTime fecha = LocalTime.of(17, 0);
            final int DURACAO_SLOT_MIN = 15; // Slots de 15 minutos

            ConsultaDAO dao = new ConsultaDAO();
            StringBuilder sb = new StringBuilder();

            // REMOVIDO: (Slot de X min)
            sb.append("Horários Livres para ").append(medico.getMedicoNome())
                    .append(" em ").append(dataStr).append(" (Exame: ").append(exameSelecionado.getExameNome()).append("):\n");

            LocalTime slot = abre;
            int slotsDisponiveis = 0;

            // O slot precisa terminar antes ou exatamente na hora de fechar (fecha = 17:00)
            while (slot.plusMinutes(duracaoExameMin).isBefore(fecha) || slot.plusMinutes(duracaoExameMin).equals(fecha)) {

                // Verifica se o exame (com sua duração específica) começando neste slot conflita com algum agendamento.
                Time horaInicioSql = Time.valueOf(slot);

                if (!dao.existeConflitoHorario(medicoId, dataSql, horaInicioSql, duracaoExameMin)) {
                    sb.append("  - ").append(slot.toString()).append(" (LIVRE)\n");
                    slotsDisponiveis++;
                }

                slot = slot.plusMinutes(DURACAO_SLOT_MIN); // Avança 15 minutos
            }

            if (slotsDisponiveis == 0) {
                sb.append("\nNenhum horário disponível para o exame de ").append(duracaoExameMin).append(" minutos.");
            }

            areaLista.setText(sb.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao converter para número.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar disponibilidade: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaAgendaExame().setVisible(true));
    }
}