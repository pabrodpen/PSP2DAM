import java.io.IOException;

public class Ej1_2 {
    public static void main(String[] args) {
        // Información de conexión al servidor FTP
        String servidor = "ftp.rediris.es"; // Dirección del servidor FTP
        int puerto = 21; // Puerto estándar para FTP
        String usuario = "anonimo"; // Usuario predeterminado para acceso anónimo
        String contra = ""; // Contraseña vacía para usuarios anónimos

        FTPClient FTPClient = new FTPClient();

        try {
            // conexión con el servidor
            FTPClient.connect(servidor, puerto);
            int codigoRespuesta = FTPClient.getReplyCode();

            if (!FTPClient.login(usuario, contra)) {
                System.out.println("Error: Fallo en la autenticación.");
                return;
            }

            System.out.println("Conexión exitosa al servidor FTP.");

            // Configuración del modo y tipo de archivo
            FTPClient.enterLocalPassiveMode();
            FTPClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Obtener y mostrar el contenido del directorio raíz
            System.out.println("Contenido del directorio raíz:");
            FTPFile[] listaArchivos = FTPClient.listFiles("/");
            if (listaArchivos != null && listaArchivos.length > 0) {
                for (FTPFile archivo : listaArchivos) {
                    System.out.println(archivo.getName());
                }
            } else {
                System.out.println("El directorio raíz está vacío.");
            }

            // Cerrar la sesión y desconectar
            FTPClient.logout();
            FTPClient.disconnect();
            System.out.println("Sesión cerrada y desconexión realizada correctamente.");
        } catch (IOException e) {
            System.out.println("Se produjo un error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
