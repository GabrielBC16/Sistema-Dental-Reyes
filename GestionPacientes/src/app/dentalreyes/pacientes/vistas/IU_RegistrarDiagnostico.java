package app.dentalreyes.pacientes.vistas;

import app.dentalreyes.entidades.Diagnostico;
import app.dentalreyes.pacientes.control.DiagnosticoDAO;
import javax.swing.*;
import java.awt.*;

public class IU_RegistrarDiagnostico extends JDialog {

    private boolean procesado = false; // Para saber si guardó o canceló
    private int idHistorial; // Necesitamos saber a quién pertenece
    
    // Componentes
    private JTextField txtPieza;
    private JComboBox<String> cbCategoria;
    private JTextArea txtDescripcion;
    private JButton btnGuardar;

    public IU_RegistrarDiagnostico(Frame parent, int idHistorial) {
        super(parent, "Nuevo Diagnóstico", true); // true = Modal (bloquea la ventana de atrás)
        this.idHistorial = idHistorial;
        initComponents();
        this.setLocationRelativeTo(parent); // Centrado sobre la ventana padre
    }

    private void initComponents() {
        setSize(400, 450);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Registrar Hallazgo Clínico");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 114, 255));
        lblTitulo.setBounds(30, 20, 300, 30);
        add(lblTitulo);

        // PIEZA DENTAL
        add(crearLabel("Pieza Dental (Ej: 1.8, General):", 30, 70));
        txtPieza = new JTextField();
        txtPieza.setBounds(30, 95, 150, 30);
        estilizarCampo(txtPieza);
        add(txtPieza);

        // CATEGORÍA
        add(crearLabel("Categoría:", 200, 70));
        cbCategoria = new JComboBox<>(new String[]{"Caries", "Endodoncia", "Periodoncia", "Cirugía", "Estética", "Otro"});
        cbCategoria.setBounds(200, 95, 150, 30);
        cbCategoria.setBackground(Color.WHITE);
        add(cbCategoria);

        // DESCRIPCIÓN
        add(crearLabel("Descripción del Problema:", 30, 140));
        txtDescripcion = new JTextArea();
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JScrollPane scroll = new JScrollPane(txtDescripcion);
        scroll.setBounds(30, 165, 320, 100);
        add(scroll);

        // BOTÓN GUARDAR
        btnGuardar = new JButton("GUARDAR DIAGNÓSTICO");
        btnGuardar.setBackground(new Color(0, 198, 255));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setBounds(30, 300, 320, 45);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(e -> guardar());
        add(btnGuardar);
    }
    
    private void guardar() {
        // Validar
        if (txtPieza.getText().trim().isEmpty() || txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete la pieza y la descripción.");
            return;
        }

        // Crear Objeto
        Diagnostico d = new Diagnostico();
        d.setIdHistorial(this.idHistorial);
        d.setPiezaDental(txtPieza.getText().trim());
        d.setCategoria(cbCategoria.getSelectedItem().toString());
        d.setDescripcion(txtDescripcion.getText().trim());
        d.setEstado("ACTIVO"); // Por defecto nace activo
        
        // Guardar directo con DAO (simple para un dialog)
        DiagnosticoDAO dao = new DiagnosticoDAO();
        if (dao.insertarDiagnostico(d)) {
            this.procesado = true; // Marcamos éxito
            dispose(); // Cerramos ventanita
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar en base de datos.");
        }
    }

    // Utils
    private JLabel crearLabel(String t, int x, int y) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(Color.GRAY);
        l.setBounds(x, y, 200, 20);
        return l;
    }
    
    private void estilizarCampo(JTextField c) {
        c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    public boolean isProcesado() {
        return procesado;
    }
}