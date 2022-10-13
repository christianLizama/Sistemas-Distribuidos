import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ConexionCliente implements Runnable{

    private Socket cliente;
    private BufferedReader in;
    private PrintWriter out;
    private String nombre;
    private Servidor server;

    public ConexionCliente(Socket cliente,Servidor server){
        this.cliente = cliente;
        this.server = server;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            out = new PrintWriter(cliente.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            out.println("Ingresar su nombre: ");
            nombre = in.readLine();
            System.out.println(nombre + " Conectado con servidor");
            server.broadcast(nombre+" Se ha unido a la puja");
            String mensaje;
            out.println("\n");
            out.println("1)Pujar");
            out.println("2)Salir de esa puja");
            out.println("3)Salir de la subasta");
            while((mensaje = in.readLine())!= null){
                switch (mensaje) {
                    //Pujar
                    case "1":
                        out.println("Ingrese precio: ");
                        int precio = Integer.parseInt(in.readLine());
                        boolean comprobador = true;
                        while(comprobador){
                            //Si el precio es mayor a 0 es un precio valido
                            if(precio > 0){
                                out.println("has pujado");
                                out.println(precio);
                                server.broadcast(nombre + " ha pujado por "+ precio);
                                comprobador = false;
                            }
                            else{
                                out.println("Ingrese un precio valido");
                            }
                        }

                        break;
                    //Salir de la puja de ese articulo
                    case "2":
                        break;
                    //Salimos de la subasta 
                    case "3":
                        server.broadcast(nombre + " ha salido de la subasta");
                        System.out.println(nombre+" Se ha desconectado del servidor");
                        desconectar();
                        break;
                        
                    default:
                        break;
                }
            }

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
