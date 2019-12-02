package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;

public class AlteraNivelAcesso implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String mens = "";
		String pgJsp = "/index.jsp";
		RequestDispatcher rd = null;
		HttpSession sessao = request.getSession();
		
		int nivel = 1;

		Login usuSet = (Login)sessao.getAttribute("objUsu");
		
		if(request.getParameter("nivel_socio")!=null&&request.getParameter("nivel_socio")!=""){
			nivel=Integer.parseInt(request.getParameter("nivel_socio"));

			sessao.removeAttribute("objUsu");

			usuSet.setNivel(nivel);
			sessao.setAttribute("objUsu", usuSet);
		}
		
		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
