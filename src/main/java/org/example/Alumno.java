package org.example;

public class Alumno {
    private final String nombre;
    private int edad;
    private final float nota;
    private final boolean aprobado;

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public float getNota() {
        return nota;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public Alumno(String nombre, int edad, float nota) {
        this.nombre = nombre;
        this.edad = edad;
        this.nota = nota;
        aprobado = !(nota < 5);
        if (edad>120) this.edad=120;
        else if(edad<0) this.edad=1;
    }

}