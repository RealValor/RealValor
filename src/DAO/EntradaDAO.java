package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Nucleo;

import logica.StuConecta;

public class EntradaDAO {

	String MensErr = "Erro de acesso aos dados "; 

	public void excluirItensEntrada(int pRec, int pAno) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		
		try {
			String SQL = "delete from tb_itens_entrada where cd_recibo="+pRec+" and year(dt_data)="+pAno;
			
			//System.out.println(SQL);
			
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

	public void excluirEntrada(int pNucleo, int pRecibo, int pAno) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;

		try {

			String SQL = "delete from tb_entrada where cd_nucleo ="+pNucleo+" and cd_recibo="+pRecibo+" and year(dt_data)="+pAno;
			
			//System.out.println(SQL);
			
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
	
	public boolean checaReciboEmMovimentoAberto(int pNucleo, int pRecibo, int pAno) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			String SQL = "select * from tb_entrada where cd_nucleo="+pNucleo+" and cd_recibo="+pRecibo+" and year(dt_data)="+pAno+" and fl_fechado<>'S'";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}else {
				return false;				
			}	
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}
	
	public static int buscaUltimoMesMovimento(int pAno, Nucleo pNucleo) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {

			int anoAnterior = pAno-1;
			String SQL = "select month(dt_data) from tb_itens_entrada where year(dt_data) in ("+anoAnterior+","+pAno+") and cd_nucleo="+pNucleo.getCodigo()
					+" order by vl_mes desc limit 1";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);

			}else {
				return 0;				
			}	
		}
		catch (SQLException sqle){
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}	

	public static boolean VerificaEntradaHistorico(int pFornecedor, Nucleo pNucleo) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {

			String SQL = "select *from tb_itens_entrada where cd_devedor="+pFornecedor+" and fl_socio='N'";

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}else {
				return false;				
			}	
		}
		catch (SQLException sqle){
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		
	}	
}
