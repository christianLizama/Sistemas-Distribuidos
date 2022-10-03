package Cliente;
import java.util.Scanner;

public class PanelCliente{
    private String persona="";
    private String op="";

    public PanelCliente(Scanner lectura){
        System.out.println("1) Anotar persona en la lista");
        System.out.println("2) Mostrar ");
        System.out.print("Ingrese opcion: ");
        String opcion = lectura.nextLine();

        switch (opcion) {
            case "1":
                //Agregar info
                op = "1";
                System.out.print("Ingresar nombre:");
                persona = lectura.nextLine();
                break;
            case "2":
                //Pedir info
                persona="#";
                op = "2";
            break;
            default:
                break;
        }
    }

    public String getOp() {
        return op;
    }

    public String getPersona() {
        return persona;
    }
}

