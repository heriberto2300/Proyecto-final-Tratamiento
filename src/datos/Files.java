package datos;

import clasificador.Constantes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Files {
    public static ArrayList<String> leerDatos(String nombreArchivo) {
        try {
            FileInputStream fi = new FileInputStream(nombreArchivo);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fi, "utf-8"));
            ArrayList<String> datos = new ArrayList<>();
            String dato;
            while((dato = bufferedReader.readLine()) != null) {
                if(dato.contains(",")) {
                    //int[] parsedDato = Stream.of(dato.split(",")).mapToInt(Integer::parseInt).toArray(); 
                    datos.add(dato);
                }
            }
            bufferedReader.close();
            return datos;
        } catch (IOException err) {err.printStackTrace();}
        return null;
    }
    
    public static void crearARFF(ArrayList<String> datos, String cabecera, String nombre) {
         try {
            FileOutputStream fo = new FileOutputStream(Constantes.PATH + nombre);
            BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(fo, "utf-8"));
            
            bf.append(Constantes.REL + " " + nombre.substring(0, nombre.length() - 5) + "\n\n");
            
            bf.append(cabecera);
            for(String dato : datos) {
                bf.append(dato + "\n");
            }            
            bf.close();
        } catch (IOException err) {err.printStackTrace();}       
    }
    
    public static String initCabeceraARFF(boolean[] tipoAtributos, int[] cabecera, int totalClases, String nombre) {
        String cabeza = "";
        for(int indexAtributo = 0; indexAtributo < cabecera.length - 1; indexAtributo++) {
            if(tipoAtributos[indexAtributo] == Constantes.NOMINAL) {
                cabeza += Constantes.ATT + " " + indexAtributo + " {";
                for(int valorAtributo = 0; valorAtributo < cabecera[indexAtributo]; valorAtributo++) {
                    if(valorAtributo != cabecera[indexAtributo] - 1) {
                        cabeza += valorAtributo + ", ";
                    }else {
                        cabeza += valorAtributo + "}\n";
                    }
                }
            }else {
                cabeza += Constantes.ATT + " " + indexAtributo + " " + Constantes.NUM + "\n";
            }
        }
            
        cabeza += Constantes.ATT + " clase {";
        for(int clase = 0; clase < totalClases; clase++) {
            if(clase != totalClases - 1) {
                cabeza += clase + ", ";
            }else {
                cabeza += clase + "}\n\n";
            }
        }
            
        cabeza += Constantes.DATA + "\n";
        
        return cabeza;
    }
}