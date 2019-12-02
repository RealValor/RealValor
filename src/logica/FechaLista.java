package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Lista;
import beans.Nucleo;


import DAO.ListaDAO;

public class FechaLista implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiLista.jsp";

		int numLista = 0;
		if(request.getParameter("num")!=null&&request.getParameter("num")!="")
			numLista = Integer.parseInt(request.getParameter("num"));

		int ano = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			ano = Integer.parseInt(request.getParameter("ano"));
		
		HttpSession sessao = request.getSession();
		
		Nucleo objNucleo = new Nucleo();
		objNucleo = (Nucleo)sessao.getAttribute("objNucleo");

		Lista itemLista = new Lista();

		if(numLista!=0&&ano!=0){
			itemLista.setNumero(numLista);
			itemLista.setAno(ano);
			itemLista.setNucleo(objNucleo);

			ListaDAO recebimentoListaDAO = new ListaDAO();
			int totalPendente = recebimentoListaDAO.consultaRecebimentoLista(itemLista);
			
			mens = "Existe(m) "+totalPendente+" pagamento(s) em aberto. Lista não pode ser fechada.";
			//se existe recebimento em aberto, avisa que não deve excluir.
			if(totalPendente<1){

				ListaDAO daoRecF = new ListaDAO();
				Lista itemRecF = daoRecF.consultarLista(itemLista);
	
				mens = "Lista "+itemLista.getNumero()+"/"+itemLista.getAno()+" não encontrada! ";

				if(itemRecF!=null){
					
					ListaDAO daoIncF = new ListaDAO();
					daoIncF.fecharLista(itemLista);
					mens = "Lista "+itemRecF.getNumero()+"/"+itemRecF.getAno()+" fechada! ";

					sessao.removeAttribute("listListas");
					ArrayList<Lista> listListas = ListaDAO.listarLista(objNucleo);
					if(listListas != null){
						sessao.setAttribute("listListas", listListas);
					}
					
					ArrayList<Lista> listFechadas = ListaDAO.listarListaFechada(objNucleo);
					if(listFechadas != null){
						sessao.setAttribute("listFechadas", listFechadas);
						sessao.setAttribute("statusLista", 1);
					}
				}
			}

		}

		request.setAttribute("objLista", itemLista);
		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
