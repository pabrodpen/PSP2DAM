package act3;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cliente FTP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Panel para ingresar credenciales
        JPanel credentialsPanel = new JPanel();
        credentialsPanel.setLayout(new GridLayout(2, 2));
        credentialsPanel.add(new JLabel("Usuario:"));
        JTextField userField = new JTextField();
        credentialsPanel.add(userField);
        credentialsPanel.add(new JLabel("Contraseña:"));
        JPasswordField passField = new JPasswordField();
        credentialsPanel.add(passField);
        JButton connectButton = new JButton("Conectar");
        credentialsPanel.add(connectButton);

        frame.add(credentialsPanel, BorderLayout.NORTH);

        // Área para mostrar los archivos disponibles
        DefaultListModel<String> fileListModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(fileListModel);
        frame.add(new JScrollPane(fileList), BorderLayout.CENTER);

        // Botones para descargar y salir
        JPanel buttonsPanel = new JPanel();
        JButton downloadButton = new JButton("Descargar");
        JButton exitButton = new JButton("Salir");
        buttonsPanel.add(downloadButton);
        buttonsPanel.add(exitButton);
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        FTPClient ftpClient = new FTPClient();

        // Acción para conectar al servidor FTP
        connectButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            try {
                ftpClient.connect("localhost", 21); // Cambia "localhost" si usas otro servidor
                boolean login = ftpClient.login(user, pass);

                if (login) {
                    JOptionPane.showMessageDialog(frame, "Conexión exitosa.");
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

                    // Listar archivos y directorios
                    fileListModel.clear();
                    FTPFile[] files = ftpClient.listFiles();
                    for (FTPFile file : files) {
                        if (file.isFile()) {
                            fileListModel.addElement(file.getName());
                        } else if (file.isDirectory()) {
                            fileListModel.addElement("[Directorio] " + file.getName());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Error de inicio de sesión.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error al conectar: " + ex.getMessage());
            }
        });

        // Descargarmos el archivo seleccionado
        downloadButton.addActionListener(e -> {
            String selectedFile = fileList.getSelectedValue();
            if (selectedFile == null || selectedFile.startsWith("[Directorio]")) {
                JOptionPane.showMessageDialog(frame, "Selecciona un archivo para descargar.");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona dónde guardar el archivo");
            fileChooser.setSelectedFile(new java.io.File(selectedFile));
            int userSelection = fileChooser.showSaveDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                try (FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile())) {
                    boolean success = ftpClient.retrieveFile(selectedFile, fos);
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Archivo descargado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error al descargar el archivo.");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        //Salimos
        exitButton.addActionListener(e -> {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        });

        frame.setVisible(true);
    }
}
