package main;

import clasificador.KNN;
import datos.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        try {
            
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0, 25);
            map.put(1, 30);
            
            System.out.println(map);
            
            map.put(1, map.get(1) + 1);
            System.out.println(map);
            
            ArrayList<int[]> entrenamiento = Files.leerDatos("datos-Sb/sb1-T.txt");
            ArrayList<int[]> prueba = Files.leerDatos("datos-Sb/sb1-P.txt");
            
            KNN kVecinos = new KNN(entrenamiento, prueba, 3);
            
            Thread thread = new Thread();
            
            thread.start();
            
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
