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
public class Lending {

    private String codeBook;
    private String codeStudent;
    private Date dateReturn;
    private Date dateDeparture;
    private String status;
    private Database database;

    public Lending(Database db) {
        this.database = db;
    }

    public String getCodeBook() {
        return codeBook;
    }

    public String getCodeStudent() {
        return codeStudent;
    }

    public Database getDatabase() {
        return database;
    }

    public Date getDateDeparture() {
        return dateDeparture;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public String getStatus() {
        return status;
    }

    public void setCodeBook(String codeBook) {
        this.codeBook = codeBook;
    }

    public void setCodeStudent(String codeStudent) {
        this.codeStudent = codeStudent;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setDateDeparture(Date dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void insertLending(String codeStudent, String title, Date dateDeparture, Date dateReturn, String status) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("INSERT INTO emprestimos(livro_idlivro, Alunos_CGM,dataSaida,dataDevolucao, status) VALUES (" + title + ", '" + codeStudent + "','" + dateDeparture + "','" + dateReturn + "','" + status + "')");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public ResultSet selectViewLending(String codeStudent) {
        try {
            String sql = "SELECT * FROM viewEmprestimos WHERE Alunos_CGM ='"+codeStudent+"'";
            ResultSet rs = this.database.stm.executeQuery(sql);

            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public void deleteLending(String codeStudent, Date dateDeparture, Date dateReturn){
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("DELETE FROM emprestimos where Alunos_CGM ='" + codeStudent + "' AND dataSaida ='" + dateDeparture + "' AND dataDevolucao ='" + dateReturn + "' AND status = 'Emprestimo';");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void deleteLending2(String idLending){
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("DELETE FROM emprestimos where idemprestimo ='" + idLending + "';");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
