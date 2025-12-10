package app.dentalreyes.pacientes.vistas;

import app.dentalreyes.pacientes.control.G_GestionarPaciente;
import app.dentalreyes.entidades.Paciente;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Date;

public class IU_ActualizarPaciente extends JFrame {

    private G_GestionarPaciente controlador;
    private Paciente pacienteActual;

    // Componentes UI
    private JTextField txtDniBuscar, txtNombre, txtApellido, txtTelefono, txtDireccion, txtCorreo;
    private JComboBox<String> cbSexo;
    private JSpinner spinnerFecha; // Agregado porque es obligatorio en BD
    private JButton btnBuscar, btnActualizar, btnCancelar;

    // Paleta de Colores
    private final Color COLOR_GRADIENT_1 = new Color(0, 198, 255);
    private final Color COLOR_GRADIENT_2 = new Color(0, 114, 255);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_TEXT_DARK = new Color(50, 50, 70);
    private final Color COLOR_TEXT_GRAY = new Color(100, 100, 100);

    public IU_ActualizarPaciente() {
        this.controlador = new G_GestionarPaciente();
        initComponentsSplit();
    }

    private void initComponentsSplit() {
        setUndecorated(true);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 900, 600, 20, 20));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        // --- 1. SECCIÓN IZQUIERDA (35%) ---
        GradientPanel leftPanel = new GradientPanel(COLOR_GRADIENT_1, COLOR_GRADIENT_2);
        leftPanel.setBounds(0, 0, 315, 600);
        leftPanel.setLayout(null);

        JLabel lblBrand = new JLabel("<html><div style='text-align: left;'>ACTUALIZAR<br>DATOS</div></html>");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setBounds(40, 50, 250, 100);
        leftPanel.add(lblBrand);

        JLabel lblSub = new JLabel("<html>Edición de<br>Expedientes</html>");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSub.setForeground(new Color(255, 255, 255, 200));
        lblSub.setBounds(40, 160, 250, 60);
        leftPanel.add(lblSub);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 100));
        sep.setBounds(40, 240, 50, 10);
        leftPanel.add(sep);

        // Botón Volver
        JButton btnVolver = new JButton("← Cancelar / Volver");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setHorizontalAlignment(SwingConstants.LEFT);
        btnVolver.setBounds(30, 530, 200, 40);
        btnVolver.addActionListener(e -> dispose());
        leftPanel.add(btnVolver);

        mainPanel.add(leftPanel);

        // --- 2. SECCIÓN DERECHA (65% - FORMULARIO) ---
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(COLOR_WHITE);
        rightPanel.setBounds(315, 0, 585, 600);
        rightPanel.setLayout(null);

        // Botón Cerrar (X)
        JLabel lblCerrar = new JLabel("X");
        lblCerrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCerrar.setForeground(new Color(150, 150, 150));
        lblCerrar.setBounds(545, 15, 30, 30);
        lblCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
        rightPanel.add(lblCerrar);

        // --- ZONA DE BÚSQUEDA ---
        JLabel lblTitulo = new JLabel("1. Buscar Paciente");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(COLOR_TEXT_DARK);
        lblTitulo.setBounds(40, 40, 300, 30);
        rightPanel.add(lblTitulo);

        txtDniBuscar = new JTextField();
        txtDniBuscar.setBounds(40, 75, 200, 35);
        estilizarCampo(txtDniBuscar);
        rightPanel.add(txtDniBuscar);

        btnBuscar = new JButton("BUSCAR");
        estilizarBoton(btnBuscar, COLOR_GRADIENT_2, COLOR_GRADIENT_1);
        btnBuscar.setBounds(250, 75, 100, 35);
        btnBuscar.addActionListener(e -> buscarPaciente());
        rightPanel.add(btnBuscar);

        JSeparator sepForm = new JSeparator();
        sepForm.setBounds(40, 130, 500, 10);
        sepForm.setForeground(Color.LIGHT_GRAY);
        rightPanel.add(sepForm);

        // --- ZONA DE EDICIÓN ---
        JLabel lblEdicion = new JLabel("2. Editar Información");
        lblEdicion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEdicion.setForeground(COLOR_TEXT_DARK);
        lblEdicion.setBounds(40, 150, 300, 30);
        rightPanel.add(lblEdicion);

        int y = 200;
        int col1 = 40;
        int col2 = 300;
        
        // Fila 1
        agregarEtiqueta(rightPanel, "Nombres:", col1, y);
        txtNombre = new JTextField();
        txtNombre.setBounds(col1, y + 25, 240, 30);
        estilizarCampo(txtNombre);
        rightPanel.add(txtNombre);

        agregarEtiqueta(rightPanel, "Apellidos:", col2, y);
        txtApellido = new JTextField();
        txtApellido.setBounds(col2, y + 25, 240, 30);
        estilizarCampo(txtApellido);
        rightPanel.add(txtApellido);

        y += 70;
        // Fila 2
        agregarEtiqueta(rightPanel, "Teléfono:", col1, y);
        txtTelefono = new JTextField();
        txtTelefono.setBounds(col1, y + 25, 240, 30);
        estilizarCampo(txtTelefono);
        rightPanel.add(txtTelefono);

        agregarEtiqueta(rightPanel, "Sexo:", col2, y);
        cbSexo = new JComboBox<>(new String[]{"M", "F"});
        cbSexo.setBounds(col2, y + 25, 100, 30);
        cbSexo.setBackground(Color.WHITE);
        rightPanel.add(cbSexo);

        y += 70;
        // Fila 3
        agregarEtiqueta(rightPanel, "Dirección:", col1, y);
        txtDireccion = new JTextField();
        txtDireccion.setBounds(col1, y + 25, 500, 30);
        estilizarCampo(txtDireccion);
        rightPanel.add(txtDireccion);

        y += 70;
        // Fila 4
        agregarEtiqueta(rightPanel, "Correo:", col1, y);
        txtCorreo = new JTextField();
        txtCorreo.setBounds(col1, y + 25, 240, 30);
        estilizarCampo(txtCorreo);
        rightPanel.add(txtCorreo);
        
        // Fecha Nacimiento (Faltaba en tu código original)
        agregarEtiqueta(rightPanel, "Fecha Nacimiento:", col2, y);
        spinnerFecha = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
        spinnerFecha.setBounds(col2, y + 25, 150, 30);
        rightPanel.add(spinnerFecha);

        // Botón Actualizar
        btnActualizar = new JButton("GUARDAR CAMBIOS");
        estilizarBoton(btnActualizar, new Color(0, 180, 80), new Color(0, 150, 60)); // Verde
        btnActualizar.setBounds(40, 520, 500, 45);
        btnActualizar.addActionListener(e -> actualizarPaciente());
        rightPanel.add(btnActualizar);

        mainPanel.add(rightPanel);
    }

    // --- LÓGICA DE NEGOCIO ---

    private void buscarPaciente() {
        String dni = txtDniBuscar.getText().trim();
        pacienteActual = controlador.buscarPaciente(dni);

        if (pacienteActual == null) {
            JOptionPane.showMessageDialog(this, "Paciente no encontrado.", "Info", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            return;
        }

        // Llenar campos
        txtNombre.setText(pacienteActual.getNombres());
        txtApellido.setText(pacienteActual.getApellidos());
        txtTelefono.setText(pacienteActual.getTelefono());
        txtDireccion.setText(pacienteActual.getDireccion());
        txtCorreo.setText(pacienteActual.getCorreo());
        cbSexo.setSelectedItem(pacienteActual.getSexo());
        if(pacienteActual.getFechaNacimiento() != null) {
            spinnerFecha.setValue(pacienteActual.getFechaNacimiento());
        }
    }

    private void actualizarPaciente() {
        if (pacienteActual == null) {
            JOptionPane.showMessageDialog(this, "Busque un paciente primero.");
            return;
        }

        String resultado = controlador.actualizarPaciente(
            pacienteActual.getDni(), // El DNI no se cambia, es la llave
            txtNombre.getText(),
            txtApellido.getText(),
            (Date) spinnerFecha.getValue(),
            cbSexo.getSelectedItem().toString(),
            txtTelefono.getText(),
            txtDireccion.getText(),
            txtCorreo.getText()
        );

        if (resultado.startsWith("Éxito")) {
            JOptionPane.showMessageDialog(this, "✅ Datos actualizados correctamente.");
            dispose(); // Cerramos al terminar
        } else {
            JOptionPane.showMessageDialog(this, "❌ " + resultado, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        txtNombre.setText(""); txtApellido.setText(""); 
        txtTelefono.setText(""); txtDireccion.setText(""); txtCorreo.setText("");
        pacienteActual = null;
    }

    // --- UTILS DISEÑO ---
    private void agregarEtiqueta(JPanel p, String t, int x, int y) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(COLOR_TEXT_GRAY);
        l.setBounds(x, y, 150, 20);
        p.add(l);
    }

    private void estilizarCampo(JTextField txt) {
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        txt.setBackground(Color.WHITE);
    }

    private void estilizarBoton(JButton btn, Color cNormal, Color cHover) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true); // Para que se pinte en Swing moderno
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(cNormal);
        
        // Corrección de pintado plano
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(cHover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(cNormal); }
        });
    }

    class GradientPanel extends JPanel {
        private Color c1, c2;
        public GradientPanel(Color c1, Color c2) { this.c1 = c1; this.c2 = c2; }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}