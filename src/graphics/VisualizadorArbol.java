package graphics;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import weka.classifiers.trees.J48;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class VisualizadorArbol extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private TreeVisualizer tv;
    
    private int[] mejoresAtributos;
    
    public VisualizadorArbol(J48 arbol, boolean inputAtributos) {
        super("Arbol generado");
        try {
            init(arbol);
            setLocationRelativeTo(null);
            setVisible(true);
            tv.fitToScreen();
            if(inputAtributos) {
                setMejoresAtributos();
            }
        } catch (Exception ex) {}
    }
    
    private void init(J48 arbol) throws Exception {
        this.setSize(1000, 600);
        this.getContentPane().setLayout(new BorderLayout());
        this.tv = new TreeVisualizer(null, arbol.graph(), new PlaceNode2());
        this.getContentPane().add(tv, BorderLayout.CENTER);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
    
    private void setMejoresAtributos() {
        String datos = "";
        
        while(datos.equals("")) {
            datos = JOptionPane.showInputDialog("Escriba los mejores atributos");
        }
        
        mejoresAtributos = Stream.of(datos.split(",")).mapToInt(Integer::parseInt).toArray();
        dispose();
    }
    
    public int[] getMejoresAtributos() {
        return mejoresAtributos;  
    }
}
