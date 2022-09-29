public class MatrizFinal {

    int matrizInicial[][];
    int matrizFinal[][];
    int suma;
    
    public void sumar(){
        suma+=1;
    }

    public MatrizFinal(int matrizInicial[][], int matrizFinal[][]) {
        this.matrizInicial = matrizInicial;
        this.matrizFinal = matrizFinal;
    
    }
    public int[][] getMatrizFinal() {
        return matrizFinal;
    }

    public int[][] getMatrizInicial() {
        return matrizInicial;
    }

    public void setMatrizFinal(int[][] matrizFinal) {
        this.matrizFinal = matrizFinal;
    }
    public synchronized void pintar(int iInicio, int jInicio, int iFinal,int jFinal, int[][] otra,int parte){
        for (int i = iInicio; i < iFinal; i++) {
            for (int j = jInicio; j < jFinal; j++) {
                this.matrizFinal[i][j] = otra[i][j];
            }
        }
    }

    public void setMatrizInicial(int[][] matrizInicial) {
        this.matrizInicial = matrizInicial;
    }

    public void imprimirMatriz(int matriz[][]){
        for (int x=0; x < matriz.length; x++) {
            System.out.print("|");
            for (int y=0; y < matriz[x].length; y++) {
              System.out.print (matriz[x][y]);
              if (y!=matriz[x].length-1) System.out.print("\t");
            }
            System.out.println("|");
        }    
    }
  
}
