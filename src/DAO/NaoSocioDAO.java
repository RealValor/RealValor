package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.NaoSocio;
import beans.Regiao;

import logica.StuConecta;


public class NaoSocioDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public NaoSocio consultarNaoSoc(NaoSocio pNSoc, int pRegiao) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_nsocio where nm_nome is not null";

			if(pNSoc.getNome()!=null){
				SQL = "select * from tb_nsocio where upper(nm_nome) like '%"+pNSoc.getNome().toUpperCase()+"%'"; 
			}

			if(pNSoc.getCodigo()!=0){
				SQL = "select * from tb_nsocio where cd_nsocio="+pNSoc.getCodigo(); 
			}
			SQL = SQL+" and cd_regiao="+pRegiao;
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				NaoSocio objNSoc = new NaoSocio();
				
				objNSoc.setCodigo(rs.getInt(1));
				
				Regiao objRegiao = new Regiao();
				objRegiao.setCodigo(rs.getInt(2));
				
				objNSoc.setRegiao(objRegiao);
				
				objNSoc.setNome(rs.getString(3));
				objNSoc.setTelefone(rs.getString(4));
				objNSoc.setCpfCnpj(rs.getString(5));
				objNSoc.setEmail(rs.getString(6));

				return objNSoc;
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

	public void incluirNaoSoc(NaoSocio pNSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("insert into tb_nsocio values(cd_nsocio, ?, ?, ?, ?, ?)");
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			
			ps.setInt(1, pNSoc.getRegiao().getCodigo());
			ps.setString(2, pNSoc.getNome());
			ps.setString(3, pNSoc.getTelefone());
			ps.setString(4, pNSoc.getCpfCnpj());
			ps.setString(5, pNSoc.getEmail());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarNaoSoc(NaoSocio pNSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("update tb_nsocio set nm_nome=?, nm_telefone=?,cpf_cnpj=?, nm_email=? where cd_nsocio="+Integer.toString(pNSoc.getCodigo()));

			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pNSoc.getNome());
			ps.setString(2, pNSoc.getTelefone());
			ps.setString(3, pNSoc.getCpfCnpj());
			ps.setString(4, pNSoc.getEmail());
			
			//--------------------------
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirSoc(NaoSocio pNSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_nsocio where cd_nsocio="+Integer.toString(pNSoc.getCodigo()));
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
	
	public static ArrayList<NaoSocio> listarNSoc(NaoSocio pNSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<NaoSocio> listTodas = null;
            NaoSocio itemNSoc;

			String SQL = ("select * from tb_nsocio where cd_regiao="+pNSoc.getRegiao().getCodigo());
			if(pNSoc.getNome()!=null){
				SQL = SQL+" and upper(nm_nome) like '%"+pNSoc.getNome().toUpperCase()+"%'"; 
			}
			SQL=SQL+" order by nm_nome";

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<NaoSocio>();
                do {
                    itemNSoc = new NaoSocio();
                    itemNSoc.setCodigo(rs.getInt("cd_nsocio"));
                    itemNSoc.setNome(rs.getString("nm_nome"));
                    itemNSoc.setTelefone(rs.getString("nm_telefone"));
                    itemNSoc.setCpfCnpj(rs.getString("cpf_cnpj"));

                    listTodas.add(itemNSoc);
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
