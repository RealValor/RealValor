package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Login;
import beans.Nucleo;
import beans.Regiao;

public class TesoureiroRegionalDAO {

	String MensErr = "Erro de acesso aos dados "; 
	public static ArrayList<Login> consultarTesoureiroRegional(Login pTesoureiro) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
            ArrayList<Login> listTodas = null;

			String SQL = "SELECT (select r.cd_regiao from tb_regiao r,tb_nucleo n where r.cd_regiao=n.cd_regiao and l.cd_nucleo=cd_nucleo) regiao,"
			+" s.cd_socio,(select nm_nome_nucleo from tb_nucleo where l.cd_nucleo=cd_nucleo) nucleo,cd_nucleo"
			+" from tb_login l, tb_socio s where l.cd_socio=s.cd_socio and vl_nivel=4";

			if(pTesoureiro!=null&&pTesoureiro.getUsuario()!=0){
				SQL = SQL+" and l.cd_socio="+pTesoureiro.getUsuario();
			}
			SQL = SQL+" order by regiao, nucleo";

			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if(rs.next()){
				listTodas = new ArrayList<Login>();
				do {
					
					Login objLogin = new Login();

					Regiao objRegiao = new Regiao();
					Nucleo objNucleo = new Nucleo();

					RegiaoDAO objRegiaoDAO = new RegiaoDAO();
					LoginDAO objLoginDAO = new LoginDAO();

					objRegiao=objRegiaoDAO.consultarRegiao(rs.getInt(1)); 
					//System.out.println("Região "+objRegiao.getCodigo()+" "+objRegiao.getDescricao());
					
					objLogin.setRegiao(objRegiao);

					objNucleo.setNome(rs.getString(3));
					objNucleo.setCodigo(rs.getInt(4));

					objLogin.setNucleo(objNucleo);
					
					objLogin.setUsuario(rs.getInt(2));
					objLogin.setCpf(0);
					objLogin = objLoginDAO.consultarUsuNome(objLogin);
					//System.out.println("objLogin "+objLogin.getRegiao().getDescricao());
					
					listTodas.add(objLogin);
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