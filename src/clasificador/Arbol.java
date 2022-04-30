package clasificador;

import graphics.VisualizadorArbol;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;


public class Arbol implements Runnable {
    private Instances entrenamiento;
    private Instances prueba;
    private J48 arbol;
    
    public Arbol(String nombreT, String nombreP) {
        try {
            DataSource source = new DataSource(Constantes.PATH + nombreT);
            this.entrenamiento = source.getDataSet();
            entrenamiento.setClassIndex(entrenamiento.numAttributes() - 1);
            DataSource source2 = new DataSource(Constantes.PATH + nombreP);
            this.prueba = source2.getDataSet();
            prueba.setClassIndex(prueba.numAttributes() - 1);
            this.arbol = new J48();
        } catch (Exception ex) {
            Logger.getLogger(Arbol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        try {
            System.out.println("\n----------INICIANDO CLASIFICADOR C4.5---------\n");
            arbol.setUnpruned(false);
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
            
            new VisualizadorArbol(arbol);
        } catch (Exception ex) {
            Logger.getLogger(Arbol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
