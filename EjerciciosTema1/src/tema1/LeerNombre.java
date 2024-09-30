package tema1;

public class LeerNombre {
    public static void main(String[] args) {
        if(args.length!=1){
            System.out.println("Argumentos no encontrados");
            System.exit(1);
        }else {
            System.out.printf("Nombre: %s%n", args[0]);
            //args[0] porque solo ponemos un argumento
            System.exit(-1);
        }
    }
}
