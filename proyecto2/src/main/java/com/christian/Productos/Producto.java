package com.christian.Productos;

import com.christian.Personas.*;

public class Producto {

    private String nombre;
    private int precioActual;
    private int precioInicial;
    private boolean vendido;
    private Persona ganador;
    
    public Producto(String nombre, int precioActual, boolean vendido) {
        this.nombre = nombre;
        this.precioActual = precioActual;
        this.vendido = vendido;
    }
    public void setGanador(Persona ganador) {
        this.ganador = ganador;
    }
    public Persona getGanador() {
        return ganador;
    }
    public void reiniciarPrecio(){
        precioActual=precioInicial;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }

    public boolean getVendido() {
        return this.vendido;
    }

    public int getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(int precioActual) {
        this.precioActual = precioActual;
    }

}
