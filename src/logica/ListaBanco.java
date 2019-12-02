package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Banco;


import DAO.BancoDAO;

public class ListaBanco implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiBanco.jsp";

		ArrayList<Banco> listBan = BancoDAO.listarBanco();
		if(listBan != null){
			request.setAttribute("listBan", listBan);		                
		}
		pgJsp = "/listaBanco.jsp";

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
