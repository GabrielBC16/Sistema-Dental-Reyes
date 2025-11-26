/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.seguridad.control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SeguridadUtils {
    /**
     * Encripta un texto usando SHA-256 (Nativo de Java).
     * @param password Contraseña plana (ej: "123456")
     * @return Hash hexadecimal (ej: "8d969eef6ecad3c29a3a62...")
     */
    public static String encriptarSHA256(String password) {
        try {
            // Usamos SHA-256 que viene en el JDK
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convertir los bytes a formato Hexadecimal (String legible)
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al inicializar SHA-256", e);
        }
    }
}
