package ejercicio_complementario_tarea2_4.src;

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
