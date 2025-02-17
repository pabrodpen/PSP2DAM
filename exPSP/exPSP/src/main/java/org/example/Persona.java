package org.example;

public class Persona extends Thread {

    Cuenta cuenta;
    String nombre;

    public Persona(String nombre,Cuenta cuenta) {
        this.nombre = nombre;
        this.cuenta = cuenta;
    }
    @Override
    public void run() {
        int aleatorio;
        aleatorio=(int)(Math.random()*500+1);
        cuenta.ingresarSaldo(aleatorio);
        System.out.println(nombre+" ingreso: "+cuenta.getSaldo());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        aleatorio=(int)(Math.random()*500+1);
        cuenta.hacerReintegro(aleatorio);
        System.out.println(nombre+" reintegro: "+cuenta.getSaldo());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        aleatorio=(int)(Math.random()*500+1);
        cuenta.ingresarSaldo(aleatorio);
        System.out.println(nombre+" : ingreso: "+cuenta.getSaldo());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        aleatorio=(int)(Math.random()*500+1);
        cuenta.hacerReintegro(aleatorio);
        System.out.println(nombre+" : reintegro: "+cuenta.getSaldo());
    }
}
