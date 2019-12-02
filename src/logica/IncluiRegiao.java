package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.RegiaoDAO;
import beans.Login;
import beans.Regiao;

public class IncluiRegiao implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiRegiao.jsp";

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		Regiao itemRegiao = new Regiao();
		String nomeRegiao = request.getParameter("desc_regiao");
		
		/*
		String controle = request.getParameter("controle");
		*/
		if(objOperador.getNivel()>4){	
			//pgJsp = "/incluiNucleoOperador.jsp";
			pgJsp = "/listaRegiao.jsp";
			//Tratamento para tesoureiro geral
		}
		/*
		if(controle=="Incluir"){			
			pgJsp = "/incluiRegiao.jsp";
		}
		*/
        sessao.removeAttribute("listaRegioes");

		if(nomeRegiao!=null&&nomeRegiao!=""){	
			itemRegiao.setDescricao(nomeRegiao);

			//String cnpjNucleo = request.getParameter("cnpj_nucleo");
			String nomeOperador = request.getParameter("nome_operador");
			String cpfOperador = request.getParameter("cpf_operador");
			
			
			Login objLogin = new Login();

			if(nomeOperador!=null&&nomeOperador!=""){
				objLogin.setNome(nomeOperador);
			}

			if(cpfOperador!=null&&cpfOperador!=""){	
				//pgJsp = "/incluiNucleoOperador.jsp";
				objLogin.setCpf(Long.parseLong(cpfOperador));
			}

			//NucleoDAO objNucleoDAO = new NucleoDAO();
			//Nucleo itemRecNucleo = objNucleoDAO.consultarNomeNucleo(itemNucleo);
			mens = "Regiao já cadastrada: "+itemRegiao.getDescricao(); 
			/*
			if(itemRecNucleo==null){
				NucleoDAO daoIncNucleo = new NucleoDAO();
				daoIncNucleo.incluirNucleoOperador(itemNucleo,objLogin);
				//mens = itemNucleo.getNome()+" Incluído! ";
			}

			request.setAttribute("objNucleo", itemRecNucleo);
			sessao.setAttribute("objNucleo", itemRecNucleo);
			*/
		}
		
		ArrayList<Regiao> listaRegioes = null;
		//----------------------------------------------------------------------------
		if(objOperador.getNivel()>4){
			//listaNucleos = NucleoDAO.listarNucleo(0);
			listaRegioes = RegiaoDAO.listarRegiao();
		}else{
			//listaNucleos = NucleoDAO.listarNucleo(objOperador.getRegiao().getCodigo());
		}
		if(listaRegioes != null){
			sessao.setAttribute("listaRegioes", listaRegioes);
		}
		//Refatorar este trecho de código
		//----------------------------------------------------------------------------

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
