package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;
import beans.Recibo;
import beans.SocioValor;

import logica.StuConecta;

public class ReciboDAO {

	String MensErr = "Erro de acesso aos dados "; 

	public static int buscaNumeroRecibo(int pAno, int pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select geraNumeroRecibo("+pAno+","+pNucleo+") from dual";

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
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public static void gravaValorSocio(int pCod, int pTip, double pVal) throws StuDAOException{ //static 
		Connection connValorSocio = null;
		CallableStatement csValorSocio = null;
		
		try {
			connValorSocio = StuConecta.getConnection();
			
			csValorSocio = (CallableStatement) connValorSocio.prepareCall("{call gravaValorSocio(?,?,?)}");
			csValorSocio.setInt(1, pCod);
			csValorSocio.setInt(2, pTip);
			csValorSocio.setDouble(3, pVal);

			csValorSocio.executeUpdate();
			
		}
		catch (SQLException sqle){
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(connValorSocio, csValorSocio);
		}
	}
	
	public static void alteraValorSocio(int pCod, int pTip, double pVal) throws StuDAOException{ //static 
		Connection connValorSocio = null;
		CallableStatement csValorSocio = null;

		try {
			connValorSocio = StuConecta.getConnection();
			
			csValorSocio = (CallableStatement) connValorSocio.prepareCall("{call alteraValorSocio(?,?,?)}");
			csValorSocio.setInt(1, pCod);
			csValorSocio.setInt(2, pTip);
			csValorSocio.setDouble(3, pVal);

			csValorSocio.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(connValorSocio, csValorSocio);
		}
	}
	
	public void incluirRecibo(ArrayList<Recibo> pListRec) throws StuDAOException{
		
		if(pListRec.get(0).getOperador().getNivel()==2){
			return;
		}else{
			
			String SQL1 = "insert into tb_entrada values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String SQL2 = "insert into tb_itens_entrada values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String SQL3 = "insert into tb_historico_recebimento values(?, ?, ?, ?, ?, ?, ?, ?)"; 
			String SQL4 = "insert into tb_itens_historico_recebimento values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			String SQL5 = "delete from tb_recibo_temp where cd_nucleo="+pListRec.get(0).getNucleo();

			PreparedStatement ps = null;
			Connection conn = null;


			try {
				int x = 0;
				
				for (Recibo itemRecibo : pListRec) {

					if(x<1){

						//------------------------------------------entrada
						conn = StuConecta.getConnection();
						ps = conn.prepareStatement(SQL1);
						
						ps.setInt(1, itemRecibo.getNumero());
						ps.setInt(2, itemRecibo.getNucleo());
						ps.setInt(3, itemRecibo.getAno());
						ps.setDate(4, new java.sql.Date(itemRecibo.getData().getTimeInMillis()));

						ps.setInt(5, itemRecibo.getData().get(Calendar.YEAR));
						
						ps.setLong(6, itemRecibo.getCpfOperador());
						ps.setInt(7, itemRecibo.getSocioPagador().getCodigo());
						ps.setString(8, itemRecibo.getObservacao());
						ps.setString(9, "N");

						ps.executeUpdate();

						//------------------------------------------historico
						StuConecta.closeConnection(conn, ps);
						
						conn = StuConecta.getConnection();
						ps = conn.prepareStatement(SQL3);

						ps.setInt(1, itemRecibo.getNumero());
						ps.setInt(2, itemRecibo.getNucleo());
						ps.setInt(3, itemRecibo.getAno());
						ps.setDate(4, new java.sql.Date(itemRecibo.getData().getTimeInMillis()));
						ps.setLong(5, itemRecibo.getCpfOperador());
						ps.setInt(6, itemRecibo.getSocioPagador().getCodigo());
						
						ps.setString(7, "N");
						if(itemRecibo.getFechado()!=null&&itemRecibo.getFechado().equalsIgnoreCase("S")){
							ps.setString(7, "S");
						}
						
						ps.setString(8, itemRecibo.getObservacao());

						ps.executeUpdate();
						StuConecta.closeConnection(conn, ps);
					}

					int meses[]=itemRecibo.getMeses();
					
					for (int i = 0; i < itemRecibo.getMeses().length; i++) {

						if(meses[i]!=0){
							//------------------------------------------itens entrada

							conn = StuConecta.getConnection();
							ps = conn.prepareStatement(SQL2);

							ps.setInt(1, itemRecibo.getNumero());
							ps.setInt(2, itemRecibo.getNucleo());
							ps.setInt(3, itemRecibo.getAno());
							ps.setInt(4, meses[i]);
							ps.setDate(5, new java.sql.Date(itemRecibo.getData().getTimeInMillis()));
							ps.setInt(6, itemRecibo.getData().get(Calendar.YEAR));
							ps.setInt(7, itemRecibo.getEntrada().getCodigo());
							ps.setDouble(8, itemRecibo.getValor());
							ps.setInt(9, itemRecibo.getSocioDevedor().getCodigo()); //Long? (int)
							ps.setString(10, itemRecibo.getFlSocio()); //<S/N>

							ps.executeUpdate();

							StuConecta.closeConnection(conn, ps);
							
							conn = StuConecta.getConnection();
							ps = conn.prepareStatement(SQL4);
							//------------------------------------------itens historico

							ps.setInt(1, itemRecibo.getNumero());
							ps.setInt(2, itemRecibo.getNucleo());
							ps.setInt(3, itemRecibo.getAno());
							ps.setInt(4, meses[i]);
							ps.setDate(5, new java.sql.Date(itemRecibo.getData().getTimeInMillis()));
							ps.setInt(6, itemRecibo.getSocioDevedor().getCodigo());

							ps.setString(7, itemRecibo.getSocioDevedor().getNome());
							ps.setString(8, itemRecibo.getFlSocio());
							ps.setInt(9, itemRecibo.getEntrada().getCodigo());
							ps.setDouble(10, itemRecibo.getValor());

							ps.executeUpdate();
							StuConecta.closeConnection(conn, ps);

							if(itemRecibo.getFlSocio().equalsIgnoreCase("S")){

								SocioValorDAO objSocValDAO = new SocioValorDAO();
								
								SocioValor objSocVal = new SocioValor();
								
								objSocVal.setCodSocio(itemRecibo.getSocioDevedor().getCodigo());
								objSocVal.setCodEntrada(itemRecibo.getEntrada().getCodigo());
								objSocVal.setValor(itemRecibo.getValor());

								if(SocioValorDAO.consultarVal(objSocVal.getCodSocio(),objSocVal.getCodEntrada())==null){

									if(itemRecibo.getValor()!=itemRecibo.getEntrada().getValor()){
										//aqui - se a diferença for a menor, perguntar se deseja fixar como diferenciada, na hora do fechamento do recibo, 
										//ou incluir essa diferença como pendente pro próximo recebimento.
										objSocValDAO.incluirValorSocio(objSocVal);
									}
									
								}else{
									
									if(SocioValorDAO.consultarVal(objSocVal.getCodSocio(),objSocVal.getCodEntrada()).getValor()!=itemRecibo.getValor()){
										objSocValDAO.alterarValorSocio(objSocVal);
									}
								} //Foram alteradas as linhas acima. Antes chamavam as procedures de banco: alteraValorSocio e GravaValorSocio.
							}
						}
					}
					x++;
				}

				conn = StuConecta.getConnection();
				
				ps = conn.prepareStatement(SQL5);
				ps.executeUpdate();

			}catch (SQLException sqle){
				throw new StuDAOException(MensErr+sqle);
			}
			finally{
				StuConecta.closeConnection(conn, ps);
			}
		}
	}

	//---------------------------------------
	public static void utilizarReciboReserva(int pNumRec, int pAno, ArrayList<Recibo> pListRec) throws StuDAOException{
		
		if(pListRec==null){
			//verificar este tratamento.
		}else{
			
			String SQL1 = "insert into tb_entrada values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String SQL2 = "insert into tb_itens_entrada values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String SQL3 = "update tb_historico_recebimento set cpf_operador=?, cd_pagador=?, vl_ano=?, fl_excluido=? where cd_recibo="+pNumRec+" and cd_nucleo="+pListRec.get(0).getNucleo()+" and vl_ano="+pAno;
			String SQL4 = "insert into tb_itens_historico_recebimento values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = null;
			Connection conn = null;
			
			try {
				int x = 0;
				
				for (Recibo itemRecibo : pListRec) {
					if(x<1){
						
						//------------------------------------------entrada
						conn = StuConecta.getConnection();
						ps = conn.prepareStatement(SQL1);

						ps.setInt(1, pNumRec);
						ps.setInt(2, itemRecibo.getNucleo());
						ps.setInt(3, itemRecibo.getAno());
						ps.setDate(4, new java.sql.Date(itemRecibo.getData().getTimeInMillis()));
						ps.setInt(5, itemRecibo.getData().get(Calendar.YEAR));
						ps.setLong(6, itemRecibo.getCpfOperador());
						ps.setInt(7, itemRecibo.getSocioPagador().getCodigo());
						ps.setString(8, itemRecibo.getObservacao());
						ps.setString(9, itemRecibo.getFechado());

						ps.executeUpdate();

						StuConecta.closeConnection(conn, ps);

						//------------------------------------------historico
						conn = StuConecta.getConnection();
						ps = conn.prepareStatement(SQL3);
						
						ps.setLong(1, itemRecibo.getCpfOperador());
						ps.setLong(2, itemRecibo.getSocioPagador().getCodigo());
						ps.setInt(3, itemRecibo.getAno());
						ps.setString(4, "N");
						
						ps.executeUpdate();
						
						StuConecta.closeConnection(conn, ps);

					}
					
					int meses[]=itemRecibo.getMeses();
					for (int i = 0; i < itemRecibo.getMeses().length; i++) {

						if(meses[i]!=0){
							//------------------------------------------itens entrada
							conn = StuConecta.getConnection();
							ps = conn.prepareStatement(SQL2);
							
							ps.setInt(1, pNumRec);
							ps.setInt(2, itemRecibo.getNucleo());
							ps.setInt(3, itemRecibo.getAno());
							ps.setInt(4, meses[i]);
							ps.setDate(5, new java.sql.Date(itemRecibo.getData().getTimeInMillis()));
							
							ps.setInt(6, itemRecibo.getData().get(Calendar.YEAR));
							
							ps.setInt(7, itemRecibo.getEntrada().getCodigo());
							ps.setDouble(8, itemRecibo.getValor());
							ps.setLong(9, itemRecibo.getSocioDevedor().getCodigo());
							ps.setString(10, itemRecibo.getFlSocio()); //<S/N>
							
							ps.executeUpdate();
							
							StuConecta.closeConnection(conn, ps);

							//------------------------------------------itens historico
							conn = StuConecta.getConnection();
							ps = conn.prepareStatement(SQL4);
							
							ps.setInt(1, pNumRec);
							ps.setInt(2, itemRecibo.getNucleo());
							ps.setInt(3, itemRecibo.getAno());
							ps.setInt(4, meses[i]);
							ps.setDate(5, new java.sql.Date(itemRecibo.getData().getTimeInMillis()));
							ps.setInt(6, itemRecibo.getSocioDevedor().getCodigo());
							ps.setString(7, itemRecibo.getSocioDevedor().getNome());
							ps.setString(8, itemRecibo.getFlSocio());
							ps.setInt(9, itemRecibo.getEntrada().getCodigo());
							ps.setDouble(10, itemRecibo.getValor());
							
							ps.executeUpdate();

							StuConecta.closeConnection(conn, ps);

							if(itemRecibo.getValor()!=itemRecibo.getEntrada().getValor()&&itemRecibo.getFlSocio().equalsIgnoreCase("S")){
								
								SocioValorDAO objSocValDAO = new SocioValorDAO();
								SocioValor objSocVal = new SocioValor();
								
								objSocVal.setCodSocio(itemRecibo.getSocioDevedor().getCodigo());
								objSocVal.setCodEntrada(itemRecibo.getEntrada().getCodigo());
								objSocVal.setValor(itemRecibo.getValor());
								
								if(SocioValorDAO.consultarVal(itemRecibo.getSocioDevedor().getCodigo(), itemRecibo.getEntrada().getCodigo())!=null){
									objSocValDAO.alterarValorSocio(objSocVal);
								}else{
									objSocValDAO.incluirValorSocio(objSocVal);
								} //Foram alteradas as linhas acima tornando dispensáveis as procedures alteraValorSocio e GravaValorSocio.
							}
						}
					}
					x++;
				}
			}
			catch (SQLException sqle){
				throw new StuDAOException(sqle);
			}
			finally{
				StuConecta.closeConnection(conn, ps);
			}
		}
	}
	
	//-------------------------------------------
	public static void incluirReciboReserva(int pNumRec, Calendar pData, Login pOperador) throws StuDAOException{

		String SQL1 = "insert into tb_historico_recebimento values(?, ?, ?, ?, ?, ?, ?, ?)";
		String SQL2 = "delete from tb_recibo_temp where cd_recibo="+pNumRec+" and cd_nucleo="+pOperador.getNucleo().getCodigo();

		PreparedStatement ps = null;
		Connection conn = null;

		int ano = pData.get(Calendar.YEAR);
		
		try {
			//------------------------------------------historico
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL1);

			ps.setInt(1, pNumRec);
			ps.setInt(2, pOperador.getNucleo().getCodigo());
			ps.setInt(3, ano);
			ps.setDate(4, new java.sql.Date(pData.getTimeInMillis()));
			ps.setLong(5, pOperador.getCpf());
			ps.setInt(6, 0);
			ps.setString(7, "R"); //Reservado
			ps.setString(8, ""); 

			ps.executeUpdate();
			StuConecta.closeConnection(conn, ps);

			//------------------------------------------tb_recibo_temp
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL2);
			ps.executeUpdate();
			
		}
		catch (SQLException sqle){
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	//---------------------------------------
	
	@SuppressWarnings("static-access")
	public static ArrayList<HistoricoRecebimento> listarRecibo(int pCodSocio, int pNucleo, MovAtual pMovAtual, int pCancelado, String pFlsoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			ArrayList<HistoricoRecebimento> listTodas = null;
			HistoricoRecebimento itemHistRec;
			
			String SQL1 = "select distinct h.cd_recibo, h.vl_ano, h.dt_data, cpf_operador, cd_pagador, fl_excluido from tb_historico_recebimento h,"
				+" tb_itens_historico_recebimento i where h.cd_recibo=i.cd_recibo and year(h.dt_data)=year(i.dt_data)"
				+" and h.cd_nucleo=i.cd_nucleo and h.cd_nucleo="+pNucleo+" and ";

			String SQL2 = "";
			String SQL3 = "";
			String SQL4 = "";

			if(pCodSocio!=0){
				SQL2 = " cd_devedor="+pCodSocio+" and";
			}
			if(pMovAtual.getMes()!=0){
				SQL4 = " vl_mes="+pMovAtual.getMes()+" and"; 
			}
			if(pMovAtual.getAno()!=0){
				SQL3 = " i.vl_ano="+pMovAtual.getAno()+" and"; 
			}
			SQL1=SQL1+SQL2+SQL3+SQL4+" fl_excluido="+(pCancelado==1?"'N'":"fl_excluido")+" and i.fl_socio="+(pFlsoc.equalsIgnoreCase("N")?"'N'":"'S'");
			
			//System.out.println(SQL1);
			
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL1);
			rs = ps.executeQuery();

			listTodas = new ArrayList<HistoricoRecebimento>();

			if (rs.next()) {
				do {
					itemHistRec = new HistoricoRecebimento();
					
					itemHistRec.setRecibo(rs.getInt(1));
					itemHistRec.setAno(rs.getInt(2));
					
					Calendar dataCalendar = new GregorianCalendar(); 
					dataCalendar.setTime(rs.getDate(3));
					
					itemHistRec.setMes(dataCalendar.MONTH+1);
					itemHistRec.setData(dataCalendar);
					
					itemHistRec.setCpfOperador(rs.getLong(4));
					
					//itemHistRec.set .setCpfOperador(rs.getLong(5));
					itemHistRec.setExcluido(rs.getString(6));

                    listTodas.add(itemHistRec);
                 
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

	public static ArrayList<HistoricoRecebimento> consultarRecibo(int pRec, int pAno, String pFlsoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			ArrayList<HistoricoRecebimento> listTodas = null;
			HistoricoRecebimento itemHistRec;
			
			String SQL1 = "select distinct h.cd_recibo, h.vl_ano, h.dt_data, cpf_operador, cd_pagador, fl_excluido from tb_historico_recebimento h,"
				+" tb_itens_historico_recebimento i where h.cd_recibo=i.cd_recibo and year(h.dt_data)=year(i.dt_data) and";
			String SQL2 = "";
			String SQL3 = "";

			if(pRec!=0){
				SQL2 = " i.cd_recibo="+pRec+" and";
			}
			if(pAno!=0){
				SQL3 = " year(h.dt_data)="+pAno+" and";
			}
			SQL1=SQL1+SQL2+SQL3+" i.fl_socio="+(pFlsoc.equalsIgnoreCase("N")?"'N'":"'S'");
			
			//System.out.println(SQL1);
			
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL1);
			rs = ps.executeQuery();

			listTodas = new ArrayList<HistoricoRecebimento>();

			if (rs.next()) {
				do {
					itemHistRec = new HistoricoRecebimento();
					
					//Socio objSocioPag = new Socio();
					//Socio objSocioDev = new Socio();
					//TipoEntrada objTipoEntr = new TipoEntrada();

					itemHistRec.setRecibo(rs.getInt(1));
					itemHistRec.setAno(rs.getInt(2));
					
					Calendar dataCalendar = new GregorianCalendar(); 
					dataCalendar.setTime(rs.getDate(3));
					
					itemHistRec.setMes(dataCalendar.get(GregorianCalendar.MONTH)+1);
					itemHistRec.setData(dataCalendar);
					
					itemHistRec.setCpfOperador(rs.getLong(4));
					
					//itemHistRec.set .setCpfOperador(rs.getLong(5));
					itemHistRec.setExcluido(rs.getString(6));

                    listTodas.add(itemHistRec);
                 
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
	
	public void alterarDataRecibo(Recibo pRecibo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		
		String SQL1 = "update tb_entrada set dt_data=? where cd_nucleo="+pRecibo.getNucleo()+" and cd_recibo="+pRecibo.getNumero()+" and vl_ano="+pRecibo.getAno();
		String SQL2 = "update tb_itens_entrada set dt_data=? where cd_nucleo="+pRecibo.getNucleo()+" and cd_recibo="+pRecibo.getNumero()+" and vl_ano="+pRecibo.getAno();
		String SQL3 = "update tb_historico_recebimento set dt_data=? where cd_nucleo="+pRecibo.getNucleo()+" and cd_recibo="+pRecibo.getNumero()+" and vl_ano="+pRecibo.getAno();
		String SQL4 = "update tb_itens_historico_recebimento set dt_data=? where cd_nucleo="+pRecibo.getNucleo()+" and cd_recibo="+pRecibo.getNumero()+" and vl_ano="+pRecibo.getAno();

		try {

			//-----------------------------------entrada 
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL1);

			ps.setDate(1, new java.sql.Date(pRecibo.getData().getTimeInMillis()));

			ps.executeUpdate();
			StuConecta.closeConnection(conn, ps);

			//-----------------------------------itens_entrada 
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL2);

			ps.setDate(1, new java.sql.Date(pRecibo.getData().getTimeInMillis()));

			ps.executeUpdate();
			StuConecta.closeConnection(conn, ps);
			
			//-----------------------------------historico_recebimento
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL3);

			ps.setDate(1, new java.sql.Date(pRecibo.getData().getTimeInMillis()));

			ps.executeUpdate();
			StuConecta.closeConnection(conn, ps);

			//-----------------------------------itens_historico_recebimento
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL4);

			ps.setDate(1, new java.sql.Date(pRecibo.getData().getTimeInMillis()));

			ps.executeUpdate();
			StuConecta.closeConnection(conn, ps);
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

}
