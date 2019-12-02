package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.NaoSocioDAO;
import beans.Login;
import beans.NaoSocio;
import beans.Nucleo;

public class ConsultaNaoSocio implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiNaoSocio.jsp";
		String codNSoc  = request.getParameter("cod_nsocio");
		String nomeNSoc = request.getParameter("consulta");
		
		HttpSession sessao = request.getSession();
		NaoSocio itemNSoc = new NaoSocio();

		if((nomeNSoc!=null&&nomeNSoc!="")||(codNSoc!=null&&codNSoc!="")){	

			if((codNSoc!=null&&codNSoc!=""))
				itemNSoc.setCodigo(Integer.parseInt(codNSoc));
			
			if((nomeNSoc!=null&&nomeNSoc!=""))
				itemNSoc.setNome(nomeNSoc);

			String teleNSoc = request.getParameter("telefone_nsocio");
			String cpfCnpj  = request.getParameter("cpf_cnpj");

			if(teleNSoc!=null&&teleNSoc!=""){	
				itemNSoc.setTelefone(teleNSoc);
			}
			
			if(cpfCnpj!=null&&cpfCnpj!=""){	
				itemNSoc.setCpfCnpj(cpfCnpj);
			}

			NaoSocioDAO objNaoSocioDAO = new NaoSocioDAO();

			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");

			itemNSoc = objNaoSocioDAO.consultarNaoSoc(itemNSoc,objOperador.getRegiao().getCodigo());

			Nucleo objNucleo = new Nucleo();
			objNucleo = (Nucleo)sessao.getAttribute("objNucleo");

			NaoSocioDAO daoRecF = new NaoSocioDAO();
			NaoSocio itemRecF = new NaoSocio();

			itemNSoc.setRegiao(objNucleo.getRegiao());
			itemRecF = daoRecF.consultarNaoSoc(itemNSoc,objOperador.getRegiao().getCodigo());

			mens = "Cadastro não encontrado: "+itemNSoc.getNome();  

			if(itemRecF!=null){
				itemNSoc = itemRecF;
				mens = "Cadastro localizado: "+itemRecF.getNome();
			}

			sessao.setAttribute("objNSoc", itemRecF);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
