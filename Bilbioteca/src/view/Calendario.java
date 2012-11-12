package view;

import com.toedter.calendar.JCalendar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Trabalho de ..
 * Professor ..
 * @author Alexandre
 * @version 1.0
 */
public class Calendario extends Padrao {

    public Calendario() {
        super();
        init();
        frame.setVisible(true);
    }
    
    public void init(){
        JCalendar calendar = new JCalendar();
        calendar.setPreferredSize(new Dimension(frame.getWidth()-30, frame.getHeight()-30));
        
        JPanel panel = new JPanel();
        panel.add(new JLabel("Calendario de Tarefas"));
        panel.add(calendar);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.add(panel);
    }
   
}
