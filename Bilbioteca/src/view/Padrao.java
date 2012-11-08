package view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * Trabalho de ..
 * Professor ..
 * @author Alexandre
 * @version 1.0
 */
public class Padrao {
    
    protected JFrame frame;
    
    public Padrao() {
        frame = new JFrame("BibliotQAM - Gerenciamento da Biblioteca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lookAndFeel();
        
        frame.pack();
        frame.setSize(800, 600);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null); //centraliza a tela 
    }

    private void lookAndFeel() {
        int i = 1;
        try {
            UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
            UIManager.setLookAndFeel(looks[i].getClassName());
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
