

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;

public class Remoto {
    public static void main (String[] args){

        FTPClient cliente = new FTPClient();
        // Servidor público

        String servidor = "ftp.rediris.es";
        int puerto = 21;
        String user = "anonymous";// Usuario anónimo
        String passwd = "";

        try {
            cliente.connect(servidor);
            cliente.enterLocalPassiveMode(); //modo pasivo

            boolean login = cliente.login(user, passwd);
            if (login)
                System.out.println("Login exitoso...");
            else {
                System.out.println("Login incorrecto...");
                cliente.disconnect();
                System.exit(1);
            }

            System.out.println("Conectado al servidor FTP.");

            // Cambiar a modo pasivo y binario
            cliente.enterLocalPassiveMode();
            cliente.setFileType(FTP.BINARY_FILE_TYPE);

            System.out.println("Dirección actual: " + cliente.printWorkingDirectory() );
            FTPFile[] archivos = cliente.listFiles();
            System.out.println("Archivos en el directorio actual:" + archivos.length);
            String tipos[] = {"Ficheros", "Directorio", "Enlace simb."};

            for (int i = 0; i < archivos.length; i++){
                System.out.println("\t" + archivos[i].getName() + " => " + tipos[archivos[i].getType()]);
            }

            boolean logout = cliente.logout();
            if (logout)
                System.out.println("Logout del servidor FTP...");
            else
                System.out.println("Error...");
            cliente.disconnect();
            System.out.println("Desconectado del servidor FTP.");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

    }
}