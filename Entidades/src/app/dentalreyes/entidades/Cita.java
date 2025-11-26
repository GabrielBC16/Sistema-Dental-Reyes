/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.entidades;
import java.sql.Date;
import java.sql.Time;


public class Cita {
    private int idCita;
    private Date fecha;
    private Time hora;
    private String estado; // "Programada", "Cancelada", "Atendida"
    private int idPaciente; // Llave foránea
    private int idAgendaHorario; // Llave foránea

    public Cita() {}

    public Cita(int idCita, Date fecha, Time hora, String estado, int idPaciente, int idAgendaHorario) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.idPaciente = idPaciente;
        this.idAgendaHorario = idAgendaHorario;
    }

    // Getters y Setters
    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }

    public int getIdAgendaHorario() { return idAgendaHorario; }
    public void setIdAgendaHorario(int idAgendaHorario) { this.idAgendaHorario = idAgendaHorario; }
}
