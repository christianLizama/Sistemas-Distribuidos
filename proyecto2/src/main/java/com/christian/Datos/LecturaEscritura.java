package com.christian.Datos;

import com.christian.Productos.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LecturaEscritura {

    private ArrayList<Producto> productos;
    
    public ArrayList<Producto> leer(){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String json = "";
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File ("..........Datos\\productos.json");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                json+=linea;
            }
            br.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try{                    
                if( null != fr ){   
                fr.close();     
                }                  
            }catch (Exception e2){ 
                e2.printStackTrace();
            }
        }

        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<Producto>>(){}.getType();
        productos = gson.fromJson(json, userListType);      

        return productos;
    }

}
