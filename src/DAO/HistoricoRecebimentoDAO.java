package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import logica.StuConecta;
import beans.DebitoSocio;
import beans.HistoricoDevedor;
import beans.HistoricoRecebimento;
import beans.Login;
import beans.NaoSocio;
import beans.Nucleo;
import beans.Socio;
import beans.TipoEntrada;

public class HistoricoRecebimentoDAO {

	String MensErr = "Erro de acesso aos dados ";
	
	public static ArrayList<HistoricoRecebimento> consultarRecibosReserva(int pNucleo,int pMes, int pAno) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			ArrayList<HistoricoRecebimento> listTodas = null;
			HistoricoRecebimento objHistRec;

			String SQL = "select * from tb_historico_recebimento where fl_excluido='R' and cd_nucleo="+pNucleo;
			if(pMes!=0&&pAno!=0){
				SQL = SQL+" and month(dt_data)="+pMes+" and vl_ano="+pAno;
			}
			SQL = SQL+" order by dt_data,cd_recibo";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			listTodas = new ArrayList<HistoricoRecebimento>();
			if (rs.next()) {
				do {
					objHistRec = new HistoricoRecebimento();

					objHistRec.setRecibo(rs.getInt(1));

					NucleoDAO objNucleoDAO = new NucleoDAO();
					objHistRec.setNucleo(objNucleoDAO.consultarNucleo(rs.getInt(2)));

					objHistRec.setAno(rs.getInt(3));

					GregorianCalendar dataCalendar = new GregorianCalendar(); 
					dataCalendar.setTime(rs.getDate(4));

					objHistRec.setData(dataCalendar);
					
					objHistRec.setMes(dataCalendar.get(GregorianCalendar.MONTH)+1);
					
					objHistRec.setCpfOperador(rs.getLong(5));

					listTodas.add(objHistRec);
				} while (rs.next());
			}else{
				objHistRec = new HistoricoRecebimento();

				objHistRec.setRecibo(0);
				objHistRec.setAno(0);

				GregorianCalendar dataCalendar = new GregorianCalendar(); 

				objHistRec.setData(dataCalendar);
				objHistRec.setMes(0);
				
				objHistRec.setCpfOperador(0);

				listTodas.add(objHistRec);
			}
			return listTodas;
		}catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}
	//-------------------------------------------------------------------------------
	public void incluirHistRec(HistoricoRecebimento pHistRec) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "insert into tb_historico_recebimento values(cd_recibo, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			
			//ps.setInt(1, pHistRec.getRecibo()); //autoincremento
			ps.setInt(2, pHistRec.getMes());
			ps.setInt(3, pHistRec.getAno());
			ps.setDate(4, new java.sql.Date(pHistRec.getData().getTimeInMillis()));
			ps.setDouble(5, pHistRec.getCpfOperador());
			ps.setInt(6, pHistRec.getSocioPagador().getCodigo());
			ps.setInt(7, pHistRec.getSocioDevedor().getCodigo());
			ps.setString(8, pHistRec.getSocioDevedor().getNome());
			ps.setString(9, pHistRec.getFlSocio());
			ps.setInt(10, pHistRec.getEntrada().getCodigo());
			ps.setString(11, pHistRec.getEntrada().getDescricao());
			ps.setDouble(12, pHistRec.getValor());
			ps.setString(13, pHistRec.getExcluido());
			
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void cancelarHistRec(int pNucleo, int pRecibo, int pAno, String pNomeOperador) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_historico_recebimento set fl_excluido=?,nm_observacao=? where cd_nucleo ="+pNucleo+" and cd_recibo="+pRecibo+" and year(dt_data)="+pAno;
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			
			ps.setString(1, "S");
			ps.setString(2, "Recibo cancelado por "+pNomeOperador); //posteriormente incluir data. 
																	//Sugestão: alterar o campo fl_excluido pra tipo data, uma vez populado foi excluido
			//System.out.println(SQL);

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public int verificarMudancaDeMes(int pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select mudancaDeMes("+pNucleo+") from dual";

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
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public Date buscarUltimaData(Login pOperador) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select max(dt_data) from tb_historico_recebimento where cd_nucleo="+pOperador.getNucleo().getCodigo();
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getDate(1);
			}else{
				return null;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public int buscarAnoPrimeiroPagamento(int pCodSocio) throws StuDAOException{ 
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "SELECT min(vl_ano) from tb_itens_historico_recebimento where cd_devedor="+pCodSocio;
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}else{
				return 0;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public static ArrayList<HistoricoRecebimento> listarHistRec(HistoricoRecebimento pHistRec) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<HistoricoRecebimento> listTodas = null;
            HistoricoRecebimento objHistRec;

			String SQL = "select * from tb_historico_recebimento";
			if(pHistRec.getSocioDevedor().getNome()!=null){
				SQL = "select * from tb_historico_recebimento where upper(nm_nome) like '%"+pHistRec.getSocioDevedor().getNome()+"%'"; 
			}
			SQL=SQL+" order by nm_nome";

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<HistoricoRecebimento>();
                do {
                	objHistRec = new HistoricoRecebimento();

                	objHistRec.setRecibo(rs.getInt(1));
                	objHistRec.setMes(rs.getInt(2));
                	objHistRec.setAno(rs.getInt(3));
                	
    				Calendar dataCalendar = new GregorianCalendar(); 
    				dataCalendar.setTime(rs.getDate(4));
    				
    				objHistRec.setData(dataCalendar);
                	//objHistRec.setData(rs.getDate(4));
                	objHistRec.setCpfOperador(rs.getLong(5));

                	Socio objSocioPagador = new Socio();
                	objSocioPagador.setCodigo(rs.getInt(6));
                	SocioDAO cSocPagDAO = new SocioDAO();
                	objSocioPagador = cSocPagDAO.consultarSocio(objSocioPagador, pHistRec.getNucleo());

                	Socio objSocioDevedor = new Socio();
                	objSocioDevedor.setCodigo(rs.getInt(7)); //NM_NOME_DEVEDOR(8)
                	SocioDAO cSocDevDAO = new SocioDAO();
                	objSocioDevedor = cSocDevDAO.consultarSocio(objSocioDevedor, pHistRec.getNucleo());

                	objHistRec.setFlSocio(rs.getString(9));

                	TipoEntrada objTipoEntrada = new TipoEntrada();
                	objTipoEntrada.setCodigo(rs.getInt(10));
                	
                	objTipoEntrada.setNucleo(pHistRec.getNucleo());
                	
                	objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);

                	objHistRec.setEntrada(objTipoEntrada);
                	//NM_DESCRICAO_ENTRADA(11)

                	objHistRec.setValor(rs.getDouble(12));
                	objHistRec.setExcluido(rs.getString(13));

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
	
	public static ArrayList<HistoricoRecebimento> listarHistoricoRecebimento(Login pHistRec, int pTPag, int pAno) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<HistoricoRecebimento> listTodas = null;
            
            HistoricoRecebimento objHistRec;

            String SQL="select *,count(vl_mes) from tb_historico_recebimento as h,tb_itens_historico_recebimento as i where h.cd_recibo=i.cd_recibo"
            	+" and year(h.dt_data)=year(i.dt_data) and h.cd_nucleo=i.cd_nucleo and i.cd_nucleo="+pHistRec.getNucleo().getCodigo();
            
			if(pHistRec.getNome()!=null){
				SQL = SQL+" and upper(i.nm_nome_devedor) like '%"+pHistRec.getNome().toUpperCase()+"%' and h.fl_excluido='N'"; 
			}
			if(pHistRec.getUsuario()!=0){
				SQL="select *,count(vl_mes) from tb_historico_recebimento as h,tb_itens_historico_recebimento as i where h.cd_recibo=i.cd_recibo"
            	+" and year(h.dt_data)=year(i.dt_data) and h.cd_nucleo=i.cd_nucleo and i.cd_nucleo="+pHistRec.getNucleo().getCodigo()
				+" and i.cd_devedor="+pHistRec.getUsuario()+" and h.fl_excluido='N'";
			}
			
			if(pTPag>0){
				if(pTPag==2){
					SQL = SQL+" and i.cd_tipo_entrada in (1,2)";
				}else{
					SQL = SQL+" and i.cd_tipo_entrada="+pTPag;
				}
			}

			if(pTPag==0&&pHistRec.getSituacao().equalsIgnoreCase("N")){
				SQL = SQL+" and i.fl_socio='N'"; 
			}

			SQL = SQL+" and h.vl_ano="+pAno+" group by vl_mes order by i.cd_devedor,h.vl_ano,i.vl_mes"; //by nm_nome_devedor,h.vl_ano,i.vl_mes
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<HistoricoRecebimento>();
                do {
                	objHistRec = new HistoricoRecebimento();

                	objHistRec.setRecibo(rs.getInt(1));
                	//rs.getInt(2) = codigo do Núcleo
                	objHistRec.setAno(rs.getInt(3));
                	
                	Calendar dataCalendar = new GregorianCalendar(); 
    				dataCalendar.setTime(rs.getDate(4));
    				objHistRec.setData(dataCalendar);
    				
    				objHistRec.setCpfOperador(rs.getLong(5));
                	
                	Socio objSocioPagador = new Socio();
                	objSocioPagador.setCodigo(rs.getInt(6));
                	//SocioDAO cSocPagDAO = new SocioDAO();
                	//objSocioPagador = cSocPagDAO.consultarSoc(objSocioPagador);
                	objHistRec.setSocioPagador(objSocioPagador);
                	
                	objHistRec.setExcluido(rs.getString(7));
                	//7 repete recibo
                	//8 observação
                	//9 repete Nucleo
                	//10 repete ano
                	objHistRec.setMes(rs.getInt(12));
                	
                	//DT_DATA(11) //do itens
                	Socio objSocioDevedor = new Socio();
                	objSocioDevedor.setCodigo(rs.getInt(14)); 
                	SocioDAO cSocDevDAO = new SocioDAO();
                	objSocioDevedor = cSocDevDAO.consultarSocio(objSocioDevedor, pHistRec.getNucleo());
                	objHistRec.setSocioDevedor(objSocioDevedor);
                	//NM_NOME_DEVEDOR(15)

                	objHistRec.setFlSocio(rs.getString(16)); //Socio <S/N>

                	TipoEntrada objTipoEntrada = new TipoEntrada();
                	objTipoEntrada.setCodigo(rs.getInt(17));
                	objTipoEntrada.setNucleo(pHistRec.getNucleo());
                	
                	objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);
                	objHistRec.setEntrada(objTipoEntrada);

                	objHistRec.setValor(rs.getDouble(18));
                	objHistRec.setQuantidade(rs.getInt(19));
                    
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
	
	public static ArrayList<HistoricoRecebimento> listarHistoricoOrdenadoPorGrau(Nucleo pNucleo, Socio pSocHist, int pTPag, int pAno) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<HistoricoRecebimento> listTodas = null;
            
            HistoricoRecebimento objHistRec;

            String SQL="select h.cd_recibo,h.vl_ano,h.dt_data,h.cpf_operador,h.cd_pagador,"
            	+" h.fl_excluido,i.vl_mes,i.cd_devedor,i.fl_socio,i.cd_tipo_entrada,i.vl_valor"
                +" from tb_socio as s, tb_historico_recebimento as h,tb_itens_historico_recebimento as i" 
            	+" where h.cd_recibo=i.cd_recibo and year(h.dt_data)=year(i.dt_data) and h.cd_nucleo=i.cd_nucleo and s.nm_situacao='A'"
                +" and i.cd_devedor=s.cd_socio and h.fl_excluido='N' and h.vl_ano="+pAno 
                +" and i.fl_socio='S' and i.cd_nucleo="+pNucleo.getCodigo();
            
            if(pTPag!=0){
            	SQL=SQL+" and i.cd_tipo_entrada="+pTPag;
            }else{
            	SQL=SQL+" and i.cd_tipo_entrada in (1,2)";
            }
            
            if(pSocHist!=null){
            	SQL=SQL+" and i.cd_devedor="+pSocHist.getCodigo();
            }            	
            SQL=SQL+" order by cd_grau<>'M',cd_grau,cd_devedor,i.vl_ano,i.vl_mes";

            //System.out.println(SQL);

            conn = StuConecta.getConnection( );
			
			Socio auxiliarObjDevedor = new Socio(); 
			TipoEntrada auxiliarObjTipoEntrada = new TipoEntrada(); 
			
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
                	objHistRec.setSocioPagador(objSocioPagador);
                	
                	objHistRec.setExcluido(rs.getString(6));
                	objHistRec.setMes(rs.getInt(7));
                	Socio objSocioDevedor = new Socio();
                	objSocioDevedor.setCodigo(rs.getInt(8));
                	
                	if(auxiliarObjDevedor.getCodigo() != objSocioDevedor.getCodigo()){
                		SocioDAO cSocDevDAO = new SocioDAO();
                		objSocioDevedor = cSocDevDAO.consultarSocio(objSocioDevedor,null); //pNucleo);
                		auxiliarObjDevedor = objSocioDevedor;
                	}else{
                		objSocioDevedor = auxiliarObjDevedor;
                	}
                	
                	//Verificar a atribuição de objetos. Refatorar código
                	
                	objHistRec.setSocioDevedor(objSocioDevedor);
                	
                	objHistRec.setFlSocio(rs.getString(9)); //Socio <S/N>
                	
                	TipoEntrada objTipoEntrada = new TipoEntrada();
                	objTipoEntrada.setCodigo(rs.getInt(10));

                	objTipoEntrada.setNucleo(pNucleo);
                	
                	if(auxiliarObjTipoEntrada.getCodigo() != objTipoEntrada.getCodigo()){
                		objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);
                		auxiliarObjTipoEntrada = objTipoEntrada;
                	}else{
                		objTipoEntrada = auxiliarObjTipoEntrada;
                	}
                	
                	objHistRec.setEntrada(objTipoEntrada);
                	objHistRec.setValor(rs.getDouble(11));

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
	//-----------------------------------------------------------------------------------
	public static ArrayList<DebitoSocio> listarDebitoSocio(Nucleo pNucleo, Socio pSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<DebitoSocio> listTodas = null;
            
            DebitoSocio objSocioDebito;

            String SQL="select distinct cd_socio,nm_nome,cd_grau,mensal,outros  from "+
            " (select a.cd_socio,a.nm_nome,a.cd_grau,mensal,outros from"+
            " (select cd_socio,nm_nome,cd_grau,cd_tipo_entrada,sum(socio) mensal from"+
            " ((select s.cd_socio,s.nm_nome,s.cd_grau,e.cd_tipo_entrada,v.vl_valor socio from"+
            
            " tb_tipo_entrada_nucleo e, tb_socio_valor v, tb_socio s, tb_socio_nucleo n " +
            " where e.nm_mensal='S' and e.cd_nucleo="+pNucleo.getCodigo()+
            " and e.cd_tipo_entrada=v.cd_tipo_entrada and s.cd_socio=v.cd_socio and s.nm_situacao='A' and e.vl_valor<>v.vl_valor"+
            " and s.cd_socio=n.cd_socio and n.cd_nucleo=e.cd_nucleo"+
            
            " union (select * from (select s.cd_socio,s.nm_nome,s.cd_grau,e.cd_tipo_entrada,e.vl_valor mensal from"+
            " tb_socio s, tb_tipo_entrada_nucleo e, tb_socio_nucleo n where s.nm_situacao='A' and e.cd_nucleo="+pNucleo.getCodigo()+
            " and nm_mensal='S' and n.cd_nucleo="+pNucleo.getCodigo()+" and n.cd_socio=s.cd_socio group by "+							
            " cd_socio,cd_tipo_entrada) sub_consulta1 where (cd_socio,cd_tipo_entrada) not in (select s.cd_socio,e.cd_tipo_entrada from"+
            " tb_tipo_entrada_nucleo e, tb_socio_valor v, tb_socio s where nm_mensal='S' and e.cd_nucleo="+pNucleo.getCodigo()+" and s.nm_situacao='A'"+
            " and e.cd_tipo_entrada=v.cd_tipo_entrada and s.cd_socio=v.cd_socio and e.vl_valor<>v.vl_valor))"+
            " order by cd_grau<>'M',cd_grau,nm_nome,cd_tipo_entrada)) sub_consulta2 group by cd_socio) a left join"+
            
            " (select s.cd_socio,s.nm_nome,s.cd_grau,sum(i.vl_valor-i.vl_pago) Outros from tb_socio s, tb_lista_arrecadacao l, tb_itens_lista_arrecadacao i, tb_socio_nucleo n"+
            " where s.nm_situacao='A' and s.cd_socio=i.cd_socio and (i.vl_valor-i.vl_pago)>0 and n.cd_nucleo="+pNucleo.getCodigo()+" and n.cd_socio=s.cd_socio"+
            " and l.cd_lista=i.cd_lista and l.vl_ano=i.vl_ano and l.cd_nucleo=i.cd_nucleo group by cd_socio) b on a.cd_socio=b.cd_socio"+
            " where a.cd_socio is not null) aa left join (select cd_devedor,cd_nucleo,fl_socio from tb_itens_historico_recebimento) bb"+
            " on aa.cd_socio=bb.cd_devedor where bb.cd_nucleo="+pNucleo.getCodigo()+" and bb.fl_socio='S'";
            
            if(pSoc!=null){
            	SQL=SQL+" and aa.cd_socio"+pSoc.getCodigo();
            }

            SQL=SQL+" order by cd_grau<>'M',cd_grau,nm_nome";

            //System.out.println(SQL);
            
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<DebitoSocio>();
                do {
                	objSocioDebito = new DebitoSocio();
                	
                	Socio objSocioDevedor = new Socio();
                	objSocioDevedor.setCodigo(rs.getInt(1));
                	
                	SocioDAO cSocDevDAO = new SocioDAO();
                	objSocioDevedor = cSocDevDAO.consultarSocio(objSocioDevedor, pNucleo);

                	objSocioDebito.setSocio(objSocioDevedor);
                	objSocioDebito.setValormensal(rs.getDouble(4));
                	objSocioDebito.setValoroutros(rs.getDouble(5));
                    listTodas.add(objSocioDebito);
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
	//-----------------------------------------------------------------------------------
	public static ArrayList<HistoricoRecebimento> listarHistoricoRecebimento(int pRecibo, Nucleo pNucleo, int pAno, int pCancelado) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<HistoricoRecebimento> listTodas = null;
            
            HistoricoRecebimento objHistRec;

            String SQL="select * from tb_historico_recebimento as h,tb_itens_historico_recebimento as i"
            	+" where h.cd_recibo=i.cd_recibo and year(h.dt_data)=year(i.dt_data) and i.cd_recibo="+pRecibo
            	+" and year(h.dt_data)="+pAno+" and h.cd_nucleo=i.cd_nucleo and h.cd_nucleo="+pNucleo.getCodigo();
            	if (pCancelado==1){
            		SQL = SQL+" and fl_excluido='N'"; 
            	}
            	SQL =SQL+" order by i.cd_devedor,i.vl_ano desc,i.cd_tipo_entrada,i.vl_mes"; 

            	//System.out.println(SQL);
            	
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<HistoricoRecebimento>();
                do {
                	objHistRec = new HistoricoRecebimento();

                	objHistRec.setRecibo(rs.getInt(1));
                	
					Nucleo objNucleo = new Nucleo();
					objNucleo.setCodigo(rs.getInt(2));
					
					objHistRec.setNucleo(objNucleo);
                	
                	objHistRec.setAno(rs.getInt(11)); //10 porque o ano na coluna 3 é único para todas as linhas 
                	
    				Calendar dataCalendar = new GregorianCalendar(); 
    				dataCalendar.setTime(rs.getDate(4));
    				
    				objHistRec.setData(dataCalendar);
                	objHistRec.setCpfOperador(rs.getLong(5));
                	
                	Socio objSocioPagador = new Socio();
                	objSocioPagador.setCodigo(rs.getInt(6));
                	//SocioDAO cSocPagDAO = new SocioDAO();
                	//objSocioPagador = cSocPagDAO.consultarSoc(objSocioPagador);
                	objHistRec.setSocioPagador(objSocioPagador);
                	
                	objHistRec.setExcluido(rs.getString(7));
                	objHistRec.setObservacao(rs.getString(8));
                	
                	//7 repete recibo
                	//8 ano, já utilizado.
                	objHistRec.setMes(rs.getInt(12));
                	
                	Socio objSocioDevedor = new Socio();
                	objSocioDevedor.setCodigo(rs.getInt(14));
                	
                	//SocioDAO cSocDevDAO = new SocioDAO();
                	//objSocioDevedor = cSocDevDAO.consultarSoc(objSocioDevedor);
                	objHistRec.setSocioDevedor(objSocioDevedor);
                	//DT_DATA(10) //do itens
                	//NM_NOME_DEVEDOR(13)

                	objHistRec.setFlSocio(rs.getString(16)); //Socio <S/N>

                	TipoEntrada objTipoEntrada = new TipoEntrada();
                	objTipoEntrada.setCodigo(rs.getInt(17));
                	objTipoEntrada.setNucleo(pNucleo);
                	
                	objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);
                	objHistRec.setEntrada(objTipoEntrada);

                	objHistRec.setValor(rs.getDouble(18));
                    
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
	//-----------------------------------------------------------------------------------
	public static String listarReciboReserva(int pRecibo, Nucleo pNucleo, int pAno) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            
           	String SQL="select * from tb_historico_recebimento where cd_pagador=0 and cd_recibo="+pRecibo
            	+" and year(dt_data)="+pAno+" and cd_nucleo="+pNucleo.getCodigo();

            	//System.out.println(SQL);
            	
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(7);
            }
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return "0";
	}

	//-----------------------------------------------------------------------------------
	public static ArrayList<HistoricoDevedor> listarHistoricoDevedor(Nucleo pNucleo) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			ArrayList<HistoricoDevedor> listTodas = null;

			HistoricoDevedor objHistoricoDevedor;

			String SQL="select b.cd_nucleo,b.cd_recibo,b.dt_data,b.cpf_operador,b.cd_pagador,b.cd_devedor,b.fl_socio,"
					+" a.nm_nome,a.cd_grau,b.vl_ano,b.vl_mes,b.cd_tipo_entrada,b.vl_valor from"
					+" (select sn.cd_nucleo,s.cd_socio,s.nm_nome,s.cd_grau"
					+" from tb_socio as s, tb_socio_nucleo as sn where sn.CD_SOCIO=s.CD_SOCIO"
					+" and s.nm_situacao<>'O' and cd_nucleo="+pNucleo.getCodigo()+") a,"
					+"(select h.cd_nucleo,h.cd_recibo,h.dt_data,h.cpf_operador,h.cd_pagador,i.cd_devedor,i.fl_socio,"
					+" i.nm_nome_devedor,h.vl_ano,i.vl_mes,i.cd_tipo_entrada,i.vl_valor "
					+" from tb_historico_recebimento as h, tb_itens_historico_recebimento as i" 
					+" where h.cd_nucleo=i.cd_nucleo and h.cd_recibo=i.cd_recibo and year(h.dt_data)=year(i.dt_data)"
					+" and h.vl_ano>year(sysdate())-2 and h.fl_excluido='N' and h.cd_nucleo="+pNucleo.getCodigo()+") b"
					+" where a.cd_nucleo=b.cd_nucleo and a.cd_socio=b.cd_devedor"
					+" order by cd_devedor,vl_ano desc,vl_mes,cd_tipo_entrada";

			//System.out.println(SQL);

			conn = StuConecta.getConnection( );

			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {

				//listTodas = new ArrayList<HistoricoDevedor>();
				listTodas = new ArrayList<HistoricoDevedor>();

				do {

					objHistoricoDevedor = new HistoricoDevedor();
					Nucleo objNucleo = new Nucleo();
				
					objHistoricoDevedor.setNucleo(rs.getInt("cd_nucleo"));
					
					objHistoricoDevedor.setRecibo(rs.getInt("cd_recibo"));
					objHistoricoDevedor.setData(rs.getDate("dt_data"));
					objHistoricoDevedor.setCpfOperador(rs.getLong("cpf_operador"));
                	objHistoricoDevedor.setSocioPagador(rs.getInt("cd_pagador"));

                	Socio objSocioDevedor = new Socio();
                	objSocioDevedor.setCodigo(rs.getInt("cd_devedor"));
                	objSocioDevedor.setNome(rs.getString("nm_nome"));
                	objSocioDevedor.setGrau(rs.getString("cd_grau"));
                	objHistoricoDevedor.setSocioDevedor(objSocioDevedor);
                	
					objHistoricoDevedor.setFlSocio(rs.getString("fl_socio"));
					objHistoricoDevedor.setAno(rs.getInt("vl_ano"));
					objHistoricoDevedor.setMes(rs.getInt("vl_mes"));
					
                	TipoEntrada objTipoEntrada = new TipoEntrada();
                	objTipoEntrada.setCodigo(rs.getInt("cd_tipo_entrada"));
                	objNucleo.setCodigo(rs.getInt("cd_nucleo"));
                	objTipoEntrada.setNucleo(objNucleo);
                	objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);
					objHistoricoDevedor.setEntrada(objTipoEntrada);

					objHistoricoDevedor.setValor(rs.getDouble("vl_valor"));

					listTodas.add(objHistoricoDevedor);
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

	public static int totalRecibosSomenteFPSG(int pMes, int pAno, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;			//Método comum para consulta balancete e movimento de entrada
		Connection conn = null;
		ResultSet rs = null;
		try {

			String SQL="select count(distinct h.cd_recibo) from tb_historico_recebimento as h,tb_itens_historico_recebimento as i"
					+" where h.cd_recibo=i.cd_recibo and year(h.dt_data)=year(i.dt_data) and month(h.dt_data)="+pMes
					+" and year(h.dt_data)="+pAno+" and h.cd_nucleo=i.cd_nucleo and i.cd_tipo_entrada=2 and h.fl_excluido<>'S' and h.cd_nucleo="+pNucleo.getCodigo()
					+" and h.cd_recibo not in (select distinct h.cd_recibo from tb_historico_recebimento as h,tb_itens_historico_recebimento as i"
					+" where h.cd_recibo=i.cd_recibo and year(h.dt_data)=year(i.dt_data) and month(h.dt_data)="+pMes
					+" and year(h.dt_data)="+pAno+" and h.cd_nucleo=i.cd_nucleo"
					+" and i.cd_tipo_entrada=1 and h.fl_excluido<>'S' and h.cd_nucleo="+pNucleo.getCodigo()+")";
		
            //System.out.println(SQL);
            
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
            }
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return 0;
	}

	//-------------------------------
	public static ArrayList<HistoricoRecebimento> listarHistoricoEntradaReciboReserva(ArrayList<HistoricoRecebimento> listRecebida, int pMes, int pAno, int pOrd, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;			//Método comum para consulta balancete e movimento de entrada
		Connection conn = null;
		ResultSet rs = null;
		try {

			ArrayList<HistoricoRecebimento> listTodas = null;
            HistoricoRecebimento objHistRec;

            Nucleo objHistNucleo = new Nucleo();

			NucleoDAO objNucleoDAO = new NucleoDAO();
			objHistNucleo = objNucleoDAO.consultarNucleo(pNucleo.getCodigo());

			String SQL="select * from tb_historico_recebimento where month(dt_data)="+pMes
					+" and year(dt_data)="+pAno+" and cd_pagador=0 and cd_nucleo="+pNucleo.getCodigo()
					+" order by cd_recibo,cd_pagador";

            //System.out.println(SQL);
            
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			listTodas = listRecebida;

			if (rs.next()) {
                do {

                	objHistRec = new HistoricoRecebimento();

                	objHistRec.setRecibo(rs.getInt(1));
            
					objHistRec.setNucleo(objHistNucleo);
                	
					objHistRec.setAno(rs.getInt(3)); 
                	
					Calendar dataCalendar = new GregorianCalendar(); 
    				dataCalendar.setTime(rs.getDate(4)); 
    				objHistRec.setData(dataCalendar);
                	
                	objHistRec.setCpfOperador(rs.getLong(5)); 
                	
                	Socio objSocioPagador = new Socio();
                	objSocioPagador.setCodigo(rs.getInt(6)); 

                	//Consultar o nome do sócio/não sócio
            		if(objSocioPagador.getCodigo()==0){
            			objSocioPagador.setNome("");
            		}else{
            			if(rs.getString(15).equalsIgnoreCase("N")){ 
            				NaoSocio objNaoSocPagador = new NaoSocio();
            				objNaoSocPagador.setCodigo(rs.getInt(6)); 
            				objNaoSocPagador.setRegiao(objHistNucleo.getRegiao()); 

            				NaoSocioDAO naoSocDevDAO = new NaoSocioDAO();
            				
            				objNaoSocPagador = naoSocDevDAO.consultarNaoSoc(objNaoSocPagador,objHistNucleo.getRegiao().getCodigo());
            				
            				objSocioPagador.setNome(objNaoSocPagador!=null?objNaoSocPagador.getNome():"Não Sócio não encontrado");
            				
            			}else{
            				SocioDAO socDevDAO = new SocioDAO();
            				objSocioPagador = socDevDAO.consultarNomeSocio(objSocioPagador);
            			}
            		}
                	objHistRec.setSocioPagador(objSocioPagador);

                	objHistRec.setExcluido(rs.getString(7)); // <S/N> 

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
	//-------------------------------
	public static ArrayList<HistoricoRecebimento> listarHistoricoEntradaTipo(int pMesInicial, int pMesFinal, int pAno, TipoEntrada pTipo, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {

			ArrayList<HistoricoRecebimento> listTodas = null;
            HistoricoRecebimento objHistRec;

            Nucleo objHistNucleo = new Nucleo();

			NucleoDAO objNucleoDAO = new NucleoDAO();
			objHistNucleo = objNucleoDAO.consultarNucleo(pNucleo.getCodigo());

            String SQL="select * from tb_historico_recebimento as h,tb_itens_historico_recebimento as i"
            	+" where h.cd_recibo=i.cd_recibo and year(h.dt_data)=year(i.dt_data) and month(h.dt_data) between "+pMesInicial+" and "+pMesFinal
            	+" and year(h.dt_data)="+pAno+" and h.cd_nucleo=i.cd_nucleo and h.cd_nucleo="+pNucleo.getCodigo()+" and i.cd_tipo_entrada="+pTipo.getCodigo()
            	+" and fl_excluido<>'S' order by h.cd_recibo,i.cd_tipo_entrada,i.cd_devedor";

            //System.out.println(SQL);
            
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {

				listTodas = new ArrayList<HistoricoRecebimento>();

                do {

                	objHistRec = new HistoricoRecebimento();

                	objHistRec.setRecibo(rs.getInt(1));
            
					objHistRec.setNucleo(objHistNucleo);
                	
					objHistRec.setAno(rs.getInt(3)); 
                	
					Calendar dataCalendar = new GregorianCalendar(); 
    				dataCalendar.setTime(rs.getDate(4)); 
    				
    				objHistRec.setData(dataCalendar);
                	
                	objHistRec.setCpfOperador(rs.getLong(5)); 
                	
                	Socio objSocioPagador = new Socio();
                	objSocioPagador.setCodigo(rs.getInt(6)); 
                	
                	objHistRec.setExcluido(rs.getString(7)); // <S/N> 

                	//9 repete recibo
                	//11 repete ano
                	
                	objHistRec.setMes(rs.getInt(12)); 
                	
                	Socio objSocioDevedor = new Socio();
                	objSocioDevedor.setCodigo(rs.getInt(14)); 

                	//Consultar o nome do sócio/não sócio
                	//objSocioPagador.setNome("");
                	objSocioDevedor.setNome("");
            		if(objSocioPagador.getCodigo()!=0||objSocioDevedor.getCodigo()!=0){
            			if(rs.getString(16).equalsIgnoreCase("N")){ 

            				NaoSocio objNaoSocDevedor = new NaoSocio();
            				objNaoSocDevedor.setCodigo(rs.getInt(6)); 
            				objNaoSocDevedor.setRegiao(objHistNucleo.getRegiao()); 

            				NaoSocioDAO naoSocDevDAO = new NaoSocioDAO();
            				
            				objNaoSocDevedor = naoSocDevDAO.consultarNaoSoc(objNaoSocDevedor,objHistNucleo.getRegiao().getCodigo());
            				objSocioDevedor.setNome(objNaoSocDevedor!=null?objNaoSocDevedor.getNome():"Não Sócio não encontrado");
            				
            			}else{
            				SocioDAO socDevDAO = new SocioDAO();
            				//objSocioPagador = socDevDAO.consultarNomeSocio(objSocioPagador);
            				objSocioDevedor = socDevDAO.consultarNomeSocio(objSocioDevedor);
            			}
            		}

            		//objHistRec.setSocioPagador(objSocioPagador);
                	objHistRec.setSocioDevedor(objSocioDevedor);
                	//12 data do itens
                	//14 nome pagador
                	
                	objHistRec.setFlSocio(rs.getString(16)); //Socio <S/N>

                	TipoEntrada objTipoEntrada = new TipoEntrada();
                	objTipoEntrada.setCodigo(rs.getInt(17));
                	objTipoEntrada.setNucleo(pNucleo);
                	
                	objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);
                	
                	objHistRec.setEntrada(objTipoEntrada);
                	objHistRec.setValor(rs.getDouble(18));

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

	//-------------------------------
	public static ArrayList<HistoricoRecebimento> listarHistoricoEntradaSocio(int pMesInicial, int pMesFinal, int pAno, Socio pSocio, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {

			ArrayList<HistoricoRecebimento> listTodas = null;
            HistoricoRecebimento objHistRec;

            Nucleo objHistNucleo = new Nucleo();

			NucleoDAO objNucleoDAO = new NucleoDAO();
			objHistNucleo = objNucleoDAO.consultarNucleo(pNucleo.getCodigo());

            String SQL="select * from tb_historico_recebimento as h,tb_itens_historico_recebimento as i"
            	+" where h.cd_recibo=i.cd_recibo and year(h.dt_data)=year(i.dt_data) and month(h.dt_data) between "+pMesInicial+" and "+pMesFinal
            	+" and year(h.dt_data)="+pAno+" and h.cd_nucleo=i.cd_nucleo and h.cd_nucleo="+pNucleo.getCodigo()+" and i.cd_devedor="+pSocio.getCodigo()
            	+" order by h.cd_recibo,i.cd_tipo_entrada,i.cd_devedor";

            //System.out.println(SQL);
            
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {

				listTodas = new ArrayList<HistoricoRecebimento>();

                do {

                	objHistRec = new HistoricoRecebimento();

                	objHistRec.setRecibo(rs.getInt(1));
            
					objHistRec.setNucleo(objHistNucleo);
                	
					objHistRec.setAno(rs.getInt(3)); 
                	
					Calendar dataCalendar = new GregorianCalendar(); 
    				dataCalendar.setTime(rs.getDate(4)); 
    				
    				objHistRec.setData(dataCalendar);
                	
                	objHistRec.setCpfOperador(rs.getLong(5)); 
                	
                	Socio objSocioPagador = new Socio();
                	objSocioPagador.setCodigo(rs.getInt(6)); 
                	
                	objHistRec.setExcluido(rs.getString(7)); // <S/N> 

                	//9 repete recibo
                	//11 repete ano
                	
                	objHistRec.setMes(rs.getInt(12)); 
                	
                	Socio objSocioDevedor = new Socio();
                	objSocioDevedor.setCodigo(rs.getInt(14)); 

                	//Consultar o nome do sócio/não sócio
                	//objSocioPagador.setNome("");
                	objSocioDevedor.setNome("");
            		if(objSocioPagador.getCodigo()!=0||objSocioDevedor.getCodigo()!=0){
            			if(rs.getString(16).equalsIgnoreCase("N")){ 

            				NaoSocio objNaoSocDevedor = new NaoSocio();
            				objNaoSocDevedor.setCodigo(rs.getInt(6)); 
            				objNaoSocDevedor.setRegiao(objHistNucleo.getRegiao()); 

            				NaoSocioDAO naoSocDevDAO = new NaoSocioDAO();
            				
            				objNaoSocDevedor = naoSocDevDAO.consultarNaoSoc(objNaoSocDevedor,objHistNucleo.getRegiao().getCodigo());
            				objSocioDevedor.setNome(objNaoSocDevedor!=null?objNaoSocDevedor.getNome():"Não Sócio não encontrado");
            				
            			}else{
            				SocioDAO socDevDAO = new SocioDAO();
            				//objSocioPagador = socDevDAO.consultarNomeSocio(objSocioPagador);
            				objSocioDevedor = socDevDAO.consultarNomeSocio(objSocioDevedor);
            			}
            		}

            		//objHistRec.setSocioPagador(objSocioPagador);
                	objHistRec.setSocioDevedor(objSocioDevedor);
                	//12 data do itens
                	//14 nome pagador
                	
                	objHistRec.setFlSocio(rs.getString(16)); //Socio <S/N>

                	TipoEntrada objTipoEntrada = new TipoEntrada();
                	objTipoEntrada.setCodigo(rs.getInt(17));
                	objTipoEntrada.setNucleo(pNucleo);
                	
                	objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);
                	
                	objHistRec.setEntrada(objTipoEntrada);
                	objHistRec.setValor(rs.getDouble(18));

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
	//-------------------------------

	public static ArrayList<HistoricoRecebimento> listarHistoricoEntrada(int pMes, int pAno, int pOrd, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;			//Método comum para consulta balancete e movimento de entrada
		Connection conn = null;
		ResultSet rs = null;
		try {

			ArrayList<HistoricoRecebimento> listTodas = null;
            HistoricoRecebimento objHistRec;

            Nucleo objHistNucleo = new Nucleo();

			NucleoDAO objNucleoDAO = new NucleoDAO();
			objHistNucleo = objNucleoDAO.consultarNucleo(pNucleo.getCodigo());

            String SQL="select * from tb_historico_recebimento as h,tb_itens_historico_recebimento as i"
            	+" where h.cd_recibo=i.cd_recibo and year(h.dt_data)=year(i.dt_data) and month(h.dt_data)="+pMes
            	+" and year(h.dt_data)="+pAno+" and h.cd_nucleo=i.cd_nucleo and h.cd_nucleo="+pNucleo.getCodigo();
            
            if(pOrd == 1){
            	SQL = SQL+" order by h.cd_recibo,i.cd_tipo_entrada,i.cd_devedor";
            }else{
            	SQL = SQL+" order by i.cd_tipo_entrada,h.cd_recibo, i.cd_devedor";
            }

            //System.out.println(SQL);
            
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {

				listTodas = new ArrayList<HistoricoRecebimento>();

                do {

                	objHistRec = new HistoricoRecebimento();

                	objHistRec.setRecibo(rs.getInt(1));
            
					objHistRec.setNucleo(objHistNucleo);
                	
					objHistRec.setAno(rs.getInt(3)); 
                	
					Calendar dataCalendar = new GregorianCalendar(); 
    				dataCalendar.setTime(rs.getDate(4)); 
    				
    				objHistRec.setData(dataCalendar);
                	
                	objHistRec.setCpfOperador(rs.getLong(5)); 
                	
                	Socio objSocioPagador = new Socio();
                	objSocioPagador.setCodigo(rs.getInt(6)); 

                	//Consultar o nome do sócio/não sócio
            		if(objSocioPagador.getCodigo()==0){
            			objSocioPagador.setNome("");
            		}else{
            			if(rs.getString(16).equalsIgnoreCase("N")){ 
            				NaoSocio objNaoSocPagador = new NaoSocio();
            				objNaoSocPagador.setCodigo(rs.getInt(6)); 
            				objNaoSocPagador.setRegiao(objHistNucleo.getRegiao()); 

            				NaoSocioDAO naoSocDevDAO = new NaoSocioDAO();
            				
            				objNaoSocPagador = naoSocDevDAO.consultarNaoSoc(objNaoSocPagador,objHistNucleo.getRegiao().getCodigo());
            				
            				objSocioPagador.setNome(objNaoSocPagador!=null?objNaoSocPagador.getNome():"Não Sócio não encontrado");
            				
            			}else{
            				SocioDAO socDevDAO = new SocioDAO();
            				objSocioPagador = socDevDAO.consultarNomeSocio(objSocioPagador);
            			}
            		}
                	
                	objHistRec.setSocioPagador(objSocioPagador);
                	objHistRec.setExcluido(rs.getString(7)); // <S/N> 

                	//8 repete recibo
                	//10 repete ano
                	
                	objHistRec.setMes(rs.getInt(12)); 
                	
                	Socio objSocioDevedor = new Socio();
                	objSocioDevedor.setCodigo(rs.getInt(14)); 
                	//SocioDAO cSocDevDAO = new SocioDAO();
                	//objSocioDevedor = cSocDevDAO.consultarSoc(objSocioDevedor);
                	
                	objHistRec.setSocioDevedor(objSocioDevedor);
                	//11 data do itens
                	//13 nome pagador
                	
                	objHistRec.setFlSocio(rs.getString(16)); //Socio <S/N>

                	TipoEntrada objTipoEntrada = new TipoEntrada();
                	objTipoEntrada.setCodigo(rs.getInt(17));
                	objTipoEntrada.setNucleo(pNucleo);
                	
                	objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);
                	
                	objHistRec.setEntrada(objTipoEntrada);
                	objHistRec.setValor(rs.getDouble(18));

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
	
	public ArrayList<HistoricoRecebimento> listarHistoricoPeriodo(HistoricoRecebimento pEntradaInicial, HistoricoRecebimento pEntradaFinal) throws StuDAOException{
		PreparedStatement ps = null;			
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<HistoricoRecebimento> listTodas = null;
            
            HistoricoRecebimento objHistRec;

    		String datainicial = new SimpleDateFormat("yyyy-MM-dd").format(pEntradaInicial.getData().getTime());
    		String datafinal   = new SimpleDateFormat("yyyy-MM-dd").format(pEntradaFinal.getData().getTime());
    		
            String SQL="select cd_tipo_entrada,sum(vl_valor) vl_valor from tb_historico_recebimento as h,tb_itens_historico_recebimento as i"
            	+" where h.cd_recibo=i.cd_recibo and year(h.dt_data)=year(i.dt_data) and h.dt_data between date('"+datainicial+"') and date('"+datafinal+"')"
            	+" and fl_excluido='N' and h.cd_nucleo=i.cd_nucleo and h.cd_nucleo="+pEntradaInicial.getNucleo().getCodigo() 
            	+" group by cd_tipo_entrada order by i.cd_tipo_entrada,h.cd_recibo, i.cd_devedor";

            //System.out.println(SQL);
            
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<HistoricoRecebimento>();
                do {
                	objHistRec = new HistoricoRecebimento();

                	TipoEntrada objTipoEntrada = new TipoEntrada();
                	objTipoEntrada.setCodigo(rs.getInt(1));
                	objTipoEntrada.setNucleo(pEntradaInicial.getNucleo());
                	
                	objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);
                	
                	objHistRec.setEntrada(objTipoEntrada);
                	objHistRec.setValor(rs.getDouble(2));
                	
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

	public ArrayList<HistoricoRecebimento> listarHistoricoEntradaAnual(int pAno, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<HistoricoRecebimento> listTodas = null;
            
            HistoricoRecebimento objHistRec;
            
            String SQL="select i.cd_tipo_entrada,month(e.dt_data),sum(i.vl_valor) from tb_historico_recebimento e, tb_itens_historico_recebimento i"
            	+" where year(e.dt_data)="+pAno+" and e.cd_recibo=i.cd_recibo and year(e.dt_data)=year(i.dt_data)"
            	+" and e.cd_nucleo=i.cd_nucleo and e.cd_nucleo="+pNucleo.getCodigo()+" and fl_excluido='N'" 
            	+" group by i.cd_tipo_entrada,month(e.dt_data) order by i.cd_tipo_entrada,e.dt_data";
            
            //System.out.println(SQL);
            
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<HistoricoRecebimento>();
                do {
                	objHistRec = new HistoricoRecebimento();
                	
                	TipoEntrada objTipoEntrada = new TipoEntrada();
                	objTipoEntrada.setCodigo(rs.getInt(1));
                	objTipoEntrada.setNucleo(pNucleo);
                	
                	objTipoEntrada = TipoEntradaDAO.consultarTipoEntrada(objTipoEntrada);
                	
                	if(objTipoEntrada==null){
                		objTipoEntrada = new TipoEntrada();
                		objTipoEntrada.setCodigo(rs.getInt(1));
                		objTipoEntrada.setDescricao("Tipo Excluído");
                		objTipoEntrada.setValor(0.0);
                	}
                	
                	objHistRec.setEntrada(objTipoEntrada);	//1
                	objHistRec.setMes(rs.getInt(2)); 		//2
                	objHistRec.setValor(rs.getDouble(3));	//3

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
	

	public static boolean VerificaEntradaHistorico(TipoEntrada pEntrada) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {

			String SQL = "select *from tb_itens_historico_recebimento where cd_nucleo="+pEntrada.getNucleo().getCodigo()+" and cd_tipo_entrada="+pEntrada.getCodigo();

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

