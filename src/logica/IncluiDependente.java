package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Dependente;
import beans.GrauParentesco;
import beans.Login;
import beans.Socio;


import DAO.DependenteDAO;
import DAO.SocioDAO;

public class IncluiDependente implements LogicaDeNegocio{ //Socio(Principal)
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		RequestDispatcher rd = null;
		
		String mens="";
		String pgJsp = "/incluiSocio.jsp";
		String nomeSoc = request.getParameter("consultadependente");
		String codSocio = request.getParameter("cod_socio");

		HttpSession sessao = request.getSession();
		Dependente itemDependente = new Dependente();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		if(nomeSoc!=null&&nomeSoc!=""){
			
			String codDependente = request.getParameter("cod_dependente");
			String grauParentesco = request.getParameter("grauparentesco");

			if(grauParentesco!=null&&grauParentesco!=""){				
				GrauParentesco objGrauParentesco = new GrauParentesco();
				
				objGrauParentesco.setCodigo(Integer.parseInt(grauParentesco));
				itemDependente.setGrauparentesco(objGrauParentesco);
			}

			if(codSocio!=null&&codSocio!=""){
				Socio objSocio = new Socio();
				
				objSocio.setCodigo(Integer.parseInt(codSocio));
				itemDependente.setSocio(objSocio);
			}

			if(codDependente!=null&&codDependente!=""){
				Socio objDepenente = new Socio();
				objDepenente.setCodigo(Integer.parseInt(codDependente));

				SocioDAO socioDAO = new SocioDAO();
				objDepenente = socioDAO.consultarSocio(objDepenente, objOperador.getNucleo());

				itemDependente.setDependente(objDepenente);
			}
			
			DependenteDAO daoRecDependente = new DependenteDAO();

			if(daoRecDependente.consultarDependente(itemDependente)==null){

				DependenteDAO objDependenteDAO = new DependenteDAO();

				if(itemDependente.getDependente().getSituacao().equalsIgnoreCase("A")){
					objDependenteDAO.incluirDependente(itemDependente);
				}else{
					mens = "O dependente "+itemDependente.getDependente().getNome().trim()+" não está ativo.";
				}
			}else{
				mens = "Dependente: "+itemDependente.getDependente().getNome().trim()+" já cadastrado";  
			}
			
		}
		
		SocioDAO daoRecSocio = new SocioDAO();
		Socio itemRecSocio = new Socio();
		itemRecSocio.setCodigo(Integer.parseInt(codSocio));		
		itemRecSocio = daoRecSocio.consultarSocio(itemRecSocio, objOperador.getNucleo());

		ArrayList<Dependente> listDependente = DependenteDAO.listarDependente(itemRecSocio, objOperador.getNucleo());
		request.setAttribute("listDependente", listDependente);		                
		sessao.setAttribute("listDependente", listDependente);

		request.setAttribute("objSoc", itemRecSocio);
		sessao.setAttribute("objSoc", itemRecSocio);		

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
