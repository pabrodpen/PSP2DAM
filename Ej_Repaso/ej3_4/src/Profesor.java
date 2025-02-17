import java.util.Arrays;

public class Profesor {
    int idProfesor;
    String nombre;
    Asignatura[] asignaturas;
    Especialidad especialidad;

    public Profesor(int idProfesor, String nombre, Asignatura[] asignaturas, Especialidad especialidad) {
        this.idProfesor = idProfesor;
        this.nombre = nombre;
        this.asignaturas = asignaturas;
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append("Nombre: ").append(nombre).append("\n");
        info.append("Especialidad: ").append(especialidad).append("\n");
        Arrays.stream(asignaturas).forEach(asig -> info.append("Asignatura: ").append(asig).append("\n"));
        return info.toString();
    }
}
