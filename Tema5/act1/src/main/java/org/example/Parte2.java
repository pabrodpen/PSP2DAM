package org.example;

import java.security.MessageDigest;

public class Parte2 {
    public static void main(String[] args) {

        String cadena1 = "Hola me llamo Pablo";
        String password = "agosto";
        String cadena2 = "Adios";

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            String texto = "Esto es un texto plano";

            byte[] textoByte = texto.getBytes();//texto a bytes
            md.update(textoByte);//se introduce texto en bytes a resumir
            byte[] resumen = md.digest(password.getBytes());

            System.out.println("Mensaje resumen: " + new String(resumen));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}