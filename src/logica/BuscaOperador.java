package logica;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.Socio;


import DAO.SocioDAO;

public class BuscaOperador implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String nomeOper = request.getParameter("nome_oper");
		Login itemOper = new Login();
		
		if(nomeOper!=null&&nomeOper!=""){	
			itemOper.setNome(nomeOper);

			Socio itemSoc = new Socio();
			itemSoc.setNome(nomeOper);
			//String pTipo = "ativo";
			String pTipo = "";
			
			HttpSession sessao = request.getSession();
			
			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");

			ArrayList<Socio> listOper = SocioDAO.listarSocio(objOperador.getNucleo().getCodigo(), itemSoc, pTipo,0);
			
			StringBuffer xml = new StringBuffer(   
			"<?xml version='1.0' encoding='ISO-8859-1'?>");   
			if (listOper!=null&&listOper.size()> 0) {   
				xml.append("<operadores>");
				for(Socio itSoc : listOper ){
					xml.append("<operador>");   
						xml.append("<codigo>");   
							xml.append(itSoc.getCodigo());					
						xml.append("</codigo>");
					
						xml.append("<nome>");   
							xml.append(itSoc.getNome());   
						xml.append("</nome>");   

						xml.append("<cpf>");   
							xml.append(itSoc.getCpfStr());
						xml.append("</cpf>");   

					xml.append("</operador>");   
				}   
				xml.append("</operadores>");   
			}   
			response.setContentType("text/xml");   
			response.setHeader("Cache-Control", "no-cache");   
			response.getWriter().write(xml.toString()); 
		}

	}
}
