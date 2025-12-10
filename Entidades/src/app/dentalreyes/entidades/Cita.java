package app.dentalreyes.entidades;

import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Timestamp;

public class Cita {
    private int idCita;
    private int idPaciente;
    private int idAgendaHorario;
    private String motivo; 
    private String estado;
    private Timestamp fechaRegistro;
    
    // Campos auxiliares (para mostrar en tablas, no se guardan en BD 'Cita')
    private LocalDate fecha; 
    private LocalTime hora;

    public Cita() {
    }

    // --- GETTERS Y SETTERS ---

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }

    public int getIdAgendaHorario() { return idAgendaHorario; }
    public void setIdAgendaHorario(int idAgendaHorario) { this.idAgendaHorario = idAgendaHorario; }

    // Aquí está el que te faltaba:
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    // Auxiliares
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
}