package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Banco;


import DAO.BancoDAO;

public class ConsultaBanco implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiBanco.jsp";
		String desBan = request.getParameter("desc_banco");
		String codBan  = request.getParameter("cod_banco");

		HttpSession sessao = request.getSession();
		Banco itemBan = new Banco();
		
		if((desBan!=null&&desBan!="")||(codBan!=null&&codBan!="")){
			itemBan.setDescricao(desBan);

			String sigBan = request.getParameter("sigla_banco");

			if(codBan!=null&&codBan!=""){
				itemBan.setCodigo(Integer.parseInt(codBan));
			}

			if(sigBan!=null&&sigBan!="0"){	
				itemBan.setSigla(sigBan);
			}

			Banco itemRecBan = BancoDAO.consultarBanco(itemBan);
			mens = "Cadastro não encontrado: "+itemBan.getDescricao();  

			if(itemRecBan!=null){
				itemBan = itemRecBan;
				mens = "Banco localizado: "+itemRecBan.getDescricao();  
			}

			request.setAttribute("objBan", itemRecBan);
			sessao.setAttribute("objBan", itemRecBan);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
