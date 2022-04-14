package datos;

import clasificador.Constantes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import math.Funciones;

public class Datos {
    private final int TOTAL_INSTANCIAS;
    
    private int totalNumericos;
    private int totalNominales;
    private int totalClases;
    
    private final ArrayList<int[]> datos;
    
    private boolean[] tipoAtributos;
    private int[] cabecera;
    
    private int[][] matrizAV;
    private Map<Integer, Integer>[][] matrizAVC; 
    private Map<Integer, Double> desvNumericos;
    
    public Datos(ArrayList<int[]> datos) {
        init(datos.get(0));
        datos.remove(0);
        this.datos = datos;
        TOTAL_INSTANCIAS = datos.size();
        initMatrizAV();
        initMatrizAVC();
        initDesviaciones();
        test();
    }
    
    private void init(int[] cabeza) {
        tipoAtributos = new boolean[Constantes.TOTAL_ATRIBUTOS];
        cabecera = new int[Constantes.TOTAL_ATRIBUTOS];
        int valor;
        for(int i = 0; i < Constantes.TOTAL_ATRIBUTOS; i++) {
            valor = cabeza[i];
            if(valor == 0) {
                tipoAtributos[i] = Constantes.NUMERICO;
                totalNumericos++;
            }else {
                tipoAtributos[i] = Constantes.NOMINAL;
                totalNominales++;
            }
            cabecera[i] = valor;
        }
        
        totalClases = cabeza[Constantes.TOTAL_ATRIBUTOS];
    }
    
    //TODO : MODIFICAR DE TAL FORMA QUE SOLO GUARDE NOMINALES. AQUI SE ENCUENTRA EL CONTEO DE NUMERICOS TAMBIEN
    private void initMatrizAV() {
        OptionalInt op = Arrays.stream(cabecera).max();
        int maximo = op.getAsInt();
        
        matrizAV = new int[maximo][Constantes.TOTAL_ATRIBUTOS]; //es totalNumericos en el segundo indice
        
        for(int[] dato : datos) {
            for(int indexAtributo = 0; indexAtributo < Constantes.TOTAL_ATRIBUTOS; indexAtributo++) {
                matrizAV[dato[indexAtributo]][indexAtributo]++;
            }
        }
        
        for(int i = 0; i < maximo; i++) {
            for(int j = 0; j < Constantes.TOTAL_ATRIBUTOS; j++) {
                System.out.print(matrizAV[i][j] + " ");
            }
            System.out.println();
        } 
        //Nota: Necesito el total de los nominales y el total de los numericos para sustituir el totalAtributos en esta parte del codigo
        //NOTA2: LA MATRIZ ES [MAXIMO][TOTAL_NOMINALES]
    }
    
    private void initMatrizAVC() {
        OptionalInt op = Arrays.stream(cabecera).max();
        int maximo = op.getAsInt();
        
        matrizAVC = new HashMap[maximo][Constantes.TOTAL_ATRIBUTOS];    
        
        for(int j = 0; j < Constantes.TOTAL_ATRIBUTOS; j++) {
            for(int i = 0; i < cabecera[j]; i++) {
                matrizAVC[i][j] = new HashMap<>();
                for(int clase = 0; clase < totalClases; clase++) {
                    matrizAVC[i][j].put(clase, 0);
                }
            }
        }
        
        int aux;
        for(int[] dato : datos) {
            for(int indexAtributo = 0; indexAtributo < Constantes.TOTAL_ATRIBUTOS; indexAtributo++) {
                if(tipoAtributos[indexAtributo] != Constantes.NUMERICO) {
                    for(int clase = 0; clase < totalClases; clase++) {
                        if(clase == dato[Constantes.TOTAL_ATRIBUTOS]) {
                            aux =  matrizAVC[dato[indexAtributo]][indexAtributo].get(clase) + 1;
                            matrizAVC[dato[indexAtributo]][indexAtributo].put(clase, aux);
                        }
                    }    
                }
            }
        }
        
        for(int j = 0; j < Constantes.TOTAL_ATRIBUTOS; j++) {
            for(int i = 0; i < maximo; i++) {
                if(matrizAVC[i][j] == null) {
                    System.out.println("Atributo " + j + " Con valor " + i + " Se distribuye de la siguiente forma; NULL");
                }else {
                    System.out.println("Atributo " + j + " Con valor " + i + " Se distribuye de la siguiente forma " + matrizAVC[i][j]);

                }
            }
        }
    }
    
    private void initDesviaciones() {
        desvNumericos = new HashMap<>();
        int[] valores;
        double desv;
        for(int i = 0; i < Constantes.TOTAL_ATRIBUTOS; i++) {
            if(tipoAtributos[i] == Constantes.NUMERICO) {
                System.out.println("DETALLES DE ATRIBUTO " + i);
                valores = getValores(i);
                desv = Funciones.desvEstandar(valores);
                System.out.println(Arrays.toString(valores));
                System.out.println(desv);
                desvNumericos.put(i, desv);
            }
        }
        
        System.out.println("HASMAP DESVIACIONES" + desvNumericos);
    }
    
    private void test() {
        System.out.println("TOTAL ATRIBUTOS = " + cabecera.length);
        System.out.println("TOTAL INSTANCIAS = " + TOTAL_INSTANCIAS);
        System.out.println("TOTAL NUMERICOS = " + totalNumericos);
        System.out.println("TOTAL NOMINALES = " + totalNominales);
        System.out.println("TOTAL CLASES = " + totalClases);

        System.out.println();
        System.out.println("CABECERA");
        for(int cabeza : cabecera) {
            System.out.print(cabeza + " ");
        }
        System.out.println();
        System.out.println("TIPOS DE DATOS");
        for(boolean atributo : tipoAtributos) {
            if(atributo == Constantes.NOMINAL) {
                System.out.println("NOMINAL");
            }else {
                System.out.println("NUMERICO");
            }
        }
        System.out.println();
        System.out.println("INSTANCIAS:");
        for(int[] dato : datos) {
            System.out.println(Arrays.toString(dato));
        }
    }
    
    public int[] getValores(int indexAtributo) {
        int[] valores = new int[TOTAL_INSTANCIAS];
        for(int i = 0; i < TOTAL_INSTANCIAS; i++) {
            valores[i] = datos.get(i)[indexAtributo];
        }
        return valores;
    }
    
}
