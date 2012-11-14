package view;

import com.toedter.calendar.JCalendar;
import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * Trabalho de ..
 * Professor ..
 * @author Alexandre
 * @version 1.0
 */
public class Calendar {
    public JDialog frameCalendar;
    
    public void init(){
        frameCalendar = new JDialog();
        frameCalendar.setTitle("Cadastrar Aluno");
        frameCalendar.setModal(true);
        frameCalendar.setSize(800, 600);

        Template.lookAndFeel();
        initComponents();

        frameCalendar.setFocusable(true);
        frameCalendar.setLocationRelativeTo(null); //centraliza a tela 
        frameCalendar.setVisible(true);
    }
    
    public void initComponents(){
        final JCalendar calendar = new JCalendar();
                
        JPanel panel = new JPanel();
        panel.add(calendar);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        frameCalendar.add(panel);
    }

}
