package logica;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.Recibo;


import DAO.RecebimentoOnlineDAO;

public class RetornoRecebimento implements LogicaDeNegocio{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/retornoPagamento.jsp";
		String retornoRecebimento = request.getParameter("id_transacao");
		int Status=1;
		
		if(retornoRecebimento.isEmpty()||retornoRecebimento==null){
			Status=0;
			retornoRecebimento="Transação Abortada";
		}
		
		HttpSession sessao = request.getSession();
		sessao.setAttribute("id_transacao",retornoRecebimento);
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		Recibo objRecibo = new Recibo();
		objRecibo = (Recibo) sessao.getAttribute("objRecibo");
		
		RecebimentoOnlineDAO objRecebimentoOnlineDAO = new RecebimentoOnlineDAO();
		objRecebimentoOnlineDAO.incluiIdTransacaoOnline(objRecibo, retornoRecebimento, Status, objOperador.getNucleo().getCodigo());

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
