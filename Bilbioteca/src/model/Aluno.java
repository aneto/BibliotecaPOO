package model;

import controler.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Trabalho de .. Professor ..
 *
 * @author Alexandre
 * @version 1.0
 */
public class Aluno {

    private String CGM;
    private String nome;
    private String serie;
    private String filiacao;
    private String telefone;
    private String endereco;
    private Database database;

    public Aluno(Database db) {
        this.database = db;
    }
    
    public String getCGM() {
        return CGM;
    }

    public String getFiliacao() {
        return filiacao;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getSerie() {
        return serie;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setCGM(String CGM) {
        this.CGM = CGM;
    }

    public void setFiliacao(String filiacao) {
        this.filiacao = filiacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public ResultSet selectAll() {
        try {
            String sql = "SELECT * FROM alunos ORDER BY CGM ASC";
            ResultSet rs = this.database.stm.executeQuery(sql);
            
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Aluno.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Boolean selectCGM(String CGM) {
        try {
            String sql = "SELECT * FROM alunos where cgm ='"+CGM+"';";
            ResultSet rs = this.database.stm.executeQuery(sql);
            if(rs.next()) return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public void insertAluno(String CGM, String name, String serie, String filiacao, String endereco, String telefone){
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("INSERT INTO alunos VALUES (" + CGM + ", '" + name + "','"+ filiacao + "','"+ telefone + "','"+ serie + "','"+ endereco + "')");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Aluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateAluno(String CGM, String name, String serie, String filiacao, String endereco, String telefone){
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("UPDATE alunos set nome ='"+name+"', serie='"+serie+"', filiacao='"+filiacao+"',endereco='"+endereco+"', telefone='"+telefone+"' where CGM = '"+CGM+"';");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void updateCampo(String CGM, String aux, String tipo){
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("UPDATE alunos set "+tipo+" ='"+aux+"' where CGM = '"+CGM+"';");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Aluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAluno(String CGM){
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("DELETE FROM alunos where cgm ='" + CGM + "';");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
