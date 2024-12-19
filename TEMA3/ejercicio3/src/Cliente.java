import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int puerto = 6001;

        Socket cliente = new Socket(host, puerto);
        PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
        BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduce dos números separados por coma: ");
        String numeros = teclado.readLine();

        salida.println(numeros);
        System.out.println(entrada.readLine());
        System.out.println(entrada.readLine());

        cliente.close();
    }
}
