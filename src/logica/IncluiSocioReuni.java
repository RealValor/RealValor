package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.SocioReuniDAO;
import beans.Login;
import beans.Socio;
import beans.SocioReuni;

public class IncluiSocioReuni implements LogicaDeNegocio{
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		RequestDispatcher rd = null;
		
		String mens="";
		String pgJsp = "/listaSocioReuni.jsp";

		int id = Integer.parseInt(request.getParameter("id_socio"));
		String cpfSoc  = request.getParameter("cpf_socio");
		
		int codigoSocio = 0;
		
		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");
		
		SocioReuni itemSocioReuni = new SocioReuni();
		itemSocioReuni.setCpf(cpfSoc); //alimenta apenas com cpf e id 
		itemSocioReuni.setId(id); 
		
		//System.out.println("aqui 1");
		
		SocioReuni objSocioReuni = new SocioReuni();
		objSocioReuni=SocioReuniDAO.consultarSocioReuni(itemSocioReuni);
		
		Socio objSocio = new Socio();		
		objSocio = PopulaSocio.alimentaDadosSocio(codigoSocio, cpfSoc, objSocioReuni);

		ArrayList<Socio> listaSocioAuxiliar = null;
		
		listaSocioAuxiliar = BuscaNomeSemelhante.buscaNomeSemelhante(objSocio, objSocioReuni, objOperador);

		//System.out.println("aqui 2");
		
		sessao.setAttribute("listaSocioAuxiliar", listaSocioAuxiliar);
		
		sessao.setAttribute("objSocio", objSocio);
		sessao.setAttribute("socioReuni", itemSocioReuni);

		ArrayList<SocioReuni> listaSocioReuni = SocioReuniDAO.listarAuxiliarSocioReuni(objOperador.getNucleo());

		if(listaSocioReuni!=null){
			pgJsp = "/listaSocioReuni.jsp";
			sessao.setAttribute("gravaTodos", (listaSocioAuxiliar!=null?listaSocioAuxiliar.size():0));
			sessao.setAttribute("listaSocioReuni", listaSocioReuni);
			sessao.setAttribute("totalSocios", listaSocioReuni.size());
		}
		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}

}
