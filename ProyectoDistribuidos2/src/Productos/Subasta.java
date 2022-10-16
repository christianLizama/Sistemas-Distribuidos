package Productos;

import java.util.ArrayList;

import Personas.Persona;

public class Subasta {

    private ArrayList<Persona> personas;
    private Producto producto;

    public Subasta(Producto producto) {
        this.producto = producto;
        this.personas = new ArrayList<>();
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(ArrayList<Persona> personas) {
        this.personas = personas;
    }

    public void personasPujando(){
        System.out.println(producto.getNombre());
        for (Persona persona : personas) {
            System.out.println(persona.getNombre());
        }
    }

    public String mostrarProducto(){
        return producto.getNombre();
    }

    public void cambiarPrecio(int precio){
        producto.setPrecioActual(precio);
    }

    public int obtenerPrecio(){
        return producto.getPrecioActual();
    }

    public void eliminarPersona(Persona persona){

        Persona posibleGanador = ganador();
        if(posibleGanador==null){
            personas.remove(persona);
        }
    }

    public void agregarPersona(Persona persona){
        personas.add(persona);
    }

     //Devolvemos el ganador
    public Persona ganador(){
        if(personas.size() == 1){
            System.out.println("Ganador del remate "+personas.get(0).getNombre());
            producto.setVendido(true);
            return personas.get(0);
        }
        return null;
    }
    
}
