package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Banco;


import DAO.BancoDAO;

public class IncluiBanco implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String desBan = request.getParameter("desc_banco");
		String pgJsp = "/incluiBanco.jsp";
		
		
		HttpSession sessao = request.getSession();
		Banco itemBan = new Banco();
		
		if(desBan!=null&&desBan!=""){	
			itemBan.setDescricao(desBan);

			String codBan = request.getParameter("cod_banco");
			String sigBan = request.getParameter("sigla_banco");

			if(codBan!=null&&codBan!=""){
				itemBan.setCodigo(Integer.parseInt(codBan));
			}

			if(sigBan!=null&&sigBan!="0"){	
				itemBan.setSigla(sigBan);
			}

			Banco itemRecBan = BancoDAO.consultarBanco(itemBan);
			mens = "Banco já cadastrado: "+itemBan.getDescricao(); 

			if(itemRecBan==null){
				BancoDAO daoIncBan = new BancoDAO();
				daoIncBan.incluirBanco(itemBan);
				mens = itemBan.getDescricao()+" Incluído! ";
			}
			
			request.setAttribute("objBan", itemRecBan);
			sessao.setAttribute("objBan", itemRecBan);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
