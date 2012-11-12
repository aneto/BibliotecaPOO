package view;

import com.toedter.calendar.JCalendar;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Trabalho de ..
 * Professor ..
 * @author Alexandre
 * @version 1.0
 */
public class Calendar extends Template {

    public Calendar() {
        super();
        init();
        frame.setVisible(true);
    }
    
    public void init(){
        JCalendar calendar = new JCalendar();
        calendar.setPreferredSize(new Dimension(frame.getWidth()-20, frame.getHeight()));
        
        JPanel panel = new JPanel();
        panel.add(calendar);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.add(panel);
    }
   
}
