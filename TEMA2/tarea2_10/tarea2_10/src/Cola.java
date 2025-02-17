package tarea2_10.tarea2_10.src;

public class Cola {
    private String mensaje; // Almacena el mensaje actual
    private boolean disponible = false; // Estado de la cola

    public synchronized String get() {
        while (!disponible) { // Espera hasta que haya un mensaje disponible
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        disponible = false; // Marca la cola como vacía
        notifyAll(); // Notifica al productor
        return mensaje; // Devuelve el mensaje
    }

    public synchronized void put(String nuevoMensaje) {
        while (disponible) { // Espera hasta que la cola esté vacía
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mensaje = nuevoMensaje; // Asigna el nuevo mensaje
        disponible = true; // Marca la cola como llena
        notifyAll(); // Notifica a los consumidores
    }
}
