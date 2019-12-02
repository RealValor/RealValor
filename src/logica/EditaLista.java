package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Lista;
import beans.Nucleo;
import beans.Socio;


import DAO.ListaDAO;

public class EditaLista implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/editaLista.jsp";

		int numero = 0;
		if(request.getParameter("num")!=null&&request.getParameter("num")!="")
			numero = Integer.parseInt(request.getParameter("num"));

		int ano = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			ano = Integer.parseInt(request.getParameter("ano"));

		int socio = 0;
		if(request.getParameter("socio")!=null&&request.getParameter("socio")!="")
			socio = Integer.parseInt(request.getParameter("socio"));

		String fechada = "0";
		if(request.getParameter("fechada")!=null&&request.getParameter("fechada")!="")
			fechada = request.getParameter("fechada");

		HttpSession sessao = request.getSession();
		Lista itemLista = new Lista();
		
		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");

		sessao.removeAttribute("listaRecibo");
		sessao.removeAttribute("listaSocio");
		sessao.removeAttribute("listDependente");
		
		int valor = 0;

		if(sessao.getAttribute("numeroRecibo")!=null){
			int numRecibo = (Integer) sessao.getAttribute("numeroRecibo");
			if(numRecibo==0){
				if(request.getParameter("valor")!=null&&request.getParameter("valor")!="")
					valor = Integer.parseInt(request.getParameter("valor"));
			}
		}
		
		itemLista.setNumero(numero);
		itemLista.setNucleo(objNucleo);
		itemLista.setAno(ano);
		
		if(socio!=0&&valor!=0){

			Socio socioDevedor = new Socio();
			socioDevedor.setCodigo(socio);

			itemLista.setSocioDevedor(socioDevedor);
			itemLista.setValor(valor);
			
			ListaDAO objListaDAO = new ListaDAO();
			objListaDAO.atualizaValorLista(itemLista);
		}else{

			ArrayList<Lista> detalheLista = ListaDAO.consultarDetalheLista(itemLista);

			ListaDAO objTotListaDAO = new ListaDAO();
			Double total = objTotListaDAO.buscaTotalLista(itemLista);
			
			request.setAttribute("totalLista", total);			
			sessao.setAttribute("objDetalheLista", detalheLista.get(0));	
			sessao.setAttribute("detalheLista", detalheLista);
			sessao.setAttribute("fechamentoLista", fechada);
			
			request.setAttribute("retorno", mens);
			rd = request.getRequestDispatcher(pgJsp);
			rd.forward(request, response);
		}
	}
}
