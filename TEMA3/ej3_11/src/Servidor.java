import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        int puerto = 6000;
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor iniciado en el puerto " + puerto);

        while (true) {
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado desde: " + cliente.getInetAddress());
            new HiloCliente(cliente).start();
        }
    }
}