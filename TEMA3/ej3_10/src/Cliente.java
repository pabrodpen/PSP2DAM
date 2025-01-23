import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Cliente {
    private static Socket cliente;
    private static PrintWriter salida;
    private static BufferedReader entrada;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Adivina el Número");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);

        // Conectar al servidor
        try {
            String host = "localhost";
            int puerto = 6000;

            cliente = new Socket(host, puerto);
            salida = new PrintWriter(cliente.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            // Leer mensaje inicial del servidor
            String mensajeInicial = entrada.readLine();
            JOptionPane.showMessageDialog(frame, mensajeInicial, "Servidor", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error al conectar con el servidor: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel labelNumero = new JLabel("Introduce un número entre 1 y 25:");
        labelNumero.setBounds(10, 20, 300, 25);
        panel.add(labelNumero);

        JTextField campoNumero = new JTextField(20);
        campoNumero.setBounds(10, 50, 300, 25);
        panel.add(campoNumero);

        JButton botonEnviar = new JButton("Enviar");
        botonEnviar.setBounds(10, 80, 80, 25);
        panel.add(botonEnviar);

        JButton botonSalir = new JButton("Salir");
        botonSalir.setBounds(100, 80, 80, 25);
        panel.add(botonSalir);

        JTextArea areaRespuesta = new JTextArea();
        areaRespuesta.setBounds(10, 120, 360, 120);
        areaRespuesta.setEditable(false);
        panel.add(areaRespuesta);

        // Acción del botón "Enviar"
        botonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numero = campoNumero.getText();
                salida.println(numero);

                try {
                    String respuesta = entrada.readLine();
                    areaRespuesta.append("Servidor: " + respuesta + "\n");

                    // Si se acierta, deshabilitar el botón y cerrar la conexión
                    if (respuesta.contains("¡Correcto!")) {
                        campoNumero.setEditable(false);
                        botonEnviar.setEnabled(false);
                        salida.close();
                        entrada.close();
                        cliente.close();
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
                    salida.close();
                    entrada.close();
                    cliente.close();
                } catch (IOException ex) {
                    System.out.println("Error al cerrar la conexión: " + ex.getMessage());
                }
                System.exit(0);
            }
        });
    }
}
