package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Cargo;
import beans.Login;
import beans.Nucleo;
import beans.Regiao;
import beans.Socio;

public class LoginDAO { 
	
	public void incluirLogin(Login pLog) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "insert into tb_login values(?, ?, ?, ?, ?)";
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			/*
			System.out.println("insert into tb_login values("+pLog.getCpf()+","+pLog.getNucleo().getCodigo()
					+","+pLog.getUsuario()+",'"+pLog.getSenha()+"',"+pLog.getNivel()+")");
			*/
			
			ps.setLong(1, pLog.getCpf());
			ps.setInt(2, pLog.getNucleo().getCodigo());
			ps.setInt(3, pLog.getUsuario());
			ps.setString(4, pLog.getSenha());
			ps.setInt(5, pLog.getNivel());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarLogin(Login pLog) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_login set cpf_operador=?, cd_nucleo=?, nm_senha=?, vl_nivel=? where cd_socio="+pLog.getUsuario();

			//System.out.println(SQL);
			//System.out.println("cpf "+pLog.getCpf()+" nucleo "+pLog.getNucleo().getCodigo()+" senha "+pLog.getSenha()+" nivel "+pLog.getNivel());
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			
			ps.setLong(1, pLog.getCpf());
			ps.setInt(2, pLog.getNucleo().getCodigo());
			ps.setString(3, pLog.getSenha());
			ps.setInt(4, (pLog.getNivel()<0?3:pLog.getNivel()));
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void alterarOperador(Login pLog) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_login set cd_socio=? where cpf_operador="+pLog.getCpf()+" and cd_nucleo="+pLog.getNucleo().getCodigo();

			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			
			ps.setLong(1, pLog.getUsuario());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void excluirLogin(Login pLog) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_login  where cpf_operador="+pLog.getCpf());
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

	String MensErr = "Erro de acesso aos dados "; 
	public Login execSelect(String pSql) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = pSql;
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			//System.out.println(SQL);
			
