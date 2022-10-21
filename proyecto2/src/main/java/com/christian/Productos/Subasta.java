package com.christian.Productos;

import java.util.ArrayList;

import com.christian.Personas.*;

public class Subasta {

    private ArrayList<Persona> personas;
    private Producto producto;
    private Persona ultimoPujador;

    

    public Subasta(Producto producto) {
        this.producto = producto;
        this.personas = new ArrayList<>();
    }

    public void limpiarPersonas(){
        this.personas = new ArrayList<>();
    }

    public Persona getUltimoPujador() {
        return ultimoPujador;
    }
    public void setUltimoPujador(Persona ultimoPujador) {
        this.ultimoPujador = ultimoPujador;
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

    public Producto getProducto(){
        return producto;
    }

    public Persona eliminarPersona(Persona eliminado){
        Persona posibleGanador = ganador(eliminado);
        //Si devuelve un null no gano nadie
        if(posibleGanador==null){
            personas.remove(eliminado);
            return null;
        }
        return posibleGanador;
    }

    public void agregarPersona(Persona persona){
        personas.add(persona);
    }

    //Devolvemos el ganador
    public Persona ganador(Persona eliminado){
        //Si quedan dos personas y se esta retirando uno de los 2
        if(personas.size() == 2 && ultimoPujador!=null && !eliminado.getNombre().equals(ultimoPujador.getNombre())){
            Persona ganador = new Persona(null);
            for (Persona persona : personas) {
                if(!persona.getNombre().equals(eliminado.getNombre())){
                    ganador = persona;
                }
            }
            producto.setGanador(ganador);
            producto.setVendido(true);
            producto.setPrecioActual(ganador.getPrecio());
            return ganador;
        }
        else{
            producto.reiniciarPrecio();
        }
        return null;
    }
    
}
