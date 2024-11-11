public class Hilo implements Runnable{

    static int contador = 0;
    String nombre;
    public Hilo(String nombre) {
        this.nombre=nombre;
    }

    public void mostrar(){
        System.out.println("Valor del contador "+nombre+":"+contador);
    }

    @Override
    public void run() {
            contador++;
            try{
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mostrar();
        }
    }

