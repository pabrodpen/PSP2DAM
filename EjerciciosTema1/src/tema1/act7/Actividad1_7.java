package tema1.act7;

import java.io.*;

public class Actividad1_7 {
    public static void main(String[] args) throws IOException {
        // Ruta absoluta para los archivos de entrada y salida
        File fIn = new File("EjerciciosTema1/src/tema1/act7/entrada.txt");
        File fOut = new File("EjerciciosTema1/src/tema1/act7/salida.txt");
        File fErr = new File("EjerciciosTema1/src/tema1/act7/error.txt");

        File ruta =new File("/home/pablo/Escritorio/PSP2DAM/out/production/PSP2DAM/");




        // Proceso que ejecuta la clase Ejemplo5
        ProcessBuilder pb = new ProcessBuilder("/home/pablo/.jdks/openjdk-23/bin/java", "tema1.Ejemplo5");
        pb.directory(ruta);

        // Redirigir la entrada, salida y errores a los archivos correspondientes
        pb.redirectInput(fIn);   // Entrada desde archivo
        pb.redirectOutput(fOut);  // Salida estándar al archivo de salida
        pb.redirectError(fErr);   // Redirección de errores a archivo

        // Iniciar el proceso
        Process process = pb.start();

        try {
            // Esperar a que el proceso termine
            int exito = process.waitFor();
            System.out.println("El proceso terminó con el código: " + exito);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
