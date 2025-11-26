/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.entidades;

public class Anamnesis {
    private int idAnamnesis;
    private String antecedentes;
    private String enfermedadesPrevias;
    private String alergias;
    private String habitosHigiene;
    private int idHistorial; // Llave foránea

    public Anamnesis() {}

    public Anamnesis(int idAnamnesis, String antecedentes, String enfermedadesPrevias, String alergias, String habitosHigiene, int idHistorial) {
        this.idAnamnesis = idAnamnesis;
        this.antecedentes = antecedentes;
        this.enfermedadesPrevias = enfermedadesPrevias;
        this.alergias = alergias;
        this.habitosHigiene = habitosHigiene;
        this.idHistorial = idHistorial;
    }

    // Getters y Setters estándar...
    public int getIdAnamnesis() { return idAnamnesis; }
    public void setIdAnamnesis(int idAnamnesis) { this.idAnamnesis = idAnamnesis; }
    // ... (Genera los getters y setters para los campos String restantes aquí)
    public String getAntecedentes() { return antecedentes; }
    public void setAntecedentes(String antecedentes) { this.antecedentes = antecedentes; }
    
    public String getEnfermedadesPrevias() { return enfermedadesPrevias; }
    public void setEnfermedadesPrevias(String enfermedadesPrevias) { this.enfermedadesPrevias = enfermedadesPrevias; }
    
    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }
    
    public String getHabitosHigiene() { return habitosHigiene; }
    public void setHabitosHigiene(String habitosHigiene) { this.habitosHigiene = habitosHigiene; }

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }
}
