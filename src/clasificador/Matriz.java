package clasificador;

import java.util.Map;

public class Matriz {
    private int dimension;
    private int[][] matriz;
    
    public Matriz(int dimension, Map<Integer, Integer> datos) {
        this.dimension = dimension;
        this.matriz = new int[dimension][dimension];
        initMatriz(datos);
    }
    
    private void initMatriz(Map<Integer, Integer> datos) {
        for(Map.Entry<Integer, Integer> dato : datos.entrySet()) {
            matriz[dato.getValue()][dato.getKey()]++;
        }
    }
    
    public void imprimir() {
        for(int asignada = 0; asignada < dimension; asignada++) {
            for(int real = 0; real < dimension; real++) {
                System.out.print(matriz[asignada][real]);
            }
            System.out.println();
        }
    }
}
