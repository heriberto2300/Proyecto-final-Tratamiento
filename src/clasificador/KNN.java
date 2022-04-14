package clasificador;

import datos.Datos;
import java.util.ArrayList;

public class KNN implements Runnable{
    private final int TOTAL_INSTANCIAS;
    private final int TOTAL_INSTANCIAS_PRUEBA;
    
    private Datos datos;
    private ArrayList<int[]> prueba;
    
    private int k;
    
    
    public KNN(ArrayList<int[]> entrenamiento, ArrayList<int[]> prueba, int k) {
        this.TOTAL_INSTANCIAS = entrenamiento.size();
        this.TOTAL_INSTANCIAS_PRUEBA = prueba.size();
        this.datos = new Datos(entrenamiento);
        this.prueba = prueba;
        this.k = k;
    }
    
    
    @Override
    public void run() {
        //System.out.println(datos.Nax(4, 2));
        System.out.println(datos.NaxC(3, 1, 3));
        System.out.println("Clasificador iniciado");
    }
    
    
}
