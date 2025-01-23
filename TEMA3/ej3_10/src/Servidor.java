import java.io.*;
import java.net.*;
import java.util.Random;

public class Servidor {
    public static void main(String[] args) throws IOException {
        int puerto = 6000; // Puerto del servidor
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor iniciado en el puerto " + puerto);

        while (true) {
            Socket cliente = servidor.accept();
            System.out.println("Nuevo cliente conectado desde " + cliente.getInetAddress() + ":" + cliente.getPort());

            // Crear un hilo para atender a cada cliente
            new Thread(() -> {
                try (BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                     PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

                    Random rand = new Random();
                    int numeroSecreto = rand.nextInt(25) + 1; // Número entre 1 y 25
                    int intentos = 0;
                    System.out.println("Número secreto generado: " + numeroSecreto);

                    salida.println("¡Bienvenido! Adivina un número entre 1 y 25.");

                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {
                        intentos++;
                        int intento;
                        try {
                            intento = Integer.parseInt(mensaje.trim());
                        } catch (NumberFormatException e) {
                            salida.println("Por favor, ingresa un número válido.");
                            continue;
                        }

                        if (intento == numeroSecreto) {
                            salida.println("¡Correcto! Has adivinado el número en " + intentos + " intentos.");
                            System.out.println("Cliente adivinó correctamente: " + cliente.getInetAddress());
                            break;
                        } else if (intento < numeroSecreto) {
                            salida.println("El número es muy pequeño. Intenta de nuevo.");
                        } else {
                            salida.println("El número es muy grande. Intenta de nuevo.");
                        }
                    }
                    cliente.close();
                    System.out.println("Cliente desconectado.");
                } catch (IOException e) {
                    System.err.println("Error al atender cliente: " + e.getMessage());
                }
            }).start();
        }
    }
}
