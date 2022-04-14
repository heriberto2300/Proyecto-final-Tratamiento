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
    
    public double hvdm(int[] instanciaA, int[] instanciaB) {
        double resultado = 0.0;
        for(int indexAtributo = 0; indexAtributo < Constantes.TOTAL_ATRIBUTOS; indexAtributo++) {
            if(datos.getTipoAtributo(indexAtributo) == Constantes.NOMINAL) {
                resultado += Math.pow(normalizedVdm(instanciaA[indexAtributo], instanciaB[indexAtributo], indexAtributo), 2);
            }else {
                resultado += Math.pow(normalizedDiff(instanciaA[indexAtributo], instanciaB[indexAtributo], indexAtributo), 2);
            }
        }
        
        return Math.sqrt(resultado);
    }
    
    public double normalizedVdm(int valorAtributoA, int valorAtributoB, int indexAtributo) {
        double resultado = 0.0;
        int numA, numB, denA, denB;
        for(int clase = 0; clase < datos.getTotalClases(); clase++) {
            numA = datos.NaxC(indexAtributo, valorAtributoA, clase);
            numB = datos.NaxC(indexAtributo, valorAtributoB, clase);
            denA = datos.Nax(indexAtributo, valorAtributoA);
            denB = datos.Nax(indexAtributo, valorAtributoB);
            resultado += Math.abs((numA / denA) - (numB / denB));
        }
        return resultado;
    }
    
    public double normalizedDiff(int valorAtributoA, int valorAtributoB, int indexAtributo) {
        return Math.abs(valorAtributoA - valorAtributoB) / 4 * datos.getDesv(indexAtributo);
    }
}
