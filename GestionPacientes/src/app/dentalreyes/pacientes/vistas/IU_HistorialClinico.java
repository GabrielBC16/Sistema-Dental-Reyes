package app.dentalreyes.pacientes.vistas;

import app.dentalreyes.entidades.*;
import app.dentalreyes.pacientes.control.G_HistorialClinico;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class IU_HistorialClinico extends JFrame {

    private final Paciente pacienteActual;
    private final G_HistorialClinico controlador;
    private HistorialClinico historialActual;

    // Componentes UI
    private JTabbedPane tabs;
    private JLabel lblNombre, lblDni, lblAlertaTexto;
    private JPanel panelAlerta;
    
    // Anamnesis
    private JCheckBox chkHipertension, chkDiabetes, chkCardiacos, chkCoagulacion, chkRespiratoria;
    private JCheckBox chkAlergiaAntibiotico, chkAlergiaAnestesia, chkEmbarazo, chkFuma, chkBruxismo;
    private JTextArea txtOtrasAlergias, txtMedicacion, txtMotivo, txtObservaciones;
    private JButton btnGuardarAnamnesis; // Promovido a variable global

    // Tablas
    private JTable tablaDiagnostico, tablaIntervencion;
    
    // BOTONES CLÍNICOS (Los que debemos ocultar al asistente)
    private JButton btnNuevoDiagnostico, btnResolverDiagnostico;
    private JButton btnNuevaIntervencion;

    // Paleta
    private final Color COLOR_GRADIENT_1 = new Color(0, 198, 255);
    private final Color COLOR_GRADIENT_2 = new Color(0, 114, 255);
    private final Color COLOR_WHITE = Color.WHITE;

    public IU_HistorialClinico(Paciente paciente) {
        this.pacienteActual = paciente;
        this.controlador = new G_HistorialClinico();
        
        this.historialActual = controlador.obtenerFicha(paciente.getIdPaciente());
        
        if (this.historialActual == null) {
            JOptionPane.showMessageDialog(null, "Error al cargar historial.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        initComponents();
        
        if (this.historialActual != null) {
            cargarDatosEnPantalla();
        }
        
        // --- APLICAR SEGURIDAD AL FINAL ---
        aplicarSeguridad();
    }
    
    // MÉTODO NUEVO: Determina qué se ve según el usuario
    private void aplicarSeguridad() {
        // Obtenemos el usuario logueado actualmente
        UsuarioSesion usuario = UsuarioSesion.getInstance();
        
        if (usuario != null && "ASISTENTE".equalsIgnoreCase(usuario.getRol())) {
            // EL ASISTENTE:
            // 1. Puede editar Anamnesis (Lo dejamos habilitado)
            
            // 2. NO PUEDE tocar diagnósticos
            btnNuevoDiagnostico.setVisible(false);
            btnResolverDiagnostico.setVisible(false);
            
            // 3. NO PUEDE tocar intervenciones
            btnNuevaIntervencion.setVisible(false);
            
            // Opcional: Deshabilitar la edición de las tablas para que no crea que puede cambiar celdas
            tablaDiagnostico.setEnabled(false);
            tablaIntervencion.setEnabled(false);
        }
    }

    private void initComponents() {
        setUndecorated(true);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 1100, 750, 20, 20));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        setContentPane(mainPanel);

        // --- ENCABEZADO ---
        GradientPanel header = new GradientPanel(COLOR_GRADIENT_1, COLOR_GRADIENT_2);
        header.setLayout(null);
        header.setPreferredSize(new Dimension(1100, 130));
        mainPanel.add(header, BorderLayout.NORTH);

        // ... (Todo el código del encabezado igual que antes) ...
        JLabel lblTitulo = new JLabel("EXPEDIENTE CLÍNICO DIGITAL");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(255, 255, 255, 180));
        lblTitulo.setBounds(30, 15, 300, 20);
        header.add(lblTitulo);

        lblNombre = new JLabel(pacienteActual.getNombres() + " " + pacienteActual.getApellidos());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(30, 35, 700, 40);
        header.add(lblNombre);

        lblDni = new JLabel("DNI: " + pacienteActual.getDni() + "  |  Tel: " + pacienteActual.getTelefono());
        lblDni.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblDni.setForeground(Color.WHITE);
        lblDni.setBounds(30, 80, 400, 20);
        header.add(lblDni);

        panelAlerta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAlerta.setBounds(750, 30, 300, 50);
        panelAlerta.setBackground(new Color(255, 80, 80));
        panelAlerta.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panelAlerta.setVisible(false);

        JLabel iconAlerta = new JLabel("⚠ ALERTAS: ");
        iconAlerta.setFont(new Font("Segoe UI", Font.BOLD, 12));
        iconAlerta.setForeground(Color.WHITE);
        lblAlertaTexto = new JLabel("-");
        lblAlertaTexto.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblAlertaTexto.setForeground(Color.WHITE);
        panelAlerta.add(iconAlerta);
        panelAlerta.add(lblAlertaTexto);
        header.add(panelAlerta);
        
        JButton btnInfo = new JButton("ℹ Ver Datos Fijos") {
            @Override
            protected void paintComponent(Graphics g) {
                // 1. Preparamos gráficos avanzados
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // 2. Pintamos el fondo nosotros mismos con tu color semitransparente
                g2.setColor(getBackground());
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                
                // 3. Dejamos que Swing pinte el texto y el borde encima
                super.paintComponent(g);
            }
        };
        btnInfo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnInfo.setForeground(Color.WHITE);
        btnInfo.setBackground(new Color(255, 255, 255, 50)); // Semitransparente
        btnInfo.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        btnInfo.setContentAreaFilled(false);
        btnInfo.setOpaque(false);
        btnInfo.setFocusPainted(false);
        btnInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnInfo.setBounds(450, 80, 120, 25); // Al lado del DNI
        
        btnInfo.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnInfo.setBackground(new Color(255, 255, 255, 90)); }
            public void mouseExited(MouseEvent e) { btnInfo.setBackground(new Color(255, 255, 255, 50)); }
        });
        
        btnInfo.addActionListener(e -> mostrarDatosFijos()); // Acción
        
        header.add(btnInfo);

        JLabel lblCerrar = new JLabel("X");
        lblCerrar.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblCerrar.setForeground(Color.WHITE);
        lblCerrar.setBounds(1060, 15, 30, 30);
        lblCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
        header.add(lblCerrar);

        // --- PESTAÑAS ---
        tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabs.setBackground(Color.WHITE);
        
        tabs.addTab("1. Anamnesis", crearPanelAnamnesis());
        tabs.addTab("2. Diagnósticos", crearPanelDiagnostico());
        tabs.addTab("3. Tratamientos", crearPanelIntervenciones());

        mainPanel.add(tabs, BorderLayout.CENTER);
    }
    private void mostrarDatosFijos() {
        // Simulamos que estos datos vienen del objeto historialActual
        // (Asegúrate de que tu DAO ya esté llenando estos campos)
        String sangre = historialActual.getGrupoSanguineo() != null ? historialActual.getGrupoSanguineo() : "No registrado";
        String consentimiento = historialActual.isFirmaConsentimiento() ? "FIRMADO" : "PENDIENTE";
        String estado = historialActual.getEstadoHistorial();
        String obs = historialActual.getObservaciones() != null ? historialActual.getObservaciones() : "Ninguna";

        JOptionPane.showMessageDialog(this,
            "DATOS ADMINISTRATIVOS DEL EXPEDIENTE\n\n" +
            "• Grupo Sanguíneo: " + sangre + "\n" +
            "• Consentimiento Informado: " + consentimiento + "\n" +
            "• Estado del Expediente: " + estado + "\n" +
            "• Observaciones Generales:\n" + obs,
            "Detalles del Historial",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    private JPanel crearPanelAnamnesis() {
        JPanel p = new JPanel(null);
        p.setBackground(COLOR_WHITE);

        int col1 = 30, col2 = 300, col3 = 600;
        int y = 20;

        agregarTitulo(p, "Enfermedades Base", col1, y);
        y += 30;
        chkHipertension = crearCheck("Hipertensión Arterial", col1, y); p.add(chkHipertension);
        chkDiabetes = crearCheck("Diabetes Mellitus", col1, y+30); p.add(chkDiabetes);
        chkCardiacos = crearCheck("Problemas Cardíacos", col1, y+60); p.add(chkCardiacos);
        chkCoagulacion = crearCheck("Prob. Coagulación (Sangrado)", col1, y+90); p.add(chkCoagulacion);
        chkRespiratoria = crearCheck("Enf. Respiratorias (Asma)", col1, y+120); p.add(chkRespiratoria);

        y = 20;
        agregarTitulo(p, "Alergias y Hábitos", col2, y);
        y += 30;
        chkAlergiaAntibiotico = crearCheck("Alergia Antibióticos", col2, y); p.add(chkAlergiaAntibiotico);
        chkAlergiaAnestesia = crearCheck("Alergia Anestesia", col2, y+30); p.add(chkAlergiaAnestesia);
        chkEmbarazo = crearCheck("Embarazo (Actual)", col2, y+60); p.add(chkEmbarazo);
        chkFuma = crearCheck("Fumador / Tabaco", col2, y+90); p.add(chkFuma);
        chkBruxismo = crearCheck("Bruxismo (Rechina dientes)", col2, y+120); p.add(chkBruxismo);

        y = 20;
        agregarTitulo(p, "Detalles Adicionales", col3, y);
        y += 30;
        p.add(new JLabel("Otras Alergias:"));
        txtOtrasAlergias = crearArea(col3, y+20, 400, 40); p.add(txtOtrasAlergias);
        
        y += 70;
        p.add(crearLabel("Medicación Actual:", col3, y));
        txtMedicacion = crearArea(col3, y+20, 400, 40); p.add(txtMedicacion);
        
        y += 70;
        p.add(crearLabel("Motivo de Consulta:", col3, y));
        txtMotivo = crearArea(col3, y+20, 400, 40); p.add(txtMotivo);

        y += 70;
        p.add(crearLabel("Observaciones Clínicas:", col3, y));
        txtObservaciones = crearArea(col3, y+20, 400, 60); p.add(txtObservaciones);

        btnGuardarAnamnesis = new JButton("GUARDAR / ACTUALIZAR FICHA");
        estilizarBoton(btnGuardarAnamnesis, new Color(0, 180, 80));
        btnGuardarAnamnesis.setBounds(30, 450, 300, 50);
        btnGuardarAnamnesis.addActionListener(e -> guardarAnamnesis());
        p.add(btnGuardarAnamnesis);

        return p;
    }

    private JPanel crearPanelDiagnostico() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        p.setBackground(COLOR_WHITE);

        tablaDiagnostico = new JTable();
        tablaDiagnostico.setRowHeight(30);
        tablaDiagnostico.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaDiagnostico.setModel(new DefaultTableModel(
            new Object[][]{}, 
            new String[]{"ID", "Fecha", "Pieza", "Categoría", "Descripción", "Estado"}
        ));
        
        JScrollPane scroll = new JScrollPane(tablaDiagnostico);
        scroll.getViewport().setBackground(Color.WHITE);
        p.add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(COLOR_WHITE);
        
        // Asignamos a las variables globales para poder ocultarlos
        btnNuevoDiagnostico = new JButton("+ Nuevo Diagnóstico");
        estilizarBoton(btnNuevoDiagnostico, COLOR_GRADIENT_2);
        btnNuevoDiagnostico.setPreferredSize(new Dimension(180, 40));
        btnNuevoDiagnostico.addActionListener(e -> {
            // 1. Abrir la ventanita modal
            IU_RegistrarDiagnostico dialog = new IU_RegistrarDiagnostico(this, historialActual.getIdHistorial());
            dialog.setVisible(true);

            // 2. Cuando se cierra, preguntamos si guardó algo
            if (dialog.isProcesado()) {
                // 3. Si guardó, recargamos la tabla para ver el nuevo dato
                cargarTablaDiagnosticos();
                JOptionPane.showMessageDialog(this, "Diagnóstico agregado.");
            }
        });
        btnResolverDiagnostico = new JButton("✓ Marcar Resuelto");
        estilizarBoton(btnResolverDiagnostico, new Color(100, 100, 100));
        btnResolverDiagnostico.setPreferredSize(new Dimension(180, 40));
        btnResolverDiagnostico.addActionListener(e -> {
        // 1. Obtener fila seleccionada
        int fila = tablaDiagnostico.getSelectedRow();
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un diagnóstico de la lista para marcarlo como resuelto.");
            return;
        }
        
        // 2. Obtener datos de la tabla (Columna 0 es ID, Columna 5 es Estado)
        int idDiagnostico = (int) tablaDiagnostico.getValueAt(fila, 0); 
        String estadoActual = (String) tablaDiagnostico.getValueAt(fila, 5); // Asumiendo que el estado está en la columna 5
        
        if ("RESUELTO".equals(estadoActual)) {
            JOptionPane.showMessageDialog(this, "Este diagnóstico ya está resuelto.");
            return;
        }

        // 3. Confirmar acción
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Confirmar que el tratamiento ha sido completado y el diagnóstico está resuelto?",
            "Cerrar Diagnóstico", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // 4. Ejecutar cambio en BD
            if (controlador.resolverDiagnostico(idDiagnostico)) {
                JOptionPane.showMessageDialog(this, "Diagnóstico actualizado a RESUELTO.");
                cargarTablaDiagnosticos(); // Recargar para ver el cambio
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar.");
            }
        }
    });
        
        btnPanel.add(btnNuevoDiagnostico);
        btnPanel.add(btnResolverDiagnostico);
        p.add(btnPanel, BorderLayout.NORTH);

        return p;
    }

    private JPanel crearPanelIntervenciones() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        p.setBackground(COLOR_WHITE);

        tablaIntervencion = new JTable();
        tablaIntervencion.setRowHeight(30);
        tablaIntervencion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaIntervencion.setModel(new DefaultTableModel(
            new Object[][]{}, 
            new String[]{"ID", "Fecha", "Pieza", "Tratamiento", "Estado", "Costo"}
        ));
        
        p.add(new JScrollPane(tablaIntervencion), BorderLayout.CENTER);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(COLOR_WHITE);
        
        // Asignar variable global
        btnNuevaIntervencion = new JButton("+ Registrar Tratamiento");
        estilizarBoton(btnNuevaIntervencion, COLOR_GRADIENT_2);
        btnNuevaIntervencion.setPreferredSize(new Dimension(200, 40));
        
        btnNuevaIntervencion.addActionListener(e -> {
            // 1. Abrir modal
            IU_RegistrarIntervencion dialog = new IU_RegistrarIntervencion(this, historialActual.getIdHistorial());
            dialog.setVisible(true); // Espera aquí

            // 2. Al cerrar, verificar si guardó
            if (dialog.isProcesado()) {
                cargarTablaIntervenciones(); // Refrescar tabla
                JOptionPane.showMessageDialog(this, "Tratamiento registrado correctamente.");
            }
        });
        
        btnPanel.add(btnNuevaIntervencion);
        p.add(btnPanel, BorderLayout.NORTH);
        
        return p;
    }

    // ... (El resto de métodos: cargarDatosEnPantalla, guardarAnamnesis, etc. se mantienen igual) ...
    // ... Copia los métodos del archivo anterior aquí abajo ...
    
    // --- LÓGICA DE CARGA Y GUARDADO ---
    private void cargarDatosEnPantalla() {
        Anamnesis a = controlador.obtenerAnamnesis(historialActual.getIdHistorial());
        if (a != null) {
            chkHipertension.setSelected(a.isHipertension());
            chkDiabetes.setSelected(a.isDiabetes());
            chkCardiacos.setSelected(a.isProblemasCardiacos());
            chkCoagulacion.setSelected(a.isProblemasCoagulacion());
            chkRespiratoria.setSelected(a.isEnfermedadRespiratoria());
            chkAlergiaAntibiotico.setSelected(a.isAlergiaAntibioticos());
            chkAlergiaAnestesia.setSelected(a.isAlergiaAnestesia());
            chkEmbarazo.setSelected(a.isEmbarazo());
            chkFuma.setSelected(a.isFuma());
            chkBruxismo.setSelected(a.isBruxismo());
            txtOtrasAlergias.setText(a.getOtrasAlergias());
            txtMedicacion.setText(a.getMedicacionActual());
            txtMotivo.setText(a.getMotivoConsulta());
            txtObservaciones.setText(a.getObservaciones());
            actualizarAlertaHeader();
        }
        cargarTablaDiagnosticos();
        cargarTablaIntervenciones();
    }

    private void guardarAnamnesis() {
        Anamnesis a = new Anamnesis();
        a.setIdHistorial(historialActual.getIdHistorial());
        a.setHipertension(chkHipertension.isSelected());
        a.setDiabetes(chkDiabetes.isSelected());
        a.setProblemasCardiacos(chkCardiacos.isSelected());
        a.setProblemasCoagulacion(chkCoagulacion.isSelected());
        a.setEnfermedadRespiratoria(chkRespiratoria.isSelected());
        a.setAlergiaAntibioticos(chkAlergiaAntibiotico.isSelected());
        a.setAlergiaAnestesia(chkAlergiaAnestesia.isSelected());
        a.setEmbarazo(chkEmbarazo.isSelected());
        a.setFuma(chkFuma.isSelected());
        a.setBruxismo(chkBruxismo.isSelected());
        a.setOtrasAlergias(txtOtrasAlergias.getText());
        a.setMedicacionActual(txtMedicacion.getText());
        a.setMotivoConsulta(txtMotivo.getText());
        a.setObservaciones(txtObservaciones.getText());

        if (controlador.guardarAnamnesis(a)) {
            JOptionPane.showMessageDialog(this, "Anamnesis actualizada correctamente.");
            actualizarAlertaHeader();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar.");
        }
    }
    
    private void actualizarAlertaHeader() {
        String alertas = controlador.obtenerAlertas(historialActual.getIdHistorial());
        lblAlertaTexto.setText(alertas);
        panelAlerta.setVisible(!alertas.equals("Ninguna alerta registrada") && !alertas.equals("Sin datos registrados"));
    }

    private void cargarTablaDiagnosticos() {
        List<Diagnostico> lista = controlador.listarDiagnosticos(historialActual.getIdHistorial());
        DefaultTableModel model = (DefaultTableModel) tablaDiagnostico.getModel();
        model.setRowCount(0);
        for (Diagnostico d : lista) {
            model.addRow(new Object[]{ d.getIdDiagnostico(), d.getFechaDiagnostico(), d.getPiezaDental(), d.getCategoria(), d.getDescripcion(), d.getEstado() });
        }
    }

    private void cargarTablaIntervenciones() {
        List<Intervencion> lista = controlador.listarIntervenciones(historialActual.getIdHistorial());
        DefaultTableModel model = (DefaultTableModel) tablaIntervencion.getModel();
        model.setRowCount(0);
        for (Intervencion i : lista) {
            model.addRow(new Object[]{ i.getIdIntervencion(), i.getFechaIntervencion(), i.getPiezaDental(), i.getTratamiento(), i.getEstado(), "S/ " + i.getCosto() });
        }
    }

    private void agregarTitulo(JPanel p, String t, int x, int y) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 16));
        l.setForeground(COLOR_GRADIENT_2);
        l.setBounds(x, y, 250, 20);
        p.add(l);
    }

    private JCheckBox crearCheck(String texto, int x, int y) {
        JCheckBox chk = new JCheckBox(texto);
        chk.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chk.setBackground(COLOR_WHITE);
        chk.setBounds(x, y, 250, 25);
        return chk;
    }
    
    private JLabel crearLabel(String texto, int x, int y) {
        JLabel l = new JLabel(texto);
        l.setBounds(x, y, 200, 20);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return l;
    }

    private JTextArea crearArea(int x, int y, int w, int h) {
        JTextArea t = new JTextArea();
        t.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        t.setLineWrap(true);
        t.setBounds(x, y, w, h);
        return t;
    }

    private void estilizarBoton(JButton btn, Color c) {
        btn.setBackground(c);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
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