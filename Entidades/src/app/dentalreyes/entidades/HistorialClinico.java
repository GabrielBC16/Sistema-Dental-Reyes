package app.dentalreyes.entidades;

import java.util.Date;

public class HistorialClinico {
    private int idHistorial;
    private int idPaciente;
    private Date fechaCreacion;
    private String antecedentes; // (Opcional, ya que tienes Anamnesis, pero útil para notas rápidas)
    private String observaciones;
    private String grupoSanguineo;
    private boolean firmaConsentimiento;
    private String estadoHistorial;
    private String alertasMedicas; // Texto para mostrar en ROJO/NEGRITA

    public HistorialClinico() {
    }

    // Getters y Setters
    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getAntecedentes() { return antecedentes; }
    public void setAntecedentes(String antecedentes) { this.antecedentes = antecedentes; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    // Nuevos
    public String getGrupoSanguineo() { return grupoSanguineo; }
    public void setGrupoSanguineo(String grupoSanguineo) { this.grupoSanguineo = grupoSanguineo; }

    public boolean isFirmaConsentimiento() { return firmaConsentimiento; }
    public void setFirmaConsentimiento(boolean firmaConsentimiento) { this.firmaConsentimiento = firmaConsentimiento; }

    public String getEstadoHistorial() { return estadoHistorial; }
    public void setEstadoHistorial(String estadoHistorial) { this.estadoHistorial = estadoHistorial; }

    public String getAlertasMedicas() { return alertasMedicas; }
    public void setAlertasMedicas(String alertasMedicas) { this.alertasMedicas = alertasMedicas; }
}