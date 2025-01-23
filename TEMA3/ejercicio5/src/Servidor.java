import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        int puerto = 44444; // Puerto del servidor
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor iniciado en el puerto " + puerto);

        while (true) {
            Socket cliente = servidor.accept(); // Acepta un cliente
            System.out.println("=> Conecta IP " + cliente.getInetAddress() + ", Puerto remoto: " + cliente.getPort());

            // Crea un nuevo hilo para manejar al cliente
            ClienteHandler handler = new ClienteHandler(cliente);
            new Thread(handler).start();
        }
    }
}
