package ej1;

public class Main {
    public static void main(String[] args) {
        Hilo1 hilo1=new Hilo1();
        Hilo2 hilo2=new Hilo2();

        Thread thread1=new Thread(hilo1);
        Thread thread2=new Thread(hilo2);

        thread1.start();
        thread2.start();
    }
}