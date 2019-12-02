package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import logica.StuConecta;
import beans.Cargo;
import beans.HistoricoSocio;
import beans.Login;
import beans.Nucleo;
import beans.Socio;
import beans.SocioReuni;

public class SocioDAO {

	static String MensErr = "Erro de acesso aos dados "; 
	public Socio consultarSocio(Socio pSoc, Nucleo pNucleo) throws StuDAOException{

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			String SQL = "select * from tb_socio s, tb_socio_nucleo n where s.cd_socio=n.cd_socio";

			if(pNucleo!=null){
				SQL = SQL+" and n.cd_nucleo="+pNucleo.getCodigo();
			}
			
			if(pSoc.getNome()!=null){
				SQL = SQL+" and upper(s.nm_nome) like '%"+pSoc.getNome().toUpperCase()+"%'";
			}
			
			if(pSoc.getCodigo()!=0){
				SQL = SQL+" and s.cd_socio="+pSoc.getCodigo();  
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			
			if(rs.next()){
				Socio objSoc = new Socio();
				Cargo objCar = new Cargo();
				
				objSoc.setCodigo(rs.getInt(1));
				objSoc.setNome(rs.getString(2));
				objSoc.setTelefone(rs.getString(3));
				objSoc.setCpf(rs.getLong(4));
				
				objSoc.setEmail(rs.getString(5));
				objSoc.setSexo(rs.getString(6));

				objSoc.setDataNasc(rs.getDate(7));
				objSoc.setDataAsso(rs.getDate(8));
				objSoc.setSituacao(rs.getString(9));
				
				objCar.setCodigo(rs.getInt(10));
				objCar = CargoDAO.consultarCargo(objCar);
				//objCar.setDescricao(CargoDAO.consultarCargo(objCar).getDescricao());
				
				objSoc.setCargo(objCar);
				
				objSoc.setGrau(rs.getString(11));
				objSoc.setIsencao(rs.getString(12));
				//objSoc.setAvatar(rs.getString(13));
				objSoc.setNotifica(rs.getString(14));

				objSoc.setObservacao(buscaObservacaoSocio(rs.getInt(1)));


				return objSoc;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, st, rs);
		}
		return null;
	}

	public static String buscaAvatar(int pCodigoSocio) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			
			String SQL = "select *from tb_socio where cd_socio="+pCodigoSocio;

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if(rs.next()){
				return rs.getString(13);
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

	//-------------------------------------------
	public static boolean mudouStatusGrau(Socio pSocio) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			
			String SQL = "select *from tb_socio where cd_socio="+pSocio.getCodigo()+" and (nm_situacao,cd_grau) in"
					+" (select nm_situacao,cd_grau from tb_historico_socio where cd_socio="+pSocio.getCodigo()+" and dt_data=(select max(dt_data)"
					+" from tb_historico_socio where cd_socio="+pSocio.getCodigo()+"))";

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if(rs.next()){
				return false;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return true;
	}

	//-------------------------------------------
	
	public static String checaHabilitaNotificacao(int pCodigoSocio) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			
			String SQL = "select nm_notifica from tb_socio where cd_socio="+pCodigoSocio+" and nm_notifica='S'";
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if(rs.next()){
				return rs.getString(1);
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

	
	public Socio consultarCodigoSocio(Socio pSoc) throws StuDAOException{
		//específica para a inclusão de socio_nuclo na Classe incluiSocio

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			String nomeSocioReuni = pSoc.getNome();
			nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("’")?nomeSocioReuni.replace("’", "`"):nomeSocioReuni;
			nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("'")?nomeSocioReuni.replace("'", "`"):nomeSocioReuni;

			
			String SQL = "select * from tb_socio s where cd_socio=(select max(cd_socio) from tb_socio where upper(nm_nome) like '%"+nomeSocioReuni.toUpperCase()+"%')";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			
			if(rs.next()){
				Socio objSoc = new Socio();
				Cargo objCar = new Cargo();
				
				objSoc.setCodigo(rs.getInt(1));
				objSoc.setNome(rs.getString(2));
				objSoc.setTelefone(rs.getString(3));
				objSoc.setCpf(rs.getLong(4));
				
				objSoc.setEmail(rs.getString(5));
				objSoc.setSexo(rs.getString(6));

				objSoc.setDataNasc(rs.getDate(7));
				objSoc.setDataAsso(rs.getDate(8));
				objSoc.setSituacao(rs.getString(9));
				
				objCar.setCodigo(rs.getInt(10));
				objCar = CargoDAO.consultarCargo(objCar);
				//objCar.setDescricao(CargoDAO.consultarCargo(objCar).getDescricao());
				
				objSoc.setCargo(objCar);
				
				objSoc.setGrau(rs.getString(11));
				objSoc.setObservacao(buscaObservacaoSocio(rs.getInt(1)));

				return objSoc;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, st, rs);
		}
		return null;
	}

	public Socio consultarSocioNucleo(Socio pSoc, Nucleo pNucleo) throws StuDAOException{
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_socio s, tb_socio_nucleo n where s.cpf="+pSoc.getCpf()+" and s.cd_socio=n.cd_socio and n.cd_nucleo="+pNucleo.getCodigo();
			
			if(pSoc.getCpf()==0){
				SQL = "select * from tb_socio s, tb_socio_nucleo n where s.nm_nome='"+pSoc.getNome()+"' and s.cd_socio=n.cd_socio and n.cd_nucleo="+pNucleo.getCodigo();
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			
			if(rs.next()){
				Socio objSoc = new Socio();
				
				objSoc.setCodigo(rs.getInt(1));
				objSoc.setNome(rs.getString(2));
				objSoc.setCpf(rs.getLong(4));
				
				return objSoc;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, st, rs);
		}
		return null;
	}
	public Socio consultarSocioCPF(Socio pSoc) throws StuDAOException{

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_socio where cpf="+pSoc.getCpf();
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			
			if(rs.next()){
				Socio objSoc = new Socio();
				
				objSoc.setCodigo(rs.getInt(1));
				objSoc.setNome(rs.getString(2));
				objSoc.setCpf(rs.getLong(4));
				objSoc.setSituacao(rs.getString(9));
				objSoc.setIsencao(rs.getString(12));
				
				return objSoc;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, st, rs);
		}
		return null;
	}
	
