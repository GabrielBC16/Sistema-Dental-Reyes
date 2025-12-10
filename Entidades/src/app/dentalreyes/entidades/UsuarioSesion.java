package app.dentalreyes.entidades;

public class UsuarioSesion {

    // 1. La instancia estática (Singleton)
    private static UsuarioSesion instance;

    // 2. Atributos del usuario logueado
    private int idUsuario;
    private String username;
    private String rol;      // "DENTISTA" o "ASISTENTE"
    private String nombreCompleto;

    // 3. Constructor privado para que nadie haga "new UsuarioSesion()"
    private UsuarioSesion() {
    }

    // 4. MÉTODO MÁGICO: getInstance()
    // Si la sesión no existe, la crea. Si existe, te devuelve la actual.
    public static UsuarioSesion getInstance() {
        if (instance == null) {
            instance = new UsuarioSesion();
        }
        return instance;
    }

    // 5. Método para iniciar sesión (Llenar datos)
    public void iniciarSesion(int id, String user, String rol, String nombre) {
        this.idUsuario = id;
        this.username = user;
        this.rol = rol;
        this.nombreCompleto = nombre;
    }

    // 6. Método para cerrar sesión (Limpiar todo)
    public static void logout() {
        instance = null; // Destruimos la instancia
    }

    // --- GETTERS (Para leer los datos) ---
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public String getRol() {  // <--- ESTE ES EL QUE TE FALTABA
        return rol;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
}