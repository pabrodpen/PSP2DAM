package tarea2_7;
public class Main {
    public static void main(String[] args) {
        Hilo h1=new Hilo("1");
        Hilo h2=new Hilo("2");
        Hilo h3=new Hilo("3");
        Hilo h4=new Hilo("4");
        Hilo h5=new Hilo("5");

        for(int i=0;i<5000;i++){
            h1.run();
            h2.run();
            h3.run();
            h4.run();
            h5.run();
        }
    }
}
