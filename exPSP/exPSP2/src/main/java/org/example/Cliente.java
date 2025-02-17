package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    //establecemos el host y puerto para la conexion
    private static final String HOST = "localhost";
    private static final int PUERTO = 8080;

    public static void main(String[] args) {
        //creamos el socket entre el cliente concreto y servidor
        try (Socket socket = new Socket(HOST, PUERTO);
             //establecemos la entrada y salida
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println(entrada.readLine()); // PROGRAMA CLIENTE INICIADO
            System.out.println(entrada.readLine()); // CLIENTE N°

            //bucle de entrada de mensajes por teclado hasta que
            //se introduzca *
            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();
                salida.println(input);

                if (input.equals("*")) {
                    System.out.println(entrada.readLine()); // Cerrando conexión
                    break;
                }

                String respuesta;
                while ((respuesta = entrada.readLine()) != null && !respuesta.isEmpty()) {
                    System.out.println(respuesta);
                }
            }
        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
