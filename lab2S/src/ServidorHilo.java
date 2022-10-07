
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServidorHilo extends Thread {

    private DataInputStream in;
    private DataOutputStream out;
    private String nombreCliente;
    private ArrayList<String> personas;

    public ServidorHilo(DataInputStream in, DataOutputStream out, String nombreCliente, ArrayList<String> personas) {
        this.in = in;
        this.out = out;
        this.nombreCliente = nombreCliente;
        this.personas = personas;
    }

    @Override
    public void run() {
        int opcion;
        
        while (true) {
            
            try {
                opcion = in.readInt();
                switch (opcion) {
                    case 1:
                        // Recibo la persona
                        String persona = in.readUTF();
                        // escribo el numero
                        System.out.println("Se agrego la persona: " + persona +" por el cliente "+nombreCliente);
                        //Agregamos la persona a la lista
                        personas.add(persona);
                        // Mando el mensaje de confirmacion al cliente
                        out.writeUTF("Persona guardada correctamente\n");
                        break;
                    
                    case 2:
                        //Enviamos la lista al cliente
                        String retorno = "Lista Personas:\n";
                        for (String string : personas) {
                            retorno+=string+"\n";
                        }
                        out.writeUTF(retorno);
                        break;
                    
                    default:
                        out.writeUTF("Solo numero del 1 al 6");
                }
                
            } catch (IOException ex) {
                Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
