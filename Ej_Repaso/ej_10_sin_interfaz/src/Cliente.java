import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String servidor = "localhost"; // Dirección del servidor
        int puerto = 6000; // Puerto del servidor

        try (Socket socket = new Socket(servidor, puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            // Mensaje de bienvenida del servidor
            System.out.println("Servidor: " + entrada.readLine());

            // Juego de adivinanza
            while (true) {
                System.out.print("Introduce un número entre 1 y 25: ");
                String numero = scanner.nextLine();
                salida.println(numero); // Enviar número al servidor

                String respuesta = entrada.readLine();
                System.out.println("Servidor: " + respuesta);

                if (respuesta.contains("¡Correcto!")) {
                    break; // Finaliza el juego cuando se acierta
                }
            }

        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}
