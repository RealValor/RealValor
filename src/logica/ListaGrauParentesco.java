package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.GrauParentesco;


import DAO.GrauParentescoDAO;

public class ListaGrauParentesco implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiGrauParentesco.jsp";

		ArrayList<GrauParentesco> listGrauParentesco = GrauParentescoDAO.listarGrauParentesco();
		if(listGrauParentesco != null){
			request.setAttribute("listGrauParentesco", listGrauParentesco);		                
		}
		pgJsp = "/listaGrauParentesco.jsp";

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
