package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
public class Principal extends Padrao {

    private Container container;
    private JPanel panel;
    private JButton btnOpcoesAlunos;
    private JButton btnCadastroLivro;
    private JButton btnOpcoes;
    private JButton btnImprimir;
    private JButton btnCalendario;
    private Aluno aluno;
    private Editora editora;
    private Livro livro;
    private Emprestimo emprestimo;

    public Principal(Aluno aluno, Editora editora, Livro livro, Emprestimo emprestimo) {
        super();
        this.aluno = aluno;
        this.livro = livro;
        this.editora = editora;
        this.emprestimo = emprestimo;
        init();
    }

    public void init() {
        container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        JLabel labelImage = new JLabel(new ImageIcon("img/biblioteca.png"));

        btnOpcoesAlunos = new JButton("Opções Alunos");
        btnOpcoesAlunos.addActionListener(new Principal.OpcoesAlunoActionListener());

        btnCadastroLivro = new JButton("Cadastro Livro");
        btnCadastroLivro.addActionListener(new Principal.CadastroLivroActionListener());

        btnOpcoes = new JButton("Opções");
        btnOpcoes.addActionListener(new Principal.OpcoesActionListener());

        btnImprimir = new JButton("Imprimir");
        btnImprimir.addActionListener(new Principal.ImprimirActionListener());

        btnCalendario = new JButton("Calendario");
        btnCalendario.addActionListener(new Principal.CalendarioActionListener());

        panel = new JPanel();
        panel.add(btnOpcoesAlunos);
        panel.add(btnCadastroLivro);
        panel.add(btnOpcoes);
        panel.add(btnImprimir);
        panel.add(btnCalendario);
        panel.setLayout(new GridLayout(5, 1));

        container.add(panel, BorderLayout.EAST);
        container.add(labelImage, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private class OpcoesAlunoActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            new OpcoesAlunos(aluno, editora, livro, emprestimo);
        }
    }

    private class CadastroLivroActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            new OpcoesLivros(aluno, editora, livro, emprestimo);
        }
    }

    private class OpcoesActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            new OpcoesGeral(aluno, livro, editora, emprestimo);
        }
    }

    private class ImprimirActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("imprimir");
        }
    }

    private class CalendarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            new Calendario();
        }
    }
}
