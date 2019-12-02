package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.Socio;


import DAO.SocioDAO;

public class ConsultaListaSocio implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";

		String pTipo = "ativo";
		String pgJsp = "/consultaSocioAtivo.jsp";
		
		HttpSession sessao = request.getSession();
		Socio itemSoc = new Socio();
		
		if(request.getParameter("tipo")!=null&&request.getParameter("tipo")!="")
			pTipo = request.getParameter("tipo");
		
		if(!pTipo.equalsIgnoreCase("ativo")){
			pgJsp = "/consultaSocio.jsp";
		}
		
		//System.out.println("pTipo "+pTipo);
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		ArrayList<Socio> listSocio = SocioDAO.listarSocio(objOperador.getNucleo().getCodigo(),itemSoc, pTipo,0);

		if(listSocio == null){
			mens = "Cadastro não encontrado: "+itemSoc.getNome();
		}
		
		sessao.setAttribute("listSocio", listSocio);

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
