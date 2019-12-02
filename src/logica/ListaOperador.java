package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;


import DAO.LoginDAO;

public class ListaOperador implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiOperador.jsp";

		HttpSession sessao = request.getSession();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");
		
		ArrayList<Login> listOper = LoginDAO.listarLogin(objOperador);
		if(listOper != null){
			request.setAttribute("listOper", listOper);		                
		}
		pgJsp = "/listaOperador.jsp";

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
