import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Servidor39 {
    private static final int PUERTO = 3009;

    public static void main(String[] args) {
        System.out.println("Servidor iniciado...");
        ExecutorService pool = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("=>Conecta IP " + cliente.getInetAddress() + ", Puerto remoto: " + cliente.getPort());
                pool.execute(new ManejadorCliente(cliente));
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    private static class ManejadorCliente implements Runnable {
        private Socket cliente;

        public ManejadorCliente(Socket cliente) {
            this.cliente = cliente;
        }

        @Override
        public void run() {
            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                 PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

                String mensaje;
                while ((mensaje = entrada.readLine()) != null) {
                    if (mensaje.equals("*")) {
                        System.out.println("=>Desconecta IP " + cliente.getInetAddress() + ", Puerto remoto: " + cliente.getPort());
                        break;
                    }
                    System.out.println("Mensaje recibido: " + mensaje);
                    salida.println(mensaje.toUpperCase());
                }
            } catch (IOException e) {
                System.err.println("Error al manejar cliente: " + e.getMessage());
            } finally {
                try {
                    cliente.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el cliente: " + e.getMessage());
                }
            }
        }
    }
}
