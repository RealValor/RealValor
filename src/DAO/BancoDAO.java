package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Banco;

import logica.StuConecta;


public class BancoDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static Banco consultarBanco(Banco pBan) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select * from tb_banco";
			if(pBan.getSigla()!=null){
				SQL = SQL+" where upper(nm_sigla) like '%"+pBan.getSigla().toUpperCase()+"%'";
			}
			if(pBan.getDescricao()!=null){
				SQL = "select * from tb_banco where upper(nm_descricao) like '%"+pBan.getDescricao().toUpperCase()+"%'"; 
			}
			if(pBan.getCodigo()!=0){
				SQL = "select * from tb_banco where cd_banco="+pBan.getCodigo(); 
			}

			//System.out.println("SQL: "+SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				Banco objBan = new Banco();
				objBan.setCodigo(rs.getInt(1));
				objBan.setDescricao(rs.getString(2));
				objBan.setSigla(rs.getString(3));
				return objBan;
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

	public void incluirBanco(Banco pBan) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("insert into tb_banco values(cd_banco,?, ?)");
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
	
	public void alterarBanco(Banco pBan) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("update tb_banco set nm_descricao=?, nm_sigla=? where cd_banco="+Integer.toString(pBan.getCodigo()));
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
	
	public void excluirBanco(Banco pBan) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_banco where cd_banco="+Integer.toString(pBan.getCodigo()));
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
	
	public static ArrayList<Banco> listarBanco() throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Banco> listTodas = null;
            Banco itemBan;

			String SQL = ("select * from tb_banco where nm_descricao <> '' order by nm_descricao");
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Banco>();
                do {
                    itemBan = new Banco();
                    itemBan.setCodigo(rs.getInt("cd_banco"));
                    itemBan.setDescricao(rs.getString("nm_descricao"));
                    itemBan.setSigla(rs.getString("nm_sigla"));
                    
                    listTodas.add(itemBan);
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
}
