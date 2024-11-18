public class Productor extends Thread {
    private Cola cola;
    private int n;

    public Productor(Cola cola, int n) {
        this.cola = cola;
        this.n = n;
    }

    public void run() {
        for (int i = 0; i < n; i++) {
            cola.put(i);//pone el numero
            System.out.println(i+"=>Productor : "+n+", produce: "+i);

            try{
                sleep(100);
            }catch(InterruptedException e){}
        }
    }
}
