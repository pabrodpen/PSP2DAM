package ej2;

public class Main {
    public static void main(String[] args) {
        Hilo h1 = new Hilo();
        Hilo h2 = new Hilo();
        Hilo h3 = new Hilo();
        Hilo h4 = new Hilo();
        Hilo h5 = new Hilo();

        Thread hilo1 = new Thread(h1);
        Thread hilo2 = new Thread(h2);
        Thread hilo3 = new Thread(h3);
        Thread hilo4 = new Thread(h4);
        Thread hilo5 = new Thread(h5);

        // Iniciar los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();
    }
}