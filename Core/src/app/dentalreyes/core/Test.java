/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.core;


/**
 *
 * @author Soporte
 */
public class Test {
    public static void main(String[] args) {
        // Instanciamos nuestra clase de arquitectura
        ConexionMYSQL con = new ConexionMYSQL();
        con.obtenerConexion();
        con.cerrarConexion();
    }
}
