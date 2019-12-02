package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.GrauParentesco;

import logica.StuConecta;

public class GrauParentescoDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static GrauParentesco consultarGrauParentesco(GrauParentesco pGrauPar) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select * from tb_grau_parentesco";
			if(pGrauPar.getDescricao()!=null){
				SQL = "select * from tb_grau_parentesco where upper(nm_descricao) like '%"+pGrauPar.getDescricao().toUpperCase()+"%'"; 
			}
			if(pGrauPar.getCodigo()!=0){
				SQL = "select * from tb_grau_parentesco where cd_grau_parentesco="+pGrauPar.getCodigo(); 
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				GrauParentesco objCar = new GrauParentesco();
				objCar.setCodigo(rs.getInt(1));
				objCar.setDescricao(rs.getString(2));
				return objCar;
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

	//------------------------------------------------------
	public static GrauParentesco consultarGrauParentescoExato(GrauParentesco pGrauPar) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select * from tb_grau_parentesco";
			if(pGrauPar.getDescricao()!=null){
				SQL = "select * from tb_grau_parentesco where upper(nm_descricao) = '"+pGrauPar.getDescricao().toUpperCase()+"'"; 
			}
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				GrauParentesco objCar = new GrauParentesco();
				objCar.setCodigo(rs.getInt(1));
				objCar.setDescricao(rs.getString(2));
				return objCar;
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

	//------------------------------------------------------
	public void incluirGrauParentesco(GrauParentesco pGrauPar) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("insert into tb_grau_parentesco values(cd_grau_parentesco,?)");
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pGrauPar.getDescricao());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarGrauParentesco(GrauParentesco pGrauPar) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("update tb_grau_parentesco set nm_descricao=? where cd_grau_parentesco="+Integer.toString(pGrauPar.getCodigo()));
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pGrauPar.getDescricao());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirGrauParentesco(GrauParentesco pGrauPar) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_grau_parentesco where cd_grau_parentesco="+Integer.toString(pGrauPar.getCodigo()));
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
	
	public static ArrayList<GrauParentesco> listarGrauParentesco() throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<GrauParentesco> listTodas = null;
            GrauParentesco itemGrauParentesco;

            String SQL = ("select * from tb_grau_parentesco order by nm_descricao");
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<GrauParentesco>();
                do {
                    itemGrauParentesco = new GrauParentesco();
                    itemGrauParentesco.setCodigo(rs.getInt("cd_grau_parentesco"));
                    itemGrauParentesco.setDescricao(rs.getString("nm_descricao"));
                    
                    listTodas.add(itemGrauParentesco);
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
	
	public static ArrayList<GrauParentesco> listarGrauParentesco(String pGrauPar) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<GrauParentesco> listTodas = null;
            GrauParentesco itemGrauParentesco;

            String SQL = "select * from tb_grau_parentesco where upper(nm_descricao) like '%"+pGrauPar.toUpperCase()+"%' order by nm_descricao";
            
            //System.out.println(SQL);
            
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<GrauParentesco>();
                do {
                    itemGrauParentesco = new GrauParentesco();
                    itemGrauParentesco.setCodigo(rs.getInt("cd_grau_parentesco"));
                    itemGrauParentesco.setDescricao(rs.getString("nm_descricao"));
                    
                    listTodas.add(itemGrauParentesco);
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

	public static GrauParentesco consultarGrauParentescoHistorico(int pGrauParentesco) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            GrauParentesco itemGrauParentesco = null;

            String SQL = "select * from tb_socio_dependente where cd_grau_parentesco="+pGrauParentesco;
            
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                do {
                    itemGrauParentesco = new GrauParentesco();
                    itemGrauParentesco.setCodigo(rs.getInt("cd_grau_parentesco"));
                } while (rs.next());
            }
            return itemGrauParentesco;
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}
}
