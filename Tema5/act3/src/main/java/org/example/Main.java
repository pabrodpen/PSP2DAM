package org.example;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Main {
    public static void main(String[] args) {
        try{
            //se inicializa el generador de claves
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            keyPairGenerator.initialize(2048,random);

            //se crea el par de claves privada y publica
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            //guardamos la clave privada en un fichero en disco de nombre Clave.privada
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(publicKey.getEncoded());

            //escribir a fichero binario la clave privada
            FileOutputStream outpriv = null;

            try {
                outpriv = new FileOutputStream("Clave.privada");
                outpriv.write(pkcs8EncodedKeySpec.getEncoded());
                outpriv.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //guardamos la clave publica en un fichero en disco de nombre Clave.publica
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(privateKey.getEncoded());

            //escribir a fichero binario la clave publica
            FileOutputStream outpub= null;

            try {
                outpub = new FileOutputStream("Clave.publica");
                outpub.write(x509EncodedKeySpec.getEncoded());
                outpub.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }catch (NoSuchAlgorithmException e1){
            e1.printStackTrace();
        }
    }
}