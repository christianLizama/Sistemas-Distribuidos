package com.christian.Datos;

import java.util.ArrayList;

public class Historial {
    String nombreProducto;
    ArrayList<Pujador> pujas;
    public Historial(String nombreProducto) {
        this.nombreProducto = nombreProducto;
        this.pujas = new ArrayList<>();
    }

    public String getNombreProducto() {
        return nombreProducto;
    }
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    public ArrayList<Pujador> getPujas() {
        return pujas;
    }
    public void agregarPersona(Pujador p) {
        pujas.add(p);
    }


}
