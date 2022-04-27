package graphics;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import weka.classifiers.trees.J48;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class VisualizadorArbol extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private TreeVisualizer tv;
    
    public VisualizadorArbol(J48 arbol) {
        super("Arbol generado");
        try {
            init(arbol);
            setLocationRelativeTo(null);
            setVisible(true);
            tv.fitToScreen();
        } catch (Exception ex) {
            Logger.getLogger(VisualizadorArbol.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
