package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.TipoEntrada;


import DAO.TipoEntradaDAO;

public class ListaTipoEntrada implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiTipoEntrada.jsp";

		HttpSession sessao = request.getSession();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");
		
		ArrayList<TipoEntrada> listTipEnt = TipoEntradaDAO.listarTipoEntrada(objOperador.getNucleo().getCodigo(),0); //0 = todos; 1 = mensal="N"; 2 = mensal="S"; 3 = ativo<>"N";
		if(listTipEnt != null){
			request.setAttribute("listTipEnt", listTipEnt);		                
		}
		pgJsp = "/listaTipoEntrada.jsp";

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
