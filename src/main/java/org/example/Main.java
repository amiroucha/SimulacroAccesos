package org.example;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
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
/*

        Alumno[] infoAlumnos = new Alumno[5];//guardo 5 alumnos max
        System.out.println("Introduce la información de 5 alumnos.");
        for (int i=0; i < 5; i++)
        {
            try {
                System.out.println("Información del alumno "+ (i+1) + ":");
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
            }catch (InputMismatchException e)
            {
                System.err.println("Error de formato : "+e.getMessage());
            }

        }

        //guardar los datos en alumnosDAT, punto 2
        try(RandomAccessFile rafAlumosDat= new RandomAccessFile(alumnosDAT, "rw")){
            for(int i=0; i<5; i++)
            {
                rafAlumosDat.writeUTF(infoAlumnos[i].getNombre());
                rafAlumosDat.writeInt(infoAlumnos[i].getEdad());
                rafAlumosDat.writeFloat(infoAlumnos[i].getNota());
                rafAlumosDat.writeBoolean(infoAlumnos[i].isAprobado());
                //rafAlumosDat.seek(alumnosDAT.length());//me posiciono al final
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/

        //punto 3, cambiar notas y variable aprobadoo
        try(RandomAccessFile rafNotasActualiza= new RandomAccessFile(alumnosDAT, "rw")) {
            int contador=0;
            /*----------------------------------------------------------------------------------------------
            ME SALTA EXCEPCION AQUI. CREO QUE ES EL READUTF
            * ----------------------------------------------------------------------------------------------*/
            //while (rafNotasActualiza.getFilePointer() < rafNotasActualiza.length()) {//mientras que el fichero no llegue a su final
            while(contador<5) {
                //aqui el puntero esta a 0
                System.out.println("Nombre: "+rafNotasActualiza.readUTF()); //leo el nombre, el puntero se mueve
                System.out.println("Edad: "+rafNotasActualiza.readInt()); //leo la edad

                long posicionNota = rafNotasActualiza.getFilePointer();
                float notaActu = rafNotasActualiza.readFloat(); //leo la nota

                long posicionAprobado = rafNotasActualiza.getFilePointer();
                boolean aprobadoAct = rafNotasActualiza.readBoolean();

                System.out.println("Nota sin actualizar: " + notaActu);
                System.out.println("Aprobado sin actualizar : " + aprobadoAct);

                if (notaActu <= 9.0f) notaActu += 1.0f;
                else notaActu = 10.0f;

                rafNotasActualiza.seek(posicionNota); //me coloco en la posision de la nota
                rafNotasActualiza.writeFloat(notaActu); //escribo la nueva nota
                System.out.println("Nota actualizada: " + notaActu);

                //compruebo si necesito cambiar el booleano aprobado
                if (notaActu<5) aprobadoAct = false;
                else aprobadoAct = true;
                rafNotasActualiza.seek(posicionAprobado);
                rafNotasActualiza.writeBoolean(aprobadoAct);
                System.out.println("Aprobado actualizado: " + aprobadoAct);
                contador++;
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: "+e.getMessage());
        }


        //exportar datos en texto plano, punto 4
        /*File alumnosTXT = new File("./tarea1/ficheros/alumnos.txt");
        try{
            alumnosTXT.createNewFile();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/
    }
}
