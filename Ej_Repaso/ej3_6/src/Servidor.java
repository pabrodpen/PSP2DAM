import java.net.Socket;
import java.util.Scanner;

public class Servidor {
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

    }
}