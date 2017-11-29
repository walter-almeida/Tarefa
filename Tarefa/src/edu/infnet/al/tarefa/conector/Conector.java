package edu.infnet.al.tarefa.conector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import edu.infnet.al.tarefa.exception.DAOException;

public class Conector {
	private static Connection connection;
    
    private static String dsn = "jdbc:mysql://localhost:3306/agendaapp";
    private static String username = "root";
    private static String password = "root";
    
    public static Connection getConnection() throws DAOException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(dsn, username, password);
            } catch (SQLException ex) {
                throw new DAOException("Erro ao abrir conexao", ex);
            }
        }
        
        return connection;
}
}
