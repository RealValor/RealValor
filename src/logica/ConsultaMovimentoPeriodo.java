package logica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.HistoricoRecebimento;
import beans.Nucleo;
import beans.Saida;


import DAO.HistoricoRecebimentoDAO;
import DAO.SaidaDAO;

public class ConsultaMovimentoPeriodo implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "As saídas neste relatório consideram o mês do movimento";
		String pgJsp = "/consultaMovimentoPeriodo.jsp";
		int controle = 0;
		
		HttpSession sessao = request.getSession();
		
		sessao.removeAttribute("datainicial");
		sessao.removeAttribute("datafinal");
		sessao.removeAttribute("inicioperiodo");
		sessao.removeAttribute("finalperiodo");
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		
		Calendar dataInicial = Calendar.getInstance();
		Calendar dataFinal = Calendar.getInstance();
		
		if(request.getParameter("data_inicial")!=null&&request.getParameter("data_inicial")!="")
			dataInicial.setTime(formato.parse(request.getParameter("data_inicial")));
		
		if(request.getParameter("data_final")!=null&&request.getParameter("data_final")!="")
			dataFinal.setTime(formato.parse(request.getParameter("data_final")));
		
		if(request.getParameter("controle")!=null&&request.getParameter("controle")!="")
			controle=Integer.parseInt(request.getParameter("controle"));
		
		if(dataInicial.equals(dataFinal)){
			dataInicial.add(Calendar.DATE,-1); //Subtrai um dia da data inicial  
		}
		
		sessao.setAttribute("datainicial", dataInicial.getTime());
		sessao.setAttribute("datafinal", dataFinal.getTime());
		
		String datainicial = new SimpleDateFormat("dd/MM/yyyy").format(dataInicial.getTime());
		String datafinal = new SimpleDateFormat("dd/MM/yyyy").format(dataFinal.getTime());
		
		sessao.setAttribute("inicioperiodo", datainicial);
		sessao.setAttribute("finalperiodo", datafinal);
		
		if(controle>0){

			//busca receitas do período totalizando por tipo de entrada
			HistoricoRecebimento itemEntradaInicial = new HistoricoRecebimento();
			HistoricoRecebimento itemEntradaFinal = new HistoricoRecebimento();

			Nucleo objNucleo = new Nucleo();
			objNucleo = (Nucleo)sessao.getAttribute("objNucleo");
			
			itemEntradaInicial.setData(dataInicial);
			itemEntradaInicial.setNucleo(objNucleo);
			
			itemEntradaFinal.setData(dataFinal);
			
			
			HistoricoRecebimentoDAO objEntradaDAO = new HistoricoRecebimentoDAO();
			ArrayList<HistoricoRecebimento> listEntrada = objEntradaDAO.listarHistoricoPeriodo(itemEntradaInicial, itemEntradaFinal);	

			if(listEntrada==null){
				controle = 0;
				mens = "Não existem entradas para o período informado";  
			}

			//busca despesas do mes e ano totalizando por tipo de saida
			Saida itemSaidaInicial = new Saida();
			Saida itemSaidaFinal = new Saida();

			itemSaidaInicial.setData(dataInicial);
			itemSaidaInicial.setAno(dataInicial.get(Calendar.YEAR));
			itemSaidaInicial.setMes(dataInicial.get(Calendar.MONTH)+1);
			itemSaidaInicial.setNucleo(objNucleo);
			
			itemSaidaFinal.setData(dataFinal);
			itemSaidaFinal.setAno(dataFinal.get(Calendar.YEAR));
			itemSaidaFinal.setMes(dataFinal.get(Calendar.MONTH)+1);

			SaidaDAO objSaidaDAO = new SaidaDAO();

			ArrayList<Saida> listSaida = objSaidaDAO.listarSaidaPeriodo(itemSaidaInicial, itemSaidaFinal);

			sessao.setAttribute("listEntrada", listEntrada);
			sessao.setAttribute("listSaida", listSaida);
		}

		controle++;
		sessao.setAttribute("controle", controle);
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
