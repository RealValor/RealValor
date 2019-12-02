package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.EntradaDAO;
import DAO.HistoricoRecebimentoDAO;
import DAO.ListaDAO;
import DAO.MovAtualDAO;
import beans.Login;
import beans.MovAtual;
import beans.Recibo;

public class CancelaRecibo implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens="Recibo cancelado";
		String pgJsp = "/cancelaRecibo.jsp";
		String pRec  = request.getParameter("numero_recibo");
		String pAno  = request.getParameter("ano_paga");

		HttpSession sessao = request.getSession();

		int numRec=Integer.parseInt(pRec);
		int numAno=Integer.parseInt(pAno);

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		MovAtual itemMov = new MovAtual();
		
		itemMov.setFechado("N");
		itemMov.setNucleo(objOperador.getNucleo());
		MovAtual itemRecMov = MovAtualDAO.consultarMovAtual(itemMov);

		String[] situPaga = new String[2];
		situPaga[0] = "A";
		situPaga[1] = "Associado";

	    if(request.getParameter("situ_paga")!=null&&request.getParameter("situ_paga")!=""){
	    	situPaga[0] = request.getParameter("situ_paga");
	    	situPaga[1] = situPaga[0]=="N"?"Não Associado":"Associado";
	    }

	    EntradaDAO objEntrada = new EntradaDAO();

		if(!objEntrada.checaReciboEmMovimentoAberto(itemRecMov.getNucleo().getCodigo(), numRec, numAno)){
			mens="Recibo "+numRec+"/"+numAno+" não pode ser cancelado devido a finalização do mês "+(itemRecMov.getMes()-1)+"/"+itemRecMov.getAno();
			sessao.setAttribute("excluido", "N");
		}else{

			EntradaDAO objEntrDAO = new EntradaDAO();
			objEntrDAO.excluirEntrada(itemRecMov.getNucleo().getCodigo(), numRec, numAno);

			HistoricoRecebimentoDAO objHistDAO = new HistoricoRecebimentoDAO();
			objHistDAO.cancelarHistRec(itemRecMov.getNucleo().getCodigo(), numRec, numAno, objOperador.getNome());

			//------------------------------------------------------------------------

			ArrayList<Recibo> valorPagamentoLista = new ArrayList<Recibo>();
			ListaDAO objListaDAO = new ListaDAO();

			valorPagamentoLista = ListaDAO.buscaPagamentoLista(objOperador.getNucleo(), numRec, numAno);

			if(valorPagamentoLista!=null){
				for (Recibo recibo : valorPagamentoLista) {
					//Reverter recebimento das listas.
					objListaDAO.revertePagamentoLista(recibo);
				}
			}

			//------------------------------------------------------------------------
			
			mens="Recibo "+numRec+"/"+numAno+" cancelado com sucesso";
			
			sessao.setAttribute("excluido", "S");			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
