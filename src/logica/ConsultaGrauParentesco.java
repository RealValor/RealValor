package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.GrauParentesco;


import DAO.GrauParentescoDAO;

public class ConsultaGrauParentesco implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiGrauParentesco.jsp";
		String desCar = request.getParameter("desc_grauparentesco");
		String codCar  = request.getParameter("cod_grauparentesco");

		HttpSession sessao = request.getSession();
		GrauParentesco itemCar = new GrauParentesco();
		
		if((desCar!=null&&desCar!="")||(codCar!=null&&codCar!="")){	
			itemCar.setDescricao(desCar);

			if(codCar!=null&&codCar!=""){
				itemCar.setCodigo(Integer.parseInt(codCar));
			}

			GrauParentesco itemRecGrauParentesco = GrauParentescoDAO.consultarGrauParentesco(itemCar);
			mens = "Cadastro não encontrado: "+itemCar.getDescricao();  

			if(itemRecGrauParentesco!=null){
				itemCar = itemRecGrauParentesco;
				mens = "Grau Parentesco localizado: "+itemRecGrauParentesco.getDescricao();  
			}

			request.setAttribute("objGrauParentesco", itemRecGrauParentesco);
			sessao.setAttribute("objGrauParentesco", itemRecGrauParentesco);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
