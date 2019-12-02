package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.TipoSaida;


import DAO.TipoSaidaDAO;

public class IncluiTipoSaida implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String descTipSai = request.getParameter("desc_tipo_saida");
		String ativoTipSai = request.getParameter("ativo_tipo_saida");
		String pgJsp = "/incluiTipoSaida.jsp";
		
		
		HttpSession sessao = request.getSession();
		TipoSaida itemTipSai = new TipoSaida();
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		if(descTipSai!=null&&descTipSai!=""){	
			itemTipSai.setDescricao(descTipSai);
			itemTipSai.setAtivo(ativoTipSai);
			
			String codTipSai = request.getParameter("cod_tipo_saida");
			
			if(codTipSai!=null&&codTipSai!=""){
				itemTipSai.setCodigo(Integer.parseInt(codTipSai));
			}

			itemTipSai.setNucleo(objOperador.getNucleo());

			TipoSaida itemRecTipSai = TipoSaidaDAO.consultarTipoSaida(itemTipSai);
			mens = "TipoSaida já cadastrado: "+itemTipSai.getDescricao();  

			if(itemRecTipSai==null){
				TipoSaidaDAO daoIncCar = new TipoSaidaDAO();
				daoIncCar.incluirTipoSaida(itemTipSai);
				mens = itemTipSai.getDescricao()+" Incluído! ";
			}
			
			request.setAttribute("objTipSai", itemRecTipSai);
			sessao.setAttribute("objTipSai", itemRecTipSai);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
