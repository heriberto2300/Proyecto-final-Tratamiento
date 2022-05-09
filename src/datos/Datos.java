package datos;

import clasificador.Constantes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Stream;
import math.Funciones;

public class Datos {
    private final int TOTAL_INSTANCIAS;
    private int TOTAL_ATRIBUTOS;

    
    private int totalNumericos;
    private int totalNominales;
    private int totalClases;
    
    private final ArrayList<String> datos;
    
    private boolean[] tipoAtributos;
    private int[] cabecera;
    
    private int[][] matrizAV;
    private Map<Integer, Integer>[][] matrizAVC; 
    private Map<Integer, Double> desvNumericos;
    
    private int maximo;
    
    public Datos(ArrayList<String> datos, String detalles) {
        init(datos.get(0));
        datos.remove(0);
        this.datos = datos;
        TOTAL_INSTANCIAS = datos.size();
        initMatrizAV();
        initMatrizAVC();
        initDesviaciones();
        
        switch(detalles) {
            case "v":
                test();
                break;
            case "vv":
                testDetallado();
                break;
        }
        
    }
    
    private void init(String cabeza) {
        cabecera = Stream.of(cabeza.split(",")).mapToInt(Integer::parseInt).toArray();
        TOTAL_ATRIBUTOS = cabecera.length - 1;
        tipoAtributos = new boolean[TOTAL_ATRIBUTOS];
        
        int[] aux = Arrays.copyOfRange(cabecera, 0, cabecera.length - 1);
        maximo = Arrays.stream(aux).max().getAsInt();
        
        for(int i = 0; i < TOTAL_ATRIBUTOS; i++) {
            if(cabecera[i] == 0) {
                tipoAtributos[i] = Constantes.NUMERICO;
                totalNumericos++;
            }else {
                tipoAtributos[i] = Constantes.NOMINAL;
                totalNominales++;
            }
        }
        
        totalClases = cabecera[TOTAL_ATRIBUTOS];
    }
    
    private void initMatrizAV() {
        OptionalInt op = Arrays.stream(cabecera).max();
        
        matrizAV = new int[maximo][TOTAL_ATRIBUTOS]; 
        
        int valorAtributo;
        for(String dato : datos) {
            for(int indexAtributo = 0; indexAtributo < TOTAL_ATRIBUTOS; indexAtributo++) {
                if(tipoAtributos[indexAtributo] == Constantes.NOMINAL) {
                    valorAtributo = Integer.parseInt(dato.split(",")[indexAtributo]);
                    matrizAV[valorAtributo][indexAtributo]++;
                }
            }
        }
    }
    
    private void initMatrizAVC() {
        OptionalInt op = Arrays.stream(cabecera).max();
        
        matrizAVC = new HashMap[maximo][TOTAL_ATRIBUTOS];    
        
        for(int j = 0; j < TOTAL_ATRIBUTOS; j++) {
            if(tipoAtributos[j] == Constantes.NOMINAL) {
                for(int i = 0; i < cabecera[j]; i++) {
                    matrizAVC[i][j] = new HashMap<>();
                    for(int clase = 0; clase < totalClases; clase++) {
                        matrizAVC[i][j].put(clase, 0);
                    }    
                }    
            }
        }
        
        int aux;
        int valorAtributo;
        for(String dato : datos) {
            for(int indexAtributo = 0; indexAtributo < TOTAL_ATRIBUTOS; indexAtributo++) {
                if(tipoAtributos[indexAtributo] == Constantes.NOMINAL) {
                    for(int clase = 0; clase < totalClases; clase++) {
                        if(clase == Integer.parseInt(dato.split(",")[TOTAL_ATRIBUTOS])) {
                            valorAtributo = Integer.parseInt(dato.split(",")[indexAtributo]);
                            aux =  matrizAVC[valorAtributo][indexAtributo].get(clase) + 1;
                            matrizAVC[valorAtributo][indexAtributo].put(clase, aux);
                        }
                    }    
                }
            }
        }
    }
    
