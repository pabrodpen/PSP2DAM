package org.example;

public class Especialidad {
    //id nombre, constructor y toString de la clase
    int id;
    String nombre;

    public Especialidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
