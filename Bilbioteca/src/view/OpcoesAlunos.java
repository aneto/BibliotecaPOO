package view;

import java.awt.Container;
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
public class OpcoesAlunos extends Padrao {
    private Aluno aluno;
    private Livro livro;
    private Emprestimo emprestimo;
    private Editora editora;
    private JFormattedTextField jtTelefone;
    private JTextField jtCgm;
    private JTextField jtNome;
    private JTextField jtSerie;
    private JTextField jtFiliacao;
    private JTextField jtEndereco;
    private JButton btnEditar;
    private JButton btnRemover;
    private JButton btnVoltar;
    private JTable tabela;
    private JTabbedPane tabbedPane;
    private Boolean changed = false;
    private DefaultTableModel modelo = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            if (mColIndex == 0) return false;
            return true;
        }
    };

    public OpcoesAlunos(Aluno aluno, Editora editora, Livro livro, Emprestimo emprestimo) {
        super();
        this.aluno = aluno;
        this.livro = livro;
        this.editora = editora;
        this.emprestimo = emprestimo;
        init();
    }

    public void init() {
        frame.setTitle("Cadastrar Aluno");
        tabbedPane = new JTabbedPane();
        //ImageIcon icon = createImageIcon("images/middle.gif");

        JComponent panel1 = new JPanel();
        cadastrar(panel1);
        tabbedPane.addTab("Cadastrar", panel1);

        JComponent panel2 = new JPanel();
        listar(panel2);
        tabbedPane.addTab("Listar", null, panel2);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public JComponent cadastrar(JComponent panel) {
        changed = false;
        JLabel lbCgm = new JLabel("CGM: ");
        jtCgm = new JTextField(40);

        JLabel lbNome = new JLabel("Nome: ");
        jtNome = new JTextField(40);

        JLabel lbSerie = new JLabel("Série: ");
        jtSerie = new JTextField(40);

        JLabel lbFiliacao = new JLabel("Filiação: ");
        jtFiliacao = new JTextField(40);

        JLabel lbEndereco = new JLabel("Endereço: ");
        jtEndereco = new JTextField(40);

        JLabel lbTelefone = new JLabel("Telefone: ");
        jtTelefone = new JFormattedTextField((setMascaraTelefone("(##) ####-####             ")));
        jtTelefone.setPreferredSize(new Dimension(450,30));

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new OpcoesAlunos.SalvarActionListener());
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new OpcoesAlunos.VoltarActionListener());

        JPanel panel0 = new JPanel();
        panel0.add(lbCgm);
        panel0.add(jtCgm);
        panel0.setLayout(new GridLayout(2,1));

        JPanel panel1 = new JPanel();
        panel1.add(lbNome);
        panel1.add(jtNome);
        panel1.setLayout(new GridLayout(2,1));

        JPanel panel2 = new JPanel();
        panel2.add(lbSerie);
        panel2.add(jtSerie);
        panel2.setLayout(new GridLayout(2,1));

        JPanel panel3 = new JPanel();
        panel3.add(lbFiliacao);
        panel3.add(jtFiliacao);
        panel3.setLayout(new GridLayout(2,1));

        JPanel panel4 = new JPanel();
        panel4.add(lbEndereco);
        panel4.add(jtEndereco);
        panel4.setLayout(new GridLayout(2,1));

        JPanel panel5 = new JPanel();
        panel5.add(lbTelefone);
        panel5.add(jtTelefone);
        panel5.setLayout(new GridLayout(2,1));

        JPanel panel6 = new JPanel();
        panel6.add(btnSalvar);
        panel6.add(btnVoltar);
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

    public JComponent listar(JComponent panel) {
        changed = true;
        tabela = new JTable(modelo);
        
        modelo.addColumn("CGM");
        modelo.addColumn("Nome");
        modelo.addColumn("Serie");
        modelo.addColumn("Filiação");
        modelo.addColumn("Endereço");
        modelo.addColumn("Telefone");

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
        btnRemover.addActionListener(new OpcoesAlunos.RemoverActionListener());

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new OpcoesAlunos.VoltarActionListener());

        panel.add(scrollPane);
        panel.add(btnRemover);
        panel.add(btnVoltar);

        tabela.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (changed) {
                    int resposta = JOptionPane.showConfirmDialog(frame, "Deseja editar esse registro?", "Edição", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (resposta == JOptionPane.OK_OPTION) {
                        int linha = tabela.getSelectedRow();
                        int coluna = tabela.getSelectedColumn();
                        String cgm = tabela.getValueAt(linha, 0).toString();
                        String aux = tabela.getValueAt(linha, coluna).toString();
                        String tipo = null;
                        if (coluna == 1) {
                            tipo = "nome";
                        }
                        if (coluna == 2) {
                            tipo = "serie";
                        }
                        if (coluna == 3) {
                            tipo = "filiacao";
                        }
                        if (coluna == 4) {
                            tipo = "endereco";
                        }
                        if (coluna == 5) {
                            tipo = "telefone";
                        }
                        if (coluna == 0) {
                            JOptionPane.showMessageDialog(frame,
                                    "CGM não pode ser Modificado!",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        if (linha >= 0 && coluna != 0) {

                            aluno.updateCampo(cgm, aux, tipo);
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
    
    public void preencheTabela() {
        try {
            ResultSet rs = aluno.selectAll();
            while (rs.next()) {
                modelo.addRow(new String[]{
                            rs.getString("CGM"),
                            rs.getString("nome"),
                            rs.getString("serie"),
                            rs.getString("filiacao"),
                            rs.getString("endereco"),
                            rs.getString("telefone")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(OpcoesAlunos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private MaskFormatter setMascaraTelefone(String mascara) {
        MaskFormatter mask = null;
        try {
            mask = new MaskFormatter(mascara);
        } catch (java.text.ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return mask;
    }


    private class SalvarActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cgm = jtCgm.getText();
            String nome = jtNome.getText();
            String serie = jtSerie.getText();
            String telefone = jtTelefone.getText();
            String filiacao = jtFiliacao.getText();
            String endereco = jtEndereco.getText();


            if (!aluno.selectCGM(cgm)) {
                changed = false;
                aluno.insertAluno(cgm, nome, serie, filiacao, endereco, telefone);
                modelo.addRow(new String[]{cgm, nome, serie, filiacao, endereco, telefone});
                JOptionPane.showMessageDialog(frame, "Registro Salvo com sucesso!!!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                tabbedPane.setSelectedIndex(1);
                jtCgm.setText("");jtEndereco.setText("");jtFiliacao.setText("");
                jtNome.setText("");jtSerie.setText("");jtTelefone.setText("");
            } else {
                changed = false;
                JOptionPane.showMessageDialog(frame, "Esse CGM já Existe!", "Error", JOptionPane.ERROR_MESSAGE);
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

    private class EditarActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                String cgm      = tabela.getValueAt(linha, 0).toString();
                String nome     = tabela.getValueAt(linha, 1).toString();
                String serie    = tabela.getValueAt(linha, 2).toString();
                String telefone = tabela.getValueAt(linha, 3).toString();
                String filiacao = tabela.getValueAt(linha, 4).toString();
                String endereco = tabela.getValueAt(linha, 5).toString();

                System.out.println(nome);
                aluno.updateAluno(cgm, nome, serie, filiacao, endereco, telefone);
            }
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
                    aluno.deleteAluno(codigo);
                    modelo.removeRow(linha); //remove a linha

                    JOptionPane.showMessageDialog(frame,
                            "Registro excluído com sucesso!!!",
                            "Remover", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}
