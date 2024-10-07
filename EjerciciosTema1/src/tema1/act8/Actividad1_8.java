package tema1.act8;

import java.io.*;

public class Actividad1_8 {
    public static void main(String[] args) throws IOException {
        // Ruta relativa para los archivos de entrada y salida
        File fIn = new File("EjerciciosTema1/src/tema1/act8/entrada.txt");  // Ruta relativa al archivo de entrada
        File fOut = new File("EjerciciosTema1/src/tema1/act8/salida.txt");  // Ruta relativa al archivo de salida

        // Ruta relativa del directorio donde está el archivo Ejemplo5.class compilado
        File ruta = new File("out/production/PSP2DAM/");  // Ruta relativa al directorio de clases compiladas

        // Configuración del ProcessBuilder para ejecutar Ejemplo5
        ProcessBuilder pb = new ProcessBuilder("java", "tema1.Ejemplo5");
        pb.directory(ruta);  // Establece el directorio de trabajo del proceso

        // Redirigir la entrada desde el archivo entrada.txt
        pb.redirectInput(fIn);

        // Redirigir la salida del proceso a la consola (INHERIT toma la salida estándar del proceso)
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        // Iniciar el proceso
        Process process = pb.start();

        // Escribir el contenido de entrada.txt en salida.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(fIn));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fOut))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Esperar a que el proceso termine
            int exitCode = process.waitFor();
            System.out.println("El proceso terminó con el código: " + exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
