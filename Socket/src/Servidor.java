import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    ServerSocket servidor; 
    Socket socket;
    int puerto = 5000;
    InputStream entrada;
    DataInputStream flujo; 
    DataOutputStream salida;


    public String operacion(String opcion,int a ,int b){
        String out = "";
        switch (opcion) {
            case "+":
                out = suma(a, b);
                break;
            case "-":
                out = resta(a, b);
                break;
            case "/":
                out = division(a, b);
                break;

            case "*":
                out = multiplicacion(a, b);
                break;    
            default:
                break;
            
        }
        return "El valor de la operacion "+a+" " + opcion +" "+b +" es igual a " + out;    
    }

    public String suma(int a,int b){
        int suma = a+b;
        return Integer.toString(suma);
    }
    public String resta(int a,int b){
        int resta = a-b;
        return Integer.toString(resta);
    }
    public String multiplicacion(int a,int b){
        int multiplicacion = a*b;
        return Integer.toString(multiplicacion);
    }
    public String division(int a,int b){
        int division = a/b;
        return Integer.toString(division);
    }

    public String procesarCadena(String cadena){
        String[] operacionSeparada = cadena.split("\\s+");

        String operador = operacionSeparada[0];
        int operando1 = Integer.parseInt(operacionSeparada[1]);
        int operando2 = Integer.parseInt(operacionSeparada[2]);

        String resultado = operacion(operador, operando1, operando2);
        
        return resultado;
    }


    public void inicio(){
        try {
            servidor = new ServerSocket(puerto);
            socket = new Socket();
            socket = servidor.accept();
            entrada = socket.getInputStream();
            
            flujo = new DataInputStream(entrada);
            
            //System.out.println("La cadena ingresada por el cliente es " + flujo.readUTF());
            //Obtenemos el resultado de la operacion
            String resultado = procesarCadena(flujo.readUTF());

            //Enviamos el resultado de la operacion al cliente
            salida = new DataOutputStream(socket.getOutputStream());
            salida.writeUTF(resultado);
            socket.close();

        } catch (IOException e) {
        }

    }
}
