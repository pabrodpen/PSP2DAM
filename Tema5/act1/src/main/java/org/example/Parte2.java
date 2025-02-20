package org.example;

import java.security.MessageDigest;
import java.util.Scanner;

public class Parte2 {
    public static void main(String[] args) {

        String cadena1 = "";
        String password = "";
        String cadena2 = "";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Cadena 1: ");
        cadena1 = scanner.nextLine();
        System.out.println("Cadena 2: ");
        cadena2 = scanner.nextLine();
        System.out.println("Contraseña: ");
        password = scanner.nextLine();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Hash de la primera cadena
            md.update(password.getBytes());
            md.update(cadena1.getBytes());
            byte[] resumen1 = md.digest();

            // Reiniciar el MessageDigest
            md.reset();

            // Hash de la segunda cadena
            md.update(password.getBytes());
            md.update(cadena2.getBytes());
            byte[] resumen2 = md.digest();

            // Convertir a hexadecimal
            String hex1 = Hexadecimal(resumen1);
            String hex2 = Hexadecimal(resumen2);

            // Mostrar los hashes en hexadecimal
            System.out.println("Hash Cadena 1: " + hex1);
            System.out.println("Hash Cadena 2: " + hex2);

            // Comparación
            boolean igual = MessageDigest.isEqual(resumen1, resumen2);
            System.out.println("¿Son iguales? " + igual);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static String Hexadecimal(byte[]resumen){
        String hex="";
        for(int i=0;i<resumen.length;i++){
            String h=Integer.toHexString(resumen[i]&0xFF);
            if(h.length()==1) hex+="0";
            hex+=h;
        }

        return hex.toUpperCase();
    }
}
