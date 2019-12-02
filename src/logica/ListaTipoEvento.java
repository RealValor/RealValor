package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.TipoEvento;


import DAO.TipoEventoDAO;

public class ListaTipoEvento implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiTipoEvento.jsp";

		ArrayList<TipoEvento> listTipEve = TipoEventoDAO.listarTipoEvento();
		if(listTipEve != null){
			request.setAttribute("listTipEve", listTipEve);		                
		}
		pgJsp = "/listaTipoEvento.jsp";

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
