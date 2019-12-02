package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import logica.ConverteFormatoData;
import logica.StuConecta;
import beans.Lista;
import beans.Nucleo;
import beans.Recibo;
import beans.Socio;
import beans.TipoEntrada;

public class ListaDAO {

	String MensErr = "Erro de acesso aos dados "; 
	
	public Lista consultarLista(Lista pLista) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_lista_arrecadacao where cd_nucleo="+pLista.getNucleo().getCodigo();
			if(pLista.getNumero()!=0&&pLista.getAno()!=0){
				SQL = "select * from tb_lista_arrecadacao where cd_lista="+pLista.getNumero()
				+" and cd_nucleo="+pLista.getNucleo().getCodigo()+" and vl_ano="+pLista.getAno();
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				Lista objLista = new Lista();
				
				objLista.setNumero(rs.getInt(1));

				NucleoDAO objNucleoDAO =new NucleoDAO();
				objLista.setNucleo(objNucleoDAO.consultarNucleo(rs.getInt(2)));

				
				objLista.setAno(rs.getInt(3));
				TipoEntrada objTipoEntrada = new TipoEntrada();
				objTipoEntrada.setCodigo(rs.getInt(4));
				
				objLista.setEntrada(objTipoEntrada);

				Calendar tipoCalendar = Calendar.getInstance();				
				tipoCalendar.setTime(rs.getDate(5));
				objLista.setData(tipoCalendar);
				objLista.setCpfOperador(rs.getLong(6));
				objLista.setFlContinua(rs.getString(7));
				objLista.setFlFechada(rs.getString(8));

				return objLista;
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

	public int buscaNumeroLista(int pAno, int pNucleo) throws StuDAOException{ //static 
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select geraNumeroLista("+pAno+","+pNucleo+") from dual";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public void incluirLista(Lista pItemLista, double pValorUnico) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		
		try {
			
			String SQL1 = "insert into tb_lista_arrecadacao values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String SQL2 = "insert into tb_itens_lista_arrecadacao values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL1);
			
			ps.setInt(1, pItemLista.getNumero());
			ps.setInt(2, pItemLista.getNucleo().getCodigo());
			
			ps.setInt(3, pItemLista.getAno());
			ps.setInt(4, pItemLista.getEntrada().getCodigo());
			if(pItemLista.getData()!=null){
				ps.setDate(5, new java.sql.Date(pItemLista.getData().getTimeInMillis()) );
			}
			ps.setLong(6, pItemLista.getCpfOperador());
			
			ps.setString(7, "N");
			if(pItemLista.getFlContinua().equalsIgnoreCase("S")){
				ps.setString(7, "S");
			}
			
			ps.setString(8, "N");
			
			ps.setInt(9, pItemLista.getQtdePagamentos());

			ps.executeUpdate();
			StuConecta.closeConnection(conn, ps);

			Socio itemSoc = new Socio();
			String pTipo = "ativo";
			
			ArrayList<Socio> listSocio = SocioDAO.listarSocio(pItemLista.getNucleo().getCodigo(),itemSoc, pTipo,0);

			if(listSocio!=null){

				for (Socio objSocio : listSocio) {

					conn = StuConecta.getConnection();
					ps = conn.prepareStatement(SQL2);
					
					ps.setInt(1, pItemLista.getNumero());
					ps.setInt(2, pItemLista.getNucleo().getCodigo());

					ps.setInt(3, pItemLista.getAno());
					ps.setInt(4, objSocio.getCodigo());
					
					ps.setDouble(5, pValorUnico);
					ps.setDouble(6, 0.0);

					ps.setDate(7, null);
					ps.setInt(8, 0);
					ps.setInt(9, 0);
					ps.setInt(10, pItemLista.getQtdePagamentos());
					
					ps.executeUpdate();
					StuConecta.closeConnection(conn, ps);
				}
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public static double buscaValorPago(int pNumeroLista, int pAno, int pCodigoDevedor, int pCodigoNucleo) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select vl_pago from tb_itens_lista_arrecadacao where cd_nucleo="+pCodigoNucleo
					+" and cd_socio="+pCodigoDevedor+" and cd_lista="+pNumeroLista+" and vl_ano="+pAno;
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getDouble(1);
			}
			return 0;
		}
		catch (SQLException sqle){
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}

		
	}
	
