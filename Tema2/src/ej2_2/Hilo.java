package ej2_2;

public class Hilo implements Runnable{

    String cadena;
    public Hilo(String cadena) {
        this.cadena=cadena;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Thread.currentThread().getId()*1000);
            System.out.println("Hola mundo "+cadena+":"+Thread.currentThread().getId());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
