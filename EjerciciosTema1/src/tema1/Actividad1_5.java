package tema1;

import java.io.File;
import java.io.InputStream;

public class Actividad1_5 {
    public static void main(String[] args) {

        File direct=new File(".\\out");//cogemos el .class del archivo que cojamos

        ProcessBuilder pb=new ProcessBuilder("java","skwjwkw");

        pb.directory(direct);

        System.out.println("Directorio de trabajo: %s%n",pb.directory());

        Process process=pb.start();


        try { InputStream er = p.getErrorStream(); BufferedReader brer = new   BufferedReader(new InputStreamReader(er)); String liner =   null; while ((liner =   brer.readLine()) !=   null) System.out.println("ERROR >" + liner); catch (IOException ioe) {
            ioe.printStackTrace();
    }
}
