package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.MovAtualDAO;
import DAO.SaidaDAO;
import beans.MovAtual;
import beans.Nucleo;
import beans.Saida;

public class ConsultaMovimentoSaida implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/consultaMovimentoSaida.jsp";
		int totSaidas = 0;
		
		HttpSession sessao = request.getSession();
		
		//mesfin_paga
		
		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");

		MovAtual itemMov = new MovAtual();
		itemMov.setNucleo(objNucleo);
		itemMov.setFechado("N");
		
		MovAtual itemRecMov = new MovAtual();
		itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);

		Calendar data = Calendar.getInstance();
		itemMov.setAno(data.get(Calendar.YEAR));
		
		itemMov.setMes(SaidaDAO.buscaUltimoMesMovimento(data.get(Calendar.YEAR), objNucleo));

		if(itemRecMov!=null&&itemRecMov.getAno()!=itemMov.getAno()){
			itemRecMov.setAno(data.get(Calendar.YEAR));
			itemMov.setMes(12);
		}

		ArrayList<MovAtual> listObjMov = new ArrayList<MovAtual>();		
		for (int i = itemMov.getMes(); i >= 1; i--) {
			MovAtual itemMovAux = new MovAtual();
			itemMovAux.setMes(i);
			listObjMov.add(itemMovAux);
		}
		sessao.setAttribute("listObjMov", listObjMov);

		int codAno = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			codAno = Integer.parseInt(request.getParameter("ano"));

		int codMes = 0;
		if(request.getParameter("mes")!=null&&request.getParameter("mes")!="")
			codMes = Integer.parseInt(request.getParameter("mes"));		
		
		if(codAno!=0&&codMes!=0){

			Saida itemSaida = new Saida();
			itemSaida.setAno(codAno);
			itemSaida.setMes(codMes);
			itemSaida.setNucleo(objNucleo);

			itemRecMov.setAno(codAno);
			itemRecMov.setMes(codMes);
			
			ArrayList<Saida> listSaida = SaidaDAO.listarSaida(itemSaida);
			
			if(listSaida==null){
				mens = "Não existem saídas para o mês "+itemSaida.getMes()+" de "+itemSaida.getAno();  
			}else{
				totSaidas = listSaida.size();
			}
			
			sessao.setAttribute("listSaida", listSaida);
		}

		//sessao.setAttribute("objMovIni", itemMov);
		sessao.setAttribute("objMovIni", itemRecMov);
		sessao.setAttribute("totSaidas", totSaidas);
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
