package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.DividaDAO;
import beans.Divida;
import beans.MovAtual;
import beans.Nucleo;

public class ConsultaDividaPeriodo implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "Este relatório contempla histórico de dívidas pagas e pendentes";
		String pgJsp = "/consultaDividaPeriodo.jsp";
		int controle = 0;
		
		HttpSession sessao = request.getSession();
		
		int ano = 0;
		int mesInicial = 0;
		int mesFinal = 0;
		
		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");

		MovAtual itemMovIni = new MovAtual();
		MovAtual itemMovFim = new MovAtual();

		ArrayList<MovAtual> listObjMov = new ArrayList<MovAtual>();		
		for (int i = 1; i <= 12; i++) {
			MovAtual itemMovAux = new MovAtual();
			itemMovAux.setMes(i);
			listObjMov.add(itemMovAux);
		}
		sessao.setAttribute("listObjMov", listObjMov);
		
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			ano=Integer.parseInt(request.getParameter("ano"));

		if(request.getParameter("ano")!=null&&request.getParameter("ano")!=""){
			ano = Integer.parseInt(request.getParameter("ano"));
		}

		if(request.getParameter("mesini")!=null&&request.getParameter("mesini")!=""){
			mesInicial = Integer.parseInt(request.getParameter("mesini"));
		}

		if(request.getParameter("mesfim")!=null&&request.getParameter("mesfim")!=""){
			mesFinal = Integer.parseInt(request.getParameter("mesfim"));
		}
		
		if(request.getParameter("controle")!=null&&request.getParameter("controle")!="")
			controle=Integer.parseInt(request.getParameter("controle"));
		
		itemMovIni.setMes(mesInicial);
		sessao.setAttribute("objMovIni", itemMovIni);
		
		Calendar data = Calendar.getInstance();
		itemMovIni.setAno(data.get(Calendar.YEAR));

		if(controle>0){

			itemMovFim.setMes(mesFinal);
			sessao.setAttribute("objMovFin", itemMovFim);
			
			DividaDAO objDividaDAO = new DividaDAO();
			ArrayList<Divida> listDivida = objDividaDAO.listarDividaPeriodo(ano,mesInicial,mesFinal,objNucleo);

			if(listDivida==null){
				controle = 0;
				mens = "Não existem entradas para o período informado";  
			}

			sessao.setAttribute("listDivida", listDivida);
		}

		controle++;
		sessao.setAttribute("controle", controle);
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
