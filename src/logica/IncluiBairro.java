package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Bairro;

import DAO.BairroDAO;

public class IncluiBairro implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String desBairro = request.getParameter("desc_bairro");
		String pgJsp = "/incluiBairro.jsp";
		
		HttpSession sessao = request.getSession();
		Bairro itemBairro = new Bairro();

		if(desBairro!=null&&desBairro!=""){	
			itemBairro.setNome(desBairro);


			String codBairro = request.getParameter("cod_bairro");

			if(codBairro!=null&&codBairro!=""){
				itemBairro.setCodigo(Integer.parseInt(codBairro));
			}

			Bairro itemRecBairro = BairroDAO.consultarBairro(itemBairro);
			mens = "Bairro já cadastrado: "+itemBairro.getNome();  


			if(itemRecBairro==null){

				BairroDAO daoIncBairro = new BairroDAO();
				daoIncBairro.incluirBairro(itemBairro);
				mens = itemBairro.getNome()+" Incluído! ";
			}
			
			request.setAttribute("objCar", itemRecBairro);
			sessao.setAttribute("objCar", itemRecBairro);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
