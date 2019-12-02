package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Cargo;


import DAO.CargoDAO;

public class IncluiCargo implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String desCar = request.getParameter("desc_cargo");
		String pgJsp = "/incluiCargo.jsp";
		
		
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
			mens = "Cargo já cadastrado: "+itemCar.getDescricao();  

			if(itemRecCar==null){
				CargoDAO daoIncCar = new CargoDAO();
				daoIncCar.incluirCargo(itemCar);
				mens = itemCar.getDescricao()+" Incluído! ";
			}
			
			request.setAttribute("objCar", itemRecCar);
			sessao.setAttribute("objCar", itemRecCar);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
