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


		//Listar todas as transa��es em que o status seja diferente de 4; 6; 7; 8 e 0.
		//select *from tb_recebimento_online where cd_status_transacao not in (4,6,7,8,0)

		ArrayList<TransacaoOnline> listTransacaoOnline = null;

		RecebimentoOnlineDAO objRecebimentoOnlineDAO = new RecebimentoOnlineDAO();
		listTransacaoOnline = objRecebimentoOnlineDAO.consultaRecebimentoOnlineEmEspera();

		sessao.setAttribute("listTransacaoOnline", listTransacaoOnline);

		/*
		1	Aguardando pagamento: o comprador iniciou a transa��o, mas at� o momento o PagSeguro n�o recebeu nenhuma informa��o sobre o pagamento.
		2	Em an�lise: o comprador optou por pagar com um cart�o de cr�dito e o PagSeguro est� analisando o risco da transa��o.
		3	Paga: a transa��o foi paga pelo comprador e o PagSeguro j� recebeu uma confirma��o da institui��o financeira respons�vel pelo processamento.

		4	Dispon�vel: a transa��o foi paga e chegou ao final de seu prazo de libera��o sem ter sido retornada e sem que haja nenhuma disputa aberta.
			PASSA PARA VERDE, GERA O RECIBO.

		5	Em disputa: o comprador, dentro do prazo de libera��o da transa��o, abriu uma disputa.

		6	Devolvida: o valor da transa��o foi devolvido para o comprador. VOLTA PARA VERMELHO
		7	Cancelada: a transa��o foi cancelada sem ter sido finalizada. VOLTA PARA VERMELHO
		8	Chargeback debitado: o valor da transa��o foi devolvido para o comprador. VOLTA PARA VERMELHO

		9	Em contesta��o: o comprador abriu uma solicita��o de chargeback junto � operadora do cart�o de cr�dito.
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
