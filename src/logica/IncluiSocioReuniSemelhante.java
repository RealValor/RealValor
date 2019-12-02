package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.LoginDAO;
import DAO.SocioDAO;
import DAO.SocioReuniDAO;
import beans.Login;
import beans.Socio;
import beans.SocioReuni;

public class IncluiSocioReuniSemelhante implements LogicaDeNegocio{
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		RequestDispatcher rd = null;
		
		String mens="";
		String pgJsp = "/listaSocioReuni.jsp";

		int cd_socio = Integer.parseInt(request.getParameter("cd_socio"));
		String cpfSoc  = request.getParameter("cpf_socio");
		int controle = Integer.parseInt(request.getParameter("ctrl"));
		//controle = 1 atualiza, = 2 grava novo

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		SocioReuni itemSocioReuni = new SocioReuni();
		itemSocioReuni = (SocioReuni) sessao.getAttribute("socioReuni");
		
		SocioReuni objSocioReuni = new SocioReuni();
		objSocioReuni = SocioReuniDAO.consultarSocioReuni(itemSocioReuni);
		
		Socio objSocio = new Socio();
		objSocio = PopulaSocio.alimentaDadosSocio(cd_socio, cpfSoc, objSocioReuni);
		
		if(controle==1){
			
			SocioDAO.atualizarDadosPrincipais(objSocio);
			
		}else{

			SocioDAO objSocioDAO = new SocioDAO();
			objSocioDAO.incluirSocio(objSocio);
			
			objSocio = objSocioDAO.consultarCodigoSocio(objSocio);
			
			Login objSocioOperador = new Login();
			
			objSocioOperador.setUsuario(objSocioDAO.consultarSocioCPF(objSocio).getCodigo());
			objSocioOperador.setCpf(objSocio.getCpf());
			objSocioOperador.setNucleo(objOperador.getNucleo());
			
			LoginDAO objLoginDAO = new LoginDAO();
			
			if(objLoginDAO.consultarUsuNome(objSocioOperador)==null){
				
				//objSocioOperador.setUsuario(objSocioOperador.getUsuario());
				objSocioOperador.setSenha(Encripta.encriptaDados("123456"));
				objSocioOperador.setNivel(1);
				
				objLoginDAO.incluirLogin(objSocioOperador);
			}

			objSocioDAO.incluirSocioNucleo(objSocio.getCodigo(),objOperador.getNucleo().getCodigo());
		}
		
		SocioReuniDAO.excluirSocioReuni(itemSocioReuni, objOperador.getNucleo());
		
		sessao.setAttribute("objSocio", objSocio);

		ArrayList<SocioReuni> listaSocioReuni = SocioReuniDAO.listarAuxiliarSocioReuni(objOperador.getNucleo());

		if(listaSocioReuni!=null){
			pgJsp = "/listaSocioReuni.jsp";
			sessao.setAttribute("gravaTodos", -1);
			sessao.setAttribute("listaSocioReuni", listaSocioReuni);
			sessao.setAttribute("totalSocios", listaSocioReuni.size());
		}
		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}

}
