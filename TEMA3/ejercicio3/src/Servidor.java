import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Servidor {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(12345);
        System.out.println("Servidor UDP iniciado...");

        // Base de datos simulada
        Map<String, Alumno> alumnos = new HashMap<>();
        alumnos.put("A001", new Alumno("A001", "Juan Perez", new Curso("C001", "Matematicas"), 85));
        alumnos.put("A002", new Alumno("A002", "Maria Lopez", new Curso("C002", "Fisica"), 90));
        alumnos.put("A003", new Alumno("A003", "Carlos Ruiz", new Curso("C003", "Quimica"), 78));

        byte[] buffer = new byte[1024];

        while (true) {
            // Recibir petición
            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
            socket.receive(peticion);

            String idalumno = new String(peticion.getData(), 0, peticion.getLength());
            System.out.println("Consulta recibida para ID: " + idalumno);

            // Crear respuesta
            Alumno alumno = alumnos.getOrDefault(idalumno, new Alumno(idalumno, "No existe", null, 0));

            // Serializar objeto
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(alumno);
            byte[] respuestaBytes = baos.toByteArray();

            // Enviar respuesta
            DatagramPacket respuesta = new DatagramPacket(
                    respuestaBytes,
                    respuestaBytes.length,
                    peticion.getAddress(),
                    peticion.getPort()
            );
            socket.send(respuesta);
        }
    }
}