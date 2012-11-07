package view;

import com.toedter.calendar.JDateChooser;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
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
public class OpcoesGeral extends Padrao {

    private Aluno aluno;
    private Livro livro;
    private Emprestimo emprestimo;
    private Editora editora;
    private JTabbedPane tabbedPane;
    private JComboBox jtAluno;
    private JComboBox jtLivro;
    private JButton btnVoltar;

    public OpcoesGeral(Aluno aluno, Livro livro, Editora editora, Emprestimo emprestimo) {
        super();
        this.aluno = aluno;
        this.livro = livro;
        this.editora = editora;
        this.emprestimo = emprestimo;
        init();
    }

    public void init() {
        frame.setTitle("Opções");
        tabbedPane = new JTabbedPane();
        //ImageIcon icon = createImageIcon("images/middle.gif");

        JComponent panel1 = new JPanel();
        emprestimo(panel1);
        tabbedPane.addTab("Empréstimo", panel1);

        JComponent panel2 = new JPanel();
        //listar(panel2);
        tabbedPane.addTab("Devolução", null, panel2);

        JComponent panel3 = new JPanel();
        //listar(panel2);
        tabbedPane.addTab("Renovação", null, panel3);

        JComponent panel4 = new JPanel();
        //listar(panel2);
        tabbedPane.addTab("Histórico Aluno", null, panel4);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public JComponent emprestimo(JComponent panel) {
        JLabel lbCGM = new JLabel(" CGM:                      ");
        jtAluno = new JComboBox(preencheComboBoxAluno());

        JLabel lbLivro = new JLabel(" Livro:                      ");
        jtLivro = new JComboBox(preencheComboBoxLivro());

        JLabel lbDataSaida = new JLabel(" Data Saida:           ");
        JDateChooser dataSaida = new JDateChooser();
        
        JLabel lbDataDevolucao = new JLabel(" Data Devolucao:  ");
        JDateChooser dataDevolucao = new JDateChooser();
        
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new OpcoesGeral.VoltarActionListener());
        
        JPanel panel0 = new JPanel();
        panel0.add(lbCGM);
        panel0.add(jtAluno);
        panel0.setLayout(new GridLayout(2,1));

        JPanel panel1 = new JPanel();
        panel1.add(lbLivro);
        panel1.add(jtLivro);
        panel1.setLayout(new GridLayout(2,1));
        
        JPanel panel2 = new JPanel();
        panel2.add(lbDataSaida);
        panel2.add(dataSaida);
        panel2.setLayout(new GridLayout(2,1));
        
        JPanel panel3 = new JPanel();
        panel3.add(lbDataDevolucao);
        panel3.add(dataDevolucao);
        panel3.setLayout(new GridLayout(2,1));
        
        JPanel panel4 = new JPanel();
        panel4.add(btnVoltar);
        panel4.setLayout(new GridLayout(1,1));

        panel.add(panel0);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 70, 20));
        return panel;
    }

    public Vector preencheComboBoxAluno() {
        try {
            ResultSet rs = aluno.selectAll();
            Vector vetor = new Vector();
            vetor.add(("--Escolha um Aluno--"));

            while (rs.next()) {
                vetor.add(rs.getString("CGM"));
            }
            return vetor;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Vector preencheComboBoxLivro() {
        try {
            ResultSet rs = livro.selectAll();
            Vector vetor = new Vector();
            vetor.add(("--Escolha um Livro--"));

            while (rs.next()) {
                vetor.add(rs.getString("idlivro"));
            }
            return vetor;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    private class VoltarActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            new Principal(aluno, editora,livro, emprestimo);
        }
    }
}
