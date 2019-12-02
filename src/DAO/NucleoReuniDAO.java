package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logica.StuConecta;
import beans.NucleoReuni;

public class NucleoReuniDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static NucleoReuni consultarNucleoReuni(NucleoReuni pNucleoReuni) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			
			NucleoReuni objNucleoReuni;

			String SQL = "select * from tb_nucleo_reuni";
			
			if(pNucleoReuni!=null){

				if(pNucleoReuni.getId_nucleo()!=0){
					SQL = "select * from tb_nucleo_reuni where id_nucleo="+pNucleoReuni.getId_nucleo();
				}
				
			}

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if(rs.next()){
				objNucleoReuni = new NucleoReuni();
				
				objNucleoReuni.setId_nucleo(rs.getInt(1));
				objNucleoReuni.setTx_nucleo_nome(rs.getString(2));
				objNucleoReuni.setTx_regiao(rs.getString(4));
				objNucleoReuni.setTx_cnpj(rs.getString(13));
				objNucleoReuni.setDt_atualizacao_socios(rs.getDate(15));
				
				return objNucleoReuni;
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

	public static int consultarCodigoNucleoReuni(int pCodigoNucleo) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			
			String SQL = "select * from tb_realvalor_reuni";
			
			if(pCodigoNucleo!=0){
				SQL = "select id_nucleo from tb_realvalor_reuni where cd_nucleo="+pCodigoNucleo;
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

	public void incluirNucleoReuni(NucleoReuni pNucleoReuni) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "insert into tb_nucleo_reuni values(?,?,?,?,?,STR_TO_DATE(?, '%d-%m-%Y'),?,?,?,?,?,?,?,?,?)";
			conn = StuConecta.getConnection();

			//System.out.println(SQL);
			
			ps = conn.prepareStatement(SQL);

			ps.setInt(1, pNucleoReuni.getId_nucleo());
			ps.setString(2, pNucleoReuni.getTx_nucleo_nome());
			ps.setInt(3, pNucleoReuni.getId_nucleo_tipo());
			ps.setInt(4, pNucleoReuni.getId_regiao());
			ps.setString(5, pNucleoReuni.getTx_regiao());
			
			ps.setString(6,pNucleoReuni.getDt_criacao());
			
			ps.setString(7,pNucleoReuni.getTx_endereco());
			ps.setString(8,pNucleoReuni.getTx_cidade());
			ps.setString(9,pNucleoReuni.getTx_estado());
			ps.setString(10,pNucleoReuni.getTx_pais());
			ps.setString(11,pNucleoReuni.getNu_lat());
			ps.setString(12,pNucleoReuni.getNu_long());
			ps.setString(13,pNucleoReuni.getTx_cnpj());
			ps.setString(14,pNucleoReuni.getTx_repre_resp());
			ps.setString(15,null);
			
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void alterarNucleoReuni(NucleoReuni pNucleoReuni) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		
		try {
			String SQL = "update tb_nucleo_reuni set dt_atualizacao_socios=? where id_nucleo="+pNucleoReuni.getId_nucleo();

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);

			ps.setDate(1, null);
			if(pNucleoReuni.getDt_atualizacao_socios()!=null){
				ps.setDate(1, new java.sql.Date(pNucleoReuni.getDt_atualizacao_socios().getTime()));
			}

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
