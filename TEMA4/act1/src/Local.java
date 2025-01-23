
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

public class Local {
    public static void main (String [] args) throws SocketException, IOException {

        FTPClient ftpClient = new FTPClient();
        String servFTP = "localhost"; // Usuario anónimo
        ftpClient.connect(servFTP);

        System.out.println("Conexion: " + servFTP);

        String user = "anonymous";
        String passwd = "";

        try {
            ftpClient.connect(servFTP);
            ftpClient.enterLocalPassiveMode();

            boolean login = ftpClient.login(user, passwd);
            if (login)
                System.out.println("Login exitoso..");
            else {
                System.out.println("Login incorrecto...");
                ftpClient.disconnect();
                System.exit(1);
            }
            System.out.println("Dirección actual: " + ftpClient.printWorkingDirectory() );
            FTPFile[] archivos = ftpClient.listFiles();
            // Listar directorios en el directorio raíz
            System.out.println("Archivos en el directorio actual:" + archivos.length);
            String tipos[] = {"Ficheros", "Directorio", "Enlace simb."};

            for (int i = 0; i < archivos.length; i++){
                System.out.println("\t" + archivos[i].getName() + " => " + tipos[archivos[i].getType()]);
            }

            boolean logout = ftpClient.logout();
            if (logout)
                System.out.println("Logout del servidor FTP...");
            else
                System.out.println("Error...");
            ftpClient.disconnect();
            System.out.println("Desconexion exitosa");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}


        