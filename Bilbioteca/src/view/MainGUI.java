package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Book;
import model.Lending;
import model.Publisher;
import model.Student;

/**
 * Trabalho de .. Professor ..
 *
 * @author Alexandre
 * @version 1.0
 */
public class MainGUI extends Template {

    private Container container;
    private JPanel panel;
    private JButton btnOptionsStudent;
    private JButton btnCadastreBook;
    private JButton btnOptionsOverall;
    private JButton btnPrint;
    private JButton btnCalendar;
    private Student student;
    private Publisher publisher;
    private Book book;
    private Lending lending;

    public MainGUI(Student student, Publisher publisher, Book book, Lending lending) {
        super();
        this.student = student;
        this.book = book;
        this.publisher = publisher;
        this.lending = lending;
        init();
    }

    public void init() {
        container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        JLabel labelImage = new JLabel(new ImageIcon("img/biblioteca.png"));

        btnOptionsStudent = new JButton("Cadastro Alunos");
        btnOptionsStudent.addActionListener(new MainGUI.OptionsStudentActionListener());

        btnCadastreBook = new JButton("Cadastro Livros");
        btnCadastreBook.addActionListener(new MainGUI.OptionsBookActionListener());

        btnOptionsOverall = new JButton("Empréstimo - Renovação - Devolução");
        btnOptionsOverall.addActionListener(new MainGUI.OptionsOverallActionListener());

        btnPrint = new JButton("Imprimir");
        btnPrint.addActionListener(new MainGUI.PrintActionListener());

        btnCalendar = new JButton("Calendário");
        btnCalendar.addActionListener(new MainGUI.CalendarActionListener());

        panel = new JPanel();
        panel.add(btnOptionsStudent);
        panel.add(btnCadastreBook);
        panel.add(btnOptionsOverall);
        panel.add(btnCalendar);
        panel.add(btnPrint);
        panel.setLayout(new GridLayout(5, 1));

        container.add(panel, BorderLayout.EAST);
        container.add(labelImage, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private class OptionsStudentActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            new OptionsStudent(student, publisher, book, lending);
        }
    }

    private class OptionsBookActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            new OptionsBook(student, publisher, book, lending);
        }
    }

    private class OptionsOverallActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            new OptionsOverall(student, book, publisher, lending);
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
            frame.setVisible(false);
            new Calendar();
        }
    }
}
