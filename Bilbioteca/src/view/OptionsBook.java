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
import model.Book;
import model.Publisher;

/**
 * Trabalho de .. Professor ..
 *
 * @author Alexandre
 * @version 1.0
 */
public class OptionsBook {

    public JDialog frameBook;
    private JTabbedPane tabbedPane;
    private Book book;
    private Publisher publisher;
    private JTextField jtCodeBook;
    private JTextField jtTitle;
    private JTextField jtAuthor;
    private JTextField jtBox;
    private JTextField jtBookcase;
    private JTextField jtArea;
    private UJComboBox jtPublisher;

    private JTable table;
    private DefaultTableModel model = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            return true;
        }
    };
    private UJComboBox jtSearchEdit;

    public OptionsBook(Book book, Publisher publisher) {
        this.book = book;
        this.publisher = publisher;
    }

    public void init() {
        frameBook = new JDialog();
        frameBook.setTitle("Cadastrar Livros");
        frameBook.setModal(true);

        Template.lookAndFeel();
        initComponents();

        frameBook.setSize(800, 600);
        frameBook.setFocusable(true);
        frameBook.setLocationRelativeTo(null); //centraliza a tela 
        frameBook.setVisible(true);
    }

    private void initComponents() {
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

        frameBook.add(tabbedPane);
    }

    private JComponent register(JComponent panel) {
        JLabel lbCode = new JLabel(" Código: ");
        jtCodeBook = new JTextField(45);

        JLabel lbTitle = new JLabel(" Título:    ");
        jtTitle = new JTextField(45);

        JLabel lbAuthor = new JLabel(" Autor:     ");
        jtAuthor = new JTextField(45);

        JLabel lbPublisher = new JLabel(" Editora:  ");
        jtPublisher = new UJComboBox(fillComboBox());
        jtPublisher.setEditable(true);
        jtPublisher.setAutocompletar(true);
        jtPublisher.setPreferredSize(new Dimension(510, 30));

        JLabel lbbox = new JLabel(" Box:        ");
        jtBox = new JTextField(45);

        JLabel lbBookcase = new JLabel(" Estante: ");
        jtBookcase = new JTextField(45);

        JLabel lbArea = new JLabel(" Área:      ");
        jtArea = new JTextField(45);
        
        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(new OptionsBook.SaveActionListener());

        JPanel panel0 = new JPanel();
        panel0.add(lbCode);
        panel0.add(jtCodeBook);
        panel0.setLayout(new GridLayout(2, 1));

        JPanel panel1 = new JPanel();
        panel1.add(lbTitle);
        panel1.add(jtTitle);
        panel1.setLayout(new GridLayout(2, 1));

        JPanel panel2 = new JPanel();
        panel2.add(lbAuthor);
        panel2.add(jtAuthor);
        panel2.setLayout(new GridLayout(2, 1));

        JPanel panel4 = new JPanel();
        panel4.add(lbPublisher);
        panel4.add(jtPublisher);
        panel4.setLayout(new GridLayout(2, 1));

        JPanel panel3 = new JPanel();
        panel3.add(lbbox);
        panel3.add(jtBox);
        panel3.setLayout(new GridLayout(2, 1));

        JPanel panel5 = new JPanel();
        panel5.add(lbBookcase);
        panel5.add(jtBookcase);
        panel5.setLayout(new GridLayout(2, 1));

        JPanel panel6 = new JPanel();
        panel6.add(lbArea);
        panel6.add(jtArea);
        panel6.setLayout(new GridLayout(2, 1));

        JPanel panel7 = new JPanel();
        panel7.add(btnSave);
        panel7.setLayout(new GridLayout(1, 1));

        panel.add(panel0);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel4);
        panel.add(panel3);
        panel.add(panel5);
        panel.add(panel6);
        panel.add(panel7);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 13));

        return panel;

    }

    public Vector fillComboBox() {
        try {
            ResultSet rs = publisher.selectAll();
            Vector vector = new Vector();
            vector.add(("--Escolha/Insira uma Editora--"));

            while (rs.next()) {
                vector.add(rs.getString("nome"));
            }
            return vector;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private JComponent edit(JComponent panel) {
        JLabel lbSearchEdit = new JLabel("CGM Aluno:  ");
        jtSearchEdit = new UJComboBox(fillComboBoxBook());
        jtSearchEdit.setAutocompletar(true);
        jtSearchEdit.setPreferredSize(new Dimension(510, 30));
        
        JLabel lbTitle = new JLabel("Titulo: ");
        final JTextField jtEditTitle = new JTextField(45);

        JLabel lbAuthor = new JLabel("Autor: ");
        final JTextField jtEditAuthor = new JTextField(45);

        JLabel lbPublisher = new JLabel("Editora: ");
        final JTextField jtEditPublisher = new JTextField(45);

        JLabel lbBox = new JLabel("Box: ");
        final JTextField jtEditBox = new JTextField(45);
        
        JLabel lbBookcase = new JLabel("Estante: ");
        final JTextField jtEditBookcase = new JTextField(45);
        
        JLabel lbArea = new JLabel("Área: ");
        final JTextField jtEditArea = new JTextField(45);
        
        JButton btnSave = new JButton("Salvar");
        ActionListener update = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jtEditTitle.getText().isEmpty() && !jtEditAuthor.getText().isEmpty() && !jtEditPublisher.getText().isEmpty() && !jtEditBox.getText().isEmpty() && !jtEditBookcase.getText().isEmpty() && !jtEditBox.getText().isEmpty() && !jtEditArea.getText().isEmpty() && !"--Escolha um Livro--".equals(jtSearchEdit.getSelectedItem().toString())) {
                    //student.updateStudent(jtSearchEdit.getSelectedItem().toString(), jtEditName.getText(), jtEditSerie.getText(), jtEditFiliation.getText(), jtEditAddress.getText(), jtEditPhone.getText());
                    JOptionPane.showMessageDialog(frameBook, "Registro Atualizado com Sucesso!!!", "Editar", JOptionPane.INFORMATION_MESSAGE);
                    clearTable();
                    fillTable();
                    tabbedPane.setSelectedIndex(2);

                } else {
                    JOptionPane.showMessageDialog(frameBook, "Não Pode ter campos em Branco ou Não Selecionados!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        btnSave.addActionListener(update);
        
        
        JPanel panel0 = new JPanel();
        panel0.add(lbSearchEdit);
        panel0.add(jtSearchEdit);
        panel0.setLayout(new GridLayout(2, 1));
        
        JPanel panel1 = new JPanel();
        panel1.add(lbTitle);
        panel1.add(jtEditTitle);
        panel1.setLayout(new GridLayout(2, 1));

        JPanel panel2 = new JPanel();
        panel2.add(lbAuthor);
        panel2.add(jtEditAuthor);
        panel2.setLayout(new GridLayout(2, 1));

        JPanel panel3 = new JPanel();
        panel3.add(lbPublisher);
        panel3.add(jtEditPublisher);
        panel3.setLayout(new GridLayout(2, 1));

        JPanel panel4 = new JPanel();
        panel4.add(lbBox);
        panel4.add(jtEditBox);
        panel4.setLayout(new GridLayout(2, 1));
        
        JPanel panel5 = new JPanel();
        panel5.add(lbBookcase);
        panel5.add(jtEditBookcase);
        panel5.setLayout(new GridLayout(2, 1));
        
        JPanel panel6 = new JPanel();
        panel6.add(lbArea);
        panel6.add(jtEditArea);
        panel6.setLayout(new GridLayout(2, 1));
        
        JPanel panel7 = new JPanel();
        panel7.add(btnSave);
        panel7.setLayout(new GridLayout(1, 2));
        
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 13));

        jtSearchEdit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    ResultSet rs = book.selectBooks(jtSearchEdit.getSelectedItem().toString());
                    while (rs.next()) {
                        jtEditTitle.setText(rs.getString("titulo"));
                        jtEditAuthor.setText(rs.getString("autor"));
                        jtEditPublisher.setText(rs.getString("editora"));
                        jtEditBox.setText(rs.getString("box"));
                        jtEditBookcase.setText(rs.getString("estante"));
                        jtEditArea.setText(rs.getString("area"));
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
        panel.add(panel4);
        panel.add(panel5);
        panel.add(panel6);
        panel.add(panel7);
        
        return panel;
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

    private JComponent list(JComponent panel) {
        if (table == null) {
            table = new JTable(model);

            model.addColumn("Código");
            model.addColumn("Titulo");
            model.addColumn("Autor");
            model.addColumn("Box");
            model.addColumn("Editora");
            model.addColumn("Estante");
            model.addColumn("Área");

            fillTable();
        }

        final JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        frameBook.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                scrollPane.setPreferredSize(new Dimension(frameBook.getWidth() - 100, frameBook.getHeight() - 150));
            }

            public void componentMoved(java.awt.event.ComponentEvent e) {
                scrollPane.setPreferredSize(new Dimension(frameBook.getWidth() - 100, frameBook.getHeight() - 150));
            }
        });
        JButton btnDelete = new JButton("Remover");
        btnDelete.addActionListener(new OptionsBook.DeleteActionListener());

        panel.add(scrollPane);
        panel.add(btnDelete);

        return panel;
    }

    public void fillTable() {
        try {
            ResultSet rs = book.selectAllBooks();
            while (rs.next()) {
                model.addRow(new String[]{
                            rs.getString("idlivro"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("box"),
                            rs.getString("editora"),
                            rs.getString("estante"),
                            rs.getString("area")});
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void clearTable() {
        model.setNumRows(0);
    }

    private class SaveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String code = jtCodeBook.getText();
            String title = jtTitle.getText();
            String author = jtAuthor.getText();
            String box = jtBox.getText();
            String publisher = jtPublisher.getSelectedItem().toString();
            String bookcase = jtBookcase.getText();
            String area = jtArea.getText();
            if (!code.isEmpty() && !title.isEmpty() && !author.isEmpty() && !box.isEmpty() && !"--Escolha/Insira uma Editora--".equals(publisher) && !bookcase.isEmpty() && !area.isEmpty()) {
                if (!book.selectCodeBook(code)) {
                    book.insertBook(code, title, author, box, publisher, bookcase, area);
                    model.addRow(new String[]{code, title, author, box, publisher, bookcase, area});
                    JOptionPane.showMessageDialog(frameBook, "Livro Salvo com Sucesso!!!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                    jtSearchEdit.addItem(code);
                    tabbedPane.setSelectedIndex(2);
                    jtCodeBook.setText("");
                    jtBookcase.setText("");
                    jtBox.setText("");
                    jtTitle.setText("");
                    jtAuthor.setText("");
                    jtArea.setText("");
                } else {
                    JOptionPane.showMessageDialog(frameBook, "Esse Código já Existe!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frameBook, "Não Pode ter campos em Branco ou Não Selecionados!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int answer = JOptionPane.showConfirmDialog(frameBook, "Deseja remover registro?", "Remoção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.OK_OPTION) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String code = table.getValueAt(row, 0).toString();
                    book.deleteBook(code);
                    model.removeRow(row); //remove a linha

                    JOptionPane.showMessageDialog(frameBook,
                            "Registro excluído com sucesso!!!",
                            "Remover", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}
