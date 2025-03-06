import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.util.Map;

public class Ejercicio5Login implements LoginModule {
    private Subject subject;
    private CallbackHandler callbackHandler;


    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        boolean autenticado = true;
         if(callbackHandler==null){
             throw new LoginException("No callback handler");
         }
         //se crea el array de callbacks
        Callback[] callbacks = new Callback[2];
         callbacks[0] = new NameCallback("Usuario");
         callbacks[1]=new PasswordCallback("Clave", false);

         try{
             callbackHandler.handle(callbacks);

             //se solicita el usuario y contraseña
             String usuario=((NameCallback)callbacks[0]).getName();
             char[] password=((PasswordCallback)callbacks[1]).getPassword();
             String clave=new String(password);

             //AUTENTICACION
             autenticado=("pedro".equalsIgnoreCase(usuario) && "abcd".equalsIgnoreCase(clave));
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
         return autenticado;
    }

    @Override
    public boolean commit() throws LoginException {
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        return true;
    }
}
