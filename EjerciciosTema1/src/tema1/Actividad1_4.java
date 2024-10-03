package tema1;

import java.io.*;

public class Actividad1_4 {

    public static void main(String[] args) throws IOException {


        File ruta =new File("/home/pablo/Escritorio/PSP2DAM/out/production/PSP2DAM/");

        ProcessBuilder pb=new ProcessBuilder("/home/pablo/.jdks/openjdk-23/bin/java", "tema1/LeerNombre", "Hola que tal");


        pb.directory(ruta);

        //inicia el proceso
        Process process= pb.start();


        //recoger el flujo de salida del proceso como flujo de entrada propio

        try{
            InputStreamReader reader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        //pg 31-->comprobacion de error o exito -0 bien -1 mal
        int exito;
        try{
            exito=process.waitFor();
            System.out.println("Valor de salida:"+exito);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
