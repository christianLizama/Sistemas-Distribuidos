import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente implements Runnable {

    private Socket cliente;
    private BufferedReader in;
    private PrintWriter out;
    private String host = "127.0.0.1";
    private int puerto = 5000;
    private boolean listo;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            cliente = new Socket(host,puerto);
            out = new PrintWriter(cliente.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            InputControlador inControlador = new InputControlador();
            Thread t = new Thread(inControlador);
            t.start();

            String inMensaje;

            while((inMensaje = in.readLine())!=null){
                System.out.println(inMensaje);
            }
        } catch (IOException e) {
            desconectar();
        }
    }

    //Salir de toda las subastas
    public void desconectar(){
        listo = true;
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

    class InputControlador implements Runnable{

        @Override
        public void run() {

            try {
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while(!listo){
                    String mensaje = inReader.readLine();
                    if(mensaje.equals("3") || mensaje.equals("0")){
                        out.println(mensaje);
                        inReader.close();
                        desconectar();    
                    }else{
                        out.println(mensaje);
                    }
                }
                
            } catch (IOException e) {
                desconectar();
            }
        }

    } 

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.run();
    }

}
