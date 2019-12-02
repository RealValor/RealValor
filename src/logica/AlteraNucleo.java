package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.BairroDAO;
import DAO.BancoDAO;
import DAO.CidadeDAO;
import DAO.EstadoDAO;
import DAO.NucleoDAO;
import beans.Bairro;
import beans.Banco;
import beans.Cidade;
import beans.Estado;
import beans.Login;
import beans.Nucleo;

public class AlteraNucleo implements LogicaDeNegocio{ //Socio(Principal)

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiNucleo.jsp";

		HttpSession sessao = request.getSession();
		Nucleo itemNucleo = new Nucleo();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		int codNucleo = objOperador.getNucleo().getCodigo();
		String nomeNucleo = request.getParameter("desc_nucleo");
		String CNPJNucleo = request.getParameter("cnpj_nucleo");
		String enderecoNucleo = request.getParameter("endereco");
		String codBairro = request.getParameter("cd_bairro");
		String codCep = request.getParameter("cep");
		String codCidade  = request.getParameter("cd_cidade");
		String codEstado = request.getParameter("cd_estado");
		String codBanco = request.getParameter("cd_banco");
		String emailNucleo = request.getParameter("email_nucleo");
		String obsNucleo  = request.getParameter("observacao");
		
		if(codNucleo!=0){	
			itemNucleo.setCodigo(codNucleo);
		}

		if(nomeNucleo!=null&&nomeNucleo!=""){	
			itemNucleo.setNome(nomeNucleo);
		}
		
		if(CNPJNucleo!=null&&CNPJNucleo!=""){	
			itemNucleo.setCnpj(CNPJNucleo);
		}

		if(enderecoNucleo!=null&&enderecoNucleo!=""){
			itemNucleo.setLogradouro(enderecoNucleo);				
		}

		if(codBairro!=null&&!codBairro.equals("")){					

			Bairro objBairro = new Bairro();
			objBairro.setCodigo(Integer.parseInt(codBairro));
			objBairro=BairroDAO.consultarBairro(objBairro);

			itemNucleo.setBairro(objBairro);
		}

		if(codCep!=null&&codCep!=""){
			itemNucleo.setCep(codCep);				
		}

		if(codCidade!=null&&!codCidade.equals("")){					

			Cidade objCidade = new Cidade();
			objCidade.setCodigo(Integer.parseInt(codCidade));
			objCidade=CidadeDAO.consultarCidade(objCidade);
			itemNucleo.setCidade(objCidade);
			
			sessao.setAttribute("objCidade", objCidade);		                
		}

		if(codEstado!=null&&!codEstado.equals("")){					

			Estado objEstado = new Estado();
			objEstado.setCodigo(Integer.parseInt(codEstado));
			objEstado=EstadoDAO.consultarEstado(objEstado);
			itemNucleo.setEstado(objEstado);

			sessao.setAttribute("objEstado", objEstado);		                
		}

		if(codBanco!=null&&!codBanco.equals("")){					

			Banco objBanco = new Banco();
			objBanco.setCodigo(Integer.parseInt(codBanco));
			objBanco=BancoDAO.consultarBanco(objBanco);
			itemNucleo.setBanco_recebimento(objBanco);
		}

		if(emailNucleo!=null&&emailNucleo!=""){
			itemNucleo.setEmail(emailNucleo);				
		}

		if(obsNucleo!=null&&obsNucleo!=""){
			itemNucleo.setObservacao(obsNucleo);				
		}

		if(codNucleo>0){	

			NucleoDAO daoRecF = new NucleoDAO();
			Nucleo itemRecF = daoRecF.consultarNucleo(codNucleo);

			mens = "Cadastro não encontrado: "+nomeNucleo;  

			if(itemRecF!=null){

				NucleoDAO alteraNucleoDAO = new NucleoDAO();
				alteraNucleoDAO.alterarNucleo(itemNucleo);
				mens = "Item atualizado: "+itemNucleo.getNome();
			}
			
			request.setAttribute("objNucleo", itemNucleo);
			sessao.setAttribute("objNucleo", itemNucleo);

		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
