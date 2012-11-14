package view;

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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import model.Student;

/**
 * Trabalho de .. Professor ..
 *
 * @author Alexandre
 * @version 1.0
 */
public class OptionsStudent {

    public JDialog frameStudent;
    private JTabbedPane tabbedPane;
    private final Student student;
    
    private JTextField jtCodeStudent;
    private JTextField jtName;
    private JTextField jtSerie;
    private JTextField jtFiliation;
    private JTextField jtAddress;
    private JFormattedTextField jtPhone;
    private JTable table;
    private DefaultTableModel model = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
        }
    };
    private UJComboBox jtSearchEdit;

    public OptionsStudent(Student student) {
        this.student = student;
    }

    public void init() {
        frameStudent = new JDialog();
        frameStudent.setTitle("Cadastrar Aluno");
        frameStudent.setModal(true);

        Template.lookAndFeel();
        initComponents();

        frameStudent.setSize(800, 600);
        frameStudent.setFocusable(true);
        frameStudent.setLocationRelativeTo(null); //centraliza a tela 
        frameStudent.setVisible(true);
    }

    public void initComponents() {
        tabbedPane = new JTabbedPane();
        //ImageIcon icon = createImageIcon("images/middle.gif");

        JComponent panel1 = new JPanel();
        register(panel1);
        tabbedPane.addTab("Cadastrar", panel1);

        JComponent panel2 = new JPanel();
        edit(panel2);
        tabbedPane.addTab("Editar", panel2);

        JComponent panel3 = new JPanel();
        list(panel3);
        tabbedPane.addTab("Listar", panel3);

        frameStudent.add(tabbedPane);
    }

    public JComponent register(JComponent panel) {
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
        jtPhone.setPreferredSize(new Dimension(450, 30));

        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(new OptionsStudent.SaveActionListener());

        JPanel panel0 = new JPanel();
        panel0.add(lbCodeStudent);
        panel0.add(jtCodeStudent);
        panel0.setLayout(new GridLayout(2, 1));

        JPanel panel1 = new JPanel();
        panel1.add(lbName);
        panel1.add(jtName);
        panel1.setLayout(new GridLayout(2, 1));

        JPanel panel2 = new JPanel();
        panel2.add(lbSerie);
        panel2.add(jtSerie);
        panel2.setLayout(new GridLayout(2, 1));

        JPanel panel3 = new JPanel();
        panel3.add(lbFiliation);
        panel3.add(jtFiliation);
        panel3.setLayout(new GridLayout(2, 1));

        JPanel panel4 = new JPanel();
        panel4.add(lbAddress);
        panel4.add(jtAddress);
        panel4.setLayout(new GridLayout(2, 1));

        JPanel panel5 = new JPanel();
        panel5.add(lbPhone);
        panel5.add(jtPhone);
        panel5.setLayout(new GridLayout(2, 1));

        JPanel panel6 = new JPanel();
        panel6.add(btnSave);
        panel6.setLayout(new GridLayout(1, 2));

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

    private MaskFormatter setMascaraPhone(String maskEnter) {
        MaskFormatter mask = null;
        try {
            mask = new MaskFormatter(maskEnter);
        } catch (java.text.ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return mask;
    }

    public JComponent list(JComponent panel) {
        if (table == null) {
            table = new JTable(model);

            model.addColumn("CGM");
            model.addColumn("Nome");
            model.addColumn("Série");
            model.addColumn("Filiação");
            model.addColumn("Endereço");
            model.addColumn("Telefone");
            fillTable();
        }

        final JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        
        frameStudent.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                scrollPane.setPreferredSize(new Dimension(frameStudent.getWidth() - 100, frameStudent.getHeight() - 150));
            
            }
        });

        JButton btnRemove = new JButton("Remover");
        btnRemove.addActionListener(new OptionsStudent.RemoveActionListener());

        panel.add(scrollPane);
        panel.add(btnRemove);

        return panel;
    }

    public JComponent edit(JComponent panel) {

        JLabel lbSearchEdit = new JLabel("CGM Aluno:  ");
        jtSearchEdit = new UJComboBox(fillComboBox());
        jtSearchEdit.setAutocompletar(true);
        jtSearchEdit.setPreferredSize(new Dimension(510, 30));

        JLabel lbName = new JLabel("Nome: ");
        final JTextField jtEditName = new JTextField(40);

        JLabel lbSerie = new JLabel("Série: ");
        final JTextField jtEditSerie = new JTextField(40);

        JLabel lbFiliation = new JLabel("Filiação: ");
        final JTextField jtEditFiliation = new JTextField(40);

        JLabel lbAddress = new JLabel("Endereço: ");
        final JTextField jtEditAddress = new JTextField(40);

        JLabel lbPhone = new JLabel("Telefone: ");
        final JFormattedTextField jtEditPhone = new JFormattedTextField((setMascaraPhone("(##) ####-####             ")));
        jtEditPhone.setPreferredSize(new Dimension(450, 30));

        JButton btnSave = new JButton("Salvar");
        ActionListener update = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jtEditName.getText().isEmpty() && !jtEditSerie.getText().isEmpty() && !jtEditFiliation.getText().isEmpty() && !jtEditAddress.getText().isEmpty() && !jtEditPhone.getText().isEmpty() && jtSearchEdit.getSelectedItem() != "--Escolha um CGM--") {
                    student.updateStudent(jtSearchEdit.getSelectedItem().toString(), jtEditName.getText(), jtEditSerie.getText(), jtEditFiliation.getText(), jtEditAddress.getText(), jtEditPhone.getText());
                    JOptionPane.showMessageDialog(frameStudent, "Registro Atualizado com Sucesso!!!", "Editar", JOptionPane.INFORMATION_MESSAGE);
                    clearTable();
                    fillTable();
                    tabbedPane.setSelectedIndex(2);

                } else {
                    JOptionPane.showMessageDialog(frameStudent, "Não Pode ter campos em Branco ou Não Selecionados!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        btnSave.addActionListener(update);

        JPanel panel0 = new JPanel();
        panel0.add(lbSearchEdit);
        panel0.add(jtSearchEdit);
        panel0.setLayout(new GridLayout(2, 1));

        JPanel panel1 = new JPanel();
        panel1.add(lbName);
        panel1.add(jtEditName);
        panel1.setLayout(new GridLayout(2, 1));

        JPanel panel2 = new JPanel();
        panel2.add(lbSerie);
        panel2.add(jtEditSerie);
        panel2.setLayout(new GridLayout(2, 1));

        JPanel panel3 = new JPanel();
        panel3.add(lbFiliation);
        panel3.add(jtEditFiliation);
        panel3.setLayout(new GridLayout(2, 1));

        JPanel panel4 = new JPanel();
        panel4.add(lbAddress);
        panel4.add(jtEditAddress);
        panel4.setLayout(new GridLayout(2, 1));

        JPanel panel5 = new JPanel();
        panel5.add(lbPhone);
        panel5.add(jtEditPhone);
        panel5.setLayout(new GridLayout(2, 1));

        JPanel panel6 = new JPanel();
        panel6.add(btnSave);
        panel6.setLayout(new GridLayout(1, 2));

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 22));

        jtSearchEdit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    ResultSet rs = student.selectStudent(jtSearchEdit.getSelectedItem().toString());
                    while (rs.next()) {
                        jtEditName.setText(rs.getString("nome"));
                        jtEditSerie.setText(rs.getString("serie"));
                        jtEditFiliation.setText(rs.getString("filiacao"));
                        jtEditAddress.setText(rs.getString("endereco"));
                        jtEditPhone.setText(rs.getString("telefone"));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(OptionsStudent.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        panel.add(panel0);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel5);
        panel.add(panel4);
        panel.add(panel6);

        return panel;
    }

    public Vector fillComboBox() {
        try {
            ResultSet rs = student.selectAll();
            Vector vector = new Vector();
            vector.add(("--Escolha um CGM--"));

            while (rs.next()) {
                vector.add(rs.getString("cgm"));
            }
            return vector;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
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

    public void clearTable() {
        model.setNumRows(0);
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
                student.insertStudent(codeStudent, name, serie, filiation, address, phone);
                model.addRow(new String[]{codeStudent, name, serie, filiation, address, phone});
                JOptionPane.showMessageDialog(frameStudent, "Registro Salvo com sucesso!!!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                jtSearchEdit.addItem(codeStudent);
                tabbedPane.setSelectedIndex(2);
                jtCodeStudent.setText("");
                jtAddress.setText("");
                jtFiliation.setText("");
                jtName.setText("");
                jtSerie.setText("");
                jtPhone.setText("");
            } else {
                JOptionPane.showMessageDialog(frameStudent, "Esse CGM já Existe!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class RemoveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int answer = JOptionPane.showConfirmDialog(frameStudent, "Deseja remover registro?", "Remoção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.OK_OPTION) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String codeStudent = table.getValueAt(row, 0).toString();
                    student.deleteStudent(codeStudent);
                    model.removeRow(row); //remove a linha

                    JOptionPane.showMessageDialog(frameStudent, "Registro excluído com sucesso!!!", "Remover", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}
