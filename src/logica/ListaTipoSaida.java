package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.TipoSaida;


import DAO.TipoSaidaDAO;

public class ListaTipoSaida implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiTipoSaida.jsp";
		
		HttpSession sessao = request.getSession();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		ArrayList<TipoSaida> listTipSai = TipoSaidaDAO.listarTipoSaida(0,objOperador.getNucleo());
		if(listTipSai != null){
			request.setAttribute("listTipSai", listTipSai);		                
		}
		pgJsp = "/listaTipoSaida.jsp";

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
