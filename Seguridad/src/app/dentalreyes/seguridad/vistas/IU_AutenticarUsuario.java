package app.dentalreyes.seguridad.vistas;

import app.dentalreyes.seguridad.control.G_AutenticarUsuario;
import app.dentalreyes.entidades.Usuario;
import app.dentalreyes.pacientes.vistas.IU_MenuDentista;
import app.dentalreyes.pacientes.vistas.IU_MenuAsistente;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class IU_AutenticarUsuario extends javax.swing.JFrame {

    private G_AutenticarUsuario controlador;

    // --- PALETA DE COLORES (MODERNA Y ARMÓNICA) ---
    // Azul "Dental" Profundo
    private final Color COLOR_GRADIENT_1 = new Color(0, 198, 255); 
    // Azul "Tecnológico"
    private final Color COLOR_GRADIENT_2 = new Color(0, 114, 255); 
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_TEXT_GRAY = new Color(150, 150, 150);
    private final Color COLOR_TEXT_DARK = new Color(50, 50, 70);

    public IU_AutenticarUsuario() {
        this.controlador = new G_AutenticarUsuario();
        initComponentsModerno();
    }

    private void initComponentsModerno() {
        setUndecorated(true); // Quitamos el borde feo de Windows para hacerlo custom
        setSize(850, 500);
        setLocationRelativeTo(null);
        // Bordes redondeados de la ventana completa
        setShape(new RoundRectangle2D.Double(0, 0, 850, 500, 20, 20)); 

        // 1. PANEL DE FONDO (DEGRADADO COMPLETO)
        GradientPanel panelFondo = new GradientPanel(COLOR_GRADIENT_1, COLOR_GRADIENT_2);
        panelFondo.setLayout(null);
        setContentPane(panelFondo);

        // Botón de Salir (X) personalizado
        JLabel lblCerrar = new JLabel("X");
        lblCerrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCerrar.setForeground(Color.WHITE);
        lblCerrar.setBounds(820, 10, 30, 30);
        lblCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { System.exit(0); }
        });
        panelFondo.add(lblCerrar);

        // 2. TARJETA BLANCA FLOTANTE (EL FORMULARIO)
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(COLOR_WHITE);
        cardPanel.setBounds(450, 50, 350, 400); // Flotando a la derecha
        cardPanel.setLayout(null);
        
        // Título
        JLabel lblTitulo = new JLabel("Bienvenido");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(COLOR_TEXT_DARK);
        lblTitulo.setBounds(40, 40, 200, 40);
        cardPanel.add(lblTitulo);

        JLabel lblSub = new JLabel("Inicia sesión en Dental Reyes");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(COLOR_TEXT_GRAY);
        lblSub.setBounds(42, 80, 250, 20);
        cardPanel.add(lblSub);

        // --- CAMPOS DE TEXTO ESTILO MATERIAL ---
        JLabel lblUser = new JLabel("USUARIO");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblUser.setForeground(COLOR_TEXT_GRAY);
        lblUser.setBounds(40, 130, 100, 20);
        cardPanel.add(lblUser);

        txtUsuario = new JTextField();
        estilizarCampo(txtUsuario);
        txtUsuario.setBounds(40, 150, 270, 30);
        cardPanel.add(txtUsuario);
        // Línea decorativa azul
        JSeparator sep1 = new JSeparator();
        sep1.setForeground(COLOR_GRADIENT_2);
        sep1.setBounds(40, 180, 270, 10);
        cardPanel.add(sep1);

        JLabel lblPass = new JLabel("CONTRASEÑA");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblPass.setForeground(COLOR_TEXT_GRAY);
        lblPass.setBounds(40, 200, 100, 20);
        cardPanel.add(lblPass);

        txtPassword = new JPasswordField();
        estilizarCampo(txtPassword);
        txtPassword.setBounds(40, 220, 270, 30);
        cardPanel.add(txtPassword);
        JSeparator sep2 = new JSeparator();
        sep2.setForeground(COLOR_GRADIENT_2);
        sep2.setBounds(40, 250, 270, 10);
        cardPanel.add(sep2);

        // --- BOTÓN DEGRADADO MODERNO ---
        JButton btnLogin = new JButton("INGRESAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Gradiente en el botón
                GradientPaint gp = new GradientPaint(0, 0, COLOR_GRADIENT_2, getWidth(), 0, COLOR_GRADIENT_1);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                
                super.paintComponent(g);
            }
        };
        btnLogin.setBounds(40, 300, 270, 45);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(evt -> logicaLogin());
        
        cardPanel.add(btnLogin);

        panelFondo.add(cardPanel);

        // 3. TEXTO E IMAGEN EN EL LADO IZQUIERDO (SOBRE EL AZUL)
        JLabel lblBrand = new JLabel("<html><div style='text-align: center;'>DENTAL<br>REYES</div></html>");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblBrand.setForeground(new Color(255, 255, 255, 200)); // Blanco con transparencia
        lblBrand.setBounds(80, 150, 300, 150);
        panelFondo.add(lblBrand);
        
        JLabel lblSlogan = new JLabel("Gestión profesional para sonrisas brillantes.");
        lblSlogan.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblSlogan.setForeground(Color.WHITE);
        lblSlogan.setBounds(85, 300, 300, 30);
        panelFondo.add(lblSlogan);
    }

    // --- UTILIDADES DE DISEÑO ---
    private void estilizarCampo(JTextField campo) {
        campo.setBorder(null);
        campo.setBackground(COLOR_WHITE);
        campo.setForeground(COLOR_TEXT_DARK);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    // --- CLASE INTERNA PARA EL FONDO DEGRADADO ---
    class GradientPanel extends JPanel {
        private Color color1, color2;
        public GradientPanel(Color c1, Color c2) {
            this.color1 = c1;
            this.color2 = c2;
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // --- LÓGICA DE LOGIN (IGUAL QUE ANTES) ---
    private void logicaLogin() {
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese sus credenciales.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuarioLogueado = controlador.validarCredenciales(usuario, password);

        if (usuarioLogueado != null) {
            this.dispose(); 
            if (usuarioLogueado.getRol().equalsIgnoreCase("Dentista")) {
                new IU_MenuDentista().setVisible(true);
            } else {
                new IU_MenuAsistente().setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables
    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new IU_AutenticarUsuario().setVisible(true));
    }
}