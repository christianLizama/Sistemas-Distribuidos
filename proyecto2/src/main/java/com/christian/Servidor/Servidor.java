package com.christian.Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.christian.Datos.Historial;
import com.christian.Datos.LecturaEscritura;
import com.christian.Personas.Persona;
import com.christian.Productos.Producto;
import com.christian.Productos.Subasta;

public class Servidor implements Runnable{
    private ArrayList<ConexionCliente> conexiones;
    private ServerSocket server;
    private boolean listo;
    private ExecutorService pool;
    private int puerto = 5000;
    private ArrayList<Subasta> subastas;
    private ArrayList<Producto> productosVendidos;
    private ArrayList<Historial> historialPujas;

    public Servidor() {
        conexiones = new ArrayList<>();
        subastas = new ArrayList<>();
        productosVendidos = new ArrayList<>();
        historialPujas = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(puerto);
            pool = Executors.newCachedThreadPool();
            while(!listo){
                Socket socket = server.accept();

                ConexionCliente cliente = new ConexionCliente(socket,this,subastas,productosVendidos,historialPujas);
                conexiones.add(cliente);
                pool.execute(cliente);
            }

        } catch (IOException e) {
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
    public void broadcastSubasta(String mensaje,Subasta subasta, Persona pujador,boolean ganador){
        for (ConexionCliente conexionCliente : conexiones) {
            if(conexionCliente!=null){
                for (Persona persona : subasta.getPersonas()) {
                    if(persona.getNombre().equals(conexionCliente.getPersona().getNombre()) && !persona.getNombre().equals(pujador.getNombre())){
                        conexionCliente.enviarMensaje(mensaje);
                        if(!ganador){
                            conexionCliente.opcionesPuja(subasta);
                        }
                    }
                }
            }
        }
    }

    //Enviar mensaje de un producto solo a los participantes de cierta subasta incluyendose a el mismo
    public void broadcastSubastaTodos(String mensaje,Subasta subasta){
        for (ConexionCliente conexionCliente : conexiones) {
            if(conexionCliente!=null){
                for (Persona persona : subasta.getPersonas()) {
                    if(persona.getNombre().equals(conexionCliente.getPersona().getNombre())){
                        conexionCliente.enviarMensaje(mensaje);
                    }
                }
            }
        }
    }

    //Actualizar lista de personas que no se han unido a una subasta
    public void broadcastClientesSinSubasta(){
        for (ConexionCliente conexionCliente : conexiones) {
            if(conexionCliente!=null && conexionCliente.getEstoyEnSubasta() == false){
                conexionCliente.enviarMensaje("Se acaba de vender un producto");
                conexionCliente.mostrarProductos();
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
            servidor.historialPujas.add(new Historial(subasta.mostrarProducto()));
        }

        System.out.println("Servidor iniciado...");
        servidor.run();
    }
}
