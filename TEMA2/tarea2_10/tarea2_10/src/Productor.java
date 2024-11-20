public class Productor extends Thread {
    private Cola cola;

    public Productor(Cola cola) {
        this.cola = cola;
    }

    @Override
    public void run() {
        boolean alternar = true; // Alterna entre PING y PONG
        while (true) {
            String mensaje = alternar ? "PING" : "PONG";
            cola.put(mensaje); // Envía el mensaje a la cola
            System.out.println(mensaje);
            alternar = !alternar; // Cambia el mensaje para la próxima iteración

            try {
                sleep(300); // Simula tiempo de producción
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
