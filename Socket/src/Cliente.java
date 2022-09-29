import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    String host = "127.0.0.1";
    int puerto = 5000;
    DataOutputStream salida;
    DataInputStream flujo;
    InputStream entrada;
    Socket socket;

    public void inicio() {
        try {
            socket = new Socket(host,puerto);
            Scanner lectura = new Scanner(System.in);
            System.out.print("Ingresar cadena:");
            String cadena = lectura.nextLine();
            salida = new DataOutputStream(socket.getOutputStream());
            salida.writeUTF(cadena);
            lectura.close();
            
            entrada = socket.getInputStream();
            flujo = new DataInputStream(entrada);
            System.out.println(flujo.readUTF());
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
