package ejercicio_complementario_tarea2_4.src;

public class MyHilo extends Thread {
    private SolicitaSuspender suspender = new SolicitaSuspender();
    private boolean running = true;  // Controla el fin del hilo
    private int contador = 0;        // Variable contador
    private ContadorApplet applet;

    public MyHilo(ContadorApplet applet) {
        this.applet = applet; // Asignamos la referencia del Applet a la variable 'applet'
    }


    // Petición de suspender el hilo
    public void suspende() {
        suspender.set(true);
    }

    // Petición de continuar el hilo
    public void reanuda() {
        suspender.set(false);
    }

    // Método para obtener el valor del contador
    public int getContador() {
        return contador;
    }

    // Método para detener el hilo
    public void finalizar() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (running) {
                suspender.esperandoParaReanudar();  // Espera si el hilo está suspendido
                // Incrementa y muestra el contador
                contador++;
                // Pausa de un segundo para ver los números
                applet.repaint(); // Llama a repaint para actualizar el Applet
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
