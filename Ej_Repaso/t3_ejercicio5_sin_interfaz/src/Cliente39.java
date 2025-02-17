import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente39 {
    private static final String HOST = "localhost";
    private static final int PUERTO = 3009;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor. Escribe un mensaje y presiona ENTER para enviarlo.");
            System.out.println("Comandos disponibles: ");
            System.out.println("   - 'l' para limpiar la pantalla.");
            System.out.println("   - 's' para salir.");

            while (true) {
                System.out.print("> ");
                String mensaje = scanner.nextLine();

                if (mensaje.equalsIgnoreCase("s")) {
                    salida.println("*");
                    System.out.println("Desconectando...");
                    break;
                } else if (mensaje.equalsIgnoreCase("l")) {
                    limpiarPantalla();
                    continue;
                }

                salida.println(mensaje);
                String respuesta = entrada.readLine();
                if (respuesta != null) {
                    System.out.println("Servidor: " + respuesta);
                }
            }
        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }

    private static void limpiarPantalla() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ex) {
            System.out.println("No se pudo limpiar la pantalla.");
        }
    }
}
