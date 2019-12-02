package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Bairro;


import DAO.BairroDAO;

public class ListaBairro implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiBairro.jsp";

		ArrayList<Bairro> listBairro = BairroDAO.listarBairro();
		if(listBairro != null){
			request.setAttribute("listBairro", listBairro);		                
		}
		pgJsp = "/listaBairro.jsp";

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
