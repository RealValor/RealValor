package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.MovAtual;
import beans.Socio;


import DAO.SocioDAO;

public class ConsultaAniversariante implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/consultaAniversariante.jsp";
		String mesAni = request.getParameter("mes_ani");
		
		HttpSession sessao = request.getSession();
		Socio itemSoc = new Socio();
		
		MovAtual objMovIni = new MovAtual();

		Calendar cal = Calendar.getInstance();  
		
		objMovIni.setAno(cal.get(Calendar.YEAR));
		objMovIni.setMes(cal.get(Calendar.MONTH)+1);

		request.setAttribute("mesAniversario", 0);
		
		if(mesAni!=null&&mesAni!=""){
			Calendar dataNasc = Calendar.getInstance();
			dataNasc.set(Calendar.YEAR, Integer.parseInt(mesAni), 1) ;

			objMovIni.setMes(Integer.parseInt(mesAni));
			
			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");

			int pFiltro = 1;
			String pTipo = "ativo";
			itemSoc.setDataNasc(dataNasc.getTime());
			
			String todos = request.getParameter("todos");
			
			if(todos!=null&&todos!=""){
				itemSoc.setDataNasc(null);
			}
			ArrayList<Socio> listSocio = SocioDAO.listarSocio(objOperador.getNucleo().getCodigo(), itemSoc, pTipo, pFiltro);
			
			if(listSocio == null){
				mens = "Cadastro não encontrado: "+itemSoc.getNome();  
			}
			
			sessao.setAttribute("listSocio", listSocio);
			request.setAttribute("mesAniversario", Integer.parseInt(mesAni));
		}
		sessao.setAttribute("objMovIni", objMovIni);

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
