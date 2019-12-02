package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Bairro;


public class BairroDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static Bairro consultarBairro(Bairro pBairro) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			
			Bairro objBairro;

			String SQL = "select * from tb_bairro";
			
			if(pBairro!=null){
				if(pBairro.getNome()!=null){
					SQL = "select * from tb_bairro where upper(nm_nome_bairro) like '%"+pBairro.getNome().toUpperCase()+"%'";
				}
				if(pBairro.getCodigo()!=0){
					SQL = "select * from tb_bairro where cd_bairro="+pBairro.getCodigo();
				}
			}

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if(rs.next()){
				objBairro = new Bairro();
				
				objBairro.setCodigo(rs.getInt(1));
				objBairro.setNome(rs.getString(2));
				
				return objBairro;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return null;
	}

	public static ArrayList<Bairro> listarBairro() throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Bairro> listTodas = null;
            Bairro itemBairro;

			String SQL = ("select * from tb_bairro where nm_nome_bairro <> '' order by nm_nome_bairro");
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Bairro>();
                do {
                    itemBairro = new Bairro();
                    itemBairro.setCodigo(rs.getInt("cd_Bairro"));
                    itemBairro.setNome(rs.getString("nm_nome_bairro"));
                    
                    listTodas.add(itemBairro);
                } while (rs.next());
            }
            return listTodas;
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}
	
	public void incluirBairro(Bairro pBairro) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("insert into tb_bairro values(?,?)");
			conn = StuConecta.getConnection();

			ps = conn.prepareStatement(SQL);
			ps.setInt(1, pBairro.getCodigo());
			ps.setString(2, pBairro.getNome());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void alterarBairro(Bairro pBairro) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			String SQL = "update tb_bairro set nm_nome_bairro=? where cd_Bairro="+pBairro.getCodigo();
			conn = StuConecta.getConnection();
			
			//System.out.println(SQL);
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pBairro.getNome());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	/*
	
	public void excluirBairro(Bairro pBairro) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_Bairro where cd_Bairro="+Integer.toString(pBairro.getCodigo()));
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	*/
}
