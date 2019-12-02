package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.SaldoCaixa;

import logica.StuConecta;

public class SaldoCaixaDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static SaldoCaixa consultarSaldoCaixa(SaldoCaixa pCaixa) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select * from tb_historico_caixa and cd_nucleo="+pCaixa.getNucleo().getCodigo();

			if(pCaixa.getAno()!=0&&pCaixa.getMes()!=0){
				SQL = "select * from tb_historico_caixa where vl_anomov="+pCaixa.getAno()+" and vl_mesmov="+pCaixa.getMes()+" and cd_nucleo="+pCaixa.getNucleo().getCodigo(); 
			}

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				SaldoCaixa objSaldo = new SaldoCaixa();
				
				objSaldo.setAno(rs.getInt(1));
				
				NucleoDAO objNucleoDAO = new NucleoDAO();
				objSaldo.setNucleo(objNucleoDAO.consultarNucleo(rs.getInt(2)));

				objSaldo.setMes(rs.getInt(3));
				objSaldo.setSaldoAnteriorCaixa(rs.getDouble(4));
				objSaldo.setSaldoAnteriorBanco(rs.getDouble(5));
				objSaldo.setSaldoAnteriorDivida(rs.getDouble(6));
				objSaldo.setFechado(rs.getString(7));
				
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

	public static SaldoCaixa consultarSaldoCaixaPeriodo(SaldoCaixa pCaixaInicial, SaldoCaixa pCaixaFinal) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select * from tb_historico_caixaand cd_nucleo="+pCaixaInicial.getNucleo().getCodigo();

			if(pCaixaInicial.getAno()!=0&&pCaixaInicial.getMes()!=0&&pCaixaFinal.getAno()!=0&&pCaixaFinal.getMes()!=0){
				SQL = "select max(vl_anomov),max(vl_mesmov),sum(vl_s_ant_caixa),sum(vl_s_ant_banco),sum(vl_s_ant_divida),'S' from tb_historico_caixa where vl_anomov="+pCaixaInicial.getAno()+" and vl_mesmov between "+pCaixaInicial.getMes()
				+" and "+pCaixaFinal.getMes()+" and cd_nucleo="+pCaixaInicial.getNucleo().getCodigo(); 
			}

			System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				SaldoCaixa objSaldo = new SaldoCaixa();
				
				objSaldo.setAno(rs.getInt(1));

				NucleoDAO objNucleoDAO = new NucleoDAO();
				objSaldo.setNucleo(objNucleoDAO.consultarNucleo(rs.getInt(2)));

				objSaldo.setMes(rs.getInt(3));
				objSaldo.setSaldoAnteriorCaixa(rs.getDouble(4));
				objSaldo.setSaldoAnteriorBanco(rs.getDouble(5));
				objSaldo.setSaldoAnteriorDivida(rs.getDouble(6));
				objSaldo.setFechado(rs.getString(7));
				
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

	public ArrayList<SaldoCaixa> consultarSaldoCaixaAnual(int pAno, int pNucleo) throws StuDAOException{
		PreparedStatement ps = null; //incluir nucleo!
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<SaldoCaixa> listTodas = null;
            
            SaldoCaixa objSaldo;
			String SQL = "select vl_mesmov,(vl_s_ant_caixa+vl_s_ant_banco) from tb_historico_caixa"
				+" where vl_anomov="+pAno+" and cd_nucleo="+pNucleo+" and fl_fechado='S'";

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<SaldoCaixa>();
                do {
                	objSaldo = new SaldoCaixa();
    				
    				objSaldo.setMes(rs.getInt(1));
    				objSaldo.setSaldoAnteriorCaixa(rs.getDouble(2));

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

	public void incluirSaldoCaixa(SaldoCaixa pCaixa) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "insert into tb_historico_caixa values(?, ?, ?, ?, ?, ?, ?)";
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			
			ps.setInt(1, pCaixa.getAno());
			ps.setInt(2, pCaixa.getNucleo().getCodigo());
			ps.setInt(3, pCaixa.getMes());
			ps.setDouble(4, pCaixa.getSaldoAnteriorCaixa());
			ps.setDouble(5, pCaixa.getSaldoAnteriorBanco());
			ps.setDouble(6, pCaixa.getSaldoAnteriorDivida());
			ps.setString(7, pCaixa.getFechado());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
}
