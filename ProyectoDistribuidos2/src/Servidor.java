import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Personas.Persona;
import Productos.Producto;
import Productos.Subasta;

public class Servidor implements Runnable{
    private ArrayList<ConexionCliente> conexiones;
    private ServerSocket server;
    private boolean listo;
    private ExecutorService pool;
    private int puerto = 5000;
    private ArrayList<Subasta> subastas;

    public Servidor() {
        conexiones = new ArrayList<>();
        subastas = new ArrayList<>();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            server = new ServerSocket(puerto);
            pool = Executors.newCachedThreadPool();
            while(!listo){
                Socket socket = server.accept();
                ConexionCliente cliente = new ConexionCliente(socket,this,subastas);
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

    //Enviar mensaje de un producto solo a los participantes de cierta subasta
    public void broadcastSubasta(String mensaje,ArrayList<Persona> personasEnSubasta){
        for (ConexionCliente conexionCliente : conexiones) {
            if(conexionCliente!=null){
                for (Persona persona : personasEnSubasta) {
                    if(persona.getNombre().equals(conexionCliente.getPersona().getNombre())){
                        conexionCliente.enviarMensaje(mensaje);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Producto auto = new Producto("auto", 1000, false);
        Producto casa = new Producto("casa", 2000000, false);
        Producto moto = new Producto("moto", 1000, false);
        
        Subasta subastaA = new Subasta(auto);
        Subasta subastaM = new Subasta(moto);
        Subasta subastaC = new Subasta(casa);
        
        Servidor servidor = new Servidor();
        servidor.subastas.add(subastaA);
        servidor.subastas.add(subastaM);
        servidor.subastas.add(subastaC);

        servidor.run();
    }
}
