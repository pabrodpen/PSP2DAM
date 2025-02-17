import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost"; // Dirección del servidor
        int puerto = 6000;         // Puerto del servidor

        try (Socket socket = new Socket(host, puerto)) {
            Scanner scanner = new Scanner(System.in);
            String idAlumno="";
            while(true){
                // Pedir al usuario un número
                System.out.print("Introduce un id del alumno(* para finalizar): ");
                idAlumno = scanner.next();
                if(idAlumno.equals("*")){
                    System.out.println("CERRANDO CONEXION");
                    break;
                }
            }

            //como estamos tratando con objetos, usamos ObjectOutPut y ObjectInput
            //en lugar de PrintWriter y Buffer
            // Enviar el número al servidor
            // Enviar el ID al servidor
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
            salida.writeObject(idAlumno);
            salida.flush();

            //recibir Alumno del servidor
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            Alumno alumnoEncontrado = (Alumno) entrada.readObject();
            System.out.println("Alumno: " + alumnoEncontrado.toString());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}