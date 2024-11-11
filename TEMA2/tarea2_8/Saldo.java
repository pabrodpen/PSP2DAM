public class Saldo {
    int saldo;

    public Saldo(int saldo) {
        this.saldo = saldo;
    }

    int nRandom=(int)(Math.random()*(1000-100+1)+100);

    public int getSaldo() {
        try {
            Thread.sleep(nRandom);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return saldo;
    }

    public void setSaldo(int saldo) {
        try {
            Thread.sleep(nRandom);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.saldo = saldo;
    }
}
