package logica;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import beans.Login;

@MultipartConfig
public class GravaFoto implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;
		String pgJsp = "/stu?p=Acesso";

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

        if (ServletFileUpload.isMultipartContent(request)) {

        	try {
                //Faz o parse do request
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
 
                //Escreve a o arquivo na pasta img
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        item.write(new File(request.getServletContext().getRealPath("imagens")+ File.separator + "Nucleo"+objOperador.getNucleo().getCodigo()+".png"));
                    }
                    //System.out.println(request.getServletContext().getRealPath("imagens")+ File.separator);
                }
                request.setAttribute("message", "Arquivo carregado com sucesso");
            } catch (Exception ex) {
                request.setAttribute("message", "Upload de arquivo falhou devido a "+ ex);
            }
            
        } else {
            request.setAttribute("message","Desculpe este Servlet lida apenas com pedido de upload de arquivos");
        }
        
		//-----------------------------------
		File origem = null;
		origem = new File("//home//udvnmg//appservers//apache-tomcat-7x//webapps//ROOT//imagens//Nucleo"+objOperador.getNucleo().getCodigo()+".png");

		String caminhoImagem = null;
		if(origem.exists()){
			
			File destino = null;
			destino = new File("//home//udvnmg//receive//Nucleo"+objOperador.getNucleo().getCodigo()+".png");
			
			if(!destino.exists()){
				Path source = Paths.get("//home//udvnmg//appservers//apache-tomcat-7x//webapps//ROOT//imagens//Nucleo"+objOperador.getNucleo().getCodigo()+".png");
				Path destination = Paths.get("//home//udvnmg//receive//Nucleo"+objOperador.getNucleo().getCodigo()+".png");
				Files.copy(source, destination);
			}
			
			caminhoImagem = "imagens/Nucleo"+objOperador.getNucleo().getCodigo()+".png";
		}
		sessao.setAttribute("imagem", caminhoImagem);
		//-----------------------------------


		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
		//mens...
	}
}
