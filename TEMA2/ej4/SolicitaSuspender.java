package ej4;

public class SolicitaSuspender {
    private boolean suspender;

    public synchronized void set(boolean b) {
        suspender = b;
        notifyAll();
    }

    public synchronized void esperandoParaReanudar() throws InterruptedException {
        while (suspender) {
            wait();
        }
    }
}
