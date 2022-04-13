package datos;

import clasificador.Constantes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Datos {
    private final int TOTAL_INSTANCIAS;
    
    private int totalNumericos;
    private int totalNominales;
    
    private final ArrayList<int[]> datos;
    
    private boolean[] tipoAtributos;
    private int[] cabecera;
    
    private int[][] matrizAV;
    private Map<Integer, Double> desvNumericos;
    
    public Datos(ArrayList<int[]> datos) {
        init(datos.get(0));
        datos.remove(0);
        this.datos = datos;
        TOTAL_INSTANCIAS = datos.size();
        initMatrizAV();
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
        
        
    }
    
    //TODO : MODIFICAR DE TAL FORMA QUE SOLO GUARDE NOMINALES. AQUI SE ENCUENTRA EL CONTEO DE NUMERICOS TAMBIEN
    private void initMatrizAV() {
        IntStream ins = Arrays.stream(cabecera);
        OptionalInt op = ins.max();
        int maximo = op.getAsInt();
        String[] split;
        int indiceRelativo = 0;
        
        matrizAV = new int[maximo][Constantes.TOTAL_ATRIBUTOS]; //es totalNumericos en el segundo indice
        
        for(int[] dato : datos) {
            for(int indexAtributo = 0; indexAtributo < Constantes.TOTAL_ATRIBUTOS; indexAtributo++) {
                matrizAV[dato[indexAtributo]][indexAtributo - indiceRelativo]++;
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
    
    private void initDesviaciones() {
        for(int i = 0; i < Constantes.TOTAL_ATRIBUTOS; i++) {
            
        }
    }
    
    private void test() {
        System.out.println("TOTAL ATRIBUTOS = " + cabecera.length);
        System.out.println("TOTAL INSTANCIAS = " + TOTAL_INSTANCIAS);
        System.out.println("TOTAL NUMERICOS = " + totalNumericos);
        System.out.println("TOTAL NOMINALES = " + totalNominales);

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
}
