package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.TipoEntrada;


import DAO.TipoEntradaDAO;

public class ConsultaTipoEntrada implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiTipoEntrada.jsp";
		String desTipEnt = request.getParameter("desc_tipo_entrada");
		String codTipEnt = request.getParameter("cod_tipo_entrada");

		HttpSession sessao = request.getSession();
		TipoEntrada itemTipEnt = new TipoEntrada();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		if((desTipEnt!=null&&desTipEnt!="")||(codTipEnt!=null&&codTipEnt!="")){	
			itemTipEnt.setDescricao(desTipEnt);

			if(codTipEnt!=null&&codTipEnt!=""){
				itemTipEnt.setCodigo(Integer.parseInt(codTipEnt));
			}
			itemTipEnt.setNucleo(objOperador.getNucleo());
			
			TipoEntrada itemRTipEnt = TipoEntradaDAO.consultarTipoEntrada(itemTipEnt);
			mens = "Cadastro não encontrado: "+itemTipEnt.getDescricao();  

			if(itemRTipEnt!=null){
				itemTipEnt = itemRTipEnt;
				mens = "Tipo de entrada localizada: "+itemRTipEnt.getDescricao();  
			}

			request.setAttribute("objTipEnt", itemRTipEnt);
			sessao.setAttribute("objTipEnt", itemRTipEnt);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
