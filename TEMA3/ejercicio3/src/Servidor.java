import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {
        int puerto = 6001;
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor de operaciones iniciado en el puerto " + puerto);

        while (true) {
            Socket cliente = servidor.accept();
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

            String linea = entrada.readLine();
            String[] numeros = linea.split(",");
            int num1 = Integer.parseInt(numeros[0]);
            int num2 = Integer.parseInt(numeros[1]);

            int suma = num1 + num2;
            int producto = num1 * num2;

            salida.println("Suma: " + suma);
            salida.println("Producto: " + producto);

            cliente.close();
        }
    }
}