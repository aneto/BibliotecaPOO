package controler;

import java.sql.SQLException;
import model.*;
import view.Principal;

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

        Aluno aluno           = new Aluno(db);
        Editora editora       = new Editora(db);
        Livro livro           = new Livro(db, editora);
        Emprestimo emprestimo = new Emprestimo(db);

        new Principal(aluno, editora, livro, emprestimo);
    }
}
