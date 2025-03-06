import javax.security.auth.callback.*;
import java.io.IOException;

public class MyCallbackHandler implements CallbackHandler {

    private String usuario;
    private String password;

    public MyCallbackHandler(String usuario, String password) {
        this.usuario=usuario;
        this.password=password;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        //se le asigna NameCallbback al nombre y PasswordCallback a la conttaseña
        for(int i=0;i<callbacks.length;i++){
            Callback callback=callbacks[i];
            if(callback instanceof NameCallback){
                NameCallback nc=(NameCallback)callback;
                nc.setName(usuario);
            }else if(callback instanceof PasswordCallback){
                PasswordCallback pc=(PasswordCallback)callback;
                pc.setPassword(password.toCharArray());
            }
        }
    }
}
