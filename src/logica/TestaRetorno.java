package logica;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestaRetorno implements LogicaDeNegocio{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/testaRetorno.jsp";
/*
		String retornoRecebimento = request.getParameter("id_transacao");
		int Status=1;
		
		if(retornoRecebimento.isEmpty()||retornoRecebimento==null){
			Status=0;
			retornoRecebimento="Transação Abortada";
		}
		
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
