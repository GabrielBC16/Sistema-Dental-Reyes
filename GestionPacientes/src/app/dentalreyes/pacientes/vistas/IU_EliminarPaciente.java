package app.dentalreyes.pacientes.vistas;

import app.dentalreyes.pacientes.control.G_GestionarPaciente;
import app.dentalreyes.entidades.Paciente;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class IU_EliminarPaciente extends JFrame {

    private G_GestionarPaciente controlador;
    private JTextField txtDni;
    private JButton btnEliminar, btnCancelar;

    // Paleta
    private final Color COLOR_GRADIENT_1 = new Color(0, 198, 255);
    private final Color COLOR_GRADIENT_2 = new Color(0, 114, 255);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_TEXT_DARK = new Color(50, 50, 70);
    private final Color COLOR_TEXT_GRAY = new Color(100, 100, 100);

    public IU_EliminarPaciente() {
        this.controlador = new G_GestionarPaciente();
        initComponentsSplit();
    }

    private void initComponentsSplit() {
        setUndecorated(true);
        setSize(900, 600); // Tamaño estándar unificado
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 900, 600, 20, 20));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        // --- 1. SECCIÓN IZQUIERDA (35%) ---
        GradientPanel leftPanel = new GradientPanel(COLOR_GRADIENT_1, COLOR_GRADIENT_2);
        leftPanel.setBounds(0, 0, 315, 600);
        leftPanel.setLayout(null);

        JLabel lblBrand = new JLabel("<html><div style='text-align: left;'>ELIMINAR<br>PACIENTE</div></html>");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setBounds(40, 50, 250, 100);
        leftPanel.add(lblBrand);

        JLabel lblSub = new JLabel("<html>Dar de baja<br>del sistema</html>");
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

        // --- 2. SECCIÓN DERECHA (65%) ---
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(COLOR_WHITE);
        rightPanel.setBounds(315, 0, 585, 600);
        rightPanel.setLayout(null);

        JLabel lblCerrar = new JLabel("X");
        lblCerrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCerrar.setForeground(new Color(150, 150, 150));
        lblCerrar.setBounds(545, 15, 30, 30);
        lblCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
        rightPanel.add(lblCerrar);

        // TÍTULO
        JLabel lblTitulo = new JLabel("Ingrese DNI del Paciente a Eliminar");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(COLOR_TEXT_DARK);
        lblTitulo.setBounds(40, 80, 400, 30);
        rightPanel.add(lblTitulo);

        // CAMPO DNI
        txtDni = new JTextField();
        txtDni.setBounds(40, 120, 300, 40);
        txtDni.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtDni.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
        rightPanel.add(txtDni);

        // NOTA DE ADVERTENCIA
        JTextArea txtNota = new JTextArea("NOTA: Esta acción cambiará el estado del paciente a 'Inactivo'. El historial clínico se mantendrá guardado pero no aparecerá en búsquedas activas.");
        txtNota.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        txtNota.setForeground(Color.GRAY);
        txtNota.setBackground(new Color(248, 249, 250));
        txtNota.setLineWrap(true);
        txtNota.setWrapStyleWord(true);
        txtNota.setEditable(false);
        txtNota.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtNota.setBounds(40, 180, 450, 80);
        rightPanel.add(txtNota);

        // BOTÓN ELIMINAR (ROJO)
        btnEliminar = new JButton("CONFIRMAR ELIMINACIÓN");
        // Color rojo para indicar peligro
        estilizarBoton(btnEliminar, new Color(220, 50, 50), new Color(200, 30, 30)); 
        btnEliminar.setBounds(40, 300, 300, 50);
        btnEliminar.addActionListener(e -> eliminar());
        rightPanel.add(btnEliminar);

        mainPanel.add(rightPanel);
    }

    // --- LÓGICA DE NEGOCIO ---

    private void eliminar() {
        String dni = txtDni.getText().trim();

        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un DNI.");
            return;
        }

        // 1. Verificar si existe primero (UX)
        Paciente p = controlador.buscarPaciente(dni);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "No existe paciente con ese DNI.");
            return;
        }

        // 2. Confirmación con datos reales
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Seguro que desea eliminar al paciente?\n\n" +
            "Nombre: " + p.getNombres() + " " + p.getApellidos() + "\n" +
            "DNI: " + p.getDni(),
            "Confirmar Baja",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        // 3. Ejecutar borrado lógico
        String resultado = controlador.eliminarPaciente(dni);

        if (resultado.startsWith("Éxito")) {
            JOptionPane.showMessageDialog(this, "✅ Paciente eliminado correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "❌ " + resultado);
        }
    }

    // --- UTILS DISEÑO ---
    private void estilizarBoton(JButton btn, Color cNormal, Color cHover) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(cNormal);
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