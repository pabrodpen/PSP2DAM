public class Main {
    public static void main(String[] args) {

        String servidor = "localhost"; // Servidor FTP
        String usuario = JOptionPane.showInputDialog("Ingrese el usuario:");
        String contraseña = JOptionPane.showInputDialog("Ingrese la contraseña:");

        FTPClient cliente = new FTPClient();

        try {
            // Conectar al servidor FTP
            System.out.println("Conectando a " + servidor + "...");
            cliente.connect(servidor);
            cliente.enterLocalPassiveMode(); // Modo pasivo para evitar bloqueos de firewall
            boolean login = cliente.login(usuario, contraseña);

            if (login) {
                System.out.println("Login exitoso.");
                cliente.setFileType(FTP.BINARY_FILE_TYPE);

                // Obtener la lista de archivos en el directorio raíz del usuario
                FTPFile[] archivos = cliente.listFiles();
                if (archivos.length == 0) {
                    JOptionPane.showMessageDialog(null, "No hay archivos en el servidor.");
                    cliente.logout();
                    cliente.disconnect();
                    return;
                }

                // Crear lista de archivos para que el usuario elija
                String[] nombresArchivos = new String[archivos.length];
                for (int i = 0; i < archivos.length; i++) {
                    if (archivos[i].isFile()) {
                        nombresArchivos[i] = archivos[i].getName();
                    }
                }

                // Mostrar la lista en un cuadro de diálogo
                String archivoSeleccionado = (String) JOptionPane.showInputDialog(
                        null,
                        "Seleccione un archivo para descargar:",
                        "Lista de archivos",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        nombresArchivos,
                        nombresArchivos[0]
                );

                if (archivoSeleccionado != null) {
                    // Abrir JFileChooser para elegir la carpeta de descarga
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Seleccione la ubicación para guardar el archivo");
                    fileChooser.setSelectedFile(new File(archivoSeleccionado)); // Nombre del archivo por defecto

                    int userSelection = fileChooser.showSaveDialog(null);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File archivoDestino = fileChooser.getSelectedFile();
                        String rutaLocal = archivoDestino.getAbsolutePath();

                        // Descargar el archivo
                        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(rutaLocal));
                        boolean success = cliente.retrieveFile(archivoSeleccionado, outputStream);
                        outputStream.close();

                        if (success) {
                            JOptionPane.showMessageDialog(null, "Archivo descargado correctamente: " + rutaLocal);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo descargar el archivo.");
                        }
                    }
                }

                // Cerrar sesión y desconectar
                cliente.logout();
                cliente.disconnect();
            } else {
                JOptionPane.showMessageDialog(null, "Error en la autenticación.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor.");
        }
    }
}