	public Socio consultarNomeSocio(Socio pSoc) throws StuDAOException{

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_socio";
			if(pSoc.getNome()!=null){
				SQL = "select * from tb_socio where upper(nm_nome) like '%"+pSoc.getNome().toUpperCase()+"%'"; 
			}
			if(pSoc.getCodigo()!=0){
				SQL = "select * from tb_socio where cd_socio="+pSoc.getCodigo(); 
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			
			if(rs.next()){
				Socio objSoc = new Socio();
				
				objSoc.setCodigo(rs.getInt("cd_socio"));
				objSoc.setNome(rs.getString("nm_nome"));
				objSoc.setTelefone(rs.getString("nm_telefone"));
				objSoc.setCpf(rs.getLong("cpf"));
				objSoc.setEmail(rs.getString("nm_email"));
				objSoc.setSexo(rs.getString("sexo"));
				objSoc.setDataNasc(rs.getDate("dt_nasc"));
				objSoc.setDataAsso(rs.getDate("dt_asso"));
				objSoc.setSituacao(rs.getString("nm_situacao"));
				objSoc.setGrau(rs.getString("cd_grau"));
				objSoc.setIsencao(rs.getString("nm_isento"));
				objSoc.setObservacao(buscaObservacaoSocio(rs.getInt(1)));

				/*
				  `NM_SITUACAO` char(1) NOT NULL DEFAULT 'A',
				  `CD_CARGO` int(4) DEFAULT NULL,
				  `CD_GRAU` char(1) NOT NULL DEFAULT 'S',
				  `NM_AVATAR` varchar(250) DEFAULT NULL,
				*/
				
				return objSoc;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, st, rs);
		}
		return null;
	}

	public String buscaObservacaoSocio(int codSocio) throws StuDAOException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_historico_socio where cd_socio="+codSocio
			+ " and dt_data = (select max(dt_data) from tb_historico_socio where cd_socio="+codSocio+")";

