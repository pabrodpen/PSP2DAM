import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.util.Scanner;

//CREAR KEY ENTRE CLIENTE Y SERVIDOR
//keytool -genkey -alias servidor -keyalg RSA -keystore AlmacenSrv -storepass 1234567
//keytool -exportcert -alias servidor -keystore AlmacenSrv -storepass 1234567 -file CertificadoServ.cer
//keytool -importcert -trustcacerts -alias servidor -file CertificadoServ.cer -keystore CliCertConfianza -storepass 890123


//LO MISMO Por parte del cliente
//keytool -genkey -alias cliente -keyalg RSA -keystore AlmacenCli -storepass clavecli
//keytool -exportcert -alias cliente -keystore AlmacenCli -storepass clavecli -file CertificadoCli.cer
//keytool -importcert -trustcacerts -alias cliente -file CertificadoCli.cer -keystore ServCertConfianza -storepass cercil

//PARA EJECUTAR en SERVIDOR:
/*
java -Djavax.net.ssl.keyStore=AlmacenSrv \
     -Djavax.net.ssl.keyStorePassword=1234567 \
     ServidorSSL

 */

//PARA EJECUTAR EN CLIENTE :
/*
java -Djavax.net.ssl.trustStore=CliCertConfianza \
     -Djavax.net.ssl.trustStorePassword=890123 \
     ClienteSSL

*/
public class ClienteSSL {
    public static void main(String[] args) {
        String Host = "localhost";
        int Puerto = 6000;

        System.setProperty("javax.net.ssl.trustStore", "CliCertConfianza");
        System.setProperty("javax.net.ssl.trustStorePassword", "890123");



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
