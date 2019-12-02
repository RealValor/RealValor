package logica;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Nucleo;

import DAO.StuDAOException;

public class NotificaSocio implements LogicaDeNegocio{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.addHeader("Access-Control-Allow-Origin", "https://sandbox.pagseguro.uol.com.br");
		response.addHeader("Access-Control-Allow-Credentials", "https://sandbox.pagseguro.uol.com.br");

		HttpSession sessao = request.getSession();

		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");

		//System.out.println("Aqui 1 ");
		//response.setContentType("text/html");
		
		String retornoNotificacao = request.getParameter("notificationCode");
		String tipoNotificacao = request.getParameter("notificationType");
		
		System.out.println("Retorno: "+retornoNotificacao);
		System.out.println("vai chamar pesquisaTransação "+tipoNotificacao);

		//HttpSession sessao = request.getSession();
		//sessao.setAttribute("id_transacao",retornoNotificacao);
		
		try {
			PesquisaTransacao.pesquisaTransacao(retornoNotificacao,objNucleo);
		} catch (StuDAOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
