package model;

import controler.Database;
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
public class Student {

    private String codeStudent;
    private String name;
    private String serie;
    private String filiation;
    private String phone;
    private String address;
    private Database database;

    public Student(Database db) {
        this.database = db;
    }

    public String getAddress() {
        return address;
    }

    public String getCodeStudent() {
        return codeStudent;
    }

    public String getFiliation() {
        return filiation;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSerie() {
        return serie;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCodeStudent(String codeStudent) {
        this.codeStudent = codeStudent;
    }

    public void setFiliation(String filiation) {
        this.filiation = filiation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public ResultSet selectAll() {
        try {
            String sql = "SELECT * FROM alunos ORDER BY CGM ASC";
            ResultSet rs = this.database.stm.executeQuery(sql);
            
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Boolean selectCodeStudent(String codeStudent) {
        try {
            String sql = "SELECT * FROM alunos where cgm ='"+codeStudent+"';";
            ResultSet rs = this.database.stm.executeQuery(sql);
            if(rs.next()) return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public ResultSet selectStudent(String codeStudent) {
        try {
            String sql = "SELECT * FROM alunos where cgm ='"+codeStudent+"';";
            ResultSet rs = this.database.stm.executeQuery(sql);
            
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public void insertStudent(String codeStudent, String name, String serie, String filiation, String address, String phone){
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("INSERT INTO alunos VALUES (" + codeStudent + ", '" + name + "','"+ filiation + "','"+ phone + "','"+ serie + "','"+ address + "')");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateStudent(String codeStudent, String name, String serie, String filiation, String address, String phone){
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("UPDATE alunos set nome ='"+name+"', serie='"+serie+"', filiacao='"+filiation+"',endereco='"+address+"', telefone='"+phone+"' where CGM = '"+codeStudent+"';");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void updateFieldStudent(String codeStudent, String aux, String field){
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("UPDATE alunos set "+field+" ='"+aux+"' where CGM = '"+codeStudent+"';");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteStudent(String codeStudent){
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("DELETE FROM alunos where cgm ='" + codeStudent + "';");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
