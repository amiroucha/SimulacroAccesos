package org.example;

public class Alumno {
    private String nombre;
    private int edad;
    private float nota;
    private boolean aprobado;

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
    public Alumno(String nombre, int edad, float nota) {
        this.nombre = nombre;
        this.edad = edad;
        this.nota = nota;
        if (nota < 5) {
            aprobado = false;
        }else{
            aprobado = true;
        }
    }

}