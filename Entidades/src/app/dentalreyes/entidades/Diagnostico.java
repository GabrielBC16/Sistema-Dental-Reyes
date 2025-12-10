package app.dentalreyes.entidades;

import java.util.Date;

public class Diagnostico {
    private int idDiagnostico;
    private int idHistorial;
    private int idCita;
    
    private String piezaDental;
    private String categoria;
    private String descripcion;
    private String estado; // "ACTIVO" o "RESUELTO"
    private Date fechaDiagnostico;

    public Diagnostico() {
    }

    // --- GETTERS Y SETTERS ---
    public int getIdDiagnostico() { return idDiagnostico; }
    public void setIdDiagnostico(int idDiagnostico) { this.idDiagnostico = idDiagnostico; }

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public String getPiezaDental() { return piezaDental; }
    public void setPiezaDental(String piezaDental) { this.piezaDental = piezaDental; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaDiagnostico() { return fechaDiagnostico; }
    public void setFechaDiagnostico(Date fechaDiagnostico) { this.fechaDiagnostico = fechaDiagnostico; }
}