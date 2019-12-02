package logica;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ReciboDAO;
import beans.HistoricoRecebimento;
import beans.Login;
import beans.MovAtual;

public class BuscaTotaldeRecibos implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int codPaga = 0;
		if(request.getParameter("cod_paga")!=null&&request.getParameter("cod_paga")!="")
			codPaga = Integer.parseInt(request.getParameter("cod_paga"));
		
		String situPaga = "";
		if(request.getParameter("situ_paga")!=null&&request.getParameter("situ_paga")!="")
			situPaga = request.getParameter("situ_paga");

		int ano = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			ano = Integer.parseInt(request.getParameter("ano"));
	
		int mes = 0;
		if(request.getParameter("mes")!=null&&request.getParameter("mes")!="")
			mes = Integer.parseInt(request.getParameter("mes"));

		HttpSession sessao = request.getSession();
		
		//System.out.println("aqui 1 - busca total");
		
		if(codPaga!=0){
			
			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");
			
			MovAtual objMovAtual = new MovAtual();
			objMovAtual.setMes(mes);
			objMovAtual.setAno(ano);
        	
			ArrayList<HistoricoRecebimento> listRecibo=null;
			listRecibo = ReciboDAO.listarRecibo(codPaga,objOperador.getNucleo().getCodigo(), objMovAtual, 1, situPaga);

			int quantidade = listRecibo.size();
			
			//System.out.println("quantidade "+quantidade);
			
			StringBuffer xml = new StringBuffer(   
			"<?xml version='1.0' encoding='ISO-8859-1'?>");   
			
			xml.append("<recibos>");
				xml.append("<quantidade>");
					xml.append("<total>");   
						xml.append(quantidade);
					xml.append("</total>");   
				xml.append("</quantidade>");
			xml.append("</recibos>");				

			response.setContentType("text/xml");   
			response.setHeader("Cache-Control", "no-cache");   
			response.getWriter().write(xml.toString());
		}
	}
}
