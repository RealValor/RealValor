package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Grau;

import logica.StuConecta;


public class GrauDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public Grau consultarGrau(Grau pGrau) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select * from tb_grau";
			if(pGrau.getGrau()!=null){
				SQL = SQL+" where upper(nm_grau) like '%"+pGrau.getGrau().toUpperCase()+"%'";
			}
			if(pGrau.getDescricao()!=null){
				SQL = "select * from tb_grau where upper(nm_descricao) like '%"+pGrau.getDescricao().toUpperCase()+"%'"; 
			}
			if(pGrau.getCodigo()!=0){
				SQL = "select * from tb_grau where cd_grau="+pGrau.getCodigo(); 
			}

			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				Grau objGrau = new Grau();
				objGrau.setCodigo(rs.getInt(1));
				objGrau.setDescricao(rs.getString(2));
				objGrau.setGrau(rs.getString(3));
				return objGrau;
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

	public void incluirGrau(Grau pGrau) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("insert into tb_grau values(cd_grau,?, ?)");
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pGrau.getDescricao());
			ps.setString(2, pGrau.getGrau());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarGrau(Grau pGrau) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("update tb_grau set nm_descricao=?, nm_grau=? where cd_grau="+Integer.toString(pGrau.getCodigo()));
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pGrau.getDescricao());
			ps.setString(2, pGrau.getGrau());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirGrau(Grau pGrau) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_grau where cd_grau="+Integer.toString(pGrau.getCodigo()));
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
	
	public static ArrayList<Grau> listarGrau() throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Grau> listTodas = null;
            Grau itemGrau;

			String SQL = ("select * from tb_grau order by nm_descricao");
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Grau>();
                do {
                    itemGrau = new Grau();
                    itemGrau.setCodigo(rs.getInt("cd_grau"));
                    itemGrau.setDescricao(rs.getString("nm_descricao"));
                    itemGrau.setGrau(rs.getString("nm_grau"));
                    
                    listTodas.add(itemGrau);
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
