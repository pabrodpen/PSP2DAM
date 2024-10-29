import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContadorApplet extends Applet implements ActionListener {
    private MyHilo hilo1=new MyHilo();
    private MyHilo hilo2=new MyHilo();

    private long cont1 = 0, cont2 = 0;
    private boolean parar1 = true, parar2 = true;
    private Font fuente;

    // Botones para ambos contadores
    private Button reanudar1, parar1Button, reanudar2, parar2Button,finalizarProceso;




    @Override
    public void init() {
        setBackground(Color.YELLOW);
        fuente = new Font("Verdana", Font.BOLD, 26);

        // Crear hilos de contadores, sin iniciar aún
        hiloContador1 = new HiloContador(cont1);
        hiloContador2 = new HiloContador(cont2);

        // Iniciar los hilos
        hiloContador1.start();
        hiloContador2.start();

        // Botones de inicio y paro para el contador 1
        iniciar1 = new Button("Iniciar contador 1");
        iniciar1.addActionListener(this);
        add(iniciar1);

        parar1Button = new Button("Parar contador 1");
        parar1Button.addActionListener(this);
        add(parar1Button);

        // Botones de inicio y paro para el contador 2
        iniciar2 = new Button("Iniciar contador 2");
        iniciar2.addActionListener(this);
        add(iniciar2);

        parar2Button = new Button("Parar contador 2");
        parar2Button.addActionListener(this);
        add(parar2Button);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
