/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.entidades;
import java.sql.Date;


public class Diagnostico {
    private int idDiagnostico;
    private double coste; // Usamos double para DECIMAL(10,2)
    private String descripcion;
    private Date fecha;
    private int idHistorial;

    public Diagnostico() {}

    public Diagnostico(int idDiagnostico, double coste, String descripcion, Date fecha, int idHistorial) {
        this.idDiagnostico = idDiagnostico;
        this.coste = coste;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.idHistorial = idHistorial;
    }

    // Getters y Setters
    public int getIdDiagnostico() { return idDiagnostico; }
    public void setIdDiagnostico(int idDiagnostico) { this.idDiagnostico = idDiagnostico; }

    public double getCoste() { return coste; }
    public void setCoste(double coste) { this.coste = coste; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }
}
