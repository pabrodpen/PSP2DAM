import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cliente {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cliente - Actividad 5");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel labelTexto = new JLabel("Escribe texto:");
        labelTexto.setBounds(10, 20, 300, 25);
        panel.add(labelTexto);

        JTextField campoTexto = new JTextField(20);
        campoTexto.setBounds(10, 50, 300, 25);
        panel.add(campoTexto);

        JTextArea areaRespuesta = new JTextArea();
        areaRespuesta.setBounds(10, 120, 360, 120);
        areaRespuesta.setEditable(false);
        panel.add(areaRespuesta);

        JButton botonEnviar = new JButton("Enviar");
        botonEnviar.setBounds(10, 80, 80, 25);
        panel.add(botonEnviar);

        JButton botonSalir = new JButton("Salir");
        botonSalir.setBounds(100, 80, 80, 25);
        panel.add(botonSalir);

        // Conexión con el servidor
        try {
            String host = "localhost";
            int puerto = 44444;
            Socket cliente = new Socket(host, puerto);

            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            // Acción del botón "Enviar"
            botonEnviar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String texto = campoTexto.getText();
                    salida.println(texto);

                    try {
                        String respuesta = entrada.readLine();
                        areaRespuesta.append(respuesta + "\n");

                        // Si el cliente envía "*", cerrar conexión
                        if (texto.equals("*")) {
                            salida.close();
                            entrada.close();
                            cliente.close();
                            System.exit(0);
                        }
                    } catch (IOException ex) {
                        areaRespuesta.append("Error al comunicar con el servidor.\n");
                    }
                }
            });

            // Acción del botón "Salir"
            botonSalir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        salida.println("*"); // Enviar desconexión al servidor
                        salida.close();
                        entrada.close();
                        cliente.close();
                    } catch (IOException ex) {
                        System.err.println("Error al cerrar el cliente: " + ex.getMessage());
                    }
                    System.exit(0);
                }
            });

        } catch (IOException e) {
            JOptionPane.showMessageDialog(panel, "Error al conectar con el servidor: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
