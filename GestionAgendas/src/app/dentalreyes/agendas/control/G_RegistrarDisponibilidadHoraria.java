/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.agendas.control;

import app.dentalreyes.entidades.Agenda_Horarios;

public class G_RegistrarDisponibilidadHoraria {

    private final AgendaDAO agendaDAO;

    public G_RegistrarDisponibilidadHoraria() {
        this.agendaDAO = new AgendaDAO();
    }

    /**
     * REGISTRA UNA NUEVA DISPONIBILIDAD HORARIA
     */
    public String registrarDisponibilidad(Agenda_Horarios agenda) {

        // 1. Validación: ¿Existe conflicto?
        boolean conflicto = agendaDAO.existeConflicto(agenda.getDia(), agenda.getHoraInicio());
        if (conflicto) {
            return "ERROR: Ya existe un horario creado en esa fecha y hora.";
        }

        // 2. Insertar horario
        boolean registrado = agendaDAO.insertarDisponibilidad(agenda);

        if (!registrado) {
            return "ERROR: No se pudo registrar la disponibilidad horaria.";
        }

        return "Disponibilidad horaria registrada correctamente.";
    }

}

