package model;

import controler.Database;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Trabalho de .. Professor ..
 *
 * @author Alexandre
 * @version 1.0
 */
public class Publisher {

    private Integer codePublisher;
    private String name;
    private Database database;

    public Publisher(Database db) {
        this.database = db;
    }

    public Integer getCodePublisher() {
        return codePublisher;
    }

    public Database getDatabase() {
        return database;
    }

    public String getName() {
        return name;
    }

    public void setCodePublisher(Integer codePublisher) {
        this.codePublisher = codePublisher;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean insertPublisher(String name) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            if (selectCodePublisher(name) == 0) {
                this.database.stm.executeUpdate("INSERT INTO editoras(nome) VALUES ('" + name + "');");
                return true;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public int selectCodePublisher(String name) {
        try {
            String sql = "Select * FROM editoras WHERE nome = '" + name + "';";
            ResultSet rs = this.database.stm.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("ideditora");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public void updateFieldPublisher(String cod, String aux) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("UPDATE editoras set nome='" + aux + "' where ideditora = '" + cod + "';");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String selectCodePublisher(int cod) {
        try {
            String sql = "Select * FROM editoras WHERE ideditora = '" + cod + "';";
            ResultSet rs = this.database.stm.executeQuery(sql);

            return rs.getString("nome");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public ResultSet selectAll() {
        try {
            String sql = "SELECT * FROM editoras ORDER BY nome ASC";
            ResultSet rs = this.database.stm.executeQuery(sql);

            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
