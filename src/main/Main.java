package main;

import clasificador.Arbol;
import clasificador.KNN;
import datos.Datos;
import datos.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {
        try {
            
            ArrayList<int[]> entrenamiento = Files.leerDatos("datos-Sb/sb1-T.txt");
            ArrayList<int[]> prueba = Files.leerDatos("datos-Sb/sb1-P.txt");
            
            Datos datos = new Datos(entrenamiento);
            
            KNN kVecinos = new KNN(datos, prueba, 5);
            
            Thread threadKNN = new Thread(kVecinos);
            
            /*int[] a = {3, 2, 1, 0, 2};
            int[] b = {2, 1, 2, 1, 2};
            
            System.out.println(kVecinos.hvdm(a, b));*/
            
            /*TODO: VALIDAR SI ES MODA O MEDIA PARA LOS VECINOS MAS CERCANOS*/
            
            threadKNN.start();
            
            threadKNN.join();
            
            String nombreT = "sb1-T.arff";
            String nombreP = "sb1-P.arff";
            /*String cabecera = Files.initCabeceraARFF(datos.getTipoAtributos(), datos.getCabecera(), datos.getTotalClases(), nombreT);
            Files.crearARFF(entrenamiento, cabecera, nombreT);
            Files.crearARFF(prueba, cabecera, nombreP);*/
            
            Arbol arbol = new Arbol(nombreT, nombreP);
            Thread threadArbol = new Thread(arbol);
            
            //threadArbol.start();
            
            threadArbol.join();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
