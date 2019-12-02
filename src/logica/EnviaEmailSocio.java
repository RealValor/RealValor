package logica;

import java.io.File;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import DAO.SocioDAO;
import beans.Login;
import beans.Socio;

public class EnviaEmailSocio implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = ""; 
		String email = ""; 
		int controle = 0;
		int codigosocio = 0;

		HttpSession sessao = request.getSession();
		
		if(request.getParameter("ctrl")!=null&&request.getParameter("ctrl")!="")
			controle = Integer.parseInt(request.getParameter("ctrl"));

		if(request.getParameter("cd_socio")!=null&&request.getParameter("cd_socio")!="")
			codigosocio = Integer.parseInt(request.getParameter("cd_socio"));

		if(request.getParameter("url")!=null&&request.getParameter("url")!="")
			pgJsp = request.getParameter("url");
		
		if(request.getParameter("email")!=null&&request.getParameter("email")!="")
			email = request.getParameter("email");
		
		Socio objSocio = new Socio();
		objSocio.setCodigo(codigosocio);
		
		SocioDAO objSocioDAO = new SocioDAO();
		objSocio = objSocioDAO.consultarNomeSocio(objSocio);
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		switch(controle) {
		case 1:

			pgJsp = "/incluiRecebimento.jsp";

	        if (ServletFileUpload.isMultipartContent(request)) {

	        	try {
	                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	 
	                for (FileItem item : multiparts) {
	                	
	                    if (!item.isFormField()) {

	                    	String destinoTemporarioRecibo = request.getServletContext().getRealPath("imagens")+ File.separator + "Recibo"+objOperador.getNucleo().getCodigo()+".png";
	                    	
	                    	item.write(new File(destinoTemporarioRecibo));
	                        GeraEmail.enviarEmailRecibo(objSocio, objOperador, email, destinoTemporarioRecibo);

	                    }
	                }
	                mens = "Arquivo carregado com sucesso";
	            } catch (Exception ex) {
	                request.setAttribute("message", "Upload de arquivo falhou devido a "+ ex);
	                mens = "Envio de arquivo falhou devido a "+ ex;
	            }
	            
	        }
			break;

		case 2:

			int totMeses = 1;
			sessao.setAttribute("totMeses", totMeses);
			GeraEmail.enviarNotificacaoMensalidades(objSocio, objOperador); 
			break;

		case 3:

			totMeses = 1;
			sessao.setAttribute("totMeses", totMeses);
			GeraEmail.enviarNotificacaoMensalidades(null, objOperador); 
			break;

		default: 
			break;
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
