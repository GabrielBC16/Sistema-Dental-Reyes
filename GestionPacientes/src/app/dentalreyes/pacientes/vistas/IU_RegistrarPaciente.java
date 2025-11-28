package app.dentalreyes.pacientes.vistas;

import app.dentalreyes.pacientes.control.G_RegistrarPaciente;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.Border;

public class IU_RegistrarPaciente extends javax.swing.JFrame {

    private G_RegistrarPaciente controlador;
    
    // Paleta
    private final Color COLOR_GRADIENT_1 = new Color(0, 198, 255); 
    private final Color COLOR_GRADIENT_2 = new Color(0, 114, 255); 
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_TEXT_GRAY = new Color(100, 100, 100);

    public IU_RegistrarPaciente() {
        this.controlador = new G_RegistrarPaciente();
        initComponentsModerno();
    }

    private void initComponentsModerno() {
        setUndecorated(true);
        setSize(550, 700);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 550, 700, 20, 20));

        // Fondo
        GradientPanel panelFondo = new GradientPanel(COLOR_GRADIENT_1, COLOR_GRADIENT_2);
        panelFondo.setLayout(null);
        setContentPane(panelFondo);
        
        // Botón Cerrar
        JLabel lblCerrar = new JLabel("X");
        lblCerrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCerrar.setForeground(Color.WHITE);
        lblCerrar.setBounds(510, 15, 30, 30);
        lblCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
        panelFondo.add(lblCerrar);

        // Tarjeta Formulario
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(COLOR_WHITE);
        cardPanel.setBounds(25, 60, 500, 600);
        cardPanel.setLayout(null);
        
        JLabel lblTitulo = new JLabel("Nuevo Paciente");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(50, 50, 70));
        lblTitulo.setBounds(40, 20, 300, 40);
        cardPanel.add(lblTitulo);

        // Campos
        txtDni = crearCampo(cardPanel, "DNI", 40, 80, 150);
        txtTelefono = crearCampo(cardPanel, "Teléfono", 260, 80, 200);
        
        txtNombres = crearCampo(cardPanel, "Nombres", 40, 150, 420);
        txtApellidos = crearCampo(cardPanel, "Apellidos", 40, 220, 420);
        
        txtFecha = crearCampo(cardPanel, "F. Nacimiento (yyyy-mm-dd)", 40, 290, 200);
        
        JLabel lblSexo = new JLabel("Sexo");
        lblSexo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSexo.setForeground(COLOR_TEXT_GRAY);
        lblSexo.setBounds(260, 290, 100, 20);
        cardPanel.add(lblSexo);
        
        cboSexo = new JComboBox<>(new String[]{"M", "F"});
        cboSexo.setBounds(260, 315, 100, 35);
        cboSexo.setBackground(Color.WHITE);
        cardPanel.add(cboSexo);

        txtDireccion = crearCampo(cardPanel, "Dirección", 40, 360, 420);
        txtCorreo = crearCampo(cardPanel, "Correo Electrónico", 40, 430, 420);

        // Botones
        btnGuardar = crearBoton("GUARDAR DATOS", COLOR_GRADIENT_2, COLOR_GRADIENT_1, 260, 520, 200);
        btnGuardar.addActionListener(evt -> guardarPaciente());

        btnCancelar = crearBoton("CANCELAR", new Color(200, 200, 200), new Color(150, 150, 150), 40, 520, 150);
        btnCancelar.addActionListener(evt -> this.dispose());

        cardPanel.add(btnGuardar);
        cardPanel.add(btnCancelar);
        panelFondo.add(cardPanel);
    }

    // --- UTILIDADES DE DISEÑO ---
    private JTextField crearCampo(JPanel panel, String titulo, int x, int y, int ancho) {
        JLabel label = new JLabel(titulo);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(COLOR_TEXT_GRAY);
        label.setBounds(x, y, ancho, 20);
        panel.add(label);

        JTextField campo = new JTextField();
        campo.setBounds(x, y + 20, ancho, 35);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 200, 200))); // Solo línea inferior
        panel.add(campo);
        return campo;
    }

    private JButton crearBoton(String texto, Color cNormal, Color cHover, int x, int y, int ancho) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, ancho, 45);
        // Lógica de diseño para botón redondeado (igual que en Menús)
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(cHover); btn.repaint(); }
            public void mouseExited(MouseEvent e) { btn.setBackground(cNormal); btn.repaint(); }
        });
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btn.getBackground() == null ? cNormal : btn.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 30, 30);
                super.paint(g, c);
                g2.dispose();
            }
        });
        btn.setBackground(cNormal);
        return btn;
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

    // --- LÓGICA DEL CONTROLADOR ---
    private void guardarPaciente() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = sdf.parse(txtFecha.getText());
            
            String respuesta = controlador.registrarPaciente(
                txtDni.getText(), txtNombres.getText(), txtApellidos.getText(), 
                fecha, cboSexo.getSelectedItem().toString(), 
                txtTelefono.getText(), txtDireccion.getText(), txtCorreo.getText()
            );
            
            if(respuesta.startsWith("Éxito")) {
                JOptionPane.showMessageDialog(this, "✅ " + respuesta);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ " + respuesta);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error de fecha (yyyy-mm-dd)");
        }
    }

    private JTextField txtDni, txtNombres, txtApellidos, txtTelefono, txtFecha, txtDireccion, txtCorreo;
    private JComboBox<String> cboSexo;
    private JButton btnGuardar, btnCancelar;
}