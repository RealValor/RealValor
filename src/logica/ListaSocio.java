package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.Socio;


import DAO.SocioDAO;

public class ListaSocio implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/listaSocio.jsp";
		String nomeSoc = request.getParameter("nome_socio");

		if(request.getParameter("flg")!=null&&request.getParameter("flg")!="")
			if(Integer.parseInt(request.getParameter("flg"))==1)pgJsp = "/listaSocioRec.jsp";;

		HttpSession sessao = request.getSession();	
			
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");
			
		Socio itemSoc = new Socio();
		if(nomeSoc!=null&&nomeSoc!=""){
			itemSoc.setNome(request.getParameter("nome_socio"));
		}
		String pTipo = "todos"; //"ativo";
		ArrayList<Socio> listSoc = SocioDAO.listarSocio(objOperador.getNucleo().getCodigo(), itemSoc, pTipo,0);
		if(listSoc != null){
			request.setAttribute("listSoc", listSoc);		                
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
