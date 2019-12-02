package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import logica.StuConecta;
import beans.Dependente;
import beans.GrauParentesco;
import beans.Nucleo;
import beans.Socio;

public class DependenteDAO {

	static String MensErr = "Erro de acesso aos dados ";
	
	public Dependente consultarDependente(Dependente pDep) throws StuDAOException{

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String SQL = "select * from tb_socio_dependente";
			
			if(pDep.getSocio().getCodigo()!=0){
				SQL = "select * from tb_socio_dependente where cd_socio="+pDep.getSocio().getCodigo(); 
			}
			if(pDep.getSocio().getCodigo()!=0&&pDep.getDependente().getCodigo()!=0){
				SQL = "select * from tb_socio_dependente where cd_socio="+pDep.getSocio().getCodigo()+
				" and cd_socio_dependente="+pDep.getDependente().getCodigo(); 
			}
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			
			if(rs.next()){
				
				Socio objSocio = new Socio();
				Socio objSocioDependente = new Socio();
				GrauParentesco ojbGrauParentesco = new GrauParentesco();
				
				Dependente objDependente = new Dependente();

				objSocio.setCodigo(rs.getInt(1));
				objSocioDependente.setCodigo(rs.getInt(2));
				ojbGrauParentesco.setCodigo(rs.getInt(3));
				
				objDependente.setSocio(objSocio);
				objDependente.setDependente(objSocioDependente);
				objDependente.setGrauparentesco(ojbGrauParentesco);

				return objDependente;
			}
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, st, rs);
		}
		return null;
	}

	public void incluirDependente(Dependente pDep) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		String SQL = "insert into tb_socio_dependente values(?, ?, ?)";
		
		try {
			
			conn = StuConecta.getConnection();
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, pDep.getSocio().getCodigo());
			ps.setInt(2, pDep.getDependente().getCodigo());
			ps.setInt(3, pDep.getGrauparentesco().getCodigo());

			ps.executeUpdate();
		}
		catch (SQLException sqle){
			throw new StuDAOException(MensErr+sqle);
		}
		finally{
			StuConecta.closeConnection(conn, ps);
		}
	}

	public void alterarDependente(Socio pSoc) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("update tb_socio_dependente set nm_nome=?, nm_telefone=?,cpf=?,sexo=?,dt_nasc=?,dt_asso=?,nm_situacao=?,cd_cargo=?,cd_grau=?"
					+" where cd_socio="+Integer.toString(pSoc.getCodigo()));

			conn = StuConecta.getConnection();
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, pSoc.getNome());
			ps.setString(2, pSoc.getTelefone());

			ps.setLong(3, pSoc.getCpf());
			
			ps.setString(4, pSoc.getSexo());
			if(pSoc.getSexo().length()>1){
				ps.setString(4, pSoc.getSexo().substring(0,1));
			}
			
			ps.setDate(5, null);
			if(pSoc.getDataNasc()!=null){
				ps.setDate(5, new java.sql.Date(pSoc.getDataNasc().getTime()));
			}
			ps.setDate(6, null);
			if(pSoc.getDataAsso()!=null){
				ps.setDate(6, new java.sql.Date(pSoc.getDataAsso().getTime()));
			}
			ps.setString(7, pSoc.getSituacao());
			ps.setInt(8, pSoc.getCargo().getCodigo());

			ps.setString(9, pSoc.getGrau());
			if(pSoc.getGrau().length()>1){
				if(pSoc.getGrau().equalsIgnoreCase("Discípulo QS")){
					ps.setString(9, "S");
				}else{
					ps.setString(9, pSoc.getGrau().substring(0,1));
				}
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

	public void excluirDependente(Dependente pDep) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			String SQL = ("delete from tb_socio_dependente where cd_socio="+Integer.toString(pDep.getSocio().getCodigo()))
			+" and cd_socio_dependente="+Integer.toString(pDep.getDependente().getCodigo());
			
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

	public static ArrayList<Dependente> listarDependente(Socio pDep, Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Dependente> listTodas = null;
            Dependente itemDependente;

			String SQL = "select * from tb_socio_dependente where ";
			
			if(pDep.getCodigo()!=0){
				SQL = SQL + " cd_socio="+pDep.getCodigo(); 
			}
			//System.out.println(SQL);	
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Dependente>();
                
                do {
                    itemDependente = new Dependente();

                    Socio objSocio = new Socio();
                    Socio objDependente = new Socio();
                    GrauParentesco objGrauParentesco = new GrauParentesco();

                    objSocio.setCodigo(rs.getInt(1));
                    objDependente.setCodigo(rs.getInt(2));
                    objGrauParentesco.setCodigo(rs.getInt(3));
                    
                    SocioDAO objSocioDAO = new SocioDAO();
                    objSocio = objSocioDAO.consultarSocio(objSocio, pNucleo);
                    
                    objDependente = objSocioDAO.consultarSocio(objDependente, pNucleo);

                    objGrauParentesco = GrauParentescoDAO.consultarGrauParentesco(objGrauParentesco);
                    
                    itemDependente.setSocio(objSocio);
                    itemDependente.setDependente(objDependente);
                    itemDependente.setGrauparentesco(objGrauParentesco);
                    
                    listTodas.add(itemDependente);
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
	
	//-----------------------
	public static ArrayList<Dependente> listarDepenente(Nucleo pNucleo) throws StuDAOException{
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
            ArrayList<Dependente> listTodas = null;
            Dependente objDependente = new Dependente();
            
            String SQL = "select *from tb_socio_dependente where cd_socio in (select cd_socio from tb_socio_nucleo where cd_nucleo="
            		+pNucleo.getCodigo()+") order by cd_socio";
			
			//System.out.println(SQL);
			
			conn = StuConecta.getConnection( );
			
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
                listTodas = new ArrayList<Dependente>();
                do {
                	objDependente = new Dependente();

                	Socio objSocio = new Socio();
                	Socio objSocioDependente = new Socio();
                	GrauParentesco objGrauParentesco = new GrauParentesco();
                	
                	objSocio.setCodigo(rs.getInt("cd_socio"));
                	objDependente.setSocio(objSocio);
                    
                	objSocioDependente.setCodigo(rs.getInt("cd_socio_dependente"));
                	objDependente.setDependente(objSocioDependente);

                	objGrauParentesco.setCodigo(rs.getInt("cd_grau_parentesco"));
                	objDependente.setGrauparentesco(objGrauParentesco);
                	
                    listTodas.add(objDependente);
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

