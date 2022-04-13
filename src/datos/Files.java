package datos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Files {
    public static ArrayList<int[]> leerDatos(String nombreArchivo) {
        try {
            FileInputStream fi = new FileInputStream(nombreArchivo);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fi, "utf-8"));
            ArrayList<int[]> datos = new ArrayList<>();
            String dato;
            while((dato = bufferedReader.readLine()) != null) {
                if(dato.contains(",")) {
                    int[] parsedDato = Stream.of(dato.split(",")).mapToInt(Integer::parseInt).toArray(); 
                    datos.add(parsedDato);
                }
            }
            bufferedReader.close();
            return datos;
        } catch (IOException err) {err.printStackTrace();}
        return null;
    }
}
