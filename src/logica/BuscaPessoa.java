package logica;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.GrauParentescoDAO;
import DAO.SocioDAO;
import beans.GrauParentesco;
import beans.Login;
import beans.MovAtual;
import beans.Socio;

public class BuscaPessoa implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String nomePaga = "";
		if(request.getParameter("nome_paga")!=null&&request.getParameter("nome_paga")!="")
			nomePaga = request.getParameter("nome_paga");
		
		String situPaga = "";
		if(request.getParameter("situ_paga")!=null&&request.getParameter("situ_paga")!="")
			situPaga = request.getParameter("situ_paga");

		int ano = 0;
		if(request.getParameter("ano")!=null&&request.getParameter("ano")!="")
			ano = Integer.parseInt(request.getParameter("ano"));
		
		HttpSession sessao = request.getSession();

		Calendar data = Calendar.getInstance();

		MovAtual objMovI = new MovAtual();
		objMovI.setAno(data.get(Calendar.YEAR));
		objMovI.setMes(1); //janeiro
		
		MovAtual objMovF = new MovAtual();
		objMovF.setAno(ano);
		objMovF.setMes(data.get(Calendar.MONTH)+1);
		
		sessao.setAttribute("objMovIni", objMovI); //Não influencia porque é via javascript
		sessao.setAttribute("objMovFin", objMovF);
		
		ArrayList<GrauParentesco> listGrauParentesco = GrauParentescoDAO.listarGrauParentesco();
		sessao.setAttribute("listGrauParentesco", listGrauParentesco);
		
		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		if(nomePaga!=null&&nomePaga!=""){	

			Socio itemPag = new Socio();
			itemPag.setNome(nomePaga.trim());
			
			ArrayList<Socio> listPaga = SocioDAO.listarPag(itemPag,situPaga,objOperador.getNucleo());
			
			StringBuffer xml = new StringBuffer(   
			"<?xml version='1.0' encoding='ISO-8859-1'?>"); 
			if (listPaga!=null&&listPaga.size()> 0) {   
				xml.append("<pagadores>");
				for(Socio itPag : listPaga ){
					xml.append("<pagador>");
					
						xml.append("<codigo>");   
							xml.append(itPag.getCodigo());					
						xml.append("</codigo>");
					
						xml.append("<nome>");   
							xml.append(itPag.getNome());   
						xml.append("</nome>");   

						xml.append("<situacao>");   
							xml.append(itPag.getSituacao());
						xml.append("</situacao>");
						
					xml.append("</pagador>");   
				}   
				xml.append("</pagadores>");   
			}
			
			sessao.setAttribute("consultaRecibo", 0);

			response.setContentType("text/xml");   
			response.setHeader("Cache-Control", "no-cache");   
			response.getWriter().write(xml.toString());
		}

	}
}
