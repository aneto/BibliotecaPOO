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
public class Editora {

    private Integer id;
    private String nome;
    private Database database;

    public Editora(Database db) {
        this.database = db;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean insertEditora(String nome) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            if (selectEditora(nome) == 0) {
                this.database.stm.executeUpdate("INSERT INTO editoras(nome) VALUES ('" + nome + "');");
                return true;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public int selectEditora(String nome) {
        try {
            String sql = "Select * FROM editoras WHERE nome = '" + nome + "';";
            ResultSet rs = this.database.stm.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("ideditora");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public void updateCampo(String cod, String aux) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("UPDATE editoras set nome='" + aux + "' where ideditora = '" + cod + "';");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String selectEditoraCod(int cod) {
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
