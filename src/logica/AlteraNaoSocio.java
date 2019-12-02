package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Login;
import beans.NaoSocio;
import beans.Nucleo;


import DAO.NaoSocioDAO;
import DAO.SaidaDAO;

public class AlteraNaoSocio implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiNaoSocio.jsp";
		String nomeNSoc = request.getParameter("consulta");
		String codNSoc  = request.getParameter("cod_nsocio");
		
		HttpSession sessao = request.getSession();
		NaoSocio itemNSoc = new NaoSocio();
		
		if((nomeNSoc!=null&&nomeNSoc!="")||(codNSoc!=null&&codNSoc!="")){	
			itemNSoc.setNome(nomeNSoc);

			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");

			String teleNSoc = request.getParameter("telefone_nsocio");
			String cpfCnpj  = request.getParameter("cpf_cnpj");
			String emailNSoc = request.getParameter("email_nsocio");
			
			if(codNSoc!=null&&codNSoc!=""){
				itemNSoc.setCodigo(Integer.parseInt(codNSoc));
			}
			
			if(teleNSoc!=null&&teleNSoc!=""){	
				itemNSoc.setTelefone(teleNSoc);
			}
			
			if(cpfCnpj!=null&&cpfCnpj!=""){	
				itemNSoc.setCpfCnpj(cpfCnpj);
			}

			if(codNSoc!=null&&codNSoc!=""){
				itemNSoc.setCodigo(Integer.parseInt(codNSoc));
			}

			if(emailNSoc!=null&&emailNSoc!=""){	
				itemNSoc.setEmail(emailNSoc);
			}

			Nucleo objNucleo = new Nucleo();
			objNucleo = (Nucleo)sessao.getAttribute("objNucleo");
			
			NaoSocioDAO daoRecF = new NaoSocioDAO();
			NaoSocio itemRecF = new NaoSocio();

			itemNSoc.setRegiao(objNucleo.getRegiao());
			itemRecF = daoRecF.consultarNaoSoc(itemNSoc,objOperador.getRegiao().getCodigo());
			
			mens = "Cadastro não encontrado: "+itemNSoc.getNome();  

			if(itemRecF!=null){
				if(SaidaDAO.VerificaSaidaHistorico(itemNSoc.getCodigo(),objNucleo)&&!itemRecF.getNome().equals(itemNSoc.getNome())){
					mens = "O fornecedor "+itemNSoc.getNome()+" foi utilizado por núcleo(s) da Região. ALTERAÇÃO BLOQUEADA!";
				}else{
					NaoSocioDAO daoAltF = new NaoSocioDAO(); 
					daoAltF.alterarNaoSoc(itemNSoc);

					NaoSocioDAO daoConNSocio = new NaoSocioDAO();
					itemNSoc = daoConNSocio.consultarNaoSoc(itemNSoc,objOperador.getRegiao().getCodigo());

					mens = "Item atualizado: "+itemNSoc.getNome();
				}
			}

			sessao.setAttribute("objNSoc", itemNSoc);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
