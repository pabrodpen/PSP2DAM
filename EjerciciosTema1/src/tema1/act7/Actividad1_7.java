package tema1.act7;

import java.io.*;

public class Actividad1_7 {
    public static void main(String[] args) {
        // Cambiamos la ruta de los archivos a ruta absoluta
        File fIn = new File("/home/pablo/Escritorio/PSP2DAM/EjerciciosTema1/src/tema1/act7/entrada.txt");   // Ruta absoluta para el archivo de entrada
        File fOut = new File("/home/pablo/Escritorio/PSP2DAM/EjerciciosTema1/src/tema1/act7/salida.txt");   // Ruta absoluta para el archivo de salida
        File fErr = new File("/home/pablo/Escritorio/PSP2DAM/EjerciciosTema1/src/tema1/act7/error.txt");    // Ruta absoluta para el archivo de errores



        // Proceso que ejecuta la clase actual
        ProcessBuilder pb = new ProcessBuilder("/home/pablo/.jdks/openjdk-23/bin/java", "-cp", "/home/pablo/Escritorio/PSP2DAM/EjerciciosTema1/src", "tema1.act7.Actividad1_7");

        // Redirigimos la entrada, salida y errores
        pb.redirectInput(fIn);
        pb.redirectOutput(fOut);
        pb.redirectError(fErr);

        // Iniciamos el proceso
        try {
            Process process = pb.start();

            // Esperamos a que el proceso termine
            int exitCode = process.waitFor();
            System.out.println("El proceso terminó con el código: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Procesar entrada y escribir en salida.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(fIn));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fOut))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                writer.write(linea);
                writer.newLine(); // Escribimos una nueva línea
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