			Login usuario = new Login();
			if(rs.next()){
				usuario.setCpf(rs.getLong(1));

				Regiao objRegiao = new Regiao();
				//objRegiao.setCodigo(rs.getInt(2));
				
				RegiaoDAO objRegiaoDAO = new RegiaoDAO();
				objRegiao=objRegiaoDAO.consultarRegiao(rs.getInt(2)); 
				
				Nucleo objNucleo = new Nucleo();
				NucleoDAO objDAONucleo = new NucleoDAO();

				objNucleo = objDAONucleo.consultarNucleo(rs.getInt(3));
				
				usuario.setNucleo(objNucleo);

				usuario.setUsuario(rs.getInt(4));				

				
				usuario.setRegiao(objRegiao);

				usuario.setSenha(rs.getString(5));				
				usuario.setNivel(rs.getInt(6));
				usuario.setNome(rs.getString(7));
				
				Cargo objCargo = new Cargo();
				objCargo.setCodigo(rs.getInt(8));
				
				objCargo = CargoDAO.consultarCargo(objCargo);
				usuario.setCargo(objCargo.getDescricao());

				return usuario;
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

	public Login consultarLogin(Login pLog) throws StuDAOException{
		
		String SQL = "select cpf_operador,n.cd_regiao,l.cd_nucleo,l.cd_socio,nm_senha,vl_nivel,nm_nome,cd_cargo from tb_login l, tb_socio s, tb_nucleo n"
				+" where l.cpf_operador="+pLog.getCpf()+" and l.cd_socio=s.cd_socio and l.cd_nucleo=n.cd_nucleo and nm_situacao='A'";

		if(pLog.getNucleo().getCodigo()>0){
			SQL = SQL+" and l.cd_nucleo="+pLog.getNucleo().getCodigo();
		}
		
		//System.out.println(SQL);
		
		Login usuario = new Login();
		usuario = execSelect(SQL);
		
		if(usuario!=null&&usuario.getSenha().equals(pLog.getSenha())){
			return usuario;
		}
		return null;
		
	}

	public Login consultarLoginOperador(Login pLog) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			
			String SQL = "select cpf_operador,cd_nucleo,cd_socio from tb_login"
					+" where cpf_operador="+pLog.getCpf()+" and cd_nucleo="+pLog.getNucleo().getCodigo();
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			Login usuario = new Login();
			if(rs.next()){
				usuario.setCpf(rs.getLong(1));
				
				Socio objSocio = new Socio();
				Nucleo objNucleo = new Nucleo();
				
				SocioDAO objSocioDAO = new SocioDAO();
				NucleoDAO objDAONucleo = new NucleoDAO();
				
				objNucleo = objDAONucleo.consultarNucleo(rs.getInt(2));
				
				usuario.setNucleo(objNucleo);
				
				usuario.setUsuario(rs.getInt(3));				
				
				objSocio.setCpf(usuario.getCpf());
				objSocio = objSocioDAO.consultarSocioCPF(objSocio);
				
				usuario.setSituacao(objSocio.getSituacao());
				
				return usuario;
			}			
		}catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return null;
		
	}

	//criar método para historico ---
	public Login consultarUsuNome(Login pLog) throws StuDAOException{
		
		String SQL = "select cpf_operador,n.cd_regiao,l.cd_nucleo,l.cd_socio,nm_senha,vl_nivel,nm_nome,cd_cargo from tb_login l, tb_socio s, tb_nucleo n"
					+" where l.cpf_operador="+pLog.getCpf()+" and l.cd_socio=s.cd_socio and l.cd_nucleo=n.cd_nucleo and l.cd_nucleo="+pLog.getNucleo().getCodigo();
		
		if(pLog.getUsuario()!=0){
			SQL = "select cpf_operador,n.cd_regiao,l.cd_nucleo,l.cd_socio,nm_senha,vl_nivel,nm_nome,cd_cargo from tb_login l, tb_socio s, tb_nucleo n"
				+" where l.cd_socio="+pLog.getUsuario()+" and l.cd_socio=s.cd_socio and l.cd_nucleo=n.cd_nucleo and l.cd_nucleo="+pLog.getNucleo().getCodigo();
		}
		
		//System.out.println(SQL);
		
		Login operador = new Login();
		operador = execSelect(SQL);
		return operador;
	}

	public Login consultarOperador(Login pLog) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {

			String SQL = "select * from tb_login where cd_socio="+pLog.getUsuario();	
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			Login usuario = new Login();
			if(rs.next()){
				usuario.setCpf(rs.getLong(1));
				
				Socio objSocio = new Socio();
				Nucleo objNucleo = new Nucleo();
				SocioDAO objSocioDAO = new SocioDAO();
				NucleoDAO objDAONucleo = new NucleoDAO();
				
				objNucleo = objDAONucleo.consultarNucleo(rs.getInt(2));

				usuario.setNucleo(objNucleo);

				usuario.setUsuario(rs.getInt(3));				
				usuario.setSenha(rs.getString(4));
				usuario.setNivel(rs.getInt(5));
				
				objSocio.setCpf(usuario.getCpf());
				objSocio = objSocioDAO.consultarSocioCPF(objSocio);

				usuario.setSituacao(objSocio.getSituacao());

				return usuario;
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

	public Login consultarNivel(Login pLog) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select vl_nivel from tb_login where cpf_operador="+pLog.getCpf();			
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()){				
				pLog.setNivel(rs.getInt(1));					
			}
			return pLog;
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public Login consultarCPFOperador(Login pLog) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			Login objLogin=null;
			Nucleo objNucleo;
			NucleoDAO objNucleoDAO = new NucleoDAO();
			
			String SQL = "select *from tb_login where cpf_operador="+pLog.getCpf();
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()){	
			
				objNucleo = new Nucleo();
				objLogin = new Login();
				
				objLogin.setCpf(rs.getLong(1));

				objNucleo = objNucleoDAO.consultarNucleo(rs.getInt(2));
				objLogin.setNucleo(objNucleo);

				objLogin.setUsuario(rs.getInt(3));
				objLogin.setSenha(rs.getString(4));
				objLogin.setNivel(rs.getInt(5));
				
				return objLogin;
			}else{
				return null;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public static ArrayList<Login> listarLogin(Login pOperador) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Login> listTodas = null;
            Login itemLog;

			String SQL = "select cpf_operador,l.cd_nucleo,l.cd_socio,nm_senha,vl_nivel,nm_nome,cd_cargo from tb_login l, tb_socio s where l.cd_socio=s.cd_socio";
			SQL=SQL+" and s.nm_situacao='A' and l.cd_nucleo="+pOperador.getNucleo().getCodigo()+" order by vl_nivel desc ,nm_nome";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Login>();
                do {
                    itemLog = new Login();
                    itemLog.setCpf(rs.getLong("cpf_operador"));
                    

                    Nucleo objNucleo = new Nucleo();
    				objNucleo.setCodigo(rs.getInt("cd_nucleo"));
    				
    				itemLog.setNucleo(objNucleo);
                    
                    itemLog.setUsuario(rs.getInt("cd_socio"));
                    itemLog.setNome(rs.getString("nm_nome"));
                    itemLog.setSenha(rs.getString("nm_senha"));
                    itemLog.setNivel(rs.getInt("vl_nivel"));

                    listTodas.add(itemLog);
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

	public static ArrayList<Login> listarMultiploLogin(Login pOperador) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Login> listTodas = null;
            Login itemLogin;
            Nucleo itemNucleo;
            Regiao itemRegiao;

			String SQL = "select n.cd_nucleo,nm_nome_nucleo,cd_regiao from tb_socio_nucleo sn, tb_nucleo n where sn.CD_SOCIO in "
					+" (select cd_socio from tb_socio where cpf="+pOperador.getCpf()+") and n.cd_nucleo=sn.CD_NUCLEO";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Login>();
                do {
                	
                	itemNucleo =  new Nucleo();
                    itemLogin = new Login();
                    itemRegiao = new Regiao();
                    
                    itemNucleo.setCodigo(rs.getInt("cd_nucleo"));
                    itemNucleo.setNome(rs.getString("nm_nome_nucleo"));

                    itemRegiao.setCodigo(rs.getInt("cd_regiao"));
                    
                    itemLogin.setNucleo(itemNucleo);
                    itemLogin.setRegiao(itemRegiao);

                    listTodas.add(itemLogin);
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
	
	public static boolean consultarAcesso() throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select acesso from tb_controle_acesso";			
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()){				
				return rs.getBoolean("acesso");
			}
			return false;
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

}