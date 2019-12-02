package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Cargo;


import DAO.CargoDAO;

public class ConsultaCargo implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiCargo.jsp";
		String desCar = request.getParameter("desc_cargo");
		String codCar  = request.getParameter("cod_cargo");

		HttpSession sessao = request.getSession();
		Cargo itemCar = new Cargo();
		
		if((desCar!=null&&desCar!="")||(codCar!=null&&codCar!="")){	
			itemCar.setDescricao(desCar);

			String sigCar = request.getParameter("sigla_cargo");

			if(codCar!=null&&codCar!=""){
				itemCar.setCodigo(Integer.parseInt(codCar));
			}

			if(sigCar!=null&&sigCar!="0"){	
				itemCar.setSigla(sigCar);
			}

			Cargo itemRecCar = CargoDAO.consultarCargo(itemCar);
			mens = "Cadastro não encontrado: "+itemCar.getDescricao();  

			if(itemRecCar!=null){
				itemCar = itemRecCar;
				mens = "Cargo localizado: "+itemRecCar.getDescricao();  
			}

			request.setAttribute("objCar", itemRecCar);
			sessao.setAttribute("objCar", itemRecCar);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
