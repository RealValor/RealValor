package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.BairroDAO;
import beans.Bairro;

public class AlteraBairro implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiBairro.jsp";
		String desBairro = request.getParameter("desc_bairro");
		
		HttpSession sessao = request.getSession();
		Bairro itemBairro = new Bairro();

		if(desBairro!=null&&desBairro!=""){	
			itemBairro.setNome(desBairro);
			
			String codBairro = request.getParameter("cod_bairro");

			if(codBairro!=null&&codBairro!=""){
				itemBairro.setCodigo(Integer.parseInt(codBairro));
			}
			
			Bairro itemRecCar = BairroDAO.consultarBairro(itemBairro);
			mens = "Cadastro não encontrado: "+itemBairro.getNome();  

			if(itemRecCar!=null){
				BairroDAO daoAltF = new BairroDAO(); 
				daoAltF.alterarBairro(itemBairro);

				itemBairro = BairroDAO.consultarBairro(itemBairro);
				mens = "Item atualizado: "+itemBairro.getNome();
			}

			request.setAttribute("objCar", itemBairro);
			sessao.setAttribute("objCar", itemBairro);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
