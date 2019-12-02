package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Estado;


public class EstadoDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static Estado consultarEstado(Estado pEstado) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			Estado objEstado;

			String SQL = "select * from tb_estado";
			
			if(pEstado!=null){
				SQL = "select * from tb_estado where cd_estado="+pEstado.getCodigo();
			}

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if(rs.next()){
				objEstado = new Estado();
				
				objEstado.setCodigo(rs.getInt(1));
				objEstado.setNome(rs.getString(2));
				objEstado.setUf(rs.getString(3));
				objEstado.setUf(rs.getString(4));
				
				return objEstado;
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

	public static ArrayList<Estado> listarEstado() throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Estado> listTodas = null;
            Estado itemEstado;

			String SQL = ("select * from tb_estado where nm_nome_Estado <> '' order by nm_nome_Estado");
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Estado>();
                do {
                    itemEstado = new Estado();
                    itemEstado.setCodigo(rs.getInt("cd_Estado"));
                    itemEstado.setNome(rs.getString("nm_nome_Estado"));
                    itemEstado.setUf(rs.getString("nm_uf_estado"));
                    itemEstado.setRegiaoUF(rs.getString("nm_regiao"));
                    
                    listTodas.add(itemEstado);
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

	/*
	public void incluirEstado(Estado pBan) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("insert into tb_Estado values(cd_Estado,?, ?)");
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pBan.getDescricao());
			ps.setString(2, pBan.getSigla());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarEstado(Estado pBan) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("update tb_Estado set nm_descricao=?, nm_sigla=? where cd_Estado="+Integer.toString(pBan.getCodigo()));
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pBan.getDescricao());
			ps.setString(2, pBan.getSigla());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirEstado(Estado pBan) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_Estado where cd_Estado="+Integer.toString(pBan.getCodigo()));
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
	
	public static ArrayList<Estado> listarEstado() throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Estado> listTodas = null;
            Estado itemEstado;

			String SQL = ("select * from tb_Estado where nm_descricao <> '' order by nm_descricao");
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Estado>();
                do {
                    itemEstado = new Estado();
                    itemEstado.setCodigo(rs.getInt("cd_Estado"));
                    itemEstado.setDescricao(rs.getString("nm_descricao"));
                    itemEstado.setSigla(rs.getString("nm_sigla"));
                    
                    listTodas.add(itemEstado);
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
	*/
}
