package ej2_2;

import ej2.Hilo1;
import ej2.Hilo2;

public class Main {
    public static void main(String[] args) {
        Hilo h1 = new Hilo("Uno");
        Hilo h2 = new Hilo("Dos");
        Hilo h3 = new Hilo("Tres");
        Hilo h4 = new Hilo("Cuatro");
        Hilo h5 = new Hilo("Cinco");

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