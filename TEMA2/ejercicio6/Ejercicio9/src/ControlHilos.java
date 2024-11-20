import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlHilos extends JFrame {
    private JButton comenzarBtn, finalizarBtn, detenerHilo1Btn, detenerHilo2Btn;
    private JLabel estadoHilo1Label, estadoHilo2Label;
    private ContadorHilo hilo1, hilo2;

    public ControlHilos() {
        setTitle("Control de Hilos");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        // Botón para comenzar el proceso
        comenzarBtn = new JButton("Comenzar Proceso");
        comenzarBtn.addActionListener(e -> iniciarHilos());
        add(comenzarBtn);

        // Estado del hilo 1
        estadoHilo1Label = new JLabel("Hilo 1: Inactivo", SwingConstants.CENTER);
        detenerHilo1Btn = new JButton("Detener Hilo 1");
        detenerHilo1Btn.setEnabled(false);
        detenerHilo1Btn.addActionListener(e -> detenerHilo(hilo1, estadoHilo1Label, "Hilo 1 detenido"));
        add(createPanel(estadoHilo1Label, detenerHilo1Btn));

        // Estado del hilo 2
        estadoHilo2Label = new JLabel("Hilo 2: Inactivo", SwingConstants.CENTER);
        detenerHilo2Btn = new JButton("Detener Hilo 2");
        detenerHilo2Btn.setEnabled(false);
        detenerHilo2Btn.addActionListener(e -> detenerHilo(hilo2, estadoHilo2Label, "Hilo 2 detenido"));
        add(createPanel(estadoHilo2Label, detenerHilo2Btn));

        // Botón para finalizar el proceso
        finalizarBtn = new JButton("Finalizar Proceso");
        finalizarBtn.setEnabled(false);
        finalizarBtn.addActionListener(e -> finalizarHilos());
        add(finalizarBtn);
    }

    private JPanel createPanel(JLabel label, JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);
        return panel;
    }

    private void iniciarHilos() {
        // Crear y lanzar los hilos
        hilo1 = new ContadorHilo("Hilo 1", 500, estadoHilo1Label);
        hilo2 = new ContadorHilo("Hilo 2", 700, estadoHilo2Label);

        hilo1.start();
        hilo2.start();

        // Actualizar botones y etiquetas
        estadoHilo1Label.setText("Hilo 1: Corriendo");
        estadoHilo2Label.setText("Hilo 2: Corriendo");
        comenzarBtn.setEnabled(false);
        detenerHilo1Btn.setEnabled(true);
        detenerHilo2Btn.setEnabled(true);
        finalizarBtn.setEnabled(true);
    }

    private void detenerHilo(ContadorHilo hilo, JLabel estadoLabel, String mensaje) {
        if (hilo != null && hilo.isAlive()) {
            hilo.interrupt();
            estadoLabel.setText(mensaje);
        }
    }

    private void finalizarHilos() {
        detenerHilo(hilo1, estadoHilo1Label, "Hilo 1 detenido");
        detenerHilo(hilo2, estadoHilo2Label, "Hilo 2 detenido");

        // Mostrar los valores finales de los contadores
        if (hilo1 != null) System.out.println(hilo1.getName() + " finalizó con valor: " + hilo1.getContador());
        if (hilo2 != null) System.out.println(hilo2.getName() + " finalizó con valor: " + hilo2.getContador());

        // Resetear la interfaz
        comenzarBtn.setEnabled(true);
        detenerHilo1Btn.setEnabled(false);
        detenerHilo2Btn.setEnabled(false);
        finalizarBtn.setEnabled(false);
        estadoHilo1Label.setText("Hilo 1: Inactivo");
        estadoHilo2Label.setText("Hilo 2: Inactivo");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ControlHilos().setVisible(true));
    }
}

class ContadorHilo extends Thread {
    private int contador = 0;
    private long sleepTime;
    private JLabel estadoLabel;

    public ContadorHilo(String name, long sleepTime, JLabel estadoLabel) {
        super(name);
        this.sleepTime = sleepTime;
        this.estadoLabel = estadoLabel;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                contador++;
                Thread.sleep(sleepTime);
                SwingUtilities.invokeLater(() -> estadoLabel.setText(getName() + ": Contador = " + contador));
            }
        } catch (InterruptedException e) {
            // El hilo fue interrumpido, terminar la ejecución
        }
    }

    public int getContador() {
        return contador;
    }
}
