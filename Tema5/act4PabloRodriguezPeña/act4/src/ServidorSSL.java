import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServidorSSL {
    public static void main(String[] args) {
        int puerto=6000;

        System.setProperty("javax.net.ssl.keyStore", "AlmacenSrv");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");


        SSLServerSocketFactory sfact= (SSLServerSocketFactory)  SSLServerSocketFactory.getDefault();
        try {
            SSLServerSocket servidorSSL= (SSLServerSocket) sfact.createServerSocket(puerto);

            SSLSocket clienteConectado=(SSLSocket) servidorSSL.accept();


            // Flujo de entrada para recibir el objeto del cliente
            ObjectInputStream entrada = new ObjectInputStream(clienteConectado.getInputStream());

            Numeros numero = null;
            try {
                numero = (Numeros) entrada.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Número recibido: " + numero.getNumero());

            // Flujo de salida para enviar el objeto modificado al cliente
            ObjectOutputStream salida = new ObjectOutputStream(clienteConectado.getOutputStream());
            salida.writeObject(numero.getCuadrado());
            System.out.println("Enviando objeto con cuadrado");

            // Cerrar flujos y socket
            salida.close();
            entrada.close();
            clienteConectado.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
