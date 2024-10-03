package tema1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Actividad1_6 {
    public static void main(String[] args) throws IOException {
        InputStreamReader inputStreamReader=new InputStreamReader(System.in);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        int n1=0,n2=0;

        try{
            try{
                System.out.print("Primer numero:");
                n1= Integer.parseInt(bufferedReader.readLine());

            }catch (NumberFormatException e){
                e.printStackTrace();
            }

            try {
                System.out.print("Segundo numero:");
                n2= Integer.parseInt(bufferedReader.readLine());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }



            int suma=n1+n2;
            System.out.println("Suma:"+suma);
            inputStreamReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
