package app.dentalreyes.entidades;

import java.util.Date;

public class Intervencion {
    private int idIntervencion;
    private int idHistorial;
    private int idCita;
    
    private String piezaDental;
    private String tratamiento;
    private String descripcion;
    private String observaciones;
    
    private double costo;
    private String estado; // "REALIZADO" o "PLANIFICADO"
    private Date fechaIntervencion;

    public Intervencion() {
    }

    // --- GETTERS Y SETTERS ---
    public int getIdIntervencion() { return idIntervencion; }
    public void setIdIntervencion(int idIntervencion) { this.idIntervencion = idIntervencion; }

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public String getPiezaDental() { return piezaDental; }
    public void setPiezaDental(String piezaDental) { this.piezaDental = piezaDental; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaIntervencion() { return fechaIntervencion; }
    public void setFechaIntervencion(Date fechaIntervencion) { this.fechaIntervencion = fechaIntervencion; }
}