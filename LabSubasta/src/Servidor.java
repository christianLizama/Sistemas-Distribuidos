import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static ArrayList<String> personas = new ArrayList<>();
    public static Pujas pujas = new Pujas();
    public static ArrayList<ServidorHilo> Clientes;
    public static void main(String[] args) {
        Clientes = new ArrayList<ServidorHilo>();
        try {
            ServerSocket server = new ServerSocket(5000);
            Socket sc;
            
            System.out.println("Servidor iniciado");
            pujas.agregarProductos();

            while(true){
                
                // Espero la conexion del cliente
                sc = server.accept();
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                
                // Pido al cliente el nombre al cliente
                out.writeUTF("Ingresa nombre del cliente:");
                String nombreCliente = in.readUTF();

                Persona persona = new Persona(nombreCliente);

                pujas.agregarPersona(persona);

                // Inicio el hilo
                ServidorHilo hilo = new ServidorHilo(sc,in, out, nombreCliente,pujas);
                Clientes.add(hilo);
                hilo.setClientes(Clientes);
                hilo.start();

                System.out.println("Creada la conexion con el cliente " + nombreCliente);
                
                pujas.mostrarProductosPersonas();
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
