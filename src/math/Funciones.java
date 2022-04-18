package math;

import java.util.HashMap;
import java.util.Map;

public class Funciones {
    public static double desvEstandar(int[] muestra) {
        double numerador = 0;
        double media = mediaAritmetica(muestra);
        
        for(double dato : muestra) {
            numerador += Math.pow(dato - media, 2);
        }
        
        return Math.sqrt(numerador / (muestra.length - 1));
    }
    
    public static double mediaAritmetica(int[] muestra) {
        double suma = 0;
        for(double dato : muestra) {
            suma += dato;
        }
        return suma /= (double)muestra.length;
    }
    
    public static int moda(int[] muestra) {
        Map<Integer, Integer> moda = new HashMap<>();
        for(int i = 0; i < muestra.length; i++) {
            if(moda.containsKey(muestra[i])) {
                moda.put(muestra[i], moda.get(muestra[i]) + 1);
            }else {
                moda.put(muestra[i], 1);
            }
        }
        int total = 0;
        int m = 0;
        for(HashMap.Entry<Integer, Integer> dato : moda.entrySet()) {
            if(dato.getValue() > total) {
                total = dato.getValue();
                m = dato.getKey();
            }
        }
        return m;
    }
}
