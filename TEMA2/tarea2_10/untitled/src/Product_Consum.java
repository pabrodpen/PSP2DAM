public class Product_Consum {
    public static void main(String[] args) {
        Cola cola = new Cola();
        Productor productor=new Productor(cola,1);
        Consumidor consumidor=new Consumidor(cola,1);

        productor.start();
        consumidor.start();

        /*
        * Sin sleep, el consumidor va más rápido que el productor y consume
        * antes de que haya algo en la cola, causando desorden en la salida.
        * Básicamente, el consumidor se "adelanta" y la cola queda vacía a ratos.
        * Con sleep, el productor se toma pausas y le da tiempo al consumidor para
        * que se coordinen mejor, pero no asegura una sincronización perfecta.
        * */
    }
}
