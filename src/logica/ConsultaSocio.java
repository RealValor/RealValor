package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Cargo;
import beans.Dependente;
import beans.Login;
import beans.Socio;


import DAO.CargoDAO;
import DAO.DependenteDAO;
import DAO.SocioDAO;

public class ConsultaSocio implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiSocio.jsp";
		
		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		int controle = 0;
		String nomeSoc = request.getParameter("consulta");
		String codSoc  = request.getParameter("cod_socio");
		
		if(request.getParameter("ctrl")!=""&&request.getParameter("ctrl")!=null){
			controle = Integer.parseInt(request.getParameter("ctrl"));
		}
		
		if(controle>0){
			if(objOperador.getNivel()<2){
				pgJsp = "/principal1.jsp";
			}else{
				pgJsp = "/principal3.jsp";
			}
		}

		Socio itemSoc = new Socio();

		ArrayList<Cargo> listCar = CargoDAO.listarCargo();
		if(listCar != null){
			request.setAttribute("listCar", listCar);		                
		}

		if((nomeSoc!=null&&nomeSoc!="")||(codSoc!=null&&codSoc!="")){	
			itemSoc.setNome(request.getParameter("nome_socio"));

			String teleSoc = request.getParameter("telefone_socio");
			String cpfSoc  = request.getParameter("cpf_socio");
			
			if(codSoc!=null&&codSoc!=""){
				itemSoc.setCodigo(Integer.parseInt(request.getParameter("cod_socio")));
			}
			
			if(teleSoc!=null&&teleSoc!=""){	
				itemSoc.setTelefone(request.getParameter("telefone_socio"));
			}
			
			if(cpfSoc!=null&&cpfSoc!=""){	
				itemSoc.setCpf(Long.parseLong(request.getParameter("cpf_socio")));
			}
			
			SocioDAO daoRecF = new SocioDAO();			
			Socio itemRecF = daoRecF.consultarSocio(itemSoc, objOperador.getNucleo());
			
			//System.out.println(itemRecF.);
			
			mens = "Cadastro não encontrado: "+itemSoc.getNome();  

			if(itemRecF!=null){
				itemSoc = itemRecF;
				mens = "Socio localizado: "+itemRecF.getNome();  
				
				ArrayList<Dependente> listDependente = DependenteDAO.listarDependente(itemRecF, objOperador.getNucleo());
				
				request.setAttribute("listDependente", listDependente);		                
				sessao.setAttribute("listDependente", listDependente);
				
			}

			String caminhoAvatar = SocioDAO.buscaAvatar(itemSoc.getCodigo());
			sessao.setAttribute("imagemAvatar", caminhoAvatar);

			request.setAttribute("objSoc", itemRecF);
			sessao.setAttribute("objSoc", itemRecF);
		}else{
			sessao.removeAttribute("listDependente");
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
