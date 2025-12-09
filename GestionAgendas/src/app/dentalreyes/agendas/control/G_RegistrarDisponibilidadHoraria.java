/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.agendas.control;

import app.dentalreyes.entidades.Agenda_Horarios;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class G_RegistrarDisponibilidadHoraria {

    private final AgendaDAO agendaDAO;

    public G_RegistrarDisponibilidadHoraria() {
        this.agendaDAO = new AgendaDAO();
    }

    /**
     * REGISTRA UNA NUEVA DISPONIBILIDAD HORARIA
     */
    public String guardarDisponibilidad(List<Agenda_Horarios> listaHorarios) {

    if (listaHorarios == null || listaHorarios.isEmpty()) {
        return "ERROR: No se recibió ningún horario para registrar.";
    }

    for (Agenda_Horarios h : listaHorarios) {

        // 1. Validar conflicto
        boolean conflicto = agendaDAO.existeConflicto(h.getDia(), h.getHoraInicio());
        if (conflicto) {
            return "ERROR: Ya existe un horario creado el "
                    + h.getDia() + " a las " + h.getHoraInicio();
        }
    }

    // 2. Guardar uno por uno
    boolean ok = agendaDAO.insertarDisponibilidadLista(listaHorarios);

    if (!ok) {
        return "ERROR: No se pudieron registrar los horarios.";
    }

    return "Disponibilidad registrada correctamente.";
}
    
    public List<Agenda_Horarios> obtenerHorariosSemana(LocalDate fechaInicio, LocalDate fechaFin){
        
        List<Agenda_Horarios> horarios = new ArrayList<>();
        agendaDAO.obtenerHorariosSemana(fechaInicio, fechaFin);
        return horarios;
    }


}

