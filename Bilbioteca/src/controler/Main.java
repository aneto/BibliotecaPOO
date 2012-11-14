package controler;

import java.sql.SQLException;
import model.Book;
import model.Lending;
import model.Publisher;
import model.Student;
import view.*;

/**
 * Trabalho de ..
 * Professor ..
 * @author Alexandre
 * @version 1.0
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database db = new Database();
	
        Student    student    = new Student(db);
        Publisher  publisher  = new Publisher(db);
        Book       book       = new Book(db, publisher);
        Lending    lending    = new Lending(db);
        
        OptionsStudent os = new OptionsStudent(student);
        OptionsBook ob = new OptionsBook(book, publisher);
        OptionsOverall oo = new OptionsOverall(student, book, lending);
        Calendar c = new Calendar();
        
        new MainGUI(os,ob,oo,c);
    }

}
