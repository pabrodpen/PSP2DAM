package ej1;

public class Hilo2 extends Thread{

    @Override
    public void run() {
        try{
            while(true){
                System.out.println("TAC");
                Thread.sleep(750);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
