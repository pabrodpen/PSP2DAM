import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {
        int puerto = 6002;
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor de calculadora iniciado en el puerto " + puerto);

        while (true) {
            Socket cliente = servidor.accept();
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

            String operacion = entrada.readLine();
            String[] partes = operacion.split(",");
            String tipoOperacion = partes[0];
            int num1 = Integer.parseInt(partes[1]);
            int num2 = Integer.parseInt(partes[2]);
            int resultado = 0;

            switch (tipoOperacion) {
                case "SUMA":
                    resultado = num1 + num2;
                    break;
                case "RESTA":
                    resultado = num1 - num2;
                    break;
                case "MULTIPLICACION":
                    resultado = num1 * num2;
                    break;
                case "DIVISION":
                    if (num2 != 0) {
                        resultado = num1 / num2;
                    } else {
                        salida.println("Error: División entre cero.");
                        cliente.close();
                        continue;
                    }
                    break;
                default:
                    salida.println("Operación no válida.");
                    cliente.close();
                    continue;
            }

            salida.println("Resultado: " + resultado);
            cliente.close();
        }
    }
}
