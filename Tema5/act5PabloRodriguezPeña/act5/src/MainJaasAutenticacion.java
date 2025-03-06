import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

//java -Dusuario=pedro -Dclave=abcd -Djava.security.policy=policy.config -Djava.security.auth.login.config=jaas.config MainJaasAutenticacion

public class MainJaasAutenticacion {
    public static void main(String[] args) {
        String usuario="pedro";
        String password="abcd";

        //se pasa al callbackhandler el usuario y la clave
        CallbackHandler handler=new MyCallbackHandler(usuario,password);

        try{
            LoginContext loginContext=new LoginContext("Ejercicio5",handler);
            //se llama al metodo login para la autenticacion
            loginContext.login();
            System.out.println("USUARIO AUTENTICADO: "+usuario);
        }catch(LoginException e){
            e.printStackTrace();
        }

    }
}