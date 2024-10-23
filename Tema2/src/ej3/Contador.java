package ej3;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Contador extends Applet implements ActionListener {
    private HiloContador hiloContador;
    long cont=0;
    private boolean parar;
    private Font fuente;
    private Button b1,b2;

    class HiloContador extends Thread{


        @Override
        public void run() {
            parar=false;
            Thread hiloActual=Thread.currentThread();
            while(hiloContador==hiloActual && !parar){
                try{
                    Thread.sleep(300);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                repaint();
                cont++;
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1){//pulso iniciar contador o Continuar
            b1.setLabel("Continuar");
            if(hiloContador!=null && hiloContador.isAlive()){
                //si el hilo no esta corriendo no hagan nada
            }else{
                //creo hilo la primera vez y cuando finaliza el metodo run
                hiloContador=new HiloContador();
                hiloContador.start();
            }
        }else if(e.getSource()==b2){//Para contador
            parar=true;
        }

    }

    public void stop(){
        hiloContador=null;
    }

    public void paint(Graphics g){
        g.clearRect(0,0,400,400);
        g.setFont(fuente);
        g.drawString(Long.toString((long) cont),80,100);
    }

    public void start(){

    }
    public void init(){
        setBackground(Color.YELLOW);
        add(b1=new Button("Iniciar contador"));
        b1.addActionListener(this);
        add(b2=new Button("Parar contador"));
        b1.addActionListener(this);
        fuente=new Font("Verdana",Font.BOLD,26);

        // Inicializo los contadores con valores diferentes
        cont = 0;     // Comienza en 0

        // Creo los hilos con valores iniciales
        hiloContador = new HiloContador();
        hiloContador.start();
    }
}
