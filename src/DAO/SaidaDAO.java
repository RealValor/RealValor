package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import logica.StuConecta;
import beans.NaoSocio;
import beans.Nucleo;
import beans.Saida;
import beans.Socio;
import beans.TipoDocumento;
import beans.TipoSaida;

public class SaidaDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public Saida consultarSaida(Saida pSaida) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_saida where cd_nucleo="+pSaida.getNucleo().getCodigo();
			if(pSaida.getNumero()!=0&&pSaida.getAno()!=0){
				SQL = "select * from tb_saida where cd_saida="+pSaida.getNumero()+" and vl_ano="+pSaida.getAno()+" and cd_nucleo="+pSaida.getNucleo().getCodigo();
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				Saida objSaida = new Saida();
				
				objSaida.setNumero(rs.getInt(1));
					
				Nucleo objNucleo = new Nucleo();				
				NucleoDAO objNucleoDAO = new NucleoDAO();
				
				objNucleo = objNucleoDAO.consultarNucleo(rs.getInt(2));
				
				objSaida.setNucleo(objNucleo);
				
				objSaida.setAno(rs.getInt(3));
				objSaida.setMes(rs.getInt(4));

				TipoSaida objTipSai = new TipoSaida();
				objTipSai.setCodigo(rs.getInt(5));
				objTipSai.setNucleo(objNucleo);
				
				objTipSai = TipoSaidaDAO.consultarTipoSaida(objTipSai);

				objSaida.setSaida(objTipSai);
				
				Calendar tipoCalendar = Calendar.getInstance();				
				tipoCalendar.setTime(rs.getDate(6));
				objSaida.setData(tipoCalendar);

				objSaida.setCpfOperador(rs.getLong(7));

				TipoDocumento objTipDoc = new TipoDocumento();
				objTipDoc.setCodigo(rs.getInt(8));
				objTipDoc = TipoDocumentoDAO.consultarTipoDocumento(objTipDoc);

				objSaida.setTipoDocumento(objTipDoc);
				objSaida.setDocumento(rs.getString(9));
				objSaida.setDataDocumento(rs.getDate(10));
				objSaida.setFornecedor(rs.getInt(11));
				objSaida.setFlSocio(rs.getString(12));
				objSaida.setValor(rs.getDouble(13));
				objSaida.setObservacao(rs.getString(14));
				objSaida.setFlEstornada(rs.getString(15));
				objSaida.setFlEstornada(rs.getString(16));

				Socio objSocio = new Socio();
				NaoSocio objNaoSocio = new NaoSocio();

				if(!rs.getString(12).equals("N")){
					objSocio.setCodigo(rs.getInt(11));
					SocioDAO objSocDAO = new SocioDAO();
					objSocio = objSocDAO.consultarSocio(objSocio, pSaida.getNucleo());
				}else{
					objNaoSocio.setCodigo(rs.getInt(11));
					NaoSocioDAO objSocDAO = new NaoSocioDAO();
					objNaoSocio = objSocDAO.consultarNaoSoc(objNaoSocio,objNucleo.getRegiao().getCodigo());
				}

				objSaida.setObjetoSocio(objSocio);
				objSaida.setObjetoNaoSocio(objNaoSocio);

				return objSaida;
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

	public int buscaNumeroSaida(int pAno, int pNucleo) throws StuDAOException{ //static 
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select geraNumeroSaida("+pAno+","+pNucleo+") from dual";
			
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

	public void incluirSaida(Saida pSaida) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "insert into tb_saida values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			
			ps.setInt(1, pSaida.getNumero());
			ps.setInt(2, pSaida.getNucleo().getCodigo());
			ps.setInt(3, pSaida.getAno());
			ps.setInt(4, pSaida.getMes());
			ps.setInt(5, pSaida.getSaida().getCodigo());
			
			ps.setDate(6, null);
			if(pSaida.getData()!=null){
				ps.setDate(6, new java.sql.Date(pSaida.getData().getTimeInMillis()) );
			}
			ps.setLong(7, pSaida.getCpfOperador());
			ps.setInt(8, pSaida.getTipoDocumento().getCodigo());
			ps.setString(9, pSaida.getDocumento());
			
			ps.setDate(10, null);
			if(pSaida.getDataDocumento()!=null){
				ps.setDate(10, new java.sql.Date(pSaida.getDataDocumento().getTime()) );
			}
			ps.setInt(11, pSaida.getFornecedor());
			ps.setString(12, pSaida.getFlSocio());
			ps.setDouble(13, pSaida.getValor());
			ps.setString(14, pSaida.getObservacao());
			ps.setString(15, pSaida.getFlEstornada());
			ps.setString(16, pSaida.getFlFechada());
			
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarSaida(Saida pSaida) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_saida set vl_mes=?,cd_tipo_saida=?,dt_lancamento=?,cpf_operador=?,cd_tipo_documento=?,"
					+" nm_documento=?,dt_documento=?,cd_fornecedor=?,nm_socio=?,vl_saida=?,nm_observacao=?,nm_estornada=?"
					+" where cd_saida="+pSaida.getNumero()+" and vl_ano="+pSaida.getAno()+" and cd_nucleo="+pSaida.getNucleo().getCodigo();

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);

			ps.setInt(1, pSaida.getMes());
			ps.setInt(2, pSaida.getSaida().getCodigo());
			
			if(pSaida.getData()!=null){
				ps.setDate(3, new java.sql.Date(pSaida.getData().getTimeInMillis()) );
			}
			ps.setLong(4, pSaida.getCpfOperador());
			ps.setInt(5, pSaida.getTipoDocumento().getCodigo());
			ps.setString(6, pSaida.getDocumento());
						
			if(pSaida.getDataDocumento()!=null){
				ps.setDate(7, new java.sql.Date(pSaida.getDataDocumento().getTime()) );
			}
			ps.setInt(8, pSaida.getFornecedor());
			ps.setString(9, pSaida.getFlSocio());
			ps.setDouble(10, pSaida.getValor());
			ps.setString(11, pSaida.getObservacao());
			ps.setString(12, pSaida.getFlEstornada());
			
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirSaida(Saida pSaida) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "delete from tb_saida where cd_saida="+pSaida.getNumero()+" and cd_nucleo="+pSaida.getNucleo().getCodigo();
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
	
	@SuppressWarnings("static-access")
	public static ArrayList<Saida> listarSaida(Saida pSaida) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			ArrayList<Saida> listTodas = null;
            Saida objSaida;

            String SQL="select * from tb_saida s, tb_tipo_saida_nucleo sn where vl_ano="+pSaida.getAno()+" and vl_mes="+pSaida.getMes()
            +" and s.cd_nucleo="+pSaida.getNucleo().getCodigo()+" and nm_estornada<>'S' and s.cd_tipo_saida=sn.cd_tipo_saida and s.cd_nucleo=sn.cd_nucleo";
			
            if(pSaida.getFornecedor()!=0){
				SQL = SQL+" and cd_fornecedor="+pSaida.getFornecedor();
			}

			if(pSaida.getDocumento()!=null&&!pSaida.getDocumento().equalsIgnoreCase("")){
				SQL = SQL+" and nm_documento="+pSaida.getDocumento();
			}

			if(pSaida.getValor()!=0){
				SQL = SQL+" and vl_saida="+pSaida.getValor();
			}
			SQL = SQL+" order by vl_ano,vl_mes,sn.nm_descricao,dt_documento";

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Saida>();
                do {
                    objSaida = new Saida();
    				
    				objSaida.setNumero(rs.getInt(1));
    				
    				NucleoDAO objNucleoDAO = new NucleoDAO();
    				objSaida.setNucleo(objNucleoDAO.consultarNucleo(rs.getInt(2)));

    				objSaida.setAno(rs.getInt(3));
    				objSaida.setMes(rs.getInt(4));
    				
    				TipoSaida objTipSai = new TipoSaida();
    				objTipSai.setCodigo(rs.getInt(5));
    				objTipSai.setNucleo(objSaida.getNucleo());
    				
    				objTipSai = TipoSaidaDAO.consultarTipoSaida(objTipSai);

    				objSaida.setSaida(objTipSai);
    				
    				Calendar tipoCalendar = Calendar.getInstance();
    				tipoCalendar.setTime(rs.getTime(6));
    				objSaida.setData(tipoCalendar.getInstance());
    				objSaida.setCpfOperador(rs.getLong(7));

    				TipoDocumento objTipDoc = new TipoDocumento();
    				objTipDoc.setCodigo(rs.getInt(8));
    				objTipDoc = TipoDocumentoDAO.consultarTipoDocumento(objTipDoc);

    				objSaida.setTipoDocumento(objTipDoc);
    				objSaida.setDocumento(rs.getString(9));
    				objSaida.setDataDocumento(rs.getDate(10));
    				objSaida.setFornecedor(rs.getInt(11));
    				objSaida.setFlSocio(rs.getString(12));
    				objSaida.setValor(rs.getDouble(13));
    				objSaida.setObservacao(rs.getString(14));
    				objSaida.setFlEstornada(rs.getString(15));
    				objSaida.setFlFechada(rs.getString(16));
    				
    				Socio objSocio = new Socio();
    				NaoSocio objNaoSocio = new NaoSocio();

    				if(rs.getString(12).equalsIgnoreCase("N")){
    					
    					objNaoSocio.setCodigo(rs.getInt(11));
    					NaoSocioDAO objNSocDAO = new NaoSocioDAO();
    					objNaoSocio = objNSocDAO.consultarNaoSoc(objNaoSocio,objSaida.getNucleo().getRegiao().getCodigo());
    					
    					objSocio.setCodigo(0);
    					objSocio.setNome("");
    					//System.out.println("não sócio: "+objNaoSocio.getNome());
    				}else{
    					objSocio.setCodigo(rs.getInt(11));
    					SocioDAO objSocDAO = new SocioDAO();
    					objSocio = objSocDAO.consultarSocio(objSocio, pSaida.getNucleo());
    					
    					objNaoSocio.setCodigo(0);
    					objNaoSocio.setNome("");
    					//System.out.println("sócio: "+objSocio.getNome());
    				}
    				
    				objSaida.setObjetoSocio(objSocio);
    				objSaida.setObjetoNaoSocio(objNaoSocio);
                    
    				listTodas.add(objSaida);
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

	//---------------------------------------------------------------------
	public ArrayList<Saida> listarSaidaPeriodo(Saida pSaidaInicial, Saida pSaidaFinal) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			ArrayList<Saida> listTodas = null;
            Saida objSaida;
            
    		String anoMesInicial = Integer.toString(pSaidaInicial.getMes());
    		anoMesInicial = anoMesInicial.length()<2?"0"+anoMesInicial:anoMesInicial;
    		anoMesInicial = Integer.toString(pSaidaInicial.getAno())+anoMesInicial;

    		String anoMesFinal = Integer.toString(pSaidaFinal.getMes());
    		anoMesFinal = anoMesFinal.length()<2?"0"+anoMesFinal:anoMesFinal;
    		anoMesFinal = Integer.toString(pSaidaFinal.getAno())+anoMesFinal;
    		
            String SQL="select cd_tipo_saida,sum(vl_saida) vl_saida from tb_saida where concat(vl_ano,lpad(vl_mes,2,'0')) between "+anoMesInicial+" and "+anoMesFinal
            +" and cd_nucleo="+pSaidaInicial.getNucleo().getCodigo()+" and nm_estornada<>'S' group by cd_tipo_saida"
            +" order by vl_ano,vl_mes,cd_tipo_saida,dt_documento";
    		
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Saida>();
                do {
                    objSaida = new Saida();
    				
                    TipoSaida objTipSai = new TipoSaida();
    				objTipSai.setCodigo(rs.getInt(1));
    				
    				objTipSai.setNucleo(pSaidaInicial.getNucleo());

    				objTipSai = TipoSaidaDAO.consultarTipoSaida(objTipSai);

    				objSaida.setSaida(objTipSai);
    				
    				objSaida.setValor(rs.getDouble(2));
    				
    				listTodas.add(objSaida);
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
	
	public ArrayList<Saida> listarSaidaPeriodoMensal(Saida pSaidaInicial, Saida pSaidaFinal) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			ArrayList<Saida> listTodas = null;
            Saida objSaida;

    		String anoInicial = new SimpleDateFormat("yyyy").format(pSaidaInicial.getData().getTime());
    		
    		String mesInicial = new SimpleDateFormat("MM").format(pSaidaInicial.getData().getTime());
    		String mesfinal   = new SimpleDateFormat("MM").format(pSaidaFinal.getData().getTime());
    		
            String SQL="select cd_tipo_saida,sum(vl_saida) vl_saida from tb_saida where vl_ano="+anoInicial+" and vl_mes between "+mesInicial+
            " and "+mesfinal+" and cd_nucleo="+pSaidaInicial.getNucleo().getCodigo()+" and nm_estornada<>'S'"
            +" group by cd_tipo_saida order by vl_ano,vl_mes,cd_tipo_saida,dt_documento";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Saida>();
                do {
                    objSaida = new Saida();
    				
                    TipoSaida objTipSai = new TipoSaida();
    				objTipSai.setCodigo(rs.getInt(1));
    				objTipSai.setNucleo(pSaidaInicial.getNucleo());
    				
    				objTipSai = TipoSaidaDAO.consultarTipoSaida(objTipSai);

    				objSaida.setSaida(objTipSai);
    				
    				objSaida.setValor(rs.getDouble(2));
    				
    				listTodas.add(objSaida);
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

	//---------------------------------------------------------------------
	public ArrayList<Saida> listarHistoricoSaidaAnual(int pAno, int pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Saida> listTodas = null;
            
            Saida objSaida;

            String SQL="select cd_tipo_saida,vl_mes,sum(vl_saida) from tb_saida where nm_estornada='N' and vl_ano="+pAno+" and cd_nucleo="+pNucleo
            	+" group by cd_tipo_saida,vl_mes order by cd_tipo_saida,vl_mes";
            
            //System.out.println(SQL);
            
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			listTodas = new ArrayList<Saida>();

        	Nucleo objNucleo = new Nucleo();
        	NucleoDAO objNucleoDAO = new NucleoDAO();
        	
        	objNucleo = objNucleoDAO.consultarNucleo(pNucleo);

			if (rs.next()) {
                do {
                	objSaida = new Saida();

                	TipoSaida objTipoSaida = new TipoSaida();
                	objTipoSaida.setCodigo(rs.getInt(1));
                	objTipoSaida.setNucleo(objNucleo);
                	
                	objTipoSaida = TipoSaidaDAO.consultarTipoSaida(objTipoSaida);
                	
                	if(objTipoSaida==null){
						objTipoSaida = new TipoSaida();
						objTipoSaida.setCodigo(rs.getInt(1));
						objTipoSaida.setDescricao("Tipo Excluído");
                	}
                	
                	objSaida.setSaida(objTipoSaida);	//1
                	objSaida.setMes(rs.getInt(2)); 		//2
                	objSaida.setValor(rs.getDouble(3));	//3

                    listTodas.add(objSaida);
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
	
	@SuppressWarnings("static-access")
	public static ArrayList<Saida> listarSaidaAnual(int pAno, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			ArrayList<Saida> listTodas = null;
            Saida objSaida;

            String SQL="select * from tb_saida where vl_ano="+pAno+" and cd_nucleo="+pNucleo.getCodigo()+" and nm_estornada<>'S'"
            		+" order by cd_tipo_saida,vl_ano,vl_mes,dt_documento";

			//System.out.println("SQL: "+SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Saida>();
                do {
                    objSaida = new Saida();
    				
    				objSaida.setNumero(rs.getInt(1));
    				
    				NucleoDAO objNucleoDAO = new NucleoDAO();
    				objSaida.setNucleo(objNucleoDAO.consultarNucleo(rs.getInt(2)));

    				objSaida.setAno(rs.getInt(3));
    				objSaida.setMes(rs.getInt(4));
    				
    				TipoSaida objTipSai = new TipoSaida();
    				objTipSai.setCodigo(rs.getInt(5));
    				objTipSai = TipoSaidaDAO.consultarTipoSaida(objTipSai);

    				objSaida.setSaida(objTipSai);
    				
    				Calendar tipoCalendar = Calendar.getInstance();
    				tipoCalendar.setTime(rs.getTime(6));
    				objSaida.setData(tipoCalendar.getInstance());
    				objSaida.setCpfOperador(rs.getLong(7));

    				TipoDocumento objTipDoc = new TipoDocumento();
    				objTipDoc.setCodigo(rs.getInt(8));
    				objTipDoc = TipoDocumentoDAO.consultarTipoDocumento(objTipDoc);

    				objSaida.setTipoDocumento(objTipDoc);
    				objSaida.setDocumento(rs.getString(9));
    				objSaida.setDataDocumento(rs.getDate(10));
    				objSaida.setFornecedor(rs.getInt(11));
    				objSaida.setFlSocio(rs.getString(12));
    				objSaida.setValor(rs.getDouble(13));
    				objSaida.setObservacao(rs.getString(14));
    				objSaida.setFlEstornada(rs.getString(15));
    				objSaida.setFlFechada(rs.getString(16));
    				
    				Socio objSocio = new Socio();
    				NaoSocio objNaoSocio = new NaoSocio();

    				if(rs.getString(11).equalsIgnoreCase("N")){
    					objNaoSocio.setCodigo(rs.getInt(11));
    					NaoSocioDAO objNSocDAO = new NaoSocioDAO();
    					objNaoSocio = objNSocDAO.consultarNaoSoc(objNaoSocio,objSaida.getNucleo().getRegiao().getCodigo());
    					
    					objSocio.setCodigo(0);
    					objSocio.setNome("");
    					//System.out.println("não sócio: "+objNaoSocio.getNome());
    				}else{
    					objSocio.setCodigo(rs.getInt(11));
    					SocioDAO objSocDAO = new SocioDAO();
    					objSocio = objSocDAO.consultarSocio(objSocio, pNucleo);
    					
    					objNaoSocio.setCodigo(0);
    					objNaoSocio.setNome("");
    					//System.out.println("sócio: "+objSocio.getNome());
    				}
    				
    				objSaida.setObjetoSocio(objSocio);
    				objSaida.setObjetoNaoSocio(objNaoSocio);
                    
    				listTodas.add(objSaida);
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

	public static int buscaUltimoMesMovimento(int pAno, Nucleo pNucleo) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {

			int anoAnterior = pAno-1;
			String SQL = "select vl_mes from tb_saida where vl_ano in ("+anoAnterior+","+pAno+") and cd_nucleo="+pNucleo.getCodigo()
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

	public static boolean VerificaSaidaHistorico(int pFornecedor, Nucleo pNucleo) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {

			String SQL = "select *from tb_saida where cd_fornecedor="+pFornecedor+" and nm_socio='N' and nm_estornada='N'";
			if(pNucleo!=null){
				SQL = SQL+" and cd_nucleo<>"+pNucleo.getCodigo();
			}

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
