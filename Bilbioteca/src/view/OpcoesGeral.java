package view;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
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
    private JComboBox jtAluno;
    private JComboBox jtLivro;
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
        JLabel lbCGM = new JLabel(" CGM:                      ");
        jtAluno = new JComboBox(preencheComboBoxAluno());

        JLabel lbLivro = new JLabel(" Livro:                      ");
        jtLivro = new JComboBox(preencheComboBoxLivro());

        JLabel lbDataSaida = new JLabel(" Data Saida:           ");
        dataSaida = new JDateChooser(dtAtual.getTime());

        JLabel lbDataDevolucao = new JLabel(" Data Devolucao:  ");
        dtAtual.add(Calendar.DAY_OF_MONTH, 10);
        dataDevolucao = new JDateChooser(dtAtual.getTime());

        /**
         * dataSaida.getDateEditor().addPropertyChangeListener(new
         * PropertyChangeListener() {
         *
         * @Override public void propertyChange(PropertyChangeEvent evt) {
         * Calendar dtAux = Calendar.getInstance();
         * dtAux.setTime(dataSaida.getDate()); dtAux.add(Calendar.DAY_OF_MONTH,
         * 10); Date dataAux = dtAtual.getTime();
         * dataDevolucao.setDate(dataAux); } });
         *
         */
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
        tfBuscar = new JTextField(30);

        btnBuscar = new JButton("OK");
        btnBuscar.addActionListener(new OpcoesGeral.BtnBuscarActionListener());

        panelBusca = new JPanel();
        panelBusca.add(new JLabel("Busca: "));
        panelBusca.add(tfBuscar);
        panelBusca.add(btnBuscar);

        tabela = new JTable(modelo);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        modelo.addColumn("CGM");
        modelo.addColumn("Nome");
        modelo.addColumn("Cod Livro");
        modelo.addColumn("Data Saida");
        modelo.addColumn("Data Devolução");
        modelo.addColumn("Status");
        
        preencheTabela();
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(100);
        
        panel.add(panelBusca, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private void preencheTabela() {
        try {
            ResultSet rs = emprestimo.selectAll();
            while (rs.next()) {
                modelo.addRow(new String[]{
                            rs.getString("Alunos_CGM"),
                            rs.getString("nome"),
                            rs.getString("livro_idlivro"),
                            rs.getString("dataSaida"),
                            rs.getString("dataDevolucao"),
                            rs.getString("status")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(OpcoesAlunos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class BtnBuscarActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String nome = tfBuscar.getText();

            //if (busca.getNome(nome)) {

            //} else {
            JOptionPane.showMessageDialog(frame, "Livro não encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            //}
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
                } else {
                    JOptionPane.showMessageDialog(frame, "Data de Devolução deve ser DEPOIS da Data de Saída!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Não Pode ter campos em Branco ou Não Selecionados!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
