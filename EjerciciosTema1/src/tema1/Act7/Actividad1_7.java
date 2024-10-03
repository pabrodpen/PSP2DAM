package tema1.Act7;

import java.io.File;

public class Actividad1_7 {
    public static void main(String[] args) {

        ProcessBuilder pb=new ProcessBuilder("/home/pablo/.jdks/openjdk-23/bin/java", "tema1/Actividad1_7");


        File fOut=new File("entrada.txt");
        File fIn=new File("salida.txt");
        File fErr=new File("error.txt");

        pb.redirectError(fErr);
        pb.redirectInput(fIn);
        pb.redirectOutput(fOut);

        try{

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
