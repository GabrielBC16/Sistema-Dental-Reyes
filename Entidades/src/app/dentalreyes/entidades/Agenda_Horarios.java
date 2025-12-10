/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.entidades;

import java.time.LocalDate;
import java.time.LocalTime;


public class Agenda_Horarios {
    private int idAgendaHorario;
    private LocalDate dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado; // "Disponible", "Ocupado"

    public Agenda_Horarios() {}

    public Agenda_Horarios(int idAgendaHorario, LocalDate dia, LocalTime horaInicio, LocalTime horaFin, String estado) {
        this.idAgendaHorario = idAgendaHorario;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }

    public int getIdAgendaHorario() {
        return idAgendaHorario;
    }

    public LocalDate getDia() {
        return dia;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdAgendaHorario(int idAgendaHorario) {
        this.idAgendaHorario = idAgendaHorario;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}