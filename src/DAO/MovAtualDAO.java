package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.StuConecta;
import beans.MovAtual;

public class MovAtualDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static MovAtual consultarMovAtual(MovAtual pMov) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_mov_atual"; //cd_nucleo acrescentado no último select
			
			if(pMov.getFechado()!=null){
				SQL = "select * from tb_mov_atual where fl_fechado='"+pMov.getFechado()+"'"; 
			}
			
			if(pMov.getAno()!=0){
				SQL = "select * from tb_mov_atual where vl_ano="+pMov.getAno()+" and fl_fechado='"+pMov.getFechado()+"'"; 
			}
			
			if(pMov.getMes()!=0&&pMov.getAno()!=0){
				SQL = "select * from tb_mov_atual where vl_mes="+pMov.getMes()+" and vl_ano="+pMov.getAno(); 
			}
			SQL = SQL+" and cd_nucleo="+pMov.getNucleo().getCodigo();

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			MovAtual objMov = null;
			if(rs.next()){
				objMov = new MovAtual();
				
				objMov.setAno(rs.getInt(1));
				
				NucleoDAO objNucleoDAO = new NucleoDAO();				
				objMov.setNucleo(objNucleoDAO.consultarNucleo(rs.getInt(2)));

				objMov.setMes(rs.getInt(3));
				objMov.setFechado(rs.getString(4));
			}
			
			return objMov;
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public void incluirMovAtual(MovAtual pMov) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "insert into tb_mov_atual values(?, ?, ?, ?)";
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, pMov.getAno());
			ps.setInt(2, pMov.getNucleo().getCodigo());
			ps.setInt(3, pMov.getMes());
			ps.setString(4, pMov.getFechado());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarMovAtual(MovAtual pMov) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_mov_atual set fl_fechado=? where vl_ano="+pMov.getAno()+" and vl_mes="+pMov.getMes()+" and cd_nucleo="+pMov.getNucleo().getCodigo();
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pMov.getFechado());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirMovAtual(MovAtual pMov) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "delete from tb_mov_atual where where vl_ano="+pMov.getAno()+" and vl_mes="+pMov.getMes()+" and cd_nucleo="+pMov.getNucleo().getCodigo();
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
	
	public static ArrayList<MovAtual> listarMovAtual(MovAtual pMov) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<MovAtual> listTodas = null;
            MovAtual movAtual;

			String SQL = "select * from tb_mov_atual where fl_fechado <> 'N' and cd_nucleo="+pMov.getNucleo().getCodigo()+" order by vl_ano,vl_mes";
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<MovAtual>();
                do {
                    movAtual = new MovAtual();
                    movAtual.setAno(rs.getInt("vl_ano"));
                    movAtual.setMes(rs.getInt("vl_mes"));
                    movAtual.setFechado(rs.getString("fl_fechado"));
                    
                    listTodas.add(movAtual);
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
