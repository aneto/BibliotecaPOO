package view;

import com.toedter.calendar.JDateChooser;
import componentes.UJComboBox;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Aluno;
import model.Editora;
import model.Emprestimo;
import model.Livro;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

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
    private UJComboBox jtAluno;
    private UJComboBox jtLivro;
    private Calendar dtAtual = Calendar.getInstance();
    private JDateChooser dataSaida;
    private JDateChooser dataDevolucao;
    private JButton btnVoltar;
    private JTextField tfBuscar;
    private JButton btnBuscar;
    private JPanel panelBusca;
    private JTable tabela;
    private DefaultTableModel modelo = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
        }
    };
    private JLabel jtNome;
    private JLabel jtNome2;
    private JButton btnRemover;
    private int largura;
    private int altura;
    private JScrollPane scrollPane;

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
        historico(panel4);
        tabbedPane.addTab("Histórico Aluno", null, panel4);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public JComponent emprestimo(JComponent panel) {
        JLabel lbCGM = new JLabel(" CGM:");
        jtAluno = new UJComboBox(preencheComboBoxAluno());
        jtAluno.setAutocompletar(true);
        
        JLabel lbLivro = new JLabel(" Livro:");
        jtLivro = new UJComboBox(preencheComboBoxLivro());
        jtLivro.setAutocompletar(true);

        JLabel lbDataSaida = new JLabel(" Data Saida:");
        dataSaida = new JDateChooser(dtAtual.getTime());
        dataSaida.setPreferredSize(new Dimension(130, 30));

        JLabel lbDataDevolucao = new JLabel(" Data Devolucao: ");
        dtAtual.add(Calendar.DAY_OF_MONTH, 10);
        dataDevolucao = new JDateChooser(dtAtual.getTime());
        dataDevolucao.setPreferredSize(new Dimension(130, 30));

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new OpcoesGeral.SalvarActionListener());
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new OpcoesGeral.VoltarActionListener());

        JPanel panel0 = new JPanel();
        panel0.add(lbCGM);
        panel0.add(jtAluno);
        panel0.setLayout(new GridLayout(2, 1));

        JPanel panel1 = new JPanel();
        panel1.add(lbLivro);
        panel1.add(jtLivro);
        panel1.setLayout(new GridLayout(2, 1));

        JPanel panel2 = new JPanel();
        panel2.add(lbDataSaida);
        panel2.add(dataSaida);
        panel2.setLayout(new GridLayout(2, 1));

        JPanel panel3 = new JPanel();
        panel3.add(lbDataDevolucao);
        panel3.add(dataDevolucao);
        panel3.setLayout(new GridLayout(2, 1));

        JPanel panel4 = new JPanel();
        panel4.add(btnSalvar);
        panel4.add(btnVoltar);
        panel4.setLayout(new GridLayout(1, 2));

        panel.add(panel0);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 40));
        return panel;
    }

    public JComponent historico(JComponent panel) {
        tfBuscar = new JTextField(35);

        btnBuscar = new JButton("OK");
        btnBuscar.addActionListener(new OpcoesGeral.BtnBuscarActionListener());

        panelBusca = new JPanel();
        panelBusca.add(new JLabel("Busca: "));
        panelBusca.add(tfBuscar);
        panelBusca.add(btnBuscar);

        jtNome = new JLabel("Nome do Aluno: ");
        jtNome2 = new JLabel("");
        JPanel panelNome = new JPanel();
        panelNome.add(jtNome);
        panelNome.add(jtNome2);
        panelNome.setLayout(new GridLayout(1, 1));

        btnRemover = new JButton("Remover");
        btnRemover.addActionListener(new OpcoesGeral.RemoverActionListener());

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new OpcoesGeral.VoltarActionListener());

        tabela = new JTable(modelo);
        //tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        modelo.addColumn("CGM");
        //modelo.addColumn("Nome");
        modelo.addColumn("Cod Livro");
        modelo.addColumn("Data Saida");
        modelo.addColumn("Data Devolução");
        modelo.addColumn("Status");
        scrollPane = new JScrollPane(tabela);
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

        panel.add(panelBusca);
        panel.add(panelNome);
        panel.add(scrollPane);
        panel.add(btnRemover);
        panel.add(btnVoltar);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

        return panel;
    }

    private Boolean preencheTabela(String cgm) {
        boolean existe = false;
        try {
            ResultSet rs = emprestimo.selectAlunoCGM(cgm);
            while (rs.next()) {
                existe = true;
                jtNome2.setText(rs.getString("nome"));
                modelo.addRow(new String[]{
                            rs.getString("Alunos_CGM"),
                            rs.getString("livro_idlivro"),
                            rs.getString("dataSaida"),
                            rs.getString("dataDevolucao"),
                            rs.getString("status")});
            }
            if (existe) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(OpcoesAlunos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private class BtnBuscarActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cgm = tfBuscar.getText();
            if (!preencheTabela(cgm)) {
                JOptionPane.showMessageDialog(frame, "Aluno não encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class RemoverActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int resposta = JOptionPane.showConfirmDialog(frame, "Deseja remover esse registro?", "Remoção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (resposta == JOptionPane.OK_OPTION) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    String codigo = tabela.getValueAt(linha, 0).toString();
                    String dataSaida = tabela.getValueAt(linha, 2).toString();
                    String dataDevolucao = tabela.getValueAt(linha, 3).toString();
                    //emprestimo.deleteEmprestimo(codigo, dataSaida, dataDevolucao);
                    modelo.removeRow(linha); //remove a linha

                    JOptionPane.showMessageDialog(frame,
                            "Registro excluído com sucesso!!!",
                            "Remover", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
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
            new Principal(aluno, editora, livro, emprestimo);
        }
    }

    private class SalvarActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cgmAluno = jtAluno.getSelectedItem().toString();
            String idLivro = jtLivro.getSelectedItem().toString();
            String data = new SimpleDateFormat("dd/MM/yyyy").format(dataSaida.getDate());
            String dtDevolucao = new SimpleDateFormat("dd/MM/yyyy").format(dataDevolucao.getDate());

            if (!"--Escolha um Aluno--".equals(cgmAluno) && !"--Escolha um Livro--".equals(idLivro) && !data.isEmpty() && !dtDevolucao.isEmpty()) {
                if (dataDevolucao.getDate().after(dataSaida.getDate())) {
                    emprestimo.insertEmprestimo(cgmAluno, idLivro, dataSaida.getDate(), dataDevolucao.getDate(), "Emprestimo");
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
