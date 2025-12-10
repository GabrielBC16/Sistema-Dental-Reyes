package app.dentalreyes.pacientes.vistas;

import app.dentalreyes.entidades.Intervencion;
import app.dentalreyes.pacientes.control.IntervencionDAO;
import javax.swing.*;
import java.awt.*;

public class IU_RegistrarIntervencion extends JDialog {

    private boolean procesado = false;
    private int idHistorial;
    
    // Componentes
    private JTextField txtPieza, txtTratamiento, txtCosto;
    private JTextArea txtDescripcion, txtObservaciones;
    private JButton btnGuardar;

    public IU_RegistrarIntervencion(Frame parent, int idHistorial) {
        super(parent, "Nuevo Tratamiento / Intervención", true);
        this.idHistorial = idHistorial;
        initComponents();
        this.setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setSize(450, 600); // Un poco más alto
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Registrar Procedimiento");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 114, 255));
        lblTitulo.setBounds(30, 20, 300, 30);
        add(lblTitulo);

        // PIEZA
        int y = 70;
        add(crearLabel("Pieza Dental (Ej: 2.4, Cuadrante 1):", 30, y));
        txtPieza = new JTextField();
        txtPieza.setBounds(30, y + 25, 180, 30);
        estilizarCampo(txtPieza);
        add(txtPieza);

        // COSTO (Importante)
        add(crearLabel("Costo (S/.):", 240, y));
        txtCosto = new JTextField("0.00");
        txtCosto.setBounds(240, y + 25, 150, 30);
        estilizarCampo(txtCosto);
        txtCosto.setHorizontalAlignment(JTextField.RIGHT);
        add(txtCosto);

        // TRATAMIENTO
        y += 70;
        add(crearLabel("Tratamiento (Ej: Endodoncia, Resina):", 30, y));
        txtTratamiento = new JTextField();
        txtTratamiento.setBounds(30, y + 25, 360, 30);
        estilizarCampo(txtTratamiento);
        add(txtTratamiento);

        // DESCRIPCIÓN (Para el paciente)
        y += 70;
        add(crearLabel("Descripción Corta (Para presupuesto):", 30, y));
        txtDescripcion = new JTextArea();
        txtDescripcion.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        txtDescripcion.setLineWrap(true);
        JScrollPane s1 = new JScrollPane(txtDescripcion);
        s1.setBounds(30, y + 25, 360, 50);
        add(s1);

        // OBSERVACIONES TÉCNICAS (Para el doctor)
        y += 90;
        add(crearLabel("Notas Técnicas (Materiales, anestesia...):", 30, y));
        txtObservaciones = new JTextArea();
        txtObservaciones.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        txtObservaciones.setLineWrap(true);
        JScrollPane s2 = new JScrollPane(txtObservaciones);
        s2.setBounds(30, y + 25, 360, 60);
        add(s2);

        // BOTÓN
        btnGuardar = new JButton("REGISTRAR PROCEDIMIENTO");
        btnGuardar.setBackground(new Color(0, 198, 255));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setBounds(30, 480, 360, 45);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(e -> guardar());
        add(btnGuardar);
    }

    private void guardar() {
        // Validar
        if (txtPieza.getText().isEmpty() || txtTratamiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la Pieza y el Tratamiento.");
            return;
        }

        try {
            double costo = Double.parseDouble(txtCosto.getText().replace(",", ".")); // Manejo de decimales

            // Objeto
            Intervencion i = new Intervencion();
            i.setIdHistorial(this.idHistorial);
            i.setPiezaDental(txtPieza.getText());
            i.setTratamiento(txtTratamiento.getText());
            i.setDescripcion(txtDescripcion.getText());
            i.setObservaciones(txtObservaciones.getText());
            i.setCosto(costo);
            i.setEstado("REALIZADO"); // Por defecto ya se hizo

            // Guardar
            IntervencionDAO dao = new IntervencionDAO();
            if (dao.registrarIntervencion(i)) {
                this.procesado = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en BD.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El costo debe ser un número válido (Ej: 150.00).");
        }
    }

    // Utils
    private JLabel crearLabel(String t, int x, int y) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(Color.GRAY);
        l.setBounds(x, y, 300, 20);
        return l;
    }
    
    private void estilizarCampo(JTextField c) {
        c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.LIGHT_GRAY));
    }

    public boolean isProcesado() { return procesado; }
}