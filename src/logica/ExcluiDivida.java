package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Divida;
import beans.Saida;


import DAO.DividaDAO;

public class ExcluiDivida implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/quitaDivida.jsp";
		
		int numSaida = 0;
		if(request.getParameter("saida")!=null&&request.getParameter("saida")!="")
			numSaida = Integer.parseInt(request.getParameter("saida"));		
		
		int ano = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			ano = Integer.parseInt(request.getParameter("ano"));
		
		int mes = 0;
		if(request.getParameter("mes")!=null&&request.getParameter("mes")!="")
			mes = Integer.parseInt(request.getParameter("mes"));		

		HttpSession sessao = request.getSession();

		Divida objExcDiv = new Divida();
		objExcDiv.setAno(ano);
		objExcDiv.setMes(mes);

		if(numSaida!=0){
			//SaidaDAO numSaiDAO = new SaidaDAO();
			Saida itemSai = new Saida();
			
			itemSai.setNumero(numSaida);
			itemSai.setAno(ano);
			
			//itemSai = numSaiDAO.consultarSaida(itemSai);
			objExcDiv.setSaida(itemSai);
			
			DividaDAO objDividaDAO = new DividaDAO();
			objDividaDAO.excluirDivida(objExcDiv);
			
			objExcDiv = (Divida) sessao.getAttribute("objDiv");
			
			ArrayList<Divida> listDiv = null;
			if(objExcDiv.getAno()!=0||objExcDiv.getMes()!=0||objExcDiv.getValor()!=0){
				listDiv = DividaDAO.listarDivida(objExcDiv);
			}

			sessao.removeAttribute("listDiv");
			sessao.setAttribute("listDiv", listDiv);
		}
	
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
