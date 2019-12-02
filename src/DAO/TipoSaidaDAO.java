package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Nucleo;
import beans.TipoSaida;

public class TipoSaidaDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static TipoSaida consultarTipoSaida(TipoSaida pTSai) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select * from tb_tipo_saida_nucleo where cd_tipo_saida=cd_tipo_saida";
			if(pTSai.getDescricao()!=null){
				SQL = "select * from tb_tipo_saida_nucleo where upper(nm_descricao) like '%"+pTSai.getDescricao().toUpperCase()+"%'"; 
			}
			if(pTSai.getCodigo()!=0){
				SQL = "select * from tb_tipo_saida_nucleo where cd_tipo_saida="+pTSai.getCodigo(); 
			}
			SQL = SQL +" and cd_nucleo="+pTSai.getNucleo().getCodigo();
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				TipoSaida objTipSai = new TipoSaida();
				objTipSai.setCodigo(rs.getInt(1));

				Nucleo objNucleo = new Nucleo();
				objNucleo.setCodigo(rs.getInt(2));
				
				objTipSai.setNucleo(objNucleo);
				
				objTipSai.setDescricao(rs.getString(3));
				objTipSai.setAtivo(rs.getString(4));
				
				return objTipSai;
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

	public void incluirTipoSaida(TipoSaida pTSai) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "insert into tb_tipo_saida_nucleo values(cd_tipo_saida,?,?,?)";
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, pTSai.getNucleo().getCodigo());
			ps.setString(2, pTSai.getDescricao());
			ps.setString(3, pTSai.getAtivo());
			
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarTipoSaida(TipoSaida pTSai) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_tipo_saida_nucleo set nm_descricao=?,nm_ativo=? where cd_tipo_saida="+pTSai.getCodigo()
			+" and cd_nucleo="+pTSai.getNucleo().getCodigo();
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pTSai.getDescricao());
			ps.setString(2, pTSai.getAtivo());
			
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirTipoSaida(TipoSaida pTSai) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "delete from tb_tipo_saida_nucleo where cd_tipo_saida="+pTSai.getCodigo()
			+" and cd_nucleo="+pTSai.getNucleo().getCodigo();
			
			//System.out.println(SQL);
			
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
	
	public static ArrayList<TipoSaida> listarTipoSaida(int pSai, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<TipoSaida> listTodas = null;
            TipoSaida itemTipSai;

			String SQL = ("select * from tb_tipo_saida_nucleo where cd_nucleo="+pNucleo.getCodigo()+" order by nm_descricao");
			if(pSai==1){
				SQL = ("select * from tb_tipo_saida_nucleo where nm_ativo='S' and cd_nucleo="+pNucleo.getCodigo()+" order by nm_descricao");
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<TipoSaida>();
                do {
                    itemTipSai = new TipoSaida();
                    itemTipSai.setCodigo(rs.getInt("cd_tipo_saida"));
                    itemTipSai.setDescricao(rs.getString("nm_descricao"));
                    itemTipSai.setAtivo(rs.getString("nm_ativo"));
                    
                    listTodas.add(itemTipSai);
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
	
	public static boolean VerificaTipoSaidaHistorico(TipoSaida pTipoSaida) throws StuDAOException{

		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {

			String SQL = "select *from tb_saida where cd_tipo_saida="+pTipoSaida.getCodigo();
			if(pTipoSaida.getNucleo()!=null){
				SQL = SQL+" and cd_nucleo="+pTipoSaida.getNucleo().getCodigo();
			}

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}else {
				return false;				
			}	
		}
		catch (SQLException sqle){
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		
	}
}
