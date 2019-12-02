package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import logica.StuConecta;
import beans.Divida;
import beans.Nucleo;
import beans.Saida;
import beans.TipoDocumento;

public class DividaDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static Divida consultarDivida(Divida pDiv) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select * from tb_divida where cd_nucleo="+pDiv.getNucleo().getCodigo();

			if(pDiv.getSaida()!=null){
				SQL = "select * from tb_divida where cd_saida="+pDiv.getSaida().getNumero()+" and vl_ano="+pDiv.getAno()+" and cd_nucleo="+pDiv.getNucleo().getCodigo(); 
			}
			if(pDiv.getAno()!=0&&pDiv.getMes()!=0){
				SQL = "select * from tb_divida where vl_ano="+pDiv.getAno()+" and vl_mes="+pDiv.getMes()+" and cd_nucleo="+pDiv.getNucleo().getCodigo(); 
			}
			if(pDiv.getSaida()!=null&&pDiv.getAno()!=0&&pDiv.getMes()!=0){
				SQL = "select * from tb_divida where cd_saida="+pDiv.getSaida().getNumero()+" and vl_ano="+pDiv.getAno()
						+" and vl_mes="+pDiv.getMes()+" and cd_nucleo="+pDiv.getNucleo().getCodigo(); 
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				Divida objDivida = new Divida();
				Saida objSaida = new Saida();
				
				objDivida.setAno(rs.getInt(1));

				Nucleo objNucleo = new Nucleo();				
				NucleoDAO objNucleoDAO = new NucleoDAO();
				
				objNucleo = objNucleoDAO.consultarNucleo(rs.getInt(2));
				
				objSaida.setNucleo(objNucleo);

				
				objDivida.setMes(rs.getInt(3));
				objDivida.setValor(rs.getDouble(4));
				
				objSaida.setNumero(rs.getInt(5));
				
				SaidaDAO itemSaidaDAO = new SaidaDAO(); 
				objSaida = itemSaidaDAO.consultarSaida(objSaida);
				
				objDivida.setSaida(objSaida);
				objDivida.setPago(rs.getString(5));
				
				return objDivida;
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

	public static Double checaDivida(Divida pDiv) throws StuDAOException{
		//Posterior verificar a necessidade deste método
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select (select vl_saida from tb_saida where cd_saida="+pDiv.getSaida().getNumero()
			+" and vl_ano="+pDiv.getAno()+")-(select sum(vl_valor)-(select vl_valor from tb_divida where cd_saida="+pDiv.getSaida().getNumero()
			+" and vl_ano="+pDiv.getAno()+" and vl_mes="+pDiv.getMes()+")+"+pDiv.getValor()
			+" from tb_divida where cd_saida="+pDiv.getSaida().getNumero()+" and vl_ano="+pDiv.getAno()+") from dual"; 

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getDouble(1);
			}
			return 0.00;
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public Double somaDivida(int pMes, int pAno) throws StuDAOException{
		//Posterior verificar a necessidade deste método
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select somaDivida("+pMes+","+pAno+") from dual"; 
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getDouble(1);
			}
			return 0.00;
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	//-----------------------------------------------------------------------------
	public Double somaDividaAberta(int pMes, int pAno) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select somaAntecipacaoAberta("+pMes+","+pMes+","+pAno+") from dual"; 
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getDouble(1);
			}
			return 0.00;
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public Double somaDividaAbertaPeriodo(int pMesInicial, int pMesFinal, int pAno, int pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select somaAntecipacaoAberta("+pMesInicial+","+pMesFinal+","+pAno+","+pNucleo+") from dual"; 
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getDouble(1);
			}
			return 0.00;
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public Double somaDividaPaga(int pMes, int pAno) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select somaAntecipacaoPaga("+pMes+","+pMes+","+pAno+") from dual"; 
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getDouble(1);
			}
			return 0.00;
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public Double somaDividaPagaPeriodo(int pMesInicial, int pMesFinal, int pAno, int pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select somaAntecipacaoPaga("+pMesInicial+","+pMesFinal+","+pAno+","+pNucleo+") from dual"; 
			
			//System.out.println(SQL);

			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getDouble(1);
			}
			return 0.00;
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public ArrayList<Divida> consultarAntecipacaoAnualAberta(int pAno, int pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Divida> listTodas = null;
            
            Divida objSaldo;

            String SQL = "select month(dt_movimento),somaAntecipacaoAberta(month(dt_movimento),month(dt_movimento),year(dt_movimento),"+pNucleo+")"
            +" from tb_divida where year(dt_movimento)="+pAno+" and cd_nucleo="+pNucleo 
			+" group by month(dt_movimento) order by month(dt_movimento)";
            
            //System.out.println(SQL);
            
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			listTodas = new ArrayList<Divida>();
			
			if (rs.next()) {
                do {
                	objSaldo = new Divida();
    				
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

	public ArrayList<Divida> consultarAntecipacaoAnualPaga(int pAno, int pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Divida> listTodas = null;
            
            Divida objSaldo;
            
			String SQL = "select month(dt_pagamento),somaAntecipacaoPaga(month(dt_pagamento),month(dt_pagamento),year(dt_pagamento),"+pNucleo+")"
			+" from tb_divida where year(dt_pagamento)="+pAno+" and cd_nucleo="+pNucleo 
			+" group by month(dt_pagamento) order by month(dt_pagamento)";
            
            //System.out.println(SQL);
            
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			listTodas = new ArrayList<Divida>();
			
			if (rs.next()) {
                do {
                	objSaldo = new Divida();
    				
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

	//---------------------------------------------------------------------------
	public void incluirDivida(ArrayList<Divida> pListDiv) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "insert into tb_divida values(?, ?, ?, ?, ?, ?, ?, ?, last_day(?), ?, ?, ?)";

			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			for (Divida itemDivida : pListDiv) {
				
				ps.setInt(1, itemDivida.getSaida().getNumero());
				ps.setInt(2, itemDivida.getNucleo().getCodigo());
				ps.setInt(3, itemDivida.getAno());
				ps.setInt(4, itemDivida.getMes());
				ps.setDouble(5, itemDivida.getValor());
				ps.setInt(6, itemDivida.getTipoSaida());
				ps.setString(7, itemDivida.getNumeroDocumento());

				ps.setDate(8, null);
				if(itemDivida.getDataDocumento()!=null){
					ps.setDate(8, new java.sql.Date(itemDivida.getDataDocumento().getTimeInMillis()));
				}
				
				ps.setDate(9, null);
				if(itemDivida.getMovimento()!=null){
					ps.setDate(9, new java.sql.Date(itemDivida.getMovimento().getTimeInMillis()));
				}

				ps.setDate(10, null);
				if(itemDivida.getVencimento()!=null){
					ps.setDate(10, new java.sql.Date(itemDivida.getVencimento().getTimeInMillis()));
				}

				ps.setDate(11, null);
				if(itemDivida.getPagamento()!=null){
					ps.setDate(11, new java.sql.Date(itemDivida.getPagamento().getTimeInMillis()));
				}
				
				ps.setString(12, itemDivida.getPago());
				ps.executeUpdate();
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}


	public void alterarDivida(Divida pDiv) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_divida set vl_valor=?,fl_pago=? where cd_saida="+pDiv.getSaida().getNumero()
			+" and vl_ano="+pDiv.getAno()+" and vl_mes="+pDiv.getMes()+" and cd_nucleo="+pDiv.getNucleo().getCodigo();
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setDouble(1, pDiv.getValor());
			ps.setString(2, pDiv.getPago());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void quitarDivida(Divida pDiv) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_divida set fl_pago=?,dt_pagamento=? where cd_saida="+pDiv.getSaida().getNumero()
			+" and vl_ano="+pDiv.getAno()+" and vl_mes="+pDiv.getMes()+" and cd_nucleo="+pDiv.getNucleo().getCodigo();

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, "S");
			
			ps.setDate(2, null);
			if(pDiv.getPagamento()!=null){
				ps.setDate(2, new java.sql.Date(pDiv.getPagamento().getTimeInMillis()));
			}

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirDivida(Divida pDiv) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "delete from tb_divida where cd_saida="+pDiv.getSaida().getNumero()
			+" and vl_ano="+pDiv.getAno()+" and vl_mes="+pDiv.getMes();

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
	
	//----------------------------------------------------
	public ArrayList<Divida> listarDividaPeriodo(int pAno, int pMesInicial, int pMesFinal, Nucleo pNucleo) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
            ArrayList<Divida> listTodas = null;
            Divida objDivida;

			String SQL = "select * from tb_divida where cd_nucleo="+pNucleo.getCodigo()+" and vl_ano="+pAno+
					" and vl_mes>="+pMesInicial+" and vl_mes<="+pMesFinal+" order by vl_ano,vl_mes,dt_movimento";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Divida>();
                do {
                	
    				objDivida = new Divida();
    				Saida objSaida = new Saida();
    				Nucleo objNucleo = new Nucleo();

    				TipoDocumento objTipoDocumento = new TipoDocumento();

    				objSaida.setNumero(rs.getInt(1));
    				objNucleo.setCodigo(rs.getInt(2));
    				objSaida.setAno(rs.getInt(3)); //Conforme chave composta
    				objDivida.setNucleo(objNucleo);

    				objDivida.setAno(rs.getInt(3));
    				objDivida.setMes(rs.getInt(4));
    				objDivida.setValor(rs.getDouble(5));
    				objDivida.setTipoSaida(rs.getInt(6));

    				objTipoDocumento.setCodigo(rs.getInt(6));
    				objSaida.setTipoDocumento(TipoDocumentoDAO.consultarTipoDocumento(objTipoDocumento));

    				objDivida.setSaida(objSaida);

    				objDivida.setNumeroDocumento(rs.getString(7));

    				Calendar dataDocCalendar = Calendar.getInstance();				
    				dataDocCalendar.setTime(rs.getDate(8));    				
    				objDivida.setDataDocumento(dataDocCalendar);

    				Calendar dataMovCalendar = Calendar.getInstance();				
    				dataMovCalendar.setTime(rs.getDate(9));
    				objDivida.setMovimento(dataMovCalendar);

    				Calendar dataVenCalendar = Calendar.getInstance();				
    				dataVenCalendar.setTime(rs.getDate(10));
    				objDivida.setVencimento(dataVenCalendar);

    				objDivida.setPagamento(null);
                	if(rs.getDate(11)!=null){
        				Calendar dataPagCalendar = Calendar.getInstance();				
        				dataPagCalendar.setTime(rs.getDate(11));
        				objDivida.setPagamento(dataPagCalendar);
                	}

    				objDivida.setPago(rs.getString(12));

                	listTodas.add(objDivida);
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
	//----------------------------------------------------
	public static ArrayList<Divida> listarDivida(Divida pDiv) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Divida> listTodas = null;
            Divida objDivida;

			String SQL = "select * from tb_divida where ";

			if(pDiv.getAno()!=0){
				SQL = SQL+"vl_ano="+pDiv.getAno()+" and ";
			}
			if(pDiv.getMes()!=0){
				SQL = SQL+"vl_mes="+pDiv.getMes()+" and ";
			}
			if(pDiv.getValor()!=0){
				SQL = SQL+"vl_valor="+pDiv.getValor()+" and ";
			}
			SQL = SQL+"cd_nucleo="+pDiv.getNucleo().getCodigo()+" and fl_pago='N' order by vl_ano,vl_mes,cd_saida";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Divida>();
                do {
                	
    				objDivida = new Divida();
    				Saida objSaida = new Saida();
    				Nucleo objNucleo = new Nucleo();

    				TipoDocumento objTipoDocumento = new TipoDocumento();

    				objSaida.setNumero(rs.getInt(1));
    				objNucleo.setCodigo(rs.getInt(2));
    				objSaida.setAno(rs.getInt(3)); //Conforme chave composta
    				objDivida.setNucleo(objNucleo);

    				objDivida.setAno(rs.getInt(3));
    				objDivida.setMes(rs.getInt(4));
    				objDivida.setValor(rs.getDouble(5));
    				objDivida.setTipoSaida(rs.getInt(6));

    				objTipoDocumento.setCodigo(rs.getInt(6));
    				objSaida.setTipoDocumento(TipoDocumentoDAO.consultarTipoDocumento(objTipoDocumento));

    				objDivida.setSaida(objSaida);

    				objDivida.setNumeroDocumento(rs.getString(7));

    				Calendar dataDocCalendar = Calendar.getInstance();				
    				dataDocCalendar.setTime(rs.getDate(8));    				
    				objDivida.setDataDocumento(dataDocCalendar);

    				Calendar dataMovCalendar = Calendar.getInstance();				
    				dataMovCalendar.setTime(rs.getDate(9));
    				objDivida.setMovimento(dataMovCalendar);

    				Calendar dataVenCalendar = Calendar.getInstance();				
    				dataVenCalendar.setTime(rs.getDate(10));
    				objDivida.setVencimento(dataVenCalendar);

    				objDivida.setPagamento(null);
                	if(rs.getDate(11)!=null){
        				Calendar dataPagCalendar = Calendar.getInstance();				
        				dataPagCalendar.setTime(rs.getDate(11));
        				objDivida.setPagamento(dataPagCalendar);
                	}

    				objDivida.setPago(rs.getString(12));

                	listTodas.add(objDivida);
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
