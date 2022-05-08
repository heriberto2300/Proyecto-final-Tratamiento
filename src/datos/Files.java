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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            FileOutputStream fo = new FileOutputStream(nombre.substring(0, nombre.length() - 5) + ".arff");
            BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(fo, "utf-8"));
            
            bf.append(Constantes.REL + " " + nombre.substring(0, nombre.length() - 5) + "\n\n");
            
            bf.append(cabecera);
            for(String dato : datos) {
                bf.append(dato + "\n");
            }            
            bf.close();
        } catch (IOException err) {err.printStackTrace();}       
    }
    
    public static String initCabeceraARFF(boolean[] tipoAtributos, int[] cabecera, int totalClases) {
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
    
    public static String initCabeceraARFF(boolean[] tipoAtributos, int[] cabecera, int totalClases, int[] nombreAtributos) {
        String cabeza = "";
        for(int indexAtributo = 0; indexAtributo < cabecera.length - 1; indexAtributo++) {
            if(tipoAtributos[indexAtributo] == Constantes.NOMINAL) {
                cabeza += Constantes.ATT + " " + nombreAtributos[indexAtributo] + " {";
                for(int valorAtributo = 0; valorAtributo < cabecera[indexAtributo]; valorAtributo++) {
                    if(valorAtributo != cabecera[indexAtributo] - 1) {
                        cabeza += valorAtributo + ", ";
                    }else {
                        cabeza += valorAtributo + "}\n";
                    }
                }
            }else {
                cabeza += Constantes.ATT + " " + nombreAtributos[indexAtributo] + " " + Constantes.NUM + "\n";
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
    
    public static void filtrar(ArrayList<String> datos, int[] atributos, int[] cabecera, String nombre) {
        try {
            FileOutputStream fo = new FileOutputStream(nombre);
            BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(fo, "utf-8"));
            
            bf.append(datos.size() + "\n");
            bf.append(atributos.length + "\n");
            
            for(int i = 0; i < atributos.length; i++) {
                bf.append(cabecera[atributos[i]] + ",");
                if(i == atributos.length - 1){
                        bf.append(cabecera[cabecera.length - 1] + "\n");
                }
            }
            
            String[] datoSplit;
            for(String dato : datos) {
                datoSplit = dato.split(",");
                for(int i = 0; i < atributos.length; i++) {
                    bf.append(datoSplit[atributos[i]] + ",");
                    if(i == atributos.length - 1){
                        bf.append(datoSplit[datoSplit.length - 1] + "\n");
                    }
                }
            }
            bf.close();
        } catch (IOException ex) {
            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}