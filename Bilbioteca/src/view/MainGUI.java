package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 * Trabalho de .. Professor ..
 *
 * @author Alexandre
 * @version 1.0
 */
public class MainGUI{
    protected JFrame frameMain;
    private final OptionsStudent os;
    private final OptionsBook ob;
    private final OptionsOverall oo;
    private final Calendar c;

    public MainGUI(OptionsStudent os, OptionsBook ob, OptionsOverall oo, Calendar c) {
        this.os = os;
        this.ob = ob;
        this.oo = oo;
        this.c = c;
        init();
    }
    
    public void init(){
        frameMain = new JFrame("BibliotQAM - Gerenciamento da Biblioteca");
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Template.lookAndFeel();
        initComponents();
        
        frameMain.pack();
        frameMain.setSize(800, 600);
        frameMain.setLocationRelativeTo(null); //centraliza a tela 
        frameMain.setVisible(true);
    }
    
    public void initComponents(){        
        Container container = frameMain.getContentPane();
        container.setLayout(new BorderLayout());

        JLabel labelImage = new JLabel(new ImageIcon("img/biblioteca.png"));
        JButton btnOptionsStudent = new JButton("Cadastro Alunos");
        btnOptionsStudent.addActionListener(new MainGUI.OptionsStudentActionListener());
        
        JButton btnCadastreBook = new JButton("Cadastro Livros");
        btnCadastreBook.addActionListener(new MainGUI.OptionsBookActionListener());
        
        JButton btnOptionsOverall = new JButton("Empréstimo - Renovação - Devolução");
        btnOptionsOverall.addActionListener(new MainGUI.OptionsOverallActionListener());
        
        JButton btnPrint = new JButton("Imprimir");
        btnPrint.addActionListener(new MainGUI.PrintActionListener());
        
        JButton btnCalendar = new JButton("Calendário");
        btnCalendar.addActionListener(new MainGUI.CalendarActionListener());
        
        JPanel panel = new JPanel();
        panel.add(btnOptionsStudent);
        panel.add(btnCadastreBook);
        panel.add(btnOptionsOverall);
        panel.add(btnCalendar);
        panel.add(btnPrint);
        panel.setLayout(new GridLayout(5, 1));

        container.add(panel, BorderLayout.EAST);
        container.add(labelImage, BorderLayout.CENTER);
    }
    
    private class OptionsStudentActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            os.init();
        }
    }
    
    private class OptionsBookActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ob.init();
        }
    }

    private class OptionsOverallActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            oo.init();
        }
    }

    private class PrintActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("imprimir");
        }
    }

    private class CalendarActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            c.init();
        }
    }
}