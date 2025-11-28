package model;

import java.sql.Date;
import java.sql.Time;

public class ExameAgendado {

    private int exameAgendaId;
    private Date data;
    private Time hora;
    private int pacienteId;
    private int medicoId;
    private int exameId;
    private String observacoes;

    public ExameAgendado() {}

    public ExameAgendado(int exameAgendaId, Date data, Time hora,
                         int pacienteId, int medicoId, int exameId, String observacoes) {
        this.exameAgendaId = exameAgendaId;
        this.data = data;
        this.hora = hora;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.exameId = exameId;
        this.observacoes = observacoes;
    }

    public ExameAgendado(Date data, Time hora,
                         int pacienteId, int medicoId, int exameId, String observacoes) {
        this.data = data;
        this.hora = hora;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.exameId = exameId;
        this.observacoes = observacoes;
    }

    public int getExameAgendaId() { return exameAgendaId; }
    public void setExameAgendaId(int exameAgendaId) { this.exameAgendaId = exameAgendaId; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }

    public int getPacienteId() { return pacienteId; }
    public void setPacienteId(int pacienteId) { this.pacienteId = pacienteId; }

    public int getMedicoId() { return medicoId; }
    public void setMedicoId(int medicoId) { this.medicoId = medicoId; }

    public int getExameId() { return exameId; }
    public void setExameId(int exameId) { this.exameId = exameId; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
