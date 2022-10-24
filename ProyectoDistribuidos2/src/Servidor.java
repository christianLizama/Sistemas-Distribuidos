import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Datos.LecturaEscritura;
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
    public void broadcastSubasta(String mensaje,Subasta subasta, Persona pujador){
        for (ConexionCliente conexionCliente : conexiones) {
            if(conexionCliente!=null){
                for (Persona persona : subasta.getPersonas()) {
                    if(persona.getNombre().equals(conexionCliente.getPersona().getNombre()) && !persona.getNombre().equals(pujador.getNombre())){
                        conexionCliente.enviarMensaje(mensaje);
                        conexionCliente.opcionesPuja(subasta);
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        LecturaEscritura lecturaEscritura = new LecturaEscritura();
        ArrayList<Producto> productos = lecturaEscritura.leer();
        Subasta subasta;
        for (Producto producto : productos) {
            subasta = new Subasta(producto);
            servidor.subastas.add(subasta);   
        }

        System.out.println("Servidor iniciado...");
        servidor.run();
    }
}
