package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Cidade;


public class CidadeDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static Cidade consultarCidade(Cidade pCidade) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			Cidade objCidade;

			String SQL = "select * from tb_cidade";
			
			if(pCidade!=null){
				SQL = "select * from tb_cidade where cd_cidade="+pCidade.getCodigo();
			}

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				objCidade = new Cidade();
				
				objCidade.setCodigo(rs.getInt(1));
				objCidade.setNome(rs.getString(2));
				objCidade.setUF(rs.getString(3));
				objCidade.setCodigoIBGE(4);
				
				return objCidade;
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

	public static ArrayList<Cidade> listarCidade(String pNome) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Cidade> listTodas = null;
            Cidade itemCidade;

			String SQL = "select * from tb_cidade where upper(nm_nome_cidade) like '%"+pNome.toUpperCase()+"%' order by nm_nome_cidade";
			//System.out.println(SQL);
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Cidade>();
                do {
                    itemCidade = new Cidade();
                    itemCidade.setCodigo(rs.getInt("cd_Cidade"));
                    itemCidade.setNome(rs.getString("nm_nome_cidade"));
                    itemCidade.setUF(rs.getString("cd_uf"));
                    itemCidade.setCodigoIBGE(rs.getInt("cd_ibge"));
                    
                    listTodas.add(itemCidade);
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

	/*
	public void incluirCidade(Cidade pBan) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("insert into tb_Cidade values(cd_Cidade,?, ?)");
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pBan.getDescricao());
			ps.setString(2, pBan.getSigla());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarCidade(Cidade pBan) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("update tb_Cidade set nm_descricao=?, nm_sigla=? where cd_Cidade="+Integer.toString(pBan.getCodigo()));
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pBan.getDescricao());
			ps.setString(2, pBan.getSigla());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirCidade(Cidade pBan) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_Cidade where cd_Cidade="+Integer.toString(pBan.getCodigo()));
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
	
	public static ArrayList<Cidade> listarCidade() throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Cidade> listTodas = null;
            Cidade itemCidade;

			String SQL = ("select * from tb_Cidade where nm_descricao <> '' order by nm_descricao");
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Cidade>();
                do {
                    itemCidade = new Cidade();
                    itemCidade.setCodigo(rs.getInt("cd_Cidade"));
                    itemCidade.setDescricao(rs.getString("nm_descricao"));
                    itemCidade.setSigla(rs.getString("nm_sigla"));
                    
                    listTodas.add(itemCidade);
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
	*/
}
