import java.util.ArrayList;

public class Pujas {

    private ArrayList<Persona> personas;
    private ArrayList<Producto> productos;

    public Pujas() {
        this.personas = new ArrayList<>();
        this.productos = new ArrayList<>();
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(ArrayList<Persona> personas) {
        this.personas = personas;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void mostrarProductosPersonas(){
        for (Producto producto : productos) {
            producto.mostrarPersonas();   
        }
    }

    public void agregarProductos(){
        Producto producto1= new Producto("Auto",personas, 0);
        Producto producto2= new Producto("Casa",personas, 0);
        Producto producto3= new Producto("Moto",personas, 0);
        
        productos.add(producto1);
        productos.add(producto2);
        productos.add(producto3);
    }

    public void eliminarProducto(Producto producto){
        productos.remove(producto);                
    }

    public void eliminarPersona(Persona persona){
        personas.remove(persona);
    }

    public void agregarPersona(Persona persona){
        personas.add(persona);
    }
    
}
