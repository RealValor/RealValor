package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Banco;
import beans.Nucleo;
import beans.SaldoBanco;

public class SaldoBancoDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static SaldoBanco consultarSaldoBanco(SaldoBanco pSal) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select * from tb_saldo_banco where cd_nucleo="+pSal.getNucleo().getCodigo();

			if(pSal.getAno()!=0&&pSal.getMes()!=0){
				SQL = "select * from tb_saldo_banco where vl_anomov="+pSal.getAno()+" and vl_mesmov="+pSal.getMes()+" and cd_nucleo="+pSal.getNucleo().getCodigo(); 
			}

			if(pSal.getBanco()!=null&&pSal.getAno()!=0&&pSal.getMes()!=0){
				SQL = "select * from tb_saldo_banco where cd_banco="+pSal.getBanco().getCodigo()
						+" and vl_anomov="+pSal.getAno()+" and vl_mesmov="+pSal.getMes()+" and cd_nucleo="+pSal.getNucleo().getCodigo(); 
			}

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				SaldoBanco objSaldo = new SaldoBanco();
				Banco objBanco = new Banco();
				
				objBanco.setCodigo(rs.getInt(1));				
				objSaldo.setBanco(objBanco);
				
				Nucleo objNucleo = new Nucleo();
				objNucleo.setCodigo(rs.getInt(2));
		
				objSaldo.setNucleo(objNucleo);
				
				objSaldo.setAno(rs.getInt(3));
				objSaldo.setMes(rs.getInt(4));
				objSaldo.setValor(rs.getDouble(5));
				
				return objSaldo;
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

	public void incluirSaldoBanco(SaldoBanco pSal) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "insert into tb_saldo_banco values(?, ?, ?, ?, ?)";
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			
			ps.setInt(1, pSal.getBanco().getCodigo());
			ps.setInt(2, pSal.getNucleo().getCodigo());
			ps.setInt(3, pSal.getAno());
			ps.setInt(4, pSal.getMes());
			ps.setDouble(5, pSal.getValor());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarSaldoBanco(SaldoBanco pSal) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_saldo_banco set vl_saldo=? where cd_nucleo="+pSal.getNucleo().getCodigo()+" and cd_banco="+pSal.getBanco().getCodigo()
			+" and vl_anomov="+pSal.getAno()+" and vl_mesmov="+pSal.getMes();

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setDouble(1, pSal.getValor());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirSaldoBanco(SaldoBanco pSal) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "delete from tb_saldo_banco where cd_nucleo="+pSal.getNucleo().getCodigo()+" and cd_banco="+pSal.getBanco().getCodigo()
			+" and vl_anomov="+pSal.getAno()+" and vl_mesmov="+pSal.getMes();

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
	
	public static ArrayList<SaldoBanco> listarSaldoBanco(SaldoBanco pSal) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<SaldoBanco> listTodas = null;
            SaldoBanco objSaldo;

			String SQL = "select * from tb_saldo_banco where cd_nucleo="+pSal.getNucleo().getCodigo();
	
			if(pSal.getAno()!=0){
				SQL = "select * from tb_saldo_banco where vl_anomov="+pSal.getAno()+" and cd_nucleo="+pSal.getNucleo().getCodigo(); 
			}
			
			if(pSal.getAno()!=0&&pSal.getMes()!=0){
				SQL = "select * from tb_saldo_banco where vl_anomov="+pSal.getAno()+" and vl_mesmov="+pSal.getMes()+" and cd_nucleo="+pSal.getNucleo().getCodigo(); 
			}
			
			SQL = SQL+" order by vl_anomov desc,vl_mesmov desc,cd_banco";

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<SaldoBanco>();
                do {
    				objSaldo = new SaldoBanco();
    				Banco objBanco = new Banco();
    				
    				objBanco.setCodigo(rs.getInt(1));
    				objBanco = BancoDAO.consultarBanco(objBanco);
    				
    				objSaldo.setBanco(objBanco);

    				Nucleo objNucleo = new Nucleo();
    				objNucleo.setCodigo(rs.getInt(2));
    				
    				objSaldo.setNucleo(objNucleo);

    				objSaldo.setAno(rs.getInt(3));
    				objSaldo.setMes(rs.getInt(4));
    				objSaldo.setValor(rs.getDouble(5));

                    listTodas.add(objSaldo);
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
	
	public static ArrayList<SaldoBanco> listar_SaldoBancoPeriodo(SaldoBanco pSalInicial,SaldoBanco pSalFinal) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<SaldoBanco> listTodas = null;
            SaldoBanco objSaldo;

			String SQL = "select * from tb_saldo_banco order by vl_anomov desc,vl_mesmov desc,cd_banco";
	
			if(pSalInicial!=null&&pSalInicial.getAno()!=0&&pSalInicial.getMes()!=0&&pSalFinal.getAno()!=0&&pSalFinal.getMes()!=0){
				SQL = "select max(cd_banco),max(vl_anomov),max(vl_mesmov),sum(vl_saldo) from tb_saldo_banco where vl_anomov="+pSalInicial.getAno()+" and vl_mesmov between "+pSalInicial.getMes()
				+" and "+pSalFinal.getMes(); 
			}

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<SaldoBanco>();
                do {
    				objSaldo = new SaldoBanco();
    				Banco objBanco = new Banco();
    				
    				objBanco.setCodigo(rs.getInt(1));
    				objBanco = BancoDAO.consultarBanco(objBanco);
    				
    				objSaldo.setBanco(objBanco);
    		
    				objSaldo.setAno(rs.getInt(2));
    				objSaldo.setMes(rs.getInt(3));
    				objSaldo.setValor(rs.getDouble(4));

                    listTodas.add(objSaldo);
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

	public ArrayList<SaldoBanco> consultarSaldoBancoAnual(int pAno, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<SaldoBanco> listTodas = null;
            
            SaldoBanco objSaldo;
            
			String SQL = "select vl_mesmov,sum(vl_saldo) from tb_saldo_banco where vl_anomov="+pAno+" and cd_nucleo="+pNucleo.getCodigo()
				+" group by vl_mesmov order by vl_mesmov";
            
            //System.out.println(SQL);
            
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<SaldoBanco>();
                do {
                	objSaldo = new SaldoBanco();
    				
    				objSaldo.setMes(rs.getInt(1));
    				objSaldo.setValor(rs.getDouble(2));

                    listTodas.add(objSaldo);
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
