package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.TipoEvento;


import DAO.TipoEventoDAO;

public class AlteraTipoEvento implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiTipoEvento.jsp";
		String desTipSai = request.getParameter("desc_tipo_evento");
		
		HttpSession sessao = request.getSession();
		TipoEvento itemTipEve = new TipoEvento();

		if(desTipSai!=null&&desTipSai!=""){	
			itemTipEve.setDescricao(desTipSai);
				
			String codTipEve = request.getParameter("cod_tipo_evento");
		
			if(codTipEve!=null&&codTipEve!=""){
				itemTipEve.setCodigo(Integer.parseInt(codTipEve));
			}
			
			TipoEvento itemRTipEve = TipoEventoDAO.consultarTipoEvento(itemTipEve);
			mens = "Cadastro não encontrado: "+itemTipEve.getDescricao();  

			if(itemRTipEve!=null){
				TipoEventoDAO daoAltF = new TipoEventoDAO(); 
				daoAltF.alterarTipoEvento(itemTipEve);

				itemTipEve = TipoEventoDAO.consultarTipoEvento(itemTipEve);
				mens = "Item atualizado: "+itemTipEve.getDescricao();
			}

			request.setAttribute("objTipEve", itemTipEve);
			sessao.setAttribute("objTipEve", itemTipEve);
		
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
