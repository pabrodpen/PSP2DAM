package org.example;

public class Cuenta {
    double saldo;
    double saldoMax;
    boolean operacionDisponible=true;
    public synchronized double getSaldo() {
        return saldo;
    }

    public synchronized void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public synchronized double getSaldoMax() {
        return saldoMax;
    }

    public synchronized void setSaldoMax(double saldoMax) {
        this.saldoMax = saldoMax;
    }

    public Cuenta(double saldo, double saldoMax) {
        this.saldo = saldo;
        this.saldoMax = saldoMax;
    }

    public synchronized void ingresarSaldo(double cantidad) {
        while(!operacionDisponible) {
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        double saldoActual=getSaldo();
        if(cantidad+saldoActual > getSaldoMax()) {
            System.out.println("Lo siento, esa cantidad supera el maximo");
        }else{
            setSaldo(saldoActual+cantidad);
            System.out.println("Saldo actual: " + saldoActual);
        }

        operacionDisponible=true;
        notifyAll();

    }

    public synchronized void hacerReintegro(double cantidad) {
        while(!operacionDisponible) {
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        double saldoActual=getSaldo();
        if(saldoActual-cantidad <0) {
            System.out.println("Lo siento, esa cantidad supera al saldo actual");
        }else{
            setSaldo(saldoActual-cantidad);
            System.out.println("Saldo actual: " + saldo);
        }

        operacionDisponible=true;
        notifyAll();

    }
}
