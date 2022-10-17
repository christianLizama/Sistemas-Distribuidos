package com.christian.Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.christian.Datos.LecturaEscritura;
import com.christian.Personas.*;
import com.christian.Productos.*;

class ConexionCliente implements Runnable{

    private Socket cliente;
    private BufferedReader in;
    private PrintWriter out;
    private Persona persona;
    private Servidor server;
    private ArrayList<Subasta> subastas;
    private ArrayList<Producto> vendidos;

    public ConexionCliente(Socket cliente,Servidor server,ArrayList<Subasta> subastas,ArrayList<Producto> productosVendidos){
        this.cliente = cliente;
        this.server = server;
        this.subastas = subastas;
        this.vendidos = productosVendidos;
    }

    public Persona getPersona(){
        return persona;
    }

    public void eliminarSubasta(Subasta subasta){
        LecturaEscritura escribir = new LecturaEscritura();
        vendidos.add(subasta.getProducto());
        escribir.escribirJson(vendidos);
        subastas.remove(subasta);
        if(subastas.size()==0){
            server.desconectar();
            desconectar();
        }
    }

    public void mostrarProductos(){
        out.println("------Lista de Subastas------");
        int i = 1;
        for (Subasta subasta : subastas) {
            out.println(i+")"+subasta.mostrarProducto());
            i+=1;
        }
        out.println("Si desea salir escriba 0");
        out.println("Ingrese opcion: ");
    }

    public void opcionesPuja(Subasta subasta){
        out.println("Producto en subasta: "+subasta.mostrarProducto());
        out.println("Precio actual: "+ subasta.obtenerPrecio());
        out.println("1)Pujar");
        out.println("2)Salir de esa puja");
        out.println("3)Salir de la subasta");
        out.println("Ingrese opcion:");
    }

    public void pujar(Subasta subasta){
        try {
            boolean ciclo=true;
            while(ciclo){
                if(!subastas.contains(subasta)){
                    ciclo=false;
                }
                else{
                    String opcion;
                    opcionesPuja(subasta);
                    opcion = in.readLine();
                                        
                    if(!subastas.contains(subasta)){
                        ciclo=false;
                        break;
                    }
                    switch (opcion) {
                        //Pujar
                        case "1":
                            boolean comprobador = true;
                            int precio = 0;
                            while(comprobador){
                                out.println("Ingrese precio: ");
                                precio = Integer.parseInt(in.readLine());
                                //Si el precio es mayor a 0 es un precio valido
                                if(precio > 0 && precio>subasta.obtenerPrecio()){
                                    out.println("Has pujado "+precio);
                                    subasta.cambiarPrecio(precio);
                                    //Solo para pujadores de cierta subasta
                                    String mensaje = persona.getNombre() + " ha pujado por "+ precio;
                                    server.broadcastSubasta(mensaje,subasta,persona,false);
                                    persona.setPrecio(precio);
                                    comprobador = false;
                                }
                                else{
                                    out.println("Ingrese un precio valido");
                                }
                            }
                            break;
                        //Salir de la puja de ese articulo
                        case "2":
                            ciclo = false;
                            Thread.sleep(10000);
                            
                            Persona posibleGanador = subasta.eliminarPersona(persona);
                            //Si ha ganado alguien 
                            if(posibleGanador != null){

                                String retirado = persona.getNombre() + " se ha retirado de la subasta";
                                String ganador = "Felicitaciones "+posibleGanador.getNombre() + " has comprado " + subasta.mostrarProducto() + " por " + subasta.obtenerPrecio()+"\nPresione enter para volver al menu principal";
                                server.broadcastSubasta(retirado, subasta, persona,true);
                                server.broadcastSubasta(ganador, subasta, persona,true);
                                out.println("Te has retirado de la subasta");
                                eliminarSubasta(subasta);
                                server.broadcastClientesSinSubasta();
                            }
                            //No ha ganado nadie
                            else{
                                String retirado = persona.getNombre() + " se ha retirado de la subasta";
                                out.println("Te has retirado de la subasta");
                                server.broadcastSubasta(retirado, subasta, persona,false);
                            }

                            break;
                        //Salimos de la subasta 
                        case "3":
                            ciclo=false;
                            server.broadcast(persona.getNombre() + " ha salido de la subasta");
                            desconectar();
                            break;
                        default:
                            break;
                    }
                }
            }
        
            
        } catch (IOException e) {
            // TODO: handle exception
        }
        catch(InterruptedException e2){
            
        }
        

    }

    public void cicloSubasta(){
        try {
            boolean ciclo = true;
            //mostrar subastas posibles
            while(ciclo){
                persona.setPrecio(0);
                mostrarProductos();
                //elige subasta 
                
                int eleccion = Integer.parseInt(in.readLine());
                if(eleccion==0){
                    server.broadcast(persona.getNombre() + " ha salido de la subasta");
                    ciclo = false;
                    desconectar();
                }else{
                    //lo manda a pujar()
                    server.broadcast(persona.getNombre()+" Se ha unido a la subasta de " + subastas.get(eleccion-1).mostrarProducto());
                    subastas.get(eleccion-1).agregarPersona(persona);
                    pujar(subastas.get(eleccion-1));
                }
            }

        } catch (IOException e) {
            desconectar();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            out = new PrintWriter(cliente.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            out.println("Ingresar su nombre: ");
            String nombre = in.readLine();
            System.out.println(nombre + " Conectado con servidor");
            persona = new Persona(nombre);
            cicloSubasta();
        } catch (IOException e) {
            // TODO: handle exception
            desconectar();
        }
    }

    public void enviarMensaje (String mensaje){
        out.println(mensaje);
    }

    //Salir de toda las subastas
    public void desconectar(){
        System.out.println(persona.getNombre()+" Se ha desconectado del servidor");
        try {
            in.close();
            out.close();
            if(!cliente.isClosed()){
                cliente.close();
            }
            
        } catch (IOException e) {
            // TODO: handle exception
        }
        
    }


}
