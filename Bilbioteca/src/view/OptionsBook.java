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
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
public class OptionsBook extends Template {

    private Student student;
    private Book book;
    private Lending lending;
    private Publisher publisher;
    private JTextField jtCodeBook;
    private JTextField jtTitle;
    private JTextField jtAuthor;
    private JTextField jtBox;
    private JTextField jtBookcase;
    private JTextField jtArea;
    private UJComboBox jtPublisher;
    private JButton btnSave;
    private JButton btnDelete;
    private JButton btnBack;
    private JTable table;
    private JTabbedPane tabbedPane;
    private Boolean changed = false;
    private DefaultTableModel model = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            if (mColIndex == 0) {
                return false;
            }
            return true;
        }
    };

    public OptionsBook(Student student, Publisher publisher, Book book, Lending lending) {
        super();
        this.student = student;
        this.book = book;
        this.publisher = publisher;
        this.lending = lending;
        init();
    }

    public void init() {
        frame.setTitle("Cadastrar Livros");
        tabbedPane = new JTabbedPane();
        //ImageIcon icon = createImageIcon("images/middle.gif");

        JComponent panel1 = new JPanel();
        cadastrar(panel1);
        tabbedPane.addTab("Cadastrar", panel1);

        JComponent panel2 = new JPanel();
        list(panel2);
        edit();
        tabbedPane.addTab("Listar", null, panel2);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public JComponent cadastrar(JComponent panel) {
        changed = false;
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

        btnSave = new JButton("Salvar");
        btnSave.addActionListener(new OptionsBook.SaveActionListener());
        btnBack = new JButton("Voltar");
        btnBack.addActionListener(new OptionsBook.BackActionListener());

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
        panel7.add(btnBack);
        panel7.setLayout(new GridLayout(1, 2));

        panel.add(panel0);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.add(panel5);
        panel.add(panel6);
        panel.add(panel7);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 13));

        return panel;
    }

    public JComponent list(JComponent panel) {
        changed = true;
        table = new JTable(model);

        model.addColumn("Código");
        model.addColumn("Titulo");
        model.addColumn("Autor");
        model.addColumn("Box");
        model.addColumn("Editora");
        model.addColumn("Estante");
        model.addColumn("Área");

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

        btnDelete = new JButton("Remover");
        btnDelete.addActionListener(new OptionsBook.DeleteActionListener());

        btnBack = new JButton("Voltar");
        btnBack.addActionListener(new OptionsBook.BackActionListener());

        panel.add(scrollPane);
        panel.add(btnDelete);
        panel.add(btnBack);

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
                    changed = false;
                    book.insertBook(code, title, author, box, publisher, bookcase, area);
                    model.addRow(new String[]{code, title, author, box, publisher, bookcase, area});
                    JOptionPane.showMessageDialog(frame, "Livro Salvo com Sucesso!!!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                    tabbedPane.setSelectedIndex(1);
                    jtCodeBook.setText("");
                    jtBookcase.setText("");
                    jtBox.setText("");
                    jtTitle.setText("");
                    jtAuthor.setText("");
                    jtArea.setText("");
                } else {
                    changed = false;
                    JOptionPane.showMessageDialog(frame, "Esse Código já Existe!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Não Pode ter campos em Branco ou Não Selecionados!", "Erro", JOptionPane.ERROR_MESSAGE);
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

    private class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            changed = false;
            int answer = JOptionPane.showConfirmDialog(frame, "Deseja remover registro?", "Remoção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.OK_OPTION) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String code = table.getValueAt(row, 0).toString();
                    book.deleteBook(code);
                    model.removeRow(row); //remove a linha

                    JOptionPane.showMessageDialog(frame,
                            "Registro excluído com sucesso!!!",
                            "Remover", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    public void edit() {
        table.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                if (changed) {
                    int answer = JOptionPane.showConfirmDialog(frame, "Deseja editar esse registro?", "Edição", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.OK_OPTION) {
                        int row = table.getSelectedRow();
                        int column = table.getSelectedColumn();
                        String code = table.getValueAt(row, 0).toString();
                        String value = table.getValueAt(row, column).toString();
                        String field = null;
                        if (column == 1) {
                            field = "titulo";
                        }
                        if (column == 2) {
                            field = "autor";
                        }
                        if (column == 3) {
                            field = "box";
                        }
                        if (column == 4) {
                            field = "editora";
                        }
                        if (column == 5) {
                            field = "estante";
                        }
                        if (column == 6) {
                            field = "area";
                        }
                        if (column == 0) {
                            JOptionPane.showMessageDialog(frame,
                                    "Código não pode ser Modificado!",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        if (row >= 0 && column != 0) {
                            if (!value.isEmpty()) {
                                if (!"editora".equals(field)) {
                                    book.updateFieldBook(code, value, field);
                                } else {
                                    String cod2 = String.valueOf(publisher.selectCodePublisher(value));
                                    publisher.updateFieldPublisher(cod2, value);
                                }
                                JOptionPane.showMessageDialog(frame,
                                        "Registro editado com sucesso!!!",
                                        "Editar", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(frame, "Não Pode ter campos em Branco ou Não Selecionados!", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });
    }
}
