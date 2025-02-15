package org.example;

import java.security.MessageDigest;

public class Parte2 {
    public static void main(String[] args) {

        String cadena1 = "Adios";
        String password = "agosto";
        String cadena2 = "Adios";

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[]cad1Byte=cadena1.getBytes();
            md.update(password.getBytes());
            md.update(cadena1.getBytes());

            byte[]resumen1=md.digest();

            md.reset();

            byte[]cad2Byte=cadena2.getBytes();
            md.update(password.getBytes());
            md.update(cadena2.getBytes());
            byte[]resumen2=md.digest();

            String hex1=Hexadecimal(resumen1);
            String hex2=Hexadecimal(resumen2);

            System.out.println("Cadena 1: " + hex1);
            System.out.println("Cadena 2: " + hex2);


            boolean igual = md.isEqual(resumen1,resumen2);
            System.out.println("Son iguales? " + igual);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    static String Hexadecimal(byte[]resumen){
        String hex="";
        for(int i=0;i<resumen.length;i++){
            String h=Integer.toHexString(resumen[i]&0xFF);
            if(hex.length()==1 ) hex+="0";
            hex+=h;
            }
        return hex.toUpperCase();
    }
}