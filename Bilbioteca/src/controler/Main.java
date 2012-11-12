package controler;

import java.sql.SQLException;
import model.*;
import view.MainGUI;

/**
 * Trabalho de .. Professor ..
 *
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

        new MainGUI(student, publisher, book, lending);
    }
}
