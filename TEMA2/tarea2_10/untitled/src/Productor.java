public class Productor extends Thread {
    private Cola cola;
    private int n;

    public Productor(Cola cola, int n) {
        this.cola = cola;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            String mensaje;
            if(i%2==0){
                mensaje="PING";
            }else{
                mensaje="PONG";
            }
            cola.put(mensaje); // Envía el mensaje a la cola
            System.out.println("Productor produce: " + mensaje);

            try {
                sleep(100); // Simula tiempo de producción
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fin productor...");
    }
}
