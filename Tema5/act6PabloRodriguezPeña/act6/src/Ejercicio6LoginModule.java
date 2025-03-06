import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.util.Map;

public class Ejercicio6LoginModule implements LoginModule {
    private Subject subject;
    private CallbackHandler callbackHandler;
    String usuario;
    String clave;

    private Ejercicio6Principal usuarioPrincipal;

    public boolean commit() throws LoginException {
        usuarioPrincipal=new Ejercicio6Principal(usuario);

        if(!subject.getPrincipals().contains(usuarioPrincipal)){
            subject.getPrincipals().add(usuarioPrincipal);
        }
        return true;
    }
    public boolean abort() throws LoginException { return true; }


    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    // Método login - se realiza la autenticación
    @Override
    public boolean login() throws LoginException {
        if (callbackHandler == null) {
            throw new LoginException("Se necesita CallbackHandler");
        }

        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("Nombre de usuario: ");
        callbacks[1] = new PasswordCallback("Clave: ", false);

        try {
            callbackHandler.handle(callbacks);
            usuario = ((NameCallback) callbacks[0]).getName();
            char[] passwordChars = ((PasswordCallback) callbacks[1]).getPassword();

            // Verificar si la contraseña es nula
            if (passwordChars == null) {
                throw new LoginException("Error: La contraseña no puede ser nula.");
            }

            clave = new String(passwordChars);

            // Validar credenciales
            if ("pedro".equalsIgnoreCase(usuario) && "abcd".equals(clave)) {
                return true; // Usuario autenticado
            } else {
                return false; // Usuario no autenticado
            }
        } catch (Exception e) {
            throw new LoginException("Error en la autenticación: " + e.getMessage());
        }
    }

    public boolean logout() throws LoginException{
        subject.getPrincipals().remove(usuarioPrincipal);
        usuarioPrincipal=null;
        usuario=null;
        clave=null;
        return true;
    }
}
