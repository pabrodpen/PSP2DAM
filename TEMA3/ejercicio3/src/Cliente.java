import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress servidor = InetAddress.getByName("localhost");
        int puerto = 12345;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Introduce el ID del alumno (o * para salir): ");
            String idalumno = scanner.nextLine();

            if (idalumno.equals("*")) {
                System.out.println("Saliendo del cliente...");
                break;
            }

            // Enviar petición
            byte[] bufferEnvio = idalumno.getBytes();
            DatagramPacket peticion = new DatagramPacket(bufferEnvio, bufferEnvio.length, servidor, puerto);
            socket.send(peticion);

            // Recibir respuesta
            byte[] bufferRespuesta = new byte[1024];
            DatagramPacket respuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
            socket.receive(respuesta);

            // Deserializar objeto
            ByteArrayInputStream bais = new ByteArrayInputStream(respuesta.getData(), 0, respuesta.getLength());
            ObjectInputStream ois = new ObjectInputStream(bais);
            Alumno alumno = (Alumno) ois.readObject();

            // Mostrar datos
            System.out.println("Datos recibidos: " + alumno);
        }

        socket.close();
    }
}
