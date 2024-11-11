package tarea2_6;

public class Hilo1 extends Thread{
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
