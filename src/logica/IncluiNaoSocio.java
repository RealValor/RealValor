package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CargoDAO;
import DAO.NaoSocioDAO;
import beans.Cargo;
import beans.Login;
import beans.NaoSocio;

public class IncluiNaoSocio implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiNaoSocio.jsp";
		String codNSoc  = request.getParameter("cod_nsocio");
		String nomeNSoc = request.getParameter("consulta");
		String retorno = request.getParameter("modulo");
		String controle = request.getParameter("ctrl");

		HttpSession sessao = request.getSession();
		NaoSocio itemNSoc = new NaoSocio();
		
		ArrayList<Cargo> listCar = CargoDAO.listarCargo();
		if(listCar != null){
			request.setAttribute("listCar", listCar);		                
		}
		
		if(controle!=null&&controle.equalsIgnoreCase("limpar")){
			itemNSoc.setCodigo(0);
			sessao.setAttribute("objNSoc", itemNSoc);
		}else if((nomeNSoc!=null&&nomeNSoc!="")||(codNSoc!=null&&codNSoc!="")){
			//Refatorar este código!
			
			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");

			itemNSoc.setNome(nomeNSoc);
			itemNSoc.setRegiao(objOperador.getRegiao());
			
			String teleNSoc = request.getParameter("telefone_nsocio");
			String cpfCnpj  = request.getParameter("cpf_cnpj");
			String emailNSoc = request.getParameter("email_nsocio");

			if(codNSoc!=null&&codNSoc!=""){
				itemNSoc.setCodigo(Integer.parseInt(codNSoc));
			}

			if(teleNSoc!=null&&teleNSoc!=""){	
				itemNSoc.setTelefone(teleNSoc);
			}
			
			if(cpfCnpj!=null&&cpfCnpj!=""){	
				itemNSoc.setCpfCnpj(cpfCnpj);
			}

			if(emailNSoc!=null&&emailNSoc!=""){	
				itemNSoc.setEmail(emailNSoc);
			}

			NaoSocioDAO daoRecF = new NaoSocioDAO();			
			NaoSocio itemRecF = daoRecF.consultarNaoSoc(itemNSoc,objOperador.getRegiao().getCodigo());

			mens = "Dados já cadastrados: "+itemNSoc.getNome();  

			if(itemRecF==null){
				NaoSocioDAO daoIncF = new NaoSocioDAO();
				
				daoIncF.incluirNaoSoc(itemNSoc);
				mens = itemNSoc.getNome()+" Incluído! ";
			}

			//request.setAttribute("objNSoc", itemRecF);
			sessao.setAttribute("objNSoc", itemRecF);

		}else{
			sessao.removeAttribute("modulo");
		}
		
		if(retorno!=null && retorno!=""){
			nomeNSoc = request.getParameter("consulta");
			itemNSoc.setNome(nomeNSoc);
			//request.setAttribute("objNSoc", itemNSoc);
			sessao.setAttribute("objNSoc", itemNSoc);
			sessao.setAttribute("modulo", retorno);
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
