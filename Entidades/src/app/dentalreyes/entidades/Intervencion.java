/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.entidades;
import java.sql.Date;

public class Intervencion {
    private int idIntervencion;
    private String tipo;
    private String descripcion;
    private String motivo;
    private String estado;
    private Date fecha;
    private int idHistorial;

    public Intervencion() {}

    // Constructor completo
    public Intervencion(int idIntervencion, String tipo, String descripcion, String motivo, String estado, Date fecha, int idHistorial) {
        this.idIntervencion = idIntervencion;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.motivo = motivo;
        this.estado = estado;
        this.fecha = fecha;
        this.idHistorial = idHistorial;
    }

    // Getters y Setters...
    public int getIdIntervencion() { return idIntervencion; }
    public void setIdIntervencion(int idIntervencion) { this.idIntervencion = idIntervencion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }
}
