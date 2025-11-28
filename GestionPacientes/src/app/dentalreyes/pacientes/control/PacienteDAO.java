package app.dentalreyes.pacientes.control; // Ajusta el paquete si es diferente

import app.dentalreyes.core.ConexionMYSQL;
import app.dentalreyes.entidades.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PacienteDAO {

    private static final String SQL_INSERT = 
        "INSERT INTO Paciente (dni, nombres, apellidos, fechaNacimiento, sexo, telefono, estado) VALUES (?, ?, ?, ?, ?, ?, 1)";

    public boolean registrar(Paciente p) {
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_INSERT);
            
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombres());
            ps.setString(3, p.getApellidos());
            ps.setDate(4, p.getFechaNacimiento()); // Debe ser java.sql.Date
            ps.setString(5, p.getSexo());
            ps.setString(6, p.getTelefono());
            ps.setString(7, p.getDireccion());
            ps.setString(8, p.getCorreo());
            
            int filas = ps.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar paciente: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) { }
        }
    }
}