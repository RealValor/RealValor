package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import beans.HistoricoRecebimento;
import beans.Login;
import beans.Recibo;
import beans.Socio;
import beans.TipoEntrada;
import beans.TransacaoOnline;

import logica.StuConecta;

public class RecebimentoOnlineDAO {
	
	String MensErr = "Erro de acesso aos dados ";
	
	public static int buscaNovoNumeroReciboTemp(int pAno) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
		
			String SQL = "select ifnull(max(cd_recibo_temp),0)+1 from tb_recebimento_online where vl_anodata="+pAno;

			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;

		} catch (SQLException sqle) {
			throw new StuDAOException(sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}
	
	public static int gravaRecebimentoOnline(int pAno, int pOpe, int pNucleo) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			
			String SQL = "select gravaRecebimentoOnline("+pAno+","+pOpe+","+pNucleo+") from dual";
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if(rs.next()){
				return rs.getInt(1);
			}
			return 0;

		} catch (SQLException sqle) {
			throw new StuDAOException(sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public void incluiIdTransacaoOnline(Recibo pRecibo, String pTransacao, int pStatus, int pNucleo) throws StuDAOException{

		if(pRecibo!=null){
			
			String SQL = "update tb_recebimento_online set cd_transacao=?,cd_status_transacao=? where cd_recibo_temp="
			+pRecibo.getNumero()+" and vl_anodata="+pRecibo.getAno()+" and cd_nucleo="+pNucleo;
			
			//System.out.println(SQL);
			
			PreparedStatement ps = null;
			Connection conn = null;
			
			try {
				conn = StuConecta.getConnection();
				ps = conn.prepareStatement(SQL);

				ps.setString(1, pTransacao);
				ps.setInt(2, pStatus);
				
				ps.executeUpdate();

			} catch (SQLException sqle) {
				throw new StuDAOException(sqle);
			}finally{
				StuConecta.closeConnection(conn, ps);
			}
		}
	}
	
	public void zeraReciboOnline(int pNumeroRecibo, int pAno) throws StuDAOException{

		String SQL = "update tb_recebimento_online set cd_status_transacao=0 where cd_recibo_temp="+pNumeroRecibo+" and vl_anodata="+pAno;
		
		//System.out.println(SQL);
		
		PreparedStatement ps = null;
		Connection conn = null;
		
		try {
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			
			ps.executeUpdate();
			
		} catch (SQLException sqle) {
			throw new StuDAOException(sqle);
		}finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void incluiItensRecebimentoOnline(int pNumRec, ArrayList<Recibo> pListRec, int pNucleo) throws StuDAOException{

		if(pListRec!=null){
			
			String SQL1 = "insert into tb_itens_recebimento_online values(?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = null;
			Connection conn = null;

			try {
				for (Recibo itemRecibo : pListRec) {

					int meses[]=itemRecibo.getMeses();
					for (int i = 0; i < itemRecibo.getMeses().length; i++) {
						
						if(meses[i]!=0){
							//------------------------------------------itens entrada

							conn = StuConecta.getConnection();
							ps = conn.prepareStatement(SQL1);
							
							ps.setInt(1, pNumRec);
							ps.setInt(2, pNucleo);
							ps.setInt(3, itemRecibo.getData().get(Calendar.YEAR));
							ps.setInt(4, itemRecibo.getAno());
							ps.setInt(5, meses[i]);
							ps.setInt(6, itemRecibo.getEntrada().getCodigo());
							ps.setDouble(7, itemRecibo.getValor());
							ps.setInt(8, itemRecibo.getSocioDevedor().getCodigo());

							ps.executeUpdate();
							StuConecta.closeConnection(conn, ps);
						}
					}
				}

			}catch (SQLException sqle){
				throw new StuDAOException(MensErr+sqle);
			}
			finally{
				StuConecta.closeConnection(conn, ps);
			}
		}
	}
	
	public boolean consultaRecebimentoOnlinePendente(Login pDevedor, int pAno) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_recebimento_online r, tb_itens_recebimento_online i where r.cd_recibo_temp=i.cd_recibo_temp"+
			" and r.vl_anodata=i.vl_anodata and cd_status_transacao not in (4,6,7,8,0) and cd_devedor="+pDevedor.getUsuario()+" and vl_ano="+pAno;
			/*
			Status da transação.

			Informa o código representando o status da transação, permitindo que você decida se deve liberar ou não os produtos ou serviços adquiridos.
			Os valores possíveis estão descritos no diagrama de status de transações e são apresentados juntamente com seus respectivos códigos na tabela abaixo.
			
			Código	Significado
			1	Aguardando pagamento: o comprador iniciou a transação, mas até o momento o PagSeguro não recebeu nenhuma informação sobre o pagamento.
			2	Em análise: o comprador optou por pagar com um cartão de crédito e o PagSeguro está analisando o risco da transação.
			3	Paga: a transação foi paga pelo comprador e o PagSeguro já recebeu uma confirmação da instituição financeira responsável pelo processamento.
			4	Disponível: a transação foi paga e chegou ao final de seu prazo de liberação sem ter sido retornada e sem que haja nenhuma disputa aberta.
			5	Em disputa: o comprador, dentro do prazo de liberação da transação, abriu uma disputa.
			6	Devolvida: o valor da transação foi devolvido para o comprador.
			7	Cancelada: a transação foi cancelada sem ter sido finalizada.
			
			Obs.: Quando a transacao está em status 4, o sistema faz a atualização dos dados e efetiva o pagamento.
			*/
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if(rs.next()){
				return true;
			}else{
				return false;
			}
		} catch (SQLException sqle) {
			throw new StuDAOException("Erro -->"+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		
	}


	public ArrayList<TransacaoOnline> consultaRecebimentoOnlineEmEspera() throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select cd_transacao,cd_status_transacao from tb_recebimento_online where cd_status_transacao not in (4,6,7,8,0)";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			ArrayList<TransacaoOnline> listTodas = null;
			TransacaoOnline objTransacaoOnline;

			if (rs.next()) {
				
				 listTodas = new ArrayList<TransacaoOnline>();
				do {
					objTransacaoOnline = new TransacaoOnline();

					objTransacaoOnline.setTransacao(rs.getString("cd_transacao"));
					objTransacaoOnline.setStatus(rs.getInt("cd_status_transacao"));

					listTodas.add(objTransacaoOnline);
				} while (rs.next());
			}
			
			return listTodas;

		} catch (SQLException sqle) {
			throw new StuDAOException("Erro -->"+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		
	}

	public int buscaStatusTransacao(Login pDevedor, int pAno, int pStatus, int pNucleo) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select cd_status_transacao,count(cd_status_transacao) from tb_recebimento_online r, tb_itens_recebimento_online i where r.cd_recibo_temp=i.cd_recibo_temp"+
			" and r.vl_anodata=i.vl_anodata and cd_devedor="+pDevedor.getUsuario()+" and vl_ano="+pAno+" and cd_status_transacao="+pStatus+" and r.cd_nucleo="+pNucleo+
			" group by cd_status_transacao";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if(rs.next()){
				return rs.getInt(1);
			}
			return -1;
			
		} catch (SQLException sqle) {
			throw new StuDAOException("Erro -->"+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public int buscaAnoDataTransacao(Login pDevedor, int pAno) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select r.vl_anodata,count(*) from tb_recebimento_online r, tb_itens_recebimento_online i where r.cd_recibo_temp=i.cd_recibo_temp"+
			" and r.vl_anodata=i.vl_anodata and cd_devedor="+pDevedor.getUsuario()+" and vl_ano="+pAno+" order by r.vl_anodata";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if(rs.next()){
				return rs.getInt(1);
			}
			return 0;
		} catch (SQLException sqle) {
			throw new StuDAOException("Erro -->"+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public int buscaReciboTransacao(Login pDevedor, int pAno, int pTransacao) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select r.cd_recibo_temp,count(*) from tb_recebimento_online r, tb_itens_recebimento_online i where r.cd_recibo_temp=i.cd_recibo_temp"+
			" and r.vl_anodata=i.vl_anodata and cd_status_transacao="+pTransacao+" and cd_devedor="+pDevedor.getUsuario()+" and r.vl_anodata="+pAno+" order by r.cd_recibo_temp";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if(rs.next()){
				return rs.getInt(1);
			}
			return 0;
		} catch (SQLException sqle) {
			throw new StuDAOException("Erro -->"+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		
	}
	
	public ArrayList<Recibo> buscaDadosRecebimentoOnline(Login pDevedor, int pAno, int pNumeroRecibo) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_recebimento_online r, tb_itens_recebimento_online i where r.cd_recibo_temp=i.cd_recibo_temp"
			+" and r.vl_anodata=i.vl_anodata and cd_devedor="+pDevedor.getUsuario()+" and r.vl_anodata="+pAno+" and r.cd_recibo_temp="+pNumeroRecibo
			+" and r.cd_nucleo="+pDevedor.getNucleo().getCodigo();
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			ArrayList<Recibo> listTodas = null;
			Recibo objRecibo;

			listTodas = new ArrayList<Recibo>();
			if (rs.next()) {
				do {
					objRecibo = new Recibo();

					TipoEntrada itemTipEnt = new TipoEntrada();
                    itemTipEnt.setCodigo(rs.getInt("cd_tipo_entrada"));
					itemTipEnt.setNucleo(pDevedor.getNucleo());
                    
					objRecibo.setEntrada(TipoEntradaDAO.consultarTipoEntrada(itemTipEnt));
					
					objRecibo.setMes(rs.getInt("vl_mes"));
					objRecibo.setAno(rs.getInt("vl_ano"));
					objRecibo.setData(Calendar.getInstance());
					objRecibo.setCpfOperador(rs.getLong("cpf_operador"));
					objRecibo.setFlSocio("S");

					Socio objSocioDevedor = new Socio();
					objSocioDevedor.setCodigo(rs.getInt("cd_devedor"));
					
					objRecibo.setSocioDevedor(objSocioDevedor);

					Socio objSocioPagador = new Socio();
					objSocioPagador.setCodigo(rs.getInt("cd_pagador"));

					objRecibo.setSocioPagador(objSocioPagador);

					objRecibo.setValor(objRecibo.getValor());
					objRecibo.setNumeroLista(0);

					listTodas.add(objRecibo);
				} while (rs.next());
			}
			
			return listTodas;
			
		} catch (SQLException sqle) {
			throw new StuDAOException("Erro -->"+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		
	}

	
	public static ArrayList<HistoricoRecebimento> listarRecebimentoOnlinePendente(Login pHistRec, int pTPag, int pAno) throws StuDAOException{
		
		//Alterar esta classe para atender aos recebimentosOnline
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<HistoricoRecebimento> listTodas = null;
            
            HistoricoRecebimento objHistRec;

            String SQL="select r.cd_recibo_temp, vl_ano, dt_data, cpf_operador, cd_pagador, 'N', r.cd_recibo_temp, vl_ano, vl_mes, dt_data,"+
            	" cd_devedor, 'Nome do devedor', 'S', cd_tipo_entrada, vl_valor, count(vl_mes)"+
				" from tb_recebimento_online r, tb_itens_recebimento_online i where r.cd_recibo_temp=i.cd_recibo_temp and r.vl_anodata=i.vl_anodata"+
				" and cd_status_transacao not in (4,6,7,8,0) and cd_devedor="+pHistRec.getUsuario()+" and r.cd_nucleo="+pHistRec.getNucleo().getCodigo();
            
			if(pTPag>0){
				if(pTPag==2){
					SQL = SQL+" and i.cd_tipo_entrada in (1,2)";
				}else{
					SQL = SQL+" and i.cd_tipo_entrada="+pTPag;
				}
			}
			SQL = SQL+" and vl_ano="+pAno+" group by vl_mes order by cd_devedor,vl_ano,vl_mes";

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				listTodas = new ArrayList<HistoricoRecebimento>();
                do {
                	objHistRec = new HistoricoRecebimento();

                	objHistRec.setRecibo(rs.getInt(1));
                	
                	objHistRec.setAno(rs.getInt(2));
                	
                	Calendar dataCalendar = new GregorianCalendar(); 
    				dataCalendar.setTime(rs.getDate(3));
    				objHistRec.setData(dataCalendar);

    				
    				objHistRec.setCpfOperador(rs.getLong(4));
                	
                	Socio objSocioPagador = new Socio();
                	objSocioPagador.setCodigo(rs.getInt(5));
                	//SocioDAO cSocPagDAO = new SocioDAO();
                	//objSocioPagador = cSocPagDAO.consultarSoc(objSocioPagador);
                	objHistRec.setSocioPagador(objSocioPagador);
                	
                	objHistRec.setExcluido(rs.getString(6));
                	//7 repete recibo
                	//8 repete ano
                	objHistRec.setMes(rs.getInt(9));
                	
                	//DT_DATA(10) //do itens
                	Socio objSocioDevedor = new Socio();
                	objSocioDevedor.setCodigo(rs.getInt(11)); 
                	SocioDAO cSocDevDAO = new SocioDAO();
                	objSocioDevedor = cSocDevDAO.consultarSocio(objSocioDevedor, pHistRec.getNucleo());
                	objHistRec.setSocioDevedor(objSocioDevedor);
                	//NM_NOME_DEVEDOR(12)

                	objHistRec.setFlSocio(rs.getString(13)); //Socio <S/N>

                	TipoEntrada objTipoEntrada = new TipoEntrada();
                	objTipoEntrada.setCodigo(rs.getInt(14));
                	objTipoEntrada.setNucleo(pHistRec.getNucleo());
                	
                	objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);
                	objHistRec.setEntrada(objTipoEntrada);

                	objHistRec.setValor(rs.getDouble(15));
                	objHistRec.setQuantidade(rs.getInt(16));
                    
                    listTodas.add(objHistRec);
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
