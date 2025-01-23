import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String Host = "localhost";
        int Puerto = 6000;

        Scanner scanner=new Scanner(System.in);
        try (Socket cliente = new Socket(Host, Puerto)) {
            System.out.println("PROGRAMA CLIENTE INICIADO");

            // Enviar un objeto Numeros con el número inicial al servidor
            ObjectOutputStream salidaCliente = new ObjectOutputStream(cliente.getOutputStream());
            Numeros numero;
            System.out.print("Dime un numero:");
            int n=scanner.nextInt();
            numero = new Numeros(n);
            salidaCliente.writeObject(numero);
            System.out.println("Número enviado al servidor: " + numero.getNumero());

            // Recibir el objeto modificado desde el servidor
            ObjectInputStream entradaCliente = new ObjectInputStream(cliente.getInputStream());
            Numeros resultado = (Numeros) entradaCliente.readObject();
            System.out.println("CUADRADO DEL NÚMERO: " + resultado.getCuadrado());
            System.out.println("CUBO DEL NÚMERO: " + resultado.getCubo());

            // Cerrar flujos y socket
            entradaCliente.close();
            salidaCliente.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
