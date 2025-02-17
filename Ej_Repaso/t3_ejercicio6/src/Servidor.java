import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Servidor {
    private static final int PUERTO = 3009;
    private static final String[][] tablero = new String[3][4];
    private static final Set<String> premiosDisponibles = new HashSet<>();

    public static void main(String[] args) {
        inicializarTablero();
        System.out.println("Servidor iniciado...");
        mostrarPremios();
        ExecutorService pool = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            int idCliente = 1;

            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente conectado => " + idCliente);
                pool.execute(new ManejadorCliente(cliente, idCliente++));
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    private static void inicializarTablero() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                tablero[i][j] = "-";

        // Definiendo premios
        tablero[1][1] = "Crucero";
        tablero[2][3] = "Entradas";
        tablero[3][1] = "Masaje";
        tablero[3][4] = "1000€";

        premiosDisponibles.add("1,1");
        premiosDisponibles.add("2,3");
        premiosDisponibles.add("3,1");
        premiosDisponibles.add("3,4");
    }

    private static void mostrarPremios() {
        System.out.println("Posiciones con premio: " + premiosDisponibles);
    }

    private static class ManejadorCliente implements Runnable {
        private final Socket cliente;
        private final int idCliente;
        private int intentos = 4;
        private int premiosGanados = 0;

        public ManejadorCliente(Socket cliente, int idCliente) {
            this.cliente = cliente;
            this.idCliente = idCliente;
        }

        @Override
        public void run() {
            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                 PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

                salida.println("Bienvenido! Tienes 4 intentos para encontrar premios.");

                while (intentos > 0 && !premiosDisponibles.isEmpty()) {
                    salida.println("Introduce fila (1-3) y columna (1-4) separadas por un espacio:");
                    String mensaje = entrada.readLine();

                    if (mensaje == null || mensaje.equalsIgnoreCase("s")) {
                        salida.println("Saliendo del juego...");
                        break;
                    }

                    String[] partes = mensaje.split(" ");
                    if (partes.length != 2) {
                        salida.println("Formato incorrecto. Usa: Fila Columna");
                        continue;
                    }

                    String posicion = partes[0] + "," + partes[1];

                    if (premiosDisponibles.contains(posicion)) {
                        premiosDisponibles.remove(posicion);
                        premiosGanados++;
                        salida.println("¡Felicidades! Has ganado un premio en [" + posicion + "]");
                    } else {
                        salida.println("Sin premio en [" + posicion + "]");
                    }

                    intentos--;
                    salida.println("Intentos restantes: " + intentos);
                }

                salida.println("Juego terminado. Premios ganados: " + premiosGanados);
                System.out.println("Cliente cerrado => " + idCliente);
            } catch (IOException e) {
                System.err.println("Error con el cliente: " + e.getMessage());
            } finally {
                try {
                    cliente.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar cliente: " + e.getMessage());
                }
            }
        }
    }
}
