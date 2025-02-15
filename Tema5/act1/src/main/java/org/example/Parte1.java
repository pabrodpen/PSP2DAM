package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

public class Parte1 {
    public static void main(String[] args) {

       MessageDigest md;
       try {
           md = MessageDigest.getInstance("MD5");
           String texto = "Esto es un texto plano";

           byte[] textoByte = texto.getBytes();//texto a bytes
           md.update(textoByte);//se introduce texto en bytes a resumir
           byte[] resumen = md.digest();

           System.out.println("Mensaje original: " +texto);
           System.out.println("Numero de bytes: " +md.getDigestLength());
           System.out.println("Algoritmo: " +md.getAlgorithm());
           System.out.println("Mensaje resumen: " + new String(resumen));
           System.out.println("Mensaje en Hexadecimal: "+Hexadecimal(resumen));

           Provider proveedor=md.getProvider();
            System.out.println("Proveedor: "+proveedor.toString());

       }catch (NoSuchAlgorithmException e){
           e.printStackTrace();
       }

    }

    static String Hexadecimal(byte[]resumen){
        String hexadecimal="";
        for(int i=0;i<resumen.length;i++){
            String hex=Integer.toHexString(resumen[i]&0xff);
            if(hex.length()==1){
                hexadecimal+=hex;
            }
        }
        return hexadecimal.toUpperCase();
    }
}