package datos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Files {
    public static ArrayList<String> leerDatos(String nombreArchivo) {
        try {
            FileInputStream fi = new FileInputStream(nombreArchivo);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fi, "utf-8"));
            ArrayList<String> datos = new ArrayList<>();
            String dato;
            
            while((dato = bufferedReader.readLine()) != null) {
                if(dato.contains(",")) {
                    datos.add(dato);
                }
            }
            bufferedReader.close();
            return datos;
        } catch (IOException err) {}
        return null;
    }
}
