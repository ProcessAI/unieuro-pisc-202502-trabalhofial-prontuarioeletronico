package model;

import java.sql.Date;
import java.sql.Time;

public class Consulta {

    private int consultaId;
    private Date data;
    private Time hora;
    private int pacienteId;
    private int medicoId;
    private String observacoes;

    public Consulta() {
    }

    public Consulta(int consultaId, Date data, Time hora, int pacienteId, int medicoId, String observacoes) {
        this.consultaId = consultaId;
        this.data = data;
        this.hora = hora;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.observacoes = observacoes;
    }

    public Consulta(Date data, Time hora, int pacienteId, int medicoId, String observacoes) {
        this.data = data;
        this.hora = hora;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.observacoes = observacoes;
    }

    public int getConsultaId() { return consultaId; }
    public void setConsultaId(int consultaId) { this.consultaId = consultaId; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }

    public int getPacienteId() { return pacienteId; }
    public void setPacienteId(int pacienteId) { this.pacienteId = pacienteId; }

    public int getMedicoId() { return medicoId; }
    public void setMedicoId(int medicoId) { this.medicoId = medicoId; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
