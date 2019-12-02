package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.TipoSaida;


import DAO.TipoSaidaDAO;

public class ConsultaTipoSaida implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiTipoSaida.jsp";
		String desTipSai = request.getParameter("desc_tipo_saida");
		String codTipSai = request.getParameter("cod_tipo_saida");

		HttpSession sessao = request.getSession();
		TipoSaida itemTipSai = new TipoSaida();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		if((desTipSai!=null&&desTipSai!="")||(codTipSai!=null&&codTipSai!="")){	
			itemTipSai.setDescricao(desTipSai);

			if(codTipSai!=null&&codTipSai!=""){
				itemTipSai.setCodigo(Integer.parseInt(codTipSai));
			}
			
			itemTipSai.setNucleo(objOperador.getNucleo());

			TipoSaida itemRTipSai = TipoSaidaDAO.consultarTipoSaida(itemTipSai);
			mens = "Cadastro não encontrado: "+itemTipSai.getDescricao();  

			if(itemRTipSai!=null){
				itemTipSai = itemRTipSai;
				mens = "Tipo de entrada localizada: "+itemRTipSai.getDescricao();  
			}

			request.setAttribute("objTipSai", itemRTipSai);
			sessao.setAttribute("objTipSai", itemRTipSai);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
