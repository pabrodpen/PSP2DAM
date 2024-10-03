package tema1;
import java.io.*;

public class Actividad1_5 {
    public static void main(String[] args) throws IOException {
        //Ruta del archivo
        File directorio = new File("/home/pablo/Escritorio/PSP2DAM/out/production/PSP2DAM/");

        //Declaramos el ProcessBuilder y ponemos una clase que no exista
        ProcessBuilder pb = new ProcessBuilder("/home/pablo/.jdks/openjdk-23/bin/java", "tema1/Hola");

        //directorio donde esta el ejectutable
        pb.directory(directorio);
        System.out.printf("Directorio de trabajo: %s%n", pb.directory());

        //Iniciamos el proceso
        Process p = pb.start();



        //Capturamos la salida del proceso normal
        try {
            InputStream is = p.getInputStream();
            int exito;
            while ((exito = is.read()) != -1) {
                System.out.print((char) exito);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //capturamos la salida del proceso en el caso de que haya error
        InputStream es = p.getErrorStream();
        System.out.println("\nSalida de error del proceso:");
        int error;
        while ((error = es.read()) != -1) {
            System.out.print((char) error);
        }
    }
}