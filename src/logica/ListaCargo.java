package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Cargo;


import DAO.CargoDAO;

public class ListaCargo implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiCargo.jsp";

		ArrayList<Cargo> listCar = CargoDAO.listarCargo();
		if(listCar != null){
			request.setAttribute("listCar", listCar);		                
		}
		pgJsp = "/listaCargo.jsp";

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
