package clasificador;

import datos.Datos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class KNN implements Runnable{
    private final int TOTAL_INSTANCIAS;
    private final int TOTAL_INSTANCIAS_PRUEBA;
    
    private final Datos datos;
    private final ArrayList<int[]> prueba;
    
    private final int k;
    
    
    public KNN(ArrayList<int[]> entrenamiento, ArrayList<int[]> prueba, int k) {
        this.TOTAL_INSTANCIAS = entrenamiento.size() - 1;
        this.TOTAL_INSTANCIAS_PRUEBA = prueba.size() - 1;
        this.datos = new Datos(entrenamiento);
        this.prueba = prueba;
        prueba.remove(0);
        this.k = k;
    }
    
    
    @Override
    public void run() {
        Scanner x = new Scanner(System.in);
        int correctos = 0, incorrectos = 0;
        System.out.println("INICIANDO CLASFICADOR");
        for(int[] instancia : prueba) {
            int[] clases = getCercanos(instancia);
            /*System.out.println("Clases mas cercanas ");
            for(int clase : clases) {
                System.out.println(clase);
            }*/
            
            //System.out.println("LE ASIGNO CLASE " + clases[0]);
            
            if(clases[0] == instancia[Constantes.TOTAL_ATRIBUTOS]) {
                correctos++;
            }else {
                incorrectos++;
            }
            //x.nextLine();
        }
        
        evaluar(correctos, incorrectos);
        
        System.out.println("Clasificador finalizado");
    }
    
    public double hvdm(int[] instanciaA, int[] instanciaB) {
        double resultado = 0.0;
        for(int indexAtributo = 0; indexAtributo < Constantes.TOTAL_ATRIBUTOS; indexAtributo++) {
            if(datos.getTipoAtributo(indexAtributo) == Constantes.NOMINAL) {
                //System.out.println("NOMINAL");
                resultado += Math.pow(normalizedVdm(instanciaA[indexAtributo], instanciaB[indexAtributo], indexAtributo), 2);
            }else {
                //System.out.println("NUMERICO");
                resultado += Math.pow(normalizedDiff(instanciaA[indexAtributo], instanciaB[indexAtributo], indexAtributo), 2);
            }
        }
        
        return Math.sqrt(resultado);
    }
    
    public double normalizedVdm(int valorAtributoA, int valorAtributoB, int indexAtributo) {
        Scanner x = new Scanner(System.in);
        double resultado = 0.0;
        double numA, numB, denA, denB;
        for(int clase = 0; clase < datos.getTotalClases(); clase++) {
            //System.out.println("indexAtributo: " + indexAtributo + ", Atributo A " + valorAtributoA + ", CLASE BUSCADA " + clase);
            //System.out.println("indexAtributo: " + indexAtributo + ", Atributo B " + valorAtributoB + ", CLASE BUSCADA " + clase);
            //x.nextLine();
            numA = datos.NaxC(indexAtributo, valorAtributoA, clase);
            numB = datos.NaxC(indexAtributo, valorAtributoB, clase);
            denA = datos.Nax(indexAtributo, valorAtributoA);
            denB = datos.Nax(indexAtributo, valorAtributoB);
            resultado += Math.abs((numA / denA) - (numB / denB));
        }
        //System.out.println("Normalized VDM es " + resultado);
        //x.nextLine();

        return resultado;
    }
    
    public double normalizedDiff(int valorAtributoA, int valorAtributoB, int indexAtributo) {
        
        //System.out.println("NORMALIZED DIFF ES " + Math.abs(valorAtributoA - valorAtributoB) / 4 * datos.getDesv(indexAtributo));
        
        return Math.abs((double)valorAtributoA - (double)valorAtributoB) / 4 * datos.getDesv(indexAtributo);
    }
    
    public int[] getCercanos(int[] instancia) {
        int[] cercanos = new int[k];
        
        double[][] distancias = new double[TOTAL_INSTANCIAS][2];
        
        //System.out.println("Instancia a evaluar : " + Arrays.toString(instancia));
        
        for(int i = 0; i <TOTAL_INSTANCIAS; i++) {
            distancias[i][0] = datos.getInstancia(i)[Constantes.TOTAL_ATRIBUTOS];
            distancias[i][1] = hvdm(instancia, datos.getInstancia(i));
            //System.out.println("Evaluando con " + Arrays.toString(datos.getInstancia(i)) + " con distancia " + distancias[i][1]);

        }
        
        double tempClase, tempDistancia;
        for(int i = 0; i < TOTAL_INSTANCIAS - 1; i++) {
            for(int j = i + 1; j< TOTAL_INSTANCIAS; j++) {
                if(distancias[i][1] > distancias[j][1]) {
                    tempClase = distancias[i][0];
                    tempDistancia = distancias[i][1];
                    
                    distancias[i][0] = distancias[j][0];
                    distancias[i][1] = distancias[j][1];
                    
                    distancias[j][0] = tempClase;
                    distancias[j][1] = tempDistancia;
                }
            }
        }
        
        for(int i = 0; i < k; i++) {
            cercanos[i] = (int)distancias[i][0];
        }
        
        return cercanos;
    }
    
    public void evaluar(int correctos, int incorrectos) {
        System.out.println("INSTANCIAS CORRECTAMENTE CLASIFICADAS " + correctos);
        System.out.println("INSTANCIAS INCORRECTAMENTE CLASIFICADAS " + incorrectos);
        double yes = (double)correctos * 100 / TOTAL_INSTANCIAS_PRUEBA;
        double no = (double)incorrectos * 100 / TOTAL_INSTANCIAS_PRUEBA;
        System.out.println("PORCENTAJE DE EXACTITUD = " + yes);
        System.out.println("PORCENTAJE DE ERROR = " + no);
        double total = no + yes;
        System.out.println("PORCENTAJE TOTAL = " + total);
    }
}
