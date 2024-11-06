package ej3;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Contador extends Applet implements ActionListener {
    // Variables para contadores y controles de parada
    private HiloContador hiloContador1, hiloContador2;
    private long cont1 = 0, cont2 = 0;
    private boolean parar1 = true, parar2 = true;
    private Font fuente;

    // Botones para ambos contadores
    private Button iniciar1, parar1Button, iniciar2, parar2Button;

    // Clase interna para el hilo del contador
    class HiloContador extends Thread {
        private long contador;
        private boolean parar;

        HiloContador(long contadorInicial) {
            this.contador = contadorInicial;
            this.parar = true;
        }

        public synchronized void detener() {
            parar = true; // Detiene el hilo
        }

        public synchronized void continuar() {
            parar = false;
            notify(); // Notifica al hilo para que continúe
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
                    Thread.sleep(300); // Espera 300 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                contador++; // Incrementa el contador
                repaint(); // Redibuja el applet
            }
        }

        public long getContador() {
            return contador;
        }

        public void setContador(long valor) {
            this.contador = valor;
        }
    }

    @Override
    public void init() {
        setBackground(Color.YELLOW);
        fuente = new Font("Verdana", Font.BOLD, 26);

        // Crear hilos de contadores, sin iniciar aún
        hiloContador1 = new HiloContador(cont1);
        hiloContador2 = new HiloContador(cont2);

        // Iniciar los hilos
        hiloContador1.start();
        hiloContador2.start();

        // Botones de inicio y paro para el contador 1
        iniciar1 = new Button("Iniciar contador 1");
        iniciar1.addActionListener(this);
        add(iniciar1);

        parar1Button = new Button("Parar contador 1");
        parar1Button.addActionListener(this);
        add(parar1Button);

        // Botones de inicio y paro para el contador 2
        iniciar2 = new Button("Iniciar contador 2");
        iniciar2.addActionListener(this);
        add(iniciar2);

        parar2Button = new Button("Parar contador 2");
        parar2Button.addActionListener(this);
        add(parar2Button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == iniciar1) {
            if (parar1) {
                hiloContador1.continuar(); // Continúa el contador 1
                parar1 = false;
            }
        } else if (e.getSource() == parar1Button) {
            hiloContador1.detener(); // Detiene el contador 1
            parar1 = true;
            cont1 = hiloContador1.getContador(); // Guarda el valor actual
        } else if (e.getSource() == iniciar2) {
            if (parar2) {
                hiloContador2.continuar(); // Continúa el contador 2
                parar2 = false;
            }
        } else if (e.getSource() == parar2Button) {
            hiloContador2.detener(); // Detiene el contador 2
            parar2 = true;
            cont2 = hiloContador2.getContador(); // Guarda el valor actual
        }
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, 400, 400);
        g.setFont(fuente);
        g.drawString("Contador 1: " + hiloContador1.getContador(), 80, 100);
        g.drawString("Contador 2: " + hiloContador2.getContador(), 80, 150);
    }
}
