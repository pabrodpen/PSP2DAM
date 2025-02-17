
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Cliente39 {
    private static final String HOST = "localhost";
    private static final int PUERTO = 3009;
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    public Cliente39() {
        configurarGUI();
    }

    private void configurarGUI() {
        // Crear ventana principal
        JFrame ventana = new JFrame("VENTANA DEL CLIENTE - EJERCICIO 5");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(400, 200);
        ventana.setLayout(new BorderLayout());

        // Panel superior para entrada de texto
        JPanel panelSuperior = new JPanel(new BorderLayout());
        JLabel etiquetaEntrada = new JLabel("Escribe texto:");
        etiquetaEntrada.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField campoTexto = new JTextField();
        campoTexto.setFont(new Font("Arial", Font.PLAIN, 14));
        panelSuperior.add(etiquetaEntrada, BorderLayout.NORTH);
        panelSuperior.add(campoTexto, BorderLayout.CENTER);

        // Panel para el área de texto de salida
        JLabel etiquetaSalida = new JLabel();
        etiquetaSalida.setFont(new Font("Arial", Font.PLAIN, 14));
        etiquetaSalida.setHorizontalAlignment(SwingConstants.LEFT);
        panelSuperior.add(etiquetaSalida, BorderLayout.SOUTH);

        // Panel lateral para botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 5, 5));
        JButton btnEnviar = new JButton("Enviar");
        JButton btnSalir = new JButton("Salir");
        JButton btnLimpiar = new JButton("Limpiar");

        btnEnviar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSalir.setFont(new Font("Arial", Font.PLAIN, 14));
        btnLimpiar.setFont(new Font("Arial", Font.PLAIN, 14));

        panelBotones.add(btnEnviar);
        panelBotones.add(btnSalir);
        panelBotones.add(btnLimpiar);

        ventana.add(panelSuperior, BorderLayout.CENTER);
        ventana.add(panelBotones, BorderLayout.EAST);

        // Acción para el botón Enviar
        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mensaje = campoTexto.getText();
                if (!mensaje.isEmpty()) {
                    enviarMensaje(mensaje);
                    campoTexto.setText("");
                }
            }
        });

        // Acción para el botón Limpiar
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoTexto.setText("");
                etiquetaSalida.setText("");
            }
        });

        // Acción para el botón Salir
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje("*");
                cerrarConexion();
                System.exit(0);
            }
        });

        ventana.setVisible(true);

        conectarServidor(etiquetaSalida);
    }

    private void conectarServidor(JLabel etiquetaSalida) {
        try {
            socket = new Socket(HOST, PUERTO);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            new Thread(() -> {
                try {
                    String respuesta;
                    while ((respuesta = entrada.readLine()) != null) {
                        etiquetaSalida.setText("Servidor: " + respuesta);
                    }
                } catch (IOException e) {
                    etiquetaSalida.setText("Conexión cerrada.");
                }
            }).start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enviarMensaje(String mensaje) {
        if (salida != null) {
            salida.println(mensaje);
        }
    }

    private void cerrarConexion() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Cliente39::new);
    }
}