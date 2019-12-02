package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Login;

import logica.StuConecta;


public class StuDAO { //implements InterfaceDAO{

	String MensErr = "Erro de acesso aos dados "; 

	public Login buscaLogin(String pCpf, String pSenha) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = ("select * from tb_login where cpf="+pCpf);
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				Login usuario = new Login();
				usuario.setSenha(rs.getString(2));
				
				if (usuario.getSenha().equals(pSenha)){
					return usuario;
				}
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return null;
	}
}
