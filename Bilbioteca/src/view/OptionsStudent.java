package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import model.Student;
import model.Publisher;
import model.Lending;
import model.Book;

/**
 * Trabalho de .. Professor ..
 *
 * @author Alexandre
 * @version 1.0
 */
public class OptionsStudent extends Template {
    private Student student;
    private Book book;
    private Lending lending;
    private Publisher publisher;
    private JFormattedTextField jtPhone;
    private JTextField jtCodeStudent;
    private JTextField jtName;
    private JTextField jtSerie;
    private JTextField jtFiliation;
    private JTextField jtAddress;
    private JButton btnRemove;
    private JButton btnBack;
    private JTable table;
    private JTabbedPane tabbedPane;
    private Boolean changed = false;
    private DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            if (mColIndex == 0) return false;
            return true;
        }
    };

    public OptionsStudent(Student student, Publisher publisher, Book book, Lending lending) {
        super();
        this.student = student;
        this.book = book;
        this.publisher = publisher;
        this.lending = lending;
        init();
    }

    public void init() {
        frame.setTitle("Cadastrar Aluno");
        tabbedPane = new JTabbedPane();
        //ImageIcon icon = createImageIcon("images/middle.gif");

        JComponent panel1 = new JPanel();
        register(panel1);
        tabbedPane.addTab("Cadastrar", panel1);

        JComponent panel2 = new JPanel();
        list(panel2);
        tabbedPane.addTab("Listar", null, panel2);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public JComponent register(JComponent panel) {
        changed = false;
        JLabel lbCodeStudent = new JLabel("CGM: ");
        jtCodeStudent = new JTextField(40);

        JLabel lbName = new JLabel("Nome: ");
        jtName = new JTextField(40);

        JLabel lbSerie = new JLabel("Série: ");
        jtSerie = new JTextField(40);

        JLabel lbFiliation = new JLabel("Filiação: ");
        jtFiliation = new JTextField(40);

        JLabel lbAddress = new JLabel("Endereço: ");
        jtAddress = new JTextField(40);

        JLabel lbPhone = new JLabel("Telefone: ");
        jtPhone = new JFormattedTextField((setMascaraPhone("(##) ####-####             ")));
        jtPhone.setPreferredSize(new Dimension(450,30));

        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(new OptionsStudent.SaveActionListener());
        btnBack = new JButton("Voltar");
        btnBack.addActionListener(new OptionsStudent.BackActionListener());

        JPanel panel0 = new JPanel();
        panel0.add(lbCodeStudent);
        panel0.add(jtCodeStudent);
        panel0.setLayout(new GridLayout(2,1));

        JPanel panel1 = new JPanel();
        panel1.add(lbName);
        panel1.add(jtName);
        panel1.setLayout(new GridLayout(2,1));

        JPanel panel2 = new JPanel();
        panel2.add(lbSerie);
        panel2.add(jtSerie);
        panel2.setLayout(new GridLayout(2,1));

        JPanel panel3 = new JPanel();
        panel3.add(lbFiliation);
        panel3.add(jtFiliation);
        panel3.setLayout(new GridLayout(2,1));

        JPanel panel4 = new JPanel();
        panel4.add(lbAddress);
        panel4.add(jtAddress);
        panel4.setLayout(new GridLayout(2,1));

        JPanel panel5 = new JPanel();
        panel5.add(lbPhone);
        panel5.add(jtPhone);
        panel5.setLayout(new GridLayout(2,1));

        JPanel panel6 = new JPanel();
        panel6.add(btnSave);
        panel6.add(btnBack);
        panel6.setLayout(new GridLayout(1,2));

        panel.add(panel0);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel5);
        panel.add(panel4);
        panel.add(panel6);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 23));
        
        return panel;
    }

    public JComponent list(JComponent panel) {
        changed = true;
        table = new JTable(model);
        
        model.addColumn("CGM");
        model.addColumn("Nome");
        model.addColumn("Série");
        model.addColumn("Filiação");
        model.addColumn("Endereço");
        model.addColumn("Telefone");

        fillTable();
        final JScrollPane scrollPane = new JScrollPane(table);
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

        btnRemove = new JButton("Remover");
        btnRemove.addActionListener(new OptionsStudent.RemoveActionListener());

        btnBack = new JButton("Voltar");
        btnBack.addActionListener(new OptionsStudent.BackActionListener());

        panel.add(scrollPane);
        panel.add(btnRemove);
        panel.add(btnBack);

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (changed) {
                    int answer = JOptionPane.showConfirmDialog(frame, "Deseja editar esse registro?", "Edição", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.OK_OPTION) {
                        int row = table.getSelectedRow();
                        int column = table.getSelectedColumn();
                        String codeStudent = table.getValueAt(row, 0).toString();
                        String value = table.getValueAt(row, column).toString();
                        String field = null;
                        if (column == 1) {
                            field = "nome";
                        }
                        if (column == 2) {
                            field = "serie";
                        }
                        if (column == 3) {
                            field = "filiacao";
                        }
                        if (column == 4) {
                            field = "endereco";
                        }
                        if (column == 5) {
                            field = "telefone";
                        }
                        if (column == 0) {
                            JOptionPane.showMessageDialog(frame,
                                    "CGM não pode ser Modificado!",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        if (row >= 0 && column != 0) {

                            student.updateFieldStudent(codeStudent, value, field);
                            JOptionPane.showMessageDialog(frame,
                                    "Registro editado com sucesso!!!",
                                    "Editar", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });

        return panel;
    }
    
    public void fillTable() {
        try {
            ResultSet rs = student.selectAll();
            while (rs.next()) {
                model.addRow(new String[]{
                            rs.getString("CGM"),
                            rs.getString("nome"),
                            rs.getString("serie"),
                            rs.getString("filiacao"),
                            rs.getString("endereco"),
                            rs.getString("telefone")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(OptionsStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private MaskFormatter setMascaraPhone(String maskEnter) {
        MaskFormatter mask = null;
        try {
            mask = new MaskFormatter(maskEnter);
        } catch (java.text.ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return mask;
    }


    private class SaveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String codeStudent = jtCodeStudent.getText();
            String name = jtName.getText();
            String serie = jtSerie.getText();
            String phone = jtPhone.getText();
            String filiation = jtFiliation.getText();
            String address = jtAddress.getText();


            if (!student.selectCodeStudent(codeStudent)) {
                changed = false;
                student.insertStudent(codeStudent, name, serie, filiation, address, phone);
                model.addRow(new String[]{codeStudent, name, serie, filiation, address, phone});
                JOptionPane.showMessageDialog(frame, "Registro Salvo com sucesso!!!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                tabbedPane.setSelectedIndex(1);
                jtCodeStudent.setText("");jtAddress.setText("");jtFiliation.setText("");
                jtName.setText("");jtSerie.setText("");jtPhone.setText("");
            } else {
                changed = false;
                JOptionPane.showMessageDialog(frame, "Esse CGM já Existe!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class BackActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            changed = false;
            frame.setVisible(false);
            new MainGUI(student, publisher, book, lending);
        }
    }

    private class RemoveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            changed = false;
            int answer = JOptionPane.showConfirmDialog(frame, "Deseja remover registro?", "Remoção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.OK_OPTION) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String codeStudent = table.getValueAt(row, 0).toString();
                    student.deleteStudent(codeStudent);
                    model.removeRow(row); //remove a linha

                    JOptionPane.showMessageDialog(frame,
                            "Registro excluído com sucesso!!!",
                            "Remover", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}
