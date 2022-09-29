import java.util.Scanner;

public class App {
    
    
    public static void imprimirMenu() {
        System.out.println("Elementos estructurantes:");
        System.out.println("     _                                       _   _ ");
        System.out.println("1) _|_|_    2)  _ _  3)  _ _ _   4)  _   5) |_|_|_|");
        System.out.println("  |_|_|_|      |_|_|    |_|_|_|     |_|      _|_|_ ");
        System.out.println("    |_|          |_|                |_|     |_| |_|");
        System.out.println();
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        LecturaEscritura lecturaYescritura = new LecturaEscritura();
        System.out.print("ingrese nombre del archivo: ");
        String nombreArchivo = sc.nextLine();
        
        lecturaYescritura.leer(nombreArchivo);
        
        int[][] original = lecturaYescritura.getMatriz2d();
        int[][] copia = lecturaYescritura.getMatrizFinal();
        imprimirMenu();
        boolean valido=true;
        int opcionE=0;
    
        while(valido){
            System.out.print("Seleccione opcion: ");
            String opcionElementos = sc.nextLine();
            opcionE = Integer.parseInt(opcionElementos)-1;
            if(opcionE>-1 && opcionE<5){
                valido=false;
                
            }
            else{
                System.out.println("Opcion ingresada no es valida");
            }
        }
        
        boolean valido2=true;
        while (valido2) {

            System.out.println("Seleccione tipo algoritmo: ");
            System.out.println("1) Paralelo");
            System.out.println("2) Secuencial");
            System.out.print("Seleccione opcion: ");
            String opcionAlgoritmo = sc.nextLine();
            int opcionAlg=Integer.parseInt(opcionAlgoritmo);
            switch (opcionAlg) {
                case 1:
                    System.out.println("Has seleccionado el algoritmo paralelo\n");
                    
                    //Hilos dilatacion
                    MatrizFinal matriz = new MatrizFinal(original,copia);
                    valido2=false;
                    Boolean valido3=true;
                    while (valido3) {
                        System.out.println("Seleccione tecnica: ");
                        System.out.println("1) Dilatacion");
                        System.out.println("2) Erosion");
                        System.out.print("Seleccione opcion: ");
                        String opcionTecnica = sc.nextLine();
                        int opcionTec=Integer.parseInt(opcionTecnica);
                        switch (opcionTec) {
                            case 1:
                            //Dilatacion
                                valido3=false;

                                long inicio = System.currentTimeMillis();
                                Thread t1 = new Thread(new Hilo(matriz,1,((original.length/2)/2),1,opcionE,1,lecturaYescritura));
                                Thread t2 = new Thread(new Hilo(matriz,(((original.length/2)/2)-1),(original.length/2)+1,1,opcionE,2,lecturaYescritura));
                                Thread t3 = new Thread(new Hilo(matriz,(original.length/2), (original.length/2)+((original.length/2)/2)+1,1,opcionE,3,lecturaYescritura));
                                Thread t4 = new Thread(new Hilo(matriz,((original.length/2)+((original.length/2)/2)), original.length-1,1,opcionE,4,lecturaYescritura));
                                
                                t1.start();
                                t2.start();
                                t3.start();
                                t4.start();
                                
                
                                long fin = System.currentTimeMillis();
                                double tiempo = (double) ((fin - inicio));
                                System.out.println(tiempo +" milisegundos");
                                break;
                            case 2:
                            //erosion paralelo
                                valido3=false;
                                long inicio2 = System.currentTimeMillis();
                                Thread t5 = new Thread(new Hilo(matriz,0,((original.length/2)/2),2,opcionE,1,lecturaYescritura));
                                Thread t6 = new Thread(new Hilo(matriz,(((original.length/2)/2)-1),(original.length/2)+1,2,opcionE,2,lecturaYescritura));
                                Thread t7 = new Thread(new Hilo(matriz,(original.length/2), (original.length/2)+((original.length/2)/2)+1,2,opcionE,3,lecturaYescritura));
                                Thread t8 = new Thread(new Hilo(matriz,((original.length/2)+((original.length/2)/2)), original.length-1,2,opcionE,4,lecturaYescritura));
                                
                                t5.start();
                                t6.start();
                                t7.start();
                                t8.start();
                                
                
                                long fin2 = System.currentTimeMillis();
                                double tiempo2 = (double) ((fin2 - inicio2));
                                System.out.println(tiempo2 +" milisegundos");
                                break;
                        
                            default:
                                break;
                        }
                    }
                    
                    valido2=false;
                    break;
    
                case 2:
                    valido2=false;
                    // Leyendo datos usando readLine
                    System.out.println("Has seleccionado el algoritmo secuencial\n");
                    boolean valido4=true;
                    while (valido4) {
                        System.out.println("Seleccione tecnica: ");
                        System.out.println("1) Dilatacion");
                        System.out.println("2) Erosion");
                        System.out.print("Seleccione opcion: ");
                        String opcionTecnica = sc.nextLine();
                        int opcionTec=Integer.parseInt(opcionTecnica);
                        switch (opcionTec) {
                            case 1:
                                valido4=false;
                                long inicio2 = System.currentTimeMillis();
                                DilatacionErosion dilatacionErosion = new DilatacionErosion(lecturaYescritura.getMatriz2d(),opcionE);
                                String nombreArchivo3 = "dilatacionSecuencial.pgm";
                                
                                int [][] resultadoDilatacion = dilatacionErosion.dilatacion();
                                long fin2 = System.currentTimeMillis();
                                double tiempo2 = (double) ((fin2 - inicio2));
                                System.out.println(tiempo2 +" milisegundos");
                                lecturaYescritura.generarPgm(nombreArchivo3,  resultadoDilatacion);
                                System.out.println("Archivo dilatacion generado correctamente");
                                break;
                            case 2:
                                valido4=false;
                                long inicio3 = System.currentTimeMillis();
                                DilatacionErosion dilatacionErosion2 = new DilatacionErosion(lecturaYescritura.getMatriz2d(),opcionE);
                                String nombreArchivo4 = "erosionSecuencial.pgm";
                                
                                int [][] resultadoErosion = dilatacionErosion2.erosion();
                                long fin3 = System.currentTimeMillis();
                                double tiempo3 = (double) ((fin3 - inicio3));
                                System.out.println(tiempo3 +" milisegundos");
                                lecturaYescritura.generarPgm(nombreArchivo4,  resultadoErosion);
                                System.out.println("Archivo erosion generado correctamente");
                            break;
                        
                            default:
                            
                                break;
                        }
                    }
                    valido2=false;
                    break;
            
                default:
                    System.out.println("Opcion ingresada no es valida");
                    break;
            }
        }

            
        
        sc.close();
        
    }
}
