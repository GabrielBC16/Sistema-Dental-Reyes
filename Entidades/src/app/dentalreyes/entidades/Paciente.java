package app.dentalreyes.entidades;

import java.sql.Date;

public class Paciente {
    private int idPaciente;
    private String dni;
    private String nombres;
    private String apellidos;
    private Date fechaNacimiento; // CORREGIDO: La BD usa fecha, no edad
    private String sexo;          // AGREGADO: 'M' o 'F'
    private String telefono;
    private String direccion;     // AGREGADO
    private String correo;        // AGREGADO
    private int estado;

    public Paciente() {
    }

    // Constructor útil para el registro rápido
    public Paciente(String dni, String nombres, String apellidos, Date fechaNacimiento, String sexo, String telefono) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.telefono = telefono;
        this.estado = 1;
    }

    // Getters y Setters
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    // Método auxiliar para calcular edad (opcional, para mostrar en pantalla)
    public int getEdadCalculada() {
        if (fechaNacimiento == null) return 0;
        long diff = new java.util.Date().getTime() - fechaNacimiento.getTime();
        return (int) (diff / (1000L * 60 * 60 * 24 * 365)); 
    }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}