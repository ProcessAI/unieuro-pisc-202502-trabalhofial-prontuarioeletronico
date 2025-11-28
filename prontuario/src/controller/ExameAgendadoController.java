package controller;

import model.ExameAgendado;
import model.ExameAgendadoDAO;
import model.ConsultaDAO;
import model.Exame;
import model.ExameDataAcessObject;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


public class ExameAgendadoController {

    private final ExameAgendadoDAO exameDAO;
    private final ConsultaDAO consultaDAO;
    private final ExameDataAcessObject exameDataAcessObject;

    public ExameAgendadoController() {
        this.exameDAO = new ExameAgendadoDAO();
        this.consultaDAO = new ConsultaDAO();
        this.exameDataAcessObject = new ExameDataAcessObject();
    }

    public boolean agendarExame(String dataStr, String horaStr,
                                int pacienteId, int medicoId, int exameId,
                                String observacoes) {

        try {
            // 1. VALIDAÇÃO DE DATA E HORÁRIO BÁSICA
            String[] partes = dataStr.split("/");
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int ano = Integer.parseInt(partes[2]);

            LocalDate ld = LocalDate.of(ano, mes, dia);
            DayOfWeek dow = ld.getDayOfWeek();
            LocalTime lt = LocalTime.parse(horaStr);

            LocalDate hoje = LocalDate.now();
            LocalTime agora = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);


            // a) Não permitir agendar no passado
            if (ld.isBefore(hoje)) {
                throw new IllegalArgumentException("Não é permitido agendar em datas passadas.");
            }

            // b) Se a data for hoje, não permitir agendar em horário passado
            if (ld.isEqual(hoje) && lt.isBefore(agora)) {
                throw new IllegalArgumentException("Não é permitido agendar em horário que já passou no dia de hoje.");
            }

            // c) Só segunda a sexta
            if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
                throw new IllegalArgumentException("A clínica só funciona de segunda a sexta.");
            }

            // d) Horário de expediente (08:00 às 17:00)
            LocalTime abre = LocalTime.of(8, 0);
            LocalTime fecha = LocalTime.of(17, 0);

            if (lt.isBefore(abre) || !lt.isBefore(fecha)) {
                throw new IllegalArgumentException("Horário fora do expediente (08:00 às 17:00).");
            }

            Date dataSql = Date.valueOf(ld);
            Time horaSql = Time.valueOf(lt);

            // 2. VALIDAÇÃO DE CONFLITO DE HORÁRIO COM DURAÇÃO

            Exame exame = exameDataAcessObject.findById(exameId);
            if (exame == null) {
                throw new RuntimeException("Exame não encontrado com ID: " + exameId);
            }
            int exameDuracaoMinutos = (exame.getDuracaoMinutos() != null) ? exame.getDuracaoMinutos() : 30;

            if (consultaDAO.existeConflitoHorario(medicoId, dataSql, horaSql, exameDuracaoMinutos)) {
                throw new IllegalArgumentException("O horário do exame (incluindo sua duração de " + exameDuracaoMinutos + " minutos) conflita com outro agendamento.");
            }

            ExameAgendado ex = new ExameAgendado(dataSql, horaSql, pacienteId, medicoId, exameId, observacoes);
            return exameDAO.insert(ex);

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao agendar exame: " + e.getMessage(), e);
        }
    }
}
