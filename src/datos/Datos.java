package datos;

import clasificador.Constantes;
import java.util.ArrayList;

public class Datos {
    private final int TOTAL_INSTANCIAS;
    
    private final ArrayList<String> datos;
    
    private boolean[] tipoAtributos;
    private int[] cabecera;
    
    
    
    public Datos(ArrayList<String> datos) {
        init(datos.get(0));
        datos.remove(0);
        this.datos = datos;
        TOTAL_INSTANCIAS = datos.size();
        test();
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
            }else {
                tipoAtributos[i] = Constantes.NOMINAL;
            }
            cabecera[i] = valor;
        }
    }
    
    private void test() {
        System.out.println("TOTAL ATRIBUTOS = " + cabecera.length);
        System.out.println("TOTAL INSTANCIAS = " + TOTAL_INSTANCIAS);
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
