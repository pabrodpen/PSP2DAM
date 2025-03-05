import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.util.Scanner;

public class ClienteSSL {
    public static void main(String[] args) {
        String Host = "localhost";
        int Puerto = 6000;

        System.out.println("PROGRAMA CLIENTE INICIADO");

        SSLSocketFactory sfact= (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket Cliente= null;
        try {
            Cliente = (SSLSocket) sfact.createSocket(Host,Puerto);

            //flujo de salida al servidor
            ObjectOutputStream salidaCliente=new ObjectOutputStream(Cliente.getOutputStream());

            //envia un objeto Numero al servidor
            Scanner sc = new Scanner(System.in);

            System.out.println("Numero:");
            int n=sc.nextInt();
            Numeros numero;
            numero= new Numeros(n);

            salidaCliente.writeObject(numero);

            //leo el cuadrado del numero

            ObjectInputStream entradaCliente=new ObjectInputStream(Cliente.getInputStream());
            try {
                System.out.println("Cuadrado del numero:"+entradaCliente.readObject());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

}
