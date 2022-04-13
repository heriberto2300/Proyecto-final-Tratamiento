package datos;

import clasificador.Constantes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Datos {
    private final int TOTAL_INSTANCIAS;
    
    private int totalNumericos;
    private int totalNominales;
    
    private final ArrayList<String> datos;
    
    private boolean[] tipoAtributos;
    private int[] cabecera;
    private int[][] matrizAV;
    
    
    public Datos(ArrayList<String> datos) {
        init(datos.get(0));
        datos.remove(0);
        this.datos = datos;
        TOTAL_INSTANCIAS = datos.size();
        initMatriz();
        //test();
    }
    
    private void init(String cabeza) {
        String split[] = cabeza.split(",");
        tipoAtributos = new boolean[Constantes.TOTAL_ATRIBUTOS];
        cabecera = new int[Constantes.TOTAL_ATRIBUTOS];
        int valor;
        for(int i = 0; i < Constantes.TOTAL_ATRIBUTOS; i++) {
            valor = Integer.parseInt(split[i]);
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
    private void initMatriz() {
        IntStream ins = Arrays.stream(cabecera);
        OptionalInt op = ins.max();
        int maximo = op.getAsInt();
        String[] split;
        int indiceRelativo = 0;
        
        matrizAV = new int[maximo][Constantes.TOTAL_ATRIBUTOS]; //es totalNumericos en el segundo indice
        
        for(String dato : datos) {
            split = dato.split(",");
            for(int indexAtributo = 0; indexAtributo < Constantes.TOTAL_ATRIBUTOS; indexAtributo++) {
                matrizAV[Integer.parseInt(split[indexAtributo])][indexAtributo - indiceRelativo]++;
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
        for(String dato : datos) {
            System.out.println(dato);
        }
    }
}
