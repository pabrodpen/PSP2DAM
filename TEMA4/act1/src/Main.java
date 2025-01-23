import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String server = "ftp.rediris.es"; // Servidor público
        int port = 21;
        String user = "anonymous"; // Usuario anónimo
        String pass = "anonymous";

        FTPClient ftpClient = new FTPClient();

        try {
            // Conexión al servidor
            ftpClient.connect(server, port);
            boolean login = ftpClient.login(user, pass);

            if (login) {
                System.out.println("Conexión exitosa al servidor FTP: " + server);

                // Listar directorios en el directorio raíz
                String[] files = ftpClient.listNames();
                if (files != null && files.length > 0) {
                    System.out.println("Contenido del directorio raíz:");
                    for (String file : files) {
                        System.out.println(" - " + file);
                    }
                } else {
                    System.out.println("No se encontraron archivos o directorios en el directorio raíz.");
                }

                // Desconexión
                ftpClient.logout();
                System.out.println("Desconectado del servidor FTP.");
            } else {
                System.out.println("No se pudo iniciar sesión en el servidor FTP.");
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
