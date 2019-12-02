package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Cargo;
import beans.Socio;

import logica.StuConecta;

public class CargoDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static Cargo consultarCargo(Cargo pCar) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select * from tb_cargo";
			if(pCar.getSigla()!=null){
				SQL = SQL+" where upper(nm_sigla) like '%"+pCar.getSigla().toUpperCase()+"%'";
			}
			if(pCar.getDescricao()!=null){
				SQL = "select * from tb_cargo where upper(nm_descricao) like '%"+pCar.getDescricao().toUpperCase()+"%'"; 
			}
			if(pCar.getCodigo()!=0){
				SQL = "select * from tb_cargo where cd_cargo="+pCar.getCodigo(); 
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				Cargo objCar = new Cargo();
				objCar.setCodigo(rs.getInt(1));
				objCar.setDescricao(rs.getString(2));
				objCar.setSigla(rs.getString(3));
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

	public void incluirCargo(Cargo pCar) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("insert into tb_cargo values(cd_cargo,?, ?)");
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pCar.getDescricao());
			ps.setString(2, pCar.getSigla());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarCargo(Cargo pCar) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("update tb_cargo set nm_descricao=?, nm_sigla=? where cd_cargo="+Integer.toString(pCar.getCodigo()));
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pCar.getDescricao());
			ps.setString(2, pCar.getSigla());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirCargo(Cargo pCar) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_cargo where cd_cargo="+Integer.toString(pCar.getCodigo()));
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
	} //verificar esta exclusão, vínculo com cargos ativos/ocupados 
	
	public static ArrayList<Cargo> listarCargo() throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Cargo> listTodas = null;
            Cargo itemCar;

            String SQL = ("select * from tb_cargo order by nm_descricao");
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Cargo>();
                do {
                    itemCar = new Cargo();
                    itemCar.setCodigo(rs.getInt("cd_cargo"));
                    itemCar.setDescricao(rs.getString("nm_descricao"));
                    itemCar.setSigla(rs.getString("nm_sigla"));
                    
                    listTodas.add(itemCar);
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
	
	public static ArrayList<Cargo> listarCargoFiltro(String pCom) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Cargo> listTodas = null;
            Cargo itemCar;

            String SQL = "select * from tb_cargo where upper(nm_descricao) like '%"+pCom.toUpperCase()+"%' order by upper(nm_descricao) <> 'PRESIDENT%'";
            
            //System.out.println(SQL);
            
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Cargo>();
                do {
                    itemCar = new Cargo();
                    itemCar.setCodigo(rs.getInt("cd_cargo"));
                    itemCar.setDescricao(rs.getString("nm_descricao"));
                    itemCar.setSigla(rs.getString("nm_sigla"));
                    
                    listTodas.add(itemCar);
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
	
	public static Socio consultarNomeFiltro(int pCargo, String pData, int pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            Socio itemSocio = null;

            String SQL = "select * from tb_socio where cd_socio in ( select cd_socio from tb_historico_socio where cd_nucleo="+pNucleo+" and cd_cargo="+pCargo
            +" and dt_data = ( select max(dt_data) from tb_historico_socio where cd_nucleo="+pNucleo+" and cd_cargo="+pCargo
            +" and dt_data <= last_day(date('"+pData+"'))))";
            
            //System.out.println(SQL);
            
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                do {
                    itemSocio = new Socio();
                    itemSocio.setCodigo(rs.getInt("cd_socio"));
                    itemSocio.setNome(rs.getString("nm_nome"));
                    
                } while (rs.next());
            }
            return itemSocio;
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

	public static Cargo consultarCargoHistorico(int pCargo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            Cargo itemCargo = null;

            String SQL = "select * from tb_historico_socio where cd_cargo="+pCargo;
            
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                do {
                    itemCargo = new Cargo();
                    itemCargo.setCodigo(rs.getInt("cd_cargo"));
                } while (rs.next());
            }
            return itemCargo;
		}
		catch (SQLException sqle){
			throw new StuDAOException("Erro -->"+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
	}

}
