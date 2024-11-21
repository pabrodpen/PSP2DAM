public class Jugador extends Thread {
    private final int id;
    private final Arbitro arbitro;

    // Constructor
    public Jugador(int id, Arbitro arbitro) {
        this.id = id;
        this.arbitro = arbitro;
    }

    // Método principal que ejecuta el jugador
    @Override
    public void run() {
        while (!arbitro.juegoTerminado()) {
            // Espera hasta su turno
            synchronized (arbitro) {
                while (arbitro.obtenerTurno() != id) {
                    try {
                        arbitro.wait();  // Espera a que sea su turno
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Genera un número aleatorio entre 1 y 10
            int jugada = 1 + (int) (Math.random() * 10);
            arbitro.comprobarJugada(id, jugada);
        }
    }
}
