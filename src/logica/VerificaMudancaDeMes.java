package logica;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;

import DAO.HistoricoRecebimentoDAO;

public class VerificaMudancaDeMes implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession sessao = request.getSession();

		if(sessao.getAttribute("listaRecibo")!=null){
			
			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");
			
			HistoricoRecebimentoDAO objHistDAO = new HistoricoRecebimentoDAO();
			int retorno = objHistDAO.verificarMudancaDeMes(objOperador.getNucleo().getCodigo());
			
			StringBuffer xml = new StringBuffer(   
			"<?xml version='1.0' encoding='ISO-8859-1'?>");   
			
			xml.append("<mudames>");
				xml.append("<mudoumes>");
					xml.append("<situacao>");   
						xml.append(retorno);
					xml.append("</situacao>");   
				xml.append("</mudoumes>");
			xml.append("</mudames>");				

			response.setContentType("text/xml");   
			response.setHeader("Cache-Control", "no-cache");   
			response.getWriter().write(xml.toString());
		}
	}
}
