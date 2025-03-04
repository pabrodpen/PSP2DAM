public class Main {
    public static void main(String[] args) {

        String servidor = "localhost"; // Servidor FTP local
        String user = "usuario";       // Usuario FTP
        String pasw = "contraseña";    // Contraseña FTP

        FTPClient cliente = new FTPClient();
        JFileChooser fileChooser = new JFileChooser();

        try {
            // Conexión al servidor
            System.out.println("Conectándose a " + servidor + "...");
            cliente.connect(servidor);
            cliente.enterLocalPassiveMode();

            // Autenticación
            boolean login = cliente.login(user, pasw);

            if (login) {
                System.out.println("Login correcto.");
                cliente.setFileType(FTP.BINARY_FILE_TYPE);

                // Abrir el selector de archivos
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File archivoSeleccionado = fileChooser.getSelectedFile();
                    String nombreArchivo = archivoSeleccionado.getName();

                    // Stream para leer el archivo seleccionado
                    FileInputStream inputStream = new FileInputStream(archivoSeleccionado);
                    BufferedInputStream in = new BufferedInputStream(inputStream);

                    // Subir archivo al directorio raíz del usuario
                    if (cliente.storeFile(nombreArchivo, in)) {
                        System.out.println("Archivo '" + nombreArchivo + "' subido correctamente.");
                    } else {
                        System.out.println("No se pudo subir el archivo.");
                    }

                    // Cerrar flujo
                    in.close();
                } else {
                    System.out.println("No se seleccionó ningún archivo.");
                }

                // Cerrar sesión y desconectar
                cliente.logout();
                cliente.disconnect();
            } else {
                System.out.println("No se pudo iniciar sesión en el servidor FTP.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}