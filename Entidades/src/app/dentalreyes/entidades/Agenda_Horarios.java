/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.entidades;

import java.sql.Date;
import java.sql.Time;


public class Agenda_Horarios {
    private int idAgendaHorario;
    private Date dia;
    private Time hora;
    private String estado; // "Disponible", "Ocupado"

    public Agenda_Horarios() {}

    public Agenda_Horarios(int idAgendaHorario, Date dia, Time hora, String estado) {
        this.idAgendaHorario = idAgendaHorario;
        this.dia = dia;
        this.hora = hora;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdAgendaHorario() { return idAgendaHorario; }
    public void setIdAgendaHorario(int idAgendaHorario) { this.idAgendaHorario = idAgendaHorario; }

    public Date getDia() { return dia; }
    public void setDia(Date dia) { this.dia = dia; }

    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    @Override
    public String toString() {
        return dia + " - " + hora + " (" + estado + ")";
    }
}
