package app.dentalreyes.agendas.vistas;

import app.dentalreyes.agendas.control.AgendaDAO;
import app.dentalreyes.agendas.control.G_RegistrarCita;
import app.dentalreyes.entidades.Agenda_Horarios;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class IU_RegistrarCita extends javax.swing.JFrame {

    private G_RegistrarCita controlador;
    private AgendaDAO agendaDAO;
    
    // Paleta
    private final Color COLOR_GRADIENT_1 = new Color(0, 198, 255); 
    private final Color COLOR_GRADIENT_2 = new Color(0, 114, 255); 
    private final Color COLOR_WHITE = Color.WHITE;

    public IU_RegistrarCita() {
        this.controlador = new G_RegistrarCita();
        this.agendaDAO = new AgendaDAO();
        initComponentsMejorado();
    }

    private void initComponentsMejorado() {
        setUndecorated(true);
        setSize(950, 650);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 950, 650, 20, 20));

        // Fondo
        GradientPanel panelFondo = new GradientPanel(COLOR_GRADIENT_1, COLOR_GRADIENT_2);
        panelFondo.setLayout(null);
        setContentPane(panelFondo);

        // Botón Cerrar
        JLabel lblCerrar = new JLabel("X");
        lblCerrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCerrar.setForeground(Color.WHITE);
        lblCerrar.setBounds(910, 15, 30, 30);
        lblCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
        panelFondo.add(lblCerrar);

        // --- TARJETA PRINCIPAL ---
        JPanel card = new JPanel();
        card.setBackground(COLOR_WHITE);
        card.setBounds(40, 60, 870, 550);
        card.setLayout(null);
        
        JLabel lblTitulo = new JLabel("Agendar Nueva Cita");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(50, 50, 70));
        lblTitulo.setBounds(40, 20, 300, 40);
        card.add(lblTitulo);

        // 1. BOTÓN VER CALENDARIO (NARANJA)
        JButton btnVerCalendario = new JButton("Ver Calendario Semanal");
        estilizarBoton(btnVerCalendario, new Color(255, 152, 0)); // Aplicamos la corrección aquí
        btnVerCalendario.setBounds(650, 30, 180, 30);
        btnVerCalendario.addActionListener(e -> abrirCalendarioVisual());
        card.add(btnVerCalendario);

        // 2. BUSCADOR
        JLabel lblFecha = new JLabel("1. Seleccione Fecha (yyyy-mm-dd):");
        lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFecha.setForeground(Color.GRAY);
        lblFecha.setBounds(40, 80, 250, 20);
        card.add(lblFecha);
        
        txtFecha = new JTextField();
        txtFecha.setBounds(40, 105, 150, 35);
        txtFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtFecha.setText(LocalDate.now().toString());
        card.add(txtFecha);
        
        // BOTÓN BUSCAR (AZUL)
        JButton btnBuscar = new JButton("Buscar Horarios");
        estilizarBoton(btnBuscar, COLOR_GRADIENT_2); // Aplicamos la corrección aquí
        btnBuscar.setBounds(200, 105, 150, 35);
        btnBuscar.addActionListener(e -> cargarTablaHorarios());
        card.add(btnBuscar);

        // 3. TABLA
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(40, 160, 790, 250);
        
        tablaHorarios = new JTable();
        tablaHorarios.setRowHeight(30);
        tablaHorarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Modelo con Columna ID Oculta
        tablaHorarios.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] { "ID_HIDDEN", "Fecha", "Hora Inicio", "Hora Fin" }
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        });
        
        // Ocultar columna 0 (ID) visualmente pero mantener el dato
        tablaHorarios.getColumnModel().getColumn(0).setMinWidth(0);
        tablaHorarios.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaHorarios.getColumnModel().getColumn(0).setWidth(0);
        
        scrollPane.setViewportView(tablaHorarios);
        card.add(scrollPane);

        // 4. DATOS PACIENTE
        JLabel lblDni = new JLabel("2. Ingrese DNI del Paciente:");
        lblDni.setBounds(40, 430, 200, 20);
        card.add(lblDni);
        
        txtDni = new JTextField();
        txtDni.setBounds(40, 455, 200, 35);
        card.add(txtDni);

        // BOTÓN CONFIRMAR (VERDE)
        JButton btnConfirmar = new JButton("CONFIRMAR CITA");
        estilizarBoton(btnConfirmar, new Color(0, 180, 80)); // Aplicamos la corrección aquí
        btnConfirmar.setBounds(630, 455, 200, 45);
        btnConfirmar.addActionListener(e -> guardarCita());
        card.add(btnConfirmar);

        panelFondo.add(card);
    }

    // --- MÉTODO DE CORRECCIÓN VISUAL ---
    private void estilizarBoton(JButton btn, Color color) {
        // LA LÍNEA MÁGICA: Quita el efecto 3D nativo
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI()); 
        
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Asegura que se pinte el fondo completo
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        
        // Limpieza visual
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // --- LÓGICA DE NEGOCIO ---

    private void abrirCalendarioVisual() {
        IU_RegistrarDisponibilidadHoraria visualizador = new IU_RegistrarDisponibilidadHoraria();
        visualizador.activarModoSoloLectura();
        visualizador.setVisible(true);
    }

    private void cargarTablaHorarios() {
        try {
            LocalDate fecha = LocalDate.parse(txtFecha.getText());
            List<Agenda_Horarios> libres = agendaDAO.obtenerDisponiblesDia(fecha);
            
            DefaultTableModel model = (DefaultTableModel) tablaHorarios.getModel();
            model.setRowCount(0);
            
            for (Agenda_Horarios h : libres) {
                model.addRow(new Object[]{
                    h.getIdAgendaHorario(), // ID Real (oculto)
                    h.getDia(),
                    h.getHoraInicio(),
                    h.getHoraFin()
                });
            }
            if (libres.isEmpty()) JOptionPane.showMessageDialog(this, "No hay horarios disponibles.");
            
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Fecha inválida.");
        }
    }

    private void guardarCita() {
        int fila = tablaHorarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un horario de la tabla.");
            return;
        }

        // Recuperar ID de la columna oculta
        int idAgenda = Integer.parseInt(tablaHorarios.getValueAt(fila, 0).toString());
        
        String dni = txtDni.getText().trim();
        LocalDate fecha = LocalDate.parse(tablaHorarios.getValueAt(fila, 1).toString());
        LocalTime hora = LocalTime.parse(tablaHorarios.getValueAt(fila, 2).toString());

        String resultado = controlador.registrarNuevaCita(dni, idAgenda, fecha, hora);

        if (resultado.equals("Éxito")) {
            JOptionPane.showMessageDialog(this, "✅ Cita agendada correctamente.");
            cargarTablaHorarios(); 
            txtDni.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "❌ " + resultado);
        }
    }
    
    // Variables UI
    private JTextField txtFecha, txtDni;
    private JTable tablaHorarios;
    
    class GradientPanel extends JPanel {
        private Color c1, c2;
        public GradientPanel(Color c1, Color c2) { this.c1 = c1; this.c2 = c2; }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
            g2.setPaint(gp); g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}