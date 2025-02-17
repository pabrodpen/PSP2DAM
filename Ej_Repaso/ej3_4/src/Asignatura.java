public class Asignatura {
    int id;
    String nombre;

    public Asignatura(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
