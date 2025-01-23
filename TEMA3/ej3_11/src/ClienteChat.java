import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClienteChat {
    private static final String HOST = "localhost";
    private static final int PUERTO = 12345;

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Cliente Chat");
        ventana.setSize(500, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaMensajes);

        JTextField campoTexto = new JTextField();
        JButton botonEnviar = new JButton("Enviar");
        JButton botonSalir = new JButton("Salir");

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(campoTexto, BorderLayout.CENTER);
        panelInferior.add(botonEnviar, BorderLayout.EAST);
        panelInferior.add(botonSalir, BorderLayout.WEST);

        ventana.add(scroll, BorderLayout.CENTER);
        ventana.add(panelInferior, BorderLayout.SOUTH);

        ventana.setVisible(true);

        try (Socket socket = new Socket(HOST, PUERTO);
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Pedir el nombre del usuario
            String nombre = JOptionPane.showInputDialog("Introduce tu nombre:");
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "El nombre no puede estar vacío. Saliendo...");
                return;
            }
            salida.println(nombre);

            // Hilo para recibir mensajes
            Thread receptor = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {
                        areaMensajes.append(mensaje + "\n");
                    }
                } catch (IOException e) {
                    areaMensajes.append("Desconectado del servidor.\n");
                }
            });
            receptor.start();

            // Acción del botón enviar
            botonEnviar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String mensaje = campoTexto.getText().trim();
                    if (!mensaje.isEmpty()) {
                        salida.println(mensaje);
                        campoTexto.setText("");
                        if (mensaje.equalsIgnoreCase("SALIR")) {
                            try {
                                socket.close();
                            } catch (IOException ex) {
                                System.err.println("Error al cerrar el socket: " + ex.getMessage());
                            }
                            System.exit(0);
                        }
                    }
                }
            });

            // Acción del botón salir
            botonSalir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    salida.println("SALIR");
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        System.err.println("Error al cerrar el socket: " + ex.getMessage());
                    }
                    System.exit(0);
                }
            });

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
