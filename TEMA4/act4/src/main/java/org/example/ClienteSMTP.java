package org.example;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import java.io.IOException;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ClienteSMTP {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        AuthenticatingSMTPClient cliente = new AuthenticatingSMTPClient();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce el servidor SMTP: ");
        String server = scanner.nextLine();
        System.out.print("Usuario: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Puerto: ");
        int puerto = Integer.parseInt(scanner.nextLine());
        System.out.print("Introduce el remitent: ");
        String remitente = scanner.nextLine();

        try {
            int respuesta;
            cliente.connect(server, puerto);
            System.out.println("2 - " + cliente.getReplyString());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(null, null);
            KeyManager km = kmf.getKeyManagers()[0];
            cliente.setKeyManager(km);

            respuesta = cliente.getReplyCode();
            if (!SMTPReply.isPositiveCompletion(respuesta)) {
                cliente.disconnect();
                System.out.println("Se ha producido un error al enviar el servidor SMTP");
                return;
            }


            cliente.ehlo(server);
            System.out.println("2 - " + cliente.getReplyString());

            if (cliente.execTLS()) {
                System.out.println("3 - " + cliente.getReplyString());


                if (cliente.auth(AuthenticatingSMTPClient.AUTH_METHOD.PLAIN, username, password)) {
                    System.out.println("4 - " + cliente.getReplyString());


                    System.out.print("Introduce el destinatario: ");
                    String destino1 = scanner.nextLine();
                    System.out.print("Introduce el asunto: ");
                    String asunto = scanner.nextLine();
                    System.out.print("Introduce el mensaje: ");
                    String mensaje = scanner.nextLine();

                    SimpleSMTPHeader cabecera = new SimpleSMTPHeader(remitente, destino1, "Asunto: " + asunto);
                    cliente.setSender(remitente);
                    cliente.addRecipient(destino1);
                    System.out.println("5 - " + cliente.getReplyString());

                    Writer writer = cliente.sendMessageData();
                    if (writer != null) {
                        writer.write(cabecera.toString());
                        writer.write(mensaje);
                        writer.close();
                        System.out.println("6 - " + cliente.getReplyString());

                        boolean exito = cliente.completePendingCommand();
                        System.out.println("7 - " + cliente.getReplyString());

                        if (exito) {
                            System.out.println("Mensaje enviado correctamente");
                        } else {
                            System.out.println("Error al enviar el servidor SMTP");
                        }
                    } else {
                        System.out.println("Fallo al enviar la data");
                    }
                } else {
                    System.out.println("Usuario no valido");
                }
            } else {
                System.out.println("Error en TLS");
            }

            cliente.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cliente.disconnect();
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Proceso finalizado");
        System.out.println(0);
    }
}