	public void gravaPagamentoLista(ArrayList<Recibo> pListRec) throws StuDAOException{

		if(pListRec!=null){

			PreparedStatement ps = null;
			Connection conn = null;

			try {
				for (Recibo itemRecibo : pListRec) {

					if(itemRecibo.getNumeroLista()>0){

						Nucleo objNucleo = new Nucleo();
						objNucleo.setCodigo(itemRecibo.getNucleo());
						
						Lista objLista = new Lista();
						
						objLista.setAno(itemRecibo.getAno());
						objLista.setNumero(itemRecibo.getNumeroLista());
						objLista.setNucleo(objNucleo);
						
						objLista = consultarLista(objLista);
						
						//consultar lista com numero, ano e socio, e pegar o total de parcelas pagas 
						
						if(objLista.getFlContinua().equalsIgnoreCase("N")||itemRecibo.getParcelasRestantes()>0){
							
							double valorPago = buscaValorPago(itemRecibo.getNumeroLista(),itemRecibo.getAno(),itemRecibo.getSocioDevedor().getCodigo(),itemRecibo.getNucleo());
							int parcelasRestantes = itemRecibo.getParcelasRestantes()==0?itemRecibo.getParcelasRestantes():itemRecibo.getParcelasRestantes()-1;
							
							String SQL = "update tb_itens_lista_arrecadacao set dt_pagto=?, cd_recibo=?, vl_ano_recibo=year(?), vl_pago=?, vl_qtde_parcelas=?"
									+" where cd_lista="+itemRecibo.getNumeroLista()+" and vl_ano="+itemRecibo.getAno()
									+" and cd_socio="+itemRecibo.getSocioDevedor().getCodigo()+" and cd_nucleo="+itemRecibo.getNucleo();

							conn = StuConecta.getConnection();
							ps = conn.prepareStatement(SQL);
							
							ps.setDate(1, new java.sql.Date(itemRecibo.getData().getTimeInMillis()));
							ps.setInt(2, itemRecibo.getNumero());
							ps.setDate(3, new java.sql.Date(itemRecibo.getData().getTimeInMillis()));
							ps.setDouble(4, itemRecibo.getValor()+valorPago);
							ps.setDouble(5, parcelasRestantes);
							
							ps.executeUpdate();
							StuConecta.closeConnection(conn, ps);

						}
						
					}
				}
			}
			catch (SQLException sqle){
				throw new StuDAOException(MensErr+sqle);
			}
			finally{
				StuConecta.closeConnection(conn, ps);
			}
		}
	}

