
public class Hilo implements Runnable{

    final MatrizFinal matrices;
    int inicio;
    int termino;
    int tipo;
    int elementos;
    int parte;
    LecturaEscritura leerEscribir;

    public Hilo(MatrizFinal matrices, int inicio,int termino, int tipo, int elementos, int parte, LecturaEscritura leerEscribir) {
        this.matrices = matrices;
        this.inicio = inicio;
        this.termino = termino;
        this.tipo = tipo;
        this.elementos = elementos;
        this.parte=parte;
        this.leerEscribir = leerEscribir;
    }
    

    public synchronized void erosion(){
        
        int colu = matrices.getMatrizInicial()[0].length;
        int otra[][] = new int[matrices.getMatrizInicial().length][matrices.getMatrizInicial()[0].length];
        for (int i = 0; i < matrices.getMatrizInicial().length; i++) {
            for (int j = 0; j < matrices.getMatrizInicial()[0].length; j++) {
                otra[i][j]=matrices.getMatrizInicial()[i][j];
            }
        }
        
        for(int i=inicio+1; i<termino; i++){
            for(int j=1; j<colu-1; j++){
                int min =255;
                switch (elementos) {
                    case 0:
                        int k[]= new int[5];
                        k[0] = matrices.getMatrizInicial()[i][j-1];
                        k[1] = matrices.getMatrizInicial()[i-1][j];
                        k[2] = matrices.getMatrizInicial()[i][j];
                        k[3] = matrices.getMatrizInicial()[i][j+1];
                        k[4] = matrices.getMatrizInicial()[i+1][j];
                        for(int l=0;l<5;l++){
                            if(k[l]<min){
                                min = k[l];
                            }
                        }
                        otra[i][j]=min;
                        //matrices.matrizFinal[i][j]=min;
                        //matrices.setPos(i, j, min);
                        break;
                    case 1:
                        int kUno[]= new int[3];
                        kUno[0] = matrices.getMatrizInicial()[i-1][j];
                        kUno[1] = matrices.getMatrizInicial()[i][j];
                        kUno[2] = matrices.getMatrizInicial()[i][j+1];
                        for(int l=0;l<3;l++){
                            if(kUno[l]<min){
                                min = kUno[l];
                            }
                        }
                        //otra[i][j]=min;
                        break;
                    case 2:
                        int kDos[]= new int[3];
                        kDos[0] = matrices.getMatrizInicial()[i-1][j];
                        kDos[1] = matrices.getMatrizInicial()[i][j];
                        kDos[2] = matrices.getMatrizInicial()[i][j-1];
                        for(int l=0;l<3;l++){
                            if(kDos[l]<min){
                                min = kDos[l];
                            }
                        }
                        //otra[i][j]=min;
                        break;
                    case 3:
                        int kTres[]= new int[3];
                        kTres[0] = matrices.getMatrizInicial()[i-1][j];
                        kTres[1] = matrices.getMatrizInicial()[i][j];
                        kTres[2] = matrices.getMatrizInicial()[i+1][j];
                        for(int l=0;l<3;l++){
                            if(kTres[l]<min){
                                min = kTres[l];
                            }
                        }
                        //otra[i][j]=min;
                        break;
                    case 4:
                        int kCuatro[]= new int[2];
                        kCuatro[0] = matrices.getMatrizInicial()[i][j];
                        kCuatro[1] = matrices.getMatrizInicial()[i][j+1];
                        for(int l=0;l<2;l++){
                            if(kCuatro[l]<min){
                                min = kCuatro[l];
                            }
                        }
                        //otra[i][j]=min;
                        break;
                    case 5:
                        int kCinco[]= new int[5];
                        kCinco[0] = matrices.getMatrizInicial()[i-1][j-1];
                        kCinco[1] = matrices.getMatrizInicial()[i-1][j+1];
                        kCinco[2] = matrices.getMatrizInicial()[i][j];
                        kCinco[3] = matrices.getMatrizInicial()[i+1][j-1];
                        kCinco[4] = matrices.getMatrizInicial()[i+1][j+1];
                        for(int l=0;l<5;l++){
                            if(kCinco[l]<min){
                                min = kCinco[l];
                            }
                        }
                        //otra[i][j]=min;
                        break;
                    default:
                        break;
                }
                
            }
        }
        matrices.pintar(inicio, 1, termino, colu, otra,parte);
        //matrices.setMatrizFinal(otra);
    }

