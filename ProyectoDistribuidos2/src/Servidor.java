import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor implements Runnable{
    private ArrayList<ConexionCliente> conexiones;
    private ServerSocket server;
    private boolean listo;
    private ExecutorService pool;
    private int puerto = 5000;

    public Servidor() {
        conexiones = new ArrayList<>();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            server = new ServerSocket(puerto);
            pool = Executors.newCachedThreadPool();
            while(!listo){
                Socket socket = server.accept();
                ConexionCliente cliente = new ConexionCliente(socket,this);
                conexiones.add(cliente);
                pool.execute(cliente);
            }
            


        } catch (IOException e) {
            // TODO: handle exception
            desconectar();
        }
    }

    //Desconectar a todos
    public void desconectar(){
        listo = true;
        pool.shutdown();
        try {
            if(!server.isClosed()){
                server.close();
            }
            for (ConexionCliente conexionCliente : conexiones) {
                conexionCliente.desconectar();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
        
    }

    //Enviamos un mensaje a todos los clientes
    public void broadcast(String mensaje){
        for (ConexionCliente conexionCliente : conexiones) {
            if(conexionCliente!=null){
                conexionCliente.enviarMensaje(mensaje);
            }
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.run();
    }
}
