package app.dentalreyes.pacientes.control;

import app.dentalreyes.core.ConexionMYSQL;
import app.dentalreyes.entidades.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class PacienteDAO {

    private static final String SQL_INSERT = 
        "INSERT INTO Paciente (dni, nombres, apellidos, fechaNacimiento, sexo, telefono, direccion, correo, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";
    
    private static final String SQL_UPDATE = 
        "UPDATE Paciente SET nombres=?, apellidos=?, fechaNacimiento=?, sexo=?, telefono=?, direccion=?, correo=? WHERE dni=?";

    // Eliminado lógico (estado = 0)
    private static final String SQL_DELETE =
        "UPDATE Paciente SET estado = 0 WHERE dni = ?";
    
    private static final String SQL_FIND =
        "SELECT * FROM Paciente WHERE dni = ? AND estado = 1";

    // --- REGISTRAR ---
    public boolean registrar(Paciente p) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_INSERT);
            
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombres());
            ps.setString(3, p.getApellidos());
            // Aseguramos que sea java.sql.Date
            ps.setDate(4, new java.sql.Date(p.getFechaNacimiento().getTime())); 
            ps.setString(5, p.getSexo());
            ps.setString(6, p.getTelefono());
            ps.setString(7, p.getDireccion());
            ps.setString(8, p.getCorreo());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar paciente: " + e.getMessage());
            return false;
        } finally {
            cerrar(ps, con, null);
        }
    }
    
    // --- ACTUALIZAR ---
    public boolean actualizar(Paciente p) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_UPDATE);

            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidos());
            ps.setDate(3, new java.sql.Date(p.getFechaNacimiento().getTime()));
            ps.setString(4, p.getSexo());
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getDireccion());
            ps.setString(7, p.getCorreo());
            ps.setString(8, p.getDni()); // WHERE

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar paciente: " + e.getMessage());
            return false;
        } finally {
            cerrar(ps, con, null);
        }
    }
    
    // --- ELIMINAR ---
    public boolean eliminar(String dni) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_DELETE);
            ps.setString(1, dni);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar paciente: " + e.getMessage());
            return false;
        } finally {
            cerrar(ps, con, null);
        }
    }
    
    // --- BUSCAR POR DNI ---
    public Paciente buscarPorDni(String dni) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_FIND);
            ps.setString(1, dni);
            rs = ps.executeQuery();

            if (rs.next()) {
                Paciente p = new Paciente();
                p.setIdPaciente(rs.getInt("idPaciente")); // Importante recuperar el ID
                p.setDni(rs.getString("dni"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                p.setSexo(rs.getString("sexo"));
                p.setTelefono(rs.getString("telefono"));
                p.setDireccion(rs.getString("direccion"));
                p.setCorreo(rs.getString("correo"));
                p.setEstado(rs.getInt("estado"));
                return p;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar paciente: " + e.getMessage());
        } finally {
            cerrar(ps, con, rs);
        }
        return null;
    }

    // Helper para cerrar recursos
    private void cerrar(PreparedStatement ps, Connection con, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException ex) {}
    }
}