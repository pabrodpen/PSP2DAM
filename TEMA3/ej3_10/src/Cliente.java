import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Cliente {
    public static void main(String[] args) throws IOException {
        int puerto = 12345;
        InetAddress grupo = InetAddress.getByName("225.0.0.1");
        MulticastSocket socket = new MulticastSocket(puerto);

        socket.joinGroup(grupo);
        System.out.println("Cliente unido al grupo multicast.");

        byte[] buffer = new byte[1024];
        String mensaje = "";

        while (!mensaje.equals("*")) {
            DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
            socket.receive(paquete);

            mensaje = new String(paquete.getData(), 0, paquete.getLength());
            System.out.println("Mensaje recibido: " + mensaje);
        }

        socket.leaveGroup(grupo);
        socket.close();
        System.out.println("Socket cerrado.");
    }
}
