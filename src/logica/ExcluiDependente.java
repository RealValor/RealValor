package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Dependente;
import beans.Login;
import beans.Socio;


import DAO.DependenteDAO;
import DAO.SocioDAO;

public class ExcluiDependente implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiSocio.jsp";
		
		
		HttpSession sessao = request.getSession();
		Dependente itemDependente = new Dependente();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		String codSocio = request.getParameter("cod_socio");

		if(codSocio!=null&&codSocio!=""){

			String codDependente = request.getParameter("cod_dependente");

			Socio objSocio = new Socio();
			Socio objDepenente = new Socio();
			
			if(codSocio!=null&&codSocio!=""){
				objSocio.setCodigo(Integer.parseInt(codSocio));
				itemDependente.setSocio(objSocio);
			}

			if(codDependente!=null&&codDependente!=""){
				objDepenente.setCodigo(Integer.parseInt(codDependente));
				itemDependente.setDependente(objDepenente);
			}
			
			DependenteDAO objDepDAO = new DependenteDAO();
			
			Dependente itemRecDep = objDepDAO.consultarDependente(itemDependente);
			mens = "Cadastro não encontrado: "+itemDependente.getDependente().getNome();  

			if(itemRecDep!=null){

				objDepDAO.excluirDependente(itemRecDep);
				mens = "Dependente excluído";
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
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
