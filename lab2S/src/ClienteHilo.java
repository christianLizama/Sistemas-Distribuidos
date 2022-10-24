import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteHilo extends Thread{

    private DataInputStream in;
    private DataOutputStream out;

    public ClienteHilo(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        Scanner sn = new Scanner(System.in);

        String mensaje;
        int opcion = 0;
        boolean salir = false;

        while (!salir) {

            try {
                System.out.println("1. Agregar Persona");
                System.out.println("2. Mostrar lista");
                System.out.print("Ingresar opcion: ");
                opcion = Integer.parseInt(sn.nextLine());
                out.writeInt(opcion);
                
                switch (opcion) {
                    case 1:
                        // Agregamos persona
                        String persona = "";
                        System.out.println("Ingresar persona: ");
                        persona = sn.nextLine();
                        System.out.println("Persona agregada: " + persona);
                        // Le mando al servidor el numero aleatorio
                        out.writeUTF(persona);
                        // Recibo y muestro el mensaje
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                        break;
                    case 2:
                        // Recibimos las personas
                        System.out.println(in.readUTF());

                        break;
                    default:
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                        
                }
            } catch (IOException ex) {
                Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
