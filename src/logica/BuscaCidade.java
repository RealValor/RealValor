package logica;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.CidadeDAO;
import beans.Cidade;
import beans.Login;

public class BuscaCidade implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String nomeCidade = request.getParameter("nome_cidade");
		
		Login itemOper = new Login();
		
		//System.out.println("nomeCidade: "+nomeCidade);
		
		if(nomeCidade!=null&&nomeCidade!=""){	
			itemOper.setNome(nomeCidade);

			Cidade itemSoc = new Cidade();
			itemSoc.setNome(nomeCidade);
			
			/*
			HttpSession sessao = request.getSession();
			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");
			*/

			ArrayList<Cidade> listCidade = CidadeDAO.listarCidade(nomeCidade);
			
			StringBuffer xml = new StringBuffer(   
			"<?xml version='1.0' encoding='ISO-8859-1'?>");   
			if (listCidade!=null&&listCidade.size()> 0) {   
				xml.append("<cidades>");
				for(Cidade itemCidade : listCidade ){
					xml.append("<cidade>");
					
						xml.append("<codigo>");   
							xml.append(itemCidade.getCodigo());					
						xml.append("</codigo>");
					
						xml.append("<nome>");   
							xml.append(itemCidade.getNome());   
						xml.append("</nome>");   

						xml.append("<uf>");   
							xml.append(itemCidade.getUF());
						xml.append("</uf>");   

					xml.append("</cidade>");   
				}   
				xml.append("</cidades>");   
			}   
			response.setContentType("text/xml");   
			response.setHeader("Cache-Control", "no-cache");   
			response.getWriter().write(xml.toString()); 
		}

	}
}
