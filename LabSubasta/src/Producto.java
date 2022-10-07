import java.util.ArrayList;

public class Producto {

    private String nombre;
    private Persona ultimoPujador;
    private ArrayList<Persona> personasPujando;
    private int precio;
    private boolean vendido;

    public Producto(String nombre,ArrayList<Persona> personasPujando, int precio) {
        this.personasPujando = personasPujando;
        this.precio = precio;
        this.vendido = false;
        this.nombre = nombre;
    }

    public void setUltimoPujador(Persona ultimoPujador) {
        this.ultimoPujador = ultimoPujador;
    }

    public Persona getUltimoPujador() {
        return ultimoPujador;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean getVendido() {
        return this.vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }

    public ArrayList<Persona> getPersonasPujando() {
        return personasPujando;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPersonasPujando(ArrayList<Persona> personasPujando) {
        this.personasPujando = personasPujando;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void mostrarPersonas(){
        for (Persona persona : personasPujando) {
            System.out.println("Producto:"+nombre+" "+persona.getNombre());
        }
        System.out.println();
    }

    //Devolvemos el ganador
    public Persona ganador(){
        System.out.println("vamos a ver si hay un ganador");
        if(personasPujando.size() == 1){
            System.out.println("Ganador del remate "+personasPujando.get(0).getNombre());
            vendido=true;
            return personasPujando.get(0);
        }
        return null;
    }
    
    


    
}
