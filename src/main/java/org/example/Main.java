package org.example;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    /*
    * Tengo el main estructurado en métodos
    * cada apartado es un método diferente
    * */
    public static int NUMALUMNOS =5;
    public static void main(String[] args) {

        //crear alumnos dat
        File carpeta = new File("./tarea1/ficheros");
        carpeta.mkdirs();
        File alumnosDAT = new File("./tarea1/ficheros/alumnos.dat");
        try {
            alumnosDAT.createNewFile();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //punto 1, pido los datos de los alumnos
        Alumno[] infoAlumnos = introducirAlumnos();

        //punto 2, los inserto en el archivo alumnos.dat, le paso el array y el archivo
        guardarAlumnosDat(infoAlumnos, alumnosDAT);

        //punto 3, cambiar notas y variable aprobadoo
        actualizaNotas(alumnosDAT);


        //punto 4, exportar datos en texto plano
        //crear el archivo
        File alumnosTXT = new File("./tarea1/ficheros/alumnos.txt");
        try{
            alumnosTXT.createNewFile();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        escribirAlumnosTxt(alumnosDAT, alumnosTXT);

    }

    public static Alumno[] introducirAlumnos(){
        Scanner leer = new Scanner(System.in);
        String nombre;
        int edad;
        float nota;

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
                if(nota>10) nota=10; //me  aseguro que la nota no este por encima  de 10 o por debajo de 0
                else if(nota<0) nota=0;
                //guardo la informacion
                Alumno alumno = new Alumno(nombre, edad, nota);
                infoAlumnos[i] = alumno;
                System.out.println("-------Alumno "+(i+1)+" guardado------------------------------------");
            }catch (InputMismatchException e)
            {
                System.err.println("Error de formato : "+e.getMessage());
            }

        }
        return infoAlumnos;
    }
    //guardar en bytes
    public static void guardarAlumnosDat(Alumno[] infoAlumnos, File alumnosDAT) {
        //guardar los datos en alumnosDAT, punto 2
        try(RandomAccessFile rafAlumosDat= new RandomAccessFile(alumnosDAT, "rw")){
            rafAlumosDat.setLength(0); //resetear para que no se ralle
            for(int i=0; i<5; i++)
            {
                rafAlumosDat.writeUTF(infoAlumnos[i].getNombre());
                rafAlumosDat.writeInt(infoAlumnos[i].getEdad());
                rafAlumosDat.writeFloat(infoAlumnos[i].getNota());
                rafAlumosDat.writeBoolean(infoAlumnos[i].isAprobado());
            }
            System.out.println("-------------------------------------------------");
            System.out.println("Alumnos guardados correctamente");
            System.out.println("-------------------------------------------------");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public static void actualizaNotas(File alumnosDAT){
        try(RandomAccessFile rafNotasActualiza= new RandomAccessFile(alumnosDAT, "rw")) {
            long posicion=0;
            System.out.println("Actualizar notas...............");
            while(posicion<rafNotasActualiza.length()) {
                //aqui el puntero esta a 0
                System.out.println("-------------------------------------------------");
                System.out.println("Nombre: "+rafNotasActualiza.readUTF()); //leo el nombre, el puntero se mueve
                System.out.println("Edad: "+rafNotasActualiza.readInt()); //leo la edad

                long posicionNota = rafNotasActualiza.getFilePointer();
                float notaActu = rafNotasActualiza.readFloat(); //leo la nota

                long posicionAprobado = rafNotasActualiza.getFilePointer();//guardo la posicion del booleano
                boolean aprobadoAct = rafNotasActualiza.readBoolean(); //leo y me guardo su valor

                System.out.println("Nota sin actualizar: " + notaActu);
                System.out.println("Aprobado sin actualizar : " + aprobadoAct);

                //incremento 1 el valor de la nota
                if (notaActu <= 9.0f) notaActu += 1.0f;
                else notaActu = 10.0f;

                rafNotasActualiza.seek(posicionNota); //me coloco en la posision de la nota
                rafNotasActualiza.writeFloat(notaActu); //escribo la nueva nota
                System.out.println("Nota actualizada: " + notaActu);

                //compruebo si necesito cambiar el booleano aprobado
                //si notaAct es < 5 = !(suspenso = true), entonces notaAct seria False
                //caso contrario, si la nota es > 5, seria  !(suspenso = false) = true
                aprobadoAct = notaActu >= 5;
                rafNotasActualiza.seek(posicionAprobado);
                rafNotasActualiza.writeBoolean(aprobadoAct);
                System.out.println("Aprobado actualizado: " + aprobadoAct);
                posicion = rafNotasActualiza.getFilePointer();
            }
            System.out.println("-------------------------------------------------");

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public  static void escribirAlumnosTxt(File alumnosDAT, File alumnosTXT ){
        try (RandomAccessFile rafLeer= new RandomAccessFile(alumnosDAT, "r");
             RandomAccessFile rafEscribirLeer= new RandomAccessFile(alumnosTXT, "rw")){

            long posicion1=0;
            System.out.println("Reescribir en alumnos.txt................");
            rafEscribirLeer.setLength(0); //resetear para que no se ralle
            while(posicion1<NUMALUMNOS) {
                //leer de alumnos.dat
                System.out.println("-------------------------------------------------");
                String nombreLee = rafLeer.readUTF(); //leo el nombre
                int edadLee = rafLeer.readInt(); //leo la edad
                float notaLee = rafLeer.readFloat(); //leo la nota
                boolean aprobadoLee = rafLeer.readBoolean();

                //COMPROBAR VALOR DE APROBADO
                String aprobadoString;
                if(aprobadoLee) aprobadoString = "si";
                else aprobadoString="no";
                //escribir en alumnos.txt
                System.out.println("Informacion leída: Nombre: "+nombreLee+", Edad: "+edadLee+", Nota: "+notaLee+", Aprobado: "+aprobadoString);
                rafEscribirLeer.writeUTF("Nombre: "+nombreLee+", Edad: "+edadLee+", Nota: "+notaLee+", Aprobado: "+aprobadoString+"\n");
                posicion1++;
            }
            System.out.println("-------------------------------------------------");

            System.out.println("Fichero txt escrito correctamente");

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: "+e.getMessage());
        }

    }
}
