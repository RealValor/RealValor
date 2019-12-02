package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Nucleo;
import beans.SocioReuni;

public class SocioReuniDAO {
	static String MensErr = "Erro de acesso aos dados "; 
	public static SocioReuni consultarSocioReuni(SocioReuni pSocioReuni) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			
			SocioReuni objSocioReuni;

			String SQL = "select * from tb_socio_reuni";
			
			if(pSocioReuni!=null){
				if(pSocioReuni.getId()!=0){
					SQL = "select * from tb_socio_reuni where id="+pSocioReuni.getId();
				}
			}

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if(rs.next()){
				objSocioReuni = new SocioReuni();
				
				objSocioReuni.setId(rs.getInt("id"));
				objSocioReuni.setName(rs.getString("name"));
				objSocioReuni.setCpf(rs.getString("cpf"));
				objSocioReuni.setAvatar(rs.getString("avatar"));
				objSocioReuni.setDegree(rs.getString("degree"));
				objSocioReuni.setStatus(rs.getString("status"));
				objSocioReuni.setCellphone(rs.getString("cellphone"));
				objSocioReuni.setEmail(rs.getString("email"));
				objSocioReuni.setGender(rs.getString("gender"));
				objSocioReuni.setBirth(rs.getString("birth"));
				
				return objSocioReuni;
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
	
	public static int consultarCodigoSocioReuni(int pCodigoSocioReuni) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			
			String SQL = "select * from tb_socio_reuni";
			
			if(pCodigoSocioReuni!=0){
				SQL = "select id from tb_socio_reuni where id="+pCodigoSocioReuni;
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
	
	public static void zerarDadosAuxiliarReuni(int pCodigoNucleoReuni) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;

		String SQL = "delete from tb_auxiliar_reuni where id_nucleo="+pCodigoNucleoReuni;
		
		//System.out.println(SQL);
		
		try {
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public static void zerarDadosSocioReuni(int pCodigoNucleoReuni) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;

		String SQL = "delete from tb_socio_reuni where id_nucleo="+pCodigoNucleoReuni;
		
		//System.out.println(SQL);
		
		try {
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void atualizarSocioReuni(SocioReuni pSocioReuni) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		
		try {
			String SQL = "update tb_socio_reuni set name=?,cpf=?,avatar=?,nickname=?,gender=?,birth=?,age=?,id_degree=?," +
					"degree=?,id_status=?,status=?,cellphone=?,phone=?,email=?,email_alt=?,address=? where id="+pSocioReuni.getId();

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);

			ps.setString(1, pSocioReuni.getName());
			ps.setString(2, pSocioReuni.getCpf());
			ps.setString(3, pSocioReuni.getAvatar());
			ps.setString(4, pSocioReuni.getNickname());
			ps.setString(5, pSocioReuni.getGender());
			ps.setString(6, pSocioReuni.getBirth());
			ps.setString(7, pSocioReuni.getAge());
			ps.setInt(8, pSocioReuni.getId_degree());
			ps.setString(9, pSocioReuni.getDegree());
			ps.setInt(10, pSocioReuni.getId_status());
			ps.setString(11, pSocioReuni.getStatus());
			ps.setString(12, pSocioReuni.getCellphone());
			ps.setString(13, pSocioReuni.getPhone());
			ps.setString(14, pSocioReuni.getEmail());
			ps.setString(15, pSocioReuni.getEmail_alt());
			ps.setString(16, pSocioReuni.getAddress());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void incluirAuxiliarReuni(SocioReuni pSocioReuni,int pIdNucleo,int pIdNucleoReuni) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		String cpfSocioReuni;
		
		try {
			String SQL = "insert into tb_auxiliar_reuni values(?,?,?,?,?)";

			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);

			ps.setInt(1, pIdNucleo);
			ps.setInt(2, pIdNucleoReuni);
			ps.setInt(3, pSocioReuni.getId());
			
			String nomeSocioReuni = pSocioReuni.getName();
			nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("’")?nomeSocioReuni.replace("’", "`"):nomeSocioReuni;
			nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("'")?nomeSocioReuni.replace("'", "`"):nomeSocioReuni;
			
			cpfSocioReuni = pSocioReuni.getCpf()==null?"0":pSocioReuni.getCpf().substring(0, pSocioReuni.getCpf().length()>11?11:pSocioReuni.getCpf().length());

			ps.setString(4, nomeSocioReuni);
			ps.setString(5, cpfSocioReuni);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	//-------------------------
	
	public void incluirSocioReuni(SocioReuni pSocioReuni,int pIdNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
	
		String nomeSocioReuni;
		String cpfSocioReuni;
		String avatar;
		String nickName;
		String genero;
		String nascimento;
		String idade;
		int idGrau;
		String grau;
		int idStatus;
		String status;
		String numeroCelular;
		String numeroTelefone;
		String email;
		String emailAlt;
		String endereco;
		
		try {
			String SQL = "insert into tb_socio_reuni values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);

			/*
			System.out.println("insert into tb_socio_reuni values("+pIdNucleo+","+pSocioReuni.getId()+","+nomeSocioReuni+", cpf: "+pSocioReuni.getCpf().substring(0, 11)
					+","+pSocioReuni.getAvatar()+","+pSocioReuni.getNickname()+","+pSocioReuni.getGender()+","+pSocioReuni.getBirth()+","+pSocioReuni.getAge()
					+","+IdDegree+","+pSocioReuni.getDegree()+","+IdStatus+","+pSocioReuni.getStatus()+","+pSocioReuni.getCellphone()+","+pSocioReuni.getPhone()
					+","+pSocioReuni.getEmail()+","+EmailAlt+","+pSocioReuni.getAddress()+")");
			*/

			//testar o acesso com esse cpf 88062082253 N estrela Dalva

			nomeSocioReuni = pSocioReuni.getName();
			nomeSocioReuni = nomeSocioReuni==null?"":pSocioReuni.getName().substring(0, pSocioReuni.getName().length()>100?100:pSocioReuni.getName().length());
			
			nomeSocioReuni.trim();
			
			if(nomeSocioReuni.length()>1){
				nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("’")?nomeSocioReuni.replace("’", "`"):nomeSocioReuni;
				nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("'")?nomeSocioReuni.replace("'", "`"):nomeSocioReuni;
				//nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("?")?nomeSocioReuni.replace("?", "."):nomeSocioReuni;
			}

			cpfSocioReuni = pSocioReuni.getCpf()==null?"0":pSocioReuni.getCpf().substring(0, pSocioReuni.getCpf().length()>11?11:pSocioReuni.getCpf().length());
			avatar = pSocioReuni.getAvatar()==null?"": pSocioReuni.getAvatar().substring(0, pSocioReuni.getAvatar().length()>150?150:pSocioReuni.getAvatar().length());
			nickName = pSocioReuni.getNickname()==null?"":pSocioReuni.getNickname().substring(0, pSocioReuni.getNickname().length()>100?100:pSocioReuni.getNickname().length());
			genero = pSocioReuni.getGender()==null?"":pSocioReuni.getGender().substring(0, pSocioReuni.getGender().length()>100?100:pSocioReuni.getGender().length());
			nascimento = pSocioReuni.getBirth()==null?"":pSocioReuni.getBirth().substring(0, pSocioReuni.getBirth().length()>10?10:pSocioReuni.getBirth().length());
			idade = pSocioReuni.getAge()==null?"":pSocioReuni.getAge().substring(0, pSocioReuni.getAge().length()>3?3:pSocioReuni.getAge().length());
			idGrau = pSocioReuni.getId_degree();
			grau = pSocioReuni.getDegree()==null?"":pSocioReuni.getDegree().substring(0, pSocioReuni.getDegree().length()>10?10:pSocioReuni.getDegree().length());
			idStatus = pSocioReuni.getId_status();
			status = pSocioReuni.getStatus()==null?"":pSocioReuni.getStatus().substring(0, pSocioReuni.getStatus().length()>50?50:pSocioReuni.getStatus().length());
			numeroCelular = pSocioReuni.getCellphone()==null?"":pSocioReuni.getCellphone().substring(0, pSocioReuni.getCellphone().length()>100?100:pSocioReuni.getCellphone().length());
			numeroTelefone = pSocioReuni.getPhone()==null?"":pSocioReuni.getPhone().substring(0, pSocioReuni.getPhone().length()>100?100:pSocioReuni.getPhone().length());
			email = pSocioReuni.getEmail()==null?"":pSocioReuni.getEmail().substring(0, pSocioReuni.getEmail().length()>100?100:pSocioReuni.getEmail().length());
			emailAlt = pSocioReuni.getEmail_alt()==null?"":pSocioReuni.getEmail_alt().substring(0, pSocioReuni.getEmail_alt().length()>100?100:pSocioReuni.getEmail_alt().length());
			endereco = pSocioReuni.getAddress()==null?"":pSocioReuni.getAddress().substring(0, pSocioReuni.getAddress().length()>150?150:pSocioReuni.getAddress().length());
			
			ps.setInt(1, pIdNucleo);
			ps.setInt(2, pSocioReuni.getId());
			ps.setString(3, nomeSocioReuni);
			ps.setString(4, cpfSocioReuni);
			ps.setString(5, avatar);
			ps.setString(6, nickName);
			ps.setString(7, genero);
			ps.setString(8, nascimento);
			ps.setString(9, idade);
			ps.setInt(10, idGrau);
			ps.setString(11, grau);
			ps.setInt(12, idStatus);
			ps.setString(13, status);
			ps.setString(14, numeroCelular);
			ps.setString(15, numeroTelefone);
			ps.setString(16, email);
			ps.setString(17, emailAlt);
			ps.setString(18, endereco);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pIdNucleo = 0;
			pSocioReuni = null;
			nomeSocioReuni = null;
			cpfSocioReuni = null;
			avatar = null;
			nickName = null;
			genero = null;
			nascimento = null;
			idade = null;
			idGrau = 0;
			grau = null;
			idStatus = 0;
			status = null;
			numeroCelular = null;
			numeroTelefone = null;
			email = null;
			emailAlt = null;
			endereco = null;

			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public static ArrayList<SocioReuni> listarAuxiliarSocioReuni(Nucleo pNucleo) throws StuDAOException{ //int pNucleo
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
            ArrayList<SocioReuni> listTodas = null;
            SocioReuni socioReuni;

			String SQL = "select *from tb_auxiliar_reuni where cd_nucleo="+pNucleo.getCodigo()+" order by name";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<SocioReuni>();
                do {
                	
                	socioReuni = new SocioReuni();
                    
                	socioReuni.setId(rs.getInt("id"));
                	socioReuni.setName(rs.getString("name"));
                	socioReuni.setCpf(rs.getString("cpf"));
                	
                    listTodas.add(socioReuni);
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
	
	public static void excluirSocioReuni(SocioReuni pSocioReuni,Nucleo pNucleo) throws StuDAOException{ //int pNucleo

		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "delete from tb_auxiliar_reuni where cd_nucleo="+pNucleo.getCodigo()+" and id="+pSocioReuni.getId() ;
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
}