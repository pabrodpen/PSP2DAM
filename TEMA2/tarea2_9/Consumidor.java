package tarea2_9;

public class Consumidor extends Thread {
    private Cola cola;
    private int n;

    public Consumidor(Cola cola, int n) {
        this.cola = cola;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            cola.get(); // recoge el número
        }
    }
}
