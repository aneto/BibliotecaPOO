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
public class Book {

    private String code;
    private String title;
    private String author;
    private String box;
    private String bookcase;
    private String area;
    private Publisher publisher;
    private Database database;

    public Book(Database db, Publisher ed) {
        this.database = db;
        this.publisher = ed;
    }

    public String getArea() {
        return area;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookcase() {
        return bookcase;
    }

    public String getBox() {
        return box;
    }

    public String getCode() {
        return code;
    }

    public Database getDatabase() {
        return database;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookcase(String bookcase) {
        this.bookcase = bookcase;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public Boolean selectCodeBook(String codeBook) {
        try {
            String sql = "SELECT * FROM livros where idlivro ='" + codeBook + "';";
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

    public int selectCodePublisher(String codeBook) {
        try {
            String sql = "SELECT * FROM livros where idlivro ='" + codeBook + "';";
            ResultSet rs;
            rs = this.database.stm.executeQuery(sql);
            return rs.getInt("editora_ideditora");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public void updateFieldBook(String code, String aux, String field) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("UPDATE livros set " + field + " ='" + aux + "' where idlivro = '" + code + "';");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteBook(String codeBook) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            this.database.stm.executeUpdate("DELETE FROM livros where idlivro ='" + codeBook + "';");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean insertBook(String code, String title, String author, String box, String publisher, String bookcase, String area) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.database.stm = this.database.conn.createStatement();
            int cod = this.publisher.selectCodePublisher(publisher);
            System.out.println(cod);
            if (cod == 0) {
                this.database.stm.executeUpdate("INSERT INTO editoras(nome) VALUES ('" + publisher + "');");
                cod = this.publisher.selectCodePublisher(publisher);
            }
            this.database.stm.executeUpdate("INSERT INTO livros VALUES ('" + code + "','" + cod + "','" + author + "','" + title + "','" + box + "','" + bookcase + "','" + area + "');");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public ResultSet selectAllBooks() {
        try {
            String sql = "SELECT l.*, e.nome AS editora FROM livros l, editoras e WHERE e.ideditora = l.editora_ideditora ORDER BY idlivro ASC";
            ResultSet rs = this.database.stm.executeQuery(sql);

            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public ResultSet selectBooks(String idLivro) {
        try {
            String sql = "SELECT l.*, e.nome AS editora FROM livros l, editoras e WHERE e.ideditora = l.editora_ideditora AND l.idlivro = '"+idLivro+"';";
            ResultSet rs = this.database.stm.executeQuery(sql);

            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
