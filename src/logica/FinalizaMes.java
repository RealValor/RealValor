package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.FinalizaMesDAO;
import DAO.HistoricoRecebimentoDAO;
import DAO.MovAtualDAO;
import DAO.SaldoCaixaDAO;
import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;
import beans.Nucleo;
import beans.SaldoCaixa;

public class FinalizaMes implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		HttpSession sessao = request.getSession();

		String mens = "O sistema não possibilita lançamentos retroativos! Para checagem pode ser emitido um balancete prévio";
		String pgJsp = "/finalizaMes.jsp";

		int mes = 0;
		int ano = 0;
		
		if(request.getParameter("mes")!=null&&request.getParameter("mes")!=""){
			mes = Integer.parseInt(request.getParameter("mes"));
		}

		if(request.getParameter("ano")!=null&&request.getParameter("ano")!=""){
			ano = Integer.parseInt(request.getParameter("ano"));		
		}

		MovAtual itemMov = new MovAtual();
		itemMov.setFechado("N");
		
		itemMov.setNucleo((Nucleo)sessao.getAttribute("objNucleo"));
		
		MovAtual itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);
		
		sessao.removeAttribute("recibosReserva");
		ArrayList<HistoricoRecebimento> recibosReserva=null;
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		SaldoCaixa objSaldoCaixa = new SaldoCaixa();
		SaldoCaixa itemRecSaldoCaixa = new SaldoCaixa();

		if(itemRecMov != null){
			request.setAttribute("objMovAtu", itemRecMov);
			recibosReserva = HistoricoRecebimentoDAO.consultarRecibosReserva(objOperador.getNucleo().getCodigo(), itemRecMov.getMes(), itemRecMov.getAno());
			
			objSaldoCaixa.setMes(itemRecMov.getMes());
			objSaldoCaixa.setAno(itemRecMov.getAno());

		}else{
			recibosReserva = HistoricoRecebimentoDAO.consultarRecibosReserva(objOperador.getNucleo().getCodigo(), mes, ano);
			objSaldoCaixa.setMes(mes);
			objSaldoCaixa.setAno(ano);
		}
		
		objSaldoCaixa.setNucleo(objOperador.getNucleo());
		
		itemRecSaldoCaixa=SaldoCaixaDAO.consultarSaldoCaixa(objSaldoCaixa);
		
		request.setAttribute("saldoCaixa", itemRecSaldoCaixa);
		
		
		if(mes!=0&&ano!=0){

			int cancelaRecibo = 0;
			if(request.getParameter("cancelarecibos")!=null&&request.getParameter("cancelarecibos")!=""){
				cancelaRecibo = Integer.parseInt(request.getParameter("cancelarecibos"));
			}
			
			if(itemRecSaldoCaixa==null){
				objSaldoCaixa.setFechado("N");

				objSaldoCaixa.setSaldoAnteriorCaixa(Double.parseDouble(request.getParameter("saldo_caixa")));

				objSaldoCaixa.setSaldoAnteriorBanco(Double.parseDouble(request.getParameter("saldo_banco")));
				objSaldoCaixa.setSaldoAnteriorDivida(0.0);
				
				SaldoCaixaDAO objSaldoCaixaDAO = new SaldoCaixaDAO();
				objSaldoCaixaDAO.incluirSaldoCaixa(objSaldoCaixa);
			}
			
			
			if(cancelaRecibo>0){
				FinalizaMesDAO.cancelarRecibosReserva(objOperador.getNucleo().getCodigo(), mes, ano);
			}

			FinalizaMesDAO.finalizarMes(objOperador.getNucleo().getCodigo(), mes, ano);

			pgJsp = "/principal3.jsp";
		}			

		sessao.setAttribute("recibosReserva", recibosReserva);
		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
