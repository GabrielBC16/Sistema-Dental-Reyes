/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.entidades;


public class HistorialClinico {
    private int idHistorial;
    private int idPaciente; // Relación 1 a 1 con Paciente

    public HistorialClinico() {}

    public HistorialClinico(int idHistorial, int idPaciente) {
        this.idHistorial = idHistorial;
        this.idPaciente = idPaciente;
    }

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
}
