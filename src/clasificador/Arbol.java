package clasificador;

import graphics.VisualizadorArbol;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


public class Arbol implements Runnable {
    private Instances entrenamiento;
    private Instances prueba;
    private J48 arbol;
    private VisualizadorArbol vA;
    
    private int[] mejoresAtributos;
    
    private boolean inputAtributos;
    
    public Arbol(String nombreT, String nombreP) {
        try {
            DataSource source = new DataSource(nombreT);
            this.entrenamiento = source.getDataSet();
            entrenamiento.setClassIndex(entrenamiento.numAttributes() - 1);
            DataSource source2 = new DataSource(nombreP);
            this.prueba = source2.getDataSet();
            prueba.setClassIndex(prueba.numAttributes() - 1);
            this.arbol = new J48();
            this.inputAtributos = true;
        } catch (Exception ex) {}
    }
    
    @Override
    public void run() {
        try {
            arbol.setUnpruned(false);
            arbol.setReducedErrorPruning(true);
            arbol.buildClassifier(entrenamiento);
            
            Evaluation evaluacion = new Evaluation(entrenamiento);
            evaluacion.evaluateModel(arbol, prueba);
            
            System.out.println(arbol.toString());
            System.out.println(evaluacion.toSummaryString());
            
            double[][] matrizConfusion = evaluacion.confusionMatrix();
            
            System.out.println("\n------MATRIZ DE CONFUSION:------\n");
            for(int i = 0; i < matrizConfusion.length; i++) {
                for(int j = 0; j < matrizConfusion.length; j++) {
                    System.out.print((int)matrizConfusion[i][j] + " ");
                }
                System.out.println();
            }
            
            vA = new VisualizadorArbol(arbol, inputAtributos);
            mejoresAtributos = vA.getMejoresAtributos();
        } catch (Exception ex) {}
    }
    
    public int[] getMejoresAtributos() {
        return mejoresAtributos;
    }
    
    public void showInput(boolean inputAtributos) {
        this.inputAtributos = inputAtributos;
    }
}
