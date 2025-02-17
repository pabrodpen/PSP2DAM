package tarea2_8;

public class GestorSaldo extends Thread{
    Saldo saldo;
    String usuario;
    int cantidad;

    public GestorSaldo(String usuario, int cantidad,Saldo saldo) {
        this.usuario=usuario;
        this.cantidad=cantidad;
        this.saldo=saldo;
    }

    @Override
    public void run() {
        saldo.addSaldo(usuario,saldo.getSaldo());
    }
}
