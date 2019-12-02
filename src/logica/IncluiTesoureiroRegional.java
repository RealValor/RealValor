package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.LoginDAO;
import DAO.NucleoDAO;
import DAO.RegiaoDAO;
import DAO.TesoureiroRegionalDAO;
import beans.Login;
import beans.Nucleo;
import beans.Regiao;

public class IncluiTesoureiroRegional implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//Reescrever esta classe!
		
		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiTesoureiroRegional.jsp";

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		Nucleo itemNucleo = new Nucleo();
		String nomeNucleo = request.getParameter("desc_nucleo");
		//String controle = request.getParameter("ctrl");
		String regiao	= request.getParameter("regiao");

        //sessao.removeAttribute("listaRegioes");
        sessao.removeAttribute("listaTesoureiros");
        //Nucleo objNucleo = new Nucleo();

		if(nomeNucleo!=null&&nomeNucleo!=""){	
			itemNucleo.setNome(nomeNucleo);

			String cnpjNucleo = request.getParameter("cnpj_nucleo");
			String nomeOperador = request.getParameter("nome_operador");
			String cpfOperador = request.getParameter("cpf_operador");
			
			if(cnpjNucleo!=null&&cnpjNucleo!=""){
				itemNucleo.setCnpj(cnpjNucleo);
			}
			
			if(regiao!=null&&regiao!=""){
				RegiaoDAO objRegiaoDAO = new RegiaoDAO();
				itemNucleo.setRegiao(objRegiaoDAO.consultarRegiao(Integer.parseInt(regiao)));
			}else{
				itemNucleo.setRegiao(objOperador.getRegiao());
			}

			Login objLogin = new Login();

			if(nomeOperador!=null&&nomeOperador!=""){
				objLogin.setNome(nomeOperador);
			}

			if(cpfOperador!=null&&cpfOperador!=""){	
				objLogin.setCpf(Long.parseLong(cpfOperador));
			}
			objLogin.setNivel(objOperador.getNivel());

			NucleoDAO objNucleoDAO = new NucleoDAO();
			Nucleo itemRecNucleo = objNucleoDAO.consultarNomeNucleo(itemNucleo);
			mens = "Nucleo j� cadastrado: "+itemNucleo.getNome(); 
			
			LoginDAO objLoginDAO = new LoginDAO();
			NucleoDAO daoIncNucleo = new NucleoDAO();
			
			if(objLoginDAO.consultarCPFOperador(objLogin)==null){
				if(itemRecNucleo==null){
					daoIncNucleo.incluirNucleoOperador(itemNucleo,objLogin);
					mens = "Tesoureiro Regional Inclu�do! ";
				}
			}else{
				Nucleo objNucleo = new Nucleo();
				objNucleo = objNucleoDAO.consultarNucleo(objLogin.getNucleo().getCodigo());
				mens = "Operador com CPf "+objLogin.getCpfStr()+" j� cadastrado para o N�cleo "+objNucleo.getNome()+". Alterado apenas o n�vel";
			}

			objLogin=objLoginDAO.consultarCPFOperador(objLogin);
			objLogin.setNivel(4);

			objLoginDAO.alterarLogin(objLogin);
			
			sessao.setAttribute("objNucleo", itemRecNucleo);
		}

		ArrayList<Login> listaTesoureiros = null;
        if(objOperador.getNivel()>4){	
			pgJsp = "/incluiTesoureiroRegional.jsp";
			//Tratamento para tesoureiro geral
			//----------------------------------------------------------------------------

			ArrayList<Regiao> listaRegioes = null;
			listaRegioes = RegiaoDAO.listarRegiao();
			if(listaRegioes != null){
				sessao.setAttribute("listaRegioes", listaRegioes);
			}
			//Refatorar este trecho de c�digo
			//----------------------------------------------------------------------------

			listaTesoureiros = TesoureiroRegionalDAO.consultarTesoureiroRegional(null);

        }

		if(listaTesoureiros != null){
			sessao.setAttribute("listaTesoureiros", listaTesoureiros);
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
