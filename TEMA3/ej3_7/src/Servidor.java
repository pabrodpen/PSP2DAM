import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int numeroPuerto = 6000;

        try (ServerSocket servidor = new ServerSocket(numeroPuerto)) {
            System.out.println("Esperando al cliente...");
            Socket cliente = servidor.accept();

            // Flujo de entrada para recibir el objeto del cliente
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            Numeros numero = (Numeros) entrada.readObject();
            System.out.println("Número recibido: " + numero.getNumero());

            // Calcular cuadrado y cubo del número recibido
            numero = new Numeros(numero.getNumero());

            // Flujo de salida para enviar el objeto modificado al cliente
            ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
            salida.writeObject(numero);
            System.out.println("Enviando objeto con cuadrado y cubo...");

            // Cerrar flujos y socket
            salida.close();
            entrada.close();
            cliente.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
