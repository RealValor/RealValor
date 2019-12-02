package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.BairroDAO;
import beans.Bairro;

public class ExcluiBairro implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiBairro.jsp";
		String desBairro = request.getParameter("desc_bairro");
		
		HttpSession sessao = request.getSession();
		Bairro itemCar = new Bairro();

		if(desBairro!=null&&desBairro!=""){	
			itemCar.setNome(desBairro);
				
			String codBairro = request.getParameter("cod_bairro");
		
			if(codBairro!=null&&codBairro!=""){
				itemCar.setCodigo(Integer.parseInt(codBairro));
			}
			
			Bairro itemRecCar = BairroDAO.consultarBairro(itemCar);
			mens = "Cadastro não encontrado: "+itemCar.getNome();  

			if(itemRecCar!=null){

				itemRecCar = BairroDAO.consultarBairro(itemRecCar);
				
				if(itemRecCar!=null){
					mens = "O Bairro "+itemCar.getNome()+" possui histórico. Isto bloqueia a exclusão!";
				}else{
					//BairroDAO daoAltF = new BairroDAO(); 
					//daoAltF.excluirBairro(itemCar);
					mens = "Cadastro excluído: "+itemCar.getNome();
					itemCar=null;
				}
			}

			request.setAttribute("objCar", itemCar);
			sessao.setAttribute("objCar", itemCar);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
