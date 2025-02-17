import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Servidor {
    public static final int PUERTO = 4444;
    public static Map<Integer, Profesor> profesores = new HashMap<>();
    public static int idCliente = 1;

    public static void main(String[] args) {
        // Inicializar profesores
        Especialidad espInformatica = new Especialidad(1, "INFORMÁTICA");
        Especialidad espMatematicas = new Especialidad(2, "MATEMÁTICAS");

        profesores.put(1, new Profesor(1, "Jesús Espejo",
                new Asignatura[]{new Asignatura(2, "ADAT"), new Asignatura(3, "PSP")},
                espInformatica));

        profesores.put(2, new Profesor(2, "María López",
                new Asignatura[]{new Asignatura(5, "Álgebra"), new Asignatura(6, "Cálculo")},
                espMatematicas));

        // Iniciar servidor
        System.out.println("Servidor iniciado...");
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente " + idCliente + " conectado");
                new Thread(new ManejadorCliente(cliente, idCliente)).start();
                idCliente++;
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
