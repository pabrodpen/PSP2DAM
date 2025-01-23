import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String host = "localhost";
        int puerto = 12345;
        DatagramSocket socket = new DatagramSocket();
        System.out.println("Cliente UDP iniciado...");

        Scanner sc = new Scanner(System.in);
        System.out.println("Nombre:");
        String nombre = sc.nextLine();
        System.out.println("Edad:");
        int edad = sc.nextInt();
        // Creamos un objeto Persona
        Persona persona = new Persona(nombre, edad);

        // Convertimos el objeto a bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(persona);
        byte[] bufferSalida = baos.toByteArray();

        // Enviamos el objeto al servidor
        InetAddress direccionServidor = InetAddress.getByName(host);
        DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionServidor, puerto);
        socket.send(paqueteSalida);
        System.out.println("Objeto enviado al servidor: Nombre = " + persona.getNombre() + ", Edad = " + persona.getEdad());

        // Buffer para recibir el objeto modificado desde el servidor
        byte[] bufferEntrada = new byte[1024];
        DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
        socket.receive(paqueteEntrada); // Espera respuesta del servidor
        System.out.println("Datagrama recibido del servidor.");

        // Convertimos los bytes recibidos a objeto Persona
        ByteArrayInputStream bais = new ByteArrayInputStream(paqueteEntrada.getData());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Persona personaModificada = (Persona) ois.readObject();
        System.out.println("Objeto modificado recibido: Nombre = " + personaModificada.getNombre() + ", Edad = " + personaModificada.getEdad());

        // Cerramos los recursos
        socket.close();
        oos.close();
        ois.close();
    }
}
