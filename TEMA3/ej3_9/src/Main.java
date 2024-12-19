import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        int puerto = 12345;
        InetAddress grupo = InetAddress.getByName("225.0.0.1"); // Dirección IP de clase D
        MulticastSocket socket = new MulticastSocket();

        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String mensaje = "";

        while (!mensaje.equals("*")) {
            System.out.print("Introduce mensaje para el grupo: ");
            mensaje = entrada.readLine();

            byte[] buffer = mensaje.getBytes();
            DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, grupo, puerto);

            socket.send(paquete);
            System.out.println("Mensaje enviado al grupo.");
        }

        socket.close();
        System.out.println("Socket cerrado.");
    }
}
