
package view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 * Trabalho de ..
 * Professor ..
 * @author Alexandre
 * @version 1.0
 */
public class Template {
    
    public static void lookAndFeel() {
        int i = 1;
        try {
            UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
            UIManager.setLookAndFeel(looks[i].getClassName());
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
