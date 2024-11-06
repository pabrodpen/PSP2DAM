package ej1_2;

public class Hilo extends Thread{

    String cadena;
    public Hilo() {
    }

    @Override
    public void run() {
        System.out.println("Hola mundo "+":"+Thread.currentThread().getId());
    }

}
