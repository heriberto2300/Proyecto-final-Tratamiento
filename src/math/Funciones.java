package math;

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
}
