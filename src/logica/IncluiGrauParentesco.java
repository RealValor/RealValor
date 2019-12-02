package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.GrauParentesco;


import DAO.GrauParentescoDAO;

public class IncluiGrauParentesco implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String desGrauParentesco = request.getParameter("desc_grauparentesco");
		String pgJsp = "/incluiGrauParentesco.jsp";
		
		
		HttpSession sessao = request.getSession();
		GrauParentesco itemGrauParentesco = new GrauParentesco();
		
		if(desGrauParentesco!=null&&desGrauParentesco!=""){	
			itemGrauParentesco.setDescricao(desGrauParentesco);

			String codGrauParentesco = request.getParameter("cod_grauparentesco");

			if(codGrauParentesco!=null&&codGrauParentesco!=""){
				itemGrauParentesco.setCodigo(Integer.parseInt(codGrauParentesco));
			}

			GrauParentesco itemRecGrauParentesco = GrauParentescoDAO.consultarGrauParentescoExato(itemGrauParentesco);
			mens = "Grau Parentesco já cadastrado: "+itemGrauParentesco.getDescricao();  

			if(itemRecGrauParentesco==null){
				GrauParentescoDAO daoIncGrauParentesco = new GrauParentescoDAO();
				daoIncGrauParentesco.incluirGrauParentesco(itemGrauParentesco);
				mens = itemGrauParentesco.getDescricao()+" Incluído! ";
			}
			
			request.setAttribute("objGrauParentesco", itemRecGrauParentesco);
			sessao.setAttribute("objGrauParentesco", itemRecGrauParentesco);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
