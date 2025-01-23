import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void addArchivo(String servFTP, String usuario, String password) {
        try {
            //Conexión al servidor
            FTPClient cliente = new FTPClient();
            cliente.connect(servFTP);
            cliente.enterLocalPassiveMode();
            System.out.println("Conectando al sevidor: "+servFTP);
            boolean logeado = cliente.login(usuario, password);
            if (logeado) {
                System.out.println("Login exitoso...");
            }else {
                System.out.println("Error en el login...");
                cliente.disconnect();
                System.exit(1);
            }

            //Nos movemos al directorio del usuario
            cliente.changeWorkingDirectory("/"+usuario);

            //Selección del archivo y subida al servidor FTP
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Archivo: " + selectedFile.getAbsolutePath());

                String archivo = selectedFile.getAbsolutePath();
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(archivo));
                if (cliente.storeFile(selectedFile.getName(), in)) {
                    JOptionPane.showMessageDialog(
                            null,
                            selectedFile.getName()+"=> Subido correctamente",
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }else {
                    JOptionPane.showMessageDialog(
                            null,
                            selectedFile.getName()+"=> Error al subir archivo",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                in.close();
            } else {
                System.out.println("Error, no se ha seleccionado ningun archivo.");
            }

            //Se muestra el contenido del directorio actual
            System.out.println("Directorio actual: "+cliente.printWorkingDirectory());
            FTPFile[] directorios = cliente.listFiles();
            for (int i = 0; i < directorios.length; i++) {
                System.out.println("\t =>"+directorios[i].getName());
            }


            //Desconexión
            boolean logout = cliente.logout();
            if (logout) {
                System.out.println("Logout correcto...");
            } else {
                System.out.println("Logout incorrecto...");
            }
            cliente.disconnect();
            System.out.println("Desconectado\n");


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        addArchivo("localhost", "pabrodpen", "usuario");
    }
}