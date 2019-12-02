package logica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import DAO.StuDAOException;

public class StuConecta {
	public static Connection getConnection() throws StuDAOException{
  		try{
  			/*
    		*/
  			InitialContext contexto = new InitialContext();
  			DataSource ds = (DataSource)contexto.lookup("java:comp/env/jdbc/udvnmg_banco");
  			return ds.getConnection();
  			//Conexão em banco MySQL - hospedagem.

  			/*
  			Class.forName("com.mysql.jdbc.Driver");
  			return DriverManager.getConnection("jdbc:mysql://localhost:3306/udvnmg_banco","udvnmg_usuario","57udvnm6");
  			//Conexão local em banco MySQL - udvnmg_banco.
  			*/

  		}catch (Exception e){
  			throw new StuDAOException("Erro: StuConecta--> "+e.getMessage( ));
  		}
	}

	public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) throws StuDAOException{
		close(conn, stmt, rs);
	}

	public static void closeConnection(Connection conn, Statement stmt)	throws StuDAOException{
		close(conn, stmt, null);
	}

	public static void closeConnection(Connection conn)	throws StuDAOException{
		close(conn, null, null);
	}

	private static void close(Connection conn, Statement stmt, ResultSet rs) throws StuDAOException{
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			};
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			};
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			};
	}
	
}   