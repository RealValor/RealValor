package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.NaoSocio;


import DAO.NaoSocioDAO;

public class ListaNaoSocio implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = ""; ///incluiNaoSocio.jsp
		String nomeNSoc = request.getParameter("nome_nsocio");

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		NaoSocio itemNSoc = new NaoSocio();
		itemNSoc.setRegiao(objOperador.getRegiao());
		
		if(nomeNSoc!=null&&nomeNSoc!=""){
			itemNSoc.setNome(nomeNSoc);
		}
		
		ArrayList<NaoSocio> listNSoc = NaoSocioDAO.listarNSoc(itemNSoc);
		if(listNSoc != null){
			request.setAttribute("listNSoc", listNSoc);
			/*
			for (NaoSocio naoSocio : listNSoc) {
				System.out.println(naoSocio.getCodigo()+" "+naoSocio.getNome()+" "+naoSocio.getTelefone()+" "+naoSocio.getCpfCnpj());
			}
			*/
		}
		
		pgJsp = "/listaNaoSocio.jsp";

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
