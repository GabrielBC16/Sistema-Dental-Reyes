package app.dentalreyes.agendas.control;

import app.dentalreyes.entidades.Agenda_Horarios;
import java.time.LocalDate;
import java.util.List;

public class G_RegistrarDisponibilidadHoraria {

    private final AgendaDAO agendaDAO;

    public G_RegistrarDisponibilidadHoraria() {
        this.agendaDAO = new AgendaDAO();
    }

    public String guardarDisponibilidad(List<Agenda_Horarios> listaHorarios) {
        if (listaHorarios == null || listaHorarios.isEmpty()) {
            return "ERROR: No hay horarios seleccionados.";
        }
        
        // Verificar conflictos (opcional, tu lógica anterior estaba bien)
        // Por ahora guardamos directo para probar
        if (agendaDAO.insertarDisponibilidadLista(listaHorarios)) {
            return "Disponibilidad registrada correctamente.";
        } else {
            return "ERROR: No se pudieron guardar los datos en BD.";
        }
    }
    
    public List<Agenda_Horarios> obtenerHorariosSemana(LocalDate fechaInicio, LocalDate fechaFin){
        // Retornamos DIRECTAMENTE lo que nos da el DAO
        return agendaDAO.obtenerHorariosSemana(fechaInicio, fechaFin);
    }
}