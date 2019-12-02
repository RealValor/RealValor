package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.Socio;


import DAO.LoginDAO;
import DAO.SocioDAO;

public class AlteraOperador implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiOperador.jsp";
		String nomeOper = request.getParameter("consulta");
		String cpfOper = request.getParameter("cpf_oper");
		
		HttpSession sessao = request.getSession();
		Login itemOper = new Login();

		if((nomeOper!=null&&nomeOper!="")||(cpfOper!=null&&cpfOper!="")){
			
			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");

			itemOper.setNome(nomeOper);

			String codOp = request.getParameter("cod_op");
			String codOper = request.getParameter("cod_oper");
			String senhaOper = request.getParameter("senha_oper");
			String nivelOper = request.getParameter("nivel_oper");

			if(codOper!=null&&codOper!=""){
				itemOper.setUsuario(Integer.parseInt(codOper));
			}
			
			if(codOp!=null&&codOp!=""){
				itemOper.setUsuario(Integer.parseInt(codOp));
			}			

			if(senhaOper!=null&&senhaOper!="0"){	
				itemOper.setSenha(Encripta.encriptaDados(request.getParameter("senha_oper").trim()));
			}

			if(nivelOper!=null&&nivelOper!=""){
				int nivel = Integer.parseInt(nivelOper);
				itemOper.setNivel(nivel>objOperador.getNivel()?objOperador.getNivel():nivel);
			}

			if(cpfOper!=null&&cpfOper!="0"){	
				itemOper.setCpf(Long.parseLong(cpfOper));
			}

			itemOper.setNucleo(objOperador.getNucleo());

			Login itemRecF = new Login();
			itemRecF.setUsuario(itemOper.getUsuario());
			itemRecF.setCpf(itemOper.getCpf());
			itemRecF.setNucleo(itemOper.getNucleo());
			
			LoginDAO daoRecOp = new LoginDAO();
			itemRecF = daoRecOp.consultarUsuNome(itemRecF);

			mens = "Cadastro não encontrado: "+itemRecF.getNome();  

			if(itemRecF!=null){
				
				itemOper.setNucleo(objOperador.getNucleo());
				
				Socio itemSoc = new Socio();
				itemSoc.setCodigo(itemOper.getUsuario());

				SocioDAO daoConSocio = new SocioDAO();
				itemSoc = daoConSocio.consultarSocio(itemSoc, objOperador.getNucleo());

				itemSoc.setCpf(itemOper.getCpf());
				
				LoginDAO daoAltOp = new LoginDAO();
				daoAltOp.alterarLogin(itemOper);
				
				SocioDAO daoAltF = new SocioDAO(); 
				daoAltF.alterarSocio(itemSoc);
				
				LoginDAO daoConOp = new LoginDAO();
				itemRecF = daoConOp.consultarUsuNome(itemOper);

				mens = "Item atualizado: "+itemRecF.getNome();
			}

			request.setAttribute("objOper", itemRecF);
			sessao.setAttribute("objOper", itemRecF);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
