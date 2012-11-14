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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Book;
import model.Lending;
import model.Student;

/**
 * Trabalho de .. Professor ..
 *
 * @author Alexandre
 * @version 1.0
 */
public class OptionsOverall {

    public JDialog frameOverall;
    private JTabbedPane tabbedPane;
    private Book book;
    private Student student;
    private Lending lending;
    private UJComboBox jtCodeStudent;
    private UJComboBox jtBook;
    private JDateChooser dateDeparture;
    private JDateChooser dateReturn;
    private java.util.Calendar dateCurrent = java.util.Calendar.getInstance();
    private JLabel jtNameSearch;
    private JScrollPane scrollPane;
    private DefaultTableModel model = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
        }
    };
    private JTable table;
    private JTextField jtSearch;
    private UJComboBox jtCodeStudentLending;

    public OptionsOverall(Student student, Book book, Lending lending) {
        this.student = student;
        this.book = book;
        this.lending = lending;
    }

    public void init() {
        frameOverall = new JDialog();
        frameOverall.setTitle("Empréstimo/Renovação/Devolução");
        frameOverall.setModal(true);

        Template.lookAndFeel();
        initComponents();

        frameOverall.setSize(800, 600);
        frameOverall.setFocusable(true);
        frameOverall.setLocationRelativeTo(null); //centraliza a tela 
        frameOverall.setVisible(true);
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        //ImageIcon icon = createImageIcon("images/middle.gif");

        JComponent panel1 = new JPanel();
        lending(panel1);
        tabbedPane.addTab("Empréstimo", panel1);

        JComponent panel2 = new JPanel();
        renovation(panel2);
        tabbedPane.addTab("Renovação", panel2);

        JComponent panel3 = new JPanel();
        devolution(panel3);
        tabbedPane.addTab("Devolução", panel3);

        JComponent panel4 = new JPanel();
        history(panel4);
        tabbedPane.addTab("Histórico Aluno", panel4);

        frameOverall.add(tabbedPane);
    }

    private JComponent lending(JComponent panel) {
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
        dateCurrent.add(java.util.Calendar.DAY_OF_MONTH, 10);
        dateReturn = new JDateChooser(dateCurrent.getTime());
        dateReturn.setPreferredSize(new Dimension(130, 30));

        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(new OptionsOverall.SaveActionListener());

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
        panel4.setLayout(new GridLayout(1, 2));

        panel.add(panel0);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 40));

        return panel;
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

    public Vector fillComboBoxLending() {
        try {
            ResultSet rs = lending.selectAll();
            Vector vector = new Vector();
            vector.add(("--Escolha um CGM--"));

            while (rs.next()) {
                vector.add(rs.getString("Alunos_CGM"));
            }
            return vector;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
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
                    jtCodeStudentLending.addItem(codeStudent);
                    JOptionPane.showMessageDialog(frameOverall, "Emprestimo Salvo com Sucesso!!!", "Emprestimo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frameOverall, "Data de Devolução deve ser DEPOIS da Data de Saída!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frameOverall, "Não Pode ter campos em Branco ou Não Selecionados!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JComponent renovation(JComponent panel) {
        JLabel lbCodeStudentLending = new JLabel(" CGM:");
        jtCodeStudentLending = new UJComboBox(fillComboBoxLending());
        jtCodeStudentLending.setAutocompletar(true);
        
        

        JPanel panel0 = new JPanel();
        panel0.add(lbCodeStudentLending);
        panel0.add(jtCodeStudentLending);
        panel0.setLayout(new GridLayout(2, 1));

        panel.add(panel0);

        return panel;

    }

    private JComponent devolution(JComponent panel) {
        return panel;

    }

    private JComponent history(JComponent panel) {
        jtSearch = new JTextField(35);

        JButton btnSearch = new JButton("OK");
        btnSearch.addActionListener(new OptionsOverall.SearchActionListener());

        JPanel panelSearch = new JPanel();
        panelSearch.add(new JLabel("Busca: "));
        panelSearch.add(jtSearch);
        panelSearch.add(btnSearch);

        JLabel jtNameStudent = new JLabel("Nome do Aluno: ");
        jtNameSearch = new JLabel("");
        JPanel panelName = new JPanel();
        panelName.add(jtNameStudent);
        panelName.add(jtNameSearch);
        panelName.setLayout(new GridLayout(1, 1));

        JButton btnDelete = new JButton("Remover");
        btnDelete.addActionListener(new OptionsOverall.DeleteActionListener());

        if (table == null) {
            table = new JTable(model);

            model.addColumn("");
            model.addColumn("CGM");
            model.addColumn("Cod Livro");
            model.addColumn("Data Saída");
            model.addColumn("Data Devolução");
            model.addColumn("Status");
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        frameOverall.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                scrollPane.setPreferredSize(new Dimension(frameOverall.getWidth() - 100, frameOverall.getHeight() - 150));
            }

            public void componentMoved(java.awt.event.ComponentEvent e) {
                scrollPane.setPreferredSize(new Dimension(frameOverall.getWidth() - 100, frameOverall.getHeight() - 150));
            }
        });

        panel.add(panelSearch);
        panel.add(panelName);
        panel.add(scrollPane);
        panel.add(btnDelete);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

        return panel;
    }

    private Boolean fillTable(String cgm) {
        boolean fill = false;
        clearTable();
        try {
            ResultSet rs = lending.selectViewLending(cgm);
            while (rs.next()) {
                fill = true;
                jtNameSearch.setText(rs.getString("nome"));
                model.addRow(new String[]{
                            rs.getString("idemprestimo"),
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

    public void clearTable() {
        model.setNumRows(0);
    }

    private class SearchActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cgm = jtSearch.getText();
            if (!fillTable(cgm)) {
                JOptionPane.showMessageDialog(frameOverall, "Aluno não encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int answer = JOptionPane.showConfirmDialog(frameOverall, "Deseja remover esse registro?", "Remoção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.OK_OPTION) {
                int row = table.getSelectedRow();
                if (row >= 0) {

                    String code = table.getValueAt(row, 0).toString();

                    lending.deleteLending2(code);
                    model.removeRow(row); //remove a linha
                    jtCodeStudentLending.removeAllItems();
                    jtCodeStudentLending.addItem(fillComboBoxLending());

                    JOptionPane.showMessageDialog(frameOverall,
                            "Registro excluído com sucesso!!!",
                            "Remover", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        }
    }
}