public class Juego {
    public static void main(String[] args) {
        int numeroDeJugadores = 3;
        Arbitro arbitro = new Arbitro(numeroDeJugadores);

        // Crear y lanzar los hilos para los jugadores
        Jugador jugador1 = new Jugador(1, arbitro);
        Jugador jugador2 = new Jugador(2, arbitro);
        Jugador jugador3 = new Jugador(3, arbitro);

        jugador1.start();
        jugador2.start();
        jugador3.start();
    }
}
