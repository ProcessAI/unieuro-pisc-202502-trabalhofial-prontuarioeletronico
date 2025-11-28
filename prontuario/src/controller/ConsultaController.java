package controller;

import model.Consulta;
import model.ConsultaDAO;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ConsultaController {

    private final ConsultaDAO consultaDAO;

    public ConsultaController() {
        this.consultaDAO = new ConsultaDAO();
    }

    public boolean agendarConsulta(String dataStr, String horaStr,
                                   int pacienteId, int medicoId, String observacoes) {

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
            final int DURACAO_CONSULTA = 60; // 60 minutos

            if (consultaDAO.existeConflitoHorario(medicoId, dataSql, horaSql, DURACAO_CONSULTA)) {
                throw new IllegalArgumentException("O horário da consulta (incluindo sua duração de " + DURACAO_CONSULTA + " minutos) conflita com outro agendamento.");
            }

            Consulta c = new Consulta(dataSql, horaSql, pacienteId, medicoId, observacoes);
            return consultaDAO.insert(c);

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao agendar consulta: " + e.getMessage(), e);
        }
    }
}