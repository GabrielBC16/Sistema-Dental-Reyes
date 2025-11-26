package app.dentalreyes.main;

import app.dentalreyes.seguridad.vistas.IU_AutenticarUsuario;

import javax.swing.UIManager;
import app.dentalreyes.core.ConexionMYSQL;

public class main {
    public static void main(String[] args) {
      // Opcional: Poner estilo "Windows" para que se vea nativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Iniciar la aplicación en el hilo gráfico de Java (EDT)
        java.awt.EventQueue.invokeLater(() -> {
            IU_AutenticarUsuario login = new IU_AutenticarUsuario();
            
            // centrar en pantalla
            login.setLocationRelativeTo(null);
            
            // mostrar
            login.setVisible(true);
        });
    }
        
    }