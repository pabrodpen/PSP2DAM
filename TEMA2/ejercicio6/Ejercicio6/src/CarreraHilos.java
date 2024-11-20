import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarreraHilos extends JFrame {
    private JProgressBar barra1, barra2, barra3;
    private JSlider slider1, slider2, slider3;
    private JButton comenzarBtn;
    private JLabel ganadorLabel;

    public CarreraHilos() {
        setTitle("Carrera de Hilos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        // Barra de progreso y slider para hilo 1
        barra1 = new JProgressBar(0, 100);
        slider1 = new JSlider(1, 10, 5);
        add(createPanel("Hilo 1", barra1, slider1));

        // Barra de progreso y slider para hilo 2
        barra2 = new JProgressBar(0, 100);
        slider2 = new JSlider(1, 10, 5);
        add(createPanel("Hilo 2", barra2, slider2));

        // Barra de progreso y slider para hilo 3
        barra3 = new JProgressBar(0, 100);
        slider3 = new JSlider(1, 10, 5);
        add(createPanel("Hilo 3", barra3, slider3));

        // Botón para comenzar la carrera
        comenzarBtn = new JButton("Comenzar Carrera");
        add(comenzarBtn);

        // Etiqueta para mostrar el ganador
        ganadorLabel = new JLabel("Ganador: ", SwingConstants.CENTER);
        add(ganadorLabel);

        // Acción del botón
        comenzarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarCarrera();
            }
        });
    }

    private JPanel createPanel(String nombre, JProgressBar barra, JSlider slider) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(nombre), BorderLayout.WEST);
        panel.add(barra, BorderLayout.CENTER);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        panel.add(slider, BorderLayout.SOUTH);
        return panel;
    }

    private void iniciarCarrera() {
        comenzarBtn.setEnabled(false); // Desactivar botón durante la carrera
        ganadorLabel.setText("Ganador: ");

        // Crear los hilos con las prioridades seleccionadas
        Thread hilo1 = new Thread(new HiloCarrera(barra1, slider1.getValue(), "Hilo 1"));
        Thread hilo2 = new Thread(new HiloCarrera(barra2, slider2.getValue(), "Hilo 2"));
        Thread hilo3 = new Thread(new HiloCarrera(barra3, slider3.getValue(), "Hilo 3"));

        hilo1.setPriority(slider1.getValue());
        hilo2.setPriority(slider2.getValue());
        hilo3.setPriority(slider3.getValue());

        hilo1.start();
        hilo2.start();
        hilo3.start();
    }

    public void actualizarGanador(String nombreGanador) {
        ganadorLabel.setText("Ganador: " + nombreGanador);
        comenzarBtn.setEnabled(true); // Reactivar el botón al finalizar la carrera
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CarreraHilos().setVisible(true);
        });
    }

    // Clase interna para manejar cada hilo
    private class HiloCarrera implements Runnable {
        private JProgressBar barra;
        private int prioridad;
        private String nombre;

        public HiloCarrera(JProgressBar barra, int prioridad, String nombre) {
            this.barra = barra;
            this.prioridad = prioridad;
            this.nombre = nombre;
        }

        @Override
        public void run() {
            int progreso = 0;
            while (progreso < 100) {
                try {
                    Thread.sleep((long) (Math.random() * 100 + 1)); // Simula el tiempo de espera
                    progreso += 1; // Incrementa el progreso
                    barra.setValue(progreso);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            verificarGanador(nombre);
        }

        private synchronized void verificarGanador(String nombre) {
            if (ganadorLabel.getText().equals("Ganador: ")) { // Solo el primer hilo que termine actualiza
                actualizarGanador(nombre);
            }
        }
    }
}
