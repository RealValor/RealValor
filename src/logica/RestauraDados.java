package logica;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestauraDados implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "Certifique-se de que existe o backup na pasta C:\\ backup";
		String pgJsp = "/restauraBackup.jsp";

		String caminho = request.getParameter("restaura");
		
		if(caminho!=null&&caminho!=""){
			String comando = "C:/STU/scripts/restaura.bat"; 
			
			mens = "Dados restaurados com sucesso!";
			
			try { 
				ProcessBuilder pb = new ProcessBuilder(comando);
				pb.start();
				
				pgJsp = "stu?p=Acesso&flg=1";
				
			} catch (IOException e) { 
				e.printStackTrace();
				mens = "ERRO! Dados não restaurados.";
			}
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
