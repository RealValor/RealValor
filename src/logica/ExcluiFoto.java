package logica;

import java.io.File;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;

@MultipartConfig
public class ExcluiFoto implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		String pgJsp = "/stu?p=Acesso";

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		File origem = null;
		origem = new File("//home//udvnmg//appservers//apache-tomcat-7x//webapps//ROOT//imagens//Nucleo"+objOperador.getNucleo().getCodigo()+".png");

		String caminhoImagem = null;
		if(origem.exists()){
			
			origem.delete();
			
			File destino = null;
			destino = new File("//home//udvnmg//receive//Nucleo"+objOperador.getNucleo().getCodigo()+".png");

			if(destino.exists()){
				destino.delete();
			}
			
			caminhoImagem = null;
		}
		sessao.setAttribute("imagem", caminhoImagem);

		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
		//mens...
	}
}
