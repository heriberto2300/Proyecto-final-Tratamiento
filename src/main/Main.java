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
            
            String t1 = "sb1-T.txt";
            String p1 = "sb1-P.txt";
            
            String t2 = "aust1-T.txt";
            String p2 = "aust1-P.txt";
            
            ArrayList<String> entrenamiento = Files.leerDatos("datos-Sb/sb1-T.txt");
            ArrayList<String> prueba = Files.leerDatos("datos-Sb/sb1-P.txt");
            
            Datos datos = new Datos(entrenamiento, false);
            
            /*Clasficador KNN*/
            KNN kVecinos = new KNN(datos, prueba, 9, true);
            
            Thread threadKNN = new Thread(kVecinos);
            
            /*int[] a = {3, 2, 1, 0, 2};
            int[] b = {2, 1, 2, 1, 2};
            
            System.out.println(kVecinos.hvdm(a, b));*/
            
           // threadKNN.start();
            
            threadKNN.join();
            
            System.out.println("\nPresionar cualquier tecla para continuar...");
            x.nextLine();
            /*Conversion de datos a formato arff*/
            
            System.out.println("\n----------CONSTRUCCION DE DATOS EN FORMATO ARFF PARA WEKA---------\n");
            
            String nombreT = "sb1-T.arff";
            String nombreP = "sb1-P.arff";
            
            String nombreT2 = "aust1-T.arff";
            String nombreP2 = "aust1-P.arff";
            
            
            String cabecera = Files.initCabeceraARFF(datos.getTipoAtributos(), datos.getCabecera(), datos.getTotalClases(), nombreT2);
            Files.crearARFF(entrenamiento, cabecera, nombreT2);
            Files.crearARFF(prueba, cabecera, nombreP2);
            
            System.out.println("\nFinalizado. Presionar cualquier tecla para continuar...");
            x.nextLine();
            
            /*Clasificador C4.5*/

            Arbol arbol = new Arbol(nombreT2, nombreP2);
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
