public class Consumidor extends Thread {
    private Cola cola;
    private int id;

    public Consumidor(Cola cola, int id) {
        this.cola = cola;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) { // Los consumidores intentan consumir indefinidamente
            int valor=0;
            for(int i=0;i<5;i++){
                valor=cola.get();//recoge el numero
                System.out.println(valor);
            }
        }
    }
}
