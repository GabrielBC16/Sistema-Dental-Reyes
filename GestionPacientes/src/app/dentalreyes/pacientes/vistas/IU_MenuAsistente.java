package app.dentalreyes.pacientes.vistas;

import app.dentalreyes.pacientes.vistas.IU_RegistrarPaciente; // Importamos la vista real
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class IU_MenuAsistente extends javax.swing.JFrame {

    // Paleta (Idéntica para consistencia)
    private final Color COLOR_GRADIENT_1 = new Color(0, 198, 255); 
    private final Color COLOR_GRADIENT_2 = new Color(0, 114, 255); 
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_TEXT_DARK = new Color(50, 50, 70);

    public IU_MenuAsistente() {
        initComponentsModerno();
    }

    private void initComponentsModerno() {
        setUndecorated(true); 
        setSize(900, 600);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 900, 600, 20, 20)); 

        // Fondo
        GradientPanel panelFondo = new GradientPanel(COLOR_GRADIENT_1, COLOR_GRADIENT_2);
        panelFondo.setLayout(null);
        setContentPane(panelFondo);

        JLabel lblCerrar = new JLabel("X");
        lblCerrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCerrar.setForeground(Color.WHITE);
        lblCerrar.setBounds(860, 15, 30, 30);
        lblCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { cerrarSesion(); }
        });
        panelFondo.add(lblCerrar);

        JLabel lblBrand = new JLabel("PANEL ASISTENTE");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setBounds(40, 30, 300, 30);
        panelFondo.add(lblBrand);

        // Tarjeta Central
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(COLOR_WHITE);
        cardPanel.setBounds(150, 100, 600, 450);
        cardPanel.setLayout(null);
        
        JLabel lblHola = new JLabel("Gestión Administrativa");
        lblHola.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHola.setForeground(COLOR_TEXT_DARK);
        lblHola.setBounds(40, 30, 300, 30);
        cardPanel.add(lblHola);

        // Botones
        int y = 100;
        crearBotonMenu(cardPanel, "Crear Historia Clínica (Nuevo Paciente)", y, e -> abrirCrearHistoria());
        crearBotonMenu(cardPanel, "Registrar Cita", y + 70, e -> abrirGestionCitas());
        crearBotonMenu(cardPanel, "Registrar Anamnesis", y + 140, e -> abrirAnamnesis());

        JButton btnLogout = new JButton("Cerrar Sesión");
        estilizarBoton(btnLogout, new Color(255, 80, 80), new Color(220, 50, 50));
        btnLogout.setBounds(40, 380, 520, 45);
        btnLogout.addActionListener(e -> cerrarSesion());
        cardPanel.add(btnLogout);

        panelFondo.add(cardPanel);
    }

    // Métodos de diseño (Copia de MenuDentista)
    private void crearBotonMenu(JPanel panel, String texto, int y, ActionListener action) {
        JButton btn = new JButton(texto);
        estilizarBoton(btn, COLOR_GRADIENT_2, COLOR_GRADIENT_1);
        btn.setBounds(40, y, 520, 45);
        btn.addActionListener(action);
        panel.add(btn);
    }

    private void estilizarBoton(JButton btn, Color colorNormal, Color colorHover) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(colorHover); btn.repaint(); }
            public void mouseExited(MouseEvent e) { btn.setBackground(colorNormal); btn.repaint(); }
        });
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btn.getBackground() == null ? colorNormal : btn.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 30, 30);
                super.paint(g, c);
                g2.dispose();
            }
        });
        btn.setBackground(colorNormal);
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

    // Navegación
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Cerrar Sesión?", "Salir", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) this.dispose();
    }
    private void abrirCrearHistoria() {
        IU_RegistrarPaciente registrarFrame = new IU_RegistrarPaciente();
        registrarFrame.setVisible(true);
    }
    private void abrirGestionCitas() { JOptionPane.showMessageDialog(this, "Próximamente: Citas"); }
    private void abrirAnamnesis() { JOptionPane.showMessageDialog(this, "Próximamente: Anamnesis"); }
}