package logica;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Divida;
import beans.Saida;


import DAO.DividaDAO;

public class AtualizaValorDivida implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int numero = 0;
		if(request.getParameter("num")!=null&&request.getParameter("num")!="")
			numero = Integer.parseInt(request.getParameter("num"));

		int ano = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			ano = Integer.parseInt(request.getParameter("ano"));

		int mes = 0;
		if(request.getParameter("mes")!=null&&request.getParameter("mes")!="")
			mes = Integer.parseInt(request.getParameter("mes"));

		Double valor = 0.0;
		if(request.getParameter("valor")!=null&&request.getParameter("valor")!="")
			valor = Double.parseDouble(request.getParameter("valor"));
		
		if(valor>0){	
			Divida itemDivida = new Divida();

			Saida itemSaida = new Saida();
			itemSaida.setNumero(numero);
			
			itemDivida.setSaida(itemSaida);
			itemDivida.setAno(ano);
			itemDivida.setMes(mes);
			itemDivida.setValor(valor);
			itemDivida.setPago("N");

			Double diferenca = 0.0;
			diferenca = DividaDAO.checaDivida(itemDivida);

			if (diferenca>=0){
				DividaDAO objListaDAO = new DividaDAO();
				objListaDAO.alterarDivida(itemDivida);
			}else{
				valor=-1.0;
			}
			
			StringBuffer xml = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			xml.append("<totais>");   
				xml.append("<total>");   
				   xml.append("<valor>");   
				      xml.append(valor);					
				   xml.append("</valor>");
				xml.append("</total>");   
			xml.append("</totais>");   

			response.setContentType("text/xml");   
			response.setHeader("Cache-Control", "no-cache");   
			response.getWriter().write(xml.toString());

		}
		
	}
}
