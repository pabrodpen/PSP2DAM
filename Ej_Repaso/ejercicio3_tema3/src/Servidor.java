import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 6000;
        Curso curso1=new Curso("1","Este es el curso 1");
        Curso curso2=new Curso("2","Este es el curso 2");
        Curso curso3=new Curso("3","Este es el curso 3");

        Alumno alumno1=new Alumno("1","Pablo",curso1,5);
        Alumno alumno2=new Alumno("2","Manuel",curso2,7);
        Alumno alumno3=new Alumno("3","Daniel",curso3,6);
        Alumno alumno4=new Alumno("4","Lucia",curso1,9);
        Alumno alumno5=new Alumno("5","Laura",curso2,3);

        Alumno[] alumnos = new Alumno[5];
        alumnos[0]=alumno1;
        alumnos[1]=alumno2;
        alumnos[2]=alumno3;
        alumnos[3]=alumno4;
        alumnos[4]=alumno5;

        try(ServerSocket s= new ServerSocket(puerto);){
            while(true){
                Socket cliente=s.accept();
                System.out.println("Cliente conectado");

                // Recibe el ID del cliente
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                String idAlumno = (String) entrada.readObject();
                System.out.println("ID recibido: " + idAlumno);

                Alumno alumnoEncontrado=null;
                boolean encontrado = false;
                for(int i=0;i<alumnos.length && !encontrado;i++){
                    if(alumnos[i].idAlumno.equals(idAlumno)){
                        alumnoEncontrado=alumnos[i];
                        encontrado=true;
                    }
                }

                //enviamos el alumno al cliente
                ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
                salida.writeObject(alumnoEncontrado);
                salida.flush();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}