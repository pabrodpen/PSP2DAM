package tarea2_9;

public class Cola {
    private int numero;
    private boolean disponible = false; // inicialmente la cola está vacía

    public synchronized int get() {
        while (!disponible) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("=> Consumidor consume: " + numero);
        disponible = false;
        notify();
        return numero;
    }

    public synchronized void put(int valor) {
        while (disponible) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        numero = valor;
        disponible = true;
        System.out.println("=> Productor produce: " + numero);
        notify();
    }
}
