public class Consumidor extends Thread {
    private Cola cola;
    private int id;

    public Consumidor(Cola cola, int id) {
        this.cola = cola;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            String mensaje = cola.get(); // Toma el mensaje de la cola
            System.out.println(mensaje);
        }
    }
}
