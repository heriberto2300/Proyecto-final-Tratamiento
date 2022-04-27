package main;

import clasificador.Arbol;
import clasificador.KNN;
import datos.Datos;
import datos.Files;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {
        try {
            Scanner x = new Scanner(System.in);
            /*Recuperacion de datos y construccion del conjunto de entrenamiento*/
            
            ArrayList<int[]> entrenamiento = Files.leerDatos("datos-Sb/sb1-T.txt");
            ArrayList<int[]> prueba = Files.leerDatos("datos-Sb/sb1-P.txt");
            
            Datos datos = new Datos(entrenamiento, true);
            
            /*Clasficador KNN*/
            KNN kVecinos = new KNN(datos, prueba, 5, true);
            
            Thread threadKNN = new Thread(kVecinos);
            
            /*int[] a = {3, 2, 1, 0, 2};
            int[] b = {2, 1, 2, 1, 2};
            
            System.out.println(kVecinos.hvdm(a, b));*/
            
            /*TODO: VALIDAR SI ES MODA O MEDIA PARA LOS VECINOS MAS CERCANOS*/
            
            threadKNN.start();
            
            threadKNN.join();
            
            System.out.println("\nPresionar cualquier tecla para continuar...");
            x.nextLine();
            /*Conversion de datos a formato arff*/
            
            System.out.println("\n----------CONSTRUCCION DE DATOS EN FORMATO ARFF PARA WEKA---------\n");
            
            String nombreT = "sb1-T.arff";
            String nombreP = "sb1-P.arff";
            String cabecera = Files.initCabeceraARFF(datos.getTipoAtributos(), datos.getCabecera(), datos.getTotalClases(), nombreT);
            Files.crearARFF(entrenamiento, cabecera, nombreT);
            Files.crearARFF(prueba, cabecera, nombreP);
            
            System.out.println("\nFinalizado. Presionar cualquier tecla para continuar...");
            x.nextLine();
            
            /*Clasificador C4.5*/

            Arbol arbol = new Arbol(nombreT, nombreP);
            Thread threadArbol = new Thread(arbol);
            
            threadArbol.start();
            
            threadArbol.join();
            
            System.out.println("\nPresionar cualquier tecla para continuar...");
            x.nextLine();
            
            x.close();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
