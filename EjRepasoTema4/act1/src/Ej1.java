import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

public class Ej1 {
    public static void main(String[] args) {
        String server = "127.0.0.1";  // IP del servidor (FileZilla Server)
        int port = 21;  // Puerto FTP (21 por defecto)
        String user = "usuario";  // Cambia por tu usuario FTP
        String pass = "Agosto.290802";  // Cambia por tu contraseña FTP

        FTPClient ftpClient = new FTPClient();
        try {
            // Conectar al servidor
            ftpClient.connect(server, port);
            boolean login = ftpClient.login(user, pass);

            if (login) {
                System.out.println("Conectado al servidor FTP");

                // Listar archivos en el directorio raíz(en este caso /home/usuario/ftp
                String[] archivos = ftpClient.listNames();
                if (archivos != null) {
                    for (String archivo : archivos) {
                        System.out.println("Archivo: " + archivo);
                    }
                }

                // Cerrar sesión y desconectar
                ftpClient.logout();
                ftpClient.disconnect();
                System.out.println("Desconectado del servidor FTP");
            } else {
                System.out.println("No se pudo conectar al servidor FTP");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
