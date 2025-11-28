package app.dentalreyes.entidades;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario; // En BD es 'username'
    private String contrasenaHash;
    private String rol; 
    private String nombreCompleto; // AGREGADO: Necesario según la BD
    private int estado;            // AGREGADO: Para saber si está activo (1) o no (0)

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreUsuario, String contrasenaHash, String rol, String nombreCompleto) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasenaHash = contrasenaHash;
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
        this.estado = 1;
    }

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
    
    @Override
    public String toString() {
        return nombreCompleto + " (" + rol + ")";
    }
}