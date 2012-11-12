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
import model.Aluno;
import model.Editora;
import model.Emprestimo;
import model.Livro;

/**
 * Trabalho de .. Professor ..
 *
 * @author Alexandre
 * @version 1.0
 */
public class OpcoesLivros extends Padrao {

    private Aluno aluno;
    private Livro livro;
    private Emprestimo emprestimo;
    private Editora editora;
    private JTextField jtCod;
    private JTextField jtTitulo;
    private JTextField jtAutor;
    private JTextField jtBox;
    private JTextField jtEstante;
    private JTextField jtArea;
    private UJComboBox jtEditora;
    private JButton btnRemover;
    private JButton btnVoltar;
    private JTable tabela;
    private JTabbedPane tabbedPane;
    private Boolean changed = false;
    private DefaultTableModel modelo = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            if (mColIndex == 0) {
                return false;
            }
            return true;
        }
    };

    public OpcoesLivros(Aluno aluno, Editora editora, Livro livro, Emprestimo emprestimo) {
        super();
        this.aluno = aluno;
        this.livro = livro;
        this.editora = editora;
        this.emprestimo = emprestimo;
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
        listar(panel2);
        editar();
        tabbedPane.addTab("Listar", null, panel2);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public JComponent cadastrar(JComponent panel) {
        changed = false;
        JLabel lbCod = new JLabel(" Código: ");
        jtCod = new JTextField(45);

        JLabel lbNome = new JLabel(" Título:    ");
        jtTitulo = new JTextField(45);

        JLabel lbAutor = new JLabel(" Autor:     ");
        jtAutor = new JTextField(45);

        JLabel lbEditora = new JLabel(" Editora:  ");
        jtEditora = new UJComboBox(preencheComboBox());
        jtEditora.setEditable(true);
        jtEditora.setAutocompletar(true);
        jtEditora.setPreferredSize(new Dimension(510,30));

        JLabel lbbox = new JLabel(" Box:        ");
        jtBox = new JTextField(45);

        JLabel lbEstante = new JLabel(" Estante: ");
        jtEstante = new JTextField(45);

        JLabel lbArea = new JLabel(" Área:      ");
        jtArea = new JTextField(45);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new OpcoesLivros.SalvarActionListener());
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new OpcoesLivros.VoltarActionListener());

        JPanel panel0 = new JPanel();
        panel0.add(lbCod);
        panel0.add(jtCod);
        panel0.setLayout(new GridLayout(2, 1));

        JPanel panel1 = new JPanel();
        panel1.add(lbNome);
        panel1.add(jtTitulo);
        panel1.setLayout(new GridLayout(2, 1));

        JPanel panel2 = new JPanel();
        panel2.add(lbAutor);
        panel2.add(jtAutor);
        panel2.setLayout(new GridLayout(2, 1));

        JPanel panel4 = new JPanel();
        panel4.add(lbEditora);
        panel4.add(jtEditora);
        panel4.setLayout(new GridLayout(2, 1));

        JPanel panel3 = new JPanel();
        panel3.add(lbbox);
        panel3.add(jtBox);
        panel3.setLayout(new GridLayout(2, 1));

        JPanel panel5 = new JPanel();
        panel5.add(lbEstante);
        panel5.add(jtEstante);
        panel5.setLayout(new GridLayout(2, 1));

        JPanel panel6 = new JPanel();
        panel6.add(lbArea);
        panel6.add(jtArea);
        panel6.setLayout(new GridLayout(2, 1));

        JPanel panel7 = new JPanel();
        panel7.add(btnSalvar);
        panel7.add(btnVoltar);
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

    public JComponent listar(JComponent panel) {
        changed = true;
        tabela = new JTable(modelo);

        modelo.addColumn("Código");
        modelo.addColumn("Titulo");
        modelo.addColumn("Autor");
        modelo.addColumn("Box");
        modelo.addColumn("Editora");
        modelo.addColumn("Estante");
        modelo.addColumn("Área");

        preencheTabela();

        final JScrollPane scrollPane = new JScrollPane(tabela);
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

        btnRemover = new JButton("Remover");
        btnRemover.addActionListener(new OpcoesLivros.RemoverActionListener());

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new OpcoesLivros.VoltarActionListener());

        panel.add(scrollPane);
        panel.add(btnRemover);
        panel.add(btnVoltar);

        return panel;
    }

    public void preencheTabela() {
        try {
            ResultSet rs = livro.selectAll();
            while (rs.next()) {
                modelo.addRow(new String[]{
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

    public Vector preencheComboBox() {
        try {
            ResultSet rs = editora.selectAll();
            Vector vetor = new Vector();
            vetor.add(("--Escolha/Insira uma Editora--"));

            while (rs.next()) {
                vetor.add(rs.getString("nome"));
            }
            return vetor;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private class SalvarActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cod = jtCod.getText();
            String titulo = jtTitulo.getText();
            String autor = jtAutor.getText();
            String box = jtBox.getText();
            String edit = jtEditora.getSelectedItem().toString();
            String estante = jtEstante.getText();
            String area = jtArea.getText();
            if (!cod.isEmpty() && !titulo.isEmpty() && !autor.isEmpty() && !box.isEmpty() && !"--Escolha/Insira uma Editora--".equals(edit) && !estante.isEmpty() && !area.isEmpty()) {
                if (!livro.selectCod(cod)) {
                    changed = false;
                    livro.insertLivro(cod, titulo, autor, box, edit, estante, area);
                    modelo.addRow(new String[]{cod, titulo, autor, box, edit, estante, area});
                    JOptionPane.showMessageDialog(frame, "Livro Salvo com Sucesso!!!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                    tabbedPane.setSelectedIndex(1);
                    jtCod.setText("");
                    jtEstante.setText("");
                    jtBox.setText("");
                    jtTitulo.setText("");
                    jtAutor.setText("");
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

    private class VoltarActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            changed = false;
            frame.setVisible(false);
            new Principal(aluno, editora, livro, emprestimo);
        }
    }

    private class RemoverActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            changed = false;
            int resposta = JOptionPane.showConfirmDialog(frame, "Deseja remover registro?", "Remoção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (resposta == JOptionPane.OK_OPTION) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    String codigo = tabela.getValueAt(linha, 0).toString();
                    livro.deleteLivro(codigo);
                    modelo.removeRow(linha); //remove a linha

                    JOptionPane.showMessageDialog(frame,
                            "Registro excluído com sucesso!!!",
                            "Remover", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    public void editar() {
        tabela.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                if (changed) {
                    int resposta = JOptionPane.showConfirmDialog(frame, "Deseja editar esse registro?", "Edição", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (resposta == JOptionPane.OK_OPTION) {
                        int linha = tabela.getSelectedRow();
                        int coluna = tabela.getSelectedColumn();
                        String cod = tabela.getValueAt(linha, 0).toString();
                        String aux = tabela.getValueAt(linha, coluna).toString();
                        String tipo = null;
                        if (coluna == 1) {
                            tipo = "titulo";
                        }
                        if (coluna == 2) {
                            tipo = "autor";
                        }
                        if (coluna == 3) {
                            tipo = "box";
                        }
                        if (coluna == 4) {
                            tipo = "editora";
                        }
                        if (coluna == 5) {
                            tipo = "estante";
                        }
                        if (coluna == 6) {
                            tipo = "area";
                        }
                        if (coluna == 0) {
                            JOptionPane.showMessageDialog(frame,
                                    "Código não pode ser Modificado!",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        if (linha >= 0 && coluna != 0) {
                            if (!aux.isEmpty()) {
                                if (!"editora".equals(tipo)) {
                                    livro.updateCampo(cod, aux, tipo);
                                } else {
                                    String cod2 = String.valueOf(editora.selectEditora(aux));
                                    editora.updateCampo(cod2, aux);
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
