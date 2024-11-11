package tarea2_6;

public class Main {
    public static void main(String[] args) {

        //PRIMER EJERCICIO
        Hilo1 hilo1=new Hilo1();
        Hilo2 hilo2=new Hilo2();



        Thread thread1=new Thread(hilo1);
        Thread thread2=new Thread(hilo2);

        hilo1.setPriority(2);
        hilo2.setPriority(1);

        thread1.start();
        thread2.start();


    }
}