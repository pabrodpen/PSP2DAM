public class Arbitro {
    private final int numeroAdivinar;
    private int turno;
    private final int totalJugadores;
    private boolean juegoTerminado = false;

    // Constructor
    public Arbitro(int totalJugadores) {
        this.totalJugadores = totalJugadores;
        this.numeroAdivinar = 1 + (int) (Math.random() * 10);  // Número aleatorio entre 1 y 10
        this.turno = 1; // El primer jugador en comenzar
    }

    // Devuelve el turno actual
    public synchronized int obtenerTurno() {
        return turno;
    }

    // Comprueba la jugada de un jugador
    public synchronized void comprobarJugada(int jugadorId, int jugada) {
        if (juegoTerminado) return;

        System.out.println("Jugador " + jugadorId + " dice: " + jugada);

        // Si adivina el número, el juego termina
        if (jugada == numeroAdivinar) {
            System.out.println("Jugador " + jugadorId + " gana, adivinó el número!!!");
            juegoTerminado = true;
            return;
        }

        // Cambia el turno al siguiente jugador
        turno = (turno % totalJugadores) + 1;
        System.out.println("Le toca a Jugador " + turno);

        notifyAll();  // Notifica a los demás jugadores
    }

    // Método para verificar si el juego ha terminado
    public synchronized boolean juegoTerminado() {
        return juegoTerminado;
    }
}
