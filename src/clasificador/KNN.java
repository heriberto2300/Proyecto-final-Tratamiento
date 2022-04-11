package clasificador;

import datos.Datos;
import java.util.ArrayList;
import math.Funciones;

public class KNN {
    private final Datos datos;
    private final Datos prueba;
    private final int k;
    
    public KNN(ArrayList<String> entrenamiento, ArrayList<String> prueba, int k) {
        this.datos = new Datos(entrenamiento);
        this.prueba = new Datos(prueba);
        this.k = k;
    }

    //@Override
    public void run() {
        System.out.println("Incia clasificacion");
        int clase;
        int contadorCorrectos = 0;
        int contador = 0;
        for(String instancia : prueba.getInstancias()) {
            System.out.println("Instancia a clasficicar: " + instancia);
            int[] claseCercanos = getClaseCercanos(instancia);
            
            System.out.println("Ya encontre los cercanos y tienen clase: ");
            for(double cercano : claseCercanos) {
                System.out.println(cercano);
            }
            
            clase = (int) Funciones.mediaAritmetica(claseCercanos);
            
            System.out.println("A esta instancia le doy clase: " + clase);

            
            if(clase == prueba.getClase(instancia)) {
                contadorCorrectos++;
            }
            contador++;
            System.out.println("Ya wacho uno, instancias clasificadas = " + contador);
        }
        
        evaluar(contadorCorrectos);
    }
    
    
    public double hvdm(String instanciaA, String instanciaB) {
        double distancia = 0;
        int[] atributosA = datos.getAtributos(instanciaA);
        int[] atributosB = datos.getAtributos(instanciaB);
        
        
        for(int indexAtributo = 0; indexAtributo < Constantes.TOTAL_ATRIBUTOS; indexAtributo++) {
            if(datos.getTipo(indexAtributo) == Constantes.NOMINAL) {
                distancia += normalizedVdm(atributosA[indexAtributo], atributosB[indexAtributo], indexAtributo);
                //System.out.println("Un atributo NOMINAL evaluado");

            }else {
                distancia += normalizedDiff(atributosA[indexAtributo], atributosB[indexAtributo], indexAtributo);
                //System.out.println("Un atributo NUMERICO evaluado");

            }
        }
        
        return Math.sqrt(distancia);
    }
    
    
    public double normalizedVdm(int valorAtributoA, int valorAtributoB, int indexAtributo) {
        double resultado = 0;
        for(int clase = 0; clase < datos.getTotalClases(); clase++) {
            resultado += Math.abs((datos.Naxc(valorAtributoA, indexAtributo, clase) / datos.Nax(valorAtributoA, indexAtributo)) - 
                    (datos.Naxc(valorAtributoB, indexAtributo, clase) / datos.Nax(valorAtributoB, indexAtributo)));
        }
        
        return resultado;
    }
    
    public double normalizedDiff(int valorAtributoA, int valorAtributoB, int index) {
        int[] datosAtributo = datos.getDatosAtributo(index);
        return ((double)Math.abs(valorAtributoA - valorAtributoB)) / (4 * Funciones.desvEstandar(datosAtributo));
    }
    
    public int[] getClaseCercanos(String instancia) { //Retorna la clase de los k vecinos mas cercanos
        double[][] distancias = new double[Constantes.TOTAL_INSTANCIAS][2];
        for(int i = 0; i < Constantes.TOTAL_INSTANCIAS; i++) {
            
            distancias[i][0] = hvdm(instancia, datos.getInstancia(i));
            distancias[i][1] = datos.getClase(datos.getInstancia(i));
            
            System.out.println("Instancia de T evaluada : " + datos.getInstancia(i) + " DISTANCIA = " + distancias[i][0]);

        }
        
        int[] kVecinos = new int[k];
        
        double temporalDistancia;
        double temporalClase;
        for(int i = 0; i < distancias.length - 1; i++) {
            for(int j = i + 1; j < distancias.length; j++) {
                if(distancias[i][0] > distancias[j][0]) {
                    temporalDistancia = distancias[i][0];
                    temporalClase = distancias[i][1];
                    
                    distancias[i][0] = distancias[j][0];
                    distancias[i][1] = distancias[j][1];
                    
                    distancias[j][0] = temporalDistancia;
                    distancias[j][1] = temporalClase;
                }
            }
        }
        
        for(int i = 0; i < k; i++) {
            kVecinos[i] = (int)distancias[i][1];
        }
        
        return kVecinos;
    }
    
    public void evaluar(int contadorCorrectos) {
        System.out.println("------------MODELO KNN CON " + k + " VECIONS CERCANOS------------\n");
        System.out.println("INSTANCIAS CORRECTAMENTE CLASIFICADAS: " + contadorCorrectos);
        System.out.println("INSTANCIAS INCORRECTAMENTE CLASIFICADAS: " + contadorCorrectos);
        
        double porcentajeAcierto = (contadorCorrectos * 100) / Constantes.TOTAL_INSTANCIAS_PRUEBA;
        double porcentajeError = 100 - porcentajeAcierto;
        
        System.out.println();
        System.out.println("PORECENTAJE DE EXACTITUD: " + porcentajeAcierto);
        System.out.println("PORECENTAJE DE ERROR: " + porcentajeError);
        
    }
    
}