    public synchronized void dilatacion(){

        int colu = matrices.getMatrizInicial()[0].length;
        int otra[][] = new int[matrices.getMatrizInicial().length][matrices.getMatrizInicial()[0].length];
        for (int i = 0; i < matrices.getMatrizInicial().length; i++) {
            for (int j = 0; j < matrices.getMatrizInicial()[0].length; j++) {
                otra[i][j]=matrices.getMatrizInicial()[i][j];
            }
        }
        for(int i=inicio; i<termino; i++){
            for(int j=1; j<colu-1; j++){
                int max =0;
                switch (elementos) {
                    case 0:
                        int k[]= new int[5];
                        k[0] = matrices.getMatrizInicial()[i][j-1];
                        k[1] = matrices.getMatrizInicial()[i-1][j];
                        k[2] = matrices.getMatrizInicial()[i][j];
                        k[3] = matrices.getMatrizInicial()[i][j+1];
                        k[4] = matrices.getMatrizInicial()[i+1][j];
                        for(int l=0;l<5;l++){
                            if(k[l]>max){
                                max = k[l];
                            }
                        }
                        otra[i][j]=max;
                        break;
                    case 1:
                        int kUno[]= new int[3];
                        kUno[0] = matrices.getMatrizInicial()[i-1][j];
                        kUno[1] = matrices.getMatrizInicial()[i][j];
                        kUno[2] = matrices.getMatrizInicial()[i][j+1];
                        for(int l=0;l<3;l++){
                            if(kUno[l]>max){
                                max = kUno[l];
                            }
                        }
                        otra[i][j]=max;
                        break;
                    case 2:
                        int kDos[]= new int[3];
                        kDos[0] = matrices.getMatrizInicial()[i-1][j];
                        kDos[1] = matrices.getMatrizInicial()[i][j];
                        kDos[2] = matrices.getMatrizInicial()[i][j-1];
                        for(int l=0;l<3;l++){
                            if(kDos[l]>max){
                                max = kDos[l];
                            }
                        }
                        otra[i][j]=max;
                        break;
                    case 3:
                        int kTres[]= new int[3];
                        kTres[0] = matrices.getMatrizInicial()[i-1][j];
                        kTres[1] = matrices.getMatrizInicial()[i][j];
                        kTres[2] = matrices.getMatrizInicial()[i+1][j];
                        for(int l=0;l<3;l++){
                            if(kTres[l]>max){
                                max = kTres[l];
                            }
                        }
                        otra[i][j]=max;
                        break;
                    case 4:
                        int kCuatro[]= new int[2];
                        kCuatro[0] = matrices.getMatrizInicial()[i][j];
                        kCuatro[1] = matrices.getMatrizInicial()[i][j+1];
                        for(int l=0;l<2;l++){
                            if(kCuatro[l]>max){
                                max = kCuatro[l];
                            }
                        }
                        otra[i][j]=max;
                        break;
                    case 5:
                        int kCinco[]= new int[5];
                        kCinco[0] = matrices.getMatrizInicial()[i-1][j-1];
                        kCinco[1] = matrices.getMatrizInicial()[i-1][j+1];
                        kCinco[2] = matrices.getMatrizInicial()[i][j];
                        kCinco[3] = matrices.getMatrizInicial()[i+1][j-1];
                        kCinco[4] = matrices.getMatrizInicial()[i+1][j+1];
                        for(int l=0;l<5;l++){
                            if(kCinco[l]>max){
                                max = kCinco[l];
                            }
                        }
                        otra[i][j]=max;
                        break;
                    default:
                        break;
                }
            }
        }
        matrices.pintar(inicio, 1, termino, colu, otra,parte);
        //System.out.println("termine:"+parte);


    }

    public void sumar(){
        matrices.sumar();
        //System.out.println(matrices.suma);
    }
    public void run(){
        if(tipo==1){
            dilatacion();
            sumar();
            if(matrices.suma==4){
                leerEscribir.generarPgm("dilatacionParalelo.pgm", matrices.matrizFinal);
                System.out.println("Archivo de dilatacion generado correctamente");
            }
        }
        else{
            erosion();
            sumar();
            if(matrices.suma==4){
                leerEscribir.generarPgm("erosionParalelo.pgm", matrices.matrizFinal);
                System.out.println("Archivo de erosion generado correctamente");
            }
        }
    }
}
