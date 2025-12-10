package app.dentalreyes.entidades;

import java.util.Date;

public class Anamnesis {
    private int idAnamnesis;
    private int idHistorial;
    private Date fechaRegistro;
    
    // Booleanos para Checkboxes
    private boolean hipertension;
    private boolean diabetes;
    private boolean problemasCardiacos;
    private boolean problemasCoagulacion;
    private boolean enfermedadRespiratoria;
    
    private boolean alergiaAntibioticos;
    private boolean alergiaAnestesia;
    
    private boolean embarazo;
    private boolean fuma;
    private boolean bruxismo;
    // Textos para detalles
    private String otrasAlergias;
    private String medicacionActual;
    private String motivoConsulta;
    private String observaciones;

    public Anamnesis() {
    }

    // --- GETTERS Y SETTERS ---
    
    public int getIdAnamnesis() { return idAnamnesis; }
    public void setIdAnamnesis(int idAnamnesis) { this.idAnamnesis = idAnamnesis; }

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    // Booleanos
    public boolean isHipertension() { return hipertension; }
    public void setHipertension(boolean hipertension) { this.hipertension = hipertension; }

    public boolean isDiabetes() { return diabetes; }
    public void setDiabetes(boolean diabetes) { this.diabetes = diabetes; }

    public boolean isProblemasCardiacos() { return problemasCardiacos; }
    public void setProblemasCardiacos(boolean problemasCardiacos) { this.problemasCardiacos = problemasCardiacos; }

    public boolean isProblemasCoagulacion() { return problemasCoagulacion; }
    public void setProblemasCoagulacion(boolean problemasCoagulacion) { this.problemasCoagulacion = problemasCoagulacion; }

    public boolean isEnfermedadRespiratoria() { return enfermedadRespiratoria; }
    public void setEnfermedadRespiratoria(boolean enfermedadRespiratoria) { this.enfermedadRespiratoria = enfermedadRespiratoria; }

    public boolean isAlergiaAntibioticos() { return alergiaAntibioticos; }
    public void setAlergiaAntibioticos(boolean alergiaAntibioticos) { this.alergiaAntibioticos = alergiaAntibioticos; }

    public boolean isAlergiaAnestesia() { return alergiaAnestesia; }
    public void setAlergiaAnestesia(boolean alergiaAnestesia) { this.alergiaAnestesia = alergiaAnestesia; }

    public boolean isEmbarazo() { return embarazo; }
    public void setEmbarazo(boolean embarazo) { this.embarazo = embarazo; }

    public boolean isFuma() { return fuma; }
    public void setFuma(boolean fuma) { this.fuma = fuma; }
    
    public boolean isBruxismo() { return bruxismo; }
    public void setBruxismo(boolean bruxismo) { this.bruxismo = bruxismo; }

    // Strings
    public String getOtrasAlergias() { return otrasAlergias; }
    public void setOtrasAlergias(String otrasAlergias) { this.otrasAlergias = otrasAlergias; }

    public String getMedicacionActual() { return medicacionActual; }
    public void setMedicacionActual(String medicacionActual) { this.medicacionActual = medicacionActual; }

    public String getMotivoConsulta() { return motivoConsulta; }
    public void setMotivoConsulta(String motivoConsulta) { this.motivoConsulta = motivoConsulta; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}