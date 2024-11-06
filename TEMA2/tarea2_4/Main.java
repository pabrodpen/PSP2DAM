package ej4y5;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyHilo hilo = new MyHilo();

        boolean hiloIniciado = false;
        String input;

        while (true) {
            System.out.print("Ingresa una cadena (S para suspender, R para reanudar, * para terminar): ");
            input = scanner.nextLine();

            if (input.equals("*")) {
                break;
            } else if (input.equals("S")) {
                hilo.suspende();
                System.out.println("Hilo suspendido.");
            } else if (input.equals("R")) {
                hilo.reanuda();
                System.out.println("Hilo reanudado.");
            }

            // Iniciar el hilo solo una vez
            if (!hiloIniciado) {
                hilo.start();
                hiloIniciado = true;
            }
        }

        // Finaliza el hilo y muestra el contador
        hilo.finalizar();


        while (hilo.isAlive()) {
            try {
                Thread.sleep(100); // Espera a que el hilo termine
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Valor final del contador: " + hilo.getContador());
        scanner.close();
    }
}
