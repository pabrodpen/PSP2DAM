package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/*
* Voy a hacer el ejercicio de la siguiente manera:
* Primero creo las clases Asignatura, Especialidad y Profesor con sus
* correspondientes atributos
* Luego creo la clase Cliente en la que establezco la conexion con el servidor
* y la entrada y salida con este
*
* En la clase Servidor inicializo los profesores y las especialidades.
* Ademas, establezco conexion con cada cliente, el cual es un hilo distinto
*
* Necesito una clase que sea un hilo que actue por cada cliente, por lo que creo
* la clase ClienteHandler. En su constructor meto el Socket que va a usar y su id
* , y hago el bucle para escirbir e id del profesor que quiere buscar y se muestra
*
* ANTES FUNCIIONABA, PERO CREO QUE HE MODIFICADO ALGO DE SERVIDOR Y YA NO FUNCIONA
*
* */

public class Servidor {
    //inicializamos el id de de los cliwntes en 1 y establecemos
    //el map para buscar a los profesores
    //que esta formado por el id (Integer) y un ojeto Profesor
    public static final int PUERTO = 8080;
    public static Map<Integer, Profesor> profesores = new HashMap<>();
    public static int idCliente = 1;

    public static void main(String[] args) {
        // Inicializar especialidades,profesores y asignaturas
        Especialidad espInformatica = new Especialidad(1, "INFORMÁTICA");
        Especialidad espMatematicas = new Especialidad(2, "MATEMATICAS");

        profesores.put(1, new Profesor(1, "Jesús Espejo",
                new Asignatura[]{new Asignatura(2, "ADAT"), new Asignatura(3, "PSP")},
                espInformatica));

        profesores.put(2, new Profesor(2, "María López",
                new Asignatura[]{new Asignatura(5, "Álgebra"), new Asignatura(6, "Cálculo")},
                espMatematicas));

        // Iniciar servidor
        System.out.println("Servidor iniciado...");
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente " + idCliente + " conectado");
                new Thread(new ClienteHandler(cliente, idCliente)).start();
                idCliente++;
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}