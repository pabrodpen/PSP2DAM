import java.security.PrivilegedAction;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.*;

//java -Dusuario=pedro -Dclave=abcd -Djava.security.policy=policy.config -Djava.security.auth.login.config=jaas.config MainJaasAutenticacion
public class MainJaasAutenticacion {
    public static void main(String[] args) {
        CallbackHandler handler;
        LoginContext loginContext = null;

        try {
            handler = new MyCallbackHandler("pedro","abcd");
            loginContext = new LoginContext("Ejercicio6", handler);
            loginContext.login();
            System.out.println("Usuario autenticado...");
        } catch (LoginException e) {
            System.err.println("[ERROR] => No se puede autenticar el usuario...");
            System.exit(-1);
        }

        // Una vez autenticado, se obtiene el Subject
        Subject subject = loginContext.getSubject();


        PrivilegedAction action = new Ejercicio6Accion();

        try {
            // El sujeto realiza la acción definida en la clase EjemploAccion()
            // con usuario autenticado y bajo las restricciones de seguridad definidas
            // Se crea un objeto PrivilegedAction

            Subject.doAsPrivileged(subject, action, null);
        } catch (SecurityException e) {
            System.out.println("[ACCESO DENEGADO] => " + e.getMessage());
        }

        // Desconectamos al usuario
        try {
            loginContext.logout();
        } catch (LoginException le) {
            System.out.println("Logout: " + le.getMessage());
        }

        System.exit(0);
    }
}
