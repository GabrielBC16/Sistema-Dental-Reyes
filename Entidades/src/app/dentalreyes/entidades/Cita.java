/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.entidades;
import java.time.LocalDate;
import java.time.LocalTime;


public class Cita {
    private int idCita;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado; // "Programada", "Cancelada", "Atendida"
    private int idPaciente; // Llave foránea
    private int idAgendaHorario; // Llave foránea
    
    private String nombrePaciente;
    private String dniPaciente;

    public Cita() {}

    public Cita(int idCita, LocalDate fecha, LocalTime hora, String estado, int idPaciente, int idAgendaHorario) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.idPaciente = idPaciente;
        this.idAgendaHorario = idAgendaHorario;
    }

    public int getIdCita() {
        return idCita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getEstado() {
        return estado;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public int getIdAgendaHorario() {
        return idAgendaHorario;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void setIdAgendaHorario(int idAgendaHorario) {
        this.idAgendaHorario = idAgendaHorario;
    }


    
    
}