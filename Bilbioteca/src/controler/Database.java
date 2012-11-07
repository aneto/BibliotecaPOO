package controler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Trabalho de .. Professor ..
 *
 * @author Alexandre
 * @version 1.0
 */
public class Database {
    //Conexão do banco de dados
    public Connection conn;
    
    //Estado do banco de dados
    public Statement stm;

    /**
     * O construtor cria uma nova conexão com o banco de dados sqlite contido no
     * arquivo passado como parâmetro. A conexão é possibilitada pelo driver
     * JDBC, fornecido por SQLiteJDBC.
     */
    public Database() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:databaseFile.db");
        this.stm = this.conn.createStatement();
        initDB();
    }

    /**
     * Cria as Tabelas da Biblioteca, caso não existão.
     */
    public void initDB() throws SQLException {
        //this.stm.executeUpdate("Drop table alunos");
        //this.stm.executeUpdate("Drop table editoras");
        this.stm.executeUpdate("CREATE TABLE IF NOT EXISTS alunos (CGM VARCHAR(40) NOT NULL , nome VARCHAR(60) NOT NULL , filiacao VARCHAR(60) NOT NULL , telefone VARCHAR(20) NULL, serie VARCHAR(45) NULL,endereco varchar(100) null, PRIMARY KEY (CGM));");
        //this.stm.executeUpdate("CREATE TABLE IF NOT EXISTS endereco (idAluno INT NOT NULL ,nomeRua VARCHAR(60) NULL ,bairro VARCHAR(60) NULL ,numero INT NULL ,complemento VARCHAR(45) NULL ,PRIMARY KEY (idAluno) ,CONSTRAINT fk_Endereco_Alunos FOREIGN KEY (idAluno ) REFERENCES alunos (CGM));");
        this.stm.executeUpdate("CREATE TABLE IF NOT EXISTS editoras (ideditora integer PRIMARY KEY AUTOINCREMENT, nome VARCHAR(45) NOT NULL);");
        this.stm.executeUpdate("CREATE TABLE IF NOT EXISTS livros (idlivro VARCHAR(45) NOT NULL ,editora_ideditora INT NOT NULL ,autor VARCHAR(45) NOT NULL ,titulo VARCHAR(45) NOT NULL ,box VARCHAR(45) NOT NULL ,estante VARCHAR(45) NOT NULL ,area VARCHAR(45) NOT NULL ,PRIMARY KEY (idlivro), CONSTRAINT fk_livro_editora1 FOREIGN KEY (editora_ideditora ) REFERENCES editoras (ideditora ));");
        this.stm.executeUpdate("CREATE TABLE IF NOT EXISTS emprestimos (livro_idlivro VARCHAR(45) NOT NULL ,Alunos_CGM VARCHAR(40) NOT NULL ,dataSaida TIMESTAMP NULL ,dataDevolucao DATETIME NULL ,status VARCHAR(45) NULL ,PRIMARY KEY (livro_idlivro, Alunos_CGM) ,CONSTRAINT fk_livro_has_Alunos_livro1 FOREIGN KEY (livro_idlivro ) REFERENCES livros (idlivro ), CONSTRAINT fk_livro_has_Alunos_Alunos1 FOREIGN KEY (Alunos_CGM ) REFERENCES alunos (CGM ))");
    }
}
