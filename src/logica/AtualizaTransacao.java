package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.TransacaoOnline;



import DAO.RecebimentoOnlineDAO;

public class AtualizaTransacao implements LogicaDeNegocio{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/atualizaTransacao.jsp";

		HttpSession sessao = request.getSession();


		//Listar todas as transações em que o status seja diferente de 4; 6; 7; 8 e 0.
		//select *from tb_recebimento_online where cd_status_transacao not in (4,6,7,8,0)

		ArrayList<TransacaoOnline> listTransacaoOnline = null;

		RecebimentoOnlineDAO objRecebimentoOnlineDAO = new RecebimentoOnlineDAO();
		listTransacaoOnline = objRecebimentoOnlineDAO.consultaRecebimentoOnlineEmEspera();

		sessao.setAttribute("listTransacaoOnline", listTransacaoOnline);

		/*
		1	Aguardando pagamento: o comprador iniciou a transação, mas até o momento o PagSeguro não recebeu nenhuma informação sobre o pagamento.
		2	Em análise: o comprador optou por pagar com um cartão de crédito e o PagSeguro está analisando o risco da transação.
		3	Paga: a transação foi paga pelo comprador e o PagSeguro já recebeu uma confirmação da instituição financeira responsável pelo processamento.

		4	Disponível: a transação foi paga e chegou ao final de seu prazo de liberação sem ter sido retornada e sem que haja nenhuma disputa aberta.
			PASSA PARA VERDE, GERA O RECIBO.

		5	Em disputa: o comprador, dentro do prazo de liberação da transação, abriu uma disputa.

		6	Devolvida: o valor da transação foi devolvido para o comprador. VOLTA PARA VERMELHO
		7	Cancelada: a transação foi cancelada sem ter sido finalizada. VOLTA PARA VERMELHO
		8	Chargeback debitado: o valor da transação foi devolvido para o comprador. VOLTA PARA VERMELHO

		9	Em contestação: o comprador abriu uma solicitação de chargeback junto à operadora do cartão de crédito.
		 */

		/*
		String transacao = request.getParameter("cd_transacao");
		HttpSession sessao = request.getSession();
		sessao.setAttribute("id_transacao",retornoRecebimento);

		Recibo objRecibo = new Recibo();
		objRecibo = (Recibo) sessao.getAttribute("objRecibo");

		RecebimentoOnlineDAO objRecebimentoOnlineDAO = new RecebimentoOnlineDAO();
		objRecebimentoOnlineDAO.incluiIdTransacaoOnline(objRecibo, retornoRecebimento, Status);
		 */

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
