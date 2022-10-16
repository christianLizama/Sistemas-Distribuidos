package com.christian.Productos;

import com.christian.Personas.*;

public class Producto {

    private String nombre;
    private int precioActual;
    private boolean vendido;
    private Persona ultimoPujador;

    public Producto(String nombre, int precioActual, boolean vendido) {
        this.nombre = nombre;
        this.precioActual = precioActual;
        this.vendido = vendido;
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
