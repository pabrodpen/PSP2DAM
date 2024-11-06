import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContadorApplet extends Applet implements ActionListener {
    private MyHilo hilo1;
    private MyHilo hilo2;

    private Font fuente;

    // Botones para ambos contadores
    private Button reanudar1, parar1Button, reanudar2, parar2Button,finalizarProcesoButton,comenzarProcesoButton;




    @Override
    public void init() {
        setBackground(Color.YELLOW);
        fuente = new Font("Verdana", Font.BOLD, 26);

        // Crear hilos de contadores, sin iniciar aún
        hilo1 = new MyHilo(this);
        hilo2 = new MyHilo(this);

        // Iniciar los hilos pero en pausa para esperar al boton comenzarProceso
        hilo1.start();
        hilo2.start();
        hilo1.suspende();
        hilo2.suspende();

        // Botones de inicio y paro para el contador 1
        reanudar1 = new Button("Continuar contador 1");
        reanudar1.addActionListener(this);
        add(reanudar1);

        parar1Button = new Button("Parar contador 1");
        parar1Button.addActionListener(this);
        add(parar1Button);

        // Botones de inicio y paro para el contador 2
        reanudar2 = new Button("Continuar contador 2");
        reanudar2.addActionListener(this);
        add(reanudar2);

        parar2Button = new Button("Parar contador 2");
        parar2Button.addActionListener(this);
        add(parar2Button);

        comenzarProcesoButton=new Button("Comenzar proceso");
        comenzarProcesoButton.addActionListener(this);
        add(comenzarProcesoButton);

        finalizarProcesoButton=new Button("Finalizar proceso");
        finalizarProcesoButton.addActionListener(this);
        add(finalizarProcesoButton);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reanudar1) {
            hilo1.reanuda(); // Continúa el contador 1
        } else if (e.getSource() == parar1Button) {
            hilo1.suspende(); // Detiene el contador 1
        } else if (e.getSource() == reanudar2) {
            hilo2.reanuda(); // Continúa el contador 2
        } else if (e.getSource() == parar2Button) {
            hilo2.suspende(); // Detiene el contador 2
        } else if(e.getSource()==finalizarProcesoButton){
            hilo1.finalizar();
            hilo2.finalizar();
            parar1Button.setEnabled(false);
            parar2Button.setEnabled(false);
            reanudar1.setEnabled(false);
            reanudar2.setEnabled(false);
            System.out.println("Valor del contador 1:"+hilo1.getContador());
            System.out.println("Valor del contador 2:"+hilo2.getContador());
        } else if(e.getSource()==comenzarProcesoButton){
            hilo1.reanuda();
            hilo2.reanuda();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, 400, 400);
        g.setFont(fuente);
        g.drawString("Contador 1: " + hilo1.getContador(), 80, 100);
        g.drawString("Contador 2: " + hilo2.getContador(), 80, 150);
    }
}
