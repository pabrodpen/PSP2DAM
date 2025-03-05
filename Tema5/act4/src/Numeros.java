import java.io.Serializable;

public class Numeros implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numero;
    private long cuadrado;
    private long cubo;

    public Numeros(int numero) {
        this.numero = numero;
        this.cuadrado = (long) numero * numero;
        this.cubo = (long) numero * numero * numero;
    }

    public Numeros() {}

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public long getCuadrado() {
        return cuadrado;
    }

    public long getCubo() {
        return cubo;
    }
}
