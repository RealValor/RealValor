package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Lista;
import beans.Nucleo;


import DAO.ListaDAO;

public class ExcluiLista implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "Exclusão de Listas";
		String pgJsp = "/stu?p=IncluiLista"; 
		
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
			
			mens = "Existe(m) "+totalPendente+" pagamento(s) em aberto. Lista não pode ser excluída.";
			//se existe recebimento em aberto, avisa que não deve excluir.
			if(totalPendente<1){

				ListaDAO doacaoListaDAO = new ListaDAO();
				totalPendente = doacaoListaDAO.consultaDoacaoLista(itemLista); //checa se houveram doações

				ListaDAO itemListaDAO = new ListaDAO();
				mens = "Lista excluída.";

				if(totalPendente<1){
					//se não houveram doações na lista, exclui
					itemListaDAO.excluirLista(itemLista);
				}else{ 
					//houveram doações, checar se a lista está fechada
					if(itemListaDAO.consultaFechamentoLista(itemLista).equalsIgnoreCase("S")){
						itemListaDAO.excluirLista(itemLista);
					}else{
						//mens = "Houveram doações. A lista "+itemLista.getNumero()+"/"+itemLista.getAno()+" será apenas fechada.";
						pgJsp = "/stu?p=FechaLista&num="+itemLista.getNumero()+"&ano="+itemLista.getAno();
					}
				}
			}
			
			sessao.removeAttribute("listListas");
		}
	
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
