/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.agendas.control;

import app.dentalreyes.entidades.Cita;
import app.dentalreyes.entidades.Agenda_Horarios;

public class G_RegistrarCita {

    private final CitaDAO citaDAO;
    private final AgendaDAO agendaDAO;

    public G_RegistrarCita() {
        this.citaDAO = new CitaDAO();
        this.agendaDAO = new AgendaDAO();
    }

    /**
     * REGISTRAR NUEVA CITA
     */
    public String registrarCita(Cita cita) {

        // 1. Validación: ¿Horario ocupado?
        boolean ocupado = citaDAO.horarioOcupado(cita.getIdAgendaHorario());
        if (ocupado) {
            return "ERROR: El horario seleccionado ya está ocupado.";
        }

        // 2. Registrar la cita
        int idGenerado = citaDAO.insertarCita(cita);

        if (idGenerado == -1) {
            return "ERROR: No se pudo registrar la cita.";
        }

        // 3. Cambiar estado del horario a OCUPADO
        boolean actualizado = agendaDAO.actualizarEstado(cita.getIdAgendaHorario(), "OCUPADO");

        if (!actualizado) {
            return "ADVERTENCIA: La cita se registró, pero NO se pudo actualizar el estado del horario.";
        }

        return "Cita registrada exitosamente con ID: " + idGenerado;
    }

}
