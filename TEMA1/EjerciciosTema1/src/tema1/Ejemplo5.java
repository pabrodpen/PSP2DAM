package tema1;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Ejemplo5 {
    public static void main(String[] args) {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);
        String texto;
        try {
            System.out.println("Introduce una cadena");
            texto = br.readLine();
            System.out.println("Hola soy: " + texto);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

