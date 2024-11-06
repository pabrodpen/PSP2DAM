package ej2;

public class Hilo implements Runnable{

    String cadena;
    public Hilo() {
    }

    @Override
    public void run() {
        System.out.println("Hola mundo "+":"+Thread.currentThread().getId());
    }

}
