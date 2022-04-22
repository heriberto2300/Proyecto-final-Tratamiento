package clasificador;


public class Matriz {
    private final int dimension;
    private final int[][] matriz;
    
    public Matriz(int dimension, int[][] datosMatriz) {
        this.dimension = dimension;
        this.matriz = new int[dimension][dimension];
        initMatriz(datosMatriz);
    }
    
    private void initMatriz(int[][] datosMatriz) {
        int real, asignada;
        for(int[] datoMatriz : datosMatriz) {
            real = datoMatriz[0];
            asignada = datoMatriz[1];
            matriz[asignada][real]++;
        }
    }
    
    public void imprimir() {
        for(int asignada = 0; asignada < dimension; asignada++) {
            for(int real = 0; real < dimension; real++) {
                System.out.print(matriz[asignada][real] + " ");
            }
            System.out.println();
        }
    }
}
