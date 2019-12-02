package logica;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;


import DAO.LoginDAO;

public class BuscaSenha implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession sessao = request.getSession();
		
		Login usuSet = (Login)sessao.getAttribute("objUsu");
		
		//System.out.println("cpf usuSet: "+usuSet.getCpf()+" senha: "+request.getParameter("senha"));
		
		Login usuario = new Login();
		
		if(usuSet!=null){
			
			if(request.getParameter("senha")!=null&&request.getParameter("senha")!=""){
				usuario.setCpf(usuSet.getCpf());
				usuario.setSenha(Encripta.encriptaDados(request.getParameter("senha").trim()));
				usuario.setNivel(1);
			}
		}


		LoginDAO daoRecOp = new LoginDAO();
		usuario = daoRecOp.consultarLogin(usuario);

		int resultado=0;
		if(usuario!=null){
			resultado=1;
		}
			
		StringBuffer xml = new StringBuffer(   
		"<?xml version='1.0' encoding='ISO-8859-1'?>");   
			
		xml.append("<operador>");
			xml.append("<identificacao>");
				xml.append("<senha>");   
					xml.append(resultado);
				xml.append("</senha>");   
			xml.append("</identificacao>");
		xml.append("</operador>");				

		response.setContentType("text/xml");   
		response.setHeader("Cache-Control", "no-cache");   
		response.getWriter().write(xml.toString());
	}
}
