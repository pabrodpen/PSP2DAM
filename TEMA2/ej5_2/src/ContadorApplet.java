import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContadorApplet extends Applet implements ActionListener {
    private Hilo hilo1;
    private Hilo hilo2;
    private Font fuente;

    // Botones y etiquetas
    private Button parar1Button, parar2Button, finalizarProcesoButton, comenzarProcesoButton;
    private Label contador1Label, contador2Label, nombreHilo1Label, nombreHilo2Label;

    @Override
    public void init() {
        setLayout(null);
        setBackground(Color.YELLOW);
        fuente = new Font("Verdana", Font.BOLD, 26);

        // Crear hilos de contadores
        hilo1 = new Hilo("H1", 2, this); // Pasar referencia del applet
        hilo2 = new Hilo("H2", 1, this); // Pasar referencia del applet

        // Botón "Comenzar Proceso"
        comenzarProcesoButton = new Button("Comenzar proceso");
        comenzarProcesoButton.setBounds(120, 20, 150, 30);
        comenzarProcesoButton.addActionListener(this);
        add(comenzarProcesoButton);

        // Botones "Interrumpir" para cada hilo
        parar1Button = new Button("Interrumpir");
        parar1Button.setBounds(50, 70, 150, 30);
        parar1Button.addActionListener(this);
        add(parar1Button);

        parar2Button = new Button("Interrumpir");
        parar2Button.setBounds(210, 70, 150, 30);
        parar2Button.addActionListener(this);
        add(parar2Button);

        // Etiquetas para mostrar los contadores
        contador1Label = new Label("0");
        contador1Label.setBounds(50, 120, 150, 30);
        contador1Label.setFont(fuente);
        add(contador1Label);

        contador2Label = new Label("0");
        contador2Label.setBounds(210, 120, 150, 30);
        contador2Label.setFont(fuente);
        add(contador2Label);

        // Etiquetas para mostrar los nombres de los hilos
        nombreHilo1Label = new Label(hilo1.nombreHilo);
        nombreHilo1Label.setBounds(50, 160, 150, 30);
        nombreHilo1Label.setFont(fuente);
        add(nombreHilo1Label);

        nombreHilo2Label = new Label(hilo2.nombreHilo);
        nombreHilo2Label.setBounds(210, 160, 150, 30);
        nombreHilo2Label.setFont(fuente);
        add(nombreHilo2Label);

        // Botón "Finalizar Proceso"
        finalizarProcesoButton = new Button("Finalizar proceso");
        finalizarProcesoButton.setBounds(120, 200, 150, 30);
        finalizarProcesoButton.addActionListener(this);
        add(finalizarProcesoButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == parar1Button) {
            hilo1.detener(); // Detiene el contador 1
            nombreHilo1Label.setText(hilo1.nombreHilo+" interr.");
        } else if (e.getSource() == parar2Button) {
            hilo2.detener(); // Detiene el contador 2
            nombreHilo2Label.setText(hilo2.nombreHilo+" interr.");
        } else if (e.getSource() == finalizarProcesoButton) {
            hilo1.detener();
            hilo2.detener();
            nombreHilo1Label.setText(hilo1.nombreHilo+" final.");
            nombreHilo2Label.setText(hilo2.nombreHilo+" final.");
            System.out.println("Valor final "+hilo1.nombreHilo+":"+hilo1.getContador());
            System.out.println("Valor final "+hilo2.nombreHilo+":"+hilo2.getContador());


            parar1Button.setEnabled(false);
            parar2Button.setEnabled(false);
            comenzarProcesoButton.setEnabled(false);
        } else if (e.getSource() == comenzarProcesoButton) {
            hilo1.start();
            hilo2.start();
            nombreHilo1Label.setText(hilo1.nombreHilo+" corr.");
            nombreHilo2Label.setText(hilo2.nombreHilo+" corr.");
        }
    }

    // Método para actualizar los contadores
    public void actualizarContadores() {
        contador1Label.setText("" + hilo1.getContador());
        contador2Label.setText("" + hilo2.getContador());
        repaint(); // Redibuja el applet para reflejar cambios
    }
}

class Hilo extends Thread {
    private long contador;
    private boolean parar;
    int segundosDormido;
    public String nombreHilo;
    private ContadorApplet applet; // Referencia al applet

    public Hilo(String nombreHilo, int segundosDormido, ContadorApplet applet) {
        this.nombreHilo = nombreHilo;
        this.segundosDormido = segundosDormido;
        this.applet = applet; // Guardar referencia del applet
    }

    public synchronized void detener() {
        parar = true;
        notifyAll();
    }

    public long getContador() {
        return contador;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (parar) {
                    try {
                        wait(); // Pausa el hilo hasta que se llame a continuar
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Thread.sleep(segundosDormido * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            contador++;
            applet.actualizarContadores(); // Actualiza los contadores en el applet
        }
    }
}
