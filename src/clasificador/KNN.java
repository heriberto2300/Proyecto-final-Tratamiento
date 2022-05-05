package clasificador;

import datos.Datos;
import java.util.ArrayList;
import java.util.Arrays;
import math.Funciones;

public class KNN implements Runnable{
    private final int TOTAL_INSTANCIAS;
    private final int TOTAL_INSTANCIAS_PRUEBA;
    private final int TOTAL_ATRIBUTOS;
    
    private final Datos datos;
    private final ArrayList<String> prueba;
    
    private final int k;
    
    private final boolean detalles;
    
    public KNN(Datos datos, ArrayList<String> prueba, int k, boolean detalles) {
        this.datos = datos;
        this.TOTAL_INSTANCIAS =  datos.getTotalInstancias();
        this.TOTAL_INSTANCIAS_PRUEBA = prueba.size() - 1;
        this.TOTAL_ATRIBUTOS = datos.getTotalAtributos();
        this.prueba = prueba;
        prueba.remove(0);
        this.k = k;
        this.detalles = detalles;
    }
    
    
    @Override
    public void run() {
        int correctos = 0, incorrectos = 0;
        int i = 0;
        int[][] datosMatriz = new int[TOTAL_INSTANCIAS_PRUEBA][2];
        int indexClase = TOTAL_ATRIBUTOS;
        int claseInstancia;

        for(String instancia : prueba) {
            int[] clases = getCercanos(instancia);
            int clase = Funciones.moda(clases);

            if(detalles) {
                System.out.println("Clasificando: " + instancia);
                System.out.println("Clases mas cercanas ");
                for(int c : clases) {
                    System.out.println(c);
                }
                System.out.println("Clase asignada a instancia: " + clase); 
            }
            
            claseInstancia = Integer.parseInt(instancia.split(",")[indexClase]);
            if(clase == claseInstancia) {
                correctos++;
            }else {
                incorrectos++;
            }
            
            datosMatriz[i][0] = claseInstancia; //clase real
            datosMatriz[i][1] = clase; //clase asignada;
            i++;
        }
        
        evaluar(correctos, incorrectos, datosMatriz);
        
    }
    
    public double hvdm(String instanciaA, String instanciaB) {
        String[] insA = instanciaA.split(",");
        String[] insB = instanciaB.split(",");
        double resultado = 0.0;
        double valorAtributoANum, valorAtributoBNum;
        int valorAtributoANom, valorAtributoBNom;
        
        for(int indexAtributo = 0; indexAtributo < TOTAL_ATRIBUTOS; indexAtributo++) {
            if(datos.getTipoAtributo(indexAtributo) == Constantes.NOMINAL) {
                valorAtributoANom = Integer.parseInt(insA[indexAtributo]);
                valorAtributoBNom = Integer.parseInt(insB[indexAtributo]);
                try {
                    resultado += Math.pow(normalizedVdm(valorAtributoANom, valorAtributoBNom, indexAtributo), 2);
                }catch(NullPointerException | ArrayIndexOutOfBoundsException err) {
                    System.out.println("Fallo en instancia A de prueba: " + instanciaA);
                    System.out.println("Fallo en instancia B Entrenamiento: " + instanciaB);
                    System.out.println("indices A : indexAtributo = " + indexAtributo + ", valor = " + valorAtributoANom);
                    System.out.println("indices A : indexAtributo = " + indexAtributo + ", valor = " + valorAtributoBNom);
                    err.printStackTrace();
                    System.exit(0);
                }
            }else {
                valorAtributoANum = Double.parseDouble(insA[indexAtributo]);
                valorAtributoBNum = Double.parseDouble(insB[indexAtributo]);
                resultado += Math.pow(normalizedDiff(valorAtributoANum, valorAtributoBNum, indexAtributo), 2);
            }
        }
        return Math.sqrt(resultado);
    }
    
    public double normalizedVdm(int valorAtributoA, int valorAtributoB, int indexAtributo) {
        double resultado = 0.0;
        double numA, numB, denA, denB;

        for(int clase = 0; clase < datos.getTotalClases(); clase++) {
            numA = (double)datos.NaxC(indexAtributo, valorAtributoA, clase);
            numB = (double)datos.NaxC(indexAtributo, valorAtributoB, clase);
            denA = (double)datos.Nax(indexAtributo, valorAtributoA);
            denB = (double)datos.Nax(indexAtributo, valorAtributoB);
            resultado += Math.abs((numA / denA) - (numB / denB));
        }
        return resultado;
    }
    
    public double normalizedDiff(double valorAtributoA, double valorAtributoB, int indexAtributo) {
        return Math.abs(valorAtributoA - valorAtributoB) / 4 * datos.getDesv(indexAtributo);
    }
    
    public int[] getCercanos(String instanciaPrueba) {
        int[] cercanos = new int[k];
        double[][] distancias = new double[TOTAL_INSTANCIAS][2];
        int indexClase = TOTAL_ATRIBUTOS;
        
        for(int i = 0; i < TOTAL_INSTANCIAS; i++) {
            distancias[i][0] = Double.parseDouble(datos.getInstancia(i).split(",")[indexClase]);
            distancias[i][1] = hvdm(instanciaPrueba, datos.getInstancia(i));
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
    
    public void evaluar(int correctos, int incorrectos, int[][] datosMatriz) {
        System.out.println("\n------RESULTADOS, K = " + k +"------\n");
        System.out.println("INSTANCIAS CORRECTAMENTE CLASIFICADAS " + correctos);
        System.out.println("INSTANCIAS INCORRECTAMENTE CLASIFICADAS " + incorrectos);
        double yes = (double)correctos * 100 / TOTAL_INSTANCIAS_PRUEBA;
        double no = (double)incorrectos * 100 / TOTAL_INSTANCIAS_PRUEBA;
        System.out.println("PORCENTAJE DE EXACTITUD = " + yes + "%");
        System.out.println("PORCENTAJE DE ERROR = " + no + "%");
        double total = no + yes;
        System.out.println("PORCENTAJE TOTAL = " + total + "%");
        
        Matriz confusion = new Matriz(datos.getTotalClases(), datosMatriz);
        System.out.println("\n------MATRIZ DE CONFUSION:------\n");
        confusion.imprimir();
    }
}
