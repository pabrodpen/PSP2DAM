package org.example;

import java.util.Arrays;

public class Profesor {
    //id nombre, array de asignaturas, nonbre de la especialidad, constructor y toString
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
        return "Profesor{" +
                "idProfesor=" + idProfesor +
                ", nombre='" + nombre + '\'' +
                ", asignaturas=" + Arrays.toString(asignaturas) +
                ", especialidad=" + especialidad +
                '}';
    }
}