package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Nucleo;
import beans.SocioValor;

public class SocioValorDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public SocioValor consultarSocVal(SocioValor pSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_socio_valor";
			if(pSoc.getCodSocio()!=0&&pSoc.getCodEntrada()!=0){
				SQL = "select * from tb_socio_valor where cd_socio="+pSoc.getCodSocio()+" and cd_tipo_entrada="+pSoc.getCodEntrada(); 
			}

			//System.out.println("SQL: "+SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				SocioValor objSocVal = new SocioValor();
				
				objSocVal.setCodSocio(rs.getInt(1));
				objSocVal.setCodEntrada(rs.getInt(2));
				objSocVal.setValor(rs.getDouble(3));
				
				return objSocVal;
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

	public static SocioValor consultarVal(int pSoc, int pEnt) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_socio_valor";
			if(pSoc!=0&&pEnt!=0){
				SQL = "select * from tb_socio_valor where cd_socio="+pSoc+" and cd_tipo_entrada="+pEnt; 
			}
			
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if(rs.next()){

				SocioValor objSocVal = new SocioValor();
				
				objSocVal.setCodSocio(rs.getInt(1));
				objSocVal.setCodEntrada(rs.getInt(2));
				objSocVal.setValor(rs.getDouble(3));
				
				return objSocVal;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return null;
	}

	public void incluirValorSocio(SocioValor pSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("insert into tb_socio_valor values(?, ?, ?)");
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);

			ps.setInt(1, pSoc.getCodSocio());
			ps.setInt(2, pSoc.getCodEntrada());
			ps.setDouble(3, pSoc.getValor());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarValorSocio(SocioValor pSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_socio_valor set vl_valor=?"+
			" where cd_socio="+pSoc.getCodSocio()+" and cd_tipo_entrada="+pSoc.getCodEntrada();

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setDouble(1, pSoc.getValor());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirSoc(SocioValor pSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "delete from tb_socio_valor where cd_socio="+pSoc.getCodSocio()+" and cd_tipo_entrada="+pSoc.getCodEntrada();
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
	
	public static ArrayList<SocioValor> listarSocioValor(SocioValor pSoc, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<SocioValor> listTodas = null;
            SocioValor itemSocVal;

            String SQL = "select * from tb_socio_valor v, tb_socio_nucleo n where v.cd_socio=n.cd_socio and n.cd_nucleo="+pNucleo.getCodigo();
            
			if(pSoc.getCodSocio()!=0){
				SQL = SQL+" and n.cd_socio="+pSoc.getCodSocio(); 
				if(pSoc.getCodEntrada()!=0){
					SQL = SQL+" and cd_tipo_entrada="+pSoc.getCodEntrada(); 
				}
			}else{
				if(pSoc.getCodEntrada()!=0){
					SQL = SQL+" and cd_tipo_entrada="+pSoc.getCodEntrada(); 
				}
			}
			SQL=SQL+" order by n.cd_socio";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<SocioValor>();
                do {
                    itemSocVal = new SocioValor();
                    itemSocVal.setCodSocio(rs.getInt("cd_socio"));
                    itemSocVal.setCodEntrada(rs.getInt("cd_tipo_entrada"));
                    itemSocVal.setValor(rs.getDouble("vl_valor"));
                    
                    listTodas.add(itemSocVal);
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
