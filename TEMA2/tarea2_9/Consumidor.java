public class Consumidor extends Thread {
    private Cola cola;
    private int n;

    public Consumidor(Cola cola, int n) {
        this.cola = cola;
        this.n = n;
    }

    @Override
    public void run() {
        int valor=0;
        for(int i=0;i<5;i++){
            valor=cola.get();//recoge el numero
            System.out.println(i+"=>Consumidor : "+n+", consume: "+i);
        }
    }
}
