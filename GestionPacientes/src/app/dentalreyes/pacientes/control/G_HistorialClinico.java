package app.dentalreyes.pacientes.control;

import app.dentalreyes.entidades.*;
import java.util.List;

public class G_HistorialClinico {

    private final HistoriaClinicaDAO historialDAO;
    private final AnamnesisDAO anamnesisDAO;
    private final DiagnosticoDAO diagnosticoDAO;
    private final IntervencionDAO intervencionDAO;

    public G_HistorialClinico() {
        this.historialDAO = new HistoriaClinicaDAO();
        this.anamnesisDAO = new AnamnesisDAO();
        this.diagnosticoDAO = new DiagnosticoDAO();
        this.intervencionDAO = new IntervencionDAO();
    }

    // --- GESTIÓN PRINCIPAL ---
    public HistorialClinico obtenerFicha(int idPaciente) {
        // Buscamos si ya tiene historial
        HistorialClinico hc = historialDAO.obtenerPorPaciente(idPaciente);
        
        // Si no tiene, lo creamos automáticamente (primera visita)
        if (hc == null) {
            if (historialDAO.crearHistorialInicial(idPaciente)) {
                hc = historialDAO.obtenerPorPaciente(idPaciente);
            }
        }
        return hc;
    }

    // --- ANAMNESIS ---
    public Anamnesis obtenerAnamnesis(int idHistorial) {
        return anamnesisDAO.obtenerUltimaAnamnesis(idHistorial);
    }

    public boolean guardarAnamnesis(Anamnesis a) {
        return anamnesisDAO.registrarAnamnesis(a);
    }

    // --- TABLAS DE DATOS ---
    public List<Diagnostico> listarDiagnosticos(int idHistorial) {
        return diagnosticoDAO.listarPorHistorial(idHistorial);
    }

    public List<Intervencion> listarIntervenciones(int idHistorial) {
        return intervencionDAO.listarPorHistorial(idHistorial);
    }
    
    public boolean resolverDiagnostico(int idDiagnostico) {
        // Llama al método específico del DAO que creamos antes
        return diagnosticoDAO.marcarResuelto(idDiagnostico);
    }
    
    // --- ESTADÍSTICAS RÁPIDAS (Para la cabecera) ---
    public String obtenerAlertas(int idHistorial) {
        Anamnesis a = obtenerAnamnesis(idHistorial);
        if (a == null) return "Sin datos registrados";
        
        StringBuilder alerta = new StringBuilder();
        if (a.isAlergiaAntibioticos()) alerta.append("• ALERGIA ANTIBIÓTICOS ");
        if (a.isAlergiaAnestesia()) alerta.append("• ALERGIA ANESTESIA ");
        if (a.isProblemasCoagulacion()) alerta.append("• COAGULACIÓN ");
        if (a.isDiabetes()) alerta.append("• DIABETES ");
        if (a.isHipertension()) alerta.append("• HIPERTENSIÓN ");
        
        return alerta.length() > 0 ? alerta.toString() : "Ninguna alerta registrada";
    }
}