package Cliente;
import java.net.Socket;
import java.util.Scanner;

public class ClienteChat{
    private Socket socket;
    private PanelCliente panel;

    public static void main(String[] args){
        new ClienteChat();
    }

    public ClienteChat(){
        try{
            socket = new Socket("localhost", 5000);
            Scanner lectura = new Scanner(System.in);
            while(true){

                panel = new PanelCliente(lectura);
                ControlCliente control = new ControlCliente(socket, panel);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

