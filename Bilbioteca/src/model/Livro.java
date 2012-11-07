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
public class Livro {

    private String codigo;
    private String titulo;
    private String autor;
    private String box;
    private String estante;
    private String area;
    private Editora editora;
    private Database database;

    public Livro(Database db, Editora ed) {
        this.database = db;
        this.editora = ed;
    }

    public String getArea() {
        return area;
    }

    public String getAutor() {
        return autor;
    }

    public String getBox() {
        return box;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getEstante() {
        return estante;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Boolean selectCod(String cod) {
        try {
            String sql = "SELECT * FROM livros where idlivro ='" + cod + "';";
            ResultSet rs;
            rs = this.database.stm.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public int selectEditoraCod(String codLivro) {
        try {
            String sql = "SELECT * FROM livros where idlivro ='" + codLivro + "';";
            ResultSet rs;
            rs = this.database.stm.executeQuery(sql);
            return rs.getInt("editora_ideditora");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public void updateCampo(String cod, String aux, String tipo) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("UPDATE livros set " + tipo + " ='" + aux + "' where idlivro = '" + cod + "';");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteLivro(String cod) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("DELETE FROM livros where idlivro ='" + cod + "';");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean insertLivro(String codigo, String titulo, String autor, String box, String editora, String estante, String area) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            int cod = this.editora.selectEditora(editora);
            System.out.println(cod);
            if (cod == 0) {
                this.database.stm.executeUpdate("INSERT INTO editoras(nome) VALUES ('" + editora + "');");
                cod = this.editora.selectEditora(editora);
            }
            this.database.stm.executeUpdate("INSERT INTO livros VALUES ('" + codigo + "','" + cod + "','" + autor + "','" + titulo + "','" + box + "','" + estante + "','" + area + "');");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public ResultSet selectAll() {
        try {
            String sql = "SELECT l.*, e.nome AS editora FROM livros l, editoras e WHERE e.ideditora = l.editora_ideditora ORDER BY idlivro ASC";
            ResultSet rs = this.database.stm.executeQuery(sql);

            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
