package act3;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;

    public class Main {

    public static void main(String[] args) {
        try {
            // Hacemos el login con usuario y contraseña
            String servFTP = "localhost";
            String usuario = JOptionPane.showInputDialog(null, "Usuario:", "Conexión al servidor", JOptionPane.QUESTION_MESSAGE);
            String password = JOptionPane.showInputDialog(null, "Contraseña:", "Conexión al servidor", JOptionPane.QUESTION_MESSAGE);

            // Conexión al servidor
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(servFTP);
            ftpClient.enterLocalPassiveMode();
            System.out.println("Conectando al servidor: " + servFTP);
            boolean login = ftpClient.login(usuario, password);
            if (login) {
                JOptionPane.showMessageDialog(null, "Login exitoso.", "Conexión exitosa", JOptionPane.INFORMATION_MESSAGE);
                ftpClient.changeWorkingDirectory("/"+usuario);
            } else {
                JOptionPane.showMessageDialog(null, "Error en el login.", "ERROR", JOptionPane.ERROR_MESSAGE);
                ftpClient.disconnect();
                System.exit(1);
            }

            //Obtenemos los archivos del directorio con el metodo listFiles
            FTPFile[] archivos = ftpClient.listFiles();
            String listaArchivos[]= new String[archivos.length];
            for (int i = 0; i < archivos.length; i++) {
                //si es un archivo
                if (archivos[i].isFile()) {
                    listaArchivos[i] = archivos[i].getName();
                }
            }

            //Interfaz para confirmar la descarga o cancelarla
            JFrame ventana = new JFrame("Archivos obtenidos");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(400, 300);

            JList<String> mostrarArchivos = new JList<>(listaArchivos);
            JScrollPane scrollPane = new JScrollPane(mostrarArchivos);
            JButton botonDescargar = new JButton("Descargar archivos");
            JButton botonSalir = new JButton("Cancelar");

            JPanel panelBotones = new JPanel();
            panelBotones.add(botonDescargar);
            panelBotones.add(botonSalir);

            ventana.add(scrollPane, "Center");
            ventana.add(panelBotones, "South");
            ventana.setVisible(true);

            //Descargamos el archivo seleccionado
            botonDescargar.addActionListener(e -> {
                String archivoSeleccionado = mostrarArchivos.getSelectedValue();
                if (archivoSeleccionado != null) {
                    JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Ubicacion para guardar archivo");
                    fileChooser.setSelectedFile(new java.io.File(archivoSeleccionado));

                    int userSelection = fileChooser.showSaveDialog(null);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        try (FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile())) {
                            boolean exito = ftpClient.retrieveFile(archivoSeleccionado, fos);
                            if (exito) {
                                JOptionPane.showMessageDialog(null, "Archivo descargado correctamente: " + archivoSeleccionado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "No se ha podido realizar la descarga.", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    //en caso de que no se haya seleccionando ningun archivo
                    JOptionPane.showMessageDialog(null, "Es necesario seleccionar un archivo.", "Warning Message", JOptionPane.WARNING_MESSAGE);
                }
            });

            //Boton para salir
            botonSalir.addActionListener(e -> {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}