import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.PrivilegedAction;

public class Ejercicio6Accion implements PrivilegedAction {
    public Object run() {
        File f = new File("fichero.txt");

        if (f.exists()) {
            // Si el archivo existe, se muestra su contenido
            System.out.println("EL FICHERO EXISTE ....");
            try {
                FileReader fic = new FileReader(f);
                System.out.println("Su contenido es: ");
                int c;
                while ((c = fic.read()) != -1) {
                    System.out.print((char) c);
                }
                fic.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Si no existe, se crea y se insertan datos
            System.out.println("EL FICHERO NO EXISTE, LO CREO ....");
            try {
                FileWriter fic = new FileWriter(f);
                fic.write("Esto es una línea de texto");
                fic.close(); // Cerrar fichero
                System.out.println("Fichero creado con datos....");
            } catch (Exception e) {
                System.out.println("[ERROR] => " + e.getMessage());
            }
        }
        return null;
    }
}
