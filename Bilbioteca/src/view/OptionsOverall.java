package view;

import com.toedter.calendar.JDateChooser;
import componentes.UJComboBox;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
public class OptionsOverall extends Template {

    private Student student;
    private Book book;
    private Lending lending;
    private Publisher publisher;
    private JTabbedPane tabbedPane;
    private UJComboBox jtCodeStudent;
    private UJComboBox jtBook;
    private Calendar dateCurrent = Calendar.getInstance();
    private JDateChooser dateDeparture;
    private JDateChooser dateReturn;
    private JButton btnBack;
    private JTextField jtSearch;
    private JButton btnSearch;
    private JPanel panelSearch;
    private JTable table;
    private JLabel jtNameStudent;
    private JLabel jtNameSearch;
    private JButton btnDelete;
    private JScrollPane scrollPane;
    private DefaultTableModel model = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
        }
    };
    

    public OptionsOverall(Student student, Book book, Publisher publisher, Lending lending) {
        super();
        this.student = student;
        this.book = book;
        this.publisher = publisher;
        this.lending = lending;
        init();
    }

    public void init() {
        frame.setTitle("Opções");
        tabbedPane = new JTabbedPane();
        //ImageIcon icon = createImageIcon("images/middle.gif");

        JComponent panel1 = new JPanel();
        lending(panel1);
        tabbedPane.addTab("Empréstimo", panel1);
        
        JComponent panel2 = new JPanel();
        renovation(panel2);
        tabbedPane.addTab("Renovação", null, panel2);

        JComponent panel3 = new JPanel();
        devolution(panel3);
        tabbedPane.addTab("Devolução", null, panel3);

        JComponent panel4 = new JPanel();
        history(panel4);
        tabbedPane.addTab("Histórico Aluno", null, panel4);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public JComponent lending(JComponent panel) {
        JLabel lbCodeStudent = new JLabel(" CGM:");
        jtCodeStudent = new UJComboBox(fillComboBoxStudent());
        jtCodeStudent.setAutocompletar(true);
        
        JLabel lbBook = new JLabel(" Livro:");
        jtBook = new UJComboBox(fillComboBoxBook());
        jtBook.setAutocompletar(true);

        JLabel lbDateDeparture = new JLabel(" Data Saída:");
        dateDeparture = new JDateChooser(dateCurrent.getTime());
        dateDeparture.setPreferredSize(new Dimension(130, 30));

        JLabel lbDateReturn = new JLabel(" Data Devolução: ");
        dateCurrent.add(Calendar.DAY_OF_MONTH, 10);
        dateReturn = new JDateChooser(dateCurrent.getTime());
        dateReturn.setPreferredSize(new Dimension(130, 30));

        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(new OptionsOverall.SaveActionListener());
        btnBack = new JButton("Voltar");
        btnBack.addActionListener(new OptionsOverall.BackActionListener());

        JPanel panel0 = new JPanel();
        panel0.add(lbCodeStudent);
        panel0.add(jtCodeStudent);
        panel0.setLayout(new GridLayout(2, 1));

        JPanel panel1 = new JPanel();
        panel1.add(lbBook);
        panel1.add(jtBook);
        panel1.setLayout(new GridLayout(2, 1));

        JPanel panel2 = new JPanel();
        panel2.add(lbDateDeparture);
        panel2.add(dateDeparture);
        panel2.setLayout(new GridLayout(2, 1));

        JPanel panel3 = new JPanel();
        panel3.add(lbDateReturn);
        panel3.add(dateReturn);
        panel3.setLayout(new GridLayout(2, 1));

        JPanel panel4 = new JPanel();
        panel4.add(btnSave);
        panel4.add(btnBack);
        panel4.setLayout(new GridLayout(1, 2));

        panel.add(panel0);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 40));
        return panel;
    }

    public JComponent history(JComponent panel) {
        jtSearch = new JTextField(35);

        btnSearch = new JButton("OK");
        btnSearch.addActionListener(new OptionsOverall.SearchActionListener());

        panelSearch = new JPanel();
        panelSearch.add(new JLabel("Busca: "));
        panelSearch.add(jtSearch);
        panelSearch.add(btnSearch);

        jtNameStudent = new JLabel("Nome do Aluno: ");
        jtNameSearch = new JLabel("");
        JPanel panelName = new JPanel();
        panelName.add(jtNameStudent);
        panelName.add(jtNameSearch);
        panelName.setLayout(new GridLayout(1, 1));

        btnDelete = new JButton("Remover");
        btnDelete.addActionListener(new OptionsOverall.DeleteActionListener());

        btnBack = new JButton("Voltar");
        btnBack.addActionListener(new OptionsOverall.BackActionListener());

        table = new JTable(model);
        //tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        model.addColumn("CGM");
        //modelo.addColumn("Nome");
        model.addColumn("Cod Livro");
        model.addColumn("Data Saída");
        model.addColumn("Data Devolução");
        model.addColumn("Status");
        scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                scrollPane.setPreferredSize(new Dimension(frame.getWidth() - 100, frame.getHeight() - 150));
            }

            public void componentMoved(java.awt.event.ComponentEvent e) {
                scrollPane.setPreferredSize(new Dimension(frame.getWidth() - 100, frame.getHeight() - 150));
            }
        });

        panel.add(panelSearch);
        panel.add(panelName);
        panel.add(scrollPane);
        panel.add(btnDelete);
        panel.add(btnBack);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

        return panel;
    }

    private Boolean fillTable(String cgm) {
        boolean fill = false;
        try {
            ResultSet rs = lending.selectViewLending(cgm);
            while (rs.next()) {
                fill = true;
                jtNameSearch.setText(rs.getString("nome"));
                model.addRow(new String[]{
                            rs.getString("Alunos_CGM"),
                            rs.getString("livro_idlivro"),
                            rs.getString("dataSaida"),
                            rs.getString("dataDevolucao"),
                            rs.getString("status")});
            }
            if (fill) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(OptionsStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void devolution(JComponent panel) {
        
    }

    private void renovation(JComponent panel) {
        
    }

    private class SearchActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cgm = jtSearch.getText();
            if (!fillTable(cgm)) {
                JOptionPane.showMessageDialog(frame, "Aluno não encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int answer = JOptionPane.showConfirmDialog(frame, "Deseja remover esse registro?", "Remoção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.OK_OPTION) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    try {
                        String pattern = "dd/MM/yyyy";
                        DateFormat df = new SimpleDateFormat(pattern);
                        
                        String codeStudent = table.getValueAt(row, 0).toString();
                        
                        String dtDeparture = df.format(new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.UK).parse(table.getValueAt(row, 2).toString()));
                        Date dateDeparture = df.parse(dtDeparture);

                        String dtReturn = df.format(new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.UK).parse(table.getValueAt(row, 3).toString()));
                        Date dateReturn = df.parse(dtReturn);
                        lending.deleteLending(codeStudent, dateDeparture, dateReturn);
                        model.removeRow(row); //remove a linha

                        JOptionPane.showMessageDialog(frame,
                                "Registro excluído com sucesso!!!",
                                "Remover", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ParseException ex) {
                        Logger.getLogger(OptionsOverall.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public Vector fillComboBoxStudent() {
        try {
            ResultSet rs = student.selectAll();
            Vector vector = new Vector();
            vector.add(("--Escolha um Aluno--"));
            while (rs.next()) {
                vector.add(rs.getString("CGM"));
            }
            return vector;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Vector fillComboBoxBook() {
        try {
            ResultSet rs = book.selectAllBooks();
            Vector vector = new Vector();
            vector.add(("--Escolha um Livro--"));

            while (rs.next()) {
                vector.add(rs.getString("idlivro"));
            }
            return vector;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private class BackActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            new MainGUI(student, publisher, book, lending);
        }
    }

    private class SaveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String codeStudent = jtCodeStudent.getSelectedItem().toString();
            String codeBook = jtBook.getSelectedItem().toString();
            String dtDeparture = new SimpleDateFormat("dd/MM/yyyy").format(dateDeparture.getDate());
            String dtReturn = new SimpleDateFormat("dd/MM/yyyy").format(dateReturn.getDate());

            if (!"--Escolha um Aluno--".equals(codeStudent) && !"--Escolha um Livro--".equals(codeBook) && !dtDeparture.isEmpty() && !dtReturn.isEmpty()) {
                if (dateReturn.getDate().after(dateDeparture.getDate())) {
                    lending.insertLending(codeStudent, codeBook, dateDeparture.getDate(), dateReturn.getDate(), "Emprestimo");
                    JOptionPane.showMessageDialog(frame, "Emprestimo Salvo com Sucesso!!!", "Emprestimo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Data de Devolução deve ser DEPOIS da Data de Saída!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Não Pode ter campos em Branco ou Não Selecionados!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}