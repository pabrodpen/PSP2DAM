package org.example;

/*He entendido el ejercicio de la siguiente manera:
He creado una clase Cuenta con saldo y saldoMax
Hago los getters y setters de manera sincronizada
Cada vez que hago un reintegro o ingreso
espero a que este la operacion disponible(no haya una operacion
haciendose a la vez) con wait y un boolean y depues notifico a los hilos que
comparten la cuenta

Despues en la clase Persona extiendo Thread para qye cada persona que use
la cuenta sea un hilo. En el run hago ingresos y reingetros con sleep para que se vea
mejor. En el contructor se asocia la cuenta con las persona

NO FUNCIONA, ES DECIR, NO SE CUMPLE EL ORDEN DE HACER UNA OPERACION Y MOSTRAR
EL RESULTADO, PERO NO SE LO QUE ME FALTA
* */

public class Main {
    public static void main(String[] args) {
        Cuenta cuenta=new Cuenta(200,800);
        Persona persona1=new Persona("Pablo",cuenta);
        Persona persona2=new Persona("Miguel",cuenta);

        persona1.start();
        persona2.start();

    }
}