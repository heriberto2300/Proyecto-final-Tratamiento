package main;

import clasificador.KNN;
import datos.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {
        try {
            
            ArrayList<int[]> entrenamiento = Files.leerDatos("datos-Sb/sb1-T.txt");
            ArrayList<int[]> prueba = Files.leerDatos("datos-Sb/sb1-P.txt");
            
            KNN kVecinos = new KNN(entrenamiento, prueba, 3);
            
            Thread thread = new Thread(kVecinos);
            
            thread.start();
            
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
