package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.GrauParentesco;


import DAO.GrauParentescoDAO;

public class ExcluiGrauParentesco implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiGrauParentesco.jsp";
		String desCar = request.getParameter("desc_grauparentesco");
		
		HttpSession sessao = request.getSession();
		GrauParentesco itemGrauParentesco = new GrauParentesco();

		if(desCar!=null&&desCar!=""){	
			itemGrauParentesco.setDescricao(desCar);
				
			String codCar = request.getParameter("cod_grauparentesco");
		
			if(codCar!=null&&codCar!=""){
				itemGrauParentesco.setCodigo(Integer.parseInt(codCar));
			}
			
			GrauParentesco itemRecGrauParentesco = GrauParentescoDAO.consultarGrauParentesco(itemGrauParentesco);
			mens = "Cadastro não encontrado: "+itemGrauParentesco.getDescricao();  

			if(itemRecGrauParentesco!=null){

				itemRecGrauParentesco = GrauParentescoDAO.consultarGrauParentescoHistorico(itemRecGrauParentesco.getCodigo());

				if(itemRecGrauParentesco!=null){
					mens = "O GrauParentesco "+itemGrauParentesco.getDescricao()+" possui histórico. Isto bloqueia a exclusão!";
				}else{
					GrauParentescoDAO daoAltF = new GrauParentescoDAO(); 
					daoAltF.excluirGrauParentesco(itemGrauParentesco);
					mens = "Cadastro excluído: "+itemGrauParentesco.getDescricao();
					itemGrauParentesco=null;
				}
			}

			request.setAttribute("objGrauParentesco", itemRecGrauParentesco);
			sessao.setAttribute("objGrauParentesco", itemRecGrauParentesco);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
