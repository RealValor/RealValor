package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.BairroDAO;
import beans.Bairro;

public class ConsultaBairro implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiBairro.jsp";
		String desCar = request.getParameter("desc_bairro");
		String codCar  = request.getParameter("cod_bairro");

		HttpSession sessao = request.getSession();
		Bairro itemCar = new Bairro();
		
		if((desCar!=null&&desCar!="")||(codCar!=null&&codCar!="")){	
			itemCar.setNome(desCar);

			if(codCar!=null&&codCar!=""){
				itemCar.setCodigo(Integer.parseInt(codCar));
			}

			Bairro itemRecBairro = BairroDAO.consultarBairro(itemCar);
			mens = "Cadastro não encontrado: "+itemCar.getNome();  

			if(itemRecBairro!=null){
				itemCar = itemRecBairro;
				mens = "Bairro localizado: "+itemRecBairro.getNome();  
			}

			request.setAttribute("objBairro", itemRecBairro);
			sessao.setAttribute("objBairro", itemRecBairro);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
