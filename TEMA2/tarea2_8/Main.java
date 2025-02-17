package tarea2_8;

public class Main {
    public static void main(String[] args) {
        // Crear el objeto Saldo con un valor inicial
        Saldo saldo = new Saldo(200);
        System.out.println("Saldo inicial: " + saldo.getSaldo());

        // Crear varios hilos que comparten el mismo objeto Saldo
        GestorSaldo hilo1 = new GestorSaldo("Usuario1", 100, saldo);
        GestorSaldo hilo2 = new GestorSaldo("Usuario2", 150, saldo);
        GestorSaldo hilo3 = new GestorSaldo("Usuario3", 200, saldo);
        GestorSaldo hilo4 = new GestorSaldo("Usuario4", 500, saldo);

        // Iniciar los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();

        // Dar tiempo suficiente para que los hilos terminen
        try {
            Thread.sleep(2000); // Tiempo estimado para que los hilos finalicen
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        // Visualizar el saldo final
        System.out.println("Saldo final: " + saldo.getSaldo());
    }
}
