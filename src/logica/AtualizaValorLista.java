package logica;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ListaDAO;
import beans.Lista;
import beans.Nucleo;
import beans.Socio;

public class AtualizaValorLista implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//RequestDispatcher rd = null;
		//String mens = "";
		//String pgJsp = "/editaLista.jsp";

		int numero = 0;
		if(request.getParameter("num")!=null&&request.getParameter("num")!="")
			numero = Integer.parseInt(request.getParameter("num"));

		int ano = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			ano = Integer.parseInt(request.getParameter("ano"));
		
		int codigoSocio = 0;
		if(request.getParameter("socio")!=null&&request.getParameter("socio")!="")
			codigoSocio = Integer.parseInt(request.getParameter("socio"));

		Double valor = 0.0;
		if(request.getParameter("valor")!=null&&request.getParameter("valor")!="")
			valor = Double.parseDouble(request.getParameter("valor"));
		
		HttpSession sessao = request.getSession();
		
		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");
		
		if(codigoSocio>0){	
			Lista itemLista = new Lista();

			itemLista.setNumero(numero);
			itemLista.setAno(ano);

			itemLista.setNucleo(objNucleo);
			
			Socio objSocio = new Socio();
			objSocio.setCodigo(codigoSocio);

			itemLista.setSocioDevedor(objSocio);
			itemLista.setValor(valor);

			ListaDAO objListaDAO = new ListaDAO(); 
			objListaDAO.atualizaValorLista(itemLista);

			ListaDAO objTotListaDAO = new ListaDAO(); 
			Double total = objTotListaDAO.buscaTotalLista(itemLista);

			//System.out.println("total: "+total);

			request.setAttribute("totalLista", total);

			StringBuffer xml = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			xml.append("<totais>");   
				xml.append("<total>");   
				   xml.append("<valor>");   
				      xml.append(total);					
				   xml.append("</valor>");
				xml.append("</total>");   
			xml.append("</totais>");   

			response.setContentType("text/xml");   
			response.setHeader("Cache-Control", "no-cache");   
			response.getWriter().write(xml.toString());

		}
	}
}