	public void atualizaValorLista(Lista pItemLista) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_itens_lista_arrecadacao set vl_valor=? "
				+" where cd_lista="+pItemLista.getNumero()+" and vl_ano="+pItemLista.getAno()
				+" and cd_nucleo="+pItemLista.getNucleo().getCodigo()+" and cd_socio="+pItemLista.getSocioDevedor().getCodigo();

			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);

			ps.setDouble(1, pItemLista.getValor());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	//-------------------------------
	public void revertePagamentoLista(Recibo pItemRecibo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;

		try {
			String SQL = "update tb_itens_lista_arrecadacao set dt_pagto=null,cd_recibo=0,vl_ano_recibo=0,vl_pago=vl_pago-"+pItemRecibo.getValor()+",vl_qtde_parcelas=vl_qtde_parcelas+1"
				+" where cd_recibo="+pItemRecibo.getNumero()+" and vl_ano_recibo="+pItemRecibo.getAno()+" and cd_nucleo="+pItemRecibo.getNucleo()
				+" and cd_lista="+pItemRecibo.getNumeroLista();
			
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
	//--------------------------------
	public Double buscaTotalLista(Lista pItem) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select sum(vl_valor) from tb_itens_lista_arrecadacao where cd_lista="+pItem.getNumero()
					+" and cd_nucleo="+pItem.getNucleo().getCodigo()+" and vl_ano="+pItem.getAno()+" and cd_socio in (select cd_socio from tb_socio)";
			
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
	
	public void alterarLista(Lista pItemLista) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_lista_arrecadacao set cd_lista=?,vl_ano=?, cd_entrada=?, dt_lista=?, cpf_operador=?,"
					+" where cd_lista="+pItemLista.getNumero()+" and vl_ano="+pItemLista.getAno();

			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);

			ps.setInt(1, pItemLista.getNumero());
			ps.setInt(2, pItemLista.getAno());
			//-----verificar as duas linhas acima
			
			ps.setInt(3, pItemLista.getEntrada().getCodigo());

			if(pItemLista.getData()!=null){
				ps.setDate(4, new java.sql.Date(pItemLista.getData().getTimeInMillis()) );
			}
			ps.setLong(5, pItemLista.getCpfOperador());
			ps.setString(6, "N");

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void fecharLista(Lista pLista) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {

			//-------------------------------
			String SQL = "update tb_lista_arrecadacao set fl_fechada=? where cd_nucleo="+pLista.getNucleo().getCodigo()
				+" and cd_lista="+pLista.getNumero()+" and vl_ano="+pLista.getAno()+" and CD_LISTA not in"
				+" (select cd_lista from tb_itens_lista_arrecadacao where cd_nucleo="+pLista.getNucleo().getCodigo()
				+" and vl_valor<>0 and dt_pagto is null group by CD_LISTA)";
			//-------------------------------
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);

			ps.setString(1, "S");

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void excluirLista(Lista pItemLista) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "delete from tb_lista_arrecadacao where cd_lista="+pItemLista.getNumero()
					+" and cd_nucleo="+pItemLista.getNucleo().getCodigo()+" and vl_ano="+pItemLista.getAno();
			
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
	
	public int consultaRecebimentoLista(Lista pItemLista) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select count(*) from (select * from tb_itens_lista_arrecadacao where cd_lista="+pItemLista.getNumero()
			+" and cd_nucleo="+pItemLista.getNucleo().getCodigo()+" and vl_ano="+pItemLista.getAno()+" and vl_valor<>0) l where dt_pagto is null";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public int consultaDoacaoLista(Lista pItemLista) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select count(*) from tb_itens_lista_arrecadacao where cd_lista="+pItemLista.getNumero()
			+" and cd_nucleo="+pItemLista.getNucleo().getCodigo()+" and vl_ano="+pItemLista.getAno()+" and vl_valor<>0";
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	//---------------------
	public String consultaFechamentoLista(Lista pItemLista) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select fl_fechada from tb_lista_arrecadacao where cd_lista="+pItemLista.getNumero()
					+" and cd_nucleo="+pItemLista.getNucleo().getCodigo()+" and vl_ano="+pItemLista.getAno();
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString(1);
			}
			return null;
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}
	//---------------------
	
	public static ArrayList<Lista> consultarDevedor(int pCodSocio, Nucleo pNucleo, int pCodLista, int pAnolista) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			ArrayList<Lista> listTodas = null;
			Lista itemLista;
			
			String SQL = "select * from tb_lista_arrecadacao l, tb_itens_lista_arrecadacao i where (vl_valor-vl_pago) <> 0 "
				+" and l.cd_lista=i.cd_lista and l.vl_ano=i.vl_ano and l.cd_nucleo=i.cd_nucleo and l.cd_nucleo="+pNucleo.getCodigo(); 
			if(pCodSocio!=0){
				SQL = SQL+" and i.cd_socio="+pCodSocio;
			}
			if(pCodLista!=0){
				SQL = SQL+" and i.cd_lista="+pCodLista;
			}
			if(pAnolista!=0){
				SQL = SQL+" and i.vl_ano="+pAnolista;
			}
			SQL = SQL+" order by i.vl_ano,i.cd_lista";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				listTodas = new ArrayList<Lista>();
				do {
					itemLista = new Lista();	

					itemLista.setNumero(rs.getInt("i.cd_lista"));
					itemLista.setAno(rs.getInt("i.vl_ano"));
					
					
					Socio objSocio = new Socio();
					objSocio.setCodigo(rs.getInt("i.cd_socio"));
					
					itemLista.setSocioDevedor(objSocio);

                    TipoEntrada objEntrada = new TipoEntrada();
                    objEntrada.setCodigo(rs.getInt("l.cd_entrada"));
                    objEntrada.setNucleo(pNucleo);
                    
                    objEntrada = TipoEntradaDAO.consultarTipoEntrada(objEntrada);

                    itemLista.setEntrada(objEntrada);
                    
                    Calendar tipoCalendar = Calendar.getInstance();				
    				tipoCalendar.setTime(rs.getDate("l.dt_lista"));

    				itemLista.setData(tipoCalendar);
					itemLista.setValor(rs.getDouble("i.vl_valor"));
					itemLista.setValorPago(rs.getDouble("i.vl_pago"));
					
					itemLista.setDataPagamento(null);
					if(rs.getDate("i.dt_pagto")!=null){
						Calendar tipoCalendarVenci = Calendar.getInstance();				
						tipoCalendarVenci.setTime(rs.getDate("i.dt_pagto"));
						
						itemLista.setDataPagamento(tipoCalendarVenci);
					}
					
					itemLista.setFlContinua(rs.getString("l.fl_continua"));

					itemLista.setTotal(rs.getInt("l.vl_qtde_parcelas")); //lista_arrecadação
					itemLista.setQtdePagamentos(rs.getInt("i.vl_qtde_parcelas")); //itens_lista
					
					itemLista.setReciboGerado(rs.getInt("i.cd_recibo")+"/"+rs.getInt("i.vl_ano_recibo"));
					listTodas.add(itemLista);
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

	public static boolean VerificaEntradaLista(TipoEntrada pEntrada) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {

			String SQL = "select *from tb_lista_arrecadacao where cd_nucleo="+pEntrada.getNucleo().getCodigo()+" and cd_entrada="+pEntrada.getCodigo();

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

	public static ArrayList<Lista> consultarDetalheLista(Lista pItemLista) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			ArrayList<Lista> listTodas = null;
			Lista itemLista;

			String SQL = "select * from tb_itens_lista_arrecadacao where cd_nucleo="+pItemLista.getNucleo().getCodigo();
			if(pItemLista.getNumero()!=0&&pItemLista.getAno()!=0){
				SQL = "select * from tb_lista_arrecadacao l, tb_itens_lista_arrecadacao i, tb_socio s where i.cd_lista="+pItemLista.getNumero()
					+" and i.cd_nucleo="+pItemLista.getNucleo().getCodigo() +" and i.vl_ano="+pItemLista.getAno()+" and l.cd_lista=i.cd_lista"
					+" and l.cd_nucleo=i.cd_nucleo and l.vl_ano=i.vl_ano and i.cd_socio=s.cd_socio order by s.cd_grau<>'M',s.cd_grau,s.nm_nome";
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			String reciboAno="em aberto";
			if (rs.next()) {
				listTodas = new ArrayList<Lista>();
				do {
					itemLista = new Lista();	

					itemLista.setNumero(rs.getInt("cd_lista"));
					itemLista.setAno(rs.getInt("vl_ano"));

					Socio objSocio = new Socio();
					objSocio.setCodigo(rs.getInt("cd_socio"));
					objSocio.setNome(rs.getString("nm_nome"));
					objSocio.setGrau(rs.getString("cd_grau"));

					itemLista.setSocioDevedor(objSocio);

                    TipoEntrada objEntrada = new TipoEntrada();
                    objEntrada.setCodigo(rs.getInt("cd_entrada"));
                    objEntrada.setNucleo(pItemLista.getNucleo());
                    
                    objEntrada = TipoEntradaDAO.consultarTipoEntrada(objEntrada);

                    itemLista.setEntrada(objEntrada);

                    Calendar tipoCalendar = Calendar.getInstance();				
    				tipoCalendar.setTime(rs.getDate("dt_lista"));

    				itemLista.setData(tipoCalendar);
					itemLista.setValor(rs.getDouble("vl_valor"));
					itemLista.setValorPago(rs.getDouble("vl_pago"));

					itemLista.setDataPagamento(null);
					if(rs.getDate("dt_pagto")!=null){
						Calendar tipoCalendarVenci = Calendar.getInstance();				
						tipoCalendarVenci.setTime(rs.getDate("dt_pagto"));
						
						itemLista.setDataPagamento(tipoCalendarVenci);

						SimpleDateFormat sdf = new SimpleDateFormat( "yyyy" );
						reciboAno=rs.getInt("cd_recibo")+"/"+sdf.format(itemLista.getDataPagamento().getTime());
					}

					itemLista.setReciboGerado(reciboAno);
					itemLista.setQtdePagamentos(rs.getInt("i.vl_qtde_parcelas"));

					listTodas.add(itemLista);
					
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
	
	public static ArrayList<Lista> listarLista(Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Lista> listTodas = null;
            Lista itemLista;
            String SQL = "select a.CD_LISTA,a.cd_nucleo,a.VL_ANO,a.CD_ENTRADA,a.DT_LISTA,a.CPF_OPERADOR,a.FL_CONTINUA,a.FL_FECHADA,b.total,b.valor,a.vl_qtde_parcelas"
            		+" from (select * from tb_lista_arrecadacao where cd_nucleo="+pNucleo.getCodigo()+" and fl_fechada<>'S') a,"
            		+" (select cd_lista,sum(valor) valor,sum(total) total,vl_ano from (select cd_lista,(vl_valor-vl_pago) valor,(vl_valor-vl_pago)/(vl_valor-vl_pago) total,vl_ano from tb_itens_lista_arrecadacao where cd_nucleo="
            		+pNucleo.getCodigo()+") bb group by CD_LISTA,vl_ano) b"
            		+" where a.cd_lista=b.cd_lista and a.VL_ANO=b.VL_ANO group by a.CD_LISTA,vl_ano order by a.vl_ano desc, b.cd_lista desc";
			
            //System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Lista>();
                do {
                    itemLista = new Lista();
                    itemLista.setNumero(rs.getInt("cd_lista"));
                    
                    itemLista.setAno(rs.getInt("vl_ano"));
                    
                    TipoEntrada objEntrada = new TipoEntrada();
                    objEntrada.setCodigo(rs.getInt("cd_entrada"));
                    objEntrada.setNucleo(pNucleo);
                    
                    objEntrada = TipoEntradaDAO.consultarTipoEntrada(objEntrada);

                    itemLista.setEntrada(objEntrada);
                    
                    Calendar tipoCalendar = Calendar.getInstance();				
    				tipoCalendar.setTime(rs.getDate("dt_lista"));

    				itemLista.setData(tipoCalendar);
    				
    				itemLista.setFlContinua(rs.getString("fl_continua"));
    				
    				itemLista.setValor(rs.getDouble("valor"));
    				itemLista.setTotal(rs.getInt("total")); 
    				itemLista.setQtdePagamentos(rs.getInt("vl_qtde_parcelas"));
                    
                    listTodas.add(itemLista);
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

	public static ArrayList<Lista> listarListaDevedor(Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Lista> listTodas = null;
            Lista itemLista;

            String SQL = "select l.cd_lista,l.vl_ano,l.cd_entrada,cd_socio,(vl_valor-vl_pago) vl_valor,l.dt_lista,l.fl_continua from tb_lista_arrecadacao l, tb_itens_lista_arrecadacao i"+
					" where l.cd_nucleo="+pNucleo.getCodigo()+" and (vl_valor-vl_pago)>0 and l.cd_nucleo=i.cd_nucleo" +
					" and l.cd_lista=i.cd_lista and l.vl_ano=i.vl_ano order by i.cd_socio";
				
			conn = StuConecta.getConnection( );
			
			//System.out.println(SQL);
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Lista>();
                do {
                    itemLista = new Lista();
                    itemLista.setNumero(rs.getInt("cd_lista"));
                    itemLista.setAno(rs.getInt("vl_ano"));
                    
                    TipoEntrada objEntrada = new TipoEntrada();
                    objEntrada.setCodigo(rs.getInt("cd_entrada"));
                    objEntrada.setNucleo(pNucleo);
                    
                    objEntrada = TipoEntradaDAO.consultarTipoEntrada(objEntrada);
                    itemLista.setEntrada(objEntrada);
                    
                    Socio objSocio = new Socio();
                    objSocio.setCodigo(rs.getInt("cd_socio"));
                    itemLista.setSocioDevedor(objSocio);
                    
                    itemLista.setValor(rs.getDouble("vl_valor"));
                    itemLista.setData(ConverteFormatoData.dateToCalendar(rs.getDate("dt_lista")));
                    itemLista.setFlFechada("N");
                    itemLista.setFlContinua(rs.getString("fl_continua")); //incluído em 26/12/2018
                    
                    listTodas.add(itemLista);
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

	public static ArrayList<Lista> listarListaFechada(Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Lista> listTodas = null;
            Lista itemLista;

			String SQL = "select * from tb_lista_arrecadacao where cd_nucleo="+pNucleo.getCodigo()+" and fl_fechada='S'"
					+" order by vl_ano desc, cd_lista desc";
			conn = StuConecta.getConnection( );
			
			//System.out.println(SQL);
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Lista>();
                do {
                    itemLista = new Lista();
                    itemLista.setNumero(rs.getInt("cd_lista"));
                    itemLista.setAno(rs.getInt("vl_ano"));
                    
                    TipoEntrada objEntrada = new TipoEntrada();
                    objEntrada.setCodigo(rs.getInt("cd_entrada"));
                    objEntrada.setNucleo(pNucleo);
                    
                    objEntrada = TipoEntradaDAO.consultarTipoEntrada(objEntrada);

                    itemLista.setEntrada(objEntrada);
                    
                    Calendar tipoCalendar = Calendar.getInstance();				
    				tipoCalendar.setTime(rs.getDate("dt_lista"));

    				itemLista.setData(tipoCalendar);
                    
                    listTodas.add(itemLista);
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
	
	public static ArrayList<Recibo> buscaPagamentoLista(Nucleo pNucleo, int pNumRec,int pNumAno) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Recibo> listTodas = null;
            Recibo itemRecibo;

			String SQL = "select l.cd_lista,h.cd_recibo,year(h.dt_data) ano,h.cd_nucleo,vl_mes,h.dt_data,i.cd_socio,h.cd_tipo_entrada,h.vl_valor"
			+" from tb_itens_historico_recebimento h, tb_lista_arrecadacao l, tb_itens_lista_arrecadacao i"
			+" where h.cd_recibo="+pNumRec+" and year(h.dt_data)="+pNumAno+" and h.cd_nucleo="+pNucleo.getCodigo()
			+" and h.cd_tipo_entrada=l.cd_entrada and i.vl_ano_recibo=year(h.dt_data) and h.cd_recibo=i.cd_recibo and h.cd_nucleo=i.cd_nucleo"
			+" and l.cd_lista=i.cd_lista and l.vl_ano=i.vl_ano and l.cd_nucleo=i.cd_nucleo and (not (l.FL_CONTINUA='S' and l.vl_qtde_parcelas=0))";
 
			conn = StuConecta.getConnection( );
			
			//System.out.println(SQL);
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			//SimpleDateFormat ano = new SimpleDateFormat("yyyy");
			
			if (rs.next()) {
                listTodas = new ArrayList<Recibo>();
                do {
                	
                    itemRecibo = new Recibo();
                    
                    itemRecibo.setNumeroLista(rs.getInt("cd_lista"));
                    itemRecibo.setNumero(rs.getInt("cd_recibo"));
                    itemRecibo.setAno(rs.getInt("ano"));
                    itemRecibo.setNucleo(rs.getInt("cd_nucleo"));
                    itemRecibo.setMes(rs.getInt("vl_mes"));
                    
                    Calendar tipoCalendar = Calendar.getInstance();				
                    tipoCalendar.setTime(rs.getDate("dt_data"));
    				itemRecibo.setData(tipoCalendar);
                    
    				Socio objSocio = new Socio();
    				objSocio.setCodigo(rs.getInt("cd_socio"));
    				itemRecibo.setSocioDevedor(objSocio);
    				
    				TipoEntrada objEntrada = new TipoEntrada();
    				objEntrada.setCodigo(rs.getInt("cd_tipo_entrada"));
    				itemRecibo.setEntrada(objEntrada);
    				itemRecibo.setValor(rs.getDouble("vl_valor"));
    				
                    listTodas.add(itemRecibo);
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
