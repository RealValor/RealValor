package logica;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CopiaDeSeguranca implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "Certifique-se de que existe a unidade C:";
		String pgJsp = "/incluiBackup.jsp";
		String caminho = request.getParameter("backup");
		
		if(caminho!=null&&caminho!=""){
			String comando = "C:/STU/scripts/backup.bat"; 
			
			try { 
				ProcessBuilder pb = new ProcessBuilder(comando);
				pb.start();
			} catch (IOException e) { 
				e.printStackTrace(); 
			}
			pgJsp = "/principal3.jsp";
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
