package app.dentalreyes.agendas.vistas;

import app.dentalreyes.agendas.control.G_RegistrarDisponibilidadHoraria;
import app.dentalreyes.entidades.Agenda_Horarios;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
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
    private JLabel lblContadorSeleccionados;
    private boolean esSoloLectura = false;

    // Lista de cambios
    private List<HorarioSeleccionado> horariosSeleccionados = new ArrayList<>();

    // Configuración
    private static final LocalTime HORA_INICIO = LocalTime.of(8, 0);
    private static final LocalTime HORA_FIN = LocalTime.of(18, 0);
    private static final int MINUTOS_BLOQUE = 60;

    // Colores
    private final Color COLOR_GRADIENT_1 = new Color(0, 198, 255);
    private final Color COLOR_GRADIENT_2 = new Color(0, 114, 255);
    private static final Color COLOR_LIBRE = new Color(245, 245, 245);
    private static final Color COLOR_DISPONIBLE = new Color(129, 199, 132); // Verde pastel
    private static final Color COLOR_OCUPADO = new Color(229, 57, 53);   // Rojo

    public IU_RegistrarDisponibilidadHoraria() {
        this.controlador = new G_RegistrarDisponibilidadHoraria();
        initComponentsCompacto();
        cargarHorariosExistentes();
    }
    
    private void initComponentsCompacto() {
        setUndecorated(true);
        // 1. TAMAÑO AJUSTADO PARA TU MONITOR
        setSize(900, 600); 
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 900, 600, 15, 15));

        // Fondo Degradado
        GradientPanel panelFondo = new GradientPanel(COLOR_GRADIENT_1, COLOR_GRADIENT_2);
        panelFondo.setLayout(new BorderLayout(10, 10));
        panelFondo.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panelFondo);

        // Barra Superior
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        JLabel lblTitulo = new JLabel(" Disponibilidad Horaria");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblCerrar = new JLabel("X   ");
        lblCerrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCerrar.setForeground(Color.WHITE);
        lblCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
        
        topBar.add(lblTitulo, BorderLayout.WEST);
        topBar.add(lblCerrar, BorderLayout.EAST);
        panelFondo.add(topBar, BorderLayout.NORTH);

        // Panel Central Blanco
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Selector Fecha
        JPanel selector = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selector.setBackground(Color.WHITE);
        selector.add(new JLabel("Semana del: "));
        spinnerFecha = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
        selector.add(spinnerFecha);
        
        JButton btnCargar = new JButton("Cargar");
        btnCargar.addActionListener(e -> cargarHorariosExistentes());
        selector.add(btnCargar);
        
        // Leyenda
        selector.add(Box.createHorizontalStrut(20));
        selector.add(crearItemLeyenda("Libre", COLOR_LIBRE));
        selector.add(crearItemLeyenda("Disponible", COLOR_DISPONIBLE));
        selector.add(crearItemLeyenda("Ocupado", COLOR_OCUPADO));
        
        cardPanel.add(selector, BorderLayout.NORTH);

        // Grid con Scroll
        gridPanel = new JPanel();
        gridPanel.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(gridPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        cardPanel.add(scroll, BorderLayout.CENTER);

        // Panel Inferior
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(Color.WHITE);
        
        lblMensaje = new JLabel("Listo.");
        lblContadorSeleccionados = new JLabel("Modificados: 0");
        
        JPanel info = new JPanel(new GridLayout(2,1));
        info.setBackground(Color.WHITE);
        info.add(lblMensaje);
        info.add(lblContadorSeleccionados);
        
        btnGuardar = new JButton("GUARDAR CAMBIOS");
        btnGuardar.setBackground(new Color(0, 180, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        btnGuardar.setOpaque(true);
        btnGuardar.setContentAreaFilled(true);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        
        btnGuardar.addActionListener(e -> guardarDisponibilidad());
        
        bottom.add(info, BorderLayout.WEST);
        bottom.add(btnGuardar, BorderLayout.EAST);
        cardPanel.add(bottom, BorderLayout.SOUTH);

        panelFondo.add(cardPanel, BorderLayout.CENTER);
    }

    private void cargarHorariosExistentes() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            horariosSeleccionados.clear();
            gridPanel.removeAll();

            Date fecha = (Date) spinnerFecha.getValue();
            LocalDate baseDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    .with(java.time.DayOfWeek.MONDAY);

            LocalDate inicioSemana = baseDate;
            LocalDate finSemana = baseDate.plusDays(4); 
            spinnerFecha.setValue(Date.from(inicioSemana.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            // Obtener de BD
            List<Agenda_Horarios> horariosBD = controlador.obtenerHorariosSemana(inicioSemana, finSemana);

            // Calcular filas
            int totalMinutos = (int) java.time.temporal.ChronoUnit.MINUTES.between(HORA_INICIO, HORA_FIN);
            int filas = (totalMinutos / MINUTOS_BLOQUE);
            gridPanel.setLayout(new GridLayout(filas + 1, 6, 2, 2));

            // Encabezados
            addHeader("", true);
            String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
            for (int i = 0; i < 5; i++) {
                addHeader(dias[i] + " " + inicioSemana.plusDays(i).format(DateTimeFormatter.ofPattern("dd/MM")), false);
            }

            // Filas
            LocalTime horaActual = HORA_INICIO;
            while (horaActual.isBefore(HORA_FIN)) {
                // Hora
                JLabel lblH = new JLabel(horaActual.toString());
                lblH.setHorizontalAlignment(SwingConstants.CENTER);
                lblH.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                gridPanel.add(lblH);

                // Botones
                for (int i = 0; i < 5; i++) {
                    LocalDate fechaDia = inicioSemana.plusDays(i);
                    
                    // 2. BÚSQUEDA ROBUSTA (Corregida para que pinte los colores)
                    Agenda_Horarios encontrado = null;
                    if(horariosBD != null) {
                        for(Agenda_Horarios h : horariosBD) {
                            // Comparamos usando métodos de tiempo de Java, no Strings
                            if(h.getDia().isEqual(fechaDia) && 
                               (h.getHoraInicio().equals(horaActual) || 
                                // A veces la BD devuelve 08:00:00 y Java tiene 08:00. Esto lo arregla:
                                h.getHoraInicio().getHour() == horaActual.getHour() && 
                                h.getHoraInicio().getMinute() == horaActual.getMinute())) {
                                encontrado = h;
                                break;
                            }
                        }
                    }
                    
                    gridPanel.add(crearBoton(fechaDia, horaActual, encontrado));
                }
                horaActual = horaActual.plusMinutes(MINUTOS_BLOQUE);
            }

            gridPanel.revalidate();
            gridPanel.repaint();
            lblMensaje.setText("Cargado correctamente.");
            actualizarContador();

        } catch (Exception e) {
            e.printStackTrace();
            lblMensaje.setText("Error cargando datos.");
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private JButton crearBoton(LocalDate f, LocalTime h, Agenda_Horarios datosBD) {
        JButton btn = new JButton("Libre");
        
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI()); 
        
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11)); // Letra negrita para que se lea bien en blanco
        btn.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setFocusPainted(false);
        
        HorarioSeleccionado hs = new HorarioSeleccionado(f, h, "LIBRE");

        if (datosBD != null && datosBD.getEstado() != null) {
            String estadoBD = datosBD.getEstado().trim();
            hs.estado = estadoBD;
            
            if (estadoBD.equalsIgnoreCase("DISPONIBLE")) {
                btn.setText("Disponible");
                btn.setBackground(COLOR_DISPONIBLE); // Ahora se verá el Verde Suave
                btn.setForeground(Color.WHITE);      // Letra Blanca
            } else if (estadoBD.equalsIgnoreCase("OCUPADO")) {
                btn.setText("Ocupado");
                btn.setBackground(COLOR_OCUPADO);
                btn.setForeground(Color.WHITE);
                btn.setEnabled(false); 
            }
        } else {
            // Estado Libre
            btn.setBackground(COLOR_LIBRE);
            btn.setForeground(Color.BLACK);
        }

        btn.putClientProperty("hs", hs);
        
        if(btn.isEnabled()) {
            // Efecto Hover manual (opcional, para que se vea bonito al pasar el mouse)
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (!btn.getBackground().equals(COLOR_OCUPADO)) {
                        btn.setBorder(BorderFactory.createLineBorder(COLOR_GRADIENT_2, 2)); // Resaltar borde
                    }
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220))); // Volver a borde normal
                }
            });
            
            btn.addActionListener(e -> toggle(btn));
        }
        
        if (this.esSoloLectura) {
            btn.setEnabled(false); 
            // Truco: Al deshabilitar, Java oscurece el color. 
            // Si quieres mantener el color vivo, usa un JLabel en vez de JButton o UIManager, 
            // pero por ahora setEnabled(false) es suficiente para bloquear.
        }
        return btn;
    }

    private void toggle(JButton btn) {
        HorarioSeleccionado hs = (HorarioSeleccionado) btn.getClientProperty("hs");
        if (hs.estado.equalsIgnoreCase("LIBRE")) {
            hs.estado = "DISPONIBLE";
            btn.setText("Disponible");
            btn.setBackground(COLOR_DISPONIBLE);
            btn.setForeground(Color.WHITE);
            if (!horariosSeleccionados.contains(hs)) horariosSeleccionados.add(hs);
        } else if (hs.estado.equalsIgnoreCase("DISPONIBLE")) {
            hs.estado = "LIBRE";
            btn.setText("Libre");
            btn.setBackground(COLOR_LIBRE);
            btn.setForeground(Color.BLACK);
            horariosSeleccionados.remove(hs);
        }
        actualizarContador();
    }

    private void guardarDisponibilidad() {
        if(horariosSeleccionados.isEmpty()) { 
            JOptionPane.showMessageDialog(this, "No hay cambios."); return; 
        }
        
        List<Agenda_Horarios> lista = new ArrayList<>();
        for(HorarioSeleccionado hs : horariosSeleccionados) {
            Agenda_Horarios a = new Agenda_Horarios();
            a.setDia(hs.fecha);
            a.setHoraInicio(hs.hora);
            a.setHoraFin(hs.hora.plusMinutes(MINUTOS_BLOQUE));
            a.setEstado(hs.estado); // Debería ser "DISPONIBLE"
            lista.add(a);
        }
        
        String res = controlador.guardarDisponibilidad(lista);
        if(res.startsWith("ERROR") || res.contains("conflicto")) {
             JOptionPane.showMessageDialog(this, res, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
             JOptionPane.showMessageDialog(this, "Guardado exitoso.");
             cargarHorariosExistentes();
        }
    }

    private void addHeader(String t, boolean bg) {
        JLabel l = new JLabel("<html><center>"+t+"</center></html>");
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(bg ? Color.WHITE : COLOR_GRADIENT_2);
        l.setForeground(bg ? Color.BLACK : Color.WHITE);
        gridPanel.add(l);
    }
    
    private void actualizarContador() {
        lblContadorSeleccionados.setText("Modificados: " + horariosSeleccionados.size());
    }

    private JPanel crearItemLeyenda(String t, Color c) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.setBackground(Color.WHITE);
        JPanel b = new JPanel(); b.setPreferredSize(new Dimension(15,15)); b.setBackground(c);
        p.add(b); p.add(new JLabel(t));
        return p;
    }

    private static class HorarioSeleccionado {
        LocalDate fecha; LocalTime hora; String estado;
        HorarioSeleccionado(LocalDate f, LocalTime h, String e) {
            this.fecha=f; this.hora=h; this.estado=e;
        }
        @Override public boolean equals(Object o) {
            if(this==o)return true; if(o==null||getClass()!=o.getClass())return false;
            HorarioSeleccionado that=(HorarioSeleccionado)o;
            return fecha.equals(that.fecha) && hora.equals(that.hora);
        }
    }

    class GradientPanel extends JPanel {
        private Color c1, c2;
        public GradientPanel(Color c1, Color c2) { this.c1=c1; this.c2=c2; }
        protected void paintComponent(Graphics g) {
            Graphics2D g2=(Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            GradientPaint gp=new GradientPaint(0,0,c1,getWidth(),getHeight(),c2);
            g2.setPaint(gp); g2.fillRect(0,0,getWidth(),getHeight());
        }
    }
    
        public void activarModoSoloLectura() {
        // Cambiar título
        setTitle("Consultar Disponibilidad (Solo Lectura)");

        // Ocultar botones de edición
        btnGuardar.setVisible(false);
        // Deshabilitar interacción en el grid
        // (Esto se hace recargando, ya que la lógica de crearBoton checa este flag)
        this.esSoloLectura = true; 
        cargarHorariosExistentes();
    }
}