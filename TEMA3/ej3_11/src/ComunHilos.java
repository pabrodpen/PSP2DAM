import java.io.*;
import java.net.*;
import java.util.*;

public class ComunHilos implements Runnable {
    private Socket socket;
    private List<ComunHilos> clientes;
    private PrintWriter salida;
    private String nombre;

    public ComunHilos(Socket socket, List<ComunHilos> clientes) {
        this.socket = socket;
        this.clientes = clientes;
    }

    @Override
    public void run() {
        try (
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            salida = new PrintWriter(socket.getOutputStream(), true);

            salida.println("Introduce tu nombre: ");
            nombre = entrada.readLine();
            System.out.println(nombre + " se ha unido al chat.");
            enviarATodos(nombre + " se ha unido al chat.");

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                if (mensaje.equalsIgnoreCase("SALIR")) {
                    break;
                }
                enviarATodos(nombre + ": " + mensaje);
            }
        } catch (IOException e) {
            System.err.println("Error con el cliente: " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    private void enviarATodos(String mensaje) {
        synchronized (clientes) {
            for (ComunHilos cliente : clientes) {
                if (cliente != this) {
                    cliente.salida.println(mensaje);
                }
            }
        }
    }

    private void desconectar() {
        try {
            socket.close();
            synchronized (clientes) {
                clientes.remove(this);
            }
            System.out.println(nombre + " se ha desconectado.");
            enviarATodos(nombre + " se ha desconectado.");
        } catch (IOException e) {
            System.err.println("Error al cerrar el socket: " + e.getMessage());
        }
    }
}
