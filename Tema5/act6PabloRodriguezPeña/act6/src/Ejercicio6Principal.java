import java.security.Principal;

public class Ejercicio6Principal implements Principal, java.io.Serializable {
    private String name;

    // Crea un EjemploPrincipal con el nombre especificado
    public Ejercicio6Principal(String name) {
        if (name == null) {
            throw new NullPointerException("Entrada nula");
        }
        this.name = name;
    }

    // Devuelve el nombre del Principal
    public String getName() {
        return name;
    }

    // Compara el objeto especificado con el Principal actual
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof Ejercicio6Principal)) return false;
        Ejercicio6Principal that = (Ejercicio6Principal) o;
        return this.getName().equals(that.getName());
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return name;
    }
}
