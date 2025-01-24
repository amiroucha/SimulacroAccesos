package org.example;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        String nombre;
        int edad;
        float nota;

        //Crear ficheros
        File carpeta = new File("./tarea1/ficheros");
        carpeta.mkdirs();
        File alumnosDAT = new File("./tarea1/ficheros/alumnos.dat");
        try {
            alumnosDAT.createNewFile();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        Alumno[] infoAlumnos = new Alumno[5];//guardo 5 alumnos max
        System.out.println("Introduce la información de 5 alumnos.");
        for (int i=0; i < 5; i++)
        {
            System.out.println("Información del alumno "+ i + ":");
            System.out.println("Nombre:");
            nombre = leer.nextLine();
            System.out.println("Edad:");
            edad = leer.nextInt();
            leer.nextLine();
            System.out.println("Nota:");
            nota = leer.nextFloat();
            leer.nextLine();
            //guardo la informacion
            Alumno alumno = new Alumno(nombre, edad, nota);
            infoAlumnos[i] = alumno;
        }

        //guardar los datos en alumnosDAT, punto 2
        try(RandomAccessFile rafAlumosDat= new RandomAccessFile(alumnosDAT, "rw")){
            for(int i=0; i<5; i++)
            {
                rafAlumosDat.writeUTF(infoAlumnos[i].getNombre());
                rafAlumosDat.writeUTF(String.valueOf(infoAlumnos[i].getEdad()));
                rafAlumosDat.writeUTF(String.valueOf(infoAlumnos[i].getNota()));
                rafAlumosDat.writeUTF(infoAlumnos[i].isAprobado());
                rafAlumosDat.seek(alumnosDAT.length());//me posiciono al final
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //exportar datos en texto plano, punto 4
        File alumnosTXT = new File("./tarea1/ficheros/alumnos.txt");
        try {
            alumnosTXT.createNewFile();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
