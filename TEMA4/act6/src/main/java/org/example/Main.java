package org.example;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String user, pass;
        FTPClient ftpClient = new FTPClient();

        try {
            // Conectar al servidor FTP una sola vez
            ftpClient.connect("localhost");

            while (true) {
                System.out.print("Ingrese su usuario (* para salir): ");
                user = scanner.nextLine();

                if (user.equals("*")) {
                    System.out.println("Cerrando sesión...");
                    break;
                }

                System.out.print("Ingrese su contraseña: ");
                pass = scanner.nextLine();

                // Intentar iniciar sesión en el servidor FTP
                if (ftpClient.login(user, pass)) {
                    System.out.println("Inicio de sesión exitoso.");
                    registrarActividad(ftpClient, user);
                    ftpClient.logout();
                } else {
                    System.out.println("Error de autenticación. Se notificará al administrador.");
                    enviarAlerta(user);
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo establecer la conexión con el servidor FTP: " + e.getMessage());
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                System.err.println("Error al cerrar la conexión FTP: " + e.getMessage());
            }
            scanner.close();
        }
    }

    /**
     * Registra la conexión del usuario en el servidor FTP.
     */
    private static void registrarActividad(FTPClient ftpClient, String user) {
        try {
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Definir la carpeta de registros
            String logFolderPath = "/" + user + "/LOG";
            if (!ftpClient.changeWorkingDirectory(logFolderPath)) {
                ftpClient.makeDirectory(logFolderPath);
                ftpClient.changeWorkingDirectory(logFolderPath);
            }

            // Crear el archivo de registro con la marca de tiempo
            String logFileName = "LOG.TXT";
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String logContent = "Registro de acceso del usuario:\nFecha y hora: " + timestamp + "\n\n";

            InputStream logStream = new ByteArrayInputStream(logContent.getBytes());
            ftpClient.storeFile(logFileName, logStream);
            logStream.close();

            System.out.println("Registro guardado en: " + logFolderPath + "/" + logFileName);
        } catch (IOException e) {
            System.err.println("No se pudo registrar la actividad del usuario: " + e.getMessage());
        }
    }

    /**
     * Simula el envío de una alerta al administrador en caso de intento fallido de autenticación.
     */
    private static void enviarAlerta(String user) {
        System.out.println("[ALERTA] Intento fallido de inicio de sesión para el usuario: " + user);
    }
}
