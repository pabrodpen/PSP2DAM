package tarea2_8;

public class Saldo {
    int saldo;

    public Saldo(int saldo) {
        this.saldo = saldo;
    }

    int nRandom=(int)(Math.random()*(1000-100+1)+100);

    public synchronized int getSaldo() {
        try {
            Thread.sleep(nRandom);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return saldo;
    }

    public synchronized void setSaldo(int saldo) {
        try {
            Thread.sleep(nRandom);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.saldo = saldo;
    }

    public synchronized void addSaldo(String persona, int cantidad) {
        saldo+=cantidad;
        System.out.println(persona+" añade "+cantidad+".Saldo actual:"+getSaldo());
    }
}
