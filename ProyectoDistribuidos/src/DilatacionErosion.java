
public class DilatacionErosion {
    
    public int[][] matriz;
    public int elementos;

    public DilatacionErosion(int[][] matriz, int elementos) {
        this.matriz = matriz;
        this.elementos = elementos;
    }

    public int[][] erosion() {

        int fila=matriz.length;
        int colu=matriz[0].length;
        int[][] otra = new int[fila][colu];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                otra[i][j]=matriz[i][j];
            }
        }
        int i,j;

        for(i=1; i<fila-1; i++){
            for(j=1; j<colu-1; j++){
                int min = 255;
                switch (elementos) {
                    case 0:
                        int k[]= new int[5];
                        k[0] = matriz[i][j-1];
                        k[1] = matriz[i-1][j];
                        k[2] = matriz[i][j];
                        k[3] = matriz[i][j+1];
                        k[4] = matriz[i+1][j];
                        for(int l=0;l<5;l++){
                            if(k[l]<min){
                                min = k[l];
                            }
                        }
                        otra[i][j]=min;
                        break;
                    case 1:
                        int kUno[]= new int[3];
                        kUno[0] = matriz[i-1][j];
                        kUno[1] = matriz[i][j];
                        kUno[2] = matriz[i][j+1];
                        for(int l=0;l<3;l++){
                            if(kUno[l]<min){
                                min = kUno[l];
                            }
                        }
                        otra[i][j]=min;
                        break;
                    case 2:
                        int kDos[]= new int[3];
                        kDos[0] = matriz[i-1][j];
                        kDos[1] = matriz[i][j];
                        kDos[2] = matriz[i][j-1];
                        for(int l=0;l<3;l++){
                            if(kDos[l]<min){
                                min = kDos[l];
                            }
                        }
                        otra[i][j]=min;
                        break;
                    case 3:
                        int kTres[]= new int[3];
                        kTres[0] = matriz[i-1][j];
                        kTres[1] = matriz[i][j];
                        kTres[2] = matriz[i+1][j];
                        for(int l=0;l<3;l++){
                            if(kTres[l]<min){
                                min = kTres[l];
                            }
                        }
                        otra[i][j]=min;
                        break;
                    case 4:
                        int kCuatro[]= new int[2];
                        kCuatro[0] = matriz[i][j];
                        kCuatro[1] = matriz[i][j+1];
                        for(int l=0;l<2;l++){
                            if(kCuatro[l]<min){
                                min = kCuatro[l];
                            }
                        }
                        otra[i][j]=min;
                        break;
                    case 5:
                        int kCinco[]= new int[5];
                        kCinco[0] = matriz[i-1][j-1];
                        kCinco[1] = matriz[i-1][j+1];
                        kCinco[2] = matriz[i][j];
                        kCinco[3] = matriz[i+1][j-1];
                        kCinco[4] = matriz[i+1][j+1];
                        for(int l=0;l<5;l++){
                            if(kCinco[l]<min){
                                min = kCinco[l];
                            }
                        }
                        otra[i][j]=min;
                        break;
                    default:
                        break;
                }
            }
        }
        return otra;
    }

    public int[][] dilatacion() {
        int fila=matriz.length;
        int colu=matriz[0].length;
        int[][] otra = new int[fila][colu];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                otra[i][j]=matriz[i][j];
            }
        }
        for(int i=1; i<fila-1; i++){
            for(int j=1; j<colu-1; j++){
                int max =0;
                switch (elementos) {
                    case 0:
                        int k[]= new int[5];
                        k[0] = matriz[i][j-1];
                        k[1] = matriz[i-1][j];
                        k[2] = matriz[i][j];
                        k[3] = matriz[i][j+1];
                        k[4] = matriz[i+1][j];
                        for(int l=0;l<5;l++){
                            if(k[l]>max){
                                max = k[l];
                            }
                        }
                        otra[i][j]=max;
                        break;
                    case 1:
                        int kUno[]= new int[3];
                        kUno[0] = matriz[i-1][j];
                        kUno[1] = matriz[i][j];
                        kUno[2] = matriz[i][j+1];
                        for(int l=0;l<3;l++){
                            if(kUno[l]>max){
                                max = kUno[l];
                            }
                        }
                        otra[i][j]=max;
                        break;
                    case 2:
                        int kDos[]= new int[3];
                        kDos[0] = matriz[i-1][j];
                        kDos[1] = matriz[i][j];
                        kDos[2] = matriz[i][j-1];
                        for(int l=0;l<3;l++){
                            if(kDos[l]>max){
                                max = kDos[l];
                            }
                        }
                        otra[i][j]=max;
                        break;
                    case 3:
                        int kTres[]= new int[3];
                        kTres[0] = matriz[i-1][j];
                        kTres[1] = matriz[i][j];
                        kTres[2] = matriz[i+1][j];
                        for(int l=0;l<3;l++){
                            if(kTres[l]>max){
                                max = kTres[l];
                            }
                        }
                        otra[i][j]=max;
                        break;
                    case 4:
                        int kCuatro[]= new int[2];
                        kCuatro[0] = matriz[i][j];
                        kCuatro[1] = matriz[i][j+1];
                        for(int l=0;l<2;l++){
                            if(kCuatro[l]>max){
                                max = kCuatro[l];
                            }
                        }
                        otra[i][j]=max;
                        break;
                    case 5:
                        int kCinco[]= new int[5];
                        kCinco[0] = matriz[i-1][j-1];
                        kCinco[1] = matriz[i-1][j+1];
                        kCinco[2] = matriz[i][j];
                        kCinco[3] = matriz[i+1][j-1];
                        kCinco[4] = matriz[i+1][j+1];
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
        return otra;
    }
}
