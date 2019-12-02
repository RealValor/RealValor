package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Bairro;
import beans.Banco;
import beans.Cidade;
import beans.Estado;
import beans.Login;
import beans.Notificacao;
import beans.Nucleo;
import beans.Regiao;

public class NucleoDAO {
	
	static String MensErr = "Erro de acesso aos dados ";
	
	public Nucleo consultarNucleoLogin(Login pLogin) throws StuDAOException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		
		try {
            Cidade objCidade = null;
            Estado objEstado;
            Bairro objBairro;
            Banco objBanco;
            
			String SQL = "select * from tb_nucleo";
			if(pLogin.getNucleo().getCodigo()!=0){
				SQL = "select * from tb_nucleo where cd_nucleo="+pLogin.getNucleo().getCodigo();
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			String strNucleo,vNucleo="Núcleo ";
			
			if(rs.next()){
				Nucleo objNucleo = new Nucleo();
				
				objNucleo.setCodigo(rs.getInt(1));
				
				RegiaoDAO objRegiaoDAO = new RegiaoDAO();
				objNucleo.setRegiao(objRegiaoDAO.consultarRegiao(rs.getInt(2)));
				
				objNucleo.setNome(rs.getString(3));
				
				if(objNucleo.getNome().length()>5){

					strNucleo = rs.getString(3).substring(0, 6);

					if(strNucleo.equalsIgnoreCase("NÚCLEO")||strNucleo.equalsIgnoreCase("NUCLEO")){
						objNucleo.setNome(rs.getString(3).substring(7));
					}else{
						if(objNucleo.getNome().length()>9){
							strNucleo = objNucleo.getNome().substring(0, 10);
							if(strNucleo.equalsIgnoreCase("ASSOCIAÇÃO")||strNucleo.equalsIgnoreCase("ASSOCIAÇAO")||strNucleo.equalsIgnoreCase("ASSOCIACAO")){
								vNucleo="";
							}
						}
					}
				}
				//Refatorar o código acima
				
				objNucleo.setNome(vNucleo+objNucleo.getNome());
			
				objNucleo.setCnpj(rs.getString(4));
				objNucleo.setLogradouro(rs.getString(5));
				
	            objBairro = new Bairro();
	            
	            objBairro.setCodigo(rs.getInt(6));
                objBairro = BairroDAO.consultarBairro(objBairro);

				objNucleo.setBairro(objBairro);
				
				objNucleo.setCep(rs.getString(7));
				
	            objCidade = new Cidade();
	            objCidade.setCodigo(rs.getInt(8));
                objCidade = CidadeDAO.consultarCidade(objCidade);
				
				objNucleo.setCidade(objCidade);
				
	            objEstado = new Estado();
	            objEstado.setCodigo(rs.getInt(9));
                objEstado = EstadoDAO.consultarEstado(objEstado);

	            objNucleo.setEstado(objEstado);

	            objBanco = new Banco();
	            objBanco.setCodigo(rs.getInt(10));
	            objBanco=BancoDAO.consultarBanco(objBanco);
	            
	            objNucleo.setBanco_recebimento(objBanco);
				objNucleo.setEmail(rs.getString(11));
				objNucleo.setObservacao(rs.getString(12));
				objNucleo.setOffline(rs.getString(13));
				
				return objNucleo;
			}
		} 
		catch (SQLException sqle) {
			throw new StuDAOException(MensErr+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return null;
	}

	public static int buscaTotalSociosNucleo(char pSituacao, int pNucleo) throws StuDAOException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		
		try {
			String SQL = "select count(*) from tb_socio_nucleo sn, tb_socio s where s.cd_socio=sn.cd_socio"
					+" and s.nm_situacao='"+pSituacao+"'"+" and cd_nucleo="+pNucleo;
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if(rs.next()){
				return rs.getInt(1);
			}
		} 
		catch (SQLException sqle) {
			throw new StuDAOException(MensErr+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return 0;
	}

	public static void incluirNotificacao(Notificacao pNotificacao) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("insert into tb_notificacao values(?,?,?)");
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, pNotificacao.getNucleo());
			
			ps.setDate(2, null);
			if(pNotificacao.getDatanotificacao()!=null){
				ps.setDate(2, new java.sql.Date(pNotificacao.getDatanotificacao().getTime()));
			}

			ps.setString(3, pNotificacao.getTipo());
			
			//System.out.println("dados "+pNotificacao.getNucleo()+" "+(new java.sql.Date(pNotificacao.getDatanotificacao().getTime()))+" "+pNotificacao.getTipo());
			
			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public static int checarNotificacaoNucleo(Notificacao pNotificacao) throws StuDAOException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		
		try {
			SimpleDateFormat ano = new SimpleDateFormat("yyyy");
			SimpleDateFormat mes = new SimpleDateFormat("MM");

			String SQL = "select ifnull(max(day(dt_data)),0) from tb_notificacao where year(dt_data)="+ano.format(pNotificacao.getDatanotificacao())
					+" and month(dt_data)="+mes.format(pNotificacao.getDatanotificacao())+" and cd_nucleo="+pNotificacao.getNucleo();
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if(rs.next()){
				return rs.getInt(1);
			}
		} 
		catch (SQLException sqle) {
			throw new StuDAOException(MensErr+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return 0;
	}

	public Nucleo consultarNucleo(int pCodigoNucleo) throws StuDAOException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		
		try {
			
            Cidade objCidade;
            Estado objEstado;
            Bairro objBairro;
            
			String SQL = "select * from tb_nucleo";
			if(pCodigoNucleo!=0){
				SQL = "select * from tb_nucleo where cd_nucleo="+pCodigoNucleo;
			}
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if(rs.next()){
				Nucleo objNucleo = new Nucleo();
				
				objNucleo.setCodigo(rs.getInt(1));
				
				RegiaoDAO objRegiaoDAO = new RegiaoDAO();
				objNucleo.setRegiao(objRegiaoDAO.consultarRegiao(rs.getInt(2)));

				//objNucleo.setRegiao(objRegiao);

				objNucleo.setNome(rs.getString(3));
				objNucleo.setCnpj(rs.getString(4));

				objNucleo.setLogradouro(rs.getString(5));
				
	            objBairro = null;
	            //objBairro.setCodigo(rs.getInt(6));
                //objBairro = CidadeDAO.consultarBairro(objBairro);

				objNucleo.setBairro(objBairro);
				
				objNucleo.setCep(rs.getString(7));
				
	            objCidade = null;
	            //objCidade.setCodigo(rs.getInt(8));
                //objCidade = CidadeDAO.consultarCidade(objCidade);
				
				objNucleo.setCidade(objCidade);
				
	            objEstado = null;
	            //objEstado.setCodigo(rs.getInt(9))
                //objEstado = CidadeDAO.consultarEstado(objEstado);

	            objNucleo.setEstado(objEstado);
	            Banco objBanco = new Banco(); 
	            
	            objBanco.setCodigo(rs.getInt(10));
	            objBanco=BancoDAO.consultarBanco(objBanco);
	            
				objNucleo.setBanco_recebimento(objBanco);
				objNucleo.setEmail(rs.getString(11));
				objNucleo.setObservacao(rs.getString(12));
				
				return objNucleo;
			}
		} 
		
		catch (SQLException sqle) {
			throw new StuDAOException(MensErr+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return null;
	}

	public Nucleo consultarNomeNucleo(Nucleo pNucleo) throws StuDAOException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		
		try {
			
            Cidade objCidade;
            Estado objEstado;
            Bairro objBairro;
            Banco objBanco;
            
			String SQL = "select * from tb_nucleo";
			if(pNucleo.getNome()!=null){
				SQL = "select * from tb_nucleo where upper(nm_nome_nucleo) like upper('"+pNucleo.getNome().replace("Núcleo ", "")+"%')";
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if(rs.next()){
				Nucleo objNucleo = new Nucleo();
				
				objNucleo.setCodigo(rs.getInt(1));
				
				RegiaoDAO objRegiaoDAO = new RegiaoDAO();
				objNucleo.setRegiao(objRegiaoDAO.consultarRegiao(rs.getInt(2)));

				objNucleo.setNome(rs.getString(3));
				objNucleo.setCnpj(rs.getString(4));
				objNucleo.setLogradouro(rs.getString(5));
				
	            objBairro = new Bairro();
	            objBairro.setCodigo(rs.getInt(6));
                objBairro = BairroDAO.consultarBairro(objBairro);

				objNucleo.setBairro(objBairro);
				
				objNucleo.setCep(rs.getString(7));
				
	            objCidade = new Cidade();
	            objCidade.setCodigo(rs.getInt(8));
                objCidade = CidadeDAO.consultarCidade(objCidade);
				
				objNucleo.setCidade(objCidade);
				
	            objEstado = new Estado();
	            objEstado.setCodigo(rs.getInt(9));
                objEstado = EstadoDAO.consultarEstado(objEstado);

	            objNucleo.setEstado(objEstado);

	            objBanco = new Banco();
	            objBanco.setCodigo(rs.getInt(10));
	            objBanco=BancoDAO.consultarBanco(objBanco);
	            
				objNucleo.setBanco_recebimento(objBanco);
				
				objNucleo.setEmail(rs.getString(11));
				
				objNucleo.setObservacao(rs.getString(12));
				
				objNucleo.setOffline(rs.getString(13));
				
				return objNucleo;
			}
		} 
		catch (SQLException sqle) {
			throw new StuDAOException(MensErr+sqle);
		}finally{
			StuConecta.closeConnection(conn, ps, rs);
		}
		return null;
	}

	public void alterarNucleo(Nucleo pNucleo) throws StuDAOException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = "update tb_nucleo set nm_nome_nucleo=?, cnpj=?, nm_logradouro=?, cd_bairro=?, nm_cep=?, cd_cidade=?, cd_estado=?, cd_banco_recebimento_online=?, nm_email=?, nm_observacao=?  where cd_nucleo="+pNucleo.getCodigo();
			conn = StuConecta.getConnection();
			
			//System.out.println(SQL);
			
			ps = conn.prepareStatement(SQL);

			ps.setString(1, pNucleo.getNome());
			ps.setString(2, pNucleo.getCnpj());
			ps.setString(3, pNucleo.getLogradouro());
			ps.setInt(4, pNucleo.getBairro().getCodigo());
			ps.setString(5, pNucleo.getCep());
			ps.setInt(6, pNucleo.getCidade().getCodigo());
			ps.setInt(7, pNucleo.getEstado().getCodigo());
			ps.setInt(8, pNucleo.getBanco_recebimento().getCodigo());
			ps.setString(9, pNucleo.getEmail());
			ps.setString(10, pNucleo.getObservacao());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	
	public static ArrayList<Nucleo> listarNucleo(int pRegiao) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Nucleo> listTodas = null;
            
            Nucleo itemNucleo;
            Regiao objRegiao;
            Cidade objCidade;

            String SQL = "select *from tb_nucleo";
            
            if(pRegiao!=0){
            	SQL = "select *from tb_nucleo where cd_regiao="+pRegiao;
            }
            SQL = SQL+" order by cd_regiao,cd_nucleo";

            //System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
                listTodas = new ArrayList<Nucleo>();
                do {
                    itemNucleo = new Nucleo();
                    objRegiao = new Regiao();
                    objCidade = new Cidade();
                    
                    //objRegiao.setCodigo(rs.getInt("cd_regiao"));
                    
                    RegiaoDAO objRegiaoDAO = new RegiaoDAO();
                    
                    objRegiao = objRegiaoDAO.consultarRegiao(rs.getInt("cd_regiao"));
                    itemNucleo.setRegiao(objRegiao);
                    
                    itemNucleo.setNome(rs.getString("nm_nome_nucleo"));
                    itemNucleo.setCnpj(rs.getString("cnpj"));

                    objCidade.setCodigo(rs.getInt("cd_cidade"));
                    objCidade = CidadeDAO.consultarCidade(objCidade);

                    itemNucleo.setCidade(objCidade);
    				
    				itemNucleo.setTotalsocios(NucleoDAO.buscaTotalSociosNucleo('A',rs.getInt("cd_nucleo"))) ;

                    listTodas.add(itemNucleo);
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
	
	//----------------------------------------------------------------------------------------
	public static ArrayList<Nucleo> listarNucleoXML(int pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Nucleo> listTodas = null;
            
            Nucleo itemNucleo;
            Regiao objRegiao;
            Cidade objCidade;

            String SQL = "select *from tb_nucleo where cd_nucleo="+pNucleo;

            //System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();

			if (rs.next()) {
                listTodas = new ArrayList<Nucleo>();
                do {
                    itemNucleo = new Nucleo();
                    objRegiao = new Regiao();
                    objCidade = new Cidade();
                    
                    itemNucleo.setCodigo(rs.getInt("cd_nucleo"));
                    
                    RegiaoDAO objRegiaoDAO = new RegiaoDAO();
                    
                    objRegiao = objRegiaoDAO.consultarRegiao(rs.getInt("cd_regiao"));
                    itemNucleo.setRegiao(objRegiao);
                    
                    itemNucleo.setNome(rs.getString("nm_nome_nucleo"));
                    itemNucleo.setCnpj(rs.getString("cnpj"));

                    objCidade.setCodigo(rs.getInt("cd_cidade"));
                    objCidade = CidadeDAO.consultarCidade(objCidade);

                    itemNucleo.setCidade(objCidade);
    				
    				itemNucleo.setTotalsocios(NucleoDAO.buscaTotalSociosNucleo('A',rs.getInt("cd_nucleo"))) ;

                    listTodas.add(itemNucleo);
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
	//----------------------------------------------------------------------------------------

	public void incluirNucleoOperador(Nucleo pNucleo, Login pOperador) throws StuDAOException{
		Connection conn = null;
		CallableStatement cs = null;
		
		try {
			conn = StuConecta.getConnection();

			cs = (CallableStatement) conn.prepareCall("{call incluiNucleo(?,?,?,?,?)}");
			
			cs.setInt(1, pNucleo.getRegiao().getCodigo());
			cs.setString(2, pNucleo.getNome());
			cs.setString(3, pNucleo.getCnpj());
			cs.setLong(4, pOperador.getCpf());
			cs.setString(5, pOperador.getNome());
			
			cs.execute();

		}
		catch (SQLException sqle){
			throw new StuDAOException(sqle);
		}
		finally{
			StuConecta.closeConnection(conn, cs);
		}
	}

}
