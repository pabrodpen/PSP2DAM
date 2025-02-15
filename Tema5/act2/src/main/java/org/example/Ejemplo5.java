package org.example;
import java.io.*;
import java.security.*;

public class Ejemplo5 {
    public static void main(String args[]) {
        try {
            // Se crean los flujos de salida para escribir en el archivo
            FileOutputStream fileout = new FileOutputStream("DATOS.DAT");
            ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

            // Se inicializa el objeto MessageDigest con el algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Texto que será resumido
            String datos = "En un lugar de la Mancha, "
                    + "de cuyo nombre no quiero acordarme, no ha mucho tiempo "
                    + "que vivía un hidalgo de los de lanza en astillero, "
                    + "adarga antigua, rocín flaco y galgo corredor.";

            // Se convierte la cadena de texto a bytes
            byte dataBytes[] = datos.getBytes();

            // Se actualiza el resumen con los datos
            md.update(dataBytes);

            // Se calcula el resumen
            byte resumen[] = md.digest();

            // Se escriben los datos originales en el archivo
            dataOS.writeObject(datos);

            // Se escribe el resumen en el archivo
            dataOS.writeObject(resumen);

            // Se cierran los flujos de salida
            dataOS.close();
            fileout.close();

            System.out.println("Datos y resumen guardados en DATOS.DAT");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
