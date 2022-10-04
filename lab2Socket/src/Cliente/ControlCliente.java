package Cliente;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
/**
 *
 * @author RPVZ
 */
public class ControlCliente implements Runnable{
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private PanelCliente panel;

    public ControlCliente(Socket socket, PanelCliente panel){
        this.panel = panel;
        try{
            dataInput = new DataInputStream(socket.getInputStream());
            dataOutput = new DataOutputStream(socket.getOutputStream());
            
            Thread hilo = new Thread(this);
            hilo.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void enviarInformacion(){
        try{
            dataOutput.writeUTF(panel.getOp()+";"+panel.getPersona());
        } catch (Exception excepcion){
            excepcion.printStackTrace();
        }
    }
    @Override
    public void run(){
        try{
            while (true){
                enviarInformacion();
                String texto = dataInput.readUTF();
                System.out.println(texto);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

