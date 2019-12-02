package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.TipoEvento;

import logica.StuConecta;

public class TipoEventoDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static TipoEvento consultarTipoEvento(TipoEvento pTEve) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select * from tb_tipo_evento";
			if(pTEve.getDescricao()!=null){
				SQL = "select * from tb_tipo_evento where upper(nm_descricao) like '%"+pTEve.getDescricao().toUpperCase()+"%'"; 
			}
			if(pTEve.getCodigo()!=0){
				SQL = "select * from tb_tipo_evento where cd_tipo_evento="+pTEve.getCodigo(); 
			}

			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				TipoEvento objTipEve = new TipoEvento();
				objTipEve.setCodigo(rs.getInt(1));
				objTipEve.setDescricao(rs.getString(2));
				return objTipEve;
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

	public void incluirTipoEvento(TipoEvento pCar) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("insert into tb_tipo_evento values(cd_tipo_evento,?)");
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pCar.getDescricao());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarTipoEvento(TipoEvento pTEve) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("update tb_tipo_evento set nm_descricao=? where cd_tipo_evento="+Integer.toString(pTEve.getCodigo()));
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pTEve.getDescricao());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirTipoEvento(TipoEvento pTEve) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_tipo_evento where cd_tipo_evento="+Integer.toString(pTEve.getCodigo()));
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
	
	public static ArrayList<TipoEvento> listarTipoEvento() throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<TipoEvento> listTodas = null;
            TipoEvento itemTipEve;

			String SQL = ("select * from tb_tipo_evento order by nm_descricao");
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<TipoEvento>();
                do {
                    itemTipEve = new TipoEvento();
                    itemTipEve.setCodigo(rs.getInt("cd_tipo_evento"));
                    itemTipEve.setDescricao(rs.getString("nm_descricao"));
                    
                    listTodas.add(itemTipEve);
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
