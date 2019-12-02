package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Setor;

import logica.StuConecta;

public class SetorDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public Setor consultarSet(Setor pSet) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {			
			String SQL = "select distinct cd_setor, nm_descricao from tb_setor where cd_setor='"+pSet.getCodigo()+"'";
			if(pSet.getDescricao()!=null){
				SQL = "select distinct cd_setor, nm_descricao from tb_setor where upper(nm_descricao) like '%"+pSet.getDescricao().toUpperCase()+"%'";
			}
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				Setor objSet = new Setor();
				objSet.setCodigo(rs.getInt(1));
				objSet.setDescricao(rs.getString(2));
				return objSet;
			}else{
				return consultarSetLoc(pSet);
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		//return null;
	}

	public Setor consultarSetLoc(Setor pSet) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select cd_setor, nm_descricao from tb_setor where cd_setor="+pSet.getCodigo();
			if(pSet.getCodigo()==0){
				SQL = "select cd_setor, nm_descricao from tb_setor where upper(nm_descricao) like '%"+pSet.getDescricao().toUpperCase()+"%'";
			}
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				Setor objSet = new Setor();

				objSet.setCodigo(rs.getInt(1));
				objSet.setDescricao(rs.getString(2));
				return objSet;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return null;
	}

	public void incluirSet(Setor pSet) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "INSERT INTO TB_SETOR VALUES(?, ?)";
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, pSet.getCodigo());
			ps.setString(2, pSet.getDescricao());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarSet(Setor pSet) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_setor set cd_setor=?, nm_descricao=? where cd_setor="+pSet.getCodigo();
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, pSet.getCodigo());
			ps.setString(2, pSet.getDescricao());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void excluirSet(Setor pSet) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "delete from tb_setor where cd_setor="+pSet.getCodigo();
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
	
	public static ArrayList<Setor> listarSet(Setor pSet) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Setor> listTodas = null;
            Setor itemSet;

			String SQL = "select * from tb_setor order by nm_descricao";
			if(pSet.getDescricao()!=null){
				SQL = "select * from tb_setor where upper(nm_descricao) like '%"+pSet.getDescricao().toUpperCase()+"%' order by nm_descricao";
			}

			conn = StuConecta.getConnection( ); //this.conn; X static
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Setor>();
                do {
                    itemSet = new Setor();
                    itemSet.setCodigo(rs.getInt("cd_setor"));
                    itemSet.setDescricao(rs.getString("nm_descricao"));
                    listTodas.add(itemSet);
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