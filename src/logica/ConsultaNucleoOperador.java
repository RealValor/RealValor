package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.LoginDAO;
import DAO.NucleoDAO;
import DAO.RegiaoDAO;
import beans.Login;
import beans.Nucleo;
import beans.Regiao;

public class ConsultaNucleoOperador implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiNucleoRegiao.jsp";

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		Nucleo itemNucleo = new Nucleo();
		String nomeNucleo = request.getParameter("desc_nucleo");
		String controle = request.getParameter("ctrl");
		String regiao	= request.getParameter("regiao");
		
        sessao.removeAttribute("listaRegioes");
        sessao.removeAttribute("listaNucleos");
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
				//pgJsp = "/incluiNucleoOperador.jsp";
				objLogin.setCpf(Long.parseLong(cpfOperador));
			}
			objLogin.setNivel(objOperador.getNivel());


			NucleoDAO objNucleoDAO = new NucleoDAO();
			Nucleo itemRecNucleo = objNucleoDAO.consultarNomeNucleo(itemNucleo);
			mens = "Nucleo já cadastrado: "+itemNucleo.getNome(); 
			

			LoginDAO objLoginDAO = new LoginDAO();

			if(objLoginDAO.consultarCPFOperador(objLogin)!=null){
				Nucleo objNucleo = new Nucleo();
				objNucleo = objNucleoDAO.consultarNucleo(objLogin.getNucleo().getCodigo());
				mens = "Operador com CPf "+objLogin.getCpfStr()+" já cadastrado para o Núcleo "+objNucleo.getNome()+". Inclusão não efetuada"; 
			}else{
				if(itemRecNucleo==null){
					NucleoDAO daoIncNucleo = new NucleoDAO();
					daoIncNucleo.incluirNucleoOperador(itemNucleo,objLogin);
					mens = itemNucleo.getNome()+" Incluído! ";
				}
			}

			request.setAttribute("objNucleo", itemRecNucleo);
			sessao.setAttribute("objNucleo", itemRecNucleo);
			
		}
		
		ArrayList<Nucleo> listaNucleos = null;
        if(objOperador.getNivel()>2){	
			pgJsp = "/incluiNucleoRegiao.jsp";
			//Tratamento para tesoureiro geral
			//----------------------------------------------------------------------------
			ArrayList<Regiao> listaRegioes = null;
			listaRegioes = RegiaoDAO.listarRegiao();
			if(listaRegioes != null){
				sessao.setAttribute("listaRegioes", listaRegioes);
			}
			//Refatorar este trecho de código
			//----------------------------------------------------------------------------

			listaNucleos = NucleoDAO.listarNucleo(0);
		}

        if(request.getParameter("ctrl")!=null&&Integer.parseInt(controle)>0){			
        	pgJsp = "/consultaNucleoRegiao.jsp";
        }
        /*
        if(objOperador.getNivel()==4){
			listaNucleos = NucleoDAO.listarNucleo(objOperador.getRegiao().getCodigo());
		}
        */
		if(listaNucleos != null){
			sessao.setAttribute("listaNucleos", listaNucleos);
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
