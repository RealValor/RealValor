package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.TipoEvento;


import DAO.TipoEventoDAO;

public class ConsultaTipoEvento implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiTipoEvento.jsp";
		String desTipEve = request.getParameter("desc_tipo_evento");
		String codTipEve = request.getParameter("cod_tipo_evento");

		HttpSession sessao = request.getSession();
		TipoEvento itemTipEve = new TipoEvento();
		
		if((desTipEve!=null&&desTipEve!="")||(codTipEve!=null&&codTipEve!="")){	
			itemTipEve.setDescricao(desTipEve);

			if(codTipEve!=null&&codTipEve!=""){
				itemTipEve.setCodigo(Integer.parseInt(codTipEve));
			}

			TipoEvento itemRTipEve = TipoEventoDAO.consultarTipoEvento(itemTipEve);
			mens = "Cadastro não encontrado: "+itemTipEve.getDescricao();  

			if(itemRTipEve!=null){
				itemTipEve = itemRTipEve;
				mens = "Tipo de entrada localizada: "+itemRTipEve.getDescricao();  
			}

			request.setAttribute("objTipEve", itemRTipEve);
			sessao.setAttribute("objTipEve", itemRTipEve);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
