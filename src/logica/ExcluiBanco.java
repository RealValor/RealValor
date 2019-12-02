package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Banco;


import DAO.BancoDAO;

public class ExcluiBanco implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiBanco.jsp";
		String desBan = request.getParameter("desc_banco");
		
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
			mens = "Cadastro não encontrado: "+itemBan.getDescricao();  

			if(itemRecBan!=null){
				BancoDAO daoAltF = new BancoDAO(); 
				daoAltF.excluirBanco(itemBan);
				mens = "Cadastro excluído: "+itemBan.getDescricao();
				itemBan=null;
			}

			request.setAttribute("objBan", itemBan);
			sessao.setAttribute("objBan", itemBan);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
