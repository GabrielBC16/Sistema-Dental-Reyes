package app.dentalreyes.pacientes.vistas;

import app.dentalreyes.agendas.vistas.IU_RegistrarCita;
import app.dentalreyes.entidades.UsuarioSesion;
import app.dentalreyes.pacientes.control.G_GestionarPaciente;
import app.dentalreyes.entidades.Paciente;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class IU_MenuAsistente extends javax.swing.JFrame {

    // --- PALETA DE COLORES ---
    private final Color COLOR_GRADIENT_1 = new Color(0, 198, 255); 
    private final Color COLOR_GRADIENT_2 = new Color(0, 114, 255); 
    
    // Colores para el panel derecho y botones discretos
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_BG_BUTTON = new Color(248, 249, 250); // Gris muy suave
    private final Color COLOR_HOVER_BUTTON = new Color(225, 245, 254); // Celeste muy pálido
    private final Color COLOR_TEXT_DARK = new Color(50, 50, 70);
    private final Color COLOR_TEXT_LIGHT = new Color(100, 100, 100);

    public IU_MenuAsistente() {
        initComponentsSplit();
    }

    private void initComponentsSplit() {
        setUndecorated(true); 
        setSize(900, 600);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 900, 600, 20, 20)); 

        // Panel Principal contenedor
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        // --- 1. SECCIÓN IZQUIERDA (35% - BRANDING) ---
        GradientPanel leftPanel = new GradientPanel(COLOR_GRADIENT_1, COLOR_GRADIENT_2);
        leftPanel.setBounds(0, 0, 315, 600);
        leftPanel.setLayout(null);

        JLabel lblBrand = new JLabel("<html><div style='text-align: left;'>PANEL<br>ASISTENTE</div></html>");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setBounds(40, 50, 250, 100);
        leftPanel.add(lblBrand);

        JLabel lblSub = new JLabel("<html>Gestión<br>Administrativa</html>");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        lblSub.setForeground(new Color(255, 255, 255, 200));
        lblSub.setBounds(40, 160, 250, 60);
        leftPanel.add(lblSub);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 100));
        sep.setBounds(40, 240, 50, 10);
        leftPanel.add(sep);

        mainPanel.add(leftPanel);

        // --- 2. SECCIÓN DERECHA (65% - ACCIONES) ---
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
            public void mouseClicked(MouseEvent e) { cerrarSesion(); }
            public void mouseEntered(MouseEvent e) { lblCerrar.setForeground(Color.BLACK); }
            public void mouseExited(MouseEvent e) { lblCerrar.setForeground(new Color(150, 150, 150)); }
        });
        rightPanel.add(lblCerrar);

        JLabel lblOpciones = new JLabel("Opciones Disponibles");
        lblOpciones.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblOpciones.setForeground(COLOR_TEXT_DARK);
        lblOpciones.setBounds(50, 80, 300, 30);
        rightPanel.add(lblOpciones);

        // --- BOTONES DE MENÚ ---
        int startY = 140;
        int gap = 70;
        
        crearBotonDiscreto(rightPanel, "Crear Historia Clínica", "Registrar nuevo paciente", startY, e -> abrirCrearHistoria());
        crearBotonDiscreto(rightPanel, "Agendar Cita", "Gestionar disponibilidad", startY + gap, e -> abrirGestionCitas());
        crearBotonDiscreto(rightPanel, "Anamnesis", "Antecedentes médicos", startY + (gap * 2), e -> abrirAnamnesis());
        
        // ✅ NUEVO BOTÓN AGREGADO AQUÍ
        crearBotonDiscreto(rightPanel, "Gestionar Pacientes", "Buscar, editar y eliminar", startY + (gap * 3), e -> abrirGestionarPacientes());

        // --- BOTÓN CERRAR SESIÓN ---
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setForeground(new Color(120, 120, 120));
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        btnLogout.setBounds(420, 530, 130, 40);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { 
                btnLogout.setForeground(new Color(220, 50, 50));
                btnLogout.setBorder(BorderFactory.createLineBorder(new Color(220, 50, 50)));
            }
            public void mouseExited(MouseEvent e) { 
                btnLogout.setForeground(new Color(120, 120, 120)); 
                btnLogout.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
            }
        });
        btnLogout.addActionListener(e -> cerrarSesion());
        rightPanel.add(btnLogout);

        mainPanel.add(rightPanel);
    }

    // --- UTILS DE DISEÑO ---
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
                btn.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, COLOR_GRADIENT_2));
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

    // --- NAVEGACIÓN ---
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Cerrar sesión actual?", "Salir", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            UsuarioSesion.logout();
            this.dispose();
            try {
                Class<?> claseLogin = Class.forName("app.dentalreyes.seguridad.vistas.IU_AutenticarUsuario");
                javax.swing.JFrame loginFrame = (javax.swing.JFrame) claseLogin.getDeclaredConstructor().newInstance();
                loginFrame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Por favor reinicie la aplicación.");
            }
        }
    }

    private void abrirCrearHistoria() {
        this.setVisible(false);
        IU_RegistrarPaciente registrarFrame = new IU_RegistrarPaciente();
        registrarFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                setVisible(true); toFront();
            }
        });
        registrarFrame.setVisible(true);
    }

    private void abrirGestionCitas() {
        this.setVisible(false);
        IU_RegistrarCita ventanaCitas = new IU_RegistrarCita();
        ventanaCitas.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                setVisible(true); toFront();
            }
        });
        ventanaCitas.setVisible(true);
    }
    
    // ✅ NUEVO MÉTODO DE NAVEGACIÓN
    private void abrirGestionarPacientes() {
        this.setVisible(false); // Ocultar Menú
        IU_GestionarPaciente gestionar = new IU_GestionarPaciente();
        
        // Agregar listener para que el menú reaparezca al cerrar la ventana hija
        gestionar.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                setVisible(true); 
                toFront();
            }
        });
        
        gestionar.setVisible(true);
    }

    private void abrirAnamnesis() {
        // 1. Pedir DNI con un cuadro de diálogo simple
        String dni = JOptionPane.showInputDialog(this, "Ingrese el DNI del Paciente para abrir su Historia:", "Acceso a Anamnesis", JOptionPane.QUESTION_MESSAGE);

        // Si el usuario no canceló y escribió algo
        if (dni != null && !dni.trim().isEmpty()) {
            
            // 2. Usamos el controlador para buscar
            app.dentalreyes.pacientes.control.G_GestionarPaciente control = new app.dentalreyes.pacientes.control.G_GestionarPaciente();
            app.dentalreyes.entidades.Paciente p = control.buscarPaciente(dni);

            if (p != null) {
                // 3. Si existe, abrimos la ventana GRANDE de Historial
                this.setVisible(false); // Ocultamos menú

                // Le pasamos el paciente encontrado
                IU_HistorialClinico historialFrame = new IU_HistorialClinico(p); 
                
                // Configurar retorno al cerrar
                historialFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        setVisible(true);
                        toFront();
                    }
                });
                
                historialFrame.setVisible(true);
                
            } else {
                JOptionPane.showMessageDialog(this, "Paciente no encontrado. Verifique el DNI.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}