package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.Socio;


import DAO.SocioDAO;

public class ConsultaHistoricoSocio implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "Para todos os associados, confirmar sem seleção do nome";

		String pgJsp = "/consultaHistoricoSocio.jsp";
		int controle = 0;
		
		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		int codSocio = 0;
		if(request.getParameter("cod_socio")!=null&&request.getParameter("cod_socio")!="")
			codSocio=Integer.parseInt(request.getParameter("cod_socio"));
		
		if(codSocio!=0){ //-1
			controle++;
			Socio itemSoc = new Socio();
			
			codSocio=codSocio<0?codSocio=0:codSocio;

			itemSoc.setCodigo(codSocio);
			
			SocioDAO objSocioDAO = new SocioDAO();
			itemSoc = objSocioDAO.consultarSocio(itemSoc, objOperador.getNucleo());

			ArrayList<Socio> listHistoricoSocio = SocioDAO.listarHistoricoSocio(itemSoc);
			
			if(listHistoricoSocio == null){
				controle = 0;
				mens = "Histórico não encontrado para "+itemSoc.getNome();
			}

			sessao.setAttribute("listHistoricoSocio", listHistoricoSocio);
		}

		sessao.setAttribute("controle", controle);
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
