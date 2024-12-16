import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int puerto = 12345; // Puerto de escucha del servidor
        DatagramSocket socket = new DatagramSocket(puerto);
        System.out.println("Servidor UDP esperando datagramas...");

        // Buffer para recibir el objeto del cliente
        byte[] bufferEntrada = new byte[1024];
        DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
        socket.receive(paqueteEntrada); // Espera a recibir un datagrama
        System.out.println("Datagrama recibido del cliente.");

        // Convertimos los bytes a objeto Persona
        ByteArrayInputStream bais = new ByteArrayInputStream(paqueteEntrada.getData());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Persona persona = (Persona) ois.readObject();
        System.out.println("Datos recibidos: Nombre = " + persona.getNombre() + ", Edad = " + persona.getEdad());

        // Modificamos los datos del objeto
        persona.setNombre("ServidorModificado");
        persona.setEdad(persona.getEdad() + 10);

        // Convertimos el objeto modificado a bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(persona);

        // Enviamos el objeto modificado de vuelta al cliente
        byte[] bufferSalida = baos.toByteArray();
        InetAddress direccionCliente = paqueteEntrada.getAddress();
        int puertoCliente = paqueteEntrada.getPort();
        DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionCliente, puertoCliente);
        socket.send(paqueteSalida);
        System.out.println("Objeto modificado enviado al cliente.");

        // Cerramos los recursos
        socket.close();
        oos.close();
        ois.close();
    }
}
