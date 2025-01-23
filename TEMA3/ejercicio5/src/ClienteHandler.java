import java.io.*;
import java.net.*;

public class ClienteHandler implements Runnable {
    private Socket cliente;

    public ClienteHandler(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
             PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

            String mensaje;

            // Bucle para manejar mensajes del cliente
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("Mensaje recibido: " + mensaje);

                if (mensaje.equals("*")) {
                    salida.println("Desconectando...");
                    break;
                }

                // Convierte el mensaje a mayúsculas y lo envía de vuelta al cliente
                String respuesta = mensaje.toUpperCase();
                salida.println("Servidor: " + respuesta);
            }

        } catch (IOException e) {
            System.err.println("Error al manejar el cliente: " + e.getMessage());
        } finally {
            try {
                System.out.println("=> Desconecta IP " + cliente.getInetAddress() + ", Puerto remoto: " + cliente.getPort());
                cliente.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
