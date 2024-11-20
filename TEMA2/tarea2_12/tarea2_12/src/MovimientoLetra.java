import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovimientoLetra extends JFrame {
    private final PanelLetra panelLetra;
    private final JButton botonControl;
    private boolean corriendo = true;

    public MovimientoLetra() {
        setTitle("Movimiento Horizontal de Letra");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel que muestra la letra
        panelLetra = new PanelLetra();
        add(panelLetra, BorderLayout.CENTER);

        // Botón para pausar/reanudar
        botonControl = new JButton("Finalizar Hilo");
        botonControl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (corriendo) {
                    panelLetra.pausarMovimiento();
                    botonControl.setText("Reanudar Hilo");
                } else {
                    panelLetra.reanudarMovimiento();
                    botonControl.setText("Finalizar Hilo");
                }
                corriendo = !corriendo;
            }
        });

        add(botonControl, BorderLayout.SOUTH);

        // Inicia el movimiento de la letra
        new Thread(panelLetra).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MovimientoLetra ventana = new MovimientoLetra();
            ventana.setVisible(true);
        });
    }
}
