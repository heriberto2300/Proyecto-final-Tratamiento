package datos;

import clasificador.Constantes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import math.Funciones;

public class Datos {
    private final ArrayList<String> datos;
    private boolean[] tipoAtributo;
    private int[] cabecera;
    
    //private Map<Integer, Double> desviacionesNumericos;
    
    public Datos(ArrayList<String> datos) {
        initCabecera(datos.get(0));
        initTipos();
        datos.remove(0);
        this.datos = datos;
        
        initDesviaciones();
        //test();
    }
    
    private void initCabecera(String c) {
        String[] cabeza = c.split(",");
        cabecera = new int[cabeza.length];
        for(int i = 0; i < cabeza.length; i++) {
            cabecera[i] = Integer.parseInt(cabeza[i]);
        }
    }
    
    private void initTipos() {
        tipoAtributo = new boolean[Constantes.TOTAL_ATRIBUTOS];
        for(int i = 0; i < Constantes.TOTAL_ATRIBUTOS; i++) {
            if(this.cabecera[i] == 0) {
                tipoAtributo[i] = Constantes.NUMERICO;
            }else {
                tipoAtributo[i] = Constantes.NOMINAL;
            }
        }
    }
    
    private void initDesviaciones() {
        //desviacionesNumericos = new HashMap<>();
        
        for(int i = 0; i < tipoAtributo.length; i++) {
            if(tipoAtributo[i] == Constantes.NUMERICO) {
                System.out.println("Atributo " + (i) + " ES numerico");
                int[] datos = getDatosAtributo(i);
                double desv = Funciones.desvEstandar(datos);
                //desviacionesNumericos.put(i, desv);
            }
        }
                System.out.println("Acabo");

    }
    
    public int[] getAtributos(String instancia) {
        int[] atributos = new int[Constantes.TOTAL_ATRIBUTOS];
        String[] at = instancia.split(",");
        for(int i = 0; i < Constantes.TOTAL_ATRIBUTOS; i++) {
            atributos[i] = Integer.parseInt(at[i]);
        }
        return atributos;
    }
    
    public int getClase(String instancia) {
        return Integer.parseInt(instancia.split(",")[35]);
    }
    
    private void test() {
        System.out.println("INSTANCIAS: ");
        
        for(String dato : datos) {
            System.out.println(dato);
        }
        System.out.println(datos.size());
        
        System.out.println("TIPO DE DATO: ");
        
        for(boolean tipo: tipoAtributo) {
            if(tipo == Constantes.NOMINAL) {
                System.out.println("NOMINAL");
            }else {
                System.out.println("NUMERICO");
            }
        }
        
        System.out.println("CLASES: ");
        for(String dato : datos) {
            System.out.println(getClase(dato));
        }
    }
    
    public boolean getTipo(int index) {
        return tipoAtributo[index] == Constantes.NOMINAL ? Constantes.NOMINAL : Constantes.NUMERICO;
    }
    
    public int[] getDatosAtributo(int index) { //Retorna todos los valores del atributo en el indice index de todas las instancias
        int[] datosAtributo = new int[Constantes.TOTAL_INSTANCIAS];
        String[] at;
        for(int i = 0; i < Constantes.TOTAL_INSTANCIAS; i++) {
            at = datos.get(i).split(",");
            datosAtributo[i] = Integer.parseInt(at[index]);
        }
        
        return datosAtributo;
    }
    
    public int getDominioAtributo(int index) {
        return tipoAtributo[index] == Constantes.NOMINAL ? cabecera[index] : -1;
    }
    
    public int getTotalClases() {
        return cabecera[35];
    }
    
    public ArrayList<String> getInstancias() {
        return datos;
    }
    
    public String getInstancia(int index) {
        return datos.get(index);
    }
    
    public int Naxc(int valorAtributo, int indexAtributo, int clase) { //Retorna el numero de veces que el atributo a tiene valor x en la clase c
        int contador = 0;
        int[] datosAtributo = getDatosAtributo(indexAtributo);
        int[] clases = getDatosAtributo(35);
        for(int i = 0; i < Constantes.TOTAL_INSTANCIAS; i++) {
            if(datosAtributo[i] == valorAtributo && clases[i] == clase) {
                contador ++;
            }
        }
        return contador;
    }
    
    public int Nax(int valorAtributo, int indexAtributo) { //Retornar el numero de veces que el atributo a tiene valor x
        int contador = 0;
        int[] datosAtributo = getDatosAtributo(indexAtributo);
        for(int i = 0; i < Constantes.TOTAL_INSTANCIAS; i++) {
            if(datosAtributo[i] == valorAtributo) {
                contador ++;
            }
        }
        return contador;
    } 
    
}
