package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.TipoEvento;


import DAO.TipoEventoDAO;

public class IncluiTipoEvento implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String desTipEnt = request.getParameter("desc_tipo_evento");
		String pgJsp = "/incluiTipoEvento.jsp";
		
		
		HttpSession sessao = request.getSession();
		TipoEvento itemTipEnt = new TipoEvento();
		
		if(desTipEnt!=null&&desTipEnt!=""){	
			itemTipEnt.setDescricao(desTipEnt);

			String codTipEnt = request.getParameter("cod_tipo_evento");
			
			if(codTipEnt!=null&&codTipEnt!=""){
				itemTipEnt.setCodigo(Integer.parseInt(codTipEnt));
			}

			TipoEvento itemRecTipEnt = TipoEventoDAO.consultarTipoEvento(itemTipEnt);
			mens = "TipoEvento já cadastrado: "+itemTipEnt.getDescricao();  

			if(itemRecTipEnt==null){
				TipoEventoDAO daoIncCar = new TipoEventoDAO();
				daoIncCar.incluirTipoEvento(itemTipEnt);
				mens = itemTipEnt.getDescricao()+" Incluído! ";
			}
			
			request.setAttribute("objTipEnt", itemRecTipEnt);
			sessao.setAttribute("objTipEnt", itemRecTipEnt);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
