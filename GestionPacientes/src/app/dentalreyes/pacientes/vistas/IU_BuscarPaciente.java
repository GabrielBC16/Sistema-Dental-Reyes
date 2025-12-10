package app.dentalreyes.pacientes.vistas;

import app.dentalreyes.entidades.Paciente;
import app.dentalreyes.pacientes.control.G_GestionarPaciente;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class IU_BuscarPaciente extends JFrame {

    private G_GestionarPaciente controlador;
    
    // Componentes
    private JTextField txtDni;
    private JLabel lblNombreVal, lblDniVal, lblTelVal, lblCorreoVal; 
    private JButton btnBuscar;

    // Paleta
    private final Color COLOR_GRADIENT_1 = new Color(0, 198, 255);
    private final Color COLOR_GRADIENT_2 = new Color(0, 114, 255);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_TEXT_DARK = new Color(50, 50, 70);
    private final Color COLOR_TEXT_GRAY = new Color(100, 100, 100);

    public IU_BuscarPaciente() {
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

        JLabel lblBrand = new JLabel("<html><div style='text-align: left;'>CONSULTAR<br>PACIENTE</div></html>");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setBounds(40, 50, 250, 100);
        leftPanel.add(lblBrand);

        JLabel lblSub = new JLabel("<html>Búsqueda rápida<br>por DNI</html>");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSub.setForeground(new Color(255, 255, 255, 200));
        lblSub.setBounds(40, 160, 250, 60);
        leftPanel.add(lblSub);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 100));
        sep.setBounds(40, 240, 50, 10);
        leftPanel.add(sep);

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

        // BUSCADOR
        JLabel lblTitulo = new JLabel("Ingrese DNI del Paciente:");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(COLOR_TEXT_DARK);
        lblTitulo.setBounds(40, 40, 300, 30);
        rightPanel.add(lblTitulo);

        txtDni = new JTextField();
        txtDni.setBounds(40, 80, 250, 35);
        txtDni.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDni.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        rightPanel.add(txtDni);

        btnBuscar = new JButton("BUSCAR");
        estilizarBoton(btnBuscar, COLOR_GRADIENT_2, COLOR_GRADIENT_1);
        btnBuscar.setBounds(310, 80, 120, 35);
        btnBuscar.addActionListener(e -> buscar());
        rightPanel.add(btnBuscar);

        JSeparator sep2 = new JSeparator();
        sep2.setBounds(40, 140, 500, 10);
        sep2.setForeground(Color.LIGHT_GRAY);
        rightPanel.add(sep2);

        // RESULTADOS (Estilo Tarjeta de Información)
        JLabel lblResTitle = new JLabel("Información del Paciente");
        lblResTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblResTitle.setForeground(COLOR_TEXT_DARK);
        lblResTitle.setBounds(40, 160, 300, 30);
        rightPanel.add(lblResTitle);

        // Panel interno para resultados (Ahora con Layout Nulo para control total)
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(null); // <--- CAMBIO IMPORTANTE: Layout Nulo
        panelInfo.setBounds(40, 200, 500, 250);
        panelInfo.setBackground(new Color(248, 249, 250)); // Fondo gris suave
        panelInfo.setBorder(new EmptyBorder(15, 15, 15, 15));

        // FILA 1: Nombre (Damos mucho ancho)
        panelInfo.add(crearLabel("Nombre Completo:", 20, 20));
        lblNombreVal = crearValor("-", 150, 20, 330); // Ancho 330px para que entre todo el nombre
        panelInfo.add(lblNombreVal);

        // FILA 2: DNI
        panelInfo.add(crearLabel("DNI:", 20, 60));
        lblDniVal = crearValor("-", 150, 60, 200);
        panelInfo.add(lblDniVal);

        // FILA 3: Teléfono
        panelInfo.add(crearLabel("Teléfono:", 20, 100));
        lblTelVal = crearValor("-", 150, 100, 200);
        panelInfo.add(lblTelVal);

        // FILA 4: Correo
        panelInfo.add(crearLabel("Correo:", 20, 140));
        lblCorreoVal = crearValor("-", 150, 140, 300);
        panelInfo.add(lblCorreoVal);

        rightPanel.add(panelInfo);
        mainPanel.add(rightPanel);
    }

    // --- LÓGICA DE NEGOCIO ---
    private void buscar() {
        String dni = txtDni.getText().trim();
        if(dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un DNI.");
            return;
        }

        Paciente p = controlador.buscarPaciente(dni);

        if (p != null) {
            // Mostrar datos
            lblNombreVal.setText(p.getNombres() + " " + p.getApellidos());
            lblNombreVal.setForeground(new Color(0, 150, 0)); // Verde
            lblDniVal.setText(p.getDni());
            lblTelVal.setText(p.getTelefono());
            lblCorreoVal.setText(p.getCorreo());
        } else {
            // Limpiar y avisar
            lblNombreVal.setText("NO ENCONTRADO");
            lblNombreVal.setForeground(Color.RED);
            lblDniVal.setText("-");
            lblTelVal.setText("-");
            lblCorreoVal.setText("-");
            JOptionPane.showMessageDialog(this, "Paciente no encontrado.");
        }
    }

    // --- UTILS DISEÑO ---
    private JLabel crearLabel(String texto, int x, int y) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(COLOR_TEXT_GRAY);
        l.setBounds(x, y, 120, 20); // Ancho fijo para las etiquetas
        return l;
    }

    private JLabel crearValor(String texto, int x, int y, int ancho) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(COLOR_TEXT_DARK);
        l.setBounds(x, y, ancho, 20); // Ancho variable
        return l;
    }

    private void estilizarBoton(JButton btn, Color cNormal, Color cHover) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
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