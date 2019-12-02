package logica;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.EntradaDAO;
import DAO.NaoSocioDAO;
import DAO.SaidaDAO;
import beans.Login;
import beans.NaoSocio;

public class ExcluiNaoSocio implements LogicaDeNegocio{ 
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "";
		String pgJsp = "/incluiNaoSocio.jsp";
		String nomeNSoc = request.getParameter("consulta");
		
		HttpSession sessao = request.getSession();
		NaoSocio itemNSoc = new NaoSocio();

		if(nomeNSoc!=null&&nomeNSoc!=""){	
			itemNSoc.setNome(nomeNSoc);

			String codNSoc  = request.getParameter("cod_nsocio");
			
			if(codNSoc!=null&&codNSoc!=""){
				itemNSoc.setCodigo(Integer.parseInt(codNSoc));
			}
			
			Login objOperador = new Login();
			objOperador = (Login)sessao.getAttribute("objUsu");

			NaoSocioDAO daoRecF = new NaoSocioDAO();			
			NaoSocio itemRecF = daoRecF.consultarNaoSoc(itemNSoc,objOperador.getRegiao().getCodigo());
			
			mens = "Cadastro n�o encontrado: "+itemNSoc.getNome();

			if(itemRecF!=null){
				if(SaidaDAO.VerificaSaidaHistorico(itemNSoc.getCodigo(),null)){
					mens = "O fornecedor "+itemNSoc.getNome()+" foi utilizado por n�cleo(s) da Regi�o. EXCLUS�O BLOQUEADA!";
				}else{
					mens = "O n�o associado "+itemNSoc.getNome()+" � parte em recibo(s). EXCLUS�O BLOQUEADA!";
					if(!EntradaDAO.VerificaEntradaHistorico(itemNSoc.getCodigo(), objOperador.getNucleo())){
						NaoSocioDAO daoExcF = new NaoSocioDAO();
						daoExcF.excluirSoc(itemNSoc);
						mens = "Cadastro exclu�do: "+itemRecF.getNome();
						itemNSoc=null;
					}
						
				}
			}
			request.setAttribute("objNSoc", itemNSoc);
			sessao.setAttribute("objNSoc", itemNSoc);
			
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
