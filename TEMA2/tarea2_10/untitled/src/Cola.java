public class Cola {
    private int numero;
    private boolean disponible=false;//inicialmente cola vacia

    public synchronized int get(){
        if(disponible){//hay numero en la cola
            disponible=false;//se pone cola vacia
            return numero;//se devuelve
        }
        return -1;//no hay numero disponible
    }

    public synchronized void put(int valor){
        numero=valor;
        disponible=true;//cola llena
    }
}
