package model;

import controler.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Trabalho de .. Professor ..
 *
 * @author Alexandre
 * @version 1.0
 */
public class Emprestimo {

    private String idLivro;
    private String idAluno;
    private Date dataDevolucao;
    private Date dataSaida;
    private String status;
    private Database database;

    public Emprestimo(Database db) {
        this.database = db;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public String getStatus() {
        return status;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public void setIdLivro(String idLivro) {
        this.idLivro = idLivro;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void insertEmprestimo(String CGM, String titulo, Date dataSaida, Date dataDevolucao, String status) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("INSERT INTO emprestimos VALUES (" + titulo + ", '" + CGM + "','" + dataSaida + "','" + dataDevolucao + "','" + status + "')");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public ResultSet selectAll() {
        try {
            String sql = "SELECT e.*, a.nome AS nome FROM emprestimos e, alunos a WHERE e.Alunos_CGM = a.CGM ORDER BY a.CGM ASC";
            ResultSet rs = this.database.stm.executeQuery(sql);

            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
