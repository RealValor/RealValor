package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.TipoDocumento;

import logica.StuConecta;

public class TipoDocumentoDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static TipoDocumento consultarTipoDocumento(TipoDocumento pTDoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			String SQL = "select * from tb_tipo_documento";
			if(pTDoc.getDescricao()!=null){
				SQL = "select * from tb_tipo_documento where upper(nm_descricao) like '%"+pTDoc.getDescricao().toUpperCase()+"%'"; 
			}
			if(pTDoc.getCodigo()!=0){
				SQL = "select * from tb_tipo_documento where cd_tipo_documento="+pTDoc.getCodigo(); 
			}

			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if(rs.next()){
				TipoDocumento objTipDoc = new TipoDocumento();
				objTipDoc.setCodigo(rs.getInt(1));
				objTipDoc.setDescricao(rs.getString(2));
				return objTipDoc;
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

	public void incluirTipoDocumento(TipoDocumento pCar) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("insert into tb_tipo_documento values(cd_tipo_documento,?)");
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pCar.getDescricao());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarTipoDocumento(TipoDocumento pTDoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("update tb_tipo_documento set nm_descricao=? where cd_tipo_documento="+Integer.toString(pTDoc.getCodigo()));
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pTDoc.getDescricao());
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirTipoDocumento(TipoDocumento pTDoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_tipo_documento where cd_tipo_documento="+Integer.toString(pTDoc.getCodigo()));
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
	
	public static ArrayList<TipoDocumento> listarTipoDocumento() throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<TipoDocumento> listTodas = null;
            TipoDocumento itemTipDoc;

			String SQL = ("select * from tb_tipo_documento order by cd_tipo_documento");
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<TipoDocumento>();
                do {
                    itemTipDoc = new TipoDocumento();
                    itemTipDoc.setCodigo(rs.getInt("cd_tipo_documento"));
                    itemTipDoc.setDescricao(rs.getString("nm_descricao"));
                    
                    listTodas.add(itemTipDoc);
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
