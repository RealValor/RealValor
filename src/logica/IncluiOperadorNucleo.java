package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.LoginDAO;
import DAO.SocioDAO;
import beans.Cargo;
import beans.Login;
import beans.Socio;

public class IncluiOperadorNucleo implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		
		String mens = "";
		String pgJsp = "/incluiTesoureiroNucleo.jsp";

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");
		Login itemOper = new Login();
		
		String nomeOper = request.getParameter("nome_operador");
		String cpfOper = request.getParameter("cpf_operador");

		if((nomeOper!=null&&nomeOper!="")||(cpfOper!=null&&cpfOper!="")){

			itemOper.setNucleo(objOperador.getNucleo());
			itemOper.setSenha("e10adc3949ba59abbe56e057f20f883e");
			itemOper.setNivel(3);

			if(cpfOper!=null&&cpfOper!=""){	
				itemOper.setCpf(Long.parseLong(cpfOper));
			}

			if(nomeOper!=null&&nomeOper!=""){
				itemOper.setNome(nomeOper);
			}
			
			Login itemRecF = new Login();
			LoginDAO daoRecF = new LoginDAO();

			if(itemOper.getCpf()!=0){
				itemRecF = daoRecF.consultarLogin(itemOper);				
			}
			mens = "Operador  já cadastrado: "+itemOper.getNome();

			if(itemRecF==null){

				itemOper.setNucleo(objOperador.getNucleo());
				
				Socio itemSoc = new Socio();
				Cargo itemCargo = new Cargo();
				itemCargo.setCodigo(0);
				
				itemSoc.setCpf(itemOper.getCpf());
				itemSoc.setNome(itemOper.getNome());
				itemSoc.setSenha(itemOper.getSenha());
				itemSoc.setCargo(itemCargo);
				itemSoc.setSituacao("A");
				itemSoc.setGrau("S");
				itemSoc.setSexo("M");

				SocioDAO objSocioDAO = new SocioDAO();

				if(objSocioDAO.consultarSocioCPF(itemSoc)==null){
					SocioDAO daoIncF = new SocioDAO();
					daoIncF.incluirSocio(itemSoc);
					
					objSocioDAO.incluirSocioNucleo(objSocioDAO.consultarSocioCPF(itemSoc).getCodigo(), objOperador.getNucleo().getCodigo());
				}
				
				itemOper.setUsuario(objSocioDAO.consultarSocioCPF(itemSoc).getCodigo());
				LoginDAO objLoginDAO = new LoginDAO();
				
				if(objLoginDAO.consultarUsuNome(itemOper)==null){
					objLoginDAO.incluirLogin(itemOper);
				}

				mens = "Operador Incluído! ";
			}
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
