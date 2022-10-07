import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteHilo extends Thread{

    private DataInputStream in;
    private DataOutputStream out;
    private String nombre;

    public ClienteHilo(DataInputStream in, DataOutputStream out, String nombre) {
        this.in = in;
        this.out = out;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public void run() {
        Scanner sn = new Scanner(System.in);

        String mensaje;
        int opcion = 0;
        boolean salir = false;
        String productoActual="";
        int precio=0;
        
        
        while (!salir) {
            try {
                //Leemos el producto actual
                productoActual = in.readUTF();
                precio = in.readInt();
                ///out.writeUTF(nombre);
                //String productoActual = in.readUTF();
                //int precio = in.readInt();
                System.out.println("Producto actual en subasta: " + productoActual + " -precio: "+precio);
                System.out.println("1. Pujar");
                System.out.println("2. Salir de la puja de " + productoActual);
                System.out.print("Ingresar opcion: ");
                opcion = Integer.parseInt(sn.nextLine());
                out.writeInt(opcion);
                
                //No se ha pujado
                
                if(opcion==1){
                    int consulta = in.readInt();
                    if(consulta==1){
                        opcion = 1;
                    }
                    else if(consulta==3){
                        System.out.println(in.readUTF());
                        opcion=4;
                    }
                    else{
                        //Leemos que se ha pujado
                        System.out.println(in.readUTF());
                        System.out.println("Ingrese un monto mayor al actual");
                        opcion=1;
                    }
                }
                
                switch (opcion) {
                    case 1:
                        // procedemos a pujar
                        String precioPuja = "";
                        System.out.print("Ingresar precio: ");
                        precioPuja = sn.nextLine();
                        System.out.println("Precio pujado: " + precioPuja);
                        System.out.println();
                        //Le mandamos al servidor el precio pujado
                        out.writeInt(Integer.parseInt(precioPuja));

                        // Recibo y muestro el mensaje del precio pujado
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                        break;
                    case 2:
                        //Enviamos el nombre de la persona que se irá de la puja
                        out.writeUTF(nombre);
                        System.out.println();
                        System.out.println(in.readUTF());
                        break;
                    default:
                        //mensaje = in.readUTF();
                        System.out.println("");
                        
                }
            } catch (IOException ex) {
                Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
