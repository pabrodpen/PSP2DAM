package tema1.act8;

import java.io.*;

public class Actividad1_8 {
    public static void main(String[] args) {
        // Cambiamos la ruta de los archivos a ruta absoluta
        File fIn = new File("/home/pablo/Escritorio/PSP2DAM/EjerciciosTema1/src/tema1/act8/entrada.txt");   // Ruta absoluta para el archivo de entrada
        File fOut = new File("/home/pablo/Escritorio/PSP2DAM/EjerciciosTema1/src/tema1/act8/salida.txt");   // Ruta absoluta para el archivo de salida
        File fErr = new File("/home/pablo/Escritorio/PSP2DAM/EjerciciosTema1/src/tema1/act8/error.txt");    // Ruta absoluta para el archivo de errores


        // Proceso que ejecuta la clase actual
        ProcessBuilder pb = new ProcessBuilder("/home/pablo/.jdks/openjdk-23/bin/java",  "/home/pablo/Escritorio/PSP2DAM/EjerciciosTema1/src", "tema1.act8.Actividad1_8");

        // Redirigimos la entrada, salida y errores
        pb.redirectInput(fIn);
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
             BufferedWriter writer = new BufferedWriter(new FileWriter(fOut));
             PrintStream out = new PrintStream(new FileOutputStream(fOut, true))) { // Para agregar a salida.txt

            String linea;
            while ((linea = reader.readLine()) != null) {
                // Procesamiento simple, convertir a mayúsculas

                // Escribir en salida.txt
                writer.write(linea);
                writer.newLine(); // Escribimos una nueva línea

                // Mostrar en consola
                out.println(linea);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
