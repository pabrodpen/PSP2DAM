public class Product_Consum {
    public static void main(String[] args) {
        Cola cola = new Cola();

        // Crear productor y consumidores
        Productor productor = new Productor(cola);
        Consumidor consumidor1 = new Consumidor(cola, 1);
        Consumidor consumidor2 = new Consumidor(cola, 2);

        // Iniciar hilos
        productor.start();
        consumidor1.start();
        consumidor2.start();
    }
}
