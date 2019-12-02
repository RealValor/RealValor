package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.TipoEvento;


import DAO.TipoEventoDAO;

public class ExcluiTipoEvento implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiTipoEvento.jsp";
		String desTipSai = request.getParameter("desc_tipo_evento");
		
		HttpSession sessao = request.getSession();
		TipoEvento itemTipSai = new TipoEvento();

		if(desTipSai!=null&&desTipSai!=""){	
			itemTipSai.setDescricao(desTipSai);
				
			String codTipEve = request.getParameter("cod_tipo_evento");
	
			if(codTipEve!=null&&codTipEve!=""){
				itemTipSai.setCodigo(Integer.parseInt(codTipEve));
			}
			
			TipoEvento itemRecCar = TipoEventoDAO.consultarTipoEvento(itemTipSai);
			mens = "Cadastro não encontrado: "+itemTipSai.getDescricao();  

			if(itemRecCar!=null){
				TipoEventoDAO daoAltF = new TipoEventoDAO(); 
				daoAltF.excluirTipoEvento(itemTipSai);
				mens = "Cadastro excluído: "+itemTipSai.getDescricao();
				itemTipSai=null;
			}

			request.setAttribute("objTipSai", itemTipSai);
			sessao.setAttribute("objTipSai", itemTipSai);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
