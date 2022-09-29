import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Scanner;


public class LecturaEscritura {

    private int maxvalue;
    private int picWidth;
    private int picHeight;
    private int[][] matriz2d;
    private int[][] matrizFinal;

    public LecturaEscritura() {
        super();
    }

    public void setMatriz2d(int[][] matriz2d) {
        this.matriz2d = matriz2d;
    }

    public int[][] getMatriz2d() {
        return matriz2d;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public int getPicWidth() {
        return picWidth;
    }

    public void setMaxvalue(int maxvalue) {
        this.maxvalue = maxvalue;
    }

    public int getMaxvalue() {
        return maxvalue;
    }

    public void setMatrizFinal(int[][] matrizFinal) {
        this.matrizFinal = matrizFinal;
    }
    public int[][] getMatrizFinal() {
        return matrizFinal;
    }

    public void leer(String nombreArchivo){
        try {
            File myFile = new File(nombreArchivo);
            String filePath = myFile.getCanonicalPath();
            FileInputStream fileInputStream= new FileInputStream(filePath);
            Scanner scan = new Scanner(fileInputStream);
            // Discard the magic number
            scan.nextLine();
            // Discard the comment line
            scan.nextLine();
            // Read pic width, height and max value
            int picWidth = scan.nextInt();
            int picHeight = scan.nextInt();
            int maxvalue = scan.nextInt();
    
            fileInputStream.close();
            scan.close();
    
            // Now parse the file as binary data
            fileInputStream = new FileInputStream(filePath);
            DataInputStream dis = new DataInputStream(fileInputStream);
            
            // look for 4 lines (i.e.: the header) and discard them
            int numnewlines = 4;
            while (numnewlines > 0) {
                char c;
                do {
                    c = (char)(dis.readUnsignedByte());
                } while (c != '\n');
                numnewlines--;
            }
    
            // read the image data
            int[][] data2D = new int[picHeight][picWidth];
            for (int row = 0; row < picHeight; row++) {
                for (int col = 0; col < picWidth; col++) {
                    data2D[row][col] = dis.readUnsignedByte();
                    //System.out.print(data2D[row][col] + " ");
                }
                //System.out.println();
            }
            dis.close();
            
            setMaxvalue(maxvalue);
            setPicHeight(picHeight);
            setPicWidth(picWidth);
            setMatriz2d(data2D);
            setMatrizFinal(data2D);

        } catch (Exception e) {
            System.out.println("error al leer el archivo");
        }
    }
    //funcion de generar un archivo pgm extraida de internet y modificada para su uso en el proyecto
    public void generarPgm(String nombreArchivo,int[][] matriz){
        try{
            File myFile = new File(nombreArchivo);
            String filePath = myFile.getCanonicalPath();
            //specify the name of the output..
            FileWriter fstream = new FileWriter(filePath);
            //we create a new BufferedWriter
            BufferedWriter out = new BufferedWriter(fstream);
            //we add the header, 128 128 is the width-height and 63 is the max value-1 of ur data
            out.write("P2\n# Diego Aguilera Christian Lizama \n"+picWidth+" "+picHeight+"\n"+maxvalue+"\n");
            //2 loops to read the 2d array
            for(int i = 0 ; i<matriz.length;i++){
               for(int j = 0 ; j<matriz[0].length;j++){
                    //we write in the output the value in the position ij of the array
                    out.write(matriz[i][j]+" ");
               }
            }
            //we close the bufferedwritter
            out.close();


        }catch (Exception e){
                System.err.println("Error : " + e.getMessage());
        }
    }
}
