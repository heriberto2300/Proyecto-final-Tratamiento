package clasificador;

import datos.Datos;
import java.util.ArrayList;

public class KNN implements Runnable{
    private final int TOTAL_INSTANCIAS;
    private final int TOTAL_INSTANCIAS_PRUEBA;
    
    private Datos datos;
    private ArrayList<String> prueba;
    
    private int k;
    
    
    public KNN(ArrayList<String> entrenamiento, ArrayList<String> prueba, int k) {
        this.TOTAL_INSTANCIAS = entrenamiento.size();
        this.TOTAL_INSTANCIAS_PRUEBA = prueba.size();
        this.datos = new Datos(prueba);
        this.prueba = prueba;
        this.k = k;
    }
    
    
    @Override
    public void run() {
        System.out.println("Hola mundo");
    }
    
}
