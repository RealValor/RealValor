package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logica.LogicaDeNegocio;


public class StuServlet extends HttpServlet {

	private static final long serialVersionUID = 1090032L; 
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String param = request.getParameter("p").trim();
		//param = param.replace("?", "&"); //Incluido para permitir recebimento de id do recebimento online pagSeguro
		String nomeClasseLogica = "logica."+param;
		
		if(request.getSession().getAttribute("objUsu")==null){ 			
			nomeClasseLogica = "logica.Acesso";
		}		

		try {
			Class<?> classeLogica = Class.forName(nomeClasseLogica);			
			if (!LogicaDeNegocio.class.isAssignableFrom(classeLogica)) { 
				throw new ServletException("Classe "+nomeClasseLogica+" não implementa a interface! ");
			}
			LogicaDeNegocio objLogicaNegocio = (LogicaDeNegocio) classeLogica.newInstance();
			objLogicaNegocio.execute(request, response);
		} catch (Exception e) {
			throw new ServletException("Erro ao instanciar a classe "+nomeClasseLogica);
		}
	}
}
//http://java.sun.com/j2se/1.5.0/docs/guide/language