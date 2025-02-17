import java.io.*;
import java.net.*;

public class ManejadorCliente implements Runnable {
    private final Socket cliente;
    private final int idCliente;

    public ManejadorCliente(Socket cliente, int idCliente) {
        this.cliente = cliente;
        this.idCliente = idCliente;
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
             PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

            salida.println("PROGRAMA CLIENTE INICIADO...");
            salida.println("SOY EL CLIENTE: " + idCliente);

            String mensaje;
            while (true) {
                salida.println("Introduce el idProfesor a consultar (o * para salir):");
                mensaje = entrada.readLine();

                if (mensaje == null || mensaje.equals("*")) {
                    salida.println("Cerrando conexión...");
                    break;
                }

                try {
                    int idProfesor = Integer.parseInt(mensaje);
                    if (Servidor.profesores.containsKey(idProfesor)) {
                        salida.println(Servidor.profesores.get(idProfesor));
                    } else {
                        salida.println("Profesor con ID " + idProfesor + " no encontrado.");
                    }
                } catch (NumberFormatException e) {
                    salida.println("ID inválido, introduce un número.");
                }
            }
            System.out.println("Cliente " + idCliente + " ha finalizado.");
        } catch (IOException e) {
            System.err.println("Error con el cliente: " + e.getMessage());
        } finally {
            try {
                cliente.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar cliente: " + e.getMessage());
            }
        }
    }
}
