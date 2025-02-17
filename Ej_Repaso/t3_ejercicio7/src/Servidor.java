import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Servidor {
    private static final int PUERTO = 3009;
    private static final int LONGITUD_CODIGO = 4;
    private static int[] combinacionSecreta = new int[LONGITUD_CODIGO];
    private static boolean juegoTerminado = false;
    private static int ganadorID = -1;

    public static void main(String[] args) {
        generarCombinacionSecreta();
        System.out.println("Servidor iniciado...");
        System.out.println("Combinación de Números: " + Arrays.toString(combinacionSecreta));

        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            int idJugador = 1;

            while (!juegoTerminado) {
                Socket cliente = serverSocket.accept();
                System.out.println("Jugador " + idJugador + " conectado.");
                pool.execute(new ManejadorCliente(cliente, idJugador++));
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    private static void generarCombinacionSecreta() {
        Random rand = new Random();
        Set<Integer> numerosUnicos = new HashSet<>();
        while (numerosUnicos.size() < LONGITUD_CODIGO) {
            numerosUnicos.add(rand.nextInt(10)); // Números del 0 al 9
        }
        int i = 0;
        for (int num : numerosUnicos) {
            combinacionSecreta[i++] = num;
        }
    }

    private static class ManejadorCliente implements Runnable {
        private final Socket cliente;
        private final int idJugador;
        private int intentos = 10;

        public ManejadorCliente(Socket cliente, int idJugador) {
            this.cliente = cliente;
            this.idJugador = idJugador;
        }

        @Override
        public void run() {
            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                 PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

                salida.println("Bienvenido, Jugador " + idJugador + ". Tienes 10 intentos para adivinar la combinación.");
                salida.println("Introduce 4 números (0-9) separados por espacios:");

                while (intentos > 0 && !juegoTerminado) {
                    String mensaje = entrada.readLine();

                    if (mensaje == null || mensaje.equalsIgnoreCase("s")) {
                        salida.println("Saliendo del juego...");
                        break;
                    }

                    String[] partes = mensaje.split(" ");
                    if (partes.length != LONGITUD_CODIGO) {
                        salida.println("Formato incorrecto. Introduce exactamente 4 números separados por espacios.");
                        continue;
                    }

                    int[] intento = new int[LONGITUD_CODIGO];
                    Set<Integer> numRepetidos = new HashSet<>();
                    boolean repetido = false;

                    try {
                        for (int i = 0; i < LONGITUD_CODIGO; i++) {
                            intento[i] = Integer.parseInt(partes[i]);
                            if (!numRepetidos.add(intento[i])) {
                                repetido = true;
                            }
                        }
                    } catch (NumberFormatException e) {
                        salida.println("Entrada inválida. Solo se permiten números del 0 al 9.");
                        continue;
                    }

                    if (repetido) {
                        salida.println("Los números no pueden repetirse. Inténtalo de nuevo.");
                        continue;
                    }

                    int aciertos = 0;
                    int coincidencias = 0;
                    boolean[] usadoSecreto = new boolean[LONGITUD_CODIGO];
                    boolean[] usadoIntento = new boolean[LONGITUD_CODIGO];

                    // Contar aciertos (posición correcta)
                    for (int i = 0; i < LONGITUD_CODIGO; i++) {
                        if (intento[i] == combinacionSecreta[i]) {
                            aciertos++;
                            usadoSecreto[i] = true;
                            usadoIntento[i] = true;
                        }
                    }

                    // Contar coincidencias (número correcto en posición incorrecta)
                    for (int i = 0; i < LONGITUD_CODIGO; i++) {
                        if (!usadoIntento[i]) {
                            for (int j = 0; j < LONGITUD_CODIGO; j++) {
                                if (!usadoSecreto[j] && intento[i] == combinacionSecreta[j]) {
                                    coincidencias++;
                                    usadoSecreto[j] = true;
                                    break;
                                }
                            }
                        }
                    }

                    intentos--;

                    if (aciertos == LONGITUD_CODIGO) {
                        juegoTerminado = true;
                        ganadorID = idJugador;
                        salida.println("¡Felicidades, has adivinado la combinación! ID Ganador: " + idJugador);
                        break;
                    } else {
                        salida.println("Aciertos: " + aciertos + " | Coincidencias: " + coincidencias);
                        salida.println("Intentos restantes: " + intentos);
                    }
                }

                if (!juegoTerminado && intentos == 0) {
                    salida.println("Te quedaste sin intentos. Fin del juego.");
                }

                System.out.println("=>Desconectando a ID Jugador: " + idJugador);
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
