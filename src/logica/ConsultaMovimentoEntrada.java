package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.EntradaDAO;
import DAO.HistoricoRecebimentoDAO;
import DAO.MovAtualDAO;
import beans.HistoricoRecebimento;
import beans.MovAtual;
import beans.Nucleo;

public class ConsultaMovimentoEntrada implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/consultaMovimentoEntrada.jsp";
		int totEntradas = 0;
		int totalFPSG = 0;
		
		HttpSession sessao = request.getSession();
		
		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");
		
		MovAtual itemMov = new MovAtual();
		itemMov.setNucleo(objNucleo);
		itemMov.setFechado("N");
		
		MovAtual itemRecMov = new MovAtual();
		itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);
		
		Calendar data = Calendar.getInstance();
		itemMov.setAno(data.get(Calendar.YEAR));

		itemMov.setMes(EntradaDAO.buscaUltimoMesMovimento(data.get(Calendar.YEAR), objNucleo));
		
		if(itemRecMov!=null&&itemRecMov.getAno()!=itemMov.getAno()){
			itemRecMov.setAno(data.get(Calendar.YEAR));
			itemMov.setMes(12);
		}

		sessao.removeAttribute("listObjMov");
		ArrayList<MovAtual> listObjMov = new ArrayList<MovAtual>();
		
		for (int i = 12; i >= 1; i--) {
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

			HistoricoRecebimento itemHistRec = new HistoricoRecebimento();
			itemHistRec.setAno(codAno);
			itemHistRec.setMes(codMes);

			itemRecMov.setAno(codAno);
			itemRecMov.setMes(codMes);

			int codOrdem = 1; //1 = ordenado por numero recibo
			ArrayList<HistoricoRecebimento> listEntrada = HistoricoRecebimentoDAO.listarHistoricoEntrada(codMes, codAno, codOrdem, objNucleo);
			ArrayList<HistoricoRecebimento> listEntradaAux = new ArrayList<HistoricoRecebimento>();
			listEntradaAux = listEntrada;

			if(listEntrada==null){
				mens = "Não existem entradas para o mês "+itemHistRec.getMes()+" de "+itemHistRec.getAno();  
			}else{
				listEntrada = HistoricoRecebimentoDAO.listarHistoricoEntradaReciboReserva(listEntradaAux, codMes, codAno, codOrdem, objNucleo);
				totalFPSG = HistoricoRecebimentoDAO.totalRecibosSomenteFPSG(codMes, codAno, objNucleo);
				totEntradas = listEntrada.size();
			}
			sessao.setAttribute("listEntrada", listEntrada);
		}
		
		sessao.setAttribute("objMovIni", itemRecMov);
		sessao.setAttribute("totEntradas", totEntradas);
		sessao.setAttribute("totalFPSG", totalFPSG);

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
