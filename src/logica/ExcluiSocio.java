package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Cargo;
import beans.Login;
import beans.Socio;


import DAO.CargoDAO;
import DAO.SocioDAO;

public class ExcluiSocio implements LogicaDeNegocio{ 
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiSocio.jsp";
		String nomeSoc = request.getParameter("consulta");
		
		HttpSession sessao = request.getSession();
		Socio itemSoc = new Socio();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");
		
		ArrayList<Cargo> listCar = CargoDAO.listarCargo();
		if(listCar != null){
			request.setAttribute("listCar", listCar);		                
		}

		if(nomeSoc!=null&&nomeSoc!=""){	
			itemSoc.setNome(request.getParameter("consulta"));

			String codSoc  = request.getParameter("cod_socio");
			
			if(codSoc!=null&&codSoc!=""){
				itemSoc.setCodigo(Integer.parseInt(request.getParameter("cod_socio")));
			}
			
			SocioDAO daoRecF = new SocioDAO();		
			Socio itemRecF = daoRecF.consultarSocio(itemSoc, objOperador.getNucleo());
			
			mens = "Cadastro não encontrado: "+itemSoc.getNome();
			
			if(itemRecF!=null){
				SocioDAO daoExcF = new SocioDAO(); 
				daoExcF.excluirSoc(itemSoc);
				mens = "Cadastro excluído: "+itemRecF.getNome();
				itemSoc=null;
			}
			request.setAttribute("objSoc", itemSoc);
			sessao.setAttribute("objSoc", itemSoc);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
