package org.example;
import java.security.MessageDigest;
import java.io.*;

public class Ejemplo6 {
    public static void main(String args[]) {
        try {
            FileInputStream fileout = new FileInputStream("DATOS.DAT");
            ObjectInputStream dataOS = new ObjectInputStream(fileout);
            Object o = dataOS.readObject();

            // Primera lectura, se obtiene el String
            String datos = (String) o;
            System.out.println("Datos: " + datos);

            // Segunda lectura, se obtiene el resumen
            o = dataOS.readObject();
            byte resumenOriginal[] = (byte[]) o;

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Se calcula el resumen del String leído del fichero
            md.update(datos.getBytes()); // TEXTO A RESUMIR
            byte resumenActual[] = md.digest(); // SE CALCULA EL RESUMEN

            // Se comparan los dos resúmenes
            if (MessageDigest.isEqual(resumenActual, resumenOriginal))
                System.out.println("DATOS VÁLIDOS");
            else
                System.out.println("DATOS NO VÁLIDOS");

            dataOS.close();
            fileout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

