
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.GapContent;


public class ServidorHilo extends Thread {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nombreCliente;
    private Pujas subasta;
    private Producto productoActual;
    private ArrayList<ServidorHilo> clientes;

    public ServidorHilo(Socket socket,DataInputStream in, DataOutputStream out, String nombreCliente, Pujas subasta) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.nombreCliente = nombreCliente;
        this.subasta = subasta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setClientes(ArrayList<ServidorHilo> clientes) {
        this.clientes = clientes;
    }

    public void pujar(){
        try {
            int precio = in.readInt();
            Persona ultimoPujador = new Persona(nombreCliente);
            
            if(productoActual.getPersonasPujando().contains(ultimoPujador)){
                //Puede pujar    
                productoActual.setUltimoPujador(ultimoPujador);
                if(subasta.getProductos().contains(productoActual)){
                    //System.out.println("encontramos");
                    int indice = subasta.getProductos().indexOf(productoActual);
                    //System.out.println(indice);
                    //System.out.println(subasta.getProductos().get(indice).getNombre());
                    subasta.getProductos().get(indice).setPrecio(precio);
                    //System.out.println(subasta.getProductos().get(indice).getPrecio());
                }
        
                // escribo el numero
                //System.out.println(ultimoPujador.getNombre()+" acaba de hacer una puja por el producto "+productoActual.getNombre()+" de "+precio);
                
                productoActual.setPrecio(precio);
                // Mando el mensaje de confirmacion al cliente
                //out.writeUTF(ultimoPujador.getNombre()+" acaba de hacer una puja por el producto "+productoActual.getNombre()+" de "+precio);
                out.writeUTF("Se pujo");
            }
            //No puede pujar
            else{
                out.writeUTF("No puedes pujar, espera");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
       

    }

    public synchronized Persona verificarGanador(int index){
        //Si la cantidad de personas pujando es de 1       
        if(subasta.getProductos().get(index).getPersonasPujando().size() == 1){
            
            Persona ganador = subasta.getProductos().get(index).ganador();
            Producto productoEliminado = subasta.getProductos().get(index);
           // System.out.println("Tenemos ganador y es "+ganador.getNombre());
            subasta.eliminarProducto(productoEliminado);
            return ganador;    
        }
        return null; 
    }
    public void morir(){
        System.exit(0);
    }

    @Override
    public void run() {
        int opcion;
        
        //setHanPujado(false);
        while (true) {
            productoActual = subasta.getProductos().get(0);
            //System.out.println(subasta.getProductos().get(0).getPrecio());
            //System.out.println("de nuevo");
            try {
                out.writeUTF(productoActual.getNombre());
                out.writeInt(productoActual.getPrecio());
                //Leemos la opcion del cliente
                opcion = in.readInt();

                switch (opcion) {
                    //Pujamos
                    case 1:
                        //Si no han pujado hacemos la primera puja
                        System.out.println(productoActual.getPrecio());
                        int index2 = subasta.getProductos().indexOf(productoActual);
                        int comprobarSalida = 0;

                        //Si la persona gano el producto por un retiro del contrario e intenta pujar algo que ya fue borrado
                        if(index2 == -1){
                            out.writeInt(3);

                            out.writeUTF("Felicidades has comprado el producto por "+ productoActual.getPrecio()+" ya que no hubo mas puja");
                            comprobarSalida=1;
                        }

                        if(productoActual.getPrecio()==0 && comprobarSalida!=1){
                            System.out.println("estamos entrando en 0");
                            //Decimos que se hara la puja
                            out.writeInt(1);
                            // Recibo la oferta
                            pujar();
                            
                        }
                        else{
                            if(comprobarSalida!=1){
                                
                                System.out.println("Pujo otro cliente, el monto es:"+productoActual.getPrecio());
                                out.writeInt(2);
                                out.writeUTF("Pujo otro cliente, el monto es:"+productoActual.getPrecio());
                                pujar();
                            }
                        }
                        break;

                    //Sacamos al cliente de la puja de ese producto
                    case 2:
                        //

                        System.out.println("se ha pulsado salir "+productoActual.getNombre());
                        String personaFuera = in.readUTF();
                        System.out.println("persona que saldra de la puja: "+ personaFuera);
                        
                        int cantidadProductos = subasta.getProductos().size();
                        System.out.println("Cantidad de productos: "+cantidadProductos);

                        int index3 = subasta.getProductos().indexOf(productoActual);
                        int comprobador=0;
                        //Significa que fue el ultimo en pujar y gano, pero el otro cliente ya salio de la puja
                        if(index3 == -1){
                            System.out.println("se han salido todos");
                            if(productoActual.getPrecio() == 0){
                                out.writeUTF("Nadie compro el producto");
                            }
                            else{
                                out.writeUTF("Felicidades has comprado el producto por "+productoActual.getPrecio()); 
                            }
                            
                            comprobador = 1;       
                        }
                        //Estamos en el ultimo producto por lo tanto daremos un ganador y termina la subasta
                        if(cantidadProductos == 0){
                            out.writeUTF("Ya no quedan más productos");

                            for (ServidorHilo servidorHilo : clientes) {
                                servidorHilo.morir();
                            }
                        }
                        //Hay mas productos en subasta
                        else{
                            if(comprobador!=1){
                                System.out.println("Hay mas productos en subasta");
                                int index = subasta.getProductos().indexOf(productoActual);

                                //El producto ya se vendio
                                if(index == -1){
                                    if(productoActual.getPrecio()==0){
                                        out.writeUTF("Nadie compro el producto");
                                    }
                                    else{
                                        out.writeUTF("Felicidades has comprado el producto");
                                    }
                                    
                                }
                                else{
                                    //Si hay mas de una persona pujando
                                    if(subasta.getProductos().get(index).getPersonasPujando().size() > 1){
                                        System.out.println("Hay mas de una persona pujando");
                                        //obtenemos todas las personas pujando por el producto actual
                                        
                                        ArrayList<Persona> personasSubastaProductoActual = new ArrayList<>();
                                        personasSubastaProductoActual.addAll(subasta.getProductos().get(index).getPersonasPujando());

                                        Iterator<Persona> recorrer = personasSubastaProductoActual.iterator();
                                        //buscamos la persona a eliminar
                                        while(recorrer.hasNext()){
                                            String nombrePujador = recorrer.next().getNombre();
                                            if(nombrePujador.equals(personaFuera)){
                                                recorrer.remove();
                                            }
                                        }
                                        System.out.println("Personas pujando por "+productoActual.getNombre());
                                        //Mostramos las personas en el servidor
                                        for (Persona persona : personasSubastaProductoActual) {
                                            System.out.println(persona.getNombre());
                                        }
                                        //Mezclamos la lista para eliminar el cliente que salio de la puja
                                        subasta.getProductos().get(index).setPersonasPujando(personasSubastaProductoActual);

                                        Persona posibleGanador = verificarGanador(index);

                                        //Hay ganador por lo que debemos cambiar de producto
                                        if(posibleGanador!=null){
                                            subasta.mostrarProductosPersonas();
                                            System.out.println();
                                            if(productoActual.getPrecio()==0){
                                                out.writeUTF("Se ha retirado de la puja ");
                                            }
                                            else{
                                                
                                                out.writeUTF("Producto vendido a "+posibleGanador.getNombre()+" por "+productoActual.getPrecio());
                                            }
                                        }
                                        //No tenemos ganador
                                        else{
                                            System.out.println("Se ha retirado del remate "+personaFuera);
                                            System.out.println("Continuamos el remate");
                                            out.writeUTF("Se ha retirado del remate "+personaFuera);
                                        }                           
                                    }
                                }
                            }
                        }
                        //out.writeUTF(retorno);
                        break;

                    //Sacamos al cliente de la subasta
                    case 3:
                        System.out.println();
                        break;
                    default:
                        //out.writeUTF("");
                        System.out.println("");
                }
                
            } catch (IOException ex) {
                Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
