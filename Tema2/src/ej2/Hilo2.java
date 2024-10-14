package ej2;

public class Hilo2 implements Runnable{

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
