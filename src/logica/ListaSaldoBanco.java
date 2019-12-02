package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Banco;
import beans.Login;
import beans.SaldoBanco;


import DAO.BancoDAO;
import DAO.SaldoBancoDAO;

public class ListaSaldoBanco implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/listaSaldoBanco.jsp";
		
		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		SaldoBanco itemSaldo = new SaldoBanco();
		itemSaldo.setNucleo(objOperador.getNucleo());
		
		int codBan;
		int numAno;
		int numMes;

		Banco itemBanco = new Banco();

		codBan=0;
		if(request.getParameter("banco")!=null&&request.getParameter("banco")!=""){
			codBan = Integer.parseInt(request.getParameter("banco"));
		}
		
		itemBanco.setCodigo(codBan);
		itemBanco = BancoDAO.consultarBanco(itemBanco);

		if(request.getParameter("ano")!=null&&request.getParameter("ano")!=""){
			numAno = Integer.parseInt(request.getParameter("ano"));
			itemSaldo.setAno(numAno);
		}
		
		if(request.getParameter("mes")!=null&&request.getParameter("mes")!=""){
			numMes = Integer.parseInt(request.getParameter("mes"));
			itemSaldo.setMes(numMes);
		}
		
		itemSaldo.setBanco(itemBanco);
		
		ArrayList<SaldoBanco> listSal = SaldoBancoDAO.listarSaldoBanco(itemSaldo);
		if(listSal != null){
			request.setAttribute("listSal", listSal);		                
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
