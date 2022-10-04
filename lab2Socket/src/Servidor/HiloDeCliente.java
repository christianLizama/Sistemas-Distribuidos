package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class HiloDeCliente implements Runnable{
    private Socket socket;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private ArrayList<String> personas;

    public HiloDeCliente(ArrayList<String> personas, Socket socket)
    {
        this.personas = personas;
        this.socket = socket;
        try{
            dataInput = new DataInputStream(socket.getInputStream());
            dataOutput = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        try{
            while (true){
                //leemos la cadena del cliente
                String texto = dataInput.readUTF();
                synchronized (personas){
                    consultas(texto);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void consultas(String operacion){
        String[] operacionSeparada = operacion.split(";");
        String opcion = operacionSeparada[0];
        String nombre = operacionSeparada[1];
        String mensaje="";
        switch (opcion){
            case "1":
                //agregar
                System.out.println("Ha solicitado agregar una persona");
                personas.add(nombre);
                mensaje = "Se ha agregado la persona "+nombre+"\n";
                enviarMensaje(mensaje);
                break;
            case "2":
                //mostrar todos
                System.out.println("Ha solicitado mostrar la lista de los participantes");
                mensaje = mostrarParticipantes();
                enviarMensaje(mensaje);
                break;
            default:
                break;
        }
    }

    public String mostrarParticipantes(){
        String mensaje="\nLista de participantes: \n";
        for (String persona : personas) {
            mensaje += persona+"\n";
        }
        return mensaje;
    }

    public void enviarMensaje(String mensaje){
        try{
            dataOutput.writeUTF(mensaje);
        } catch (Exception excepcion){
            excepcion.printStackTrace();
        }
    }
}
