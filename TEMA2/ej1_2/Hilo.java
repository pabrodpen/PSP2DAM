package ej1_2;

public class Hilo extends Thread{

    String cadena;
    public Hilo() {
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Thread.currentThread().getId()*500);
            System.out.println("Hola mundo ");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
