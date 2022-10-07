public class Persona {

    private String nombre;
    private int precioPuja;

    public Persona(String nombre) {
        this.nombre = nombre;
        this.precioPuja = 0;
    }

    public int getPrecio() {
        return precioPuja;
    }
    public void setPrecio(int precio) {
        this.precioPuja = precio;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
}