    private void initDesviaciones() {
        desvNumericos = new HashMap<>();
        double[] valores;
        double desv;
        for(int i = 0; i < TOTAL_ATRIBUTOS; i++) {
            if(tipoAtributos[i] == Constantes.NUMERICO) {
                valores = getValores(i);
                desv = Funciones.desvEstandar(valores);
                desvNumericos.put(i, desv);
            }
        }
    }
    
    public double[] getValores(int indexAtributo) {
        double[] valores = new double[TOTAL_INSTANCIAS];

        for(int i = 0; i < TOTAL_INSTANCIAS; i++) {
            valores[i] = Double.parseDouble(datos.get(i).split(",")[indexAtributo]);
        }
        return valores;
    }
    
    public boolean getTipoAtributo(int indexAtributo) {
        return tipoAtributos[indexAtributo];
    }
    
    public int getTotalClases() {
        return totalClases;
    }
    
    public double getDesv(int indexAtributo) {
        return desvNumericos.get(indexAtributo);
    }
    
    public String getInstancia(int index) {
        return datos.get(index);
    }
    
    public int getTotalInstancias() {
        return TOTAL_INSTANCIAS;
    }
    
    public boolean[] getTipoAtributos() {
        return tipoAtributos;
    }
    
    public int[] getCabecera() {
        return cabecera;
    }
    
    public int getTotalAtributos() {
        return TOTAL_ATRIBUTOS;
    }
    
    public int NaxC (int indexAtributo, int valor, int clase) {
        return matrizAVC[valor][indexAtributo].get(clase);
    }
    
    public int Nax(int indexAtributo, int valor) {
        return matrizAV[valor][indexAtributo];
    }
    
    private void test() {
        System.out.println("------------DETALLES DEL CONJUNTO DE ENTRENAMIENTO----------\n");
        System.out.println("TOTAL ATRIBUTOS = " + TOTAL_ATRIBUTOS);
        System.out.println("TOTAL INSTANCIAS = " + TOTAL_INSTANCIAS);
        System.out.println("TOTAL NUMERICOS = " + totalNumericos);
        System.out.println("TOTAL NOMINALES = " + totalNominales);
        System.out.println("TOTAL CLASES = " + totalClases);

        System.out.println();
        System.out.println("-----CABECERA-----\n");
        for(int cabeza : cabecera) {
            System.out.print(cabeza + " ");
        }
        System.out.println("\n");
        System.out.println("------TIPOS DE DATOS-----\n");
        for(boolean atributo : tipoAtributos) {
            if(atributo == Constantes.NOMINAL) {
                System.out.println("NOMINAL");
            }else {
                System.out.println("NUMERICO");
            }
        }
        System.out.println();
    }
    
    private void testDetallado() {
        test();
        
        System.out.println("-----DISTRIBUCION DE LOS ATRIBUTOS NOMINALES RESPECTO A SU VALOR Y VALOR DE CLASE (matriz AVC)-----\n");
        
        for(int j = 0; j < TOTAL_ATRIBUTOS; j++) {
            for(int i = 0; i < maximo; i++) {
                if(matrizAVC[i][j] == null) {
                    System.out.println("Atributo " + j + " Con valor " + i + " Se distribuye de la siguiente forma: NULL");
                }else {
                    System.out.println("Atributo " + j + " Con valor " + i + " Se distribuye de la siguiente forma " + matrizAVC[i][j]);

                }
            }
        }
        System.out.println();
        
        System.out.println("-----DISTRIBUCION DE LOS ATRIBUTOS NOMINALES RESPECTO A SU VALOR (matriz AV)-----\n");
        
        for(int i = 0; i < maximo; i++) {
            for(int j = 0; j < TOTAL_ATRIBUTOS; j++) {
                System.out.print(matrizAV[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        
        System.out.println("-----DESVIACION ESTANDAR DE LOS ATRIBUTOS NUMERICOS-----\n");
        System.out.println(desvNumericos);

    }
    
}
