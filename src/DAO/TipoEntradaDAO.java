package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Nucleo;
import beans.TipoEntrada;

public class TipoEntradaDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static TipoEntrada consultarTipoEntrada(TipoEntrada pTEnt) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			String SQL = "select cd_tipo_entrada,cd_nucleo,nm_descricao,vl_valor,nm_mensal,nm_ativo"
					+" from tb_tipo_entrada_nucleo where cd_tipo_entrada=cd_tipo_entrada";

			if(pTEnt.getDescricao()!=null){
				SQL = SQL+" and upper(nm_descricao) like '%"+pTEnt.getDescricao().toUpperCase()+"%'"; 
			}

			if(pTEnt.getCodigo()!=0){
				SQL = "select cd_tipo_entrada,cd_nucleo,nm_descricao,vl_valor,nm_mensal,nm_ativo"
				+" from tb_tipo_entrada_nucleo where cd_tipo_entrada=cd_tipo_entrada and cd_tipo_entrada="+pTEnt.getCodigo(); 
			}
			SQL = SQL+" and cd_nucleo="+pTEnt.getNucleo().getCodigo();
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				TipoEntrada objTipEnt = new TipoEntrada();
				Nucleo objNucleo = new Nucleo();
				
				objTipEnt.setCodigo(rs.getInt(1));
				
				objNucleo.setCodigo(rs.getInt(2));
				objTipEnt.setNucleo(objNucleo);
				
				objTipEnt.setDescricao(rs.getString(3));
				objTipEnt.setValor(rs.getDouble(4));
				objTipEnt.setMensal(rs.getString(5));
				objTipEnt.setAtivo(rs.getString(6));
				
				return objTipEnt;
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

	public void incluirTipoEntrada(TipoEntrada pTEnt) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "insert into tb_tipo_entrada_nucleo values(cd_tipo_entrada,?,?,?,?,?)";
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			
			ps.setInt(1, pTEnt.getNucleo().getCodigo());
			ps.setString(2, pTEnt.getDescricao());
			ps.setDouble(3, pTEnt.getValor());
			ps.setString(4, pTEnt.getMensal());
			ps.setString(5, pTEnt.getAtivo());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void alterarTipoEntrada(TipoEntrada pTEnt) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_tipo_entrada_nucleo set vl_valor=?,nm_mensal=?,nm_ativo=?,nm_descricao=? where cd_tipo_entrada="+pTEnt.getCodigo()
					+" and cd_nucleo="+pTEnt.getNucleo().getCodigo();
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			
			ps.setDouble(1, pTEnt.getValor());
			ps.setString(2, pTEnt.getMensal());
			ps.setString(3, pTEnt.getAtivo());
			ps.setString(4, pTEnt.getDescricao());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}
	
	public void excluirTipoEntrada(TipoEntrada pTEnt) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			String SQL = "delete from tb_tipo_entrada_nucleo where cd_nucleo="+pTEnt.getNucleo().getCodigo()+" and cd_tipo_entrada="+pTEnt.getCodigo();
			
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
	
	public static ArrayList<TipoEntrada> listarTipoEntrada(int pNucleo, int pTip) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
            ArrayList<TipoEntrada> listTodas = null;
            TipoEntrada itemTipEnt;
            Nucleo objNucleo;

            String SQL="select cd_tipo_entrada,cd_nucleo,nm_descricao,vl_valor,nm_mensal,nm_ativo from tb_tipo_entrada_nucleo"+ 
		            " where cd_nucleo="+pNucleo+" order by cd_tipo_entrada<>1,nm_descricao";
			
			if(pTip==1){
				SQL =  "select cd_tipo_entrada,cd_nucleo,nm_descricao,vl_valor,nm_mensal,nm_ativo from tb_tipo_entrada_nucleo where cd_nucleo="+ 
						pNucleo+" and nm_ativo='S' and nm_mensal='N' order by cd_tipo_entrada<>1,nm_descricao";
			}
			if(pTip==2){
				SQL =  "select cd_tipo_entrada,cd_nucleo,nm_descricao,vl_valor,nm_mensal,nm_ativo from tb_tipo_entrada_nucleo where cd_nucleo="+ 
						pNucleo+" and nm_ativo='S' and nm_mensal='S' order by cd_tipo_entrada<>1,nm_descricao";
			}

			if(pTip==3){
				SQL =  "select cd_tipo_entrada,cd_nucleo,nm_descricao,vl_valor,nm_mensal,nm_ativo from tb_tipo_entrada_nucleo where cd_nucleo="+ 
			            pNucleo+" and nm_ativo='S' order by cd_tipo_entrada<>1,nm_descricao";
			}

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<TipoEntrada>();
                do {
                    itemTipEnt = new TipoEntrada();
                    objNucleo = new Nucleo();
                    
                    objNucleo.setCodigo(rs.getInt("cd_nucleo"));
                    itemTipEnt.setNucleo(objNucleo);
                    
                    itemTipEnt.setCodigo(rs.getInt("cd_tipo_entrada"));
                    itemTipEnt.setDescricao(rs.getString("nm_descricao"));
                    itemTipEnt.setValor(rs.getDouble("vl_valor"));
                    itemTipEnt.setMensal(rs.getString("nm_mensal"));
                    itemTipEnt.setAtivo(rs.getString("nm_ativo"));
                    
                    listTodas.add(itemTipEnt);
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
