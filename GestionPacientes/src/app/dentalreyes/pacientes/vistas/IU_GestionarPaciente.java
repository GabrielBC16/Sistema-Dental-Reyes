package app.dentalreyes.pacientes.vistas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class IU_GestionarPaciente extends JFrame {

    // --- PALETA DE COLORES MODERNOS ---
    private final Color COLOR_GRADIENT_1 = new Color(0, 198, 255);
    private final Color COLOR_GRADIENT_2 = new Color(0, 114, 255);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_BG_BUTTON = new Color(248, 249, 250); 
    private final Color COLOR_HOVER_BUTTON = new Color(225, 245, 254);
    private final Color COLOR_TEXT_DARK = new Color(50, 50, 70);
    private final Color COLOR_TEXT_LIGHT = new Color(100, 100, 100);

    public IU_GestionarPaciente() {
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

        JLabel lblBrand = new JLabel("<html><div style='text-align: left;'>GESTIÓN DE<br>PACIENTES</div></html>");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setBounds(40, 50, 250, 100);
        leftPanel.add(lblBrand);

        JLabel lblSub = new JLabel("<html>Base de Datos<br>Clínica</html>");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSub.setForeground(new Color(255, 255, 255, 200));
        lblSub.setBounds(40, 160, 250, 60);
        leftPanel.add(lblSub);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 100));
        sep.setBounds(40, 240, 50, 10);
        leftPanel.add(sep);
        
        // Botón "Volver" incrustado en el panel izquierdo (opcional, se ve elegante)
        JButton btnVolver = new JButton("← Volver al Menú");
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

        JLabel lblOpciones = new JLabel("Operaciones Disponibles");
        lblOpciones.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblOpciones.setForeground(COLOR_TEXT_DARK);
        lblOpciones.setBounds(50, 80, 300, 30);
        rightPanel.add(lblOpciones);

        // --- BOTONES CRUD ---
        int startY = 140;
        int gap = 70;

        crearBotonDiscreto(rightPanel, "Buscar Paciente", "Consultar datos por DNI", startY, e -> abrirBuscar());
        crearBotonDiscreto(rightPanel, "Actualizar Datos", "Modificar información existente", startY + gap, e -> abrirActualizar());
        
        // Botón Eliminar con toque rojo al hover (alerta)
        crearBotonDiscreto(rightPanel, "Eliminar Paciente", "Dar de baja del sistema", startY + (gap * 2), e -> abrirEliminar());

        mainPanel.add(rightPanel);
    }

    // --- UTILS DE DISEÑO (Reutilizados para consistencia) ---
    private void crearBotonDiscreto(JPanel panel, String titulo, String desc, int y, ActionListener action) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        
        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTit.setForeground(COLOR_TEXT_DARK);
        
        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc.setForeground(COLOR_TEXT_LIGHT);
        
        textPanel.add(lblTit);
        textPanel.add(lblDesc);
        textPanel.setBorder(new EmptyBorder(5, 15, 5, 0));
        
        btn.add(textPanel, BorderLayout.CENTER);
        
        btn.setBounds(50, y, 480, 55);
        btn.setBackground(COLOR_BG_BUTTON);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(COLOR_HOVER_BUTTON);
                // Si es eliminar, borde rojo, si no azul
                Color borde = titulo.contains("Eliminar") ? new Color(255, 100, 100) : COLOR_GRADIENT_2;
                btn.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, borde));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(COLOR_BG_BUTTON);
                btn.setBorder(BorderFactory.createEmptyBorder());
            }
        });
        
        panel.add(btn);
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

    // --- MÉTODOS DE LÓGICA (Stubs) ---
    // Aquí conectaremos las nuevas ventanas CRUD cuando las tengas
    private void abrirBuscar() { 
        this.setVisible(false); // Ocultamos la ventana actual
        IU_BuscarPaciente frame = new IU_BuscarPaciente();
        
        // Al cerrar la búsqueda, que regrese este menú
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                setVisible(true);
                toFront();
            }
        });
        
        frame.setVisible(true);
    }

    private void abrirActualizar() { 
        this.setVisible(false);
        IU_ActualizarPaciente frame = new IU_ActualizarPaciente();
        
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                setVisible(true);
                toFront();
            }
        });
        
        frame.setVisible(true);
    }

    private void abrirEliminar() { 
        this.setVisible(false);
        IU_EliminarPaciente frame = new IU_EliminarPaciente();
        
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                setVisible(true);
                toFront();
            }
        });
        
        frame.setVisible(true);
    }
}