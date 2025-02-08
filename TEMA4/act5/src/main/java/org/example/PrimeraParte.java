package org.example;

import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PrimeraParte {

    public static void main(String[] args) {
        // Configuración del servidor y credenciales del usuario
        String servidorPOP3 = "localhost";
        String usuario = "usu1";
        String contrasena = "usuario";
        int puertoPOP3 = 110;

        // Creación del cliente POP3
        POP3Client clientePOP3 = new POP3Client();

        try {
            // Conectar al servidor
            clientePOP3.connect(servidorPOP3, puertoPOP3);
            System.out.println("Conexión establecida con el servidor POP3: " + servidorPOP3);
            System.out.println("Respuesta del servidor: " + clientePOP3.getReplyString());

            // Autenticación del usuario
            if (!clientePOP3.login(usuario, contrasena)) {
                System.err.println("Error: No se pudo iniciar sesión con las credenciales proporcionadas.");
                System.out.println("Respuesta del servidor: " + clientePOP3.getReplyString());
            } else {
                System.out.println("Inicio de sesión exitoso.");
                System.out.println("Respuesta del servidor: " + clientePOP3.getReplyString());

                // Obtener la lista de mensajes disponibles en el servidor
                POP3MessageInfo[] listaMensajes = clientePOP3.listMessages();

                if (listaMensajes == null) {
                    System.out.println("No se pudieron recuperar los mensajes.");
                } else {
                    System.out.println("Cantidad de mensajes en el buzón: " + listaMensajes.length);
                }

                // Mostrar información de los mensajes
                System.out.println("\n=== Información de los Mensajes");
                mostrarDetallesMensajes(listaMensajes, clientePOP3);

                // Mostrar cabeceras de los mensajes
                System.out.println("Cabeceras de los Mensajes");
                mostrarCabecerasMensajes(listaMensajes, clientePOP3);

                // Cerrar sesión
                clientePOP3.logout();
                System.out.println("Sesión cerrada correctamente.");
            }

            // Desconectar del servidor
            clientePOP3.disconnect();
            System.out.println("Desconectado del servidor POP3.");

        } catch (IOException e) {
            System.err.println("Error en la comunicación con el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método para mostrar detalles sobre los mensajes en el buzón.
     * @param listaMensajes Array con la información de los mensajes.
     * @param clientePOP3 Cliente POP3 para obtener detalles adicionales.
     */
    private static void mostrarDetallesMensajes(POP3MessageInfo[] listaMensajes, POP3Client clientePOP3) throws IOException {
        if (listaMensajes == null || listaMensajes.length == 0) {
            System.out.println("No hay mensajes en el buzón.");
            return;
        }

        for (int i = 0; i < listaMensajes.length; i++) {
            System.out.println("Mensaje #" + (i + 1));
            POP3MessageInfo infoMensaje = listaMensajes[i];

            System.out.println("Identificador único: " + infoMensaje.identifier);
            System.out.println("Número del mensaje: " + infoMensaje.number);
            System.out.println("Tamaño del mensaje: " + infoMensaje.size + " bytes");

            // Obtener el identificador único del mensaje
            POP3MessageInfo mensajeUnico = clientePOP3.listUniqueIdentifier(infoMensaje.number);
            if (mensajeUnico != null) {
                System.out.println("🔹 Identificador único (UIDL): " + mensajeUnico.identifier);
            }
            System.out.println("Respuesta del servidor: " + clientePOP3.getReplyString());
        }
    }

    /**
     * Método para recuperar y mostrar las cabeceras de los mensajes.
     * @param listaMensajes Array con la información de los mensajes.
     * @param clientePOP3 Cliente POP3 para recuperar las cabeceras.
     */
    private static void mostrarCabecerasMensajes(POP3MessageInfo[] listaMensajes, POP3Client clientePOP3) throws IOException {
        if (listaMensajes == null || listaMensajes.length == 0) {
            System.out.println("No hay cabeceras de mensajes para mostrar.");
            return;
        }

        for (int i = 0; i < listaMensajes.length; i++) {
            System.out.println("Cabecera del mensaje #" + (i + 1));

            // Recuperar y mostrar la cabecera del mensaje
            BufferedReader lectorCabecera = (BufferedReader) clientePOP3.retrieveMessageTop(listaMensajes[i].number, 0);
            System.out.println("Respuesta del servidor: " + clientePOP3.getReplyString());

            String linea;
            while ((linea = lectorCabecera.readLine()) != null) {
                System.out.println(linea);
            }
            lectorCabecera.close();
        }
    }
}