			conn = StuConecta.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			
			if(rs.next()){
				return rs.getString("nm_observacao");
			}
		} 
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, st, rs);
		}
		return null;
	}
	
	public void incluirSocio(Socio pSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		String SQL = "insert into tb_socio values(cd_socio, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		/*
		System.out.println(SQL);
		System.out.println(pSoc.getNome()+", "+pSoc.getTelefone()+", "+pSoc.getCpf()+", "+pSoc.getEmail()+", "
				+pSoc.getSexo()+", "+pSoc.getDataNasc()+" "+pSoc.getDataAsso()+" "+pSoc.getSituacao()+" "+pSoc.getCargo().getCodigo());
		System.out.println(pSoc.getGrau()+", "+pSoc.getIsencao()+", "+pSoc.getAvatar()+" "+pSoc.getNotifica());
		*/

		try {
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			
			String nomeSocio = pSoc.getNome();
			nomeSocio = pSoc.getNome().toUpperCase().contains("'")?pSoc.getNome().replace("'", "´"):pSoc.getNome();
			//Verificar o comportamento da linha acima
			
			ps.setString(1, nomeSocio);
			ps.setString(2, pSoc.getTelefone());
			ps.setLong(3, pSoc.getCpf());
			ps.setString(4, pSoc.getEmail());
			ps.setString(5, pSoc.getSexo());

			ps.setDate(6, null);
			if(pSoc.getDataNasc()!=null){
				ps.setDate(6, new java.sql.Date(pSoc.getDataNasc().getTime()));
			}
			ps.setDate(7, null);
			if(pSoc.getDataAsso()!=null){
				ps.setDate(7, new java.sql.Date(pSoc.getDataAsso().getTime()));
			}
		
			ps.setString(8, pSoc.getSituacao());
			ps.setInt(9, pSoc.getCargo().getCodigo());
			ps.setString(10, pSoc.getGrau());
			ps.setString(11, pSoc.getIsencao());
			ps.setString(12, pSoc.getAvatar());
			ps.setString(13, "N");
			
			ps.executeUpdate();
			
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void incluirSocioNucleo(int pCodigoSocio, int pCodigoNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		
		String SQL = "insert into tb_socio_nucleo values(?, ?)";
		
		try {
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			
			ps.setInt(1, pCodigoSocio);
			ps.setInt(2, pCodigoNucleo);

			ps.executeUpdate();
			
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void excluirSocioNucleo(Socio pSocio) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		String SQL = "delete from tb_socio_nucleo where cd_socio="+pSocio.getCodigo();
		
		try {
			
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

	public HistoricoSocio consultarHistSocio(HistoricoSocio pHistSoc, Login pLogin) throws StuDAOException{

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_historico_socio where cd_nucleo="+pLogin.getNucleo().getCodigo();

			if(pHistSoc.getSocio().getCodigo()!=0){
				SQL = "select * from tb_historico_socio where cd_nucleo="+pLogin.getNucleo().getCodigo()+" and cd_socio="+pHistSoc.getSocio().getCodigo()
				+" and dt_data=date('"+(new java.sql.Date( pHistSoc.getDataHistorico().getTime()))+"')"; 
			}
			
			//System.out.println(SQL);

			conn = StuConecta.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			
			if(rs.next()){
				
				HistoricoSocio objHistSoc = new HistoricoSocio();
				Socio objSoc = new Socio();
				Cargo objCar = new Cargo();

				objSoc.setCodigo(rs.getInt(1));
				//2 Nucleo
				objSoc.setSituacao(rs.getString(3));
				
				objCar.setCodigo(rs.getInt(4));

				objSoc.setCargo(objCar);
				objSoc.setGrau(rs.getString(5));
				
				objHistSoc.setDataHistorico(rs.getDate(6));
				objHistSoc.setDataLancamento(rs.getDate(7));
				objHistSoc.setCpfOperador(rs.getLong(8));
				
				objSoc.setObservacao(rs.getString(9));
				objHistSoc.setSocio(objSoc);

				return objHistSoc;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, st, rs);
		}
		return null;
	}

	public HistoricoSocio consultarHistCargo(HistoricoSocio pHistSoc, Login pLogin) throws StuDAOException{

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			String SQL = "select * from tb_historico_socio where cd_nucleo="+pLogin.getNucleo().getCodigo();

			if(pHistSoc.getSocio().getCodigo()!=0){
				SQL = "select * from tb_historico_socio where cd_nucleo="+pLogin.getNucleo().getCodigo()+" and cd_cargo="+pHistSoc.getSocio().getCargo().getCodigo()
				+" and dt_data=date('"+(new java.sql.Date( pHistSoc.getDataHistorico().getTime()))+"')";
			}

			//System.out.println(SQL);

			conn = StuConecta.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			
			if(rs.next()){
				
				HistoricoSocio objHistSoc = new HistoricoSocio();
				Socio objSoc = new Socio();
				Cargo objCar = new Cargo();

				objSoc.setCodigo(rs.getInt(1));
				//2 Nucleo
				objSoc.setSituacao(rs.getString(3));
				
				objCar.setCodigo(rs.getInt(4));

				objSoc.setCargo(objCar);
				objSoc.setGrau(rs.getString(5));

				objHistSoc.setDataHistorico(rs.getDate(6));
				objHistSoc.setDataLancamento(rs.getDate(7));
				objHistSoc.setCpfOperador(rs.getLong(8));

				objSoc.setObservacao(rs.getString(9));
				objHistSoc.setSocio(objSoc);
				
				return objHistSoc;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, st, rs);
		}
		return null;
	}

	public void incluirHistSoc(HistoricoSocio pHistSoc, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		String SQL = "insert into tb_historico_socio values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);

			ps.setInt(1, pHistSoc.getSocio().getCodigo());
			ps.setInt(2, pNucleo.getCodigo());
			ps.setString(3, pHistSoc.getSocio().getSituacao());
			ps.setInt(4, pHistSoc.getSocio().getCargo().getCodigo());
			ps.setString(5, pHistSoc.getSocio().getGrau());
			
			ps.setDate(6, null);
			if(pHistSoc.getDataHistorico()!=null){
				ps.setDate(6, new java.sql.Date(pHistSoc.getDataHistorico().getTime()));
			}
			ps.setDate(7, null);
			if(pHistSoc.getDataLancamento()!=null){
				ps.setDate(7, new java.sql.Date(pHistSoc.getDataLancamento().getTime()));
			}
			ps.setLong(8, pHistSoc.getCpfOperador());
			ps.setString(9, pHistSoc.getSocio().getObservacao());

			ps.executeUpdate();

		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	//-------------------------------------
	public static void excluirHistSocioTemp(Login pOperador, int pCodigoSocio) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;

		String SQL = "delete from tb_historico_socio where cd_nucleo="+pOperador.getNucleo().getCodigo()+" and nm_situacao='T' and dt_data=CURDATE()"
			 +" and cd_socio="+pCodigoSocio; //and nm_observacao='ALTERADO VIA REUNI'
		
		try {
			
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

	//-------------------------------------

	public static void gravarHistSocioTemp(Login pLogin) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;

		String SQL = "insert into tb_historico_socio (select cd_socio,"+pLogin.getNucleo().getCodigo()+",'T',cd_cargo,cd_grau,CURDATE(),CURDATE(),cpf,'ALTERADO VIA REUNI'"
				+" from tb_socio where nm_situacao='A' and cd_socio in (select cd_socio from tb_socio_nucleo where cd_nucleo="+pLogin.getNucleo().getCodigo()+" and cd_socio<>"+pLogin.getUsuario()+"))";
		
		//System.out.println(SQL);
		
		try {
			
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

	public void alterarHistSocio(HistoricoSocio pHistSoc, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_historico_socio set nm_situacao=?, cd_nucleo=?, cd_cargo=?,cd_grau=?,dt_data=?,dt_lancamento=?,cpf_operador=?,nm_observacao=?"
			+" where cd_socio="+pHistSoc.getSocio().getCodigo()+" and dt_data=date('"+(new java.sql.Date( pHistSoc.getDataHistorico().getTime()))+"')";

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);

			ps.setString(1, pHistSoc.getSocio().getSituacao());
			ps.setInt(2, pNucleo.getCodigo());
			
			ps.setInt(3, pHistSoc.getSocio().getCargo().getCodigo());
			ps.setString(4, pHistSoc.getSocio().getGrau());

			ps.setDate(5, null);
			if(pHistSoc.getDataHistorico()!=null){
				ps.setDate(5, new java.sql.Date(pHistSoc.getDataHistorico().getTime()));
			}

			ps.setDate(6, null);
			if(pHistSoc.getDataLancamento()!=null){
				ps.setDate(6, new java.sql.Date(pHistSoc.getDataLancamento().getTime()));
			}

			ps.setLong(7, pHistSoc.getCpfOperador());
			ps.setString(8, pHistSoc.getSocio().getObservacao());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	//-----------------------------
	public static void alterarStatusSocio(Login pLogin) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;

		try {
			String SQL = "update tb_socio set nm_situacao=? where nm_situacao='A' and cd_socio in (select cd_socio from tb_socio_nucleo where cd_nucleo="+pLogin.getNucleo().getCodigo()+" and cd_socio<>"+pLogin.getUsuario()+")";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, "T");

			ps.executeUpdate();

		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	//-----------------------------
	public void alterarHistCargo(HistoricoSocio pHistSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_historico_socio set nm_situacao=?, cd_cargo=?,cd_grau=?,dt_data=?,dt_lancamento=?,cpf_operador=?,nm_observacao=?,cd_socio=?"
			+" where cd_cargo="+pHistSoc.getSocio().getCargo().getCodigo()+" and dt_data=date('"+(new java.sql.Date( pHistSoc.getDataHistorico().getTime()))+"')";

			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);

			ps.setString(1, pHistSoc.getSocio().getSituacao());
			ps.setInt(2, pHistSoc.getSocio().getCargo().getCodigo());
			ps.setString(3, pHistSoc.getSocio().getGrau());
			
			ps.setDate(4, null);
			if(pHistSoc.getDataHistorico()!=null){
				ps.setDate(4, new java.sql.Date(pHistSoc.getDataHistorico().getTime()));
			}

			ps.setDate(5, null);
			if(pHistSoc.getDataLancamento()!=null){
				ps.setDate(5, new java.sql.Date(pHistSoc.getDataLancamento().getTime()));
			}

			ps.setLong(6, pHistSoc.getCpfOperador());
			ps.setString(7, pHistSoc.getSocio().getObservacao());
			ps.setInt(8, pHistSoc.getSocio().getCodigo());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	//-----------------------

	public static void atualizarDadosPrincipais(Socio pSocio) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		//String status = "";
		//String grau = "";
		
		try {
			String SQL = "update tb_socio set nm_nome=?, nm_email=?, nm_telefone=?,nm_situacao=?,cd_grau=?,nm_avatar=?,cpf=?"
					+" where cd_socio="+pSocio.getCodigo();
			
			/*
			System.out.println(SQL);
			System.out.println(pSocio.getNome()+","+pSocio.getEmail()+","+(pSocio.getTelefone().length()>50?pSocio.getTelefone().substring(0, 50):pSocio.getTelefone())
					+","+pSocio.getSituacao()+","+pSocio.getGrau()+","+pSocio.getAvatar()+","+pSocio.getCpf());
			*/
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			
			String nomeSocioReuni = pSocio.getNome();
			nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("’")?nomeSocioReuni.replace("’", "`"):nomeSocioReuni;
			nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("'")?nomeSocioReuni.replace("'", "`"):nomeSocioReuni;
			
			ps.setString(1, nomeSocioReuni);
			ps.setString(2, pSocio.getEmail());

			//System.out.println(pSocio.getNome()+","+pSocio.getEmail());

			ps.setString(3, pSocio.getTelefone().length()>50?pSocio.getTelefone().substring(0, 50):pSocio.getTelefone());

			//status = pSocio.getSituacao().toUpperCase().contains("FREQUENTE")?"A":pSocio.getSituacao().toUpperCase().contains("LICENCIADO")?"L":pSocio.getSituacao().toUpperCase().contains("AFASTADO")?"F":"O";
			ps.setString(4, pSocio.getSituacao());

			//grau = pSocio.getGrau().substring(1,2).equalsIgnoreCase("D")?"C":pSocio.getGrau().substring(1,2);
			ps.setString(4, pSocio.getGrau());
			/*
			//System.out.println(pSocio.getNome()+","+pSocio.getEmail()+","+(pSocio.getTelefone().length()>50?pSocio.getTelefone().substring(0, 50):pSocio.getTelefone())+","+status);
			*/

			ps.setString(4, pSocio.getSituacao());
			ps.setString(5, pSocio.getGrau());
			
			ps.setString(6, pSocio.getAvatar());
			ps.setLong(7, pSocio.getCpf());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	//-----------------------
	public void alterarSocio(Socio pSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_socio set nm_nome=?, nm_email=?, nm_telefone=?,cpf=?,sexo=?,dt_nasc=?,dt_asso=?,nm_situacao=?,cd_cargo=?,cd_grau=?,nm_isento=?,nm_notifica=?"
					+" where cd_socio="+pSoc.getCodigo();

			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			
			String nomeSocioReuni = pSoc.getNome();

			if(nomeSocioReuni!=null&&!nomeSocioReuni.equalsIgnoreCase("")){
				nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("’")?nomeSocioReuni.replace("’", "`"):nomeSocioReuni;
				nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("'")?nomeSocioReuni.replace("'", "`"):nomeSocioReuni;
			}
			
			ps.setString(1, nomeSocioReuni);
			ps.setString(2, pSoc.getEmail());
			ps.setString(3, pSoc.getTelefone());

			ps.setLong(4, pSoc.getCpf());

			ps.setString(5, pSoc.getSexo());
			if(pSoc.getSexo().length()>1){
				ps.setString(5, pSoc.getSexo().substring(0,1));
			}

			ps.setDate(6, null);
			if(pSoc.getDataNasc()!=null){
				ps.setDate(6, new java.sql.Date(pSoc.getDataNasc().getTime()));
			}

			ps.setDate(7, null);
			if(pSoc.getDataAsso()!=null){
				ps.setDate(7, new java.sql.Date(pSoc.getDataAsso().getTime()));
			}

			ps.setString(8, pSoc.getSituacao());
			ps.setInt(9, pSoc.getCargo().getCodigo());
			
			ps.setString(10, pSoc.getGrau());
			if(pSoc.getGrau().length()>1){
				if(pSoc.getGrau().equalsIgnoreCase("Discípulo QS")){
					ps.setString(10, "S");
				}else{
					ps.setString(10, pSoc.getGrau().substring(0,1));
				}
			}
			
			ps.setString(11, pSoc.getIsencao());
			ps.setString(12, pSoc.getNotifica());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void unificarCargoSocio(Socio pSoc, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("update tb_socio set cd_cargo=?"+
					" where cd_socio<>"+pSoc.getCodigo()+" and cd_cargo="+pSoc.getCargo().getCodigo())+" and cd_socio in"+
					" (select cd_socio from tb_socio_nucleo where cd_nucleo="+pNucleo.getCodigo()+")";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, 0);

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void excluirSoc(Socio pSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_socio where cd_socio="+Integer.toString(pSoc.getCodigo()));
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

	public static int buscarTotalSocioAtivo(int pNucleo) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			String SQL = "select count(distinct s.cd_socio) from tb_socio s, tb_socio_nucleo n where n.CD_SOCIO=s.CD_SOCIO and nm_situacao='A' and n.CD_NUCLEO="+pNucleo;

			//System.out.println(SQL);
			
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

	public static ArrayList<Socio> listarSocioAtivoNaoIsento(int pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
            ArrayList<Socio> listTodas = null;
            Socio itemSoc;

			String SQL = "select distinct s.cd_socio,nm_nome,nm_telefone,cpf,nm_email,sexo,dt_nasc,dt_asso,nm_situacao,cd_cargo,cd_grau"+
					" from tb_socio s, tb_socio_nucleo n where s.cd_socio=n.cd_socio and cd_nucleo="+pNucleo+" and s.nm_isento<>'S' and"+
					" nm_situacao='A' order by cd_grau<>'M',cd_grau,nm_nome";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Socio>();
                do {
                    itemSoc = new Socio();
                    itemSoc.setCodigo(rs.getInt("cd_socio"));
                    itemSoc.setNome(rs.getString("nm_nome"));
                    itemSoc.setTelefone(rs.getString("nm_telefone"));
                    itemSoc.setEmail(rs.getString("nm_email"));
                    itemSoc.setCpf(rs.getLong("cpf"));
                    itemSoc.setDataNasc(rs.getDate("dt_nasc"));
                    itemSoc.setGrau(rs.getString("cd_grau"));
                    //itemSoc.setNotifica(rs.getString("nm_notifica"));
                    
                    listTodas.add(itemSoc);
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

	public static ArrayList<Socio> listarSocioNotificacao(int pNucleo, int pSocio) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
            ArrayList<Socio> listTodas = null;
            Socio itemSoc;

			String SQL = "select distinct s.cd_socio,nm_nome,nm_telefone,cpf,nm_email,sexo,dt_nasc,dt_asso,nm_situacao,cd_cargo,cd_grau,nm_notifica"+
					" from tb_socio s, tb_socio_nucleo n where s.cd_socio=n.cd_socio and cd_nucleo="+pNucleo+" and s.nm_isento<>'S' and"+
					" nm_situacao='A'";
			
			if(pSocio!=0){
				SQL = SQL+" and s.cd_socio="+pSocio;
			}else{
				SQL = SQL+" and nm_notifica='S'";
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Socio>();
                do {
                    itemSoc = new Socio();
                    itemSoc.setCodigo(rs.getInt("cd_socio"));
                    itemSoc.setNome(rs.getString("nm_nome"));
                    itemSoc.setTelefone(rs.getString("nm_telefone"));
                    itemSoc.setEmail(rs.getString("nm_email"));
                    itemSoc.setSexo(rs.getString("sexo"));
                    itemSoc.setCpf(rs.getLong("cpf"));
                    itemSoc.setDataNasc(rs.getDate("dt_nasc"));
                    itemSoc.setGrau(rs.getString("cd_grau"));
                    itemSoc.setNotifica(rs.getString("nm_notifica"));
                    
                    listTodas.add(itemSoc);
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

	public static ArrayList<Socio> listarSocio(int pNucleo, Socio pSoc, String pTip,int pFiltro) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
            ArrayList<Socio> listTodas = null;
            Socio itemSoc;

			String SQL = "select distinct s.cd_socio,nm_nome,nm_telefone,cpf,nm_email,sexo,dt_nasc,dt_asso,nm_situacao,cd_cargo,cd_grau "+
					" from tb_socio s, tb_socio_nucleo n where s.cd_socio=n.cd_socio and cd_nucleo="+pNucleo+" and ";
			if(pTip.equalsIgnoreCase("ativo")){
            	SQL = SQL+"nm_situacao='A' and ";
            }

			if(pSoc.getNome()!=null){
				SQL = SQL + " upper(nm_nome) like '%"+pSoc.getNome().toUpperCase()+"%' and"; 
			}

			if(pSoc.getDataNasc()!=null){
				Calendar data = Calendar.getInstance();  
				data.setTime(pSoc.getDataNasc());
				SQL = SQL+" month(dt_nasc)="+data.get(Calendar.MONTH)+" and"; 
			}

			if (pFiltro==1){
				SQL=SQL+" cd_cargo=cd_cargo order by month(dt_nasc),day(dt_nasc),cd_grau<>'M',cd_grau,nm_nome";
			}else{
				SQL=SQL+" cd_cargo=cd_cargo order by cd_grau<>'M',cd_grau,nm_nome";
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Socio>();
                do {
                    itemSoc = new Socio();
                    itemSoc.setCodigo(rs.getInt("cd_socio"));
                    itemSoc.setNome(rs.getString("nm_nome"));
                    itemSoc.setTelefone(rs.getString("nm_telefone"));
                    itemSoc.setEmail(rs.getString("nm_email"));
                    itemSoc.setCpf(rs.getLong("cpf"));
                    itemSoc.setDataNasc(rs.getDate("dt_nasc"));
                    itemSoc.setGrau(rs.getString("cd_grau"));
                    
                    listTodas.add(itemSoc);
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

	//---------------------------------------
	public static ArrayList<Socio> listarSociosSemelhantes(String pTrechoNome, int pCodigoNucleo) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Socio> listTodas = null;
            Socio itemSoc;
            
			String SQL = "select * from tb_socio s, tb_socio_nucleo sn where s.cd_socio=sn.cd_socio and cd_nucleo=null";
			
			if(pTrechoNome!=null&&!pTrechoNome.equalsIgnoreCase("")){

				String nomeSocioReuni = pTrechoNome;
				nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("'")?nomeSocioReuni.replace("'", "´"):nomeSocioReuni;
				
				SQL = "select * from tb_socio s, tb_socio_nucleo sn where upper(nm_nome) like upper('"+nomeSocioReuni
						+" %') and s.cd_socio=sn.cd_socio and cd_nucleo="+pCodigoNucleo;
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if (rs.next()) {
                listTodas = new ArrayList<Socio>();
                do {
                	
                    itemSoc = new Socio();
                    
                    itemSoc.setCodigo(rs.getInt("cd_socio"));
                    itemSoc.setNome(rs.getString("nm_nome"));
                    itemSoc.setCpf(rs.getLong("cpf"));

                    listTodas.add(itemSoc);
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

	//---------------------------------------
	
	public int compararReuniRealValor(SocioReuni pSocioReuni, int pCodigoNucleo) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			
			String SQL = "select * from tb_socio s, tb_socio_nucleo sn where s.cd_socio=sn.cd_socio and cd_nucleo=null";
			
			if(pSocioReuni.getName()!=null&&!pSocioReuni.getName().equalsIgnoreCase("")){

				String nomeSocioReuni = pSocioReuni.getName();
				nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("’")?nomeSocioReuni.replace("’", "`"):nomeSocioReuni;
				nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("'")?nomeSocioReuni.replace("'", "`"):nomeSocioReuni;
				
				SQL = "select * from tb_socio s, tb_socio_nucleo sn where upper(nm_nome) like upper('"+nomeSocioReuni
						+"%') and s.cd_socio=sn.cd_socio and cd_nucleo="+pCodigoNucleo;
			}			

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if(rs.next()){
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
	
	public static ArrayList<Socio> listarHistoricoSocio(Socio pSoc) throws StuDAOException{
													
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Socio> listTodas = null;
            Socio itemSoc;
            
			String SQL = "select s.nm_nome,h.nm_situacao,h.cd_cargo,h.cd_grau,h.dt_data,h.nm_observacao FROM tb_historico_socio h, tb_socio s"
				+" where h.cd_socio=s.cd_socio and";
			if(pSoc.getCodigo()!=0){
				SQL = SQL + " h.cd_socio="+pSoc.getCodigo()+" and"; 
			}
			
			SQL=SQL+" h.cd_cargo=h.cd_cargo order by s.nm_nome,h.dt_data";
			
			//System.out.println("SQL: "+SQL);	
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if (rs.next()) {
                listTodas = new ArrayList<Socio>();
                do {
                	
                    itemSoc = new Socio();
                    Cargo objCargo = new Cargo();
                    
                    itemSoc.setNome(rs.getString("nm_nome"));
                    itemSoc.setSituacao(rs.getString("nm_situacao"));
                    
                    objCargo.setCodigo(rs.getInt("cd_cargo"));

                    itemSoc.setCargo(CargoDAO.consultarCargo(objCargo));
                    itemSoc.setGrau(rs.getString("cd_grau"));
                    itemSoc.setDataAsso(rs.getDate("dt_data"));
                    itemSoc.setObservacao(rs.getString("h.nm_observacao"));
                    listTodas.add(itemSoc);
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

	public static ArrayList<Socio> listarPag(Socio pPag, String pSitu, Nucleo pNucleo) throws StuDAOException{ //int pNucleo
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Socio> listTodas = null;
            Socio itemPag;

			String SQL = "select s.cd_socio,nm_nome,nm_situacao from tb_socio s, tb_socio_nucleo n where s.cd_socio=n.cd_socio and n.cd_nucleo="+pNucleo.getCodigo();
			if(pSitu.equalsIgnoreCase("N")){
				SQL = "select cd_nsocio,nm_nome,'N' from tb_nsocio";
				if(pPag.getNome()!=null){
					SQL = SQL+" where upper(nm_nome) like '%"+pPag.getNome().toUpperCase()+"%' and cd_regiao="+pNucleo.getRegiao().getCodigo();
				}
			}else{
				if(pPag.getNome()!=null){
					SQL = SQL + " and upper(nm_nome) like '%"+pPag.getNome().toUpperCase()+"%' and nm_situacao <> 'O'"; //and nm_isento<>'S' 
				}
			}
			SQL=SQL+" order by nm_nome";

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Socio>();
                do {
                    itemPag = new Socio();
                    itemPag.setCodigo(rs.getInt(1));
                    itemPag.setNome(rs.getString(2).replace("&", "Æ"));
                    
                    itemPag.setSituacao(rs.getString(3));
                    
                    listTodas.add(itemPag);
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

