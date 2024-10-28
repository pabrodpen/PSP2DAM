package ej2;

public class Hilo1 implements Runnable{
    @Override
    public void run() {
        try{
            while(true){
                System.out.println("TIC");
                Thread.sleep(750);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
