package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Cargo;


import DAO.CargoDAO;

public class AlteraCargo implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiCargo.jsp";
		String desCar = request.getParameter("desc_cargo");
		
		HttpSession sessao = request.getSession();
		Cargo itemCar = new Cargo();

		if(desCar!=null&&desCar!=""){	
			itemCar.setDescricao(desCar);
				
			String codCar = request.getParameter("cod_cargo");
			String sigCar = request.getParameter("sigla_cargo");
		
			if(codCar!=null&&codCar!=""){
				itemCar.setCodigo(Integer.parseInt(codCar));
			}
			
			if(sigCar!=null&&sigCar!="0"){	
				itemCar.setSigla(sigCar);
			}
			
			Cargo itemRecCar = CargoDAO.consultarCargo(itemCar);
			mens = "Cadastro n�o encontrado: "+itemCar.getDescricao();  

			if(itemRecCar!=null){
				CargoDAO daoAltF = new CargoDAO(); 
				daoAltF.alterarCargo(itemCar);

				itemCar = CargoDAO.consultarCargo(itemCar);
				mens = "Item atualizado: "+itemCar.getDescricao();
			}

			request.setAttribute("objCar", itemCar);
			sessao.setAttribute("objCar", itemCar);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
