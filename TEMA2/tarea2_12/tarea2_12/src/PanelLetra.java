import javax.swing.*;
import java.awt.*;

class PanelLetra extends JPanel implements Runnable {
    private int x = 1; // Posición inicial en X
    private final int y = 100; // Posición fija en Y
    private boolean moviendoDerecha = true; // Dirección inicial
    private boolean pausado = false;

    public synchronized void pausarMovimiento() {
        pausado = true;
    }

    public synchronized void reanudarMovimiento() {
        pausado = false;
        notify();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("A", x, y); // Dibuja la letra
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (pausado) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Cambiar dirección si llega a los bordes
            if (moviendoDerecha) {
                x++;
                if (x + 40 >= getWidth()) { // Rebote en el borde derecho
                    moviendoDerecha = false;
                }
            } else {
                x--;
                if (x <= 1) { // Rebote en el borde izquierdo
                    moviendoDerecha = true;
                }
            }

            repaint(); // Actualiza la pantalla

            try {
                Thread.sleep(10); // Controla la velocidad del movimiento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
