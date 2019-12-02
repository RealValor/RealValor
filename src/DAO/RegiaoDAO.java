package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Login;
import beans.Regiao;

public class RegiaoDAO {

	static String MensErr = "Erro de acesso aos dados "; 
	public Regiao consultarRegiaoLogin(Login pLogin) throws StuDAOException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		
		try {
			
			String SQL = "select * from tb_regiao";
			if(pLogin.getNucleo().getCodigo()!=0){
				SQL = "select * from tb_regiao where cd_regiao="+pLogin.getRegiao().getCodigo();
			}
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if(rs.next()){
				Regiao objRegiao = new Regiao();
				
				objRegiao.setCodigo(rs.getInt(1));
				objRegiao.setDescricao(rs.getString(2));
				objRegiao.setComplemento(rs.getString(3));

				return objRegiao;
			}
		} 
		catch (SQLException sqle) {
			throw new StuDAOException(MensErr+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return null;
	}

	public Regiao consultarRegiao(int pCodigoRegiao) throws StuDAOException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		
		try {
			
			String SQL = "select * from tb_regiao";
			if(pCodigoRegiao!=0){
				SQL = "select * from tb_regiao where cd_regiao="+pCodigoRegiao;
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if(rs.next()){
				Regiao objRegiao = new Regiao();
				
				objRegiao.setCodigo(rs.getInt(1));
				objRegiao.setDescricao(rs.getString(2));
				objRegiao.setComplemento(rs.getString(3));

				return objRegiao;
			}
		} 
		catch (SQLException sqle) {
			throw new StuDAOException(MensErr+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return null;
	}

	public static ArrayList<Regiao> listarRegiao() throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Regiao> listTodas = null;
            
            Regiao itemRegiao;

            String SQL = "select *from tb_regiao order by cd_regiao";
            
            //System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
                listTodas = new ArrayList<Regiao>();
                do {
                    itemRegiao = new Regiao();
                    
                    itemRegiao.setCodigo(rs.getInt("cd_regiao"));
                    itemRegiao.setDescricao(rs.getString("nm_descricao"));
                    itemRegiao.setComplemento(rs.getString("nm_complemento"));
                    itemRegiao.setListanucleo(NucleoDAO.listarNucleo(rs.getInt("cd_regiao")));
                    
                    listTodas.add(itemRegiao);
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
