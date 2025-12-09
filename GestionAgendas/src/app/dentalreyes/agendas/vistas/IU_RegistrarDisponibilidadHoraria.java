package app.dentalreyes.agendas.vistas;

import app.dentalreyes.agendas.control.G_RegistrarDisponibilidadHoraria;
import app.dentalreyes.entidades.Agenda_Horarios;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class IU_RegistrarDisponibilidadHoraria extends javax.swing.JFrame {

    private final G_RegistrarDisponibilidadHoraria controlador;

    // Componentes UI
    private JSpinner spinnerFecha;
    private JPanel gridPanel;
    private JLabel lblMensaje;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JLabel lblContadorSeleccionados;

    // Lista de horarios modificados
    private List<HorarioSeleccionado> horariosSeleccionados = new ArrayList<>();

    // Configuración de horarios
    private static final LocalTime HORA_INICIO = LocalTime.of(8, 0);
    private static final LocalTime HORA_FIN = LocalTime.of(18, 0);
    private static final int MINUTOS_BLOQUE = 30;

    // Colores personalizados
    private static final Color COLOR_LIBRE = new Color(238, 238, 238);
    private static final Color COLOR_DISPONIBLE = new Color(144, 238, 144);
    private static final Color COLOR_OCUPADO = new Color(255, 182, 193);
    private static final Color COLOR_HOVER = new Color(200, 230, 201);
    private static final Color COLOR_PRIMARY = new Color(33, 150, 243);
    private static final Color COLOR_SUCCESS = new Color(76, 175, 80);
    private static final Color COLOR_DANGER = new Color(244, 67, 54);

    public IU_RegistrarDisponibilidadHoraria() {
        this.controlador = new G_RegistrarDisponibilidadHoraria();
        inicializarComponentes();
        cargarHorariosExistentes();
    }

    private void inicializarComponentes() {
        setTitle("Gestión de Disponibilidad Horaria - Dental Reyes");
        setSize(1000, 700);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel superior: Título y selector de fecha
        add(crearPanelSuperior(), BorderLayout.NORTH);

        // Panel central: Grid de horarios
        add(crearPanelCentral(), BorderLayout.CENTER);

        // Panel inferior: Mensaje y botones
        add(crearPanelInferior(), BorderLayout.SOUTH);

        // Estilos generales
        getContentPane().setBackground(Color.WHITE);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 20, 10, 20));

        // Título
        JLabel lblTitulo = new JLabel("Registrar Disponibilidad Horaria");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_PRIMARY);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Seleccione los horarios en los que estará disponible para atender");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(Color.GRAY);

        JPanel panelTitulos = new JPanel(new GridLayout(2, 1, 0, 5));
        panelTitulos.setBackground(Color.WHITE);
        panelTitulos.add(lblTitulo);
        panelTitulos.add(lblSubtitulo);

        // Selector de semana
        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        selectorPanel.setBackground(Color.WHITE);

        JLabel lblFecha = new JLabel("Semana del:");
        lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 13));

        spinnerFecha = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
        spinnerFecha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ((JSpinner.DefaultEditor) spinnerFecha.getEditor()).getTextField().setEditable(false);

        JButton btnCargar = crearBoton("Cargar Semana", COLOR_PRIMARY, Color.WHITE);
        btnCargar.addActionListener(e -> cargarHorariosExistentes());

        JButton btnHoy = crearBoton("Semana Actual", new Color(156, 39, 176), Color.WHITE);
        btnHoy.addActionListener(e -> {
            spinnerFecha.setValue(new Date());
            cargarHorariosExistentes();
        });

        selectorPanel.add(lblFecha);
        selectorPanel.add(spinnerFecha);
        selectorPanel.add(btnCargar);
        selectorPanel.add(btnHoy);

        panel.add(panelTitulos, BorderLayout.NORTH);
        panel.add(selectorPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 20, 5, 20));

        // Leyenda
        JPanel leyendaPanel = crearPanelLeyenda();

        // Grid de horarios
        gridPanel = new JPanel();
        gridPanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(gridPanel);
        scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(leyendaPanel, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelLeyenda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 0, 10, 0));

        panel.add(crearItemLeyenda("Libre", COLOR_LIBRE));
        panel.add(crearItemLeyenda("Disponible", COLOR_DISPONIBLE));
        panel.add(crearItemLeyenda("Ocupado (con cita)", COLOR_OCUPADO));

        lblContadorSeleccionados = new JLabel("Horarios modificados: 0");
        lblContadorSeleccionados.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblContadorSeleccionados.setForeground(COLOR_PRIMARY);
        panel.add(Box.createHorizontalStrut(30));
        panel.add(lblContadorSeleccionados);

        return panel;
    }

    private JPanel crearItemLeyenda(String texto, Color color) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        item.setBackground(Color.WHITE);

        JPanel colorBox = new JPanel();
        colorBox.setPreferredSize(new Dimension(20, 20));
        colorBox.setBackground(color);
        colorBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        item.add(colorBox);
        item.add(label);

        return item;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 20, 15, 20));

        // Mensaje
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);

        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        botonesPanel.setBackground(Color.WHITE);

        btnLimpiar = crearBoton("Limpiar Selección", new Color(255, 152, 0), Color.WHITE);
        btnLimpiar.addActionListener(e -> limpiarSeleccion());

        btnGuardar = crearBoton("Guardar Disponibilidad", COLOR_SUCCESS, Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(180, 35));
        btnGuardar.addActionListener(e -> guardarDisponibilidad());

        JButton btnCancelar = crearBoton("Cerrar", COLOR_DANGER, Color.WHITE);
        btnCancelar.addActionListener(e -> dispose());

        botonesPanel.add(btnLimpiar);
        botonesPanel.add(btnGuardar);
        botonesPanel.add(btnCancelar);

        panel.add(lblMensaje, BorderLayout.CENTER);
        panel.add(botonesPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton crearBoton(String texto, Color fondo, Color textoColor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(fondo);
        btn.setForeground(textoColor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 32));

        // Efecto hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(fondo.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(fondo);
            }
        });

        return btn;
    }

    private void cargarHorariosExistentes() {
        // Mostrar cursor de espera
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnGuardar.setEnabled(false);

        try {
            horariosSeleccionados.clear();
            gridPanel.removeAll();

            // Obtener lunes de la semana seleccionada
            Date fecha = (Date) spinnerFecha.getValue();
            LocalDate baseDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    .with(java.time.DayOfWeek.MONDAY);

            LocalDate inicioSemana = baseDate;
            LocalDate finSemana = baseDate.plusDays(4);

            // Actualizar spinner a lunes
            spinnerFecha.setValue(Date.from(inicioSemana.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            // Obtener horarios de BD
            List<Agenda_Horarios> horariosBD = 
                controlador.obtenerHorariosSemana(inicioSemana, finSemana);

            // Crear grid
            int numeroFilas = ((HORA_FIN.toSecondOfDay() - HORA_INICIO.toSecondOfDay()) / 60 / MINUTOS_BLOQUE) + 1;
            gridPanel.setLayout(new GridLayout(numeroFilas + 1, 6, 2, 2));

            // Encabezados
            agregarEncabezado("", true);
            String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
            for (String dia : dias) {
                agregarEncabezado(dia + "\n" + inicioSemana.plusDays(java.util.Arrays.asList(dias).indexOf(dia))
                        .format(DateTimeFormatter.ofPattern("dd/MM")), false);
            }

            // Crear botones de horario
            LocalTime horaActual = HORA_INICIO;
            while (horaActual.isBefore(HORA_FIN)) {
                // Columna de hora
                JLabel lblHora = new JLabel(horaActual.format(DateTimeFormatter.ofPattern("HH:mm")));
                lblHora.setFont(new Font("Segoe UI", Font.BOLD, 12));
                lblHora.setHorizontalAlignment(SwingConstants.CENTER);
                lblHora.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                lblHora.setOpaque(true);
                lblHora.setBackground(new Color(245, 245, 245));
                gridPanel.add(lblHora);

                // Botones para cada día
                for (int i = 0; i < 5; i++) {
                    LocalDate fechaDia = inicioSemana.plusDays(i);
                    JButton btn = crearBotonHorario(fechaDia, horaActual, horariosBD);
                    gridPanel.add(btn);
                }

                horaActual = horaActual.plusMinutes(MINUTOS_BLOQUE);
            }

            gridPanel.revalidate();
            gridPanel.repaint();

            mostrarMensaje("Horarios cargados correctamente para la semana del " + 
                          inicioSemana.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 
                          COLOR_SUCCESS);
            actualizarContador();

        } catch (Exception e) {
            mostrarMensaje("ERROR al cargar horarios: " + e.getMessage(), COLOR_DANGER);
            e.printStackTrace();
        } finally {
            setCursor(Cursor.getDefaultCursor());
            btnGuardar.setEnabled(true);
        }
    }

    private void agregarEncabezado(String texto, boolean esHora) {
        JLabel lbl = new JLabel("<html><center>" + texto.replace("\n", "<br>") + "</center></html>");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, esHora ? 12 : 11));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lbl.setOpaque(true);
        lbl.setBackground(new Color(33, 150, 243));
        lbl.setForeground(Color.WHITE);
        lbl.setPreferredSize(new Dimension(esHora ? 80 : 150, 40));
        gridPanel.add(lbl);
    }

    private JButton crearBotonHorario(LocalDate fecha, LocalTime hora, List<Agenda_Horarios> horariosBD) {
        JButton btn = new JButton("Libre");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(COLOR_LIBRE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 35));

        HorarioSeleccionado hs = new HorarioSeleccionado(fecha, hora, "LIBRE");
        btn.putClientProperty("horario", hs);

        // Verificar si existe en BD
        for (Agenda_Horarios bd : horariosBD) {
            if (bd.getDia().equals(fecha) && bd.getHoraInicio().equals(hora)) {
                hs.estado = bd.getEstado();

                if ("DISPONIBLE".equals(bd.getEstado())) {
                    btn.setText("✓ Disponible");
                    btn.setBackground(COLOR_DISPONIBLE);
                } else if ("OCUPADO".equals(bd.getEstado())) {
                    btn.setText("✗ Ocupado");
                    btn.setBackground(COLOR_OCUPADO);
                    btn.setEnabled(false);
                    btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                break;
            }
        }

        // Efecto hover (solo si está habilitado)
        if (btn.isEnabled()) {
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                Color colorOriginal = btn.getBackground();

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (!btn.getBackground().equals(COLOR_OCUPADO)) {
                        btn.setBackground(COLOR_HOVER);
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(colorOriginal);
                }
            });
        }

        btn.addActionListener(e -> toggleHorario(btn));

        return btn;
    }

    private void toggleHorario(JButton btn) {
        HorarioSeleccionado hs = (HorarioSeleccionado) btn.getClientProperty("horario");

        switch (hs.estado) {
            case "LIBRE":
                hs.estado = "DISPONIBLE";
                btn.setText("✓ Disponible");
                btn.setBackground(COLOR_DISPONIBLE);
                if (!horariosSeleccionados.contains(hs)) {
                    horariosSeleccionados.add(hs);
                }
                break;

            case "DISPONIBLE":
                hs.estado = "LIBRE";
                btn.setText("Libre");
                btn.setBackground(COLOR_LIBRE);
                horariosSeleccionados.remove(hs);
                break;

            case "OCUPADO":
                JOptionPane.showMessageDialog(this,
                        "No puede modificar un horario que ya tiene una cita programada.",
                        "Horario Ocupado",
                        JOptionPane.WARNING_MESSAGE);
                return;
        }

        actualizarContador();
    }

    private void limpiarSeleccion() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de limpiar todos los horarios seleccionados?",
                "Confirmar Limpieza",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            for (Component comp : gridPanel.getComponents()) {
                if (comp instanceof JButton) {
                    JButton btn = (JButton) comp;
                    HorarioSeleccionado hs = (HorarioSeleccionado) btn.getClientProperty("horario");

                    if (hs != null && "DISPONIBLE".equals(hs.estado)) {
                        hs.estado = "LIBRE";
                        btn.setText("Libre");
                        btn.setBackground(COLOR_LIBRE);
                    }
                }
            }

            horariosSeleccionados.clear();
            actualizarContador();
            mostrarMensaje("Selección limpiada correctamente.", COLOR_SUCCESS);
        }
    }

    private void guardarDisponibilidad() {
        if (horariosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay cambios para guardar. Seleccione al menos un horario.",
                    "Sin Cambios",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Confirma que desea guardar " + horariosSeleccionados.size() + " horario(s) como disponible(s)?",
                "Confirmar Guardado",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnGuardar.setEnabled(false);

        try {
            List<Agenda_Horarios> lista = new ArrayList<>();

            for (HorarioSeleccionado hs : horariosSeleccionados) {
                Agenda_Horarios a = new Agenda_Horarios();
                a.setDia(hs.fecha);
                a.setHoraInicio(hs.hora);
                a.setHoraFin(hs.hora.plusMinutes(MINUTOS_BLOQUE));
                a.setEstado("DISPONIBLE");
                lista.add(a);
            }

            String resultado = controlador.guardarDisponibilidad(lista);

            if (resultado.startsWith("ERROR")) {
                JOptionPane.showMessageDialog(this,
                        resultado,
                        "Error al Guardar",
                        JOptionPane.ERROR_MESSAGE);
                mostrarMensaje(resultado, COLOR_DANGER);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Disponibilidad guardada exitosamente.\n" + 
                        horariosSeleccionados.size() + " horario(s) registrado(s).",
                        "Guardado Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
                mostrarMensaje(resultado, COLOR_SUCCESS);
                horariosSeleccionados.clear();
                cargarHorariosExistentes();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            setCursor(Cursor.getDefaultCursor());
            btnGuardar.setEnabled(true);
        }
    }

    private void actualizarContador() {
        lblContadorSeleccionados.setText("Horarios modificados: " + horariosSeleccionados.size());
    }

    private void mostrarMensaje(String msg, Color color) {
        lblMensaje.setText(msg);
        lblMensaje.setForeground(color);

    }

    // Clase auxiliar
    private static class HorarioSeleccionado {
        LocalDate fecha;
        LocalTime hora;
        String estado;

        HorarioSeleccionado(LocalDate f, LocalTime h, String e) {
            this.fecha = f;
            this.hora = h;
            this.estado = e;
        }
    }

    // Método main para testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            IU_RegistrarDisponibilidadHoraria frame = new IU_RegistrarDisponibilidadHoraria();
            frame.setVisible(true);
        });
    }
}