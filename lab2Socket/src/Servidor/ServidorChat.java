package Servidor;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorChat{
    private ArrayList<String> personas = new ArrayList<>();

    public static void main(String[] args){
        new ServidorChat();
    }
    public ServidorChat(){
        try{
            ServerSocket socketServidor = new ServerSocket(5000);
            while (true){
                Socket cliente = socketServidor.accept();
                Runnable nuevoCliente =  new HiloDeCliente(personas, cliente);
                Thread hilo = new Thread(nuevoCliente);
                hilo.start();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
