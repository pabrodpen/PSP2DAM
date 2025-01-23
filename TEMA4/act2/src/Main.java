package Bloque4.Actividad4_2;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Actividad4_2 {


    public static void gestionarFTP(String servFTP, String usuario, String password) {
        FTPClient cliente = new FTPClient();

        try {
            // Conectar al servidor FTP
            cliente.connect(servFTP);
            cliente.enterLocalPassiveMode(); // Cambia a modo pasivo para evitar problemas de red
            System.out.println("Conectando al servidor: " + servFTP);
            boolean login = cliente.login(usuario, password);

            if (login) {
                System.out.println("Inicio de sesión exitoso.");

                // Cambiar al directorio asociado al usuario
                cliente.changeWorkingDirectory("/" + usuario);

                // Seleccionar un archivo para subir al servidor
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());

                    // Subir el archivo al servidor FTP
                    String archivo = selectedFile.getAbsolutePath();
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(archivo));
                    if (cliente.storeFile(selectedFile.getName(), in)) {
                        JOptionPane.showMessageDialog(
                                null,
                                selectedFile.getName() + " => Subido correctamente.",
                                "Subida exitosa",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                selectedFile.getName() + " => No se pudo subir.",
                                "Error de subida",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                    in.close();
                } else {
                    System.out.println("No se seleccionó ningún archivo.");
                }

                // Listar y mostrar el contenido del directorio en el servidor
                System.out.println("Directorio actual: " + cliente.printWorkingDirectory());
                FTPFile[] archivos = cliente.listFiles();
                System.out.println("Contenido del directorio:");
                for (FTPFile archivo : archivos) {
                    if (archivo.isDirectory()) {
                        System.out.println("[Directorio] " + archivo.getName());
                    } else if (archivo.isFile()) {
                        System.out.println("[Archivo] " + archivo.getName());
                    }
                }

                // Cerrar sesión en el servidor FTP
                if (cliente.logout()) {
                    System.out.println("Sesión cerrada correctamente.");
                } else {
                    System.out.println("Error al cerrar la sesión.");
                }

            } else {
                System.out.println("Error en el inicio de sesión. Verifique las credenciales.");
                cliente.disconnect();
                System.exit(1);
            }

        } catch (IOException e) {
            System.out.println("Ocurrió un error durante la operación: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Asegurarse de desconectar siempre el cliente FTP
            try {
                if (cliente.isConnected()) {
                    cliente.disconnect();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("Conexión cerrada.");
        }
    }

    public static void main(String[] args) {
        // Cambiar las credenciales según la configuración del servidor FileZilla
        String servidor = "localhost"; // Dirección del servidor FTP (por defecto localhost)
        String usuario = "pabrodpen29"; // Usuario configurado en el servidor FTP
        String contraseña = "usuario"; // Contraseña asociada al usuario

        // Llamar al método para gestionar la conexión y operaciones FTP
        gestionarFTP(servidor, usuario, contraseña);
    }
